package scott.com.workhard.app.ui.home.adapter;

import android.support.annotation.IntDef;
import android.support.v7.widget.RecyclerView;
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
public class AdapterWorkout extends BaseFilterSimpleAdapter<Workout, RecyclerView.ViewHolder> {

    public static final int HOME = 1234;
    public static final int HISTORY = 4321;

    @typeToView
    private int typeView;

    @Override
    protected boolean searchCondition(Workout item, String query) {
        return false;
    }

    @Override
    protected boolean ifValidCondition(Workout workout) {
        return false;
    }

    /**
     * @hide
     */
    @IntDef({HOME, HISTORY})
    @Retention(RetentionPolicy.SOURCE)
    public @interface typeToView {
    }

    public AdapterWorkout(@typeToView int typeView) {
        this.typeView = typeView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()) {
            case EMPTY_VIEW: {
                ((EmptyViewHomeHolder) holder).bindView();
                break;
            }
            case LOADING_VIEW: {
                break;
            }
            default: {
                ((WorkoutHolder) holder).bindView(getItem(position));
            }
        }
    }

    class WorkoutHolder extends RecyclerView.ViewHolder {

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
            ButterKnife.bind(this,view);
            if (getClickListener() != null) {
                initListeners();
            }
        }

        private void initListeners() {

        }

        void bindView(Workout exercise) {
            tVItemWorkoutDate.setVisibility(typeView == HISTORY ? View.VISIBLE : View.GONE);
        }


    }

    public class EmptyViewHomeHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tVFrgHomeEmpty)
        TextView tVFrgHomeEmpty;

        EmptyViewHomeHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void initListeners() {

        }

        void bindView() {
            tVFrgHomeEmpty.setText(typeView == HOME ? tVFrgHomeEmpty.getContext().getString(R.string.frg_home_empty_home) :
                    tVFrgHomeEmpty.getContext().getString(R.string.frg_home_empty_history));
        }


    }


}


