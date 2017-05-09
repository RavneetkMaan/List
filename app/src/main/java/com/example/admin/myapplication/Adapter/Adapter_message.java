package com.example.admin.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.myapplication.Message;
import com.example.admin.myapplication.R;

import java.util.ArrayList;

/**
 * Created by admin on 5/3/2017.
 */

public class Adapter_message extends BaseAdapter {

    String name, messages;
    Boolean isLoggedInUser;
    private LayoutInflater inflater;
    ArrayList<Message> messageArrayList;
    private Context context;
    private TextView txtName, txtMessage;
    private ImageView imgUser;
    private Message message;

    public Adapter_message(Context context, ArrayList<Message> messageArrayList) {
        this.messageArrayList = messageArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return messageArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return messageArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Message m = messageArrayList.get(position);


        name = m.getName();
        messages = m.getMessages();
        isLoggedInUser = m.getLoggedInUser();

        //Right Align the messages of loggedInUser
        if (isLoggedInUser) {

            convertView = inflater.inflate(R.layout.chat_message_right, parent, false);
            txtName = (TextView) convertView.findViewById(R.id.txtName);
            txtMessage = (TextView) convertView.findViewById(R.id.txtMessage);
            imgUser = (ImageView) convertView.findViewById(R.id.imageView1);
            if (messageArrayList.size() != 0) {
                message = messageArrayList.get(position);
                txtName.setText(message.getName());
                txtMessage.setText(message.getMessages());
                imgUser.setImageResource(R.drawable.profile);
            }


        }
        //Left Align the messages of loggedInUser
        else {
            convertView = inflater.inflate(R.layout.chat_message_left, parent, false);
            txtName = (TextView) convertView.findViewById(R.id.txtName);
            txtMessage = (TextView) convertView.findViewById(R.id.txtMessage);
            imgUser = (ImageView) convertView.findViewById(R.id.imageView2);
            if (messageArrayList.size() != 0) {

                message = messageArrayList.get(position);
                txtName.setText(message.getName());
                txtMessage.setText(message.getMessages());
                imgUser.setImageResource(R.drawable.profile);
            }
        }
        return convertView;
    }
}
