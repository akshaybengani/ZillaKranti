package com.zillakranti.zillakranti;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TaskAdd extends Fragment {

    // todo Variable Declaration
    EditText editTextTaskName;
    EditText editTextTaskDesc;
    Button buttonAddMenu;

    DatabaseReference databaseReference;

    private FirebaseAuth firebaseAuth;
    // TODO End of Variable Declaration




    public TaskAdd() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_task_add, container, false);

        // TODO Modified onCreate Starts here
        editTextTaskName = (EditText) view.findViewById(R.id.Taskname);
        editTextTaskDesc = (EditText) view.findViewById(R.id.TaskDesc);
        buttonAddMenu = (Button) view.findViewById(R.id.Submit);

        firebaseAuth = FirebaseAuth.getInstance();


        // Access Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("Tasks");

        buttonAddMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveInfo();
            }
        });

        // OnCreate Ends here ----------
        // TODO Modified onCreate Ends here
        return  view;
    }

    // TODO Function Definations starts here


    public void saveInfo()
    {


        String TaskName = "";
        TaskName = editTextTaskName.getText().toString().trim();
        String TaskDesc = "";
        TaskDesc = editTextTaskDesc.getText().toString().trim();
        //FirebaseUser user = firebaseAuth.getCurrentUser();

        databaseReference.child(TaskName).setValue(TaskDesc);

        Toast.makeText(getContext(),"Value Added",Toast.LENGTH_SHORT).show();

    }


}
