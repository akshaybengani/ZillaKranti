package com.zillakranti.zillakrantiadmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity {

    // Variable Declarations
    TextView goToLogin;
    Button registerUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    // TODO here we have to use insert function of firebase

        // Declaration or initialization of variable login
        goToLogin = (TextView)findViewById(R.id.textviewLogin);
        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(SignUpActivity.this, LogInActivity.class);
                startActivity(intent1);
            }
        });

        // Declaration and initialization of variable register
        registerUser = (Button)findViewById(R.id.buttonRegister);
        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerMe();
            }

        });





    }

    private void registerMe() {


    }


}
