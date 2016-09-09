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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import scott.com.workhard.R;
import scott.com.workhard.app.base.view.BaseActivity;
import scott.com.workhard.app.base.view.BaseFragment;
import scott.com.workhard.app.base.view.BaseSimpleAdapter;
import scott.com.workhard.app.ui.create_workout.adapter.SimpleAdapterExercise;
import scott.com.workhard.models.Exercise;

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
    public int restBetweenRounds;
    public int restBetweenExercises;
    public int round;
    private SimpleAdapterExercise adapter = new SimpleAdapterExercise();

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
        rVFrgCreateWorkOut.setAdapter(adapter);
        adapter.addHeaderClickListener(new SimpleAdapterExercise.onHeaderClickListener() {
            @Override
            public void onRoundChangeListener(int round) {
                FrgCreateWorkout.this.round = round;
            }

            @Override
            public void onRestBetweenExercisesListener(int restBetweenExercises) {
                FrgCreateWorkout.this.restBetweenExercises = restBetweenExercises;
            }

            @Override
            public void onRestBetweenRoundsListener(int restBetweenRounds) {
                FrgCreateWorkout.this.restBetweenRounds = restBetweenRounds;
            }

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
            public void onItemDismissed(int position, Exercise itemsToDismiss) {

            }
        });
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
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                adapter.undoLastItemsChangesPosition();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.fBFrgCreateWork)
    public void onClick() {
        adapter.addItem(new Exercise().withName("Hola mundo")
                .withRepetitions(adapter.getItemCount() + 1));
    }
}
