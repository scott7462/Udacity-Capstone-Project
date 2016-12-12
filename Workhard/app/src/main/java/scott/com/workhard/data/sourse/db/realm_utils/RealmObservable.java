package scott.com.workhard.data.sourse.db.realm_utils;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmResults;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author pedroscott. scott7462@gmail.com
 * @version 7/18/16.
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
public final class RealmObservable {

    public static <T extends RealmObject> Observable<T> object(final Func1<Realm, T> function) {
        return Observable.create(new OnSubscribeRealm<T>() {
            @Override
            public T get(Realm realm) {
                return function.call(realm);
            }
        });
    }

    public static <T extends RealmObject> Observable<RealmList<T>> list(final Func1<Realm, RealmList<T>> function) {
        return Observable.create(new OnSubscribeRealm<RealmList<T>>() {
            @Override
            public RealmList<T> get(Realm realm) {
                return function.call(realm);
            }
        });
    }

    public static <T extends RealmObject> Observable<RealmResults<T>> results(final Func1<Realm, RealmResults<T>> function) {
        return Observable.create(new OnSubscribeRealm<RealmResults<T>>() {
            @Override
            public RealmResults<T> get(Realm realm) {
                return function.call(realm);
            }
        });
    }

    public static <T> Observable<T> remove(final Func1<Realm, T> function) {
        return Observable.create(new OnSubscribeRealm<T>() {
            @Override
            public T get(Realm realm) {
                return function.call(realm);
            }
        });
    }

}