package com.crimebusters.crimebuster;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class Signup extends AppCompatActivity {

    EditText editTextSignupfullname;
    EditText editTextSignUpNic;
    EditText editTextSignUpCity;
    EditText editTextSignUpDob;
    EditText editTextSignUpEmail;
    EditText editTextSignUpPassword;
    CheckInternetBroadcast checkInternetBroadcast;

    String FinalUrl = "signup.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editTextSignupfullname = (EditText) findViewById(R.id.editTextSignupfullname);
        editTextSignUpNic = (EditText) findViewById(R.id.editTextSignUpNic);
        editTextSignUpCity = (EditText) findViewById(R.id.editTextSignUpCity);
        editTextSignUpDob = (EditText) findViewById(R.id.editTextSignUpDob);
        editTextSignUpEmail = (EditText) findViewById(R.id.editTextSignUpEmail);
        editTextSignUpPassword = (EditText) findViewById(R.id.editTextSignUpPassword);

        checkInternetBroadcast = new CheckInternetBroadcast();


    }


    public void dateOfBirth(View v){

        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

        DatePickerDialog datePickerDialog = new DatePickerDialog(Signup.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        editTextSignUpDob.setText(dayOfMonth + "/"
                                + (monthOfYear + 1) + "/" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();

    }

    public void signUp(View v){



        final String name = editTextSignupfullname.getText().toString();
        final String cnic = editTextSignUpNic.getText().toString();
        final String city=editTextSignUpCity.getText().toString();
        final String dob= editTextSignUpDob.getText().toString();
        final String email= editTextSignUpEmail.getText().toString();
        final String pass= editTextSignUpPassword.getText().toString();

        int status =0;



        UserDb userDb = new UserDb(Signup.this);
        UserBean addme = new UserBean(0,name,Integer.parseInt(cnic),city,dob,email,pass,status);
        if(checkInternetBroadcast.isNetworkAvailble(Signup.this)){

            status = 1;
            addme = new UserBean(0,name,Integer.parseInt(cnic),city,dob,email,pass,status);
            Gson gson = new Gson();
            String jsonUser = gson.toJson(addme);
            Log.i("jsonUser","User : "+jsonUser);

            RequestParams params = new RequestParams();
            params.put("registerUser",jsonUser);

            AssynHttpClient.post(FinalUrl,params,responeHandler);

        }
        userDb.AddUsers(addme);
        Log.i("save","DATA SAVE TO SQLITE");






        clearSignup();


    }

    public void login(View v){
        Intent intent = new Intent(Signup.this, Login.class);
        startActivity(intent);

    }


    public void clearSignup(){


        editTextSignupfullname.setText("");
        editTextSignUpNic.setText("");
        editTextSignUpCity.setText("");
        editTextSignUpDob.setText("");
        editTextSignUpEmail.setText("");
        editTextSignUpPassword.setText("");


        editTextSignupfullname.setHint("Full Name");

        editTextSignUpNic.setHint("NIC");
        editTextSignUpCity.setHint("CITY");
        editTextSignUpDob.setHint("Select Date Of Birth");
        editTextSignUpEmail.setHint("Email");
        editTextSignUpPassword.setHint("Password");


    }




    AsyncHttpResponseHandler responeHandler = new AsyncHttpResponseHandler(){
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            Log.i("Response","Response Code : "+statusCode);
            Log.i("Response","Succesfully Send Data ");
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Log.i("Response","Failure Error : "+error);
            Log.i("Response","Failure Error : "+statusCode);

        }
    };




}

