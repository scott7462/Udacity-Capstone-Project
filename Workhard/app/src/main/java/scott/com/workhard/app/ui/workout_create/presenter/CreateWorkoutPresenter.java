package scott.com.workhard.app.ui.workout_create.presenter;

import rx.Subscriber;
import scott.com.workhard.R;
import scott.com.workhard.app.App;
import scott.com.workhard.base.presenter.BasePresenter;
import scott.com.workhard.data.Injection;
import scott.com.workhard.data.sourse.rest.ApiErrorRest;
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

public class CreateWorkoutPresenter extends BasePresenter<CreateWorkoutPresenterListeners> {

    /**
     * Method create the workout on my workouts list
     *
     * @param workout is the workout that the user want create in the server
     *                <p>
     *                Rx function to call the listener onCreateWorkoutSuccess() or showMessage() in case of error
     */
    public void doCreateWorkout(Workout workout) {
        getViewListener().showProgressIndicator(App.getGlobalContext().getString(R.string.frg_create_workout_creating_workout));
        setSubscription(Injection.provideWorkoutsRepository()
                .add(workout)
                .subscribe(new Subscriber<Workout>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        getViewListener().showMessage(ApiErrorRest.handelError(e));
                        getViewListener().removeProgressIndicator();
                    }

                    @Override
                    public void onNext(Workout workout) {
                        getViewListener().removeProgressIndicator();
                        getViewListener().onCreateWorkoutSuccess();
                    }
                }));

    }

    /**
     * Method remove the  workout on the server
     *
     * @param workout is the workout that the user want to finish on the server
     *                <p>
     *                Rx function to call the listener onCreateWorkoutSuccess() or showMessage() in case of error
     */
    public void onDeleteWorkout(Workout workout, boolean history) {
        getViewListener().showProgressIndicator(App.getGlobalContext().getString(R.string.frg_create_workout_deleting_workout));
        if (history) {
            setSubscription(Injection.provideWorkoutsRepository()
                    .deleteWorkoutHistory(workout)
                    .subscribe(subscriptionDelete()));
        } else {
            setSubscription(Injection.provideWorkoutsRepository()
                    .delete(workout)
                    .subscribe(subscriptionDelete()));
        }

    }

    private Subscriber<Boolean> subscriptionDelete() {
        return new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {
                getViewListener().removeProgressIndicator();
            }

            @Override
            public void onError(Throwable e) {
                getViewListener().showMessage(ApiErrorRest.handelError(e));
                getViewListener().removeProgressIndicator();
            }

            @Override
            public void onNext(Boolean finish) {
                if (finish) {
                    getViewListener().onDeleteWorkoutSuccess();
                }

            }
        };
    }
}
