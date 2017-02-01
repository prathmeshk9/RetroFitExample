package com.example.admin.retrofitexample;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private EditText editTextName;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextEmail;

    TextView tv12;

    private Button buttonRegister;
    private Button getData;

    //This is our root url
    public static final String ROOT_URL = "http://192.168.1.136/";

    //This Cool Acharya root
    public static final String MY_ROOT = "http://coolacharya.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing Views
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextUsername = (EditText) findViewById(R.id.editTextUsername);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);

        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        getData = (Button) findViewById(R.id.buttonGetData);
        tv12 = (TextView) findViewById(R.id.textView12);

        //Adding listener to button
        buttonRegister.setOnClickListener(this);
        getData.setOnClickListener(this);
    }

    //Overriding onclick method
    @Override
    public void onClick(View v) {
        //Calling insertUser on button click
        if(v.getId()==R.id.buttonRegister) {
            insertUser();
        }
        if(v.getId()==R.id.buttonGetData){
            Log.d(TAG, "onClick: ");
            getDataFromServer();
        }
    }

    private void insertUser() {
        //Here we will handle the http request to insert user to mysql db
        //Creating a RestAdapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL) //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        RegisterAPI api = adapter.create(RegisterAPI.class);

        //Defining the method insertuser of our interface
        api.insertUser(
                //Passing the values by getting it from editTexts
                editTextName.getText().toString(),
                editTextUsername.getText().toString(),
                editTextPassword.getText().toString(),
                editTextEmail.getText().toString(),

                //Creating an anonymous callback
                new Callback<Response>() {
                    @Override
                    public void success(Response result, Response response) {
                        //On success we will read the server's output using bufferedreader
                        //Creating a bufferedreader object
                        BufferedReader reader = null;

                        //An string to store output from the server
                        String output = "";

                        try {
                            //Initializing buffered reader
                            reader = new BufferedReader(new InputStreamReader(result.getBody().in()));

                            //Reading the output in the string
                            output = reader.readLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        //Displaying the output as a toast
                        Toast.makeText(MainActivity.this, output, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        //If any error occured displaying the error as toast
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void getDataFromServer() {
        //Here we will handle the http request to get user info from mysql db
        //Creating a RestAdapter
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(MY_ROOT) //Setting the Root URL
                .build(); //Finally building the adapter

        //Creating object for our interface
        RegisterAPI api = adapter.create(RegisterAPI.class);

        Log.d(TAG, "Retro called...: ");

        String userid = "1";
        JsonObject POST_PARAM = new JsonObject();
        POST_PARAM.addProperty("userid", userid);

        api.getConatct(POST_PARAM,new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                BufferedReader reader = null;
                StringBuilder sb = new StringBuilder();

                //An string to store output from the server
                String output = "";

                try {
                    //Initializing buffered reader
                    reader = new BufferedReader(new InputStreamReader(response2.getBody().in()));

                    //Reading the output in the string
                    output = reader.readLine();
                    sb.append(output);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                tv12.setText(sb.toString());
                //Displaying the output as a toast
                Toast.makeText(MainActivity.this, " "+ response.getStatus(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "failure: "+error);
            }
        });
    }
}
