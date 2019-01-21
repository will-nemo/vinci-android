package io.anua.vinci.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.ProgressDialog;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import io.anua.vinci.R;

public class LoginActivity extends AppCompatActivity {

    /**************************
     * Constants
     *************************/

    public static String LOGIN_ACCOUNT_DIALOG = "Signing in....";
    public static String ERROR_INVALID_EMAIL = "Enter a valid email address";
    public static String ERROR_INVALID_PASSWORD = "Must be greater than 5 alphanumeric characters";
    public static String ERROR_INVALID_PASSWORD_REST = "Password is incorrect";
    public static String ERROR_EMAIL_NOT_EXIST = "User with this email does not exist";
    public static String OK_TEXT = "OK";

    /**************************
     * Private Members
     *************************/

    private Button loginAccountButton;
    private EditText userEmail;
    private EditText userPassword;
    private ProgressDialog progressDialog;
    private TextView createPageLink;

    private FirebaseAuth firebaseAuthService;

    /**************************
     * LifeCycle Methods
     *************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuthService = FirebaseAuth.getInstance();

        userEmail = findViewById(R.id.login_user_email);
        userPassword = findViewById(R.id.login_user_password);
        loginAccountButton = findViewById(R.id.login_account_button);
        createPageLink = findViewById(R.id.link_signup);
        progressDialog = new ProgressDialog(this, R.style.AppCompatProgressDialogStyle);

        loginAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage(LOGIN_ACCOUNT_DIALOG);
                progressDialog.show();
                loginUserAccount();
            }
        });

        createPageLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, CreateAccountActivity.class));
            }
        });
    }

    /* called in the Lifecycle after onCreate
     * Checks to see if this is current User and if so automatically
     * logs them in
     *
     * @method onStart
     * @public
     */
    @Override
    protected void onStart() {
        super.onStart();
        if(firebaseAuthService.getCurrentUser()!= null) {
            startActivity( new Intent(LoginActivity.this, StockDisplayActivity.class));
            finish();
        }
    }

    /**************************
     * Public Methods
     *************************/

    /* logs a User into their account using the email and password if valid
     *
     * @method loginUserAccount
     * @public
     */
    public void loginUserAccount() {
        String email = userEmail.getText().toString().trim();
        String password = userPassword.getText().toString().trim();

        if (!validateEmailAndPassword(email, password)) {
            progressDialog.dismiss();
            return;
        }

        firebaseAuthService.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            startActivity(new Intent(LoginActivity.this, StockDisplayActivity.class));
                            finish();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        if (e instanceof FirebaseAuthInvalidCredentialsException) {
                            buildAlertDialog(ERROR_INVALID_PASSWORD_REST);
                        } else if (e instanceof FirebaseAuthInvalidUserException) {
                            buildAlertDialog(ERROR_EMAIL_NOT_EXIST);
                        } else {
                            buildAlertDialog(e.getLocalizedMessage());
                        }
                    }
                });
    }

    /* validates entered email and password
     *
     * @method validateEmailAndPassword
     * @param {@link String} - email
     * @param {@link String} - password
     * @returns {@link Boolean}
     * @public
     */
    public boolean validateEmailAndPassword(String email, String password) {
        boolean valid = true;

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            userEmail.setError(ERROR_INVALID_EMAIL);
            valid = false;
        } else {
            userEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            userPassword.setError(ERROR_INVALID_PASSWORD);
            valid = false;
        } else {
            userPassword.setError(null);
        }

        return valid;
    }

    /* builds alert dialog if failure while logging in
     *
     * @method buildAlertDialog
     * @param {@link Exception} - e
     * @public
     */
    public void buildAlertDialog(String errorMessage) {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(LoginActivity.this, R.style.AlertDialogCustom);
        dlgAlert.setTitle(errorMessage);
        dlgAlert.setPositiveButton(OK_TEXT, null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }
}
