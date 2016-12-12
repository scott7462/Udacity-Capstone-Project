package scott.com.workhard.data.sourse.db.tables;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import rx.Observable;
import scott.com.workhard.entities.Exercise;
import scott.com.workhard.entities.Workout;

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
public class CurrentWorkoutTable extends RealmObject {

    @PrimaryKey
    private String id;
    private int restBetweenExercise;
    private int restRoundsExercise;
    private int rounds;
    private String currentExercise;
    private String name;
    private int currentRound = 1;
    private int status;
    public RealmList<ExerciseTable> exercises;
    private int currentExercisePosition;

    public CurrentWorkoutTable(Workout workout) {
        setId(workout.getKey());
        setName(workout.getName());
        setRestBetweenExercise(workout.getRestBetweenExercise());
        setRestRoundsExercise(workout.getRestRoundsExercise());
        setRounds(workout.getRounds());
        setCurrentExercise(workout.getCurrentExercise());
        setCurrentRound(workout.getCurrentRound());
        setStatus(workout.getStatus());
        setCurrentExercisePosition(workout.getCurrentExercisePosition());
        RealmList<ExerciseTable> exerciseTables = new RealmList<>();
        for (Exercise exercise : workout.getExerciseList()) {
            exerciseTables.add(new ExerciseTable(exercise));
        }
        setExercises(exerciseTables);
    }

    public CurrentWorkoutTable() {
    }

    public String getCurrentExercise() {
        return currentExercise;
    }

    public void setCurrentExercise(String currentExercise) {
        this.currentExercise = currentExercise;
    }

    public RealmList<ExerciseTable> getExercises() {
        return exercises;
    }

    public void setExercises(RealmList<ExerciseTable> exercises) {
        this.exercises = exercises;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCurrentExercisePosition() {
        return currentExercisePosition;
    }

    public void setCurrentExercisePosition(int currentExercisePosition) {
        this.currentExercisePosition = currentExercisePosition;
    }

    public Observable<Workout> transformToWorkout() {
        return Observable.just(
                new Workout()
                        .withKey(getId())
                        .withName(getName())
                        .withCurrentExercise(getCurrentExercise())
                        .withRestBetweenExercise(getRestBetweenExercise())
                        .withRestRoundsExercise(getRestRoundsExercise())
                        .withRounds(getRounds())
                        .withCurrentRound(getCurrentRound())
                        .withStatus(Workout.getValidStatus(getStatus()))
                        .withExercises(getExercisesList())
                        .withExercisePosition(getCurrentExercisePosition()));
    }

    public List<Exercise> getExercisesList() {
        List<Exercise> exercisesList = new ArrayList<>();
        for (ExerciseTable exerciseTable : getExercises()) {
            exercisesList.add(exerciseTable.transformToExercise());
        }
        return exercisesList;
    }
}
