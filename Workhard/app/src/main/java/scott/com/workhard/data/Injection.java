package scott.com.workhard.data;

import scott.com.workhard.data.models.current_workout.CurrentWorkoutDataManager;
import scott.com.workhard.data.models.current_workout.local.CurrentWorkoutLocal;
import scott.com.workhard.data.models.current_workout.remote.CurrentWorkoutRemote;
import scott.com.workhard.data.models.exercise.ExerciseDataManager;
import scott.com.workhard.data.models.exercise.local.ExerciseLocalData;
import scott.com.workhard.data.models.exercise.remote.ExerciseRemoteData;
import scott.com.workhard.data.models.session.SessionDataManager;
import scott.com.workhard.data.models.session.sourse.local.SessionLocalData;
import scott.com.workhard.data.models.session.sourse.remote.SessionRemoteData;

/**
 * Created by androiddev3 on 10/5/16.
 */

public class Injection {

    public static CurrentWorkoutDataManager provideCurrentWorkoutRepository() {
        return CurrentWorkoutDataManager.newInstance(CurrentWorkoutRemote.newInstance(), CurrentWorkoutLocal.newInstance());
    }

    public static ExerciseDataManager provideExercisesRepository() {
        return ExerciseDataManager.newInstance(ExerciseRemoteData.newInstance(), ExerciseLocalData.newInstance());
    }


    public static SessionDataManager provideSessionRepository() {
        return SessionDataManager.newInstance(SessionRemoteData.newInstance(), SessionLocalData.newInstance());
    }


}
