package scott.com.workhard.app.ui.init.launch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import scott.com.workhard.R;
import scott.com.workhard.app.ui.ActivityMain;
import scott.com.workhard.app.ui.MainPresenterListener;
import scott.com.workhard.app.ui.PresenterMain;
import scott.com.workhard.app.ui.init.ActivityInit;
import scott.com.workhard.base.view.BaseActivity;
import scott.com.workhard.entities.User;
import scott.com.workhard.widget.WidgetUtils;

/**
 * @author pedroscott. scott7462@gmail.com
 * @version 7/12/16.
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

public class LaunchActivity extends BaseActivity implements MainPresenterListener {

    private PresenterMain presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        WidgetUtils.updateWidget();
        presenter = new PresenterMain();
        presenter.attachView(this);
        addSubscription(Observable.timer(2, TimeUnit.SECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        presenter.doGetSession();
                    }
                }));
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    public static void newInstance(Context activity) {
        activity.startActivity(new Intent(activity, LaunchActivity.class));
    }

    @Override
    public void onLogoutSuccessful() {

    }

    @Override
    public void onLogoutError() {

    }

    @Override
    public void onNotCurrentSession() {
        ActivityInit.newInstance(this);
        finish();
    }

    @Override
    public void onCurrentSession(User user) {
        ActivityMain.newInstance(this);
        finish();
    }

    @Override
    public void showProgressIndicator(String message) {

    }

    @Override
    public void removeProgressIndicator() {

    }

    @Override
    public void showMessage(String stringId) {

    }
}
