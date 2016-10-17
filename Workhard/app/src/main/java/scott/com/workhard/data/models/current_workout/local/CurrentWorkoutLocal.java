package scott.com.workhard.data.models.current_workout.local;

import java.util.List;

import io.realm.Realm;
import rx.Observable;
import rx.functions.Func1;
import scott.com.workhard.data.models.current_workout.CurrentWorkoutRepository;
import scott.com.workhard.data.models.current_workout.remote.CurrentWorkoutRemote;
import scott.com.workhard.data.sourse.db.realm_utils.RealmObservable;
import scott.com.workhard.data.sourse.db.tables.CurrentWorkoutTable;
import scott.com.workhard.entities.Workout;

/**
 * Created by androiddev3 on 10/5/16.
 */

public class CurrentWorkoutLocal implements CurrentWorkoutRepository {


    private static CurrentWorkoutLocal instance;

    public static CurrentWorkoutLocal newInstance() {
        if (instance == null) {
            instance = new CurrentWorkoutLocal();
        }
        return instance;
    }

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

    @Override
    public Observable<Boolean> finishWorkout() {
        return RealmObservable.remove(new Func1<Realm, Boolean>() {
            @Override
            public Boolean call(Realm realm) {
                return realm.where(CurrentWorkoutTable.class).findAll().deleteAllFromRealm();
            }
        });
    }

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
