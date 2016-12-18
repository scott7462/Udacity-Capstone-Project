package scott.com.workhard.service;

import android.app.IntentService;
import android.content.Intent;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func3;
import rx.subscriptions.CompositeSubscription;
import scott.com.workhard.data.Injection;
import scott.com.workhard.entities.Workout;
import timber.log.Timber;

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
public class SyncWorkoutsService extends IntentService {

    public SyncWorkoutsService() {
        super("SyncWorkoutsService");
    }

    private CompositeSubscription subscription = new CompositeSubscription();

    @Override
    protected void onHandleIntent(Intent intent) {
        subscription.add(Observable.zip(Injection.provideWorkoutsRepository().findAll(),
                Injection.provideWorkoutsRepository().findMyWorkouts(),
                Injection.provideWorkoutsRepository().findHistoriesWorkouts(),
                new Func3<List<Workout>, List<Workout>, List<Workout>, List<Workout>>() {
                    @Override
                    public List<Workout> call(List<Workout> workouts, List<Workout> workouts2, List<Workout> workouts3) {
                        return workouts;
                    }
                })
                .subscribe());

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
