package scott.com.workhard.data.models.current_workout.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import rx.Observable;
import scott.com.workhard.data.models.current_workout.CurrentWorkoutRepository;
import scott.com.workhard.data.models.session.sourse.preference.SessionPreference;
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
public class CurrentWorkoutFireBase implements CurrentWorkoutRepository {

    private static CurrentWorkoutFireBase instance;

    public static CurrentWorkoutFireBase newInstance() {
        if (instance == null) {
            instance = new CurrentWorkoutFireBase();
            instance.getFireBaseWorkoutHistoryReference().keepSynced(true);

        }
        return instance;
    }

    @Override
    public Observable<Workout> add(final Workout workout) {
        return null;
    }

    /**
     * Method to finish the workout and remove the value of the database
     *
     * @return Observable<Boolean>
     */
    @Override
    public Observable<Boolean> finishWorkout(Workout workout) {
        workout.setOwner(SessionPreference.getPreferenceToken());
        getFireBaseWorkoutHistoryReference().push().setValue(workout);
        return Observable.just(true);
    }

    @Override
    public Observable<Workout> findCurrentWorkout() {
        return null;
    }

    @Override
    public Observable<Boolean> delete(Workout object) {
        return null;
    }

    @Override
    public Observable<Workout> update(Workout object) {
        return null;
    }

    @Override
    public Observable<List<Workout>> findAll() {
        return null;
    }

    /**
     * Method get the preference to user on FireBase
     *
     * @return DatabaseReference the preference to user child on User in FireBase
     */
    private DatabaseReference getFireBaseWorkoutHistoryReference() {
        return FirebaseDatabase.getInstance().getReference().child(Workout.HISTORY_WORKOUT);
    }
}
