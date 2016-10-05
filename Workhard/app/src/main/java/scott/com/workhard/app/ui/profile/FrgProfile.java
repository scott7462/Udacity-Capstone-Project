package scott.com.workhard.app.ui.profile;

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
import android.widget.TextView;

import com.kbeanie.multipicker.api.entity.ChosenImage;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.joda.time.MutableDateTime;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import scott.com.workhard.R;
import scott.com.workhard.base.view.BasePickImageFragment;
import scott.com.workhard.bus.event.EventCallPickPhoto;
import scott.com.workhard.bus.event.EventUploadImage;
import scott.com.workhard.entities.User;
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

public class FrgProfile extends BasePickImageFragment implements Validator.ValidationListener {

    @BindView(R.id.tVFrgProfileDate)
    TextView tVFrgProfileDate;
    @BindView(R.id.eTFrgProfileName)
    AppCompatEditText eTFrgProfileName;
    @BindView(R.id.eTFrgProfileLastName)
    AppCompatEditText eTFrgProfileLastName;
    @BindView(R.id.eTFrgProfileEmail)
    AppCompatEditText eTFrgProfileEmail;

    private Validator validator;
    private String avatarFilePath;
    private User user;

    public static Fragment newInstance() {
        return new FrgProfile();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVars();
    }

    @Override
    public void imagePiker(ChosenImage image, int responseCode) {
        avatarFilePath = image.getThumbnailPath();
        updateImageToCover(new File(image.getThumbnailPath()));
    }

    private void updateImageToCover(File thumbnailPath) {
        EventBus.getDefault().post(new EventUploadImage(thumbnailPath, null));
    }

    @Override
    public void errorFindingImage(String errorMessage, int responseCode) {

    }

    private void initVars() {
        setHasOptionsMenu(true);
        validator = new Validator(this);
        validator.setValidationListener(this);
        user = new User();
        user.setName("Pedro");
        user.setLastName("Scott");
        user.setEmail("scott7462@gmail.com");
        user.setBirthday(594864000);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frg_profile, container, false);
        ButterKnife.bind(this, view);
        intViews();
        return view;
    }

    private void intViews() {
        eTFrgProfileName.setText(user.getName());
        eTFrgProfileLastName.setText(user.getLastName());
        eTFrgProfileEmail.setText(user.getEmail());
        tVFrgProfileDate.setText(DateTimeUtils.getStringPatternFromDateTime(
                getString(R.string.date_register_formatter), new MutableDateTime(user.getBirthday())));
    }


//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        presenter = new DoWorkoutPresenter();
//        presenter.attachView(this);
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        presenter.detachView();
//    }

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

    public void showDatePickerDialog(TextView v) {
        DatePickerFragment.showDatePickerDialog(getActivity().getSupportFragmentManager(), v);
    }

    @OnClick(R.id.tVFrgProfileDate)
    public void onClick(TextView textView) {
        showDatePickerDialog(textView);
    }

    @Subscribe
    public void pickPhoto(EventCallPickPhoto eventCallPickPhoto) {
        showPikerGallery(0);
    }

}
