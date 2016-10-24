package scott.com.workhard.data.models.session.sourse.local;

import android.support.annotation.NonNull;

import java.util.List;

import io.realm.Realm;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import scott.com.workhard.data.models.session.SessionRepository;
import scott.com.workhard.data.sourse.db.realm_utils.RealmObservable;
import scott.com.workhard.data.sourse.db.tables.CurrentWorkoutTable;
import scott.com.workhard.data.sourse.db.tables.ExerciseTable;
import scott.com.workhard.data.sourse.db.tables.TokenTable;
import scott.com.workhard.data.sourse.db.tables.UserTable;
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


public class SessionLocalData implements SessionRepository {

    private static SessionLocalData instance = null;

    @NonNull
    public static SessionLocalData newInstance() {
        if (instance == null) {
            instance = new SessionLocalData();
        }
        return instance;
    }


    @Override
    public Observable<User> login(String email, String password) {
        return null;
    }

    @Override
    public Observable<Boolean> logout() {
        return RealmObservable.remove(new Func1<Realm, Boolean>() {
            @Override
            public Boolean call(Realm realm) {
                realm.where(CurrentWorkoutTable.class).findAll().deleteAllFromRealm();
                realm.where(ExerciseTable.class).findAll().deleteAllFromRealm();
                realm.where(TokenTable.class).findAll().deleteAllFromRealm();
                return realm.where(UserTable.class).findAll().deleteAllFromRealm();
            }
        });
    }

    @Override
    public Observable<User> getSessionUser() {
        return RealmObservable.object(new Func1<Realm, UserTable>() {
            @Override
            public UserTable call(Realm realm) {
                return realm.where(UserTable.class).findFirst();
            }
        }).flatMap(new Func1<UserTable, Observable<User>>() {
            @Override
            public Observable<User> call(UserTable userTable) {
                return userTable.transformToUser();
            }
        });
    }

    @Override
    public Observable<User> register(User user) {
        return null;
    }

    @Override
    public Observable<User> add(final User user) {
        return RealmObservable.object(new Func1<Realm, UserTable>() {
            @Override
            public UserTable call(Realm realm) {
                return realm.copyToRealmOrUpdate(new UserTable(user));
            }
        }).flatMap(new Func1<UserTable, Observable<User>>() {
            @Override
            public Observable<User> call(UserTable userTable) {
                return userTable.transformToUser();
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Boolean> delete(User object) {
        return null;
    }

    @Override
    public Observable<User> update(User user) {
        return add(user);
    }

    @Override
    public Observable<List<User>> findAll() {
        return null;
    }

}