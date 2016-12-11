package scott.com.workhard.app.ui.workout_create;

import android.content.Context;
import android.content.DialogInterface;
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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import scott.com.workhard.R;
import scott.com.workhard.app.ui.workout_create.adapter.AdapterExercise;
import scott.com.workhard.app.ui.workout_create.presenter.CreateWorkoutPresenter;
import scott.com.workhard.app.ui.workout_create.presenter.CreateWorkoutPresenterListeners;
import scott.com.workhard.app.ui.workout_do.ActivityDoWorkout;
import scott.com.workhard.base.view.BaseActivity;
import scott.com.workhard.base.view.BaseFragment;
import scott.com.workhard.base.view.BaseSimpleAdapter;
import scott.com.workhard.bus.event.EventAddExercises;
import scott.com.workhard.bus.event.EventAlterDialog;
import scott.com.workhard.bus.event.EventProgressDialog;
import scott.com.workhard.bus.event.EventSnackBar;
import scott.com.workhard.bus.event.EventUpdateWorkoutList;
import scott.com.workhard.entities.Exercise;
import scott.com.workhard.entities.Workout;
import scott.com.workhard.utils.SpacesItemDecoration;

import static scott.com.workhard.app.ui.workout_do.ActivityDoWorkout.NEW_WORKOUT;

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
public class FrgCreateOrUpdateWorkout extends BaseFragment implements CreateWorkoutPresenterListeners {

    @BindView(R.id.rVFrgCreateWorkOut)
    RecyclerView rVFrgCreateWorkOut;

    public static final int NEW = 1234;
    public static final int UPDATE = 4321;

    @IntDef({NEW, UPDATE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TypeToView {
    }

    @TypeToView
    int typeView;

    private AdapterExercise adapter = new AdapterExercise(AdapterExercise.SHOW_IN_WORKOUT);
    private CreateWorkoutPresenter presenter;

    public static FrgCreateOrUpdateWorkout newInstance(Workout workout) {
        Bundle args = new Bundle();
        args.putParcelable(Workout.WORKOUT_ARG, workout);
        FrgCreateOrUpdateWorkout fragment = new FrgCreateOrUpdateWorkout();
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
        adapter.showHeaderView(true);
        Workout workout = (Workout) getArguments().getParcelable(Workout.WORKOUT_ARG);
        if (workout == null) {
            typeView = NEW;
        } else {
            typeView = UPDATE;
        }

        adapter.setWorkout(workout);

        adapter.addHeaderClickListener(new AdapterExercise.onHeaderClickListener() {
            @Override
            public void onNameWorkoutChange(String name) {
                ((BaseActivity) getActivity()).getToolbar().setTitle(name);
            }
        });
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
        rVFrgCreateWorkOut.setLayoutManager(new LinearLayoutManager(getActivity()));
        rVFrgCreateWorkOut.addItemDecoration(
                new SpacesItemDecoration(adapter.haveAdapterHeaderView(), R.dimen.default_medium_size));
        rVFrgCreateWorkOut.setAdapter(adapter);
        rVFrgCreateWorkOut.setHasFixedSize(true);
        adapter.addItemTouchHelperAdapter(rVFrgCreateWorkOut, new BaseSimpleAdapter.ItemTouchHelperAdapter<Exercise>() {

            @Override
            public void onItemMoved(int fromAdapterPosition, int fromItemPosition, Exercise itemOrigin, int toAdapterPosition, int toItemsPosition, Exercise itemTarget) {
            }

            @Override
            public void onItemDismissed(final int position, final Exercise item) {
                EventBus.getDefault().post(new EventSnackBar().withMessage("You removed " + item.getName() + " of this workout.")
                        .withAction("UNDO", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                adapter.undoRemovedItem(position, item);
                            }
                        }));

            }
        }, true, true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        presenter = new CreateWorkoutPresenter();
        presenter.attachView(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        presenter.detachView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (typeView == NEW) {
            inflater.inflate(R.menu.menu_create_workout, menu);
        } else {
            inflater.inflate(R.menu.menu_update, menu);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create:
                ((BaseActivity) getActivity()).clearKeyboardFromScreen();
                if (validateDataToSend()) {
                    presenter.doCreateWorkout(adapter.getWorkout());
                    break;
                }
            case R.id.action_do:
                ActivityDoWorkout.newInstance(getActivity(), NEW_WORKOUT, adapter.getWorkout());
                getActivity().finish();
                break;
            case R.id.action_delete:
                EventBus.getDefault().post(new EventAlterDialog().withMessage(getString(R.string.frg_create_workout_delete_workout))
                        .withPositveButton(getString(R.string.action_yes), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                presenter.onDeleteWorkout(adapter.getWorkout());
                                dialogInterface.dismiss();
                            }
                        })
                        .withNegativeButton(getString(R.string.action_not), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }));
                break;
            case android.R.id.home: {
                getActivity().onBackPressed();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean validateDataToSend() {
        if (adapter.getItems().size() <= 0) {
            EventBus.getDefault().post(new EventSnackBar().withMessage(getString(R.string.frg_create_workout_need_on)));
            return false;
        } else if (!adapter.validateHeader()) {
            return false;
        } else {
            return true;
        }

    }

    @OnClick(R.id.fBFrgCreateWork)
    public void onClick() {
        ((BaseActivity) getActivity()).goToSelectExercise();
    }

    @Subscribe
    public void onGetExercisesToAdd(EventAddExercises event) {
        adapter.addItems(event.getExercises());
    }

    @Override
    public void onCreateWorkoutSuccess() {
        EventBus.getDefault().post(new EventUpdateWorkoutList());
        getActivity().finish();
    }

    @Override
    public void onDeleteWorkoutSuccess() {
        EventBus.getDefault().post(new EventUpdateWorkoutList());
        getActivity().finish();
    }

    @Override
    public void showProgressIndicator(String message) {
        EventBus.getDefault().post(new EventProgressDialog(message, true));
    }

    @Override
    public void removeProgressIndicator() {
        EventBus.getDefault().post(new EventProgressDialog(false));
    }

    @Override
    public void showMessage(String string) {
        EventBus.getDefault().post(new EventSnackBar().withMessage(string));
    }
}
