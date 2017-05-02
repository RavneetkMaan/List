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

import com.example.admin.myapplication.FriendList;
import com.example.admin.myapplication.HttpRequestProcessor.HttpRequestProcessor;
import com.example.admin.myapplication.HttpRequestProcessor.Response;
import com.example.admin.myapplication.Member;
import com.example.admin.myapplication.R;
import com.example.admin.myapplication.Request;
import com.example.admin.myapplication.apiConfiguration.ApiConfiguration;
import com.example.admin.myapplication.sharedPref.MyPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by admin on 4/29/2017.
 */

public class Adapter_Request extends BaseAdapter implements View.OnClickListener {

    private HttpRequestProcessor httpRequestProcessor;
    private Response response;
    private ApiConfiguration apiConfiguration;
    private String baseURL, jsonStringToPost, urlRequest, jsonResponse;
    private boolean success;
    private String jsonPostString, jsonResponseString, applicationFriendAssociationID;
    private String message, name;

    private Context context;
    private ArrayList<Request> RequestArrayList;
    private LayoutInflater inflater;
    private Button btnAccept, btnDecline;
    private String memberId;
    private String loggedInId;
    private SharedPreferences sharedPreferences;
    private Request request;
    Adapter_Request adapter_request;
    private int memberID;
    private int ApplicationFriendAssociationId;

    public Adapter_Request(Context context, ArrayList<Request> RequestArrayList) {
        this.context = context;
        this.RequestArrayList = RequestArrayList;
    }

    @Override
    public int getCount() {
        return RequestArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return RequestArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.requests, parent, false);

        TextView txt1 = (TextView) convertView.findViewById(R.id.txtName);


        Request request = RequestArrayList.get(position);

        btnAccept = (Button) convertView.findViewById(R.id.btnAccept);
        btnDecline = (Button) convertView.findViewById(R.id.btnDecline);
        //Request request1 = RequestArrayList.get(position);

        name = request.getName();
        int mId = request.getMemberId();
        memberId = String.valueOf(mId);
        applicationFriendAssociationID = String.valueOf(request.getApplicationFriendAssociationID());


        txt1.setText(name);


        btnAccept.setOnClickListener(this);
        btnDecline.setOnClickListener(this);


        return convertView;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnAccept:
                httpRequestProcessor = new HttpRequestProcessor();
                response = new Response();
                apiConfiguration = new ApiConfiguration();

                //Getting base url
                baseURL = apiConfiguration.getApi();
                urlRequest = baseURL + "ApplicationFriendAPI/ActionOnFriendRequest";

                new ActionOnFriendRequestTask().execute(applicationFriendAssociationID);

                btnAccept.setText("Friends");
                btnDecline.setVisibility(View.INVISIBLE);
                break;

            case R.id.btnDecline:
                httpRequestProcessor = new HttpRequestProcessor();
                response = new Response();
                apiConfiguration = new ApiConfiguration();

                //Getting base url
                baseURL = apiConfiguration.getApi();
                urlRequest = baseURL + "ApplicationFriendAPI/ActionOnFriendRequest";

                new ActionOnFriendRequestTask().execute(applicationFriendAssociationID);
                btnAccept.setVisibility(View.INVISIBLE);

                break;

        }
    }

    public class ActionOnFriendRequestTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            applicationFriendAssociationID = params[0];
            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("ApplicationFriendAssociationId", applicationFriendAssociationID);
                jsonObject.put("Status", "Accept");
                jsonObject.put("Status","Reject");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            jsonStringToPost = jsonObject.toString();
            response = httpRequestProcessor.pOSTRequestProcessor(jsonStringToPost, urlRequest);
            jsonResponseString = response.getJsonResponseString();
            return jsonResponseString;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("response", s);
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
                        name=object.getString("MemberName");
                        Log.e("MemberName",name );
                        applicationFriendAssociationID = object.getString("ApplicationFriendAssociationId");
                        //memberID = object.getInt("MemberId");
                        memberID=Integer.parseInt(memberId);
                        ApplicationFriendAssociationId=Integer.parseInt(applicationFriendAssociationID);
                        request = new Request(name,memberID,ApplicationFriendAssociationId);
                        RequestArrayList.add(request);

                    }
                    adapter_request.notifyDataSetChanged();
                    Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}



