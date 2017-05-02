package com.example.admin.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.myapplication.HttpRequestProcessor.HttpRequestProcessor;
import com.example.admin.myapplication.HttpRequestProcessor.Response;
import com.example.admin.myapplication.apiConfiguration.ApiConfiguration;
import com.example.admin.myapplication.sharedPref.MyPref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private EditText edtuser, edtpass;
    private Button btnLogin;
    private int loggedInUserID;
    private String user, pass, applicationUserId;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    //private String name, passwd;
    private HttpRequestProcessor httpRequestProcessor;
    private Response response;
    private ApiConfiguration apiConfiguration;
    private String baseURL, urlLogin, jsonStringToPost, jsonResponseString;
    private boolean success;
    private String ErrorMessage, name, emailId;// address, emailID, phone, password, userName;
    private int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtuser = (EditText) findViewById(R.id.edtuser);
        edtpass = (EditText) findViewById(R.id.edtpass);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        //Initialization
        httpRequestProcessor = new HttpRequestProcessor();
        response = new Response();
        apiConfiguration = new ApiConfiguration();

        //Getting base url
        baseURL = apiConfiguration.getApi();
        urlLogin = baseURL + "AccountAPI/GetLoginUser";


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Getting name and password
                user = edtuser.getText().toString();
                pass = edtpass.getText().toString();

                new LoginTask().execute(user, pass);

                if (edtuser.getText().toString().trim().length() == 0) {
                    edtuser.setError("Username is not entered");
                    edtuser.requestFocus();
                }
                if (edtpass.getText().toString().trim().length() == 0) {
                    edtpass.setError("Password is not entered");
                    edtpass.requestFocus();
                } else {
                    Intent intent = new Intent(getApplicationContext(), ChatListActivity.class);
                    startActivity(intent);
                }

                user = edtuser.getText().toString().trim();
                pass = edtpass.getText().toString().trim();

                /*sharedPreferences = getSharedPreferences("My LoginPrefrence", Context.MODE_PRIVATE);
                editor = sharedPreferences.edit();

                editor.putString("user_key", user);
                editor.putString("pass_key", pass);
                editor.putString("appUserId_key", applicationUserId);
                editor.commit();
*/
                // new LoginTask().execute(user, pass);
            }
        });

    }

    public class LoginTask extends AsyncTask<String, String, String> {


        @Override
        protected String doInBackground(String... params) {
            user = params[0];
            pass = params[1];
            // applicationUserId=params[2];

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("UserName", user);
                jsonObject.put("Password", pass);
                //jsonObject.put("ApplicationUserId",applicationUserId);

                jsonStringToPost = jsonObject.toString();
                response = httpRequestProcessor.pOSTRequestProcessor(jsonStringToPost, urlLogin);
                jsonResponseString = response.getJsonResponseString();


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return jsonResponseString;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //Log.d("Response String", s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                success = jsonObject.getBoolean("success");
                Log.d("Success", String.valueOf(success));
                ErrorMessage = jsonObject.getString("ErrorMessage");


                Log.d("ErrorMessage", ErrorMessage);

                if (ErrorMessage.equals("User Authenticated!!")) {

                    //JSONObject object = responseData.getJSONObject();
                    name = jsonObject.getString("Name");
                    applicationUserId = jsonObject.getString("ApplicationUserId");
                    emailId = jsonObject.getString("EmailId");
                    Log.d("ApplicationUserId", applicationUserId);
                    sharedPreferences = getSharedPreferences(MyPref.Pref_Name, Context.MODE_PRIVATE);
                    editor = sharedPreferences.edit();
                    editor.putString(MyPref.UserName, name);
                    editor.putString(MyPref.EmailId, emailId);
                    editor.putString(MyPref.LoggedInUserID, applicationUserId);
                    editor.commit();
                    Toast.makeText(LoginActivity.this, ErrorMessage, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this, ChatListActivity.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(LoginActivity.this, ErrorMessage, Toast.LENGTH_LONG).show();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
