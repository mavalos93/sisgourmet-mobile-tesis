package tesis.com.py.sisgourmetmobile.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.view.View;

import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.entities.Users;
import tesis.com.py.sisgourmetmobile.repositories.UsersRepository;
import tesis.com.py.sisgourmetmobile.utils.AppPreferences;
import tesis.com.py.sisgourmetmobile.utils.DialogClass;


public class LoginActivity extends AppCompatActivity {
    private AppCompatEditText userNameInputText;
    private AppCompatEditText passwordInputText;
    private AppCompatButton loginButton;
    private Users controlUser;
    private AlertDialog.Builder alertDialog;
    private FloatingActionButton inputUserButton;


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

    private void setupView() {
        userNameInputText = (AppCompatEditText) findViewById(R.id.loginActivity_username_input_text);
        passwordInputText = (AppCompatEditText) findViewById(R.id.loginActivity_password_input_text);
        loginButton = (AppCompatButton) findViewById(R.id.id_login_button);
        inputUserButton = (FloatingActionButton) findViewById(R.id.fab_login_button);
        inputUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, FormActivity.class));
            }
        });


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

        if (TextUtils.isEmpty(userNameString)) {
            userNameInputText.setError(getString(R.string.error_username_name_register_required));
            focusView = userNameInputText;
            focusView.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(userPasswordString)) {
            passwordInputText.setError(getString(R.string.error_password_register_required));
            focusView = passwordInputText;
            focusView.requestFocus();
            return;
        }

        controlUser = UsersRepository.loginControlQuery(userNameString, userPasswordString);
        if (controlUser != null) {
            AppPreferences.getAppPreferences(this).edit().putBoolean(AppPreferences.KEY_PREFERENCE_LOGGED_IN, true).apply();
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            alertDialog = DialogClass.createSimpleDialog(this, getString(R.string.login_title), getString(R.string.error_user_not_exist), R.mipmap.ic_error_black_36dp);
            alertDialog.setPositiveButton(getString(R.string.label_accept), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertDialog.create();
            alertDialog.show();
        }
    }


}
