package scott.com.workhard.app.ui.do_workout;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import scott.com.workhard.R;
import scott.com.workhard.base.view.BaseFragment;
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
public class FrgDoWorkout extends BaseFragment {

    @BindView(R.id.tVFrgDoWorkoutName)
    TextView tVFrgDoWorkoutName;
    @BindView(R.id.tVFrgDoWorkoutReps)
    TextView tVFrgDoWorkoutReps;
    @BindView(R.id.tVFrgDoWorkoutRounds)
    TextView tVFrgDoWorkoutRounds;
    @BindView(R.id.tVFrgDoWorkoutNextExercise)
    TextView tVFrgDoWorkoutNextExercise;
    @BindView(R.id.bTFrgDoWorkoutSkip)
    Button bTFrgDoWorkoutSkip;
    @BindView(R.id.bTFrgDoWorkoutNext)
    Button bTFrgDoWorkoutNext;
    private Workout workout;
    private Exercise exercise;

    public static FrgDoWorkout newInstance(@NonNull Workout workout) {
        Bundle args = new Bundle();
        args.putParcelable(Workout.WORKOUT_ARG, workout);
        FrgDoWorkout fragment = new FrgDoWorkout();
        fragment.setArguments(args);
        return fragment;
    }

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
        intViews();
        return view;
    }

    private void intViews() {
        tVFrgDoWorkoutName.setText(exercise.getName());
        tVFrgDoWorkoutNextExercise.setText(workout.findNextExercise() != null ? workout.findNextExercise().getName() : "None");
        tVFrgDoWorkoutReps.setText(getString(R.string.frg_do_workout_number_of_repetitions, exercise.getRepetition()));
        tVFrgDoWorkoutRounds.setText(getString(R.string.frg_do_workout_rounds_of_exercises, workout.getCurrentRound(), 5));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_do_workout, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_menu_finish:
                EventBus.getDefault().post(new EventAlterDialog().withMessage(getString(R.string.frg_do_workout_finish_alert))
                        .withPositveButton(getString(R.string.action_yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EventBus.getDefault().post(new EventFinishWorkout());
                            }
                        })
                        .withNegativeButton(getString(R.string.action_not), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }));

                break;
            case android.R.id.home: {
                getActivity().onBackPressed();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.bTFrgDoWorkoutNext)
    public void onClick() {
        EventBus.getDefault().post(new EventNextExercise());
    }
}
