package scott.com.workhard.app.ui.create_workout.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import scott.com.workhard.R;
import scott.com.workhard.app.base.view.BaseSimpleAdapter;
import scott.com.workhard.models.Exercise;

/**
 * @author pedroscott. scott7462@gmail.com
 * @version 9/4/16.
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
public class SimpleAdapterExercise extends BaseSimpleAdapter<Exercise, RecyclerView.ViewHolder> {


    private onHeaderClickListener onHeaderClickListener;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case LOADING_VIEW: {
                return new EmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise_loading, parent, false));
            }
            case EMPTY_VIEW: {
                return new EmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise_empty, parent, false));
            }
            case HEADER_VIEW: {
                return new HeaderHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.frg_create_workout_header, parent, false));
            }
            default: {
                return new ExerciseHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise, parent, false));
            }
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case EMPTY_VIEW: {
                break;
            }
            case LOADING_VIEW: {
                break;
            }
            case HEADER_VIEW: {
                break;
            }
            default: {
                ((ExerciseHolder) holder).bindView(getItem(position));
            }
        }
    }

    class HeaderHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.eTFrgLoginEmail)
        EditText eTFrgLoginEmail;
        @BindView(R.id.tVFrgCreateWorkoutRounds)
        TextView tVFrgCreateWorkoutRounds;
        @BindView(R.id.tVFrgCreateWorkoutRestExercise)
        TextView tVFrgCreateWorkoutRestExercise;
        @BindView(R.id.tVFrgCreateWorkoutRestRounds)
        TextView tVFrgCreateWorkoutRestRounds;
        private int restBetweenExercises = 30;
        private int restBetweenRounds = 30;
        private int rounds = 1;

        @OnClick({R.id.iBFrgCreateWorkoutRoundsMinus, R.id.iBFrgCreateWorkoutRoundsPlus})
        public void onClickRounds(View view) {
            switch (view.getId()) {
                case R.id.iBFrgCreateWorkoutRoundsMinus:
                    if (rounds > view.getContext().getResources().getInteger(R.integer.min_rounds)) {
                        rounds--;
                    }
                    break;
                case R.id.iBFrgCreateWorkoutRoundsPlus:
                    rounds++;
                    break;
            }
            updateViewsTimes();
        }


        @OnClick({R.id.iBFrgCreateWorkoutRestExerciseMinus, R.id.iBFrgCreateWorkoutRestExercisePlus})
        public void onClickRestExercise(View view) {
            switch (view.getId()) {
                case R.id.iBFrgCreateWorkoutRestExerciseMinus:
                    if (restBetweenExercises > view.getContext().getResources().getInteger(R.integer.min_rest_beteween_exercise)) {
                        restBetweenExercises = restBetweenExercises - 5;
                    }
                    break;
                case R.id.iBFrgCreateWorkoutRestExercisePlus:
                    restBetweenExercises = restBetweenExercises + 5;
                    break;
            }
            updateViewsTimes();
        }

        @OnClick({R.id.iBFrgCreateWorkoutRestRoundsMinus, R.id.iBFrgCreateWorkoutRestRoundsPlus})
        public void onClickRestRounds(View view) {
            switch (view.getId()) {
                case R.id.iBFrgCreateWorkoutRestRoundsMinus:
                    if (restBetweenRounds > view.getContext().getResources().getInteger(R.integer.min_rest_beteween_rounds)) {
                        restBetweenRounds = restBetweenRounds - 5;
                    }
                    break;
                case R.id.iBFrgCreateWorkoutRestRoundsPlus:
                    restBetweenRounds = restBetweenRounds + 5;
                    break;
            }
            updateViewsTimes();
        }

        private void updateViewsTimes() {
            tVFrgCreateWorkoutRounds.setText(
                    itemView.getContext().getString(R.string.frg_create_workout_rounds_number,
                            rounds));
            tVFrgCreateWorkoutRestExercise.setText(
                    itemView.getContext().getString(R.string.frg_create_workout_rest_between_exercise_number,
                            restBetweenExercises));
            tVFrgCreateWorkoutRestRounds.setText(
                    itemView.getContext().getString(R.string.frg_create_workout_rest_between_rounds_number,
                            restBetweenRounds));

            if (getOnHeaderClickListener() != null) {
                getOnHeaderClickListener().onRoundChangeListener(rounds);
                getOnHeaderClickListener().onRestBetweenExercisesListener(restBetweenExercises);
                getOnHeaderClickListener().onRestBetweenRoundsListener(restBetweenRounds);
            }
        }

        HeaderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            eTFrgLoginEmail.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (getOnHeaderClickListener() != null) {
                        if (charSequence.length() >= 4) {
                            getOnHeaderClickListener().onNameWorkoutChange(charSequence.toString());
                        } else {

                            getOnHeaderClickListener().onNameWorkoutChange(
                                    eTFrgLoginEmail.getContext()
                                            .getString(R.string.frg_create_workoud_title));
                        }
                    }

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            updateViewsTimes();
        }


    }

    class ExerciseHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tVItemExerciseName)
        TextView tVItemExerciseName;
        @BindView(R.id.tVItemExerciseRepetitions)
        TextView tVItemExerciseRepetitions;

        ExerciseHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            if (getClickListener() != null) {
                initListeners();
            }
        }

        private void initListeners() {

        }

        public void bindView(Exercise exercise) {
            tVItemExerciseName.setText(exercise.getName());
            tVItemExerciseRepetitions.setText(tVItemExerciseRepetitions.getContext()
                    .getString(R.string.item_exercise, exercise.getRepetition()));
        }
    }

    public onHeaderClickListener getOnHeaderClickListener() {
        return onHeaderClickListener;
    }

    public void addHeaderClickListener(onHeaderClickListener onHeaderClickListener) {
        this.onHeaderClickListener = onHeaderClickListener;
    }

    public interface onHeaderClickListener {

        void onRoundChangeListener(int round);

        void onRestBetweenExercisesListener(int restBetweenExercises);

        void onRestBetweenRoundsListener(int restBetweenRounds);

        void onNameWorkoutChange(String name);
    }


}
