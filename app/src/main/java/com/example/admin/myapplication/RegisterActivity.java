package com.example.admin.myapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.myapplication.HttpRequestProcessor.HttpRequestProcessor;
import com.example.admin.myapplication.HttpRequestProcessor.Response;
import com.example.admin.myapplication.apiConfiguration.ApiConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class RegisterActivity extends AppCompatActivity {

    // private ImageView imageView;
    private EditText edtUser, edtPass, edtName, edtPhone, edtAddress, edtEmailID;
    private Button btnRegister;

    private String name, address, emailID, phone, userName, password;
    private HttpRequestProcessor httpRequestProcessor;
    private Response response;
    private ApiConfiguration apiConfiguration;
    private String baseURL, urlRegister;
    private String jsonPostString, jsonResponseString;
    private String message;
    private boolean success;
    private int userID;
    private int responseData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // imageView= (ImageView) findViewById(R.id.imageView);
        edtName = (EditText) findViewById(R.id.edtName);
        edtUser = (EditText) findViewById(R.id.edtUser);
        edtPass = (EditText) findViewById(R.id.edtPass);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        edtAddress = (EditText) findViewById(R.id.edtAddress);
        edtEmailID = (EditText) findViewById(R.id.edtEmailID);

        //Initialization
        httpRequestProcessor = new HttpRequestProcessor();
        response = new Response();
        apiConfiguration = new ApiConfiguration();

        //Getting BaseURL
        baseURL = apiConfiguration.getApi();
        urlRegister = baseURL + "AccountAPI/SaveApplicationUser";

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtName.getText().toString().trim().length() == 0) {
                    edtName.setError("Name is not entered");
                    edtName.requestFocus();
                }
                if (edtAddress.getText().toString().trim().length() == 0){
                    edtAddress.setError("Address is not entered");
                    edtAddress.requestFocus();
                }
                if (edtEmailID.getText().toString().trim().length() == 0){
                    edtEmailID.setError("EmailId is not entered");
                    edtEmailID.requestFocus();
                }
                if (edtPhone.getText().toString().trim().length() == 0){
                    edtPhone.setError("Phone is not entered");
                    edtPhone.requestFocus();
                }
                if (edtUser.getText().toString().trim().length() == 0) {
                    edtUser.setError("Username is not entered");
                    edtUser.requestFocus();
                }
                if (edtPass.getText().toString().trim().length() == 0) {
                    edtPass.setError("Password is not entered");
                    edtPass.requestFocus();
                } else {
                    name = edtName.getText().toString();
                    address = edtAddress.getText().toString();
                    emailID = edtEmailID.getText().toString();
                    phone = edtPhone.getText().toString();
                    userName = edtUser.getText().toString();
                    password = edtPass.getText().toString();
                    new RegistrationTask().execute(name, address, emailID, phone, userName, password);
                }

                //Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                //startActivity(intent);

            }
        });

    }

    private class RegistrationTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            name = params[0];
            Log.e("Name", name);
            address = params[1];
            emailID = params[2];
            phone = params[3];
            userName = params[4];
            password = params[5];

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("Name", name);
                jsonObject.put("Address", address);
                jsonObject.put("EmailId", emailID);
                jsonObject.put("Phone", phone);
                jsonObject.put("UserName", userName);
                jsonObject.put("Password", password);

                jsonPostString = jsonObject.toString();
                response = httpRequestProcessor.pOSTRequestProcessor(jsonPostString, urlRegister);
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
                message = jsonObject.getString("message");
                Log.d("message", message);
                responseData = jsonObject.getInt("responseData");
                if(responseData==1){
                    Toast.makeText(RegisterActivity.this,"User Registered Successfully",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                }else if(responseData == 2){
                    Toast.makeText(RegisterActivity.this,"User already exists.Please Try again...",Toast.LENGTH_LONG).show();
                }
                /*if (success) {
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                } else {
                    Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
                }*/

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
