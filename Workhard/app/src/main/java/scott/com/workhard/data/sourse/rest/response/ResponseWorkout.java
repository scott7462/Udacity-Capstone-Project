package scott.com.workhard.data.sourse.rest.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import scott.com.workhard.entities.Workout;

/**
 * Created by androiddev3 on 10/21/16.
 */
public class ResponseWorkout {
    @SerializedName("workouts")
    @Expose
    private List<Workout> workouts;

    public List<Workout> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(List<Workout> workouts) {
        this.workouts = workouts;
    }
}
