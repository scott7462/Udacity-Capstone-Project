package scott.com.workhard.data.models.session;

import android.support.annotation.NonNull;

import rx.Observable;
import rx.functions.Func1;
import scott.com.workhard.base.model.BaseDataManager;
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


public class SessionDataManager extends BaseDataManager<User, SessionRepository>  implements SessionRepository {

    private static SessionDataManager instance = null;

    @NonNull
    public static SessionDataManager newInstance(@NonNull SessionRepository restRepository, @NonNull SessionRepository dbRepository) {
        if (instance == null) {
            instance = new SessionDataManager(restRepository, dbRepository);
        }
        return instance;
    }

    public SessionDataManager(@NonNull SessionRepository restRepository, @NonNull SessionRepository dbRepository) {
        super(restRepository, dbRepository);
    }

    @Override
    public Observable<User> login(String email, String password) {
        return getRestRepository().login(email, password)
                .flatMap(new Func1<User, Observable<User>>() {
                    @Override
                    public Observable<User> call(User user) {
                        return getDbRepository().add(user);
                    }
                });
    }

    @Override
    public Observable<Boolean> logout() {
        return getDbRepository().logout();
    }

    @Override
    public Observable<User> getSessionUser() {
        return getDbRepository().getSessionUser();
    }

    @Override
    public Observable<Boolean> delete(User object) {
        return null;
    }


}
