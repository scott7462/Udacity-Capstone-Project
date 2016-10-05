package scott.com.workhard.data.models.exercise.remote;

import android.support.annotation.NonNull;

import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import scott.com.workhard.R;
import scott.com.workhard.app.App;
import scott.com.workhard.data.models.exercise.ExerciseRepository;
import scott.com.workhard.data.sourse.rest.api.RestClient;
import scott.com.workhard.data.sourse.rest.response.ResponseExercises;
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


public class ExerciseRemoteData implements ExerciseRepository {

    private static ExerciseRemoteData INSTANCE = null;
    private RestClient restClientPublic;

    @NonNull
    public static ExerciseRemoteData newInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ExerciseRemoteData();
        }
        return INSTANCE;
    }

    public ExerciseRemoteData() {
        restClientPublic = new RestClient(App.getGlobalContext().getString(R.string.base_url));
    }

    public RestClient getRestClientPublic() {
        return restClientPublic;
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

    @Override
    public Observable<List<Exercise>> findAll() {
        return getRestClientPublic().getPublicService().exercises()
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .flatMap(new Func1<ResponseExercises, Observable<List<Exercise>>>() {
                    @Override
                    public Observable<List<Exercise>> call(ResponseExercises responseExercises) {
                        return Observable.just(responseExercises.getExercises());
                    }
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Observable.error(throwable);
                    }
                });
    }
}
