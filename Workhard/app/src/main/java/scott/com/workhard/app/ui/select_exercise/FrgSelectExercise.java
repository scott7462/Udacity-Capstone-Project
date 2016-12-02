package scott.com.workhard.app.ui.select_exercise;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import scott.com.workhard.R;
import scott.com.workhard.app.ui.exercise.ExerciseActivity;
import scott.com.workhard.app.ui.select_exercise.presenter.ExercisesPresenterListener;
import scott.com.workhard.app.ui.select_exercise.presenter.PresenterExercises;
import scott.com.workhard.app.ui.workout_create.adapter.AdapterExercise;
import scott.com.workhard.base.view.BaseFragment;
import scott.com.workhard.base.view.BaseSimpleAdapter;
import scott.com.workhard.bus.event.EventAddExercises;
import scott.com.workhard.bus.event.EventSnackBar;
import scott.com.workhard.entities.Exercise;
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
public class FrgSelectExercise extends BaseFragment implements ExercisesPresenterListener {

    @BindView(R.id.rVFrgExercises)
    RecyclerView rVFrgExercises;
    @BindView(R.id.sRFrgExercises)
    SwipeRefreshLayout sRFrgExercises;

    private AdapterExercise adapter = new AdapterExercise(AdapterExercise.ADD_TO_WORKOUT);
    private int numberSelectedItems = 0;

    PresenterExercises presenter;

    public static Fragment newInstance() {
        return new FrgSelectExercise();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVars();
    }

    private void initVars() {
        setHasOptionsMenu(true);
        presenter.doGetExercises();
        adapter.showEmptyState(true);
        adapter.addOnExerciseClickListener(new AdapterExercise.onExerciseClickListener() {
            @Override
            public void onNumberOfSelectExercisesListener(int numberSelectedItems) {
                FrgSelectExercise.this.numberSelectedItems = numberSelectedItems;
                getActivity().invalidateOptionsMenu();
            }
        });
        adapter.addClickListener(new BaseSimpleAdapter.onItemClickListener<Exercise>() {
            @Override
            public void onItemViewsClick(Exercise item, int position) {
                ExerciseActivity.newInstance(getActivity(), item);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_exercises, container, false);
        ButterKnife.bind(this, view);
        intViews();
        return view;
    }

    private void intViews() {
        rVFrgExercises.setLayoutManager(new LinearLayoutManager(getActivity()));
        rVFrgExercises.addItemDecoration(
                new SpacesItemDecoration(adapter.haveAdapterHeaderView(), R.dimen.default_medium_size));
        rVFrgExercises.setAdapter(adapter);

        sRFrgExercises.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.doGetExercises();
            }
        });

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        presenter = new PresenterExercises();
        presenter.attachView(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        presenter.detachView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_add_exercises, menu);
        final MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                myActionMenuItem.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.filterItems(s);
                return true;
            }
        });
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItemAdd = menu.findItem(R.id.item_menu_add);
        MenuItem menuItemAddAll = menu.findItem(R.id.item_menu_add_all);
        switch (numberSelectedItems) {
            case 0: {
                menuItemAdd.setEnabled(false);
                break;
            }
            case 1: {
                menuItemAdd.setEnabled(true);
                break;
            }
            default: {
                menuItemAdd.setVisible(false);
                menuItemAddAll.setVisible(true);
                break;
            }
        }
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_menu_add: {
                returnExercisesSelected();
                break;
            }
            case R.id.item_menu_add_all: {
                returnExercisesSelected();
                break;
            }
            case android.R.id.home: {
                getActivity().onBackPressed();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void returnExercisesSelected() {
        EventBus.getDefault().post(new EventAddExercises(adapter.getItemsByCondition()));
        getActivity().finish();
    }

    @Override
    public void onGetListExercises(List<Exercise> exercises) {
        adapter.cleanItemsAndUpdate(exercises);
    }

    @Override
    public void showProgressIndicator(String message) {
        adapter.showLoadingState(true);
    }

    @Override
    public void removeProgressIndicator() {
        adapter.showLoadingState(false);
        sRFrgExercises.setRefreshing(false);
    }

    @Override
    public void showMessage(String stringId) {
        EventBus.getDefault().post(new EventSnackBar().withMessage(stringId));
    }


}
