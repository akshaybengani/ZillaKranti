package com.zillakranti.zillakrantiadmin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TaskData extends AppCompatActivity {

    // TODO Variable Declarations
    DatabaseReference databaseReference;
    TextView textViewShowTaskDesc;
    TextView textViewTaskName;

    // Here we declared a ProgressDialog variable
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_data);

        // TODO onCreate Starts here

        //Here we initialised the progress Dialog
        progressDialog=new ProgressDialog(this);
        // Here we set the logging message in the progress dialog
        progressDialog.setMessage("Loading Data");
        // Here we show the progress Dialog message
        progressDialog.show();

        // Value passed from the previous activity collects here
        Intent intent = getIntent();
        String value = intent.getStringExtra("argg");
        // The value passed from the listview item clicked from previous activity

        textViewTaskName =(TextView)findViewById(R.id.Showtaskmname);
        textViewShowTaskDesc = (TextView)findViewById(R.id.Showtaskdetails);
        // Setting the value in the Task Name
        textViewTaskName.setText(value);

        // Path to the Database entity
        databaseReference = FirebaseDatabase.getInstance().getReference("Tasks/"+value);

        // value event listner to listen the values inside the reference JSON
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Data of the exact node object
                String Desc = dataSnapshot.getValue().toString();
                // Setting the value in the textview
                textViewShowTaskDesc.setText(Desc);
                // Stopped the progress bar
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



    }
    // TODO Function Bodies


}
