package com.example.admin.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.admin.myapplication.FriendList;
import com.example.admin.myapplication.Member;
import com.example.admin.myapplication.R;

import java.util.ArrayList;

/**
 * Created by admin on 4/27/2017.
 */

public class Adapter_friendList extends BaseAdapter {

    private Context context;
    private ArrayList<FriendList> friendListArrayList;
    private LayoutInflater inflater;
    private String memberId;

    public Adapter_friendList(Context context, ArrayList<FriendList> friendListArrayList) {
        this.context = context;
        this.friendListArrayList = friendListArrayList;
    }

    @Override
    public int getCount() {
        return friendListArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return friendListArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.friend_list,parent,false);

        TextView txt1 = (TextView) convertView.findViewById(R.id.txtName);

        FriendList friendList=friendListArrayList.get(position);

        String name= friendList.getName();


        txt1.setText(name);
        int mId = friendList.getMemberId();
        memberId = String.valueOf(mId);

        return convertView;
    }
}
