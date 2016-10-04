package scott.com.workhard.app.ui.create_workout.adapter;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import scott.com.workhard.R;
import scott.com.workhard.app.App;
import scott.com.workhard.base.view.BaseFilterSimpleAdapter;
import scott.com.workhard.bus.event.EventSnackBar;
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
public class AdapterExercise extends BaseFilterSimpleAdapter<Exercise, RecyclerView.ViewHolder> {

    private onHeaderClickListener onHeaderClickListener;
    private onExerciseClickListener onExerciseClickListener;
    public static final int ADD_TO_WORKOUT = 1234;
    public static final int SHOW_IN_WORKOUT = 4321;

    @typeToView
    private int typeView;

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

    /**
     * @hide
     */
    @IntDef({ADD_TO_WORKOUT, SHOW_IN_WORKOUT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface typeToView {
    }

    public AdapterExercise(@typeToView int typeView) {
        this.typeView = typeView;
        workout.setRestBetweenExercise(30);
        workout.setRounds(1);
        workout.setRestRoundsExercise(30);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case LOADING_VIEW: {
                return new EmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise_loading, parent, false));
            }
            case EMPTY_VIEW: {
                return new EmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_state, parent, false));
            }
            case HEADER_VIEW: {
                return new HeaderHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.frg_create_workout_header, parent, false));
            }
            case LOAD_MORE_VIEW: {
                return new EmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_load_more, parent, false));
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
            case LOAD_MORE_VIEW: {
                break;
            }
            case HEADER_VIEW: {
                ((HeaderHolder) holder).bindView();
                break;
            }
            default: {
                ((ExerciseHolder) holder).bindView(getItem(position));
            }
        }
    }

    class HeaderHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.eTFrgCreateWorkoutName)
        EditText eTFrgCreateWorkoutName;
        @BindView(R.id.tVFrgCreateWorkoutRounds)
        TextView tVFrgCreateWorkoutRounds;
        @BindView(R.id.tVFrgCreateWorkoutRestExercise)
        TextView tVFrgCreateWorkoutRestExercise;
        @BindView(R.id.tVFrgCreateWorkoutRestRounds)
        TextView tVFrgCreateWorkoutRestRounds;

        @OnClick({R.id.iBFrgCreateWorkoutRoundsMinus, R.id.iBFrgCreateWorkoutRoundsPlus})
        public void onClickRounds(View view) {
            switch (view.getId()) {
                case R.id.iBFrgCreateWorkoutRoundsMinus:
                    if (workout.getRounds() > view.getContext().getResources().getInteger(R.integer.min_rounds)) {
                        workout.setRounds(workout.getRounds() - view.getContext().getResources().getInteger(R.integer.min_rounds));
                    }
                    break;
                case R.id.iBFrgCreateWorkoutRoundsPlus:
                    workout.setRounds(workout.getRounds() + view.getContext().getResources().getInteger(R.integer.min_rounds));
                    break;
            }
            updateViewsTimes();
        }


        @OnClick({R.id.iBFrgCreateWorkoutRestExerciseMinus, R.id.iBFrgCreateWorkoutRestExercisePlus})
        public void onClickRestExercise(View view) {
            switch (view.getId()) {
                case R.id.iBFrgCreateWorkoutRestExerciseMinus:
                    if (workout.getRestBetweenExercise() > view.getContext().getResources()
                            .getInteger(R.integer.min_rest_between_exercise)) {
                        workout.setRestBetweenExercise(workout.getRestBetweenExercise()
                                - view.getContext().getResources().getInteger(R.integer.number_of_rest));
                    }
                    break;
                case R.id.iBFrgCreateWorkoutRestExercisePlus:
                    workout.setRestBetweenExercise(workout.getRestBetweenExercise()
                            + view.getContext().getResources().getInteger(R.integer.number_of_rest));
                    break;
            }
            updateViewsTimes();
        }

        @OnClick({R.id.iBFrgCreateWorkoutRestRoundsMinus, R.id.iBFrgCreateWorkoutRestRoundsPlus})
        public void onClickRestRounds(View view) {
            switch (view.getId()) {
                case R.id.iBFrgCreateWorkoutRestRoundsMinus:
                    if (workout.getRestRoundsExercise() > view.getContext().getResources().getInteger(R.integer.min_rest_between_rounds)) {
                        workout.setRestRoundsExercise(workout.getRestRoundsExercise() - view.getContext().getResources().getInteger(R.integer.number_of_rest));
                    }
                    break;

                case R.id.iBFrgCreateWorkoutRestRoundsPlus:
                    workout.setRestRoundsExercise(workout.getRestRoundsExercise() +
                            view.getContext().getResources().getInteger(R.integer.number_of_rest));
                    break;
            }
            updateViewsTimes();
        }

        private void updateViewsTimes() {
            tVFrgCreateWorkoutRounds.setText(
                    itemView.getContext().getString(R.string.frg_create_workout_rounds_number,
                            workout.getRounds()));
            tVFrgCreateWorkoutRestExercise.setText(
                    itemView.getContext().getString(R.string.frg_create_workout_rest_between_exercise_number,
                            workout.getRestBetweenExercise()));
            tVFrgCreateWorkoutRestRounds.setText(
                    itemView.getContext().getString(R.string.frg_create_workout_rest_between_rounds_number,
                            workout.getRestRoundsExercise()));
        }

        HeaderHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            eTFrgCreateWorkoutName.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    workout.setName(charSequence.toString());
                    if (getOnHeaderClickListener() != null) {
                        if (charSequence.length() >= 4) {
                            getOnHeaderClickListener().onNameWorkoutChange(charSequence.toString());
                        } else {
                            getOnHeaderClickListener().onNameWorkoutChange(
                                    eTFrgCreateWorkoutName.getContext()
                                            .getString(R.string.frg_create_workout_title));
                        }
                    }

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            updateViewsTimes();
        }

        public void bindView() {
            eTFrgCreateWorkoutName.setText(workout.getName());
            updateViewsTimes();
        }
    }

    class ExerciseHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iVItemExerciseMove)
        ImageView iVItemExerciseMove;
        @BindView(R.id.tVItemExerciseName)
        TextView tVItemExerciseName;
        @BindView(R.id.tVItemExerciseRepetitions)
        TextView tVItemExerciseRepetitions;
        @BindView(R.id.cBItemExercise)
        CheckBox cBCreateExercise;
        @BindView(R.id.iBCreateExerciseDelete)
        ImageView iBCreateExerciseDelete;
        @BindView(R.id.lLItemExerciseControllerRepetitions)
        LinearLayout lLItemExerciseControllerRepetitions;

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
                    .getString(R.string.frg_create_workout_item_exercise, exercise.getRepetition()));
            cBCreateExercise.setVisibility(typeView == ADD_TO_WORKOUT ? View.VISIBLE : View.GONE);
            lLItemExerciseControllerRepetitions.setVisibility(typeView == SHOW_IN_WORKOUT ? View.VISIBLE : View.GONE);
            cBCreateExercise.setChecked(exercise.isChecked());
            tVItemExerciseRepetitions.setVisibility(typeView == SHOW_IN_WORKOUT ? View.VISIBLE : View.GONE);
            iBCreateExerciseDelete.setVisibility(typeView == SHOW_IN_WORKOUT ? View.VISIBLE : View.GONE);
            iVItemExerciseMove.setVisibility(isCallbackMoved() ? View.VISIBLE : View.GONE);
        }

        @OnClick({R.id.iBFItemExerciseMinusRepetitions, R.id.iBFItemExercisePluseRepetitions})
        public void onClickRounds(View view) {
            switch (view.getId()) {
                case R.id.iBFItemExerciseMinusRepetitions:
                    if (getItems().get(getItemPosition(getAdapterPosition())).getRepetition() > view.getContext().getResources().getInteger(R.integer.min_rounds)) {
                        getItems().get(getItemPosition(getAdapterPosition()))
                                .setRepetition(getItems().get(getItemPosition(getAdapterPosition())).getRepetition()
                                        - view.getContext().getResources().getInteger(R.integer.min_rounds));
                    }
                    break;
                case R.id.iBFItemExercisePluseRepetitions:
                    getItems().get(getItemPosition(getAdapterPosition()))
                            .setRepetition(getItems().get(getItemPosition(getAdapterPosition())).getRepetition()
                                    + view.getContext().getResources().getInteger(R.integer.min_rounds));
                    break;
            }
            tVItemExerciseRepetitions.setText(tVItemExerciseRepetitions.getContext()
                    .getString(R.string.frg_create_workout_item_exercise, getItems()
                            .get(getItemPosition(getAdapterPosition())).getRepetition()));
        }

        @OnClick(R.id.iBCreateExerciseDelete)
        public void onDeleteItem() {
            removeItemByPosition(getItemPosition(getAdapterPosition()));
        }


        @OnCheckedChanged(R.id.cBItemExercise)
        public void checkExercise(boolean check) {
            getItems().get(getItemPosition(getAdapterPosition())).setChecked(check);
            if (getOnExerciseClickListener() != null) {
                getOnExerciseClickListener().onNumberOfSelectExercisesListener(getCountSelectedItems());
            }
        }
    }

    private int getCountSelectedItems() {
        int count = 0;
        for (Exercise exercise : getItems()) {
            count = count + (exercise.isChecked() ? 1 : 0);
        }
        return count;
    }

    onHeaderClickListener getOnHeaderClickListener() {
        return onHeaderClickListener;
    }

    public void addHeaderClickListener(onHeaderClickListener onHeaderClickListener) {
        this.onHeaderClickListener = onHeaderClickListener;
    }

    public interface onHeaderClickListener {
        void onNameWorkoutChange(String name);
    }

    public interface onExerciseClickListener {
        void onNumberOfSelectExercisesListener(int numberSelectedItems);
    }

    public AdapterExercise.onExerciseClickListener getOnExerciseClickListener() {
        return onExerciseClickListener;
    }

    public void addOnExerciseClickListener(AdapterExercise.onExerciseClickListener onExerciseClickListener) {
        this.onExerciseClickListener = onExerciseClickListener;
    }

    @Override
    protected boolean searchCondition(Exercise item, String query) {
        return item.getName().toLowerCase().contains(query.toLowerCase());
    }

    @Override
    protected boolean ifValidCondition(Exercise exercise) {
        return exercise.isChecked();
    }

    public boolean validateHeader() {
        if (workout.getName().length() < 4) {
            EventBus.getDefault().post(new EventSnackBar().withMessage(App.getGlobalContext().getString(R.string.frg_create_workout_name_nim)));
            return false;
        }
        return true;
    }

}


