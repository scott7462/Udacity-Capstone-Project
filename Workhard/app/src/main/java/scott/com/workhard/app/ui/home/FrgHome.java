package scott.com.workhard.app.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import scott.com.workhard.R;
import scott.com.workhard.app.ui.workout_create.ActivityCreateWorkout;
import scott.com.workhard.app.ui.workout_do.ActivityDoWorkout;
import scott.com.workhard.app.ui.home.adapter.AdapterWorkout;
import scott.com.workhard.app.ui.workout_resume.FrgWorkoutResume;
import scott.com.workhard.app.ui.workout_resume.ActivityWorkoutResume;
import scott.com.workhard.base.view.BaseFragment;
import scott.com.workhard.base.view.BaseSimpleAdapter;
import scott.com.workhard.entities.Exercise;
import scott.com.workhard.entities.Workout;
import scott.com.workhard.utils.SpacesItemDecoration;

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
public class FrgHome extends BaseFragment {

    private static final String TYPE_VIEW_ADAPTER = "type_view_adapter";
    @BindView(R.id.rVFrgHome)
    RecyclerView rVFrgHome;
    @BindView(R.id.fBHomeAddWorkout)
    FloatingActionButton fBHomeAddWorkout;

    private List<Workout> workouts = new ArrayList<>();
    private AdapterWorkout adapter;

    public static final int HOME = 1234;
    public static final int HISTORY = 4321;
    public static final int MY_WORKOUTS = 1987;

    @IntDef({HOME, HISTORY, MY_WORKOUTS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface typeToView {
    }


    public static Fragment newInstance(@typeToView int typeView) {
        FrgHome frgHome = new FrgHome();
        Bundle arg = new Bundle();
        arg.putInt(TYPE_VIEW_ADAPTER, typeView);
        frgHome.setArguments(arg);
        return frgHome;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVars();
    }

    private void initVars() {
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            switch (getArguments().getInt(TYPE_VIEW_ADAPTER)) {
                case HISTORY: {
                    adapter = new AdapterWorkout(AdapterWorkout.HISTORY);
                    break;
                }
                case MY_WORKOUTS: {
                    adapter = new AdapterWorkout(AdapterWorkout.MY_WORKOUTS);
                    break;
                }
                default: {
                    adapter = new AdapterWorkout(AdapterWorkout.HOME);
                    break;
                }
            }
        } else {
            adapter = new AdapterWorkout(AdapterWorkout.HOME);
        }

        adapter.addOnClickListener(new BaseSimpleAdapter.onItemClickListener<Workout>() {
            @Override
            public void onItemViewsClick(Workout item, int position) {
                switch (getArguments().getInt(TYPE_VIEW_ADAPTER)) {
                    case HISTORY: {
                        ActivityWorkoutResume.newInstance(getActivity(), item, FrgWorkoutResume.RESUME);
                        break;
                    }
                    default: {
                        ActivityDoWorkout.newInstance(getActivity(), ActivityDoWorkout.NEW_WORKOUT ,item);
                        break;
                    }
                }
            }
        });
        adapter.showEmptyState(true);


        List<Exercise> exercises = new ArrayList<>();
        exercises.add(new Exercise().withName("1")
                .withRepetitions(60)
                .withId("1")
                .withDescription(getString(R.string.text_exercise))
                .withUrl("https://en.wikipedia.org/wiki/Push-up"));
        exercises.add(new Exercise().withName("2")
                .withRepetitions(20)
                .withId("2")
                .withDescription(getString(R.string.text_exercise))
                .withUrl("https://en.wikipedia.org/wiki/Push-up"));

        exercises.add(new Exercise().withName("3")
                .withRepetitions(20)
                .withId("3")
                .withDescription(getString(R.string.text_exercise))
                .withUrl("https://en.wikipedia.org/wiki/Push-up"));

        exercises.add(new Exercise().withName("4")
                .withRepetitions(20)
                .withId("4")
                .withDescription(getString(R.string.text_exercise))
                .withUrl("https://en.wikipedia.org/wiki/Push-up"));

        exercises.add(new Exercise().withName("5")
                .withRepetitions(20)
                .withId("5")
                .withDescription(getString(R.string.text_exercise))
                .withUrl("https://en.wikipedia.org/wiki/Push-up"));

        exercises.add(new Exercise().withName("6")
                .withRepetitions(20)
                .withId("6")
                .withDescription(getString(R.string.text_exercise))
                .withUrl("https://en.wikipedia.org/wiki/Push-up"));

        exercises.add(new Exercise().withName("7")
                .withRepetitions(20)
                .withId("7")
                .withDescription(getString(R.string.text_exercise))
                .withUrl("https://en.wikipedia.org/wiki/Push-up"));
        workouts.add(new Workout()
                .withId("342")
                .withName("Test Name")
                .withRestBetweenExercise(10)
                .withRestRoundsExercise(15)
                .withRounds(2)
                .withDateCompleted("07 Nov 2016")
                .withExercises(exercises));
        workouts.add(new Workout());
        workouts.add(new Workout());
        workouts.add(new Workout());
        workouts.add(new Workout());
        workouts.add(new Workout());
        workouts.add(new Workout());
        adapter.addItems(workouts);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_home, container, false);
        ButterKnife.bind(this, view);
        intViews();
        return view;
    }

    private void intViews() {
        rVFrgHome.setLayoutManager(new LinearLayoutManager(getActivity()));
        rVFrgHome.addItemDecoration(
                new SpacesItemDecoration(adapter.haveAdapterHeaderView(), R.dimen.default_medium_size));
        rVFrgHome.setAdapter(adapter);
        rVFrgHome.setHasFixedSize(true);
        rVFrgHome.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || dy < 0 && fBHomeAddWorkout.isShown())
                    fBHomeAddWorkout.hide();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    fBHomeAddWorkout.show();
                }
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }

    @OnClick(R.id.fBHomeAddWorkout)
    public void createWorkout() {
        ActivityCreateWorkout.newInstance(getActivity(), null);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        presenter = new DoWorkoutPresenter();
//        presenter.attachView(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        presenter.detachView();
    }



}
