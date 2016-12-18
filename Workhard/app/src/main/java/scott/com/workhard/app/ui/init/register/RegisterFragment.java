package scott.com.workhard.app.ui.init.register;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import scott.com.workhard.R;
import scott.com.workhard.app.ui.ActivityMain;
import scott.com.workhard.app.ui.init.login.presenter.SessionPresenter;
import scott.com.workhard.app.ui.init.login.presenter.SessionPresenterListeners;
import scott.com.workhard.base.view.BaseActivity;
import scott.com.workhard.base.view.BaseFragment;
import scott.com.workhard.bus.event.EventProgressDialog;
import scott.com.workhard.bus.event.EventSnackBar;
import scott.com.workhard.utils.DatePickerFragment;
import scott.com.workhard.utils.DateTimeUtils;

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

public class RegisterFragment extends BaseFragment implements SessionPresenterListeners
        , Validator.ValidationListener {

    @NotEmpty
    @BindView(R.id.eTFrgRegisterName)
    AppCompatEditText eTFrgRegisterName;

    @NotEmpty
    @BindView(R.id.eTFrgRegisterLastName)
    AppCompatEditText eTFrgRegisterLastName;

    @Email
    @BindView(R.id.eTFrgRegisterEmail)
    AppCompatEditText eTFrgRegisterEmail;
    @BindView(R.id.tVFrgRegisterDate)
    TextView tVFrgRegisterDate;
    @NotEmpty
    @Password
    @BindView(R.id.eTFrgRegisterPassword)
    AppCompatEditText eTFrgRegisterPassword;
    @NotEmpty
    @ConfirmPassword
    @BindView(R.id.eTFrgRegisterRepeatPassword)
    AppCompatEditText eTFrgRegisterRepeatPassword;

    private SessionPresenter presenter;
    private Validator validator;


    public static Fragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVars();
    }

    protected void initVars() {
        setHasOptionsMenu(true);
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
        eTFrgRegisterRepeatPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                ((BaseActivity) getActivity()).clearKeyboardFromScreen();
                return true;
            }
        });
    }

    private void cleanValidations() {
        ((TextInputLayout) eTFrgRegisterName.getParent().getParent()).setError(null);
        ((TextInputLayout) eTFrgRegisterLastName.getParent().getParent()).setError(null);
        ((TextInputLayout) eTFrgRegisterEmail.getParent().getParent()).setError(null);
        ((TextInputLayout) eTFrgRegisterPassword.getParent().getParent()).setError(null);
        ((TextInputLayout) eTFrgRegisterRepeatPassword.getParent().getParent()).setError(null);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                getActivity().onBackPressed();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onValidationSucceeded() {
        presenter.doRegister(eTFrgRegisterName.getText().toString(),
                eTFrgRegisterLastName.getText().toString(),
                eTFrgRegisterEmail.getText().toString(),
                eTFrgRegisterPassword.getText().toString(),
                DateTimeUtils.getDateTimeFromPattern(getString(R.string.date_register_formatter),
                        tVFrgRegisterDate.getText().toString()).getMillis());
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(getActivity());
            if (view instanceof AppCompatEditText) {
                ((TextInputLayout) view.getParent().getParent()).setError(message);
            }
        }
    }

    @Override
    public void showMessage(String stringId) {
        EventBus.getDefault().post(new EventSnackBar().withMessage(stringId));
    }

    @Override
    public void showProgressIndicator(String message) {
        EventBus.getDefault().post(new EventProgressDialog(message, true));
    }

    @Override
    public void removeProgressIndicator() {
        EventBus.getDefault().post(new EventProgressDialog(false));
    }

    public void showDatePickerDialog(TextView v) {
        DatePickerFragment.showDatePickerDialog(getActivity().getSupportFragmentManager(), v);
    }

    @OnClick(R.id.tVFrgRegisterDate)
    public void onClick(TextView textView) {
        showDatePickerDialog(textView);
    }

    @Override
    public void onLoginSuccessful() {
        ActivityMain.newInstance(getActivity());
    }

    @OnClick(R.id.fBFrgSingIn)
    public void onClick() {
        cleanValidations();
        validator.validate();
    }
}
