package scott.com.workhard.data.models.session.sourse.remote;

import android.support.annotation.NonNull;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import scott.com.workhard.R;
import scott.com.workhard.app.App;
import scott.com.workhard.data.models.session.SessionRepository;
import scott.com.workhard.data.sourse.rest.api.RestClient;
import scott.com.workhard.data.sourse.rest.request.RequestLogin;
import scott.com.workhard.data.sourse.rest.response.ResponseLogin;
import scott.com.workhard.entities.User;

/**
 * @author pedroscott. scott7462@gmail.com
 * @version 9/17/16.
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


public class SessionRemoteData implements SessionRepository {

    private static SessionRemoteData instance = null;

    @NonNull
    public static SessionRemoteData newInstance() {
        if (instance == null) {
            instance = new SessionRemoteData();
        }
        return instance;
    }

    private RestClient restClientPublic;

    public SessionRemoteData() {
        restClientPublic = new RestClient(App.getGlobalContext().getString(R.string.base_url));
    }

    public RestClient getRestClientPublic() {
        return restClientPublic;
    }

    @Override
    public Observable<User> login(String email, String password) {
        final RequestLogin requestLogin = new RequestLogin("scott7452", "casa");
        return getRestClientPublic().getPublicService().login(requestLogin)
                .flatMap(new Func1<ResponseLogin, Observable<User>>() {
                    @Override
                    public Observable<User> call(ResponseLogin responseLogin) {
                        return Observable.just(responseLogin.getUser().withToken(responseLogin.getToken()));
                    }
                });
    }

    @Override
    public Observable<Boolean> logout() {
        return null;
    }

    @Override
    public Observable<User> getSessionUser() {
        return null;
    }

    @Override
    public Observable<User> add(User object) {

        return null;
    }

    @Override
    public Observable<Boolean> delete(User object) {
        return null;
    }

    @Override
    public Observable<User> update(User object) {
        return null;
    }

    @Override
    public Observable<List<User>> findAll() {
        return null;
    }

}
