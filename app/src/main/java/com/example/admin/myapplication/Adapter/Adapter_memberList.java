package com.example.admin.myapplication.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.myapplication.HttpRequestProcessor.HttpRequestProcessor;
import com.example.admin.myapplication.HttpRequestProcessor.Response;
import com.example.admin.myapplication.Member;
import com.example.admin.myapplication.R;
import com.example.admin.myapplication.apiConfiguration.ApiConfiguration;
import com.example.admin.myapplication.sharedPref.MyPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by admin on 4/27/2017.
 */

public class Adapter_memberList extends BaseAdapter implements View.OnClickListener {

    private HttpRequestProcessor httpRequestProcessor;
    private Response response;
    private ApiConfiguration apiConfiguration;
    private String baseURL, jsonStringToPost, urlAddFriend;
    private boolean success;
    private String jsonResponse, memberId, friendId, requestBy;
    private String jsonPostString, jsonResponseString;
    private String message, name, emailId;
    private int memberID, loggedInUserID;
    private SharedPreferences sharedPreferences;
    private String loggedInId;

    private Context context;
    private Button btnAddFriend;
    private ArrayList<Member> memberArrayList;
    private LayoutInflater inflater;
    //private Button btnAddFriend;

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

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.member, parent, false);

        TextView txt1 = (TextView) convertView.findViewById(R.id.txtName);
        TextView txt2 = (TextView) convertView.findViewById(R.id.txtEmailId);
        btnAddFriend = (Button) convertView.findViewById(R.id.btnAddFriend);

        Member member = memberArrayList.get(position);

        name = member.getName();
        emailId = member.getEmailId();
        int mId = member.getMemberID();
        memberId = String.valueOf(mId);
        //Button btnAdd = member.getBtnAddFriend();

        txt1.setText(name);
        txt2.setText(emailId);

        btnAddFriend.setOnClickListener(this);


        return convertView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAddFriend:
                httpRequestProcessor = new HttpRequestProcessor();
                response = new Response();
                apiConfiguration = new ApiConfiguration();

                //Getting base url
                baseURL = apiConfiguration.getApi();
                urlAddFriend = baseURL + "ApplicationFriendAPI/AddFriendRequest";

                //Getting LoggedIn user Detail from SharedPreference
                sharedPreferences = context.getSharedPreferences(MyPref.Pref_Name, Context.MODE_PRIVATE);
                loggedInId = sharedPreferences.getString(MyPref.LoggedInUserID, null);

                new AddFriendTask(v).execute(memberId, loggedInId);
                //Toast.makeText(context,"Clicked",Toast.LENGTH_LONG).show();
                break;
        }
    }

    public class AddFriendTask extends AsyncTask<String, String, String> {

        private View view;

        public AddFriendTask(View view) {
            this.view = view;
        }


        @Override
        protected String doInBackground(String... params) {

            friendId = params[0];
            Log.e("friendId", friendId);

            loggedInId = params[1];
            Log.e("loggedInUserId", loggedInId);
            //requestBy = params[2];

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("MemberId", loggedInId);
                Log.d("MemberId", loggedInId);
                jsonObject.put("FriendId", friendId);
                Log.d("FriendId", memberId);
                jsonObject.put("RequestBy", loggedInId);
                jsonObject.put("CreatedBy", loggedInId);
                jsonObject.put("ModifiedBy", loggedInId);

                jsonPostString = jsonObject.toString();
                response = httpRequestProcessor.pOSTRequestProcessor(jsonPostString, urlAddFriend);
                jsonResponseString = response.getJsonResponseString();

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonResponseString;
            //jsonResponse = httpRequestProcessor.gETRequestProcessor(urlAddFriendList);
            //return jsonResponse;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("Response String", s);

            try {

                JSONObject jsonObject = new JSONObject(s);
                success = jsonObject.getBoolean("success");
                Log.d("Success", String.valueOf(success));
                message = jsonObject.getString("message");
                Log.d("message", message);


                if (success) {
                    Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_LONG).show();
                    ((Button)view).setText("Request Sent");

                } else {
                    Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
