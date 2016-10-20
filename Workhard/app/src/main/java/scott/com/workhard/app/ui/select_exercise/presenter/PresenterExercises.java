package scott.com.workhard.app.ui.select_exercise.presenter;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import scott.com.workhard.R;
import scott.com.workhard.base.presenter.BasePresenter;
import scott.com.workhard.data.Injection;
import scott.com.workhard.entities.Exercise;

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


public class PresenterExercises extends BasePresenter<ExercisesPresenterListener> {

    public void doGetExercises() {
        getViewListener().showProgressIndicator("");
        setSubscription(Injection.provideExercisesRepository()
                .findAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<Exercise>>() {
                    @Override
                    public void onCompleted() {
                        getViewListener().removeProgressIndicator();
                    }

                    @Override
                    public void onError(Throwable e) {
                        getViewListener().showMessage(R.string.error_loading_exercises);
                        getViewListener().removeProgressIndicator();
                    }

                    @Override
                    public void onNext(List<Exercise> exercises) {
                        getViewListener().onGetListExercises(exercises);
                    }
                }));
    }
}
