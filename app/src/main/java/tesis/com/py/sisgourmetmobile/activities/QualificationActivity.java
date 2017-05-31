package tesis.com.py.sisgourmetmobile.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import py.com.library.style.TabStepper;
import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.entities.Garnish;
import tesis.com.py.sisgourmetmobile.entities.Lunch;
import tesis.com.py.sisgourmetmobile.entities.Order;
import tesis.com.py.sisgourmetmobile.entities.Qualification;
import tesis.com.py.sisgourmetmobile.recivers.OrdersObserver;
import tesis.com.py.sisgourmetmobile.repositories.GarnishRepository;
import tesis.com.py.sisgourmetmobile.repositories.LunchRepository;
import tesis.com.py.sisgourmetmobile.repositories.OrderRepository;
import tesis.com.py.sisgourmetmobile.repositories.QualificationRepository;
import tesis.com.py.sisgourmetmobile.utils.Constants;
import tesis.com.py.sisgourmetmobile.utils.Utils;

public class QualificationActivity extends AppCompatActivity {

    private final String TAG_CLASS = QualificationActivity.class.getName();

    // Utilitarian variable
    private long mRatingValue = 0;
    private long mQTransactionId = 0;
    private long mOrderUpdateId = 0;

    // List
    private List<Order> mOrderList = new ArrayList<>();

    // View atributes

    private TextView mainMenuTextView;
    private TextView garnishTextView;
    private TextView ratingDescriptionTextView;
    private TextView priceLunchTextView;
    private ImageView menuIamgeView;
    private AppCompatRatingBar mRatingMenu;
    private AppCompatEditText mCommentEditText;

    // Object & instances
    private Order mOrderObject = new Order();
    private Garnish mGarnishObject = new Garnish();

    // Dialogs
    private AlertDialog.Builder commentDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_qualification);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_activity_qualification);
        setSupportActionBar(toolbar);
        getOrderObject();
        mainMenuTextView = (TextView) findViewById(R.id.qualification_main_menu_textView);
        garnishTextView = (TextView) findViewById(R.id.qualification_garnish_textView);
        ratingDescriptionTextView = (TextView) findViewById(R.id.qualificaition_rating_description_textView);
        menuIamgeView = (ImageView) findViewById(R.id.qualification_menu_image);
        priceLunchTextView = (TextView) findViewById(R.id.qualification_price_unit_textView);
        mRatingMenu = (AppCompatRatingBar) findViewById(R.id.qualification_menu_rating);
        mCommentEditText = (AppCompatEditText) findViewById(R.id.qualification_comment_edtitText);

        setupDataView();
        setupRatingListener();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_done_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.id_action_done) {
            validateData();
        }

        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }


    private void getOrderObject() {
        try {
            Bundle bundle = this.getIntent().getExtras().getBundle(Constants.SERIALIZABLE);
            if (bundle != null) {
                mOrderObject = (Order) bundle.get(Constants.SEND_ORDER_OBJECT);
                if (mOrderObject != null) {
                    Log.d("QUALIFICATION", "ORDER: " + mOrderObject.toString());
                } else {
                    Utils.builToast(this, getString(R.string.error_get_lunch_object));
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void setupDataView() {

        mGarnishObject = GarnishRepository.getGarnishById(mOrderObject.getGarnishId());

        Lunch lunchQuery = LunchRepository.getLunchById(mOrderObject.getLunchId());
        if (lunchQuery != null) {
            mainMenuTextView.setText(lunchQuery.getMainMenuDescription());
        } else {
            mainMenuTextView.setText("Sin datos");
        }

        if (mGarnishObject != null) {
            garnishTextView.setText(mGarnishObject.getDescription());
        }

        priceLunchTextView.setText(mOrderObject.getOrderAmount() + " Gs.");

    }

    private void setupRatingListener() {
        mRatingMenu.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Log.d(TAG_CLASS, "RATING_VALUE: " + rating);
                setupRatingValue(String.valueOf(rating));
            }
        });
    }

    private void validateData() {

        mCommentEditText.setError(null);
        String mCommentString = mCommentEditText.getText().toString().trim();
        View focusView = null;
        boolean cancel = false;


        if (TextUtils.isEmpty(mCommentString)) {
            mCommentEditText.setError(getString(R.string.error_comment_empty));
            focusView = mCommentEditText;
            focusView.requestFocus();
            cancel = true;
        }

        if (mRatingValue == 0) {
            Utils.builToast(this, "Debes asignar una calificacion");
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            Qualification qualificationObject = new Qualification();
            qualificationObject.setCommentary(mCommentString);
            qualificationObject.setGarnishId(Long.valueOf(mOrderObject.getGarnishId()));
            qualificationObject.setLunchId(mOrderObject.getLunchId());
            qualificationObject.setProviderId(mOrderObject.getProviderId());
            qualificationObject.setQualificationValue(mRatingValue);
            mQTransactionId = QualificationRepository.store(qualificationObject);
            mOrderObject.setRatingLunch(mRatingValue);
            mOrderUpdateId = OrderRepository.store(mOrderObject);

            if (mQTransactionId > 0 && mOrderUpdateId > 0) {
                commentDialog = Utils.createSimpleDialog(this,
                        getString(R.string.qualification_activity_title),
                        getString(R.string.qualification_send_succes_message),
                        R.mipmap.ic_done_black_36dp, false);
                commentDialog.setPositiveButton(getString(R.string.label_accept), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        sendBroadcast(new Intent(OrdersObserver.ACTION_LOAD_ORDERS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

                    }
                });
                commentDialog.create();
                commentDialog.show();
                mRatingValue = 0;
            } else {
                mRatingValue = 0;
                Utils.builToast(this, getString(R.string.unexpected_error_comment));
                finish();
            }
        }


    }

    private void setupRatingValue(String stringRaiting) {
        try {


            float mRaitingValue = Float.parseFloat(stringRaiting);
            mRatingValue = (long) mRaitingValue;
            switch (stringRaiting) {
                case "1.0":
                    mRatingMenu.setRating(mRaitingValue);
                    ratingDescriptionTextView.setText("Muy Malo");
                    break;
                case "2.0":
                    mRatingMenu.setRating(mRaitingValue);
                    ratingDescriptionTextView.setText("Malo");
                    break;
                case "3.0":
                    mRatingMenu.setRating(mRaitingValue);
                    ratingDescriptionTextView.setText("Bién");
                    break;
                case "4.0":
                    mRatingMenu.setRating(mRaitingValue);
                    ratingDescriptionTextView.setText("Muy Bién");
                    break;
                case "5.0":
                    mRatingMenu.setRating(mRaitingValue);
                    ratingDescriptionTextView.setText("Excelente");
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Utils.builToast(this, "Error al guardar nota de calificacion");
            finish();
        }
    }

}
