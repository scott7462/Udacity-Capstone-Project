package scott.com.workhard.app.ui.init.login;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Min;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import scott.com.workhard.R;
import scott.com.workhard.app.ui.init.ActivityInit;
import scott.com.workhard.app.ui.init.login.presenter.SessionPresenter;
import scott.com.workhard.app.ui.init.login.presenter.SessionPresenterListeners;
import scott.com.workhard.base.view.BaseFragment;
import scott.com.workhard.bus.event.EventSnackBar;
import scott.com.workhard.entities.User;
import timber.log.Timber;

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

public class SessionFragment extends BaseFragment implements SessionPresenterListeners,
        Validator.ValidationListener {

    @Email(messageResId = R.string.error_invalid_email)
    @BindView(R.id.eTFrgLoginEmail)
    AppCompatEditText eTFrgLoginEmail;

    @Password(messageResId = R.string.error_invalid_password)
    @NotEmpty
    @BindView(R.id.eTFrgLoginPassword)
    AppCompatEditText eTFrgLoginPassword;

    @BindView(R.id.lBFrgLoginFacebook)
    LoginButton lBFrgLoginFacebook;

    @BindView(R.id.lBFrgLoginTwitter)
    TwitterLoginButton lBFrgLoginTwitter;

    @BindView(R.id.lBFrgLoginGooglePlus)
    SignInButton lBFrgLoginGooglePlus;

    private SessionPresenter presenter;
    private Validator validator;
    private ProgressDialog progress;

    public static Fragment newInstance() {
        return new SessionFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVars();
    }

    private void initVars() {
        validator = new Validator(this);
        validator.setValidationListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_login, container, false);
        ButterKnife.bind(this, view);
        intViews();
        iniListeners();
        return view;
    }

    private void iniListeners() {

    }

    private void intViews() {
        facebookManger();
        twitterManager();
    }

    public TwitterLoginButton getlBFrgLoginTwitter() {
        return lBFrgLoginTwitter;
    }

    private void twitterManager() {
        lBFrgLoginTwitter.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                TwitterSession session = result.data;
                String msg = "@" + session.getUserName() + " logged in! (#" + session.getUserId() + ")";
                Timber.e(msg);
                presenter.doLoginWithTwitter(session.getUserName(), session.getUserId());
            }

            @Override
            public void failure(TwitterException exception) {
                EventBus.getDefault().post(new EventSnackBar().withMessage(getString(R.string.error_twitter_connection)));
            }
        });

    }

    @OnClick(R.id.lBFrgLoginGooglePlus)
    public void onClickToSingInGoogle() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        presenter = new SessionPresenter();
        presenter.attachView(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        presenter.detachView();
    }

    private void facebookManger() {
        lBFrgLoginFacebook.setReadPermissions("public_profile","email");
        lBFrgLoginFacebook.setFragment(this);
        lBFrgLoginFacebook.registerCallback(((ActivityInit) getActivity()).getCallbackManager(),
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Timber.e(loginResult.toString());
                        // App code
                    }

                    @Override
                    public void onCancel() {
                        Timber.e("Error");
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Timber.e(exception.toString());
                        // App code
                    }
                });
    }


    @Override
    public void showMessage(String stringId) {
        EventBus.getDefault().post(new EventSnackBar().withMessage(stringId));
    }

    @Override
    public void showProgressIndicator(String message) {
        progress = ProgressDialog.show(getActivity(), "Login",
                "login message ...", true);
    }

    @Override
    public void removeProgressIndicator() {
        progress.dismiss();
    }

    @Override
    public void onLoginSuccessful() {
        ((ActivityInit) getActivity()).navigateToMain();
    }

    @Override
    public void onRegisterSuccessful() {

    }

    @OnClick({R.id.bTFrgLoginButton, R.id.bTFrgLoginGoogleButton, R.id.bTFrgLoginFacebookButton, R.id.bTFrgLoginTwitterButton})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bTFrgLoginButton: {
                cleanErrors();
                validator.validate();
                break;
            }
            case R.id.bTFrgLoginGoogleButton: {
                if (getActivity() instanceof ActivityInit) {
                    ((ActivityInit) getActivity()).startLoginGooglePlus();
                }
                break;
            }
            case R.id.bTFrgLoginFacebookButton: {
                lBFrgLoginFacebook.performClick();
                break;
            }
            case R.id.bTFrgLoginTwitterButton: {
                lBFrgLoginTwitter.callOnClick();
                break;
            }
        }
    }

    @Override
    public void onValidationSucceeded() {
        cleanErrors();
        presenter.doLogin(eTFrgLoginEmail.getText().toString(),
                eTFrgLoginPassword.getText().toString(), User.EMAIL);
    }

    private void cleanErrors() {
        ((TextInputLayout) eTFrgLoginEmail.getParent().getParent()).setErrorEnabled(false);
        ((TextInputLayout) eTFrgLoginPassword.getParent().getParent()).setError(null);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getActivity());
            if (view instanceof AppCompatEditText) {
                ((TextInputLayout) view.getParent().getParent()).setErrorEnabled(true);
                ((TextInputLayout) view.getParent().getParent()).setError(message);
            }
        }
    }

    public void loginWithGoogle(String email, String acctId) {
        presenter.doLoginWithGoogle(email, acctId);
    }
}
