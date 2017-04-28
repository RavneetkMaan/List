package com.example.admin.myapplication.Adapter;

import android.content.Context;
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
    private String message;

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

        String name = member.getName();
        String emailId = member.getEmailId();
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

                new AddFriendTask().execute();
                //Toast.makeText(context,"Clicked",Toast.LENGTH_LONG).show();
                break;
        }
    }

    public class AddFriendTask extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {

            memberId = params[0];
            Log.e("MemberId", memberId);
            friendId = params[1];
            requestBy= params[2];

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("MemberId", memberId);
                jsonObject.put("FriendId", friendId);
                jsonObject.put("RequestBy", requestBy);

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
                    JSONArray responseData = jsonObject.getJSONArray("responseData");
                    for (int i = 0; i < responseData.length(); i++) {
                        JSONObject object = responseData.getJSONObject(i);
                        memberId = object.getString("MemberId");
                        Log.d("MemberId", memberId);
                        friendId = object.getString("FriendId");
                        Log.d("FriendId", friendId);
                        requestBy= object.getString("RequestBy");
                        Log.d("RequestBy", requestBy);
                        Toast.makeText(context.getApplicationContext(), "Request sent successfully", Toast.LENGTH_LONG).show();
                        btnAddFriend.setText("SENT");
                    }
                }else{
                    Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
            } catch(JSONException e){
                e.printStackTrace();
            }
        }
    }
}
