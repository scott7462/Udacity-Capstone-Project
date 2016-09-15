package scott.com.workhard.base.view;

import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.inputmethod.InputMethodManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import scott.com.workhard.R;
import scott.com.workhard.app.ui.MainActivity;
import scott.com.workhard.app.ui.init.register.RegisterActivity;
import scott.com.workhard.app.ui.select_exercise.SelectExerciseActivity;
import scott.com.workhard.bus.event.EventAlterDialog;
import scott.com.workhard.bus.event.EventSnackBar;
import scott.com.workhard.bus.util.SnackBarUtils;
import scott.com.workhard.utils.AlterDialogFragment;

/**
 * Copyright (C) 2015 The Android Open Source Project
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class BaseActivity extends AppCompatActivity {

    private final ArrayList<String> titleStack = new ArrayList<>();
    private Toolbar toolbar;
    private ProgressDialog progress;

    /**
     * Get the toolbar in the baseActivity instance.
     */
    public Toolbar getToolbar() {
        return toolbar;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        progress = new ProgressDialog(this);
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    /**
     * Set the toolbar to the baseActivity instance.
     */
    protected void setToolbar(Toolbar toolbar) {
        this.toolbar = toolbar;
    }

    /**
     * Method to navigate using  FragmentTransaction and FragmentManager.
     */
    private static void navigateTo(FragmentManager manager, Fragment newFragment, int containerId, boolean options) {
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(containerId, newFragment, newFragment.getClass().getSimpleName());
        if (options) {
            ft.addToBackStack(newFragment.getClass().getSimpleName());
        }
        ft.commit();
    }

    /**
     * clean Fragment Stack in the FragmentManager.
     */
    private static void cleanFragmentStack(FragmentManager fm) {
        fm.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    /**
     * Update the toolbar title.
     */
    private void updateActionBarTitle() {
        if (getToolbar() != null) {
            getToolbar().setTitle(titleStack.get(titleStack.size() - 1));
        }
    }

    /**
     * Navigate in the R.id.container with the SupportFragmentManager.
     */
    public void navigateMainContent(Fragment frg, String title) {
        cleanFragmentStack(getSupportFragmentManager());
        navigateTo(getSupportFragmentManager(), frg, R.id.container, false);
        titleStack.clear();
        titleStack.add(title);
        updateActionBarTitle();
    }

    /**
     * Navigate in the R.id.container with the SupportFragmentManager.
     */
    public void navigateLowContent(Fragment frg, String title) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        navigateTo(getSupportFragmentManager(), frg, R.id.container, true);
        titleStack.add(title);
        updateActionBarTitle();
    }

    /**
     * Navigate in the any FrameLayout container with the SupportFragmentManager.
     */
    public void navigateDetailContent(Fragment frg, String title, int container) {
        navigateTo(getSupportFragmentManager(), frg, container, false);
        titleStack.add(title);
        updateActionBarTitle();
    }

    protected Fragment getCurrentFrg() {
        return getSupportFragmentManager().findFragmentById(R.id.container);
    }

    public void navigateToMain() {
        MainActivity.newInstance(this);
    }


    public void goToRegister(FloatingActionButton floatingActionButton) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            RegisterActivity.newInstance(this, floatingActionButton);
        } else {
            RegisterActivity.newInstance(this);
        }
    }

    @Override
    public void onBackPressed() {
        updateBackNavigation();
        clearKeyboardFromScreen();
        super.onBackPressed();
    }

    private void updateBackNavigation() {
        if ((titleStack.size()) > 0) {
            titleStack.remove(titleStack.size() - 1);
        }
        if (titleStack.size() == 1) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(false);
        }
        if (titleStack.size() > 0) {
            updateActionBarTitle();
        }
    }

    public void clearKeyboardFromScreen() {
        if (this.getWindow().getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) this.getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(this.getWindow().getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void showProgressDialog(String message) {
        if (progress != null) {
            progress.setMessage(message);
            progress.show();
        }
    }

    public void dissmisProgressDialog() {
        if (progress != null) {
            progress.dismiss();
        }
    }

    @Subscribe
    public void showSnackBar(EventSnackBar eventSnackBar) {
        eventSnackBar.setViewParent(findViewById(android.R.id.content));
        SnackBarUtils.makeSnackBar(eventSnackBar);
    }

    @Subscribe
    public void showAlterDialog(EventAlterDialog event) {
        AlterDialogFragment newFragment = new AlterDialogFragment();
        newFragment.setEventAlterDialog(event);
        newFragment.show(getSupportFragmentManager(), "dialog");
    }


    public void goToSelectExercise() {
        SelectExerciseActivity.newInstance(this);
    }


}
