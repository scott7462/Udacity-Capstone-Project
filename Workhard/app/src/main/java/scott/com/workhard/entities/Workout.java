package scott.com.workhard.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pedroscott. scott7462@gmail.com
 * @version 9/4/16.
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
public class Workout implements Parcelable {

    public static final String WORKOUT_ARG = Workout.class.getName();
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("rest_between_exercise")
    @Expose
    private int restBetweenExercise;
    @SerializedName("rest_rounds_exercise")
    @Expose
    private int restRoundsExercise;
    @SerializedName("rounds")
    @Expose
    private int rounds;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("date_completed")
    @Expose
    private String dateCompleted;
    @SerializedName("list_exercise")
    @Expose
    private List<Exercise> exerciseList = new ArrayList<>();
    private boolean errorMessageInName;

    public int getRestBetweenExercise() {
        return restBetweenExercise;
    }

    public void setRestBetweenExercise(int restBetweenExercise) {
        this.restBetweenExercise = restBetweenExercise;
    }

    public int getRestRoundsExercise() {
        return restRoundsExercise;
    }

    public void setRestRoundsExercise(int restRoundsExercise) {
        this.restRoundsExercise = restRoundsExercise;
    }

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Exercise> getExerciseList() {
        return exerciseList;
    }

    public void setExerciseList(List<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.restBetweenExercise);
        dest.writeInt(this.restRoundsExercise);
        dest.writeInt(this.rounds);
        dest.writeString(this.name);
        dest.writeList(this.exerciseList);
        dest.writeByte(this.errorMessageInName ? (byte) 1 : (byte) 0);
    }

    public Workout() {
    }

    protected Workout(Parcel in) {
        this.id = in.readInt();
        this.restBetweenExercise = in.readInt();
        this.restRoundsExercise = in.readInt();
        this.rounds = in.readInt();
        this.name = in.readString();
        this.exerciseList = new ArrayList<Exercise>();
        in.readList(this.exerciseList, Exercise.class.getClassLoader());
        this.errorMessageInName = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Workout> CREATOR = new Parcelable.Creator<Workout>() {
        @Override
        public Workout createFromParcel(Parcel source) {
            return new Workout(source);
        }

        @Override
        public Workout[] newArray(int size) {
            return new Workout[size];
        }
    };

    public String getDateCompleted() {
        return dateCompleted;
    }
}
