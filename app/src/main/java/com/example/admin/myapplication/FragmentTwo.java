package com.example.admin.myapplication;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by admin on 3/30/2017.
 */

public class FragmentTwo extends Fragment {

    //datasource
    String[] name={"Name","Name","Name","Name","Name"};
    String[] desc={"Status","Status","Status","Status","Status"};
    int[] image={R.drawable.chrysanthemum,R.drawable.hydrangeas,R.drawable.tulips,R.drawable.penguins,R.drawable.koala};


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab_two,container,false);
    }

    //onActivityCreated

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListView lv2=(ListView)getActivity().findViewById(R.id.lv2);
        MyAdapter dd2=new MyAdapter();
        lv2.setAdapter(dd2);
    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return name.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater= (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView=inflater.inflate(R.layout.requests,parent,false);

            TextView txt1= (TextView) convertView.findViewById(R.id.txtName);
            TextView txt2= (TextView) convertView.findViewById(R.id.txtDesc);
            ImageView imageView= (ImageView) convertView.findViewById(R.id.imageView1);

            txt1.setText(name[position]);
            txt2.setText(desc[position]);
            imageView.setImageResource(image[position]);
            return convertView;
        }
    }
}
