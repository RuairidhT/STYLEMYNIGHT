package com.stylemynight.ruairidh.stylemynight;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;


public class LoginScreen extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;

    private Button btnSignIn;
    private TextView tvRegister;
    private TextView tvGuest;
    private TextView forgotPswd;
    private EditText editTextEmail;
    private EditText editTextPassword;

    LoginButton facebookButton;

    private ProgressDialog progressDialog;
    CallbackManager mCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            openSelectBrand();
        }

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();

        if (isLoggedIn) {
            openSelectBrand();
        }


        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = findViewById(R.id.facebookButton);
        loginButton.setReadPermissions("email", "public_profile");


        progressDialog = new ProgressDialog(this);

        tvGuest = findViewById(R.id.continueAsGuest);
        forgotPswd = findViewById(R.id.forgotPassword);
        btnSignIn = findViewById(R.id.signin);
        tvRegister = findViewById(R.id.register);
        facebookButton = findViewById(R.id.facebookButton);

        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);

        tvGuest.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        facebookButton.setOnClickListener(this);
        forgotPswd.setOnClickListener(this);

    }

    public void openSelectBrand() {
        Intent intent = new Intent(this, Core.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.continueAsGuest:
                openSelectBrand();
                break;
            case R.id.signin:
                userLogin();
                break;
            case R.id.register:
                Intent intent = new Intent(this, createAccountActivity.class);
                startActivity(intent);
                break;
            case R.id.facebookButton:
                facebookLogin();
                break;
            case R.id.forgotPassword:
                Intent xIntent = new Intent(this, ForgotPasswordActivity.class);
                startActivity(xIntent);
                break;
            default:
                break;
        }
    }

    public void userLogin() {

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
            return;
        }

        progressDialog.setMessage("Logging in...");
        progressDialog.show();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            openSelectBrand();
                            Toast.makeText(LoginScreen.this, "Login successful", Toast.LENGTH_SHORT).show();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(LoginScreen.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void facebookLogin() {
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "User cancelled login", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void handleFacebookToken(AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        AccessToken accessToken = AccessToken.getCurrentAccessToken();
                        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
                        if (isLoggedIn) {
                            openSelectBrand();
                            Toast.makeText(LoginScreen.this, "Login successful", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginScreen.this, "Couldn't log you in.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
