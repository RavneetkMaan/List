package com.example.admin.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.myapplication.Adapter.Adapter_friendList;
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

public class Fragment_Friends extends Fragment implements AdapterView.OnItemClickListener {

    private HttpRequestProcessor httpRequestProcessor;
    private Response response;
    private ApiConfiguration apiConfiguration;
    private String baseURL, urlFriendList, jsonStringToPost, jsonResponseString;
    private boolean success;
    private String message, name, jsonResponse, loggedInUserID;
    private FriendList friendList;
    private ArrayList<FriendList> friendListArrayList;
    String[] Name;
    Adapter_friendList adapter_friendList;
    private String memberId, friendId;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int logID;
    private String loggedInId;
    private int memberID;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab_one, container, false);


        ListView lv = (ListView) view.findViewById(R.id.lv1);
        httpRequestProcessor = new HttpRequestProcessor();
        response = new Response();
        apiConfiguration = new ApiConfiguration();

        sharedPreferences = getActivity().getSharedPreferences(MyPref.Pref_Name, Context.MODE_PRIVATE);
        loggedInUserID = sharedPreferences.getString(MyPref.LoggedInUserID, null);
        logID = Integer.parseInt(loggedInUserID);

        //Getting base url
        baseURL = apiConfiguration.getApi();
        urlFriendList = baseURL + "ApplicationFriendAPI/MyFriendList/" + logID;
        friendListArrayList = new ArrayList<>();
        new getFriendListTask().execute();

        adapter_friendList = new Adapter_friendList(getActivity(), friendListArrayList);
        lv.setAdapter(adapter_friendList);

        lv.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        friendList = friendListArrayList.get(position);
        name = friendList.getName();
        int mID = friendList.getMemberId();
        Toast.makeText(getActivity(), name + " selected", Toast.LENGTH_LONG).show();
        Intent i = new Intent(getActivity(), ChatActivity.class);
        i.putExtra("friendID", mID);
        i.putExtra("name",name);

        startActivity(i);
    }

    public class getFriendListTask extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {


            jsonResponse = httpRequestProcessor.gETRequestProcessor(urlFriendList);
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
                        name = object.getString("FriendName");
                        Log.d("FriendName", name);

                        memberId = object.getString("FriendId");
                        Log.d("FriendId", memberId);
                        memberID = Integer.parseInt(memberId);

                        friendList = new FriendList(name, memberID);
                        friendListArrayList.add(friendList);
                    }
                    adapter_friendList.notifyDataSetChanged();
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
