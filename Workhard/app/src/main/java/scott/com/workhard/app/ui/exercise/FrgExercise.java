package scott.com.workhard.app.ui.exercise;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import scott.com.workhard.R;
import scott.com.workhard.base.view.BaseFragment;
import scott.com.workhard.entities.Exercise;
import scott.com.workhard.utils.IntentUtils;

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

public class FrgExercise extends BaseFragment {

    @BindView(R.id.tVFrgExerciseName)
    TextView tVFrgExerciseName;
    @BindView(R.id.tVFrgExerciseDetails)
    TextView tVFrgExerciseDetails;
    @BindView(R.id.tVFrgExerciseSource)
    TextView tVFrgExerciseSource;

    private Exercise exercise;

    public static Fragment newInstance(Exercise exercise) {
        FrgExercise frgExercise = new FrgExercise();
        Bundle args = new Bundle();
        args.putParcelable(Exercise.EXERCISE_ARG, exercise);
        frgExercise.setArguments(args);
        return frgExercise;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVars();
    }

    private void initVars() {
        setHasOptionsMenu(true);
        exercise = (Exercise) getArguments().getParcelable(Exercise.EXERCISE_ARG);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_exercise, container, false);
        ButterKnife.bind(this, view);
        intViews();
        return view;
    }

    private void intViews() {
        if (exercise != null) {
            tVFrgExerciseName.setText(exercise.getName());
            tVFrgExerciseDetails.setText(exercise.getDescription());
            if (exercise.getUrl() != null) {
                SpannableStringBuilder builder = new SpannableStringBuilder();
                SpannableString url = new SpannableString(exercise.getUrl().length() > 20 ?
                        exercise.getUrl().substring(0, 20).concat(getString(R.string.tree_points)) : exercise.getUrl());
                url.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getActivity(), R.color.primary)), 0, url.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                builder.append(getString(R.string.frg_exercise_source));
                builder.append(" ");
                builder.append(url);
                tVFrgExerciseSource.setText(builder);
                tVFrgExerciseSource.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        IntentUtils.openWebView(getActivity(), exercise.getUrl());
                    }
                });
            } else {
                tVFrgExerciseSource.setVisibility(View.GONE);
            }

        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        presenter = new CreateWorkoutPresenter();
//        presenter.attachView(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        presenter.detachView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_exercise, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                getActivity().onBackPressed();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }


}
