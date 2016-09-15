package scott.com.workhard.app.ui.select_exercise;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import scott.com.workhard.R;
import scott.com.workhard.base.view.BaseFragment;
import scott.com.workhard.app.ui.create_workout.adapter.AdapterExercise;
import scott.com.workhard.bus.event.EventAddExercises;
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
public class FrgSelectExercise extends BaseFragment {

    @BindView(R.id.rVFrgCreateWorkOut)
    RecyclerView rVFrgCreateWorkOut;

    private AdapterExercise adapter = new AdapterExercise(AdapterExercise.ADD_TO_WORKOUT);
    private int numberSelectedItems = 0;

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
        adapter.showEmptyState(true);
        adapter.addOnExerciseClickListener(new AdapterExercise.onExerciseClickListener() {
            @Override
            public void onNumberOfSelectExercisesListener(int numberSelectedItems) {
                FrgSelectExercise.this.numberSelectedItems = numberSelectedItems;
                getActivity().invalidateOptionsMenu();
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
        rVFrgCreateWorkOut.setLayoutManager(new LinearLayoutManager(getActivity()));
        rVFrgCreateWorkOut.addItemDecoration(
                new SpacesItemDecoration(adapter.ifAdapterHaveHeaderView(), R.dimen.default_medium_size));
        rVFrgCreateWorkOut.setAdapter(adapter);
        fillDummyData();
    }

    private void fillDummyData() {

        adapter.addItem(new Exercise().withName("Pedro")
                .withRepetitions(adapter.getItemCount() + 1));
        adapter.addItem(new Exercise().withName("Hola mundo " + adapter.getItemCount())
                .withRepetitions(adapter.getItemCount() + 1));

        adapter.addItem(new Exercise().withName("Hola mundo " + adapter.getItemCount())
                .withRepetitions(adapter.getItemCount() + 1));
        adapter.addItem(new Exercise().withName("Hola mundo " + adapter.getItemCount())
                .withRepetitions(adapter.getItemCount() + 1));
        adapter.addItem(new Exercise().withName("Hola mundo " + adapter.getItemCount())
                .withRepetitions(adapter.getItemCount() + 1));
        adapter.addItem(new Exercise().withName("Hola mundo " + adapter.getItemCount())
                .withRepetitions(adapter.getItemCount() + 1));
        adapter.addItem(new Exercise().withName("Hola mundo " + adapter.getItemCount())
                .withRepetitions(adapter.getItemCount() + 1));
        adapter.addItem(new Exercise().withName("Hola mundo " + adapter.getItemCount())
                .withRepetitions(adapter.getItemCount() + 1));
        adapter.addItem(new Exercise().withName("Hola mundo " + adapter.getItemCount())
                .withRepetitions(adapter.getItemCount() + 1));
        adapter.addItem(new Exercise().withName("Hola mundo " + adapter.getItemCount())
                .withRepetitions(adapter.getItemCount() + 1));
        adapter.addItem(new Exercise().withName("Hola mundo " + adapter.getItemCount())
                .withRepetitions(adapter.getItemCount() + 1));
        adapter.addItem(new Exercise().withName("Hola mundo " + adapter.getItemCount())
                .withRepetitions(adapter.getItemCount() + 1));
        adapter.addItem(new Exercise().withName("Hola mundo " + adapter.getItemCount())
                .withRepetitions(adapter.getItemCount() + 1));
        adapter.addItem(new Exercise().withName("Hola mundo " + adapter.getItemCount())
                .withRepetitions(adapter.getItemCount() + 1));
        adapter.addItem(new Exercise().withName("Hola mundo " + adapter.getItemCount())
                .withRepetitions(adapter.getItemCount() + 1));


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
        inflater.inflate(R.menu.menu_add_exercises, menu);
        final MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                UserFeedback.show("SearchOnQueryTextSubmit: " + query);
                if (!searchView.isIconified()) {
                    searchView.setIconified(true);
                }
                myActionMenuItem.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.filterItems(s);
                // UserFeedback.show( "SearchOnQueryTextChanged: " + s);
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

}
