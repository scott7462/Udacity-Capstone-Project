package scott.com.workhard.app.ui.create_workout;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import scott.com.workhard.R;
import scott.com.workhard.app.base.view.BaseFragment;
import scott.com.workhard.app.ui.create_workout.adapter.SimpleAdapterExercise;
import scott.com.workhard.models.Exercise;

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
public class FrgCreateWorkout extends BaseFragment {

    //    @BindView(R.id.tVFrgCreateWorkoutRounds)
//    TextView tVFrgCreateWorkoutRounds;
//    @BindView(R.id.tVFrgCreateWorkoutRestExercise)
//    TextView tVFrgCreateWorkoutRestExercise;
//    @BindView(R.id.tVFrgCreateWorkoutRestRounds)
//    TextView tVFrgCreateWorkoutRestRounds;
    @BindView(R.id.rVFrgCreateWorkOut)
    RecyclerView rVFrgCreateWorkOut;

    private SimpleAdapterExercise adapter = new SimpleAdapterExercise();
    private int rounds = 1;
    private int restBetweenExercises = 30;
    private int restBetweenRounds = 30;
    private Exercise exercise;


    public static Fragment newInstance() {
        return new FrgCreateWorkout();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVars();
    }


    private void initVars() {
        setHasOptionsMenu(true);
        adapter.setHeaderView(true);
        adapter.setEntryState(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_create_workout, container, false);
        ButterKnife.bind(this, view);
        intViews();
        return view;
    }

    private void intViews() {
        updateViewsTimes();
        rVFrgCreateWorkOut.setLayoutManager(new LinearLayoutManager(getActivity()));
        rVFrgCreateWorkOut.setAdapter(adapter);
    }

    private void updateViewsTimes() {
//        tVFrgCreateWorkoutRounds.setText(getString(R.string.frg_create_workout_rounds_number, rounds));
//        tVFrgCreateWorkoutRestExercise.setText(getString(R.string.frg_create_workout_rest_between_exercise_number, restBetweenExercises));
//        tVFrgCreateWorkoutRestRounds.setText(getString(R.string.frg_create_workout_rest_between_rounds_number, restBetweenRounds));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        presenter = new LoginPresenter();
//        presenter.attachView(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        presenter.detachView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                getActivity().onBackPressed();
            }
        }
        return super.onOptionsItemSelected(item);
    }

//    @OnClick({R.id.iBFrgCreateWorkoutRoundsMinus, R.id.iBFrgCreateWorkoutRoundsPlus})
//    public void onClickRounds(View view) {
//        switch (view.getId()) {
//            case R.id.iBFrgCreateWorkoutRoundsMinus:
//                if (rounds > getActivity().getResources().getInteger(R.integer.min_rounds)) {
//                    rounds--;
//                }
//                break;
//            case R.id.iBFrgCreateWorkoutRoundsPlus:
//                rounds++;
//                break;
//        }
//        updateViewsTimes();
//    }
//
//
//    @OnClick({R.id.iBFrgCreateWorkoutRestExerciseMinus, R.id.iBFrgCreateWorkoutRestExercisePlus})
//    public void onClickRestExercise(View view) {
//        switch (view.getId()) {
//            case R.id.iBFrgCreateWorkoutRestExerciseMinus:
//                if (restBetweenExercises > getActivity().getResources().getInteger(R.integer.min_rest_beteween_exercise)) {
//                    restBetweenExercises = restBetweenExercises - 5;
//                }
//                break;
//            case R.id.iBFrgCreateWorkoutRestExercisePlus:
//                restBetweenExercises = restBetweenExercises + 5;
//                break;
//        }
//        updateViewsTimes();
//    }
//
//    @OnClick({R.id.iBFrgCreateWorkoutRestRoundsMinus, R.id.iBFrgCreateWorkoutRestRoundsPlus})
//    public void onClickRestRounds(View view) {
//        switch (view.getId()) {
//            case R.id.iBFrgCreateWorkoutRestRoundsMinus:
//                if (restBetweenRounds > getActivity().getResources().getInteger(R.integer.min_rest_beteween_rounds)) {
//                    restBetweenRounds = restBetweenRounds - 5;
//                }
//                break;
//            case R.id.iBFrgCreateWorkoutRestRoundsPlus:
//                restBetweenRounds = restBetweenRounds + 5;
//                break;
//        }
//        updateViewsTimes();
//    }

    @OnClick(R.id.fBFrgCreateWork)
    public void onClick() {
        adapter.removeItemByPosition(2);
        adapter.removeItem(exercise);
    }
}
