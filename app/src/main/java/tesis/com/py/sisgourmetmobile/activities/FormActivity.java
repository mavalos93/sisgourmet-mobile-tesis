package tesis.com.py.sisgourmetmobile.activities;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.dialogs.AlertDialogFragment;
import tesis.com.py.sisgourmetmobile.entities.Users;
import tesis.com.py.sisgourmetmobile.repositories.UsersRepository;
import tesis.com.py.sisgourmetmobile.utils.DialogClass;

public class FormActivity extends AppCompatActivity implements AlertDialogFragment.AlertDialogFragmentListener {

    private AppCompatEditText usernameInputText;
    private AppCompatEditText passwordInputText;
    private AppCompatEditText identifyCardInputText;
    private AppCompatButton acceptButton;
    private AppCompatButton cancelButton;
    private Users userControlQuery, usersClass;
    private AlertDialog.Builder alertDialog;
    private final String TAG_CLASS = FormActivity.class.getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_activity_form);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        usernameInputText = (AppCompatEditText) findViewById(R.id.edit_person_input_edit_Text);
        passwordInputText = (AppCompatEditText) findViewById(R.id.register_password_editText);
        identifyCardInputText = (AppCompatEditText) findViewById(R.id.register_identify_card_editText);
        acceptButton = (AppCompatButton) findViewById(R.id.ok_register_person_button);
        cancelButton = (AppCompatButton) findViewById(R.id.cancel_register_person_button);
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateFields();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


    public void validateFields() {
        String usernameString;
        String passwordString;
        String identyfiCardString;
        View focusView = null;

        usernameString = usernameInputText.getText().toString().trim();
        passwordString = passwordInputText.getText().toString().trim();
        identyfiCardString = identifyCardInputText.getText().toString().trim();

        if (TextUtils.isEmpty(usernameString)) {
            usernameInputText.setError(getString(R.string.error_username_name_register_required));
            focusView = usernameInputText;
            focusView.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(passwordString)) {
            passwordInputText.setError(getString(R.string.error_password_register_required));
            focusView = passwordInputText;
            focusView.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(identyfiCardString)) {
            identifyCardInputText.setError(getString(R.string.error_password_register_identifyCard));
            focusView = identifyCardInputText;
            focusView.requestFocus();
            return;
        }

        userControlQuery = UsersRepository.controlUser(usernameString, passwordString, identyfiCardString);
        usersClass = new Users();
        usersClass.setUserName(usernameString);
        usersClass.setPassword(passwordString);
        usersClass.setIdentifyCardNumber(identyfiCardString);
        if (userControlQuery == null) {
            UsersRepository.store(usersClass);
            AlertDialogFragment alertDialogFragment = AlertDialogFragment.newInstance(getString(R.string.register_user_title),
                    getString(R.string.label_transaction_done),
                    getString(R.string.label_accept),
                    R.mipmap.ic_done_black_36dp);
            alertDialogFragment.show((this).getFragmentManager(), AlertDialogFragment.TAG_CLASS);
        } else {
            alertDialog = DialogClass.createSimpleDialog(this, getString(R.string.register_user_title),
                    getString(R.string.error_user_alredy_exist),
                    R.mipmap.ic_error_black_36dp);
            alertDialog.setPositiveButton(getString(R.string.label_accept), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertDialog.create();
            alertDialog.show();
        }

    }

    @Override
    public void onAlertDialogPositiveClick(DialogFragment dialog) {
        finish();
    }
}
