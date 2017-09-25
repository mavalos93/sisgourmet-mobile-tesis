package tesis.com.py.sisgourmetmobile.activities;

import android.app.AlertDialog;
import android.app.DialogFragment;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import py.com.library.style.TabStepper;
import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.dialogs.AlertDialogFragment;
import tesis.com.py.sisgourmetmobile.dialogs.CancelableAlertDialogFragment;
import tesis.com.py.sisgourmetmobile.dialogs.ProgressDialogFragment;
import tesis.com.py.sisgourmetmobile.entities.Garnish;
import tesis.com.py.sisgourmetmobile.entities.Lunch;
import tesis.com.py.sisgourmetmobile.entities.Order;
import tesis.com.py.sisgourmetmobile.entities.Provider;
import tesis.com.py.sisgourmetmobile.entities.Qualification;
import tesis.com.py.sisgourmetmobile.entities.Users;
import tesis.com.py.sisgourmetmobile.network.MyRequest;
import tesis.com.py.sisgourmetmobile.network.NetworkQueue;
import tesis.com.py.sisgourmetmobile.recivers.MyCommentsObserver;
import tesis.com.py.sisgourmetmobile.recivers.OrdersObserver;
import tesis.com.py.sisgourmetmobile.repositories.GarnishRepository;
import tesis.com.py.sisgourmetmobile.repositories.LunchRepository;
import tesis.com.py.sisgourmetmobile.repositories.OrderRepository;
import tesis.com.py.sisgourmetmobile.repositories.ProviderRepository;
import tesis.com.py.sisgourmetmobile.repositories.QualificationRepository;
import tesis.com.py.sisgourmetmobile.repositories.UsersRepository;
import tesis.com.py.sisgourmetmobile.utils.AppPreferences;
import tesis.com.py.sisgourmetmobile.utils.Constants;
import tesis.com.py.sisgourmetmobile.utils.JsonObjectRequest;
import tesis.com.py.sisgourmetmobile.utils.URLS;
import tesis.com.py.sisgourmetmobile.utils.Utils;

public class QualificationActivity extends AppCompatActivity implements AlertDialogFragment.AlertDialogFragmentListener, CancelableAlertDialogFragment.CancelableAlertDialogFragmentListener {

    private final String TAG_CLASS = QualificationActivity.class.getName();

    // Utilitarian variable
    private long mRatingValue = 0;
    private boolean isCombinable = false;
    private long mLunchId = 0;
    private String mPrice;
    private long mProviderId = 0;
    private String mMainMenuDescription = "";
    private String KEY_ACTIVITY = "";
    private long mQualificationId = 0;
    private String mGarnishDescription = "";


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
    private QualificationTask mQualificationTask;
    private Lunch mLunchObject = new Lunch();
    private Qualification mQualification;

    // Dialogs

