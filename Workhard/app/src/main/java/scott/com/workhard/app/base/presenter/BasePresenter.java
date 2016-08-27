package scott.com.workhard.app.base.presenter;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;
import scott.com.workhard.repository.DataManager;

/**
 * @author pedroscott. scott7462@gmail.com
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

public class BasePresenter<V extends BasePresenterListener> implements Presenter<V> {

    private V baseViewListener;

    private CompositeSubscription subscription = new CompositeSubscription();

    private final DataManager dataManager = new DataManager();

    protected Subscription getSubscription() {
        return subscription;
    }

    protected void setSubscription(Subscription subscription) {
        this.subscription.add(subscription);
    }

    protected DataManager getDataManager() {
        return dataManager;
    }

    @Override
    public void attachView(V viewListener) {
        this.baseViewListener = viewListener;
    }

    @Override
    public void detachView() {
        if (this.subscription != null && !this.subscription.isUnsubscribed()) {
            this.subscription.unsubscribe();
        }
        baseViewListener = null;
    }

    public V getViewListener() {
        checkViewAttached();
        return baseViewListener;
    }

    public boolean isViewAttached() {
        return baseViewListener != null;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new PresenterException();
    }

    public static class PresenterException extends RuntimeException {
        public PresenterException() {
            super("Please attach view listener before use the methods.");
        }
    }

}
