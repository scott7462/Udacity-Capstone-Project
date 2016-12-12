package scott.com.workhard.app.ui.workout_do;

import android.content.DialogInterface;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import scott.com.workhard.R;
import scott.com.workhard.base.view.BaseFragment;
import scott.com.workhard.bus.event.EventAlterDialog;
import scott.com.workhard.bus.event.EventFinishRecoveryTime;
import scott.com.workhard.bus.event.EventFinishWorkout;
import scott.com.workhard.bus.event.EventSnackBar;
import scott.com.workhard.entities.Workout;

/**
 * @author pedroscott. scott7462@gmail.com
 * @version 9/3/16.
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
public abstract class FrgBaseDoRestWorkout extends BaseFragment {

    protected static final String TIMER = "timer_to_rest";
    @BindView(R.id.tVFrgDoWorkoutTime)
    TextView tVFrgDoWorkoutTime;
    @BindView(R.id.tVFrgDoWorkoutNextExerciseName)
    TextView tVFrgDoWorkoutNextExerciseName;
    @BindView(R.id.tVFrgDoWorkoutNextExerciseDescription)
    TextView tVFrgDoWorkoutNextExerciseDescription;
    private int timer = 0;
    private int currentTimer = 0;
    private Workout workout;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVars();
    }

    private void initVars() {
        setHasOptionsMenu(true);
        timer = getArguments().getInt(TIMER);
        workout = (Workout) getArguments().getParcelable(Workout.WORKOUT_ARG);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_do_rest_workout, container, false);
        ButterKnife.bind(this, view);
        initViews();
        loadNextExercise();
        initTimer();
        return view;
    }

    private void initTimer() {
        addSubscription(Observable.interval(1, TimeUnit.SECONDS)
                .take(timer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {

                    @Override
                    public void onStart() {
                        super.onStart();
                        tVFrgDoWorkoutTime.setText(Html.fromHtml(getString(R.string.frg_do_workout_sec_in_clock, timer - currentTimer)));
                    }

                    @Override
                    public void onCompleted() {
                        EventBus.getDefault().post(new EventFinishRecoveryTime());
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Long aLong) {
                        updateProgress(aLong.intValue());
                    }
                }));
    }

    private void updateProgress(int seg) {
        currentTimer = seg;
        tVFrgDoWorkoutTime.setText(Html.fromHtml(getString(R.string.frg_do_workout_sec_in_clock, timer - currentTimer)));
        if ((timer - currentTimer) == 0) {
            EventBus.getDefault().post(new EventFinishRecoveryTime());
        }
        if (timer - currentTimer <= 5) {
            beap();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_do_workout, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_menu_finish:
                finishWorkout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void finishWorkout() {
        EventBus.getDefault().post(new EventAlterDialog().withMessage(getString(R.string.frg_do_workout_finish_alert))
                .withPositveButton(getString(R.string.action_yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EventBus.getDefault().post(new EventFinishWorkout());
                        dialog.dismiss();
                    }
                })
                .withNegativeButton(getString(R.string.action_not), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }));
    }

    @OnClick(R.id.tVFrgDoWorkoutTime)
    public void plusSegToTimer() {
        EventBus.getDefault().post(new EventSnackBar().withMessage(getString(R.string.frg_do_workout_add_ten_sec)));
        timer = timer - currentTimer + 10;
        tVFrgDoWorkoutTime.setText(Html.fromHtml(getString(R.string.frg_do_workout_sec_in_clock, timer)));
        getSubscription().clear();
        initTimer();
    }

    @OnClick(R.id.bTFrgDoWorkoutNext)
    public void onClick() {
        EventBus.getDefault().post(new EventFinishRecoveryTime());
    }

    public void beap() {
        ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
        toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
        toneG.release();
    }

    private void loadNextExercise() {
        tVFrgDoWorkoutNextExerciseDescription.setMovementMethod(new ScrollingMovementMethod());
        tVFrgDoWorkoutNextExerciseName.setText(getString(R.string.frg_do_workout_next_exercise_name,
                workout.findCurrentExercise() != null ? workout.findCurrentExercise().getName() : "None"));
        tVFrgDoWorkoutNextExerciseDescription.setText(
                Html.fromHtml(getString(R.string.frg_do_workout_next_exercise_description,
                        workout.findCurrentExercise() != null ? workout.findCurrentExercise().getDescription() : "None")));
    }

    @OnClick(R.id.tVFrgDoWorkoutFinishWorkout)
    public void onClickFinish() {
        finishWorkout();
    }

    abstract protected void initViews();

}
