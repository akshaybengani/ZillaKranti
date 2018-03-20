package com.zillakranti.zillakranti;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import javax.security.auth.login.LoginException;

public class LogInActivity extends AppCompatActivity {

    // This is the static value to check the sign in code is completed or not if the value not match there is an error
    private static final int RC_SIGN_IN = 2;
    // The button user clicks for sign in
    SignInButton buttonSignIn;
    // To access firebase authentication feature
    FirebaseAuth firebaseAuth;

    // Google Sign In client
    // You have to Implement and complile the auth and core data in app/build.gradle so that you can access GoogleSignInClient
    // This is in the latest version of the firebase sdk so remember you will not found this method in any youtube video except one
    GoogleSignInClient mGoogleSignInClient;
    // Here this TAG is to show what happens in the log
    private static final String TAG ="Play";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        // Initialisation of the signIn Button
        buttonSignIn = (SignInButton) findViewById(R.id.sign_in_button);
        // Initialisation of the signUp Button
        Button buttonSignUp = (Button) findViewById(R.id.userSignUp);

        // Here we process the signUp operation
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(LogInActivity.this,SignUpActivity.class);
                startActivity(intent1);
            }
        });

        // Initialisation of the firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

//        // Value passed from the previous activity collects here
//        Intent intent = getIntent();
//        String userType = intent.getStringExtra("argg");
//        // The value passed from the listview item clicked from previous activity


        // this is for if the user is already logged in you can bypass the Google sign IN procedure
        if(firebaseAuth.getCurrentUser()!=null)
        {
            //Start Profile Activity
            Intent intent1 = new Intent(LogInActivity.this,MainActivity.class);
            // The next two lines create a blank task and acts as when back button pressed the application closes
            // Insteed of going back to the previous activity
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            // End of back button pressed material
            // To start a new intent
            startActivity(intent1);
        }

        //These are the Google Sign In options Window
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Initialisation of the mGoogleSignInClient variable remember it takes the Google Sign in option variable
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);

        // Here when the user presses the button
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                signIn();
            }
        });
// Finish ----------


    }


    // This function calls when the user press the button
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    // This is the sign in API of google You just have to copy and paste it
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            Toast.makeText(LogInActivity.this,"Authentication done",Toast.LENGTH_SHORT).show();
                            FirebaseUser user = firebaseAuth.getCurrentUser();

                            // updateUI Now Home Activity will start
                            Intent intent = new Intent(LogInActivity.this,MainActivity.class);
                            // The next two lines create a blank task and acts as when back button pressed the application closes
                            // Insteed of going back to the previous activity
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            // End of back button pressed material
                            // To start a new intent
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            // Auth Fail
                            Toast.makeText(LogInActivity.this,"Authentication Failure",Toast.LENGTH_SHORT).show();
                            // Cannot update UI because there is an authentication error
                            //updateUI(null);
                        }


                    }
                });
    }

    // This is also part of the Google Sign In API.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
                // ...
            }
        }
    }


}
