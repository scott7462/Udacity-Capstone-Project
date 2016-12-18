package scott.com.workhard.data;

import scott.com.workhard.data.models.current_workout.CurrentWorkoutDataManager;
import scott.com.workhard.data.models.current_workout.firebase.CurrentWorkoutFireBase;
import scott.com.workhard.data.models.current_workout.local.CurrentWorkoutLocal;
import scott.com.workhard.data.models.exercise.ExerciseDataManager;
import scott.com.workhard.data.models.exercise.firebase.ExerciseFireBase;
import scott.com.workhard.data.models.session.SessionDataManager;
import scott.com.workhard.data.models.session.sourse.firebase.SessionFireBaseData;
import scott.com.workhard.data.models.workout.WorkoutDataManager;
import scott.com.workhard.data.models.workout.firebase.WorkoutFireBase;

/**
 * @author pedroscott. scott7462@gmail.com
 * @version 10/3/16.
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
public class Injection {

    public static CurrentWorkoutDataManager provideCurrentWorkoutRepository() {
        return CurrentWorkoutDataManager.newInstance(CurrentWorkoutFireBase.newInstance(), CurrentWorkoutLocal.newInstance());
    }

    public static WorkoutDataManager provideWorkoutsRepository() {
        return WorkoutDataManager.newInstance(WorkoutFireBase.newInstance());
    }

    public static ExerciseDataManager provideExercisesRepository() {
        return ExerciseDataManager.newInstance(ExerciseFireBase.newInstance());
    }


    public static SessionDataManager provideSessionRepository() {
        return SessionDataManager.newInstance(SessionFireBaseData.newInstance());
    }


}
