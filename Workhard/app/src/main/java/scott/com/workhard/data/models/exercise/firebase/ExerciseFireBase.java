package scott.com.workhard.data.models.exercise.firebase;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kelvinapps.rxfirebase.RxFirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;
import scott.com.workhard.data.models.exercise.ExerciseRepository;
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
public class ExerciseFireBase implements ExerciseRepository {

    private static ExerciseFireBase instance = null;

    @NonNull
    public static ExerciseFireBase newInstance() {
        if (instance == null) {
            instance = new ExerciseFireBase();
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            instance.getFireBaseExercisesReference().keepSynced(true);
        }
        return instance;
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
    public Observable<Exercise> update(Exercise object) {
        return null;
    }

    /**
     * Method get all the exercises the Rx function to transform the DataSnapshot in Exercises.
     *
     * @return Observable List<Exercise>
     */
    @Override
    public Observable<List<Exercise>> findAll() {
        return RxFirebaseDatabase.observeSingleValueEvent(getFireBaseExercisesReference().orderByChild(Exercise.NAME))
                .flatMap(new Func1<DataSnapshot, Observable<List<Exercise>>>() {
                    @Override
                    public Observable<List<Exercise>> call(DataSnapshot dataSnapshot) {
                        List<Exercise> exercises = new ArrayList<>();
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            Exercise exercise = postSnapshot.getValue(Exercise.class);
                            exercise.setKey(postSnapshot.getKey());
                            exercises.add(exercise);
                        }
                        return Observable.just(exercises);
                    }
                });
    }

    /**
     * Method get the preference to exercises on FireBase
     *
     * @return DatabaseReference the preference to user child on Exercises in FireBase
     */
    private DatabaseReference getFireBaseExercisesReference() {
        return FirebaseDatabase.getInstance().getReference().child(Exercise.EXERCISE_TABLE);
    }
}
