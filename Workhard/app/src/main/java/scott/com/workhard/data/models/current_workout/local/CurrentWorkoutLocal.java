package scott.com.workhard.data.models.current_workout.local;

import java.util.List;

import io.realm.Realm;
import rx.Observable;
import rx.functions.Func1;
import scott.com.workhard.data.models.current_workout.CurrentWorkoutRepository;
import scott.com.workhard.data.sourse.db.realm_utils.RealmObservable;
import scott.com.workhard.data.sourse.db.tables.CurrentWorkoutTable;
import scott.com.workhard.entities.Workout;

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
public class CurrentWorkoutLocal implements CurrentWorkoutRepository {

    private static CurrentWorkoutLocal instance;

    public static CurrentWorkoutLocal newInstance() {
        if (instance == null) {
            instance = new CurrentWorkoutLocal();
        }
        return instance;
    }

    /**
     * Method add or update the current workout that the user are doing
     *
     * @param workout to create the temporal workout to do reference to the workout that the user are doing
     * @return Observable<Workout>
     */
    @Override
    public Observable<Workout> add(final Workout workout) {
        return RealmObservable.object(new Func1<Realm, CurrentWorkoutTable>() {
            @Override
            public CurrentWorkoutTable call(Realm realm) {
                return realm.copyToRealmOrUpdate(new CurrentWorkoutTable(workout));
            }
        }).flatMap(new Func1<CurrentWorkoutTable, Observable<Workout>>() {
            @Override
            public Observable<Workout> call(CurrentWorkoutTable currentWorkoutTable) {
                return currentWorkoutTable.transformToWorkout();
            }
        });
    }

    /**
     * Method to finish the workout and remove the value of the database
     *
     * @return Observable<Boolean>
     */
    @Override
    public Observable<Boolean> finishWorkout(Workout workout) {
        return RealmObservable.remove(new Func1<Realm, Boolean>() {
            @Override
            public Boolean call(Realm realm) {
                return realm.where(CurrentWorkoutTable.class).findAll().deleteAllFromRealm();
            }
        });
    }

    /**
     * Method get the Rx to get to realm Workout
     *
     * @return Observable<Workout> Return the workout that the user are doing
     */
    @Override
    public Observable<Workout> findCurrentWorkout() {
        return RealmObservable.object(new Func1<Realm, CurrentWorkoutTable>() {
            @Override
            public CurrentWorkoutTable call(Realm realm) {
                return realm.where(CurrentWorkoutTable.class).findFirst();
            }
        }).flatMap(new Func1<CurrentWorkoutTable, Observable<Workout>>() {
            @Override
            public Observable<Workout> call(CurrentWorkoutTable currentWorkoutTable) {
                return currentWorkoutTable.transformToWorkout();
            }
        });
    }

    @Override
    public Observable<Boolean> delete(Workout object) {
        return null;
    }

    @Override
    public Observable<Workout> update(Workout object) {
        return null;
    }

    @Override
    public Observable<List<Workout>> findAll() {
        return null;
    }


}
