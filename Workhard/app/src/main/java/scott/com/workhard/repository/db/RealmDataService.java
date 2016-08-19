package scott.com.workhard.repository.db;

import io.realm.Realm;
import rx.Observable;
import rx.functions.Func1;
import scott.com.workhard.models.User;
import scott.com.workhard.repository.db.realm_utils.RealmObservable;

/**
 * @author pedroscott. scott7462@gmail.com
 * @version 7/18/16.
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

public class RealmDataService implements DataService {

    @Override
    public Observable<User> getUserById(String id) {
        return null;
    }

    @Override
    public Observable<User> saveUser(final User user) {
        return RealmObservable.object(new Func1<Realm, User>() {
            @Override
            public User call(Realm realm) {
                return realm.copyToRealmOrUpdate(user);
            }
        });
    }

    @Override
    public Observable<Void> removeUser(User user) {
        return null;
    }


}
