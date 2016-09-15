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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import scott.com.workhard.R;
import scott.com.workhard.base.view.BaseActivity;
import scott.com.workhard.base.view.BaseFragment;
import scott.com.workhard.base.view.BaseSimpleAdapter;
import scott.com.workhard.app.ui.create_workout.adapter.AdapterExercise;
import scott.com.workhard.bus.event.EventAddExercises;
import scott.com.workhard.bus.event.EventSnackBar;
import scott.com.workhard.models.Exercise;
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
public class FrgCreateWorkout extends BaseFragment {

    @BindView(R.id.rVFrgCreateWorkOut)
    RecyclerView rVFrgCreateWorkOut;

    private AdapterExercise adapter = new AdapterExercise(AdapterExercise.SHOW_IN_WORKOUT);

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
        adapter.showHeaderView(true);
        adapter.showEmptyState(true);
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
                new SpacesItemDecoration(adapter.ifAdapterHaveHeaderView(), R.dimen.default_medium_size));
        rVFrgCreateWorkOut.setAdapter(adapter);
        rVFrgCreateWorkOut.setHasFixedSize(true);
        adapter.addHeaderClickListener(new AdapterExercise.onHeaderClickListener() {
            @Override
            public void onNameWorkoutChange(String name) {
                ((BaseActivity) getActivity()).getToolbar().setTitle(name);
            }

        });

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
        inflater.inflate(R.menu.menu_save, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_menu_save: {
                ((BaseActivity) getActivity()).clearKeyboardFromScreen();
                if (validateDataToSend())
                    EventBus.getDefault().post(new EventSnackBar().withMessage("TODO Create service"));
                break;
            }
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
}
