package scott.com.workhard.app.ui.profile.presenter;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import scott.com.workhard.base.presenter.BasePresenter;
import scott.com.workhard.data.Injection;
import scott.com.workhard.data.sourse.rest.ApiErrorRest;
import scott.com.workhard.entities.User;

/**
 * @author pedroscott. scott7462@gmail.com
 * @version 10/23/16.
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


public class ProfilePresenter extends BasePresenter<ProfilePresenterListener> {


    public void doGetProfile() {
        setSubscription(Injection.provideSessionRepository()
                .getSessionUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<User>() {

                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        getViewListener().showMessage(ApiErrorRest.handelError(e));
                    }

                    @Override
                    public void onNext(User user) {
                        getViewListener().onLoadUser(user);
                    }
                }));
    }

    public void doUpdateProfile(String name, String lastName, String email, String date) {
        setSubscription(Injection.provideSessionRepository()
                .update(new User().withName(name)
                        .withLastName(lastName)
                        .withEmail(email)
                        .withBirthday(date))
                .subscribe(new Subscriber<User>() {
                    @Override
                    public void onCompleted() {
                        getViewListener().removeProgressIndicator();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getViewListener().removeProgressIndicator();
                        getViewListener().showMessage(ApiErrorRest.handelError(e));
                    }

                    @Override
                    public void onNext(User user) {
                        getViewListener().onLoadUser(user);
                    }
                }));
    }
}
