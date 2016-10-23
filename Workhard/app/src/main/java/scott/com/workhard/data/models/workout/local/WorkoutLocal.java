package scott.com.workhard.data.models.workout.local;

import java.util.List;

import io.realm.Realm;
import rx.Observable;
import rx.functions.Func1;
import scott.com.workhard.data.models.current_workout.CurrentWorkoutRepository;
import scott.com.workhard.data.models.workout.WorkoutRepository;
import scott.com.workhard.data.sourse.db.realm_utils.RealmObservable;
import scott.com.workhard.data.sourse.db.tables.CurrentWorkoutTable;
import scott.com.workhard.entities.Workout;

/**
 * Created by androiddev3 on 10/5/16.
 */

public class WorkoutLocal implements WorkoutRepository {

    private static WorkoutLocal instance;

    public static WorkoutLocal newInstance() {
        if (instance == null) {
            instance = new WorkoutLocal();
        }
        return instance;
    }

    @Override
    public Observable<Workout> add(final Workout workout) {
        return null;
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


    @Override
    public Observable<List<Workout>> findMyWorkouts() {
        return null;
    }

    @Override
    public Observable<List<Workout>> findHistoriesWorkouts() {
        return null;
    }
}
