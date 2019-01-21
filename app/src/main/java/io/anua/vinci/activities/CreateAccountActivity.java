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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import io.anua.vinci.R;
import io.anua.vinci.model.User;

public class CreateAccountActivity extends AppCompatActivity {

    /**************************
     * Constants
     *************************/

    public static String CREATE_ACCOUNT_DIALOG = "Creating account....";
    public static String ERROR_ACCOUNT_EXISTS = "This email already has an account";
    public static String ERROR_INVALID_EMAIL = "Enter a valid email address";
    public static String ERROR_INVALID_PASSWORD = "Must be greater than 5 alphanumeric characters";
    public static String ERROR_INVALID_NAME = "Should be at least 3 characters";
    public static String OK_TEXT = "OK";

    /**************************
     * Private Members
     *************************/

    private Button createAccountButton;
    private EditText userName;
    private EditText userEmail;
    private EditText userPassword;
    private ProgressDialog progressDialog;
    private TextView loginPageLink;

    private FirebaseAuth firebaseAuthService;
    private FirebaseFirestore firebaseFirestoreService;

    /**************************
     * LifeCycle Methods
     *************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        firebaseAuthService = FirebaseAuth.getInstance();
        firebaseFirestoreService = FirebaseFirestore.getInstance();

        userName = findViewById(R.id.user_name);
        userEmail = findViewById(R.id.create_user_email);
        userPassword = findViewById(R.id.create_user_password);
        createAccountButton = findViewById(R.id.create_account_button);
        loginPageLink = findViewById(R.id.link_login);
        progressDialog = new ProgressDialog(this, R.style.AppCompatProgressDialogStyle);

        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage(CREATE_ACCOUNT_DIALOG);
                progressDialog.show();
                createUserAccount();
            }
        });

        loginPageLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateAccountActivity.this, LoginActivity.class));
            }
        });
    }

    /**************************
     * Public Methods
     *************************/

    /* creates a User Account using the email and password
     *
     * @method createUserAccount
     * @public
     */
    public void createUserAccount() {
        final String name = userName.getText().toString().trim();
        final String email = userEmail.getText().toString().trim();
        String password = userPassword.getText().toString().trim();

        if (!validateEmailAndPassword(name, email, password)) {
            progressDialog.dismiss();
            return;
        }

        firebaseAuthService.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            buildNewUserObject(name, email);
                            progressDialog.dismiss();
                            startActivity(new Intent(CreateAccountActivity.this, StockDisplayActivity.class));
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        if (e instanceof FirebaseAuthUserCollisionException) {
                            buildAlertDialog(ERROR_ACCOUNT_EXISTS);
                        } else {
                            buildAlertDialog(e.getLocalizedMessage());
                        }
                    }
                });
    }

    /* builds the new user object and posts it to the firestore
     *
     * @method buildNewUserObject
     * @param {@link String} - name
     * @param {@link String} - email
     * @public
     */
    public void buildNewUserObject(String name, String email) {
        String userID = "";
        FirebaseUser firebaseUser = firebaseAuthService.getCurrentUser();

        if(firebaseUser != null) {
            userID = firebaseUser.getUid();
        }
        User userAccount = new User(name, email, userID, System.currentTimeMillis());
        firebaseFirestoreService.collection("users").document(userID).set(userAccount);
    }

    /* validates entered email, name, and password
     *
     * @method validateEmailAndPassword
     * @param {@link String} - name
     * @param {@link String} - email
     * @param {@link String} - password
     * @returns {@link Boolean}
     * @public
     */
    public boolean validateEmailAndPassword(String name, String email, String password) {
        boolean valid = true;

        if (name.isEmpty() || name.length() < 3) {
            userName.setError(ERROR_INVALID_NAME);
            valid = false;
        } else {
            userName.setError(null);
        }

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
     * @param {@link String} - errorMessage
     * @public
     */
    public void buildAlertDialog(String errorMessage) {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(CreateAccountActivity.this, R.style.AlertDialogCustom);
        dlgAlert.setTitle(errorMessage);
        dlgAlert.setPositiveButton(OK_TEXT, null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }
}
