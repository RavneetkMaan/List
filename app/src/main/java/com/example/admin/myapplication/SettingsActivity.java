package com.example.admin.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by admin on 4/20/2017.
 */

public class SettingsActivity extends AppCompatActivity{

    private ImageView profilePhoto;
    private TextView ProfileSet,Privacy,Logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        profilePhoto= (ImageView) findViewById(R.id.profilePhoto);
        ProfileSet= (TextView) findViewById(R.id.ProfileSet);
        Privacy= (TextView) findViewById(R.id.Privacy);
        Logout= (TextView) findViewById(R.id.Logout);

        ProfileSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SettingsActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });


        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1=new Intent(SettingsActivity.this,LogoutActivity.class);
                startActivity(intent1);

            }
        });
    }

}
