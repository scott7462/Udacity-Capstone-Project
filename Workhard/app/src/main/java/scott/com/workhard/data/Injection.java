package scott.com.workhard.data;

import scott.com.workhard.data.models.current_workout.CurrentWorkoutDataManager;
import scott.com.workhard.data.models.current_workout.local.CurrentWorkoutLocal;
import scott.com.workhard.data.models.current_workout.remote.CurrentWorkoutRemote;
import scott.com.workhard.data.models.exercise.ExerciseDataManager;
import scott.com.workhard.data.models.exercise.firebase.ExerciseFireBase;
import scott.com.workhard.data.models.session.SessionDataManager;
import scott.com.workhard.data.models.session.sourse.firebase.SessionFireBaseData;
import scott.com.workhard.data.models.workout.WorkoutDataManager;
import scott.com.workhard.data.models.workout.local.WorkoutLocal;
import scott.com.workhard.data.models.workout.remote.WorkoutRemote;

/**
 * Created by androiddev3 on 10/5/16.
 */

public class Injection {

    public static CurrentWorkoutDataManager provideCurrentWorkoutRepository() {
        return CurrentWorkoutDataManager.newInstance(CurrentWorkoutRemote.newInstance(), CurrentWorkoutLocal.newInstance());
    }

    public static WorkoutDataManager provideWorkoutsRepository() {
        return WorkoutDataManager.newInstance(WorkoutRemote.newInstance(), WorkoutLocal.newInstance());
    }

    public static ExerciseDataManager provideExercisesRepository() {
        return ExerciseDataManager.newInstance(ExerciseFireBase.newInstance());
    }


    public static SessionDataManager provideSessionRepository() {
        return SessionDataManager.newInstance(SessionFireBaseData.newInstance());
    }


}
