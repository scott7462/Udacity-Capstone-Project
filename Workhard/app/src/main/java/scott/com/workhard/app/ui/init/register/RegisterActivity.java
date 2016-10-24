package scott.com.workhard.app.ui.init.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import scott.com.workhard.R;
import scott.com.workhard.base.view.BaseActivity;
import scott.com.workhard.bus.event.EventCallPickPhoto;
import scott.com.workhard.bus.event.EventUploadImage;

/**
 * @author Pedro Scott. scott7462@gmail.com
 * @version 7/12/16.
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

public class RegisterActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setToolbar(toolbar);
        savedFragmentState(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
    }

    private void savedFragmentState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            navigateMainContent(getSupportFragmentManager().getFragment(
                    savedInstanceState, "mContent"), getString(R.string.app_name));
        } else {
           toolbar.setTitle(getString(R.string.app_name));
            navigateMainContent(RegisterFragment.newInstance(), getString(R.string.app_name));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "mContent", getSupportFragmentManager().findFragmentById(R.id.container));
    }

    public static void newInstance(Activity activity) {
        activity.startActivity(new Intent(activity, RegisterActivity.class));
    }

}
