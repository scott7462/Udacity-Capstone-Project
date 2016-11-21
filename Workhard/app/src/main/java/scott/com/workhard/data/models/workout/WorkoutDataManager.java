package scott.com.workhard.data.models.workout;

import android.support.annotation.NonNull;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;
import scott.com.workhard.base.model.BaseDataManager;
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


public class WorkoutDataManager extends BaseDataManager<Workout, WorkoutRepository> implements WorkoutRepository {

    private static WorkoutDataManager INSTANCE = null;

    public WorkoutDataManager(@NonNull WorkoutRepository restRepository, @NonNull WorkoutRepository dbRepository) {
        super(restRepository, dbRepository);
    }

    @NonNull
    public static WorkoutDataManager newInstance(@NonNull WorkoutRepository restRepository, @NonNull WorkoutRepository dbRepository) {
        if (INSTANCE == null) {
            INSTANCE = new WorkoutDataManager(restRepository, dbRepository);
        }
        return INSTANCE;
    }

    @Override
    public Observable<Workout> add(Workout workout) {
        if (workout != null)
            for (int i = 0; i < workout.getExerciseList().size(); i++) {
                workout.getExerciseList().get(i).setPosition(i);
            }
        return getRestRepository().add(workout);
    }

    @Override
    public Observable<Boolean> delete(final Workout workout) {
        return getRestRepository().delete(workout)
                .flatMap(new Func1<Boolean, Observable<Boolean>>() {
                    @Override
                    public Observable<Boolean> call(Boolean aBoolean) {
                        return getDbRepository().delete(workout);
                    }
                });
    }

    @Override
    public Observable<Workout> update(Workout workout) {
        return null;
    }

    @Override
    public Observable<List<Workout>> findAll() {
        return getRestRepository().findAll();
    }

    public Observable<List<Workout>> findMyWorkouts() {
        return getRestRepository().findMyWorkouts();
    }

    @Override
    public Observable<List<Workout>> findHistoriesWorkouts() {
        return getRestRepository().findHistoriesWorkouts();
    }
}
