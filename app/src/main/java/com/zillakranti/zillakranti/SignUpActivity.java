package com.zillakranti.zillakranti;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private EditText editTextFullName,editTextEmail,editTextPassword,editTextPostalAddress,editTextLandmark;
    private RadioButton radioButtonMale,radioButtonFemale,radioButtonOther;
    private RadioGroup radioGroup;
    private Button buttonRegister;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    //Wih this refernece we can store data to firebase database
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editTextFullName = (EditText)findViewById(R.id.editTextFullName);
        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        editTextPostalAddress = (EditText)findViewById(R.id.editTextAddress);
        editTextLandmark = (EditText)findViewById(R.id.editTextAddressLandmark);
        radioButtonMale = (RadioButton) findViewById(R.id.radioButtonMale);
        radioButtonFemale = (RadioButton) findViewById(R.id.radioButtonFemale);
        radioButtonOther = (RadioButton) findViewById(R.id.radioButtonOther);
        radioGroup = (RadioGroup)findViewById(R.id.radioGroupGender);
        buttonRegister = (Button)findViewById(R.id.buttonRegister);

        firebaseAuth = FirebaseAuth.getInstance();
        // here we initialise the object of firebase user
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        // here we initialise the object of firebase database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("UserData/");


        if(firebaseAuth.getCurrentUser()!=null)
        {
            //Start Profile Activity
            Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
            startActivity(intent);
        }
        //Here we created an instance of the progress Dialog
        progressDialog =new ProgressDialog(this);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
}

    private void registerUser()
    {
        String fullName=editTextFullName.getText().toString().trim();
        String email=editTextEmail.getText().toString().trim();
        String password=editTextPassword.getText().toString().trim();
        String postalAddress = editTextPostalAddress.getText().toString().trim();
        String landmark =editTextLandmark.getText().toString().trim();
        String sex = checkGender().toString().trim();

        //Here we check that the user filled the edittext or not
        if(TextUtils.isEmpty(fullName))
        {
            //Email is empty
            Toast.makeText(SignUpActivity.this,"Please Enter Your email Address",Toast.LENGTH_SHORT).show();
            //To stop the function execution
            return;
        }
        if (TextUtils.isEmpty(email))
        {
            //Password is empty
            Toast.makeText(SignUpActivity.this,"Please Enter Your Password",Toast.LENGTH_SHORT).show();
            //To stop the function execution
            return;
        }
        if(TextUtils.isEmpty(password))
        {
            //Email is empty
            Toast.makeText(SignUpActivity.this,"Please Enter Your email Address",Toast.LENGTH_SHORT).show();
            //To stop the function execution
            return;
        }
        if(TextUtils.isEmpty(postalAddress))
        {
            //Email is empty
            Toast.makeText(SignUpActivity.this,"Please Enter Your email Address",Toast.LENGTH_SHORT).show();
            //To stop the function execution
            return;
        }
        if(TextUtils.isEmpty(landmark))
        {
            //Email is empty
            Toast.makeText(SignUpActivity.this,"Please Enter Your email Address",Toast.LENGTH_SHORT).show();
            //To stop the function execution
            return;
        }
        // Now at this stage user has filled the email and password and submitted the form
        // so now it takes time for the user to get to the server
        // so we will use ProgressDialog

        //Here we use a progress bar which shows like a loading function
        progressDialog.setMessage("Registering User... \n Please Wait.");
        //This is for to show the progress Dialog on the screen
        progressDialog.show();

        // todo this is only for email and passwords
        // This method is used to send email and password data which we stored in the string now we sent it to the server
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                // this listner executes this method when the registration is completed
                // with a task object to check weather the registration is successful or not

                if(task.isSuccessful())
                {
                    // task is successful

                    // here we can display a message for successfull registration
                    Toast.makeText(SignUpActivity.this,"Congratulations You have registered Successfully",Toast.LENGTH_SHORT).show();

                    // here we stopped the dialog bar roatating screen
 //                   progressDialog.dismiss();


                    //This is used to finish the current Activity
//                    finish();

                    //Now we will start Profile Activity as we are in onCompleteListner so we cannot use the Activity name we have to use getApplicationContext
  //                  Intent intent= new Intent(getApplicationContext(),MainActivity.class);
   //                 startActivity(intent);

                }
                else
                {
                    // task unsuccessfull

                    //here we can displaly a message for unsuccessfull registration
                    Toast.makeText(SignUpActivity.this,"Sorry... Registration Unsuccessfull please try again...",Toast.LENGTH_SHORT).show();

                    // here we stopped the dialog bar roatating screen
     //               progressDialog.dismiss();

                }


            }
        });

        // todo for database entry

        // Here we created the object of our UserInformation Class
        UserInformation userInformation= new UserInformation(fullName,postalAddress,landmark,sex);

        //This takes out the unique id of the current user
        FirebaseUser user= firebaseAuth.getCurrentUser();

        // Here we send data to the firebase by a unique id of the user by the
        // special function .getUid through user id in our database
        databaseReference.child(user.getUid()).setValue(userInformation).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful())
                {
                    // here we stopped the dialog bar roatating screen
                    progressDialog.dismiss();
                    //This is used to finish the current Activity
                    finish();
                    //Now we will start Profile Activity as we are in onCompleteListner so we cannot use the Activity name we have to use getApplicationContext
                    Intent intent= new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(intent);
                }
                else
                {
                    // task unsuccessfull
                    //here we can displaly a message for unsuccessfull registration
                    Toast.makeText(SignUpActivity.this,"Sorry... Registration Unsuccessfull please try again...",Toast.LENGTH_SHORT).show();
                    // here we stopped the dialog bar roatating screen
                    progressDialog.dismiss();

                }

            }
        });


        // Now we show the data saved info
        Toast.makeText(this, "Information Saved",Toast.LENGTH_SHORT).show();

        // todo finished database entries



    }

    private String checkGender() {
    String sex="";
    if (radioButtonMale.isSelected())
    {
        sex="Male";
    }
    if (radioButtonFemale.isSelected())
    {
     sex="Female";
    }
    if(radioButtonOther.isSelected())
    {
        sex="Other";
    }
    return sex;

    }


}
