package scott.com.workhard.app.ui.do_workout.presenter;

import android.util.Log;

import rx.Subscriber;
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

    public void saveWorkout(Workout workout) {
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
                        getViewListener().onSavedWorkout(workout);
                    }
                }));
    }


}
