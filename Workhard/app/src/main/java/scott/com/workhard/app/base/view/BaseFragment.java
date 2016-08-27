package scott.com.workhard.app.base.view;

import android.content.Context;
import android.support.v4.app.Fragment;

import rx.subscriptions.CompositeSubscription;
import scott.com.workhard.bus.BusProvider;

/**
 * @author pedroscott,
 * @version 7/14/16.
 *          <p>
 *          Copyright (C) 2015 The Android Open Source Project
 *          <p/>
 *          Licensed under the Apache License, Version 2.0 (the "License");
 *          you may not use this file except in compliance with the License.
 *          You may obtain a copy of the License at
 *          <p/>
 * @see <a href = "http://www.aprenderaprogramar.com" /> http://www.apache.org/licenses/LICENSE-2.0 </a>
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class BaseFragment extends Fragment {

    private CompositeSubscription subscription;

    public CompositeSubscription getSubscription() {
        return subscription;
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        BusProvider.getInstance().register(this);
        subscription = new CompositeSubscription();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        BusProvider.getInstance().unregister(this);
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

}
