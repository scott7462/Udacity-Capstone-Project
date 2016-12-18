package scott.com.workhard.app.ui.init.login.presenter;

import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.twitter.sdk.android.core.TwitterSession;

import rx.Subscriber;
import scott.com.workhard.R;
import scott.com.workhard.app.App;
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

    /**
     * Method do the auth of the a simple user by email and password
     *
     * @param email    The email of the user
     * @param password The password to auth the user
     *                 <p>
     *                 Rx function to call the listener onLoginSuccessful() or showMessage() in case of error
     */
    public void doLogin(String email, String password) {
        setSubscription(Injection.provideSessionRepository()
                .login(email, password)
                .subscribe(subscriptionSingIn(App.getGlobalContext().getString(R.string.frg_login_loading_login))));
    }

    /**
     * Method do the auth or register a simple user by Twitter SDK
     *
     * @param session The TwitterSession provide by the Twitter SDK to manage the auth
     *                <p>
     *                Rx function to call the listener onLoginSuccessful() or showMessage() in case of error
     */
    public void doLoginWithTwitter(TwitterSession session) {
        setSubscription(Injection.provideSessionRepository()
                .loginTwitter(session.getAuthToken().token, session.getAuthToken().secret)
                .subscribe(subscriptionSingIn(App.getGlobalContext().getString(R.string.frg_login_loading_login))));
    }

    /**
     * Method do the auth or register a simple user by Google SDK
     *
     * @param acct The GoogleSignInAccount provide by the Google SDK to manage the auth
     *             <p>
     *             Rx function to call the listener onLoginSuccessful() or showMessage() in case of error
     */
    public void doLoginWithGoogle(GoogleSignInAccount acct) {
        setSubscription(Injection.provideSessionRepository()
                .loginGoogle(acct.getIdToken())
                .subscribe(subscriptionSingIn(App.getGlobalContext().getString(R.string.frg_login_loading_login))));
    }

    /**
     * Method do the auth or register a simple user by Facebook SDK
     *
     * @param token The LoginResult provide by the Facebook SDK to manage the auth
     *              <p>
     *              Rx function to call the listener onLoginSuccessful() or showMessage() in case of error
     */
    public void doLoginWithFacebook(LoginResult token) {
        setSubscription(Injection.provideSessionRepository()
                .loginFacebook(token)
                .subscribe(subscriptionSingIn(App.getGlobalContext().getString(R.string.frg_login_loading_login))));

    }


    /**
     * Method do the register a simple user by information of the user email and password
     *
     * @param name     The name to display by the user
     * @param lastName The last name to display by the user
     * @param email    The email to display by the user
     * @param password The password to display by the user
     * @param date     The date to display by the user on milliseconds
     *                 <p>
     *                 Rx function to call the listener onLoginSuccessful() or showMessage() in case of error
     */
    public void doRegister(String name, String lastName, String email, String password, long date) {
        setSubscription(Injection.provideSessionRepository()
                .register(new User().withName(name)
                        .withLastName(lastName)
                        .withEmail(email)
                        .withBirthday(date)
                        .withPassword(password))
                .subscribe(subscriptionSingIn(App.getGlobalContext().getString(R.string.frg_register_loading))));
    }

    /**
     * Method do the register a simple user by information of the user email and password
     *
     * @param message The text to show or indicate to show on the progress.
     *                <p>
     *                Rx function to call the listener onLoginSuccessful() or showMessage() in case of error
     *                and show or remove the progress indicator with the method showProgressIndicator() or removeProgressIndicator()
     *                if the connection have an error indicate to teh view with showMessage()
     */
    private Subscriber<User> subscriptionSingIn(final String message) {
        return new Subscriber<User>() {
            @Override
            public void onStart() {
                super.onStart();
                getViewListener().showProgressIndicator(message);
            }

            @Override
            public void onCompleted() {
                getViewListener().onLoginSuccessful();
            }

            @Override
            public void onError(Throwable e) {
                getViewListener().removeProgressIndicator();
                getViewListener().showMessage(ApiErrorRest.handelError(e));
            }

            @Override
            public void onNext(User user) {
                getViewListener().removeProgressIndicator();
            }
        };
    }


}
