package com.zillakranti.zillakranti;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    // Defined Required variables
    ListView listView;
    DatabaseReference databaseReference;
    ArrayList<String> list = new ArrayList<>();
    ArrayAdapter<String> adapter;
    private FirebaseAuth firebaseAuth;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        // TODO This is the onCreate for this fragement

//        String MealTime = Intent().getExtras().getString("Meal Time");
//        setTitle(MealTime);


        listView = (ListView) view.findViewById(R.id.mylist);
        firebaseAuth = FirebaseAuth.getInstance();

        adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_dropdown_item_1line,list);
        listView.setAdapter(adapter);

        // Access Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference("Tasks");

        listDataView();
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent Q = new Intent(HomeFragment.this,TaskData.class);
//                Q.putExtra("argg",position);
//                startActivity(Q);
//            }
//        });


        return view;
    }
    // TODO Function Bodies can be start from here

    public void listDataView()
    {
        // TODO listDataView Started


        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                String value = String.valueOf(dataSnapshot.getKey());
                list.add(value);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {



            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                list.remove(value);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        // Function ends -----------
    }


}
