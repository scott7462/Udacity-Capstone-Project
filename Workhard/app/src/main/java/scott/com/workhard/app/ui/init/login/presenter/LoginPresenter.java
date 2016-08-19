package scott.com.workhard.app.ui.init.login.presenter;

import rx.Subscriber;
import scott.com.workhard.R;
import scott.com.workhard.app.base.presenter.BasePresenter;
import scott.com.workhard.app.base.presenter.Presenter;
import scott.com.workhard.models.User;
import timber.log.Timber;

/**
 * @author pedroscott. scott7462@gmail.com
 * @version 7/14/16.
 * <p>
 * Copyright (C) 2015 The Android Open Source Project
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 *  @see <a href = "http://www.aprenderaprogramar.com" /> http://www.apache.org/licenses/LICENSE-2.0 </a>
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class LoginPresenter extends BasePresenter implements Presenter<LoginPresenterListeners> {

    private LoginPresenterListeners listeners;

    public void doLogin(String email, String password) {
        listeners.showProgressIndicator();
        setSubscription(getDataManager()
                .login(email, password)
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {
                        listeners.removeProgressIndicator();
                        listeners.navigateToMain();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getMessage());
                        listeners.showMessage(R.string.error_login);
                    }

                    @Override
                    public void onNext(User user) {
                        Timber.e(user.toString());
                    }
                }));
    }

    @Override
    public void attachView(LoginPresenterListeners listeners) {
        this.listeners = listeners;
    }

    @Override
    public void detachView() {
        this.listeners = null;
        if (getSubscription() != null) getSubscription().unsubscribe();
    }
}
