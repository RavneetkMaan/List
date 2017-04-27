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
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.myapplication.HttpRequestProcessor.HttpRequestProcessor;
import com.example.admin.myapplication.HttpRequestProcessor.Response;
import com.example.admin.myapplication.apiConfiguration.ApiConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static android.R.attr.name;
import static com.example.admin.myapplication.R.id.image;


/**
 * Created by admin on 3/30/2017.
 */

public class FragmentThree extends Fragment {

    private HttpRequestProcessor httpRequestProcessor;
    private Response response;
    private ApiConfiguration apiConfiguration;
    private String baseURL, urlGetApplicationMemberList, jsonStringToPost, jsonResponseString;
    private boolean success;
    private String message, name,emailID;
    private int userID;
    //private





    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tab_three, container, false);
        
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Initialization
        httpRequestProcessor = new HttpRequestProcessor();
        response = new Response();
        apiConfiguration = new ApiConfiguration();

        //Getting base url
        baseURL = apiConfiguration.getApi();
        urlGetApplicationMemberList = baseURL + "ApplicationFriendAPI/GetApplicationMemberList";

    }


    //onActivitityCreated
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ListView lv3=(ListView)getActivity().findViewById(R.id.lv3);
        MyAdapter dd3=new MyAdapter();
        lv3.setAdapter(dd3);
    }
    public class getMemberListTask extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {
            name = params[0];
            emailID = params[1];

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("Name", name);
                jsonObject.put("EmailId", emailID);

                jsonStringToPost = jsonObject.toString();
                response = httpRequestProcessor.pOSTRequestProcessor(jsonStringToPost, urlGetApplicationMemberList);
                jsonResponseString = response.getJsonResponseString();


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonResponseString;
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
                        Log.d("name", name);
                        emailID = object.getString("EmailId");
                        Log.d("emailId", emailID);
                    }
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(), ChatListActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                }

            }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        }
    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {return 0;}

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
            convertView=inflater.inflate(R.layout.add_friend,parent,false);

            TextView txt1= (TextView) convertView.findViewById(R.id.txtName);
            TextView txt2= (TextView) convertView.findViewById(R.id.txtEmailId);
            //ImageView imageView= (ImageView) convertView.findViewById(R.id.imageView1);

            txt1.setText(name);
            txt2.setText(emailID);
            //imageView.setImageResource(imageView);




            return convertView;
        }


    }

