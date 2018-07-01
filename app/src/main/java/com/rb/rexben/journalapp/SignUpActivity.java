package com.rb.rexben.journalapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class SignUpActivity extends AppCompatActivity  {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private FirebaseAuth mAuth;

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */

    private static final String TAG = "SIGN_UP";
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText mUsername;
    private View mProgressView;
    private View mLoginFormView;
    boolean successLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        // Set up the login form.
        mEmailView = findViewById(R.id.email);
        mAuth = FirebaseAuth.getInstance();
        mPasswordView = findViewById(R.id.password);
        mProgressView = findViewById(R.id.progressBar);
        mUsername = findViewById(R.id.username);

//         email = mEmailView.getText().toString();
//
//         password = mPasswordView.getText().toString();
//        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
//                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
//                    attemptLogin();
//                    return true;
//                }
//                return false;
//            }
//        });

        Button mEmailSignInButton = findViewById(R.id.sign_up);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
createUser();            }
        });}
        private void createUser(){
            if(!TextUtils.isEmpty(mEmailView.getText()) && !TextUtils.isEmpty(mPasswordView.getText())){
                String email = mEmailView.getText().toString();
                String password = mPasswordView.getText().toString();
                mAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    final FirebaseUser newUser = mAuth.getCurrentUser();
                                    if(!TextUtils.isEmpty(mUsername.getText())){
                                        String displayName = mUsername.getText().toString();
                                        UserProfileChangeRequest updateUserProfile = new UserProfileChangeRequest.Builder()
                                                .setDisplayName(displayName)
                                                .build();
                                        newUser.updateProfile(updateUserProfile)
                                                .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()) {
                                                            FirebaseUser updatedUser = mAuth.getCurrentUser();
                                                            mProgressView.setVisibility(View.GONE);
                                                            updateUI(updatedUser);
                                                        }
                                                        else{
                                                            Log.e(TAG,"Could not update profile", task.getException());
                                                            Toast.makeText(SignUpActivity.this, "Display name not updated", Toast.LENGTH_SHORT).show();

                                                        }
                                                    }
                                                });
                                    }
                                    else{
                                        mProgressView.setVisibility(View.GONE);
                                        updateUI(newUser);
                                    }

                                }
                                else{
                                    mProgressView.setVisibility(View.GONE);
                                    Log.e(TAG,"Could not create user", task.getException());
//                                    mErrorDisplay.setText(getResources().getString(R.string.signup_error));
                                    return;

                                }
                            }
                        });
            }
        }

    public void gotoSignIn(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }


    private void updateUI(FirebaseUser currentUser) {

            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
            finish();
        }
    }



