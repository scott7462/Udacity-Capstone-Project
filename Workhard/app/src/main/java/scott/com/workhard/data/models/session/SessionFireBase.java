package scott.com.workhard.data.models.session;

import com.facebook.login.LoginResult;

import rx.Observable;
import scott.com.workhard.base.model.Repository;
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
public interface SessionFireBase extends Repository<User> {

    Observable<User> login(String email, String password);

    Observable<Boolean> logout();

    Observable<User> getSessionUser();

    Observable<User> register(User user);

    Observable<User> loginFacebook(LoginResult token);

    Observable<User> loginTwitter(String token, String secret);

    Observable<User> loginGoogle(String idToken);
}
