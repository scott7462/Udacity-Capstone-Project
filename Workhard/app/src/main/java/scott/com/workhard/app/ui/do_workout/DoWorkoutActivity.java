package scott.com.workhard.app.ui.do_workout;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import scott.com.workhard.R;
import scott.com.workhard.app.ui.do_workout.presenter.DoWorkoutPresenter;
import scott.com.workhard.app.ui.do_workout.presenter.DoWorkoutPresenterListeners;
import scott.com.workhard.base.view.BaseActivity;
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

public class DoWorkoutActivity extends BaseActivity implements DoWorkoutPresenterListeners,
        MultiplePermissionsListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    DoWorkoutPresenter presenter;
    private Workout workout;

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

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        getSupportFragmentManager().putFragment(outState, "mContent", getSupportFragmentManager().findFragmentById(R.id.container));
//    }

    public static void newInstance(Activity activity, Workout workout) {
        Intent intent = new Intent(activity, DoWorkoutActivity.class);
        intent.putExtra(Workout.WORKOUT_ARG, workout);
        activity.startActivity(intent);
    }

    @Override
    public void onSavedWorkout(Workout workout) {
        this.workout = workout;
        goToCurrentStep();
    }

    private void goToCurrentStep() {
        if (workout != null) {
            if (!workout.isRecoveryTime()) {
                navigateMainContent(FrgDoWorkout.newInstance(workout), getString(R.string.frg_do_workout_title));
            } else {
                navigateMainContent(FrgDoRestWorkout.newInstance(), getString(R.string.frg_do_rest_workout_title));
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
        presenter.finishWorkout();
    }

    @Override
    public void onErrorSavingWorkout() {

    }

    @Override
    public void onErrorFinishingWorkout() {

    }

    @Override
    public void onFinishWorkout() {
        finish();
    }

    @Override
    public void showProgressIndicator() {

    }

    @Override
    public void removeProgressIndicator() {

    }

    @Override
    public void showMessage(int stringId) {

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

    private void initWorkout() {
        presenter.saveWorkout((Workout) getIntent().getParcelableExtra(Workout.WORKOUT_ARG));
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
}
