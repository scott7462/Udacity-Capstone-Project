package scott.com.workhard.app.ui.do_workout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscriber;
import scott.com.workhard.R;
import scott.com.workhard.base.view.BaseActivity;
import scott.com.workhard.data.exercise.ExerciseDataManager;
import scott.com.workhard.data.exercise.sourse.local.ExerciseLocalData;
import scott.com.workhard.data.exercise.sourse.remote.ExerciseRemoteData;
import scott.com.workhard.entities.Exercise;

/**
 * @author Pedro Scott. scott7462@gmail.com
 * @version 7/12/16.
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

public class DoWorkoutActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_workout);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setToolbar(toolbar);
        savedFragmentState(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        ExerciseDataManager
                .newInstance(ExerciseRemoteData.newInstance(), ExerciseLocalData.newInstance())
                .findAll()
                .subscribe(new Subscriber<List<Exercise>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("Test", e.getMessage());
                    }

                    @Override
                    public void onNext(List<Exercise> exercises) {
                        Log.e("Test", exercises.toString());
                    }
                });
    }

    private void savedFragmentState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            navigateMainContent(getSupportFragmentManager().getFragment(
                    savedInstanceState, "mContent"), getString(R.string.frg_do_rest_workoud_title));
        } else {
            navigateMainContent(FrgDoRestWorkout.newInstance(), getString(R.string.frg_do_rest_workoud_title));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "mContent", getSupportFragmentManager().findFragmentById(R.id.container));
    }

    public static void newInstance(Activity activity) {
        activity.startActivity(new Intent(activity, DoWorkoutActivity.class));
    }


}
