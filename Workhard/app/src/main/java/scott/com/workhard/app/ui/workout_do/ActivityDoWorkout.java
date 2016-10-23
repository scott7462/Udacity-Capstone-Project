package scott.com.workhard.app.ui.workout_do;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.v7.widget.Toolbar;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.greenrobot.eventbus.Subscribe;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import scott.com.workhard.R;
import scott.com.workhard.app.ui.workout_do.presenter.DoWorkoutPresenter;
import scott.com.workhard.app.ui.workout_do.presenter.DoWorkoutPresenterListeners;
import scott.com.workhard.app.ui.workout_resume.ActivityWorkoutResume;
import scott.com.workhard.app.ui.workout_resume.FrgWorkoutResume;
import scott.com.workhard.base.view.BaseActivity;
import scott.com.workhard.bus.event.EventAddRoundToWorkout;
import scott.com.workhard.bus.event.EventFinishRecoveryTime;
import scott.com.workhard.bus.event.EventFinishWorkout;
import scott.com.workhard.bus.event.EventNextExercise;
import scott.com.workhard.entities.Workout;

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

public class ActivityDoWorkout extends BaseActivity implements DoWorkoutPresenterListeners,
        MultiplePermissionsListener {

    private static final String TYPE_INIT = "Init_type";
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    DoWorkoutPresenter presenter;
    private Workout workout;

    public static final int NEW_WORKOUT = 100;
    public static final int CONTINUE_CURRENT_WORKOUT = 200;

    @IntDef({NEW_WORKOUT, CONTINUE_CURRENT_WORKOUT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface InitType {
    }

    @InitType
    private int initType;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initPresenter();
        Dexter.checkPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    private void initPresenter() {
        presenter = new DoWorkoutPresenter();
        presenter.attachView(this);
    }

    private void initView() {
        setContentView(R.layout.activity_create_workout);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setToolbar(toolbar);
    }

    @Override
    protected void onDestroy() {
        presenter.detachView();
        super.onDestroy();
    }

    public static void newInstance(Activity activity, @InitType int initType, Workout workout) {
        Intent intent = setTypeIntent(activity, initType);
        intent.putExtra(Workout.WORKOUT_ARG, workout);
        activity.startActivity(intent);
    }

    public static void newInstance(Activity activity, @InitType int initType) {
        activity.startActivity(setTypeIntent(activity, initType));
    }

    private static Intent setTypeIntent(Activity activity, @InitType int initType) {
        Intent intent = new Intent(activity, ActivityDoWorkout.class);
        intent.putExtra(TYPE_INIT, initType);
        return intent;
    }


    @Override
    public void onGetCurrentWorkout(Workout workout) {
        this.workout = workout;
        goToCurrentStep();
    }

    private void goToCurrentStep() {
        if (workout != null) {
            switch (workout.getStatus()) {
                case Workout.DOING_EXERCISE:
                    navigateMainContent(FrgDoWorkout.newInstance(workout), workout.getName());
                    break;
                case Workout.RECOVERY_TIME:
                    navigateMainContent(FrgDoRestWorkout.newInstance(workout, workout.getRestBetweenExercise()), workout.getName());
                    break;
                case Workout.RECOVERY_TIME_LARGE:
                    navigateMainContent(FrgDoRestWorkout.newInstance(workout, workout.getRestRoundsExercise()), workout.getName());
                    break;
                case Workout.COMPLETED:
                    finish();
                    break;
            }
        }
    }

    @Subscribe
    public void onNextExercise(EventNextExercise eventNextExercise) {
        presenter.doGoToNextExercise(workout);
    }

    @Subscribe
    public void onFinishRecoveryTime(EventFinishRecoveryTime eventFinishRecoveryTime) {
        presenter.doFinishRecoveryTime(workout);
    }

    @Subscribe
    public void onFinishRecoveryTime(EventFinishWorkout eventFinishWorkout) {
        presenter.doFinishWorkout();
    }

    @Override
    public void onErrorSavingWorkout() {

    }

    @Override
    public void onErrorFinishingWorkout() {
    }

    @Override
    public void onFinishWorkout() {
        ActivityWorkoutResume.newInstance(this, workout, FrgWorkoutResume.FINISH);
        finish();
    }

    @Override
    public void showProgressIndicator(String message) {

    }

    @Override
    public void removeProgressIndicator() {

    }

    @Override
    public void showMessage(int stringId) {

    }


    private void initWorkout() {
        switch (getIntent().getIntExtra(TYPE_INIT, NEW_WORKOUT)) {
            case NEW_WORKOUT:
                presenter.doSaveWorkout((Workout) getIntent().getParcelableExtra(Workout.WORKOUT_ARG));
                break;
            case CONTINUE_CURRENT_WORKOUT:
                presenter.doGetCurrentWorkout();
                break;
        }
    }

    @Override
    public void onPermissionsChecked(MultiplePermissionsReport report) {
        if (report.areAllPermissionsGranted() && report.getGrantedPermissionResponses().size() == 2) {
            for (PermissionGrantedResponse permissionGrantedResponse : report.getGrantedPermissionResponses()) {
                if (!validatePermission(permissionGrantedResponse)) {
                    return;
                }
            }
        }
        initWorkout();
    }

    private boolean validatePermission(PermissionGrantedResponse permissionGrantedResponse) {
        return permissionGrantedResponse.getPermissionName().equals(Manifest.permission.READ_EXTERNAL_STORAGE) ||
                permissionGrantedResponse.getPermissionName().equals(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
        token.continuePermissionRequest();
    }

    @Override
    public void onBackPressed() {
    }

    @Subscribe
    public void addRound(EventAddRoundToWorkout eventAddRoundToWorkout) {
        workout.setRounds(workout.getRounds() + 1);
        presenter.doGoToNextExercise(workout);
    }
}
