package scott.com.workhard.data.models.workout;

import android.support.annotation.NonNull;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
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

    private static WorkoutDataManager instance = null;

    public WorkoutDataManager(@NonNull WorkoutRepository fireBaseRepository) {
        super(fireBaseRepository);
    }

    @NonNull
    public static WorkoutDataManager newInstance(@NonNull WorkoutRepository fireBaseRepository) {
        if (instance == null) {
            instance = new WorkoutDataManager(fireBaseRepository);
        }
        return instance;
    }

    @Override
    public Observable<Workout> add(Workout workout) {
        if (workout != null)
            for (int i = 0; i < workout.getExerciseList().size(); i++) {
                workout.getExerciseList().get(i).setPosition(i);
            }
        return getFireBaseRepository().add(workout)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Boolean> delete(Workout workout) {
        return getFireBaseRepository().delete(workout)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<Workout> update(Workout workout) {
        return null;
    }

    @Override
    public Observable<List<Workout>> findAll() {
        return  getFireBaseRepository().findAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<Workout>> findMyWorkouts() {
        return getFireBaseRepository().findMyWorkouts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Workout>> findHistoriesWorkouts() {
        return getFireBaseRepository().findHistoriesWorkouts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
