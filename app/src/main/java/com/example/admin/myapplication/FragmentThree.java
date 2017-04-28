package com.example.admin.myapplication;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.myapplication.Adapter.Adapter_memberList;
import com.example.admin.myapplication.HttpRequestProcessor.HttpRequestProcessor;
import com.example.admin.myapplication.HttpRequestProcessor.Response;
import com.example.admin.myapplication.apiConfiguration.ApiConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.admin.myapplication.R.id.btnAddFriend;
import static com.example.admin.myapplication.R.id.lv3;


/**
 * Created by admin on 3/30/2017.
 */

public class FragmentThree extends Fragment {

    private HttpRequestProcessor httpRequestProcessor;
    private Response response;
    private ApiConfiguration apiConfiguration;
    private String baseURL, urlGetApplicationMemberList, jsonStringToPost, urlAddFriendList;
    private boolean success;
    private String message, name, emailID, jsonResponse, memberId, friendId, requestBy;
    private int userID;
    private Member member;
    private ArrayList<Member> memberArrayList;
    private String jsonPostString, jsonResponseString;
    String[] Name;
    String[] emailId;
    Adapter_memberList adapter_memberList;
    private Button btnAddFriend;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_three, container, false);



        ListView lv = (ListView) view.findViewById(R.id.lv3);
        httpRequestProcessor = new HttpRequestProcessor();
        response = new Response();
        apiConfiguration = new ApiConfiguration();

        //Getting base url
        baseURL = apiConfiguration.getApi();
        urlGetApplicationMemberList = baseURL + "ApplicationFriendAPI/GetApplicationMemberList";
        memberArrayList = new ArrayList<>();
        new getMemberListTask().execute();


        adapter_memberList = new Adapter_memberList(getActivity(), memberArrayList);
        lv.setAdapter(adapter_memberList);



        return view;
    }


    public class getMemberListTask extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {


                jsonResponse = httpRequestProcessor.gETRequestProcessor(urlGetApplicationMemberList);
                 return jsonResponse;
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
                        name = object.getString("Name");
                        Log.d("Name", name);
                        emailID = object.getString("EmailId");
                        Log.d("EmailId", emailID);

                        member = new Member(name, emailID);
                        memberArrayList.add(member);

                    }
                    adapter_memberList.notifyDataSetChanged();
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}



