package tesis.com.py.sisgourmetmobile.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.entities.Users;
import tesis.com.py.sisgourmetmobile.repositories.UsersRepository;
import tesis.com.py.sisgourmetmobile.utils.DialogClass;
import tesis.com.py.sisgourmetmobile.utils.Utils;

public class ResetPassActivity extends AppCompatActivity {

    private String username;
    private String currentPasword;
    private String identyfyCard;
    private String newPassword;
    private String passwordConfirm;
    private AppCompatEditText usernameInputText;
    private AppCompatEditText currentPasswordInputText;
    private AppCompatEditText identifyCardInputText;
    private AppCompatEditText newPasswordInputText;
    private AppCompatEditText passwordConfirmInputText;
    private AppCompatButton checkUserButton;
    private Users userQuery;
    private LinearLayout newFormPassLinearLayout;
    private AlertDialog.Builder confirmDialog;
    private AlertDialog.Builder errorDialog;
    private AppCompatButton acceptButton;
    private AppCompatButton cancelButton;
    private boolean checkData = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_reset_pass);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        usernameInputText = (AppCompatEditText) findViewById(R.id.edit_person_input_edit_Text);
        currentPasswordInputText = (AppCompatEditText) findViewById(R.id.reset_password_editText);
        identifyCardInputText = (AppCompatEditText) findViewById(R.id.reset_identify_card_editText);
        newPasswordInputText = (AppCompatEditText) findViewById(R.id.reset_new_password_editText);
        passwordConfirmInputText = (AppCompatEditText) findViewById(R.id.reset_confirm_password_editText);
        newFormPassLinearLayout = (LinearLayout) findViewById(R.id.new_pass_linear_layout);
        checkUserButton = (AppCompatButton) findViewById(R.id.id_check_user_button);
        acceptButton = (AppCompatButton) findViewById(R.id.ok_reset_person_button);
        cancelButton = (AppCompatButton) findViewById(R.id.cancel_reset_person_button);

        checkUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateLocalFields();
            }
        });
        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkData) {
                    validateNewData();
                } else {
                    Utils.builToast(ResetPassActivity.this, "Debe verificar usuario");
                }
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void validateLocalFields() {
        username = usernameInputText.getText().toString().trim();
        currentPasword = currentPasswordInputText.getText().toString().trim();
        identyfyCard = identifyCardInputText.getText().toString().trim();
        View focusView = null;

        if (TextUtils.isEmpty(username)) {
            usernameInputText.setError(getString(R.string.error_username_name_reset_required));
            focusView = usernameInputText;
            focusView.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(currentPasword)) {
            currentPasswordInputText.setError(getString(R.string.error_password_reset_required));
            focusView = currentPasswordInputText;
            focusView.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(identyfyCard)) {
            identifyCardInputText.setError(getString(R.string.error_password_reset_identifyCard));
            focusView = identifyCardInputText;
            focusView.requestFocus();
            return;
        }
        userQuery = UsersRepository.controlUser(username, currentPasword, identyfyCard);
        if (userQuery != null) {
            newFormPassLinearLayout.setVisibility(View.VISIBLE);
            checkData = true;
        } else {
            errorDialog = DialogClass.createSimpleDialog(this, getString(R.string.reset_password_menu_description),
                    getString(R.string.error_user_not_exist),
                    R.mipmap.ic_error_black_36dp);
            errorDialog.create();
            errorDialog.show();
        }
    }


    private void validateNewData() {
        newPassword = newPasswordInputText.getText().toString().trim();
        passwordConfirm = passwordConfirmInputText.getText().toString().trim();
        View focusView = null;
        if (TextUtils.isEmpty(newPassword)) {
            newPasswordInputText.setError(getString(R.string.error_new_password_reset_required));
            focusView = newPasswordInputText;
            focusView.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(passwordConfirm)) {
            passwordConfirmInputText.setError(getString(R.string.error_password_confirm_required));
            focusView = passwordConfirmInputText;
            focusView.requestFocus();
            return;
        }

        if (!newPassword.equals(passwordConfirm)) {
            errorDialog = DialogClass.createSimpleDialog(this, getString(R.string.reset_password_menu_description),
                    getString(R.string.error_not_equal_pass),
                    R.mipmap.ic_error_black_36dp);
            clearDataFields();
            errorDialog.create();
            errorDialog.show();
        } else {
            try {
                userQuery.setUserName(username);
                userQuery.setPassword(newPassword);
                UsersRepository.store(userQuery);
                confirmDialog = DialogClass.createSimpleDialog(this, getString(R.string.reset_password_menu_description),
                        getString(R.string.label_transaction_done),
                        R.mipmap.ic_done_black_36dp);
                confirmDialog.setPositiveButton(getString(R.string.label_accept), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                confirmDialog.create();
                confirmDialog.show();
            } catch (Exception ex) {
                ex.printStackTrace();
                Utils.builToast(this, "Error en la operacion");
            }
        }
    }

    private void clearDataFields() {
        newPasswordInputText.getText().clear();
        passwordConfirmInputText.getText().clear();
    }
}
