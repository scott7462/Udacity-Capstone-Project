package scott.com.workhard.app.ui.home.presenter;

import android.support.annotation.IntDef;
import android.util.Log;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import rx.Subscriber;
import scott.com.workhard.base.presenter.BasePresenter;
import scott.com.workhard.data.Injection;
import scott.com.workhard.entities.Workout;

/**
 * @author pedroscott. scott7462@gmail.com
 * @version 10/10/16.
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


public class PresenterWorkouts extends BasePresenter<WorkoutsPresenterListener> {

    public static final int HOME = 1234;
    public static final int HISTORY = 4321;
    public static final int MY_WORKOUTS = 1987;

    @TypeCalls
    public int getTypeCall(int anInt) {
        switch (anInt) {
            case HOME:
                return HOME;
            case HISTORY:
                return HISTORY;
            case MY_WORKOUTS:
                return MY_WORKOUTS;
        }
        return HOME;
    }

    @IntDef({HOME, HISTORY, MY_WORKOUTS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TypeCalls {
    }

    public void doGetWorkouts(@TypeCalls int typeCall) {
        switch (typeCall) {
            case HOME:
                doCallGeneralWorkouts();
                break;

            case HISTORY:
                doCallWorkoutsHistory();
                break;

            case MY_WORKOUTS:
                doCallMyWorkouts();
                break;
        }

    }

    private void doCallMyWorkouts() {
        setSubscription(Injection.provideWorkoutsRepository()
                .findMyWorkouts()
                .subscribe(new Subscriber<List<Workout>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Test", e.toString());
                    }

                    @Override
                    public void onNext(List<Workout> workouts) {
                        getViewListener().onLoadWorkoutLoad(workouts);
                    }
                }));
    }

    private void doCallWorkoutsHistory() {
        setSubscription(Injection.provideWorkoutsRepository()
                .findHistoriesWorkouts()
                .subscribe(new Subscriber<List<Workout>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("Test", e.toString());
                    }

                    @Override
                    public void onNext(List<Workout> workouts) {
                        getViewListener().onLoadWorkoutLoad(workouts);
                    }
                }));
    }

    private void doCallGeneralWorkouts() {
//        setSubscription(Injection.provideWorkoutsRepository()
//                .findMyWorkouts()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<List<Workout>>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d("Test", e.toString());
//                    }
//
//                    @Override
//                    public void onNext(List<Workout> workouts) {
//                        getViewListener().onLoadWorkoutLoad(workouts);
//                    }
//                }));
    }

}
