package tesis.com.py.sisgourmetmobile.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;

import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.entities.User;
import tesis.com.py.sisgourmetmobile.repositories.LunchRepository;
import tesis.com.py.sisgourmetmobile.repositories.UserRepository;
import tesis.com.py.sisgourmetmobile.utils.AppPreferences;
import tesis.com.py.sisgourmetmobile.utils.Utils;

public class CheckAmountActivity extends AppCompatActivity {

    // VIEW
    private TextView mName;
    private TextView mLastName;
    private TextView mIdentifyCard;
    private TextView mAvailableAmount;
    private ImageView mToolbarImageView;
    private Dialog dialog;

    // Utilitarian Variable
    private static final int CAMERA_REQUEST = 1888;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_amount);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mToolbarImageView = (ImageView) findViewById(R.id.header_image);
        mName = (TextView) findViewById(R.id.name_user_value);
        mLastName = (TextView) findViewById(R.id.last_name_user_value);
        mIdentifyCard = (TextView) findViewById(R.id.identify_card_user_value);
        mAvailableAmount = (TextView) findViewById(R.id.amount_value);
        FloatingActionButton mFABSettings = (FloatingActionButton) findViewById(R.id.fab_settings);
        mFABSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSettingsDialog();
            }
        });
        setupData();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getExtras();
            if (bundle != null) {
                if (saveImageUser(Utils.bitmapToByteArray((Bitmap) bundle.get("data"))) > 0) {
                    setupData();
                } else {
                    Utils.builToast(this, getString(R.string.error_save_user_data));
                    return;
                }
            } else {
                Utils.builToast(this, getString(R.string.error_processing_image));
                return;
            }
        }
    }

    private void setupData() {
        User user = UserRepository.getUser(this);
        if (user != null) {
            mName.setText(user.getName());
            mLastName.setText(user.getLastName());
            mIdentifyCard.setText(user.getIdentifyCard());
            mAvailableAmount.setText(Utils.formatNumber(user.getCurrentAmount(), "Gs."));
            if (user.getImageProfile() != null) {
                Bitmap bmp = BitmapFactory.decodeByteArray(user.getImageProfile(), 0, user.getImageProfile().length);
                mToolbarImageView.setImageBitmap(bmp);
            }
        } else {
            Utils.builToast(this, getString(R.string.error_get_data_user));
            finish();
        }
    }

    private long saveImageUser(byte[] arrayImage) {
        User user = UserRepository.getUser(this);
        long mOperationId = 0;
        if (user != null) {
            user.setImageProfile(arrayImage);
            mOperationId = UserRepository.store(user);
        }
        return mOperationId;
    }

    private void showSettingsDialog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View contentDialogView = layoutInflater.inflate(R.layout.settings_account_dialog, null);
        ImageView mAccountImage = (ImageView) contentDialogView.findViewById(R.id.account_image);
        ImageButton mChangeImageButton = (ImageButton) contentDialogView.findViewById(R.id.change_image_button);
        final AppCompatEditText mNameValue = (AppCompatEditText) contentDialogView.findViewById(R.id.account_name_editText);
        final AppCompatEditText mLastNameValue = (AppCompatEditText) contentDialogView.findViewById(R.id.account_lastName_editText);
        AppCompatButton mAccepButton = (AppCompatButton) contentDialogView.findViewById(R.id.button_accept);
        AppCompatButton mCancelButton = (AppCompatButton) contentDialogView.findViewById(R.id.button_cancel);
        AppCompatCheckBox mChangeDataCheckBox = (AppCompatCheckBox) contentDialogView.findViewById(R.id.change_data_checkBox);
        User user = UserRepository.getUser(CheckAmountActivity.this);
        if (user != null) {
            mNameValue.setText(user.getName());
            mLastNameValue.setText(user.getLastName());
            if(user.getImageProfile() != null){
                Bitmap bmp = BitmapFactory.decodeByteArray(user.getImageProfile(), 0, user.getImageProfile().length);
                mAccountImage.setImageBitmap(bmp);
            }

        }

        mChangeDataCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    mNameValue.setEnabled(true);
                    mLastNameValue.setEnabled(true);
                }else {
                    mNameValue.setEnabled(false);
                    mLastNameValue.setEnabled(false);
                }
            }
        });



        mAccepButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mChangeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        mCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        builder.setView(contentDialogView);
        dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();

    }

}
