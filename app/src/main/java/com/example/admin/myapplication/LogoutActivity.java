package com.example.admin.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LogoutActivity extends AppCompatActivity {

    private Button btnLogOut,btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        btnLogOut= (Button) findViewById(R.id.btnLogOut);
        btnCancel= (Button) findViewById(R.id.btnCancel);

        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LogoutActivity.this,MainActivity.class);
                startActivity(intent);
                Toast.makeText(LogoutActivity.this,"User Logged Out",Toast.LENGTH_LONG).show();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(LogoutActivity.this,SettingsActivity.class);
                startActivity(intent1);
            }
        });

    }
}
