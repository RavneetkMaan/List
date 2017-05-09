package com.example.admin.myapplication;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.myapplication.Adapter.Adapter_Request;
import com.example.admin.myapplication.Adapter.Adapter_memberList;
import com.example.admin.myapplication.HttpRequestProcessor.HttpRequestProcessor;
import com.example.admin.myapplication.HttpRequestProcessor.Response;
import com.example.admin.myapplication.apiConfiguration.ApiConfiguration;
import com.example.admin.myapplication.sharedPref.MyPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by admin on 3/30/2017.
 */

public class Fragment_Requests extends Fragment {

    private HttpRequestProcessor httpRequestProcessor;
    private Response response;
    private ApiConfiguration apiConfiguration;
    private String baseURL, urlMyFriendRequest, jsonStringToPost, urlRequest;
    private boolean success;
    private String message, name, jsonResponse, loggedInUserID,applicationFriendAssociationId;

    private Request request;
    private ArrayList<Request> RequestArrayList;
    String[] Name;
    Adapter_Request adapter_request;
    private String memberName,friendName,friendId,memberId;
    private int memberID;
    private int ApplicationUserId;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int logID;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_two, container, false);

        ListView lv = (ListView) view.findViewById(R.id.lv2);
        httpRequestProcessor = new HttpRequestProcessor();
        response = new Response();
        apiConfiguration = new ApiConfiguration();
        sharedPreferences = getActivity().getSharedPreferences(MyPref.Pref_Name, Context.MODE_PRIVATE);
        loggedInUserID = sharedPreferences.getString(MyPref.LoggedInUserID, null);
        logID = Integer.parseInt(loggedInUserID);

        //editor=sharedPreferences.edit();
       // editor.putString(MyPref.ApplicationFriendAssociationId,ApplicationFriendAssociationId);
        //editor.commit();
        sharedPreferences = getActivity().getSharedPreferences(MyPref.Pref_Name,Context. MODE_PRIVATE);
        memberId = sharedPreferences.getString(MyPref.ApplicationFriendAssociationId, null);
        //ApplicationFriendAssociationId=sharedPreferences.getString(MyPref.ApplicationFriendAssociationId,null);
       //ApplicationUserId=Integer.parseInt(ApplicationFriendAssociationId);

        //Getting base url
        baseURL = apiConfiguration.getApi();
        urlMyFriendRequest = baseURL + "ApplicationFriendAPI/MyFriendRequest/" + logID;
        RequestArrayList = new ArrayList<>();
        new RequestTask().execute();


        adapter_request = new Adapter_Request(getActivity(), RequestArrayList);
        lv.setAdapter(adapter_request);


        return view;
    }

    public class RequestTask extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {
            jsonResponse = httpRequestProcessor.gETRequestProcessor(urlMyFriendRequest);
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
                        applicationFriendAssociationId = object.getString("ApplicationFriendAssociationId");
                        memberId=object.getString("FriendId");
                        Log.d("FriendId ",memberId);

                        friendId=object.getString("MemberId");
                        Log.d("MemberId ", friendId);

                        memberName=object.getString("MemberName");
                        Log.d("MemberName ",memberName);

                        name=object.getString("FriendName");
                        Log.d("FriendName ",name);

                        sharedPreferences = getActivity().getSharedPreferences(MyPref.Pref_Name,Context.MODE_PRIVATE);
                        editor = sharedPreferences.edit();
                        editor.putString(MyPref.ApplicationFriendAssociationId,applicationFriendAssociationId);
                        editor.commit();

                       request = new Request(name, memberID,Integer.parseInt(applicationFriendAssociationId));
                        RequestArrayList.add(request);

                    }
                    adapter_request.notifyDataSetChanged();
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
