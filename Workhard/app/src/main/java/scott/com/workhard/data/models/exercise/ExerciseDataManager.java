package scott.com.workhard.data.models.exercise;

import android.support.annotation.NonNull;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;
import scott.com.workhard.base.model.BaseDataManager;
import scott.com.workhard.entities.Exercise;

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


public class ExerciseDataManager extends BaseDataManager<Exercise, ExerciseRepository> {

    private static ExerciseDataManager INSTANCE = null;

    public ExerciseDataManager(@NonNull ExerciseRepository restRepository, @NonNull ExerciseRepository dbRepository) {
        super(restRepository, dbRepository);
    }

    @NonNull
    public static ExerciseDataManager newInstance(@NonNull ExerciseRepository restRepository, @NonNull ExerciseRepository dbRepository) {
        if (INSTANCE == null) {
            INSTANCE = new ExerciseDataManager(restRepository, dbRepository);
        }
        return INSTANCE;
    }

    @Override
    public Observable<Exercise> add(Exercise object) {
        return null;
    }

    @Override
    public Observable<Boolean> delete(Exercise object) {
        return null;
    }

    @Override
    public Observable<List<Exercise>> findAll() {
        return getDbRepository().findAll()
                .flatMap(new Func1<List<Exercise>, Observable<List<Exercise>>>() {
                    @Override
                    public Observable<List<Exercise>> call(List<Exercise> exercises) {
                        Observable.just(exercises);
                        return getRestRepository().findAll();
                    }
                })
                .flatMap(new Func1<List<Exercise>, Observable<Exercise>>() {
                    @Override
                    public Observable<Exercise> call(List<Exercise> exercises) {
                        return Observable.from(exercises);
                    }
                })
                .flatMap(new Func1<Exercise, Observable<Exercise>>() {
                    @Override
                    public Observable<Exercise> call(Exercise exercise) {
                        return getDbRepository().add(exercise);
                    }
                }).toList();
    }
}
