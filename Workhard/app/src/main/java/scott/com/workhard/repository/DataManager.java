package scott.com.workhard.repository;


import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import scott.com.workhard.app.App;
import scott.com.workhard.models.User;
import scott.com.workhard.repository.db.RealmDataService;
import scott.com.workhard.repository.rest.response.ResponseLogin;

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
public class DataManager {

    private final RealmDataService realmDataService = new RealmDataService();

    public Observable<User> login(String email, String password) {
        return App.getRestClientPublic().getPublicService()
                .login(email, password)
                .flatMap(new Func1<ResponseLogin, Observable<User>>() {
                    @Override
                    public rx.Observable<User> call(ResponseLogin responseLogin) {
                        return Observable.just(responseLogin.getUser());
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .concatMap(new Func1<User, Observable<User>>() {
                    @Override
                    public Observable<User> call(User user) {
                        return realmDataService.saveUser(user);
                    }
                });
    }


}
