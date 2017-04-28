package com.example.admin.myapplication;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by admin on 3/30/2017.
 */

public class FragmentOne extends Fragment {

    private HttpRequestProcessor httpRequestProcessor;
    private Response response;
    private ApiConfiguration apiConfiguration;
    private String baseURL, urlFriendList, jsonStringToPost, jsonResponseString;
    private boolean success;
    private String message, name, emailID, jsonResponse;
    private int userID;
    private FriendList friendList;
    private ArrayList<FriendList> friendListArrayList;
    String[] Name;
    String[] emailId;
    Adapter_friendList adapter_friendList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_tab_one,container,false);


        ListView lv = (ListView) view.findViewById(R.id.lv1);
        httpRequestProcessor = new HttpRequestProcessor();
        response = new Response();
        apiConfiguration = new ApiConfiguration();

        //Getting base url
        baseURL = apiConfiguration.getApi();
        urlFriendList = baseURL + "ApplicationFriendAPI/MyFriendList/{MemberId}";
        friendListArrayList = new ArrayList<>();
        new FragmentOne.getFriendListTask().execute();


        adapter_friendList = new Adapter_friendList(getActivity(), friendListArrayList);
        lv.setAdapter(adapter_friendList);
        return view;
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
                        name = object.getString("Name");
                        Log.d("Name", name);
                        emailID = object.getString("EmailId");
                        Log.d("EmailId", emailID);

                        friendList = new FriendList(name, emailID);
                        friendListArrayList.add(friendList);
                    }
                    adapter_friendList.notifyDataSetChanged();
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                    //Intent intent = new Intent(getActivity(), ChatListActivity.class);
                    //startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


}
