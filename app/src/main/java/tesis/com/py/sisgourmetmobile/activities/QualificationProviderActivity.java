package tesis.com.py.sisgourmetmobile.activities;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.adapters.ProviderSpinnerAdapter;
import tesis.com.py.sisgourmetmobile.dialogs.AlertDialogFragment;
import tesis.com.py.sisgourmetmobile.dialogs.CancelableAlertDialogFragment;
import tesis.com.py.sisgourmetmobile.entities.Provider;
import tesis.com.py.sisgourmetmobile.entities.Qualification;
import tesis.com.py.sisgourmetmobile.repositories.ProviderRepository;
import tesis.com.py.sisgourmetmobile.repositories.QualificationRepository;
import tesis.com.py.sisgourmetmobile.utils.Utils;

public class QualificationProviderActivity extends AppCompatActivity implements AlertDialogFragment.AlertDialogFragmentListener, CancelableAlertDialogFragment.CancelableAlertDialogFragmentListener {

    static final String TAG_CLASS = QualificationProviderActivity.class.getName();
    private AppCompatSpinner mSpinnerProvider;
    private RatingBar mRatingBar;
    private TextView mQualificationTextView;
    private AppCompatEditText mCommentInputText;
    private Qualification qualificationObject = new Qualification();
    private ProviderSpinnerAdapter mProviderSpinnerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qualification);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_qualification);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        mSpinnerProvider = (AppCompatSpinner) findViewById(R.id.id_provider_spinner);
        mQualificationTextView = (TextView) findViewById(R.id.qualification_text_view);
        mCommentInputText = (AppCompatEditText) findViewById(R.id.comment_input_Text);
        chargeSpinnerData();
        qualificationListener();

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CancelableAlertDialogFragment cancelableAlertDialogFragment = CancelableAlertDialogFragment.newInstance(
                        getString(R.string.qualification_title),
                        getString(R.string.qualification_message_back_press),
                        getString(R.string.label_accept),
                        getString(R.string.label_cancel),
                        R.mipmap.ic_info_black_36dp);
                cancelableAlertDialogFragment.show(getFragmentManager(), TAG_CLASS);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_done_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.id_action_done:
                save();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onBackPressed() {
        CancelableAlertDialogFragment cancelableAlertDialogFragment = CancelableAlertDialogFragment.newInstance(
                getString(R.string.qualification_title),
                getString(R.string.qualification_message_back_press),
                getString(R.string.label_accept),
                getString(R.string.label_cancel),
                R.mipmap.ic_info_black_36dp);
        cancelableAlertDialogFragment.show((this).getFragmentManager(), TAG_CLASS);
    }

    private void chargeSpinnerData() {
        long countProviderRegister = ProviderRepository.count();
        if (countProviderRegister > 0) {
            mProviderSpinnerAdapter = new ProviderSpinnerAdapter(this, R.layout.item_provider_spinner, ProviderRepository.getAllProvider());
            mSpinnerProvider.setAdapter(mProviderSpinnerAdapter);
        } else {
            Utils.builToast(this, getString(R.string.error_no_provider_data));
            finish();
        }
    }


    private void qualificationListener() {
        mRatingBar = (RatingBar) findViewById(R.id.id_rating_bar);

        //if rating value is changed,
        //display the current rating value in the result (textview) automatically
        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                String ratingValue = String.valueOf(mRatingBar.getRating());
                String arrayString[] = getResources().getStringArray(R.array.qualification_string_values);

                switch (ratingValue) {
                    case "1.0":
                        mQualificationTextView.setText(arrayString[0]);
                        break;
                    case "2.0":
                        mQualificationTextView.setText(arrayString[1]);
                        break;
                    case "3.0":
                        mQualificationTextView.setText(arrayString[2]);
                        break;
                    case "4.0":
                        mQualificationTextView.setText(arrayString[3]);
                        break;
                    case "5.0":
                        mQualificationTextView.setText(arrayString[4]);
                        break;
                    default:
                        mQualificationTextView.setText("hola");
                        break;
                }
            }
        });


    }

    private void save() {
        Log.d(TAG_CLASS, "CALL_METHOD");

        Provider mSelectedSpinner = (Provider) mSpinnerProvider.getSelectedItem();
        String mQualificationString = mQualificationTextView.getText().toString().trim();
        String mCommentValueString = mCommentInputText.getText().toString().trim();

        Log.d(TAG_CLASS, "calificacion: " + mQualificationString);


        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(mQualificationString)) {
            mQualificationTextView.setError(getString(R.string.error_empty_qualification));
            focusView = mQualificationTextView;
            focusView.requestFocus();
            cancel = true;
        } else if (mQualificationString.contains(getString(R.string.quialification_text))) {
            mQualificationTextView.setError(getString(R.string.error_empty_qualification));
            Utils.builToast(this, getString(R.string.error_empty_qualification));
            focusView = mQualificationTextView;
            focusView.requestFocus();
            cancel = true;
        }

        if (TextUtils.isEmpty(mCommentValueString)) {
            mCommentValueString = "SIN COMENTARIO";
        }


        if (cancel) {
            focusView.requestFocus();
        } else {
            try {
                Log.d(TAG_CLASS, "entro en el cancel");

                qualificationObject.setProvider(mSelectedSpinner.getProviderName());
                qualificationObject.setQualification(mQualificationString);
                qualificationObject.setCommentary(mCommentValueString);
                Log.d(TAG_CLASS, "object:" + qualificationObject.toString());
                QualificationRepository.store(qualificationObject);
                AlertDialogFragment alertDialogFragment = AlertDialogFragment.newInstance(
                        getString(R.string.qualification_title),
                        getString(R.string.qualification_dialog_message),
                        getString(R.string.label_accept),
                        R.mipmap.ic_done_black_36dp);
                alertDialogFragment.show((this).getFragmentManager(), TAG_CLASS);

            } catch (Exception ex) {
                AlertDialogFragment errorDialogFragment = AlertDialogFragment.newInstance(
                        getString(R.string.qualification_title),
                        getString(R.string.database_error_message),
                        getString(R.string.label_accept),
                        R.mipmap.ic_error_black_36dp);
                errorDialogFragment.show((this).getFragmentManager(), TAG_CLASS);
            }
        }
    }

    @Override
    public void onAlertDialogPositiveClick(DialogFragment dialog) {
        finish();
    }

    @Override
    public void onCancelableAlertDialogPositiveClick(DialogFragment dialog) {
        finish();
    }

    @Override
    public void onCancelableAlertDialogNegativeClick(DialogFragment dialog) {

    }
}
