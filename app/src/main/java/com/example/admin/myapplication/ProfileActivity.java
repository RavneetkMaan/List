package com.example.admin.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity {

    private ImageView profilephoto;
    private EditText name,phone,email,address;
    private FloatingActionButton fab;
    private Button btnDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profilephoto= (ImageView) findViewById(R.id.profilePhoto);
        name=(EditText)findViewById(R.id.edtName);
        phone=(EditText)findViewById(R.id.edtPhone);
        email=(EditText)findViewById(R.id.edtEmail);
        address=(EditText)findViewById(R.id.edtCity);
        fab=(FloatingActionButton)findViewById(R.id.fabCamera);
        btnDone= (Button) findViewById(R.id.btnDone);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivity(intent);
            }
        });

        profilephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(Intent.ACTION_VIEW,Uri.parse("https//www.google.com"));
                startActivity(intent1);
            }
        });
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ProfileActivity.this,ChatListActivity.class);
                startActivity(intent);
            }
        });
    }
}
