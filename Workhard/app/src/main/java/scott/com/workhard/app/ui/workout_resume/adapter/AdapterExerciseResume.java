package scott.com.workhard.app.ui.workout_resume.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import scott.com.workhard.R;
import scott.com.workhard.base.view.BaseSimpleAdapter;
import scott.com.workhard.entities.Exercise;
import scott.com.workhard.entities.Workout;

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
public class AdapterExerciseResume extends BaseSimpleAdapter<Exercise, RecyclerView.ViewHolder> {

    private Workout workout = new Workout();

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(@NonNull Workout workout) {
        this.workout = workout;
        if (workout.getExerciseList() != null) {
            cleanItemsAndUpdate(workout.getExerciseList());
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ExerciseHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise_resume, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ExerciseHolder) holder).bindView(getItem(position));
    }

    public class ExerciseHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tVItemExerciseName)
        TextView tVItemExerciseName;
        @BindView(R.id.tVItemExerciseRepetitions)
        TextView tVItemExerciseRepetitions;

        ExerciseHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            initListeners();
        }

        private void initListeners() {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callItemListenerByPosition(getAdapterPosition());
                }
            });
        }

        void bindView(Exercise exercise) {
            tVItemExerciseName.setText(exercise.getName());
            tVItemExerciseRepetitions.setText(tVItemExerciseRepetitions.getContext()
                    .getString(R.string.frg_create_workout_item_exercise, exercise.getRepetition() *
                            (getAdapterPosition() <= workout.getCurrentExercisePosition() ?
                                    workout.getCurrentRound() : workout.getCurrentRound() - 1)));
        }
    }

    @Override
    protected boolean ifValidCondition(Exercise exercise) {
        return exercise.isChecked();
    }

}


