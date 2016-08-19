package scott.com.workhard.app.base.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;

import scott.com.workhard.R;
import scott.com.workhard.app.ui.MainActivity;
import scott.com.workhard.app.ui.init.register.RegisterFragment;

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

    /**
     * Get the toolbar in the baseActivity instance.
     */
    private Toolbar getToolbar() {
        return toolbar;
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
        cleanFragmentStack(getSupportFragmentManager());
        navigateTo(getSupportFragmentManager(), frg, R.id.container, true);
        titleStack.clear();
        titleStack.add(title);
        updateActionBarTitle();
    }

    /**
     * Navigate in the any FrameLayout container with the SupportFragmentManager.
     */
    public void navigateDetailContent(Fragment frg, String title, int container) {
        cleanFragmentStack(getSupportFragmentManager());
        navigateTo(getSupportFragmentManager(), frg, container, false);
        titleStack.clear();
        titleStack.add(title);
        updateActionBarTitle();
    }

    protected Fragment getCurrentFrg() {
        return getSupportFragmentManager().findFragmentById(R.id.container);
    }

    public void navigateToMain() {
        MainActivity.newInstance(this);
    }


    public void goToRegister() {
        navigateLowContent(RegisterFragment.newInstance(), "Register");
    }
}