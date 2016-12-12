package scott.com.workhard.entities;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntDef;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
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
@IgnoreExtraProperties
public class Workout implements Parcelable {

    public static final String WORKOUT_ARG = Workout.class.getName();
    public static final String CURRENT_ROUND_ARG = "current_round_in_workout";

    public static final int DOING_EXERCISE = 100;
    public static final int RECOVERY_TIME = 200;
    public static final int RECOVERY_TIME_LARGE = 300;
    public static final int COMPLETED = 400;
    public static final String WORKOUT_TABLE = "user_workouts";
    public static final String OWNER = "owner";
    public static final String HISTORY_WORKOUT = "history_workout_user";

    @Status
    public static int getValidStatus(int status) {
        switch (status) {
            case DOING_EXERCISE:
                return DOING_EXERCISE;
            case RECOVERY_TIME:
                return RECOVERY_TIME;
            case RECOVERY_TIME_LARGE:
                return RECOVERY_TIME_LARGE;
            case COMPLETED:
                return COMPLETED;
            default:
                return DOING_EXERCISE;
        }
    }


    @IntDef({DOING_EXERCISE, RECOVERY_TIME, RECOVERY_TIME_LARGE, COMPLETED})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Status {
    }

    private String key;
    private String owner;
    private int restBetweenExercise;
    private int restRoundsExercise;
    private int rounds;
    private String name;
    private List<Exercise> exerciseList = new ArrayList<>();
    @Exclude
    private boolean errorMessageInName;

    /**
     * Current elements current workout and current exercise;
     */
    @Exclude
    @Workout.Status
    private int status = DOING_EXERCISE;
    private String currentExercise;
    private int currentExercisePosition;
    private int currentRound = 1;

    public Workout() {
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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

    public String getCurrentExercise() {
        return currentExercise;
    }

    public void setCurrentExercise(String currentExercise) {
        this.currentExercise = currentExercise;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    @Status
    public int getStatus() {
        return status;
    }

    public void setStatus(@Status int status) {
        this.status = status;
    }

    public int getCurrentExercisePosition() {
        return currentExercisePosition;
    }

    public void setCurrentExercisePosition(int currentExercisePosition) {
        this.currentExercisePosition = currentExercisePosition;
    }

    public Workout withKey(String key) {
        setKey(key);
        return this;
    }

    public Workout withName(String name) {
        setName(name);
        return this;
    }

    public Workout withRestBetweenExercise(int restBetweenExercise) {
        setRestBetweenExercise(restBetweenExercise);
        return this;
    }

    public Workout withRestRoundsExercise(int restRoundsExercise) {
        setRestRoundsExercise(restRoundsExercise);
        return this;
    }

    public Workout withRounds(int rounds) {
        setRounds(rounds);
        return this;
    }

    public Workout withExercises(List<Exercise> exercises) {
        setExerciseList(exercises);
        return this;
    }

    public Workout withCurrentExercise(String currentExercise) {
        setCurrentExercise(currentExercise);
        return this;
    }

    public Workout withCurrentRound(int currentRound) {
        setCurrentRound(currentRound);
        return this;
    }

    public Workout withStatus(@Status int status) {
        setStatus(status);
        return this;
    }

    public Workout withExercisePosition(int position) {
        setCurrentExercisePosition(position);
        return this;
    }

    public Workout finishRecoveryTime() {
        setStatus(DOING_EXERCISE);
        return this;
    }

    public void updateToNextStep() {
        Exercise exercise = findNextExercise();
        if (exercise != null) {
            setStatus(getRestBetweenExercise() != 0 ? RECOVERY_TIME : DOING_EXERCISE);
            setCurrentExercise(exercise.getKey());
            setCurrentExercisePosition(getCurrentExercisePosition() + 1);
        } else if (!isTheLastExercise()) {
            setStatus(getRestBetweenExercise() != 0 ? RECOVERY_TIME_LARGE : DOING_EXERCISE);
            setCurrentRound(getCurrentRound() + 1);
            setCurrentExercise(getExerciseList().get(0).getKey());
            setCurrentExercisePosition(0);
        } else {
            completeRecoveryTime();
        }
    }

    public void completeRecoveryTime() {
        setStatus(COMPLETED);
    }

    public boolean isTheLastExercise() {
        return findNextExercise() == null && getCurrentRound() == rounds;
    }

    public Exercise findCurrentExercise() {
        return findCurrentExercise(0);
    }

    public Exercise findNextExercise() {
        return findNextExercise(0);
    }

    public Exercise findNextExercise(int position) {
        if (getExerciseList() != null) {
            if (getCurrentExercise() == null) {
                return null;
            }
            if (position >= getExerciseList().size()) {
                return null;
            }
            if (getExerciseList().get(position).getKey().equals(getCurrentExercise())
                    && position + 1 < getExerciseList().size() && position == getCurrentExercisePosition()) {
                return getExerciseList().get(position + 1);
            } else {
                return findNextExercise(++position);
            }
        } else {
            return null;
        }
    }

    private Exercise findCurrentExercise(int position) {
        if (getExerciseList() != null) {
            if (getCurrentExercise() == null) {
                setCurrentExercise(getExerciseList().get(0).getKey());
                return getExerciseList().get(0);
            }

            if (position >= getExerciseList().size()) {
                setCurrentExercise(getExerciseList().get(0).getKey());
                return getExerciseList().get(0);
            }

            if (getExerciseList().get(position).getKey().equals(getCurrentExercise()) && position == getCurrentExercisePosition()) {
                return getExerciseList().get(position);
            } else {
                return findCurrentExercise(++position);
            }

        } else {
            return null;
        }
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.key);
        dest.writeString(this.owner);
        dest.writeInt(this.restBetweenExercise);
        dest.writeInt(this.restRoundsExercise);
        dest.writeInt(this.rounds);
        dest.writeString(this.name);
        dest.writeTypedList(this.exerciseList);
        dest.writeByte(this.errorMessageInName ? (byte) 1 : (byte) 0);
        dest.writeInt(this.status);
        dest.writeString(this.currentExercise);
        dest.writeInt(this.currentExercisePosition);
        dest.writeInt(this.currentRound);
    }

    protected Workout(Parcel in) {
        this.key = in.readString();
        this.owner = in.readString();
        this.restBetweenExercise = in.readInt();
        this.restRoundsExercise = in.readInt();
        this.rounds = in.readInt();
        this.name = in.readString();
        this.exerciseList = in.createTypedArrayList(Exercise.CREATOR);
        this.errorMessageInName = in.readByte() != 0;
        this.status = getValidStatus(in.readInt());
        this.currentExercise = in.readString();
        this.currentExercisePosition = in.readInt();
        this.currentRound = in.readInt();
    }

    public static final Creator<Workout> CREATOR = new Creator<Workout>() {
        @Override
        public Workout createFromParcel(Parcel source) {
            return new Workout(source);
        }

        @Override
        public Workout[] newArray(int size) {
            return new Workout[size];
        }
    };

}
