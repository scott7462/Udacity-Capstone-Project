package scott.com.workhard.app.ui.init.login.presenter;

import rx.Subscriber;
import scott.com.workhard.R;
import scott.com.workhard.base.presenter.BasePresenter;
import scott.com.workhard.data.Injection;
import scott.com.workhard.data.sourse.rest.ApiErrorRest;
import scott.com.workhard.entities.User;

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

public class SessionPresenter extends BasePresenter<SessionPresenterListeners> {

    public void doLogin(String email, String password) {
        setSubscription(Injection.provideSessionRepository()
                .login(email, password)
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        getViewListener().showProgressIndicator("login");
                    }

                    @Override
                    public void onCompleted() {
                        getViewListener().removeProgressIndicator();
                        getViewListener().onLoginSuccessful();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getViewListener().removeProgressIndicator();
                        getViewListener().showMessage(ApiErrorRest.handelError(e));
                    }

                    @Override
                    public void onNext(User user) {

                    }
                }));
    }


    public void doLoginWithTwitter(String userName, long userId) {
        doLogin("", "");
    }

    public void doLoginWithGoogle(String email, String name, String lastName, String acctId) {
        doLogin("", "");
    }

    public void doRegister(String name, String lastName, String email, String password, String date) {
        setSubscription(Injection.provideSessionRepository()
                .register(new User().withName(name)
                        .withLastName(lastName)
                        .withEmail(email)
                        .withBirthday(date)
                        .withPassword(password))
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {
                        getViewListener().removeProgressIndicator();
                        getViewListener().onRegisterSuccessful();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getViewListener().removeProgressIndicator();
                        getViewListener().showMessage(ApiErrorRest.handelError(e));
                    }

                    @Override
                    public void onNext(User user) {

                    }
                }));
    }

}
