package scott.com.workhard.app.ui.init.register;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.SignInButton;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import scott.com.workhard.R;
import scott.com.workhard.app.base.view.BaseFragment;
import scott.com.workhard.app.ui.MainActivity;
import scott.com.workhard.app.ui.init.InitActivity;
import scott.com.workhard.app.ui.init.login.presenter.LoginPresenter;
import scott.com.workhard.app.ui.init.login.presenter.LoginPresenterListeners;

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

public class RegisterFragment extends BaseFragment implements LoginPresenterListeners
        , Validator.ValidationListener {


    @BindView(R.id.eTFrgRegisterName)
    AppCompatEditText eTFrgRegisterName;
    @BindView(R.id.eTFrgLoginLastName)
    AppCompatEditText eTFrgLoginLastName;
    @BindView(R.id.eTFrgRegisterDate)
    AppCompatEditText eTFrgRegisterDate;
    @BindView(R.id.eTFrgLoginEmail)
    AppCompatEditText eTFrgLoginEmail;
    @BindView(R.id.eTFrgLoginPassword)
    AppCompatEditText eTFrgLoginPassword;
    @BindView(R.id.eTFrgLoginRepeatPassword)
    AppCompatEditText eTFrgLoginRepeatPassword;
    @BindView(R.id.bTFrgLoginButton)
    AppCompatButton bTFrgLoginButton;
    @BindView(R.id.lBFrgLoginGooglePlus)
    SignInButton lBFrgLoginGooglePlus;
    @BindView(R.id.lBFrgLoginFacebook)
    LoginButton lBFrgLoginFacebook;
    @BindView(R.id.lBFrgLoginTwitter)
    TwitterLoginButton lBFrgLoginTwitter;
    private LoginPresenter presenter;
    private Validator validator;
    private ProgressDialog progress;


    public static Fragment newInstance() {
        return new RegisterFragment();
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
        View view = inflater.inflate(R.layout.frg_register, container, false);
        ButterKnife.bind(this, view);
        intViews();
        return view;
    }

    private void intViews() {
    }

    @OnClick(R.id.bTFrgLoginButton)
    public void onClick() {
        cleanValidations();
        validator.validate();
    }

    @OnClick(R.id.lBFrgLoginGooglePlus)
    public void onClickToSingInGoogle() {
        if (getActivity() instanceof InitActivity) {
            ((InitActivity) getActivity()).startLoginGooglePlus();
        }
    }

    private void cleanValidations() {
        ((TextInputLayout) eTFrgLoginEmail.getParent()).setError(null);
        ((TextInputLayout) eTFrgLoginPassword.getParent()).setError(null);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        presenter = new LoginPresenter();
        presenter.attachView(this);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        presenter.detachView();
    }

    @Override
    public void onValidationSucceeded() {
        presenter.doLogin(eTFrgLoginEmail.getText().toString(),
                eTFrgLoginPassword.getText().toString());
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getActivity());
            if (view instanceof AppCompatEditText) {
                ((TextInputLayout) view.getParent()).setError(message);
            }
        }
    }

    @Override
    public void showMessage(int stringId) {

    }

    @Override
    public void showProgressIndicator() {
        progress = ProgressDialog.show(getActivity(), "Login",
                "login message ...", true);
    }

    @Override
    public void removeProgressIndicator() {
        progress.dismiss();
    }

    @Override
    public void navigateToMain() {
        MainActivity.newInstance(getActivity());
    }

}
