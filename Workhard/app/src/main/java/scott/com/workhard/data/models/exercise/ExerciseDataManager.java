package scott.com.workhard.data.models.exercise;

import android.support.annotation.NonNull;

import java.util.List;

import rx.Observable;
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

    public ExerciseDataManager(@NonNull ExerciseRepository fireBaseRepository) {
        super(fireBaseRepository);
    }

    @NonNull
    public static ExerciseDataManager newInstance(@NonNull ExerciseRepository fireBaseRepository) {
        if (INSTANCE == null) {
            INSTANCE = new ExerciseDataManager(fireBaseRepository);
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
        return getFireBaseRepository().findAll();
    }

}
