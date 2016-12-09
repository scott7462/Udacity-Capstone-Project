package scott.com.workhard.data.models.session;

import android.support.annotation.NonNull;

import com.facebook.login.LoginResult;

import rx.Observable;
import scott.com.workhard.base.model.BaseDataManager;
import scott.com.workhard.data.models.session.sourse.preference.SessionPreference;
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
public class SessionDataManager extends BaseDataManager<User, SessionFireBase> implements SessionFireBase {

    private static SessionDataManager instance = null;

    @NonNull
    public static SessionDataManager newInstance(@NonNull SessionFireBase fireBaseRepository) {
        if (instance == null) {
            instance = new SessionDataManager(fireBaseRepository);
        }
        return instance;
    }

    public SessionDataManager(@NonNull SessionFireBase sessionFireBase) {
        super(sessionFireBase);
    }

    @Override
    public Observable<User> loginFacebook(LoginResult token) {
        return getFireBaseRepository().loginFacebook(token);
    }

    @Override
    public Observable<User> loginTwitter(String token,String secret) {
        return getFireBaseRepository().loginTwitter(token,secret);
    }

    @Override
    public Observable<User> loginGoogle(String idToken) {
        return getFireBaseRepository().loginGoogle(idToken);
    }

    @Override
    public Observable<User> login(String email, String password) {
        return getFireBaseRepository().login(email,password);
    }

    @Override
    public Observable<Boolean> logout() {
        return getFireBaseRepository().logout();
    }

    @Override
    public Observable<User> getSessionUser() {
        return getFireBaseRepository().getSessionUser();
    }

    @Override
    public Observable<User> register(User user) {
        return getFireBaseRepository().register(user);
    }

    @Override
    public Observable<User> update(User user) {
        return getFireBaseRepository().update(user);
    }
    public String getToken() {
        return SessionPreference.getPreferenceToken();
    }

}
