package scott.com.workhard.app.ui.init.register;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;
import scott.com.workhard.R;
import scott.com.workhard.app.base.view.BaseFragment;
import scott.com.workhard.app.ui.MainActivity;
import scott.com.workhard.app.ui.init.login.presenter.LoginPresenter;
import scott.com.workhard.app.ui.init.login.presenter.LoginPresenterListeners;
import scott.com.workhard.utils.DatePickerFragment;

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
    }

    private void cleanValidations() {
//        ((TextInputLayout) eTFrgLoginEmail.getParent()).setError(null);
//        ((TextInputLayout) eTFrgLoginPassword.getParent()).setError(null);
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

    public void showDatePickerDialog(EditText v) {
        DatePickerFragment.showDatePickerDialog(getActivity().getSupportFragmentManager(), v);
    }

    @OnClick(R.id.eTFrgRegisterDate)
    public void onClick(EditText editText) {
        showDatePickerDialog(editText);
    }
}