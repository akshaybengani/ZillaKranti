package com.zillakranti.zillakranti;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {

    Button signOut;
    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);
            // TODO Code starts here

        FirebaseAuth mAuth = null;
        FirebaseAuth.getInstance().addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (FirebaseAuth.getInstance().getCurrentUser()==null)
                {
                    Intent intent = new Intent(UserFragment.this.getContext(),LogInActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getContext(),"User is there somewhere"+FirebaseAuth.getInstance().getCurrentUser().toString().trim(),Toast.LENGTH_SHORT).show();
                }


            }
        });

        signOut = (Button)view.findViewById(R.id.signOutButton);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent1 = new Intent(UserFragment.this.getContext(),LogInActivity.class);
                startActivity(intent1);
            }
        });


    return view;
    }

}
