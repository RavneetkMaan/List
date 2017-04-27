package com.example.admin.myapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.admin.myapplication.Member;
import com.example.admin.myapplication.R;

import java.util.ArrayList;

/**
 * Created by admin on 4/27/2017.
 */

public class Adapter_memberList extends BaseAdapter {

    private Context context;
    private ArrayList<Member> memberArrayList;
    private LayoutInflater inflater;

    public Adapter_memberList(Context context, ArrayList<Member> memberArrayList) {
        this.context = context;
        this.memberArrayList = memberArrayList;
    }

    @Override
    public int getCount() {
        return memberArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return memberArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=inflater.inflate(R.layout.member,parent,false);

        TextView txt1 = (TextView) convertView.findViewById(R.id.txtName);
        TextView txt2 = (TextView) convertView.findViewById(R.id.txtEmailId);

        Member member=memberArrayList.get(position);

        String name= member.getName();
        String emailId=member.getEmailId();

        txt1.setText(name);
        txt2.setText(emailId);

        return convertView;
    }
}
