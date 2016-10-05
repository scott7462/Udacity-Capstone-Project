package scott.com.workhard.data;

import scott.com.workhard.data.models.current_workout.CurrentWorkoutDataManager;
import scott.com.workhard.data.models.current_workout.local.CurrentWorkoutLocal;
import scott.com.workhard.data.models.current_workout.remote.CurrentWorkoutRemote;

/**
 * Created by androiddev3 on 10/5/16.
 */

public class Injection {

    public static CurrentWorkoutDataManager provideCurrentWorkoutRepository() {
        return CurrentWorkoutDataManager.newInstance(new CurrentWorkoutRemote(), new CurrentWorkoutLocal());
    }

}
