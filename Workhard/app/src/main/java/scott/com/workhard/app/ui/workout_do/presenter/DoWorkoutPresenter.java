package scott.com.workhard.app.ui.workout_do.presenter;

import android.util.Log;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import scott.com.workhard.base.presenter.BasePresenter;
import scott.com.workhard.data.Injection;
import scott.com.workhard.entities.Workout;

/**
 * @author pedroscott. scott7462@gmail.com
 * @version 7/14/16.
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

public class DoWorkoutPresenter extends BasePresenter<DoWorkoutPresenterListeners> {
    /**
     * Method save the current workout on the internal database
     * @param workout is the workout that the user want to save on the database to keep doing if the app was close
     *              <p>
     *              Rx function to call the listener onGetCurrentWorkout() or showMessage() in case of error
     */
    public void doSaveWorkout(Workout workout) {
        setSubscription(Injection.provideCurrentWorkoutRepository()
                .add(workout)
                .subscribe(new Subscriber<Workout>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("Error", e.toString());
                        getViewListener().onErrorSavingWorkout();
                    }

                    @Override
                    public void onNext(Workout workout) {
                        getViewListener().onGetCurrentWorkout(workout);
                    }
                }));
    }
    /**
     * Method get the current workout that the user are doing
     *              <p>
     *              Rx function to call the listener onGetCurrentWorkout() or showMessage() in case of error
     */
    public void doGetCurrentWorkout() {
        setSubscription(Injection.provideCurrentWorkoutRepository()
                .findCurrentWorkout().subscribe(new Subscriber<Workout>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Workout workout) {
                        getViewListener().onGetCurrentWorkout(workout);
                    }
                }));

    }
    /**
     * Method update the current exercises on the current workout
     * @param workout that the user want to update
     *              <p>
     *              Rx function to call the listener onCreateWorkoutSuccess() or showMessage() in case of error
     */
    public void doGoToNextExercise(Workout workout) {
        if (workout.isTheLastExercise()) {
            doFinishWorkout();
        } else {
            workout.updateToNextStep();
            doSaveWorkout(workout);
        }
    }
    /**
     * Method update the finish and change state of tje current workout
     * @param workout that the user want to update
     *              <p>
     *              Rx function to call the listener onCreateWorkoutSuccess() or showMessage() in case of error
     */
    public void doFinishRecoveryTime(Workout workout) {
        doSaveWorkout(workout.finishRecoveryTime());
    }
    /**
     * Method finish the workout remove to the database and notify the server to create history workout
     *              <p>
     *              Rx function to call the listener onCreateWorkoutSuccess() or showMessage() in case of error
     */
    public void doFinishWorkout() {
        setSubscription(Injection.provideCurrentWorkoutRepository()
                .findCurrentWorkout()
                .flatMap(new Func1<Workout, Observable<Boolean>>() {
                    @Override
                    public Observable<Boolean> call(Workout workout) {
                        return Injection.provideCurrentWorkoutRepository().finishWorkout(workout);
                    }
                })
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getViewListener().onErrorFinishingWorkout();
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            getViewListener().onFinishWorkout();
                        } else {
                            getViewListener().onErrorFinishingWorkout();
                        }
                    }
                }));

    }
}
