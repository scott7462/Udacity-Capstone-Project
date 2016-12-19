package scott.com.workhard.data.models.workout.firebase;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kelvinapps.rxfirebase.RxFirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;
import scott.com.workhard.app.App;
import scott.com.workhard.data.models.session.sourse.preference.SessionPreference;
import scott.com.workhard.data.models.workout.WorkoutRepository;
import scott.com.workhard.data.provider.DataPersister;
import scott.com.workhard.entities.Workout;

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
public class WorkoutFireBase implements WorkoutRepository {

    private static WorkoutFireBase instance;

    public static WorkoutFireBase newInstance() {
        if (instance == null) {
            instance = new WorkoutFireBase();
            instance.getFireWorkoutsUserReference().keepSynced(true);
            instance.getFireWorkoutsUserHistoryReference().keepSynced(true);
            instance.getFireWorkoutsReference().keepSynced(true);
        }
        return instance;
    }

    @Override
    public Observable<Workout> add(Workout workout) {
        workout.setOwner(SessionPreference.getPreferenceToken());
        getFireWorkoutsUserReference().push().setValue(workout);
        return Observable.just(workout);
    }

    @Override
    public Observable<Boolean> delete(Workout workout) {
        getFireWorkoutsUserReference()
                .child(workout.getKey()).removeValue();
        return RxFirebaseDatabase.observeSingleValueEvent(getFireWorkoutsUserReference()
                .child(workout.getKey()))
                .flatMap(new Func1<DataSnapshot, Observable<Boolean>>() {
                    @Override
                    public Observable<Boolean> call(DataSnapshot dataSnapshot) {
                        return Observable.just(!dataSnapshot.exists());
                    }
                });

    }

    @Override
    public Observable<Workout> update(Workout workout) {
        return null;
    }


    @Override
    public Observable<List<Workout>> findAll() {
        return RxFirebaseDatabase.observeSingleValueEvent(
                getFireWorkoutsReference())
                .flatMap(transformWorkoutsModelsWithResponse());
    }

    @Override
    public Observable<List<Workout>> findMyWorkouts() {
        return RxFirebaseDatabase.observeSingleValueEvent(
                getFireWorkoutsUserReference()
                        .orderByChild(Workout.OWNER).equalTo(SessionPreference.getPreferenceToken()))
                .flatMap(transformWorkoutsModelsWithResponse());
    }

    @Override
    public Observable<List<Workout>> findHistoriesWorkouts() {
        return RxFirebaseDatabase.observeSingleValueEvent(
                getFireWorkoutsUserHistoryReference()
                        .orderByChild(Workout.OWNER).equalTo(SessionPreference.getPreferenceToken()))
                .flatMap(transformWorkoutsModelsWithResponse())
                .flatMap(new Func1<List<Workout>, Observable<List<Workout>>>() {
                    @Override
                    public Observable<List<Workout>> call(List<Workout> workouts) {
                        DataPersister dataPersister = new DataPersister(App.getGlobalContext().getContentResolver());
                        dataPersister.removeAllHistory();
                        dataPersister.addWorkout(workouts);
                        return Observable.just(workouts);
                    }
                });
    }

    @Override
    public Observable<Boolean> deleteWorkoutHistory(Workout workout) {
        getFireWorkoutsUserHistoryReference()
                .child(workout.getKey()).removeValue();
        return RxFirebaseDatabase.observeSingleValueEvent(getFireWorkoutsUserHistoryReference()
                .child(workout.getKey()))
                .flatMap(new Func1<DataSnapshot, Observable<Boolean>>() {
                    @Override
                    public Observable<Boolean> call(DataSnapshot dataSnapshot) {
                        return Observable.just(!dataSnapshot.exists());
                    }
                });
    }


    private Func1<DataSnapshot, Observable<List<Workout>>> transformWorkoutsModelsWithResponse() {
        return new Func1<DataSnapshot, Observable<List<Workout>>>() {
            @Override
            public Observable<List<Workout>> call(DataSnapshot dataSnapshot) {
                List<Workout> workouts = new ArrayList<>();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Workout workout = postSnapshot.getValue(Workout.class);
                    workout.setKey(postSnapshot.getKey());
                    workouts.add(workout);
                }
                return Observable.just(workouts);
            }
        };
    }

    /**
     * Method get the preference to user workouts  on FireBase
     *
     * @return DatabaseReference the preference to user child on User in FireBase
     */
    private DatabaseReference getFireWorkoutsUserReference() {
        return FirebaseDatabase.getInstance().getReference().child(Workout.WORKOUT_TABLE);
    }

    /**
     * Method get the preference to user workouts  on FireBase
     *
     * @return DatabaseReference the preference to user child on User in FireBase
     */
    private DatabaseReference getFireWorkoutsUserHistoryReference() {
        return FirebaseDatabase.getInstance().getReference().child(Workout.HISTORY_WORKOUT);
    }


    /**
     * Method get the preference to user workouts  on FireBase form star workouts
     *
     * @return DatabaseReference the preference to user child on User in FireBase
     */
    private DatabaseReference getFireWorkoutsReference() {
        return FirebaseDatabase.getInstance().getReference().child(Workout.WORKOUTS);
    }


}
