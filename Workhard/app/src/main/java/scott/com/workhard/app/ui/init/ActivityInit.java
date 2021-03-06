package scott.com.workhard.app.ui.init;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import scott.com.workhard.R;
import scott.com.workhard.app.ui.init.login.SessionFragment;
import scott.com.workhard.base.view.BaseActivity;
import scott.com.workhard.bus.event.EventSnackBar;

/**
 * @author pedroscott. scott7462@gmail.com
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

public class ActivityInit extends BaseActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        MultiplePermissionsListener {

    private static final int RC_SIGN_IN = 9001;
    private static final int TWITTER_REQUEST_CODE = 140;
    private static final String CONTENT_FRAGMENT = "content";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.fBFrgSingIn)
    FloatingActionButton fBFrgSingIn;

    public FloatingActionButton getfBFrgSingIn() {
        return fBFrgSingIn;
    }

    private GoogleSignInOptions gso;
    private GoogleApiClient mGoogleApiClient;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setToolbar(toolbar);
        Dexter.checkPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        savedFragmentState(savedInstanceState);
        googleSingIn();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, CONTENT_FRAGMENT, getSupportFragmentManager().findFragmentById(R.id.container));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        getCurrentFrgToTwitter(requestCode, resultCode, data);
        handleSignInResult(requestCode, data);
    }


    public static void newInstance(Activity activity) {
        activity.startActivity(new Intent(activity, ActivityInit.class));
    }

    private void googleSingIn() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void savedFragmentState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            navigateMainContent(getSupportFragmentManager().getFragment(
                    savedInstanceState,CONTENT_FRAGMENT), getString(R.string.app_name));
        } else {
            navigateMainContent(SessionFragment.newInstance(), getString(R.string.app_name));
        }
    }

    private void handleSignInResult(int requestCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount acct = result.getSignInAccount();
                if (getCurrentFrg() instanceof SessionFragment) {
                    if (acct != null && acct.getEmail() != null) {
                        ((SessionFragment) getCurrentFrg()).loginWithGoogle(acct);
                    } else {
                        EventBus.getDefault().post(new EventSnackBar().withMessage(getString(R.string.error_login_with_google_email)));
                    }
                }
            } else {
                EventBus.getDefault().post(new EventSnackBar().withMessage(getString(R.string.error_login_with_google)));
            }
        }
    }

    private void getCurrentFrgToTwitter(int requestCode, int resultCode, Intent data) {
        if (requestCode == TWITTER_REQUEST_CODE) {
            Fragment fragment = getCurrentFrg();
            if (fragment instanceof SessionFragment) {
                ((SessionFragment) fragment).getlBFrgLoginTwitter().onActivityResult(requestCode, resultCode, data);
            }
        }

    }

    public void startLoginGooglePlus() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @OnClick(R.id.fBFrgSingIn)
    public void goToRegister() {
        goToRegister(fBFrgSingIn);
    }

    @Override
    public void onPermissionsChecked(MultiplePermissionsReport report) {
        if (report.areAllPermissionsGranted() && report.getGrantedPermissionResponses().size() == 2) {
            for (PermissionGrantedResponse permissionGrantedResponse : report.getGrantedPermissionResponses()) {
                if (!validatePermission(permissionGrantedResponse)) {
                    finish();
                }
            }
        }
    }

    private boolean validatePermission(PermissionGrantedResponse permissionGrantedResponse) {
        return permissionGrantedResponse.getPermissionName().equals(Manifest.permission.READ_EXTERNAL_STORAGE) ||
                permissionGrantedResponse.getPermissionName().equals(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
        token.continuePermissionRequest();
    }

}
