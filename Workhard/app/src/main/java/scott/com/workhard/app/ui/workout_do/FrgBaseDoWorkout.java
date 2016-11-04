package scott.com.workhard.app.ui.workout_do;

import android.content.DialogInterface;
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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import scott.com.workhard.R;
import scott.com.workhard.base.view.BaseFragment;
import scott.com.workhard.bus.event.EventAddRoundToWorkout;
import scott.com.workhard.bus.event.EventAlterDialog;
import scott.com.workhard.bus.event.EventFinishWorkout;
import scott.com.workhard.bus.event.EventNextExercise;
import scott.com.workhard.entities.Exercise;
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
public abstract class FrgBaseDoWorkout extends BaseFragment {

    @BindView(R.id.tVFrgDoWorkoutName)
    TextView tVFrgDoWorkoutName;
    @BindView(R.id.tVFrgDoWorkoutReps)
    TextView tVFrgDoWorkoutReps;
    @BindView(R.id.tVFrgDoWorkoutRounds)
    TextView tVFrgDoWorkoutRounds;
    @BindView(R.id.bTFrgDoWorkoutNext)
    Button bTFrgDoWorkoutNext;
    @BindView(R.id.tVFrgDoWorkoutNextExerciseName)
    TextView tVFrgDoWorkoutNextExerciseName;
    @BindView(R.id.tVFrgDoWorkoutNextExerciseDescription)
    TextView tVFrgDoWorkoutNextExerciseDescription;
    @BindView(R.id.lLFrgDoWorkoutComplete)
    LinearLayout lLFrgDoWorkoutComplete;

    @BindView(R.id.lLFrgDoWorkoutFooter)
    LinearLayout lLFrgDoWorkoutFooter;
    private Workout workout;
    private Exercise exercise;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVars();
    }

    private void initVars() {
        setHasOptionsMenu(true);
        workout = (Workout) getArguments().getParcelable(Workout.WORKOUT_ARG);
        exercise = workout.findCurrentExercise();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_do_workout, container, false);
        ButterKnife.bind(this, view);
        initBaseViews();
        initViews();
        return view;
    }

    protected void initBaseViews() {
        tVFrgDoWorkoutName.setText(exercise.getName());
        loadNextExercise();
        tVFrgDoWorkoutReps.setText(getString(R.string.frg_do_workout_number_of_repetitions, exercise.getRepetitions()));
        tVFrgDoWorkoutRounds.setText(getString(R.string.frg_do_workout_rounds_of_exercises, workout.getCurrentRound(), workout.getRounds()));
    }

    private void loadNextExercise() {
        if (workout.isTheLastExercise()) {
            lLFrgDoWorkoutFooter.setVisibility(View.GONE);
            lLFrgDoWorkoutComplete.setVisibility(View.VISIBLE);
        } else {
            lLFrgDoWorkoutFooter.setVisibility(View.VISIBLE);
            tVFrgDoWorkoutNextExerciseDescription.setMovementMethod(new ScrollingMovementMethod());
            if (workout.findNextExercise() != null) {
                tVFrgDoWorkoutNextExerciseName.setText(getString(R.string.frg_do_workout_next_exercise_name,
                        workout.findNextExercise().getName()));
                tVFrgDoWorkoutNextExerciseDescription.setText(
                        Html.fromHtml(getString(R.string.frg_do_workout_next_exercise_description,
                                workout.findNextExercise().getDescription())));
            } else {
                tVFrgDoWorkoutNextExerciseName.setText(getString(R.string.frg_do_workout_next_exercise_name,
                        workout.getExerciseList().get(0).getName()));
                tVFrgDoWorkoutNextExerciseDescription.setText(
                        Html.fromHtml(getString(R.string.frg_do_workout_next_exercise_description,
                                workout.getExerciseList().get(0).getDescription())));
            }

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
                finishWorkout(false);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void finishWorkout(boolean isCompleted) {
        EventBus.getDefault().post(new EventAlterDialog().withMessage(!isCompleted ?
                getString(R.string.frg_do_workout_finish_alert) : getString(R.string.frg_do_workout_complete_alert))
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

    @OnClick(R.id.bTFrgDoWorkoutNext)
    public void onClick() {
        EventBus.getDefault().post(new EventNextExercise());
    }

    @OnClick(R.id.tVFrgDoWorkoutFinishWorkout)
    public void onClickFinish() {
        finishWorkout(true);
    }

    @OnClick({R.id.tVFrgDoWorkoutDoOther, R.id.bTFrgDoWorkoutComplete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tVFrgDoWorkoutDoOther:

                EventBus.getDefault().post(new EventAlterDialog().withMessage(getString(R.string.frg_do_workout_other_round_alert))
                        .withPositveButton(getString(R.string.action_yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EventBus.getDefault().post(new EventAddRoundToWorkout());
                                dialog.dismiss();
                            }
                        })
                        .withNegativeButton(getString(R.string.action_not), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }));
                break;
            case R.id.bTFrgDoWorkoutComplete:
                finishWorkout(true);
                break;
        }
    }

    abstract protected void initViews();

}
