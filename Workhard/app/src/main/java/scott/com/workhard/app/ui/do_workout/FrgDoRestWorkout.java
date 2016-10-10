package scott.com.workhard.app.ui.do_workout;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.github.lzyzsd.circleprogress.DonutProgress;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import scott.com.workhard.R;
import scott.com.workhard.base.view.BaseFragment;
import scott.com.workhard.bus.event.EventFinishRecoveryTime;
import scott.com.workhard.bus.event.EventNextExercise;

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
public class FrgDoRestWorkout extends BaseFragment {

    @BindView(R.id.donut_progress)
    DonutProgress donutProgress;
    private int timer = 100;

    public static Fragment newInstance() {
        return new FrgDoRestWorkout();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVars();
    }

    private void initVars() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_rest_workout, container, false);
        ButterKnife.bind(this, view);
        intViews();
        return view;
    }

    private void intViews() {
        addSubscription(Observable.interval(1, TimeUnit.SECONDS)
                .take(100)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("TEST", "ERRRO: " + e.toString());

                    }

                    @Override
                    public void onNext(Long aLong) {
                        if (aLong.intValue() <= 100) {
                            Log.d("TEST", "Server response: " + aLong.toString());
                            updateProgress(aLong.intValue());
                        }

                    }
                }));

    }

    private void updateProgress(int i) {
        donutProgress.setProgress(i);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        presenter = new DoWorkoutPresenter();
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
            case android.R.id.home: {
                getActivity().onBackPressed();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.bTFrgDoWorkoutNext)
    public void onClick() {
        EventBus.getDefault().post(new EventFinishRecoveryTime());
    }

}
