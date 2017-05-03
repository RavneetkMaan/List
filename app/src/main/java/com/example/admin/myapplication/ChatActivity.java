package com.example.admin.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.zip.Inflater;

/**
 * Created by admin on 4/21/2017.
 */

public class ChatActivity extends AppCompatActivity {

    private TextView txtFullName,txtBack,txtStatus;
    private ImageView imageView3;
    private FloatingActionButton fab;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        txtBack= (TextView) findViewById(R.id.txtBack);
        txtFullName= (TextView) findViewById(R.id.txtFullName);
        //txtStatus= (TextView) findViewById(R.id.txtBack);
        imageView3= (ImageView) findViewById(R.id.imageView3);



        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popup=new PopupMenu(context,fab);
                popup.getMenu().add("Contacts");
                popup.getMenu().add("Camera");
                popup.getMenu().add("Gallery");

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId())
                        {
                            case R.id.contacts:
                                Intent intent=new Intent(ChatActivity.this,FragmentThree.class);
                                startActivity(intent);
                                return true;
                            case R.id.camera:
                                Intent intent1=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivity(intent1);
                                return true;
                            case R.id.gallery:
                                Intent intent4=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivity(intent4);
                                return true;
                            default:
                                return false;
                        }
                    }
                });*/
    }
}
