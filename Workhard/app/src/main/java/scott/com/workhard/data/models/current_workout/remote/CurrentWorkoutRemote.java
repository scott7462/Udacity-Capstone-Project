package scott.com.workhard.data.models.current_workout.remote;

import java.util.List;

import rx.Observable;
import scott.com.workhard.data.models.current_workout.CurrentWorkoutRepository;
import scott.com.workhard.entities.Workout;

/**
 * Created by androiddev3 on 10/5/16.
 */

public class CurrentWorkoutRemote implements CurrentWorkoutRepository {
    @Override
    public Observable<Workout> add(Workout object) {
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
    public Observable<Boolean> finishWorkout() {
        return null;
    }
}
