package scott.com.workhard.data.models.current_workout;

import android.support.annotation.NonNull;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;
import scott.com.workhard.base.model.BaseDataManager;
import scott.com.workhard.data.models.current_workout.preference.CurrentWorkoutPreference;
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
public class CurrentWorkoutDataManager extends BaseDataManager<Workout, CurrentWorkoutRepository> implements CurrentWorkoutRepository {

    private static CurrentWorkoutDataManager INSTANCE = null;

    public CurrentWorkoutDataManager(@NonNull CurrentWorkoutRepository fireBaseRepository, @NonNull CurrentWorkoutRepository localRepository) {
        super(fireBaseRepository, localRepository);
    }

    @NonNull
    public static CurrentWorkoutDataManager newInstance(@NonNull CurrentWorkoutRepository fireBaseRepository, @NonNull CurrentWorkoutRepository dbRepository) {
        if (INSTANCE == null) {
            INSTANCE = new CurrentWorkoutDataManager(fireBaseRepository, dbRepository);
        }
        return INSTANCE;
    }


    @Override
    public Observable<Workout> add(Workout workout) {
        CurrentWorkoutPreference.setPreferenceCurrentWorkOut(true);
        return getLocalRepository().add(workout);
    }

    @Override
    public Observable<Boolean> delete(Workout workout) {
        return null;
    }

    @Override
    public Observable<Workout> update(Workout workout) {
        return null;
    }

    @Override
    public Observable<List<Workout>> findAll() {
        return null;
    }

    @Override
    public Observable<Boolean> finishWorkout(final Workout workout) {
        return getFireBaseRepository().finishWorkout(workout)
                .flatMap(new Func1<Boolean, Observable<Boolean>>() {
                    @Override
                    public Observable<Boolean> call(Boolean aBoolean) {
                        CurrentWorkoutPreference.setPreferenceCurrentWorkOut(false);
                        return getLocalRepository().finishWorkout(workout);
                    }
                });
    }

    @Override
    public Observable<Workout> findCurrentWorkout() {
        return getLocalRepository().findCurrentWorkout();
    }

    public boolean isCurrentWorkout() {
        return CurrentWorkoutPreference.getPreferenceCurrentWorkOut();
    }
}
