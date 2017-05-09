package com.example.admin.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.myapplication.Adapter.Adapter_message;
import com.example.admin.myapplication.HttpRequestProcessor.HttpRequestProcessor;
import com.example.admin.myapplication.HttpRequestProcessor.Response;
import com.example.admin.myapplication.apiConfiguration.ApiConfiguration;
import com.example.admin.myapplication.sharedPref.MyPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * Created by admin on 4/21/2017.
 */

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtName;
    private TextView txtBack;
    private TextView txtStatus;
    private ImageView imageView3;
    private Button btnRefresh, btnSend;
    private ListView messageList;
    private EditText edtMessage;

    private String senderId, receiverId, senderName, messages, receiverName,msg;
    private HttpRequestProcessor httpRequestProcessor;
    private Response response;
    private ApiConfiguration apiConfiguration;
    private String baseURL, urlLoadMessage, urlSubmitMessage, jsonStringToPost, jsonResponseString, jsonResponse, loggedInUserID, message;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int logID, responseData;
    private Boolean isLoggedInUserId, success;
    private int friendID;
    private ListView lv;
    private Message m;
    ArrayList<Message> messageArrayList;
    private String memberId, jsonPostString;
    Context context;
    private String messageBody;
    private Adapter_message adapter_message;
    private int memberID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        txtBack = (TextView) findViewById(R.id.txtBack);
        txtName = (TextView) findViewById(R.id.txtName);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
        btnRefresh = (Button) findViewById(R.id.btnRefresh);
        btnSend = (Button) findViewById(R.id.btnSend);
        edtMessage = (EditText) findViewById(R.id.edtMessage);
        lv = (ListView) findViewById(R.id.lvMsg);
        //Initialization
        httpRequestProcessor = new HttpRequestProcessor();
        response = new Response();
        apiConfiguration = new ApiConfiguration();



        sharedPreferences = ChatActivity.this.getSharedPreferences(MyPref.Pref_Name, Context.MODE_PRIVATE);
        loggedInUserID = sharedPreferences.getString(MyPref.LoggedInUserID, null);
        logID = Integer.parseInt(loggedInUserID);

        //Getting FriendID from FriendList Screen

        friendID = getIntent().getExtras().getInt("friendID");
        String name=getIntent().getExtras().getString("name");
        txtName.setText(name);
        //Getting base url
        baseURL = apiConfiguration.getApi();
//        urlLoadMessage = baseURL + "MessageAPI/GetOurOldMessage/" + logID + "/" + friendID;
//        messageArrayList = new ArrayList<Message>();
//        new LoadMessageTask().execute();
//       /* if (messageArrayList.size() != 0) {
//            adapter_message = new Adapter_message(ChatActivity.this, messageArrayList);
//            lv.setAdapter(adapter_message);
//        }*/
//        adapter_message = new Adapter_message(ChatActivity.this, messageArrayList);
//        lv.setAdapter(adapter_message);

        btnSend.setOnClickListener(this);
        btnRefresh.setOnClickListener(this);
        txtBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSend:
                urlSubmitMessage = baseURL + "MessageAPI/SubmitMessage";

                msg=edtMessage.getText().toString();

                new SubmitMessageTask().execute(senderId, String.valueOf(friendID),msg);
                edtMessage.setText(" ");
                //Toast.makeText(ChatActivity.this, "Message sent", Toast.LENGTH_LONG).show();
                break;

            case R.id.btnRefresh:
                urlLoadMessage = baseURL + "MessageAPI/GetOurOldMessage/" + logID + "/" + friendID;
                messageArrayList = new ArrayList<Message>();
                new LoadMessageTask().execute();

                adapter_message = new Adapter_message(ChatActivity.this, messageArrayList);
                lv.setAdapter(adapter_message);
                break;

            case R.id.txtBack:
                Intent intent=new Intent(ChatActivity.this,ChatListActivity.class);
                startActivity(intent);
                break;


        }
    }


    public class LoadMessageTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {


            jsonResponse = httpRequestProcessor.gETRequestProcessor(urlLoadMessage);
            return jsonResponse;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                success = jsonObject.getBoolean("success");
                Log.d("Success", String.valueOf(success));
                message = jsonObject.getString("message");
                Log.d("message", message);

                if (success) {
                    JSONArray responseData = jsonObject.getJSONArray("responseData");

                    //If responseData is not null
                    if (responseData != null && responseData.length() != 0) {
                        Log.d("Response Data", "response data not null");
                        for (int i = 0; i < responseData.length(); i++) {
                            JSONObject object = responseData.getJSONObject(i);

                            senderName = object.getString("SenderName");
                            Log.d("SenderName", senderName);
                            receiverName = object.getString("ReceiverName");
                            Log.d("ReceiverName", receiverName);
                            receiverId = object.getString("RecipientId");
                            messages = object.getString("MessageBody");
                            // memberID = object.getInt("RecipientId");
                            senderId = object.getString("SenderId");

                            //If sender of message is same as the loggedIn User,set the boolean field isLoggedInUserID to true
                            if (senderId.equals(loggedInUserID)) {
                                isLoggedInUserId = true;
                                m = new Message(senderName, messages, isLoggedInUserId);
                                messageArrayList.add(m);

                            } else {
                                isLoggedInUserId = false;
                                m = new Message(senderName, messages, isLoggedInUserId);
                                messageArrayList.add(m);
                            }
                        }
                        adapter_message.notifyDataSetChanged();
                        Toast.makeText(ChatActivity.this, message, Toast.LENGTH_LONG).show();


                    }
                    /*//if JSONArray is null i.e. users are not invloved in any chat till now
                    else if (responseData.length() == 0) {
                        m = new Message();
                        messageArrayList.add(m);
                    }*/

                } else {
                    Toast.makeText(ChatActivity.this, message, Toast.LENGTH_LONG).show();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    public class SubmitMessageTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {


           senderId = params[0];
//            Log.e("SenderId", loggedInUserID);
//
            friendID = Integer.parseInt(params[1]);
//            Log.e("ReceiverId", memberId);
//
            msg=params[2];
//            Log.d("MessageBody",messageBody);

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("SenderId", loggedInUserID);
                Log.d("SenderId", loggedInUserID);
                jsonObject.put("ReceiverId", friendID);
                Log.d("ReceiverId", String.valueOf(friendID));
                jsonObject.put("MessageBody", msg);
                Log.d("MessageBody", msg);

                jsonPostString = jsonObject.toString();
                response = httpRequestProcessor.pOSTRequestProcessor(jsonPostString, urlSubmitMessage);
                jsonResponseString = response.getJsonResponseString();

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonResponseString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                success = jsonObject.getBoolean("success");
                Log.d("Success", String.valueOf(success));
                message = jsonObject.getString("message");
                Log.d("message", message);
                responseData = jsonObject.getInt("responseData");
                if (responseData == 1) {
                    Toast.makeText(ChatActivity.this, message, Toast.LENGTH_LONG).show();
                } else if (responseData == 2) {
                    Toast.makeText(ChatActivity.this, message, Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}

