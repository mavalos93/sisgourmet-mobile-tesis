package tesis.com.py.sisgourmetmobile.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.dialogs.ProgressDialogFragment;
import tesis.com.py.sisgourmetmobile.entities.User;
import tesis.com.py.sisgourmetmobile.network.MyRequest;
import tesis.com.py.sisgourmetmobile.network.NetworkQueue;
import tesis.com.py.sisgourmetmobile.repositories.UserRepository;
import tesis.com.py.sisgourmetmobile.utils.AppPreferences;
import tesis.com.py.sisgourmetmobile.utils.JsonObjectRequest;
import tesis.com.py.sisgourmetmobile.utils.URLS;
import tesis.com.py.sisgourmetmobile.utils.Utils;


public class LoginActivity extends AppCompatActivity {
    private AppCompatEditText userNameInputText;
    private AppCompatEditText passwordInputText;
    private AppCompatButton loginButton;
    private CoordinatorLayout mCoordinatorLayout;
    private LoginTask mLoginTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        boolean isLogged = AppPreferences.getAppPreferences(this).getBoolean(AppPreferences.KEY_PREFERENCE_LOGGED_IN, false);
        if (isLogged) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            AppPreferences.getAppPreferences(this).edit().clear().apply();
            setupView();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_done_menu, menu);
        return true;
    }

    private void setupView() {
        userNameInputText = (AppCompatEditText) findViewById(R.id.loginActivity_username_input_text);
        passwordInputText = (AppCompatEditText) findViewById(R.id.loginActivity_password_input_text);
        loginButton = (AppCompatButton) findViewById(R.id.id_login_button);
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginSession();
            }
        });
    }


    public void loginSession() {
        String userNameString;
        String userPasswordString;
        userNameString = userNameInputText.getText().toString().trim();
        userPasswordString = passwordInputText.getText().toString().trim();
        View focusView = null;
        boolean cancel = false;

        if (TextUtils.isEmpty(userNameString)) {
            userNameInputText.setError(getString(R.string.error_username_name_register_required));
            focusView = userNameInputText;
            focusView.requestFocus();
            cancel = true;
        }

        if (TextUtils.isEmpty(userPasswordString)) {
            passwordInputText.setError(getString(R.string.error_password_register_required));
            focusView = passwordInputText;
            focusView.requestFocus();
            cancel = true;
        }
        if (cancel) {
            focusView.requestFocus();
        } else {
            mLoginTask = new LoginTask(userNameString, userPasswordString);
            mLoginTask.execute();


        }
    }


    private class LoginTask extends MyRequest {
        static final String REQUEST_TAG = "LoginTask";

        private String mUsername;
        private String mPassword;

        LoginTask(String username, String password) {
            mUsername = username;
            mPassword = password;
        }

        @Override
        protected void confirm() {

        }

        @Override
        protected void execute() {
            progressDialog = ProgressDialogFragment.newInstance(getApplicationContext());
            progressDialog.show(getFragmentManager(), ProgressDialogFragment.TAG_CLASS);

            LoginRequest mLoginRequest = new LoginRequest(mUsername, mPassword);
            jsonObjectRequest = new JsonObjectRequest(Request.Method.POST,
                    URLS.LOGIN_URL,
                    mLoginRequest.getParams(),
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            progressDialog.dismiss();
                            handleResponse(response);
                            mLoginTask = null;
                            jsonObjectRequest.cancel();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            String message = Utils.volleyErrorHandler(error, LoginActivity.this);
                            jsonObjectRequest.cancel();
                            Utils.getSnackBar(mCoordinatorLayout, message);
                        }
                    });

            jsonObjectRequest.setRetryPolicy(Utils.getRetryPolicy());
            jsonObjectRequest.setTag(LoginActivity.LoginTask.REQUEST_TAG);
            NetworkQueue.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest, getApplicationContext());
        }

        @Override
        protected void handleResponse(JSONObject response) {
            String message = null;
            String mUsername = "";
            String mIdentifyCard = "";
            String mName = "";
            String mUserLastName = "";
            String mCurrentAmount = "";
            int status = -1;

            if (response == null) {
                Utils.getSnackBar(mCoordinatorLayout, getString(R.string.volley_parse_error));
                return;
            }

            Log.d("TAG", "LOGIN_RESPONSE: " + response.toString());
            try {
                if (response.has("status")) status = response.getInt("status");
                if (response.has("message")) message = response.getString("message");
                if (status != 1) {
                    Utils.getSnackBar(mCoordinatorLayout, message);
                } else {
                    if (response.has("usuario")) mUsername = response.getString("usuario");
                    if (response.has("identifyCard"))
                        mIdentifyCard = response.getString("identifyCard");
                    if (response.has("name")) mName = response.getString("name");
                    if (response.has("lastName")) mUserLastName = response.getString("lastName");
                    if (response.has("currentAmount"))
                        mCurrentAmount = response.getString("currentAmount");

                    long userId = saveUserData(mName, mUserLastName, mCurrentAmount, mUsername, mIdentifyCard);
                    if (userId > 0) {
                        AppPreferences.getAppPreferences(LoginActivity.this).edit().putBoolean(AppPreferences.KEY_PREFERENCE_LOGGED_IN, true).apply();
                        AppPreferences.getAppPreferences(LoginActivity.this).edit().putLong(AppPreferences.KEY_USER_ID, userId).apply();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    } else {
                        Utils.builToast(LoginActivity.this, getString(R.string.error_save_user_data));
                        return;
                    }

                }

            } catch (JSONException e) {
                e.printStackTrace();
                Utils.getSnackBar(mCoordinatorLayout, getString(R.string.error_parsing_json));
            } catch (Exception ex) {
                ex.printStackTrace();
                Utils.getSnackBar(mCoordinatorLayout, getString(R.string.volley_default_error));
            }

        }

        private long saveUserData(String name, String lastName, String currentAmount, String mUsername, String identifyCard) {
            User user = new User();
            user.setName(name);
            user.setIdentifyCard(identifyCard);
            user.setLastName(lastName);
            user.setCurrentAmount(currentAmount);
            user.setUserName(mUsername);
            return UserRepository.store(user);
        }

        class LoginRequest extends RequestObject {

            private final String TAG_CLASS = LoginActivity.LoginTask.LoginRequest.class.getName();

            private String mUserName;
            private String mPassword;


            LoginRequest(String username, String password) {
                mUserName = username;
                mPassword = password;

            }

            @Override
            public JSONObject getParams() {
                JSONObject params = new JSONObject();
                try {
                    params.put("usuario", mUserName);
                    params.put("password", mPassword);

                } catch (JSONException jEX) {
                    Log.w(TAG_CLASS, "Error while create JSONObject " + jEX.getMessage());
                }
                return params;
            }
        }
    }


}
