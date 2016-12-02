package scott.com.workhard.app.ui.workout_create.adapter;

import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.Html;
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
import org.greenrobot.eventbus.Subscribe;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import scott.com.workhard.R;
import scott.com.workhard.app.App;
import scott.com.workhard.base.view.BaseFilterSimpleAdapter;
import scott.com.workhard.base.view.BaseSimpleAdapter;
import scott.com.workhard.bus.event.EventErrorName;
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
public class AdapterExercise extends BaseFilterSimpleAdapter<Exercise, BaseSimpleAdapter.BaseViewHolder<Exercise>> {

    private onHeaderClickListener onHeaderClickListener;
    private onExerciseClickListener onExerciseClickListener;
    public static final int ADD_TO_WORKOUT = 1234;
    public static final int SHOW_IN_WORKOUT = 4321;

    @typeToView
    private int typeView;

    private Workout workout = new Workout();

    public Workout getWorkout() {
        return workout.withExercises(getItems());
    }

    public void setWorkout(@Nullable Workout workout) {
        if (workout != null) {
            this.workout = workout;
            if (workout.getExerciseList() != null) {
                cleanItemsAndUpdate(workout.getExerciseList());
            }
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
    public BaseViewHolder<Exercise> onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case LOADING_VIEW:
                return new EmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise_loading, parent, false));
            case EMPTY_VIEW:
                return new ExerciseEmptyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty_state, parent, false));
            case HEADER_VIEW:
                return new HeaderHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.frg_create_workout_header, parent, false));
            case LOAD_MORE_VIEW:
                return new EmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_load_more, parent, false));
            default:
                return new ExerciseHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise, parent, false));

        }
    }

    @Override
    public void onViewAttachedToWindow(BaseViewHolder<Exercise> holder) {
        super.onViewAttachedToWindow(holder);
        if (holder instanceof HeaderHolder) {
            EventBus.getDefault().register(holder);
        }
    }

    @Override
    public void onViewDetachedFromWindow(BaseViewHolder<Exercise> holder) {
        super.onViewDetachedFromWindow(holder);
        if (holder instanceof HeaderHolder) {
            EventBus.getDefault().unregister(holder);
        }
    }

    class HeaderHolder extends EmptyViewHolder<Exercise> {

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
            tVFrgCreateWorkoutRounds.setText(Html.fromHtml(
                    itemView.getContext().getString(R.string.frg_create_workout_rounds_number,
                            workout.getRounds())));
            tVFrgCreateWorkoutRestExercise.setText(Html.fromHtml(
                    itemView.getContext().getString(R.string.frg_create_workout_rest_between_exercise_number,
                            workout.getRestBetweenExercise())));
            tVFrgCreateWorkoutRestRounds.setText(Html.fromHtml(
                    itemView.getContext().getString(R.string.frg_create_workout_rest_between_rounds_number,
                            workout.getRestRoundsExercise())));
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

        @Override
        protected void bindView() {
            super.bindView();
            eTFrgCreateWorkoutName.setText(workout.getName());
            updateViewsTimes();
        }

        @Subscribe
        public void errorInValidationFragment(EventErrorName eventErrorName) {
            ((TextInputLayout) eTFrgCreateWorkoutName.getParent().getParent()).setError(eventErrorName.getMessage());
        }

    }

    class ExerciseHolder extends BaseViewHolder<Exercise> {

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

        @Override
        protected void bindView(Exercise exercise) {
            tVItemExerciseName.setText(exercise.getName());
            tVItemExerciseRepetitions.setText(tVItemExerciseRepetitions.getContext()
                    .getString(R.string.frg_create_workout_item_exercise, exercise.getRepetitions()));
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
                    if (getItems().get(getItemPosition(getAdapterPosition())).getRepetitions() > view.getContext().getResources().getInteger(R.integer.min_rounds)) {
                        getItems().get(getItemPosition(getAdapterPosition()))
                                .setRepetitions(getItems().get(getItemPosition(getAdapterPosition())).getRepetitions()
                                        - view.getContext().getResources().getInteger(R.integer.min_rounds));
                    }
                    break;
                case R.id.iBFItemExercisePluseRepetitions:
                    getItems().get(getItemPosition(getAdapterPosition()))
                            .setRepetitions(getItems().get(getItemPosition(getAdapterPosition())).getRepetitions()
                                    + view.getContext().getResources().getInteger(R.integer.min_rounds));
                    break;
            }
            tVItemExerciseRepetitions.setText(tVItemExerciseRepetitions.getContext()
                    .getString(R.string.frg_create_workout_item_exercise, getItems()
                            .get(getItemPosition(getAdapterPosition())).getRepetitions()));
        }

        @OnClick(R.id.iBCreateExerciseDelete)
        public void onDeleteItem() {
            removeItemByPosition(getItemPosition(getAdapterPosition()));
        }

        @OnCheckedChanged(R.id.cBItemExercise)
        public void checkExercise(boolean check) {
            getItems().get(getItemPosition(getAdapterPosition())).setChecked(check);
            if (getOnExerciseClickListener() != null) {
                getOnExerciseClickListener().onNumberOfSelectExercisesListener(getItemsByCondition().size());
            }
        }

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
    public boolean ifValidCondition(Exercise exercise) {
        return exercise.isChecked();
    }

    public boolean validateHeader() {
        if (workout.getName().length() < 4) {
            EventBus.getDefault().post(new EventErrorName(App.getGlobalContext().getString(R.string.frg_create_workout_name_nim)));
            return false;
        } else {
            EventBus.getDefault().post(new EventErrorName(null));
        }
        return true;
    }

    class ExerciseEmptyHolder extends EmptyViewHolder<Exercise> {

        @BindView(R.id.tVItemEmpty)
        TextView tVItemEmpty;

        public ExerciseEmptyHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void bindView() {
            super.bindView();
            if(typeView==AdapterExercise.ADD_TO_WORKOUT){
                tVItemEmpty.setText(tVItemEmpty.getContext().getString(R.string.frg_exercise_empty_exercices));
            }

        }
    }


}


