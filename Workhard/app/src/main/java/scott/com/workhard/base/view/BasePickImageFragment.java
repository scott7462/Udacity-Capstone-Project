package scott.com.workhard.base.view;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;
import android.widget.LinearLayout;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.kbeanie.multipicker.api.CacheLocation;
import com.kbeanie.multipicker.api.CameraImagePicker;
import com.kbeanie.multipicker.api.ImagePicker;
import com.kbeanie.multipicker.api.Picker;
import com.kbeanie.multipicker.api.callbacks.ImagePickerCallback;
import com.kbeanie.multipicker.api.entity.ChosenImage;

import java.util.List;

import scott.com.workhard.R;
import timber.log.Timber;

/**
 * Created by Pedro Scott on 8/4/16.
 */

public abstract class BasePickImageFragment extends BaseFragment implements ImagePickerCallback,
        MultiplePermissionsListener {

    private static final String CAMERA_FACING = "android.intent.extras.CAMERA_FACING";
    BottomSheetDialog bottomSheetDialog;
    private String pickerPath;
    private LinearLayout lLFrgPickGallery;
    private LinearLayout lLFrgPickCamera;
    private ImagePicker imagePicker;
    private CameraImagePicker cameraPicker;
    private int RESPONSE_CODE_TO_IMAGE = 0;

    public String getPickerPath() {
        return pickerPath;
    }

    public void setPickerPath(String pickerPath) {
        this.pickerPath = pickerPath;
    }

    public ImagePicker getImagePicker() {
        return imagePicker;
    }

    public void setImagePicker(ImagePicker imagePicker) {
        this.imagePicker = imagePicker;
    }

    public CameraImagePicker getCameraPicker() {
        return cameraPicker;
    }

    public void setCameraPicker(CameraImagePicker cameraPicker) {
        this.cameraPicker = cameraPicker;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initListeners();
    }

    protected void initViews() {
        View sheetView = getActivity().getLayoutInflater().inflate(R.layout.frg_button_sheet_camera_gallery, null);
        bottomSheetDialog = new BottomSheetDialog(getActivity());
        lLFrgPickGallery = (LinearLayout) sheetView.findViewById(R.id.lLFrgPickGallery);
        lLFrgPickCamera = (LinearLayout) sheetView.findViewById(R.id.lLFrgPickCamera);
        bottomSheetDialog.setContentView(sheetView);
    }

    protected void initListeners() {
        lLFrgPickGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImageSingle();
            }
        });
        lLFrgPickCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePicture();
            }
        });
    }

    @Override
    public void onImagesChosen(List<ChosenImage> list) {
        bottomSheetDialog.dismiss();
        if (list != null && list.size() > 0) {
            this.imagePiker(list.get(0), RESPONSE_CODE_TO_IMAGE);
        } else {
            this.errorFindingImage(getString(R.string.error_file_not_found), RESPONSE_CODE_TO_IMAGE);
        }
    }

    @Override
    public void onError(String error) {
        bottomSheetDialog.dismiss();
        Timber.e(error);
        this.errorFindingImage(error, RESPONSE_CODE_TO_IMAGE);
    }

    protected void checkPermissionGalleryAndCamera() {
        Dexter.checkPermissions(this, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
    }

    protected void showPikerGallery(int responseCode) {
        if (!Dexter.isRequestOngoing()) {
            RESPONSE_CODE_TO_IMAGE = responseCode;
            checkPermissionGalleryAndCamera();
        }
    }

    public void pickImageSingle() {
        imagePicker = new ImagePicker(this);
        imagePicker.shouldGenerateMetadata(true);
        imagePicker.setImagePickerCallback(this);
        Bundle bundle = new Bundle();
        bundle.putInt(CAMERA_FACING, 1);
        imagePicker.setCacheLocation(CacheLocation.EXTERNAL_STORAGE_APP_DIR);
        imagePicker.pickImage();
    }

    public void takePicture() {
        cameraPicker = new CameraImagePicker(this);
        cameraPicker.setImagePickerCallback(this);
        cameraPicker.shouldGenerateMetadata(true);
        cameraPicker.shouldGenerateThumbnails(true);
        pickerPath = cameraPicker.pickImage();
    }


    @Override
    public void onPermissionsChecked(MultiplePermissionsReport report) {
        if (report.getDeniedPermissionResponses() != null && report.getDeniedPermissionResponses().size() == 0) {
            bottomSheetDialog.show();
        }
    }

    @Override
    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
        token.continuePermissionRequest();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Picker.PICK_IMAGE_DEVICE) {
                if (getImagePicker() == null) {
                    setImagePicker(new ImagePicker(this));
                    getImagePicker().setImagePickerCallback(this);
                }
                getImagePicker().submit(data);
            } else if (requestCode == Picker.PICK_IMAGE_CAMERA) {
                if (getCameraPicker() == null) {
                    setCameraPicker(new CameraImagePicker(this));
                    getCameraPicker().setImagePickerCallback(this);
                    getCameraPicker().reinitialize(getPickerPath());
                }
                getCameraPicker().submit(data);
            }
        }
    }

    public abstract void imagePiker(ChosenImage image, int responseCode);

    public abstract void errorFindingImage(String errorMessage, int responseCode);

}