    // Request
    private RequestQueue mQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_qualification);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_activity_qualification);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getValues();
        mainMenuTextView = (TextView) findViewById(R.id.qualification_main_menu_textView);
        garnishTextView = (TextView) findViewById(R.id.qualification_garnish_textView);
        ratingDescriptionTextView = (TextView) findViewById(R.id.qualificaition_rating_description_textView);
        menuIamgeView = (ImageView) findViewById(R.id.qualification_menu_image);
        priceLunchTextView = (TextView) findViewById(R.id.qualification_price_unit_textView);
        mRatingMenu = (AppCompatRatingBar) findViewById(R.id.qualification_menu_rating);
        mCommentEditText = (AppCompatEditText) findViewById(R.id.qualification_comment_edtitText);

        setupDataView(mProviderId, mLunchId);
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


    private void getValues() {


        try {
            Bundle bundle = this.getIntent().getExtras().getBundle(Constants.SERIALIZABLE);
            KEY_ACTIVITY = this.getIntent().getStringExtra("KEY_ACTIVITY");

            if (bundle != null) {
                switch (KEY_ACTIVITY) {
                    case Constants.ACTION_QUALIFICATION_MENU:
                        mLunchObject = (Lunch) bundle.get(Constants.ACTION_QUALIFICATION_MENU);
                        if (mLunchObject != null) {
                            mLunchId = mLunchObject.getPrincipalMenuCode();
                            mMainMenuDescription = mLunchObject.getMainMenuDescription();
                            mProviderId = mLunchObject.getProviderId();
                            mPrice = String.valueOf(mLunchObject.getPriceUnit());
                        }
                        break;
                    case Constants.SEND_ORDER_OBJECT:
                        mOrderObject = (Order) bundle.get(Constants.SEND_ORDER_OBJECT);
                        if (mOrderObject != null) {
                            mLunchId = mOrderObject.getLunchId();
                            mPrice = mOrderObject.getOrderAmount();
                            mProviderId = mOrderObject.getProviderId();
                            Lunch lunchQuery = LunchRepository.getLunchById(mOrderObject.getLunchId());
                            if (lunchQuery != null) {
                                mMainMenuDescription = lunchQuery.getMainMenuDescription();
                            }

                        } else {
                            Utils.builToast(this, getString(R.string.error_get_lunch_object));
                        }
                        break;
                }

            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void setupDataView(long providerId, long mLunchId) {


        List<Garnish> mGarnishList = GarnishRepository.getGarnishByLunchId(mLunchId);

        Lunch lunchQuery = LunchRepository.getLunchById(mLunchId);
        if (lunchQuery != null) {
            mainMenuTextView.setVisibility(View.VISIBLE);
            mainMenuTextView.setText(lunchQuery.getMainMenuDescription());
        } else {
            mainMenuTextView.setVisibility(View.GONE);

        }

        if (mGarnishList.size() != 0) {

            switch (mGarnishList.size()) {
                case 0:
                    Utils.builToast(this, "No se encontro guarnicion");
                    finish();
                    break;
                case 1:
                    for (Garnish gr : mGarnishList) {
                        garnishTextView.setVisibility(View.VISIBLE);
                        mGarnishDescription = gr.getDescription();
                        garnishTextView.setText(gr.getDescription());
                    }
                    break;
                default:
                    garnishTextView.setVisibility(View.GONE);
                    isCombinable = true;
                    mGarnishDescription = "SIN GUARNICION";
                    break;
            }
            isCombinable = true;

        } else {
            garnishTextView.setVisibility(View.GONE);

        }

        priceLunchTextView.setText(mPrice + " Gs.");

        Provider mProviderObject = ProviderRepository.getProviderById(providerId);
        if (mProviderObject != null) {
            switch (mProviderObject.getProviderName()) {
                case "La Vienesa":
                    menuIamgeView.setImageResource(R.mipmap.la_vienesa);
                    break;
                case "Ña Eustaquia":
                    menuIamgeView.setImageResource(R.mipmap.nha_esutaquia);
                    break;
                case "Bolsi":
                    menuIamgeView.setImageResource(R.mipmap.bolsi);
                    break;
            }
        }

    }

    private void setupRatingListener() {
        mRatingMenu.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
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
            Utils.builToast(this, getString(R.string.error_not_qualification));
            return;
        }

        if (mProviderId == 0) {
            Utils.builToast(this, getString(R.string.error_provider_id_is_empty));
            return;
        }

        if (mMainMenuDescription.isEmpty()) {
            Utils.builToast(this, getString(R.string.error_main_menu_is_empty));
            return;
        }

        if (!isCombinable) {
            if (mGarnishDescription.isEmpty()) {
                Utils.builToast(this, getString(R.string.error_garnish_is_empty));
                return;
            }
        }


        if (cancel) {
            focusView.requestFocus();
        } else {
            if (KEY_ACTIVITY.equals(Constants.SEND_ORDER_OBJECT)) {
                mOrderObject.setRatingLunch(mRatingValue);
                OrderRepository.store(mOrderObject);
            }
            String mUsername = AppPreferences.getAppPreferences(this).getString(AppPreferences.KEY_PREFERENCE_USER, null);
            Log.d(TAG_CLASS, "username: "+ mUsername);
            mQualificationTask = new QualificationTask(mUsername, mCommentString, mProviderId, mMainMenuDescription, mGarnishDescription, mRatingValue);
            mQualificationTask.confirm();
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

    @Override
    public void onAlertDialogPositiveClick(DialogFragment dialog) {

    }

    @Override
    public void onCancelableAlertDialogPositiveClick(DialogFragment dialog) {
        mQualificationTask.execute();
    }

    @Override
    public void onCancelableAlertDialogNegativeClick(DialogFragment dialog) {
        finish();
    }

    private class QualificationTask extends MyRequest {
        static final String REQUEST_TAG = "QualificationTask";
        private String mUserName;
        private String mComment;
        private long mProviderId;
        private String mMainMenu;
        private String mGarnish;
        private long mRatingValue;

        QualificationTask(String mUserName, String mComment, long mProviderId, String mMainMenu, String mGarnish, long mRatingValue) {
            this.mUserName = mUserName;
            this.mComment = mComment;
            this.mProviderId = mProviderId;
            this.mMainMenu = mMainMenu;
            this.mGarnish = mGarnish;
            this.mRatingValue = mRatingValue;
        }

        @Override
        protected void confirm() {
            final AlertDialog.Builder builder = new AlertDialog.Builder(QualificationActivity.this);
            builder.setIcon(R.mipmap.ic_info_black_36dp);
            builder.setTitle(R.string.dialog_confirmation_title);
            builder.setMessage(R.string.dialog_confirmation_message);
            builder.setPositiveButton(R.string.label_accept, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    execute();
                }
            });
            builder.setNegativeButton(R.string.label_cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mQualificationTask = null;
                }
            });
            AlertDialog confirmDialog = builder.create();
            confirmDialog.setCanceledOnTouchOutside(false);
            confirmDialog.show();
        }

        @Override
        protected void execute() {

            progressDialog = ProgressDialogFragment.newInstance(getApplicationContext());
            progressDialog.show(getFragmentManager(), ProgressDialogFragment.TAG_CLASS);

            if (jsonObjectRequest != null)
                mQueue.cancelAll(REQUEST_TAG);
            QualificationRequest mQualificationRequest = new QualificationRequest(mUserName, mComment, mProviderId, mMainMenu, mGarnish, mRatingValue);
            if (mQualification == null) {
                mQualification = new Qualification();
                mQualification.setCreatedAt(Utils.getToday().getTime());
                mQualification.setStatusSend(Constants.TRANSACTION_NO_SEND);
                mQualification.setUser(mUserName);
                mQualification.setCommentary(mComment);
                mQualification.setProviderId(mProviderId);
                mQualification.setOrder(0);
                mQualification.setMainMenu(mMainMenu);
                mQualification.setGarnish(mGarnish);
                mQualification.setQualificationValue(mRatingValue);
                mQualification.setSendAppAt(Utils.formatDate(new Date(), Constants.DEFAULT_DATE_FORMAT_POSTGRES));
                mQualification.setHttpDetail(String.valueOf(mQualificationRequest.getParams()));
                mQualificationId = QualificationRepository.store(mQualification);

                try {
                    if (mQualificationId <= 0) {
                        Utils.builToast(QualificationActivity.this, getString(R.string.error_save_transaction));
                        progressDialog.dismiss();
                        return;
                    } else {
                        sendBroadcast(new Intent(OrdersObserver.ACTION_LOAD_ORDERS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        sendBroadcast(new Intent(MyCommentsObserver.ACTION_LOAD_MY_COMMENTS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }


            jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                    URLS.QUALIFICATION_URL,
                    mQualificationRequest.getParams(),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            progressDialog.dismiss();
                            handleResponse(response);
                            jsonObjectRequest.cancel();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            String message = NetworkQueue.handleError(error, getApplicationContext());
                            updateQualificationTransaction(mQualification, Constants.TRANSACTION_NO_SEND);
                            jsonObjectRequest.cancel();
                            CancelableAlertDialogFragment errorDialog = CancelableAlertDialogFragment.newInstance(getString(R.string.dialog_error_title),
                                    message,
                                    getString(R.string.label_retry),
                                    getString(R.string.label_send_queue),
                                    R.mipmap.ic_error_black_36dp);
                            errorDialog.show(getFragmentManager(), CancelableAlertDialogFragment.TAG);
                        }
                    });
            jsonObjectRequest.setRetryPolicy(Utils.getRetryPolicy());
            jsonObjectRequest.setTag(REQUEST_TAG);
            NetworkQueue.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest, getApplicationContext());


        }

        @Override
        protected void handleResponse(JSONObject response) {

            String message = null;
            int status = -1;

            if (response == null) {
                updateQualificationTransaction(mQualification, Constants.TRANSACTION_NO_SEND);
                Utils.builToast(QualificationActivity.this, getString(R.string.volley_parse_error));
                finish();
                return;
            }

            Log.i(TAG_CLASS, REQUEST_TAG + " | Response: " + response.toString());

            try {
                if (response.has("status")) status = response.getInt("status");
                if (response.has("message")) message = response.getString("message");

                if (status != Constants.RESPONSE_OK) {
                    updateQualificationTransaction(mQualification, Constants.TRANSACTION_NO_SEND);
                    Utils.builToast(QualificationActivity.this, getString(R.string.volley_default_error));
                    return;
                }

                updateQualificationTransaction(mQualification, Constants.TRANSACTION_SEND);
                mQualification = null;

                AlertDialogFragment alertDialogFragment = AlertDialogFragment.newInstance(getString(R.string.dialog_success_title),
                        message,
                        getString(R.string.label_accept),
                        R.mipmap.ic_done_black_36dp);
                alertDialogFragment.show(getFragmentManager(), AlertDialogFragment.TAG_CLASS);

            } catch (JSONException e) {
                e.printStackTrace();
                Utils.builToast(QualificationActivity.this, getString(R.string.error_parsing_json));
            }
        }

        class QualificationRequest extends RequestObject {
            private final String TAG_CLASS = QualificationRequest.class.getName();

            private String mUsername;
            private String mIndifyCard;
            private String mComment;
            private long mProviderId;
            private String mMainMenu;
            private String mGarnish;
            private long mRatingValue;

            QualificationRequest(String username, String comment, long providerId, String mainMenu, String garnish, long ratingValue) {
                mUsername = username;
                mComment = comment;
                mProviderId = providerId;
                mMainMenu = mainMenu;
                mGarnish = garnish;
                mRatingValue = ratingValue;
                mIndifyCard = AppPreferences.getAppPreferences(QualificationActivity.this).getString(AppPreferences.KEY_IDENTIYFY_CARD, null);

            }

            @Override
            public JSONObject getParams() {
                JSONObject params = new JSONObject();
                try {
                    params.put("username", mUsername);
                    params.put("identify_card", mIndifyCard);
                    params.put("comment", mComment);
                    params.put("provider_id", mProviderId);
                    params.put("main_menu", mMainMenu);
                    params.put("garnish", mGarnish);
                    params.put("qualification_value", String.valueOf(mRatingValue));
                    params.put("send_app_at", Utils.formatDate(new Date(), Constants.DEFAULT_DATE_FORMAT_POSTGRES));
                } catch (JSONException jEX) {
                    Log.w(TAG_CLASS, "Error while create JSONObject " + jEX.getMessage());
                }
                return params;
            }
        }
    }

    public static void updateQualificationTransaction(Qualification mQualification, int status) {
        if (mQualification != null) {
            mQualification.setCreatedAt(Utils.getToday().getTime());
            mQualification.setStatusSend(status);
            QualificationRepository.store(mQualification);
        }
    }


}
