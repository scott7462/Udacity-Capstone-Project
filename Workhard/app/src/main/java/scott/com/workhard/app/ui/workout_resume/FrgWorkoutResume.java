package scott.com.workhard.app.ui.workout_resume;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import butterknife.BindView;
import butterknife.ButterKnife;
import scott.com.workhard.R;
import scott.com.workhard.app.ui.workout_create.ActivityCreateWorkout;
import scott.com.workhard.app.ui.workout_resume.adapter.AdapterExerciseResume;
import scott.com.workhard.base.view.BaseActivity;
import scott.com.workhard.base.view.BaseFragment;
import scott.com.workhard.entities.Workout;

/**
 * @author pedroscott. scott7462@gmail.com
 * @version 9/28/16.
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
public class FrgWorkoutResume extends BaseFragment {

    @BindView(R.id.tVFrgWorkoutName)
    TextView tVFrgWorkoutName;
    @BindView(R.id.tVFrgWorkoutRounds)
    TextView tVFrgWorkoutRounds;
    @BindView(R.id.rVFrgWorkoutResume)
    RecyclerView rVFrgWorkoutResume;
    @BindView(R.id.tVFrgWorkoutRestRounds)
    TextView tVFrgWorkoutRestRounds;
    @BindView(R.id.tVFrgWorkoutRestExercise)
    TextView tVFrgWorkoutRestExercise;
    @BindView(R.id.tVFrgWorkoutDate)
    TextView tVFrgWorkoutDate;

    private AdapterExerciseResume adapter;

    public static final int FINISH = 1234;
    public static final int RESUME = 4321;
    public static final String VIEW_TYPE_ARG = "view_type";

    @IntDef({FINISH, RESUME})
    @Retention(RetentionPolicy.SOURCE)
    public @interface typeToView {
    }

    @typeToView
    private int viewType;

    private Workout workout;

    public static FrgWorkoutResume newInstance(Workout workout, @typeToView int viewType) {
        Bundle args = new Bundle();
        args.putParcelable(Workout.WORKOUT_ARG, workout);
        args.putInt(VIEW_TYPE_ARG, viewType);
        FrgWorkoutResume fragment = new FrgWorkoutResume();
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
        adapter = new AdapterExerciseResume();
        adapter.setWorkout(workout);
        switch (getArguments().getInt(VIEW_TYPE_ARG)) {
            case FINISH:
                viewType = FINISH;
                break;
            case RESUME:
                viewType = RESUME;
                break;
        }
        if (viewType == FINISH) {
            ((BaseActivity) getActivity()).getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);
            ((BaseActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_workout, container, false);
        ButterKnife.bind(this, view);
        intViews();
        return view;
    }

    private void intViews() {
        rVFrgWorkoutResume.setLayoutManager(new LinearLayoutManager(getActivity()));
        rVFrgWorkoutResume.setAdapter(adapter);
        rVFrgWorkoutResume.setHasFixedSize(true);
        tVFrgWorkoutDate.setVisibility(viewType == FINISH ? View.GONE : View.VISIBLE);
        if (workout != null) {
            tVFrgWorkoutName.setText(workout.getName());
            tVFrgWorkoutRounds.setText(getString(R.string.frg_do_workout_rounds_of_exercises, workout.getCurrentRound(), workout.getRounds()));
            tVFrgWorkoutRestExercise.setText(getString(R.string.frg_workout_rest_between_exercise, workout.getRestBetweenExercise()));
            tVFrgWorkoutRestRounds.setText(getString(R.string.frg_workout_rest_rounds, workout.getRestRoundsExercise()));
//            tVFrgWorkoutDate.setText(getString(R.string.frg_workout_dates, workout.getDateCompleted()));
        }
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
        switch (viewType) {
            case FINISH:
                inflater.inflate(R.menu.menu_workout_finish, menu);
                break;
            case RESUME:
                inflater.inflate(R.menu.menu_workout_resume, menu);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
            case R.id.item_menu_finish:
                getActivity().finish();
                break;
            case R.id.item_menu_do_it_again:
                ActivityCreateWorkout.newInstance(getActivity(), workout);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
