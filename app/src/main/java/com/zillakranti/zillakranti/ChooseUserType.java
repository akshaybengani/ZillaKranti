package com.zillakranti.zillakranti;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChooseUserType extends AppCompatActivity {

    Button userclick,adminclick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_user_type);

        userclick = (Button)findViewById(R.id.userClick);
        adminclick = (Button)findViewById(R.id.administratorClick);

        userclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseUserType.this,LogInActivity.class);
                //Toast.makeText(getContext(),"Value is "+selectedFromList,Toast.LENGTH_SHORT).show();
                intent.putExtra("argg","user");

                startActivity(intent);
            }
        });
        adminclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChooseUserType.this,LogInActivity.class);
                //Toast.makeText(getContext(),"Value is "+selectedFromList,Toast.LENGTH_SHORT).show();
                intent.putExtra("argg","admin");

                startActivity(intent);

            }
        });

    }


}
