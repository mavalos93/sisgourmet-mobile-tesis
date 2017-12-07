package tesis.com.py.sisgourmetmobile.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Date;

import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.entities.User;
import tesis.com.py.sisgourmetmobile.repositories.LunchRepository;
import tesis.com.py.sisgourmetmobile.repositories.UserRepository;
import tesis.com.py.sisgourmetmobile.utils.AppPreferences;
import tesis.com.py.sisgourmetmobile.utils.Constants;
import tesis.com.py.sisgourmetmobile.utils.Utils;

public class CheckAmountActivity extends AppCompatActivity {

    // VIEW

    private TextView mMainUsername;
    private TextView mName;
    private TextView mLastName;
    private TextView mIdentifyCard;
    private TextView mAvailableAmount;
    private TextView mAsignedToAmount;
    private ImageView mUserImageView;
    private TextView mAsignedToDate;


    private Dialog dialog;

    // Utilitarian Variable
    private static final int CAMERA_REQUEST = 1888;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_amount);
        Toolbar toolbar = findViewById(R.id.toolbar_account);
        setSupportActionBar(toolbar);

        mMainUsername = findViewById(R.id.username);
        mName = findViewById(R.id.username_value);
        mLastName = findViewById(R.id.lastname_value);
        mIdentifyCard = findViewById(R.id.identifycard_value);
        mAvailableAmount = findViewById(R.id.available_amount);
        mAsignedToAmount = findViewById(R.id.asigned_amount);
        mUserImageView = findViewById(R.id.user_image);
        mAsignedToDate = findViewById(R.id.asigned_to_date);
        setupData();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_menu_account, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_change_image) {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }


        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
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
            mMainUsername.setText(user.getUserName());
            mName.setText(user.getName());
            mLastName.setText(user.getLastName());
            mIdentifyCard.setText(user.getIdentifyCard());
            mAsignedToDate.setText(user.getAsignedToDate());
            mAsignedToAmount.setText(Utils.formatNumber(String.valueOf(user.getAsignedToAmount()), "Gs."));
            mAvailableAmount.setText(Utils.formatNumber(String.valueOf(user.getCurrentAmount()), "Gs."));

            if (user.getImageProfile() != null) {
                Bitmap bmp = BitmapFactory.decodeByteArray(user.getImageProfile(), 0, user.getImageProfile().length);
                mUserImageView.setImageBitmap(bmp);
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


}
