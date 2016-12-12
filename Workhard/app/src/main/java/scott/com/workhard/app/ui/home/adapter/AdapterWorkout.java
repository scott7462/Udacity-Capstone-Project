package scott.com.workhard.app.ui.home.adapter;

import android.support.annotation.IntDef;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import butterknife.BindView;
import butterknife.ButterKnife;
import scott.com.workhard.R;
import scott.com.workhard.base.view.BaseFilterSimpleAdapter;
import scott.com.workhard.base.view.BaseSimpleAdapter;
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
public class AdapterWorkout extends BaseFilterSimpleAdapter<Workout,BaseSimpleAdapter.BaseViewHolder<Workout>> {

    public static final int HOME = 1234;
    public static final int HISTORY = 4321;
    public static final int MY_WORKOUTS = 9453;

    @typeToView
    private int typeView;

    public int getTypeView() {
        return typeView;
    }

    @Override
    protected boolean searchCondition(Workout item, String query) {
        return false;
    }

    @IntDef({HOME, HISTORY, MY_WORKOUTS})
    @Retention(RetentionPolicy.SOURCE)
    public @interface typeToView {
    }

    public AdapterWorkout(@typeToView int typeView) {
        this.typeView = typeView;
    }

    @Override
    public BaseViewHolder<Workout> onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case LOADING_VIEW: {
                return new EmptyViewHomeHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise_loading, parent, false));
            }
            case EMPTY_VIEW: {
                return new EmptyViewHomeHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.frg_empty_view, parent, false));
            }
            default: {
                return new WorkoutHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_workout, parent, false));
            }
        }
    }

    class WorkoutHolder extends BaseViewHolder<Workout> implements View.OnClickListener {

        @BindView(R.id.tVItemWorkoutName)
        TextView tVItemWorkoutName;
        @BindView(R.id.tVItemWorkoutRounds)
        TextView tVItemWorkoutRounds;
        @BindView(R.id.tVItemWorkoutRestExercises)
        TextView tVItemWorkoutRestExercises;
        @BindView(R.id.tVItemWorkoutRestRounds)
        TextView tVItemWorkoutRestRounds;
        @BindView(R.id.tVItemWorkoutDate)
        TextView tVItemWorkoutDate;

        WorkoutHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            initListeners();
        }

        @Override
        protected void bindView(Workout workout) {
            tVItemWorkoutName.setText(workout.getName());
            tVItemWorkoutRounds.setText(tVItemWorkoutRounds.getContext()
                    .getString(R.string.frg_item_workout_rounds_number, workout.getRounds()));
            tVItemWorkoutRestExercises.setText(tVItemWorkoutRestExercises.getContext()
                    .getString(R.string.frg_create_item_rest_between_exercise_number, workout.getRestBetweenExercise()));
            tVItemWorkoutRestRounds.setText(tVItemWorkoutRestRounds.getContext()
                    .getString(R.string.frg_create_item_rest_between_rounds_number, workout.getRestRoundsExercise()));
            tVItemWorkoutDate.setVisibility(typeView == HISTORY ? View.VISIBLE : View.GONE);
//            tVItemWorkoutDate.setText(workout.getDateCompleted());
        }

        private void initListeners() {
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            callItemListenerByPosition(getAdapterPosition());
        }
    }

    public class EmptyViewHomeHolder extends EmptyViewHolder<Workout> {

        @BindView(R.id.tVFrgHomeEmpty)
        TextView tVFrgHomeEmpty;

        EmptyViewHomeHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        protected void bindView() {
            super.bindView();
            switch (typeView) {
                case HOME:
                    tVFrgHomeEmpty.setText(tVFrgHomeEmpty.getContext().getString(R.string.frg_home_empty_home));
                    break;
                case HISTORY:
                    tVFrgHomeEmpty.setText(tVFrgHomeEmpty.getContext().getString(R.string.frg_home_empty_history));
                    break;
                case MY_WORKOUTS:
                    tVFrgHomeEmpty.setText(tVFrgHomeEmpty.getContext().getString(R.string.frg_home_empty_my_workouts));
                    break;
            }
        }
    }


}


