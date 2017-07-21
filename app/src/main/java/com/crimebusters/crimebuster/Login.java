package com.crimebusters.crimebuster;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Login extends AppCompatActivity {

    EditText editTextLoginUser,editTextLoginPass;
    CheckInternetBroadcast checkInternetBroadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        checkInternetBroadcast = new CheckInternetBroadcast();

    }


    public void loginButton(View v){

        editTextLoginUser = (EditText) findViewById(R.id.editTextLoginUser);
        editTextLoginPass = (EditText) findViewById(R.id.editTextLoginPass);

        String userEmail = editTextLoginUser.getText().toString();
        String userPass = editTextLoginPass.getText().toString();

        UserDb userDb = new UserDb(Login.this);

        UserBean userdetails = userDb.getUserDetails(userEmail,userPass);

        if(userdetails==null){
            if(checkInternetBroadcast.isNetworkAvailble(Login.this)) {
                AssynHttpClient client = new AssynHttpClient();
                String url = "login.php";
                RequestParams param = new RequestParams();
                param.put("userEmail", userEmail);
                param.put("userPass", userPass);
                client.post(url, param, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Log.i("response", "Failed " + throwable);

                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                                if(responseString.length() == 3){
                                    invalidEmailPasword();
                                }
                        try {
                            JSONArray array=new JSONArray(responseString);
                            JSONObject obj= (JSONObject) array.get(0);
                            Log.i("obj","obj "+obj);
                            Gson gson = new Gson();
                            UserBean userBean = gson.fromJson(obj.toString(),UserBean.class);

                            Log.i("response", "Response From Server : " + responseString);
                            Log.i("response","Lenth : "+ responseString.length());
                            Log.i("response","Email : "+  userBean.getUserEmail());
                            Log.i("response","FullName : "+  userBean.getUserFullname());
                            UserDb userdb = new UserDb(Login.this);
                            userdb.AddUsers(userBean);
                            startActivity(new Intent(Login.this,MainActivity.class));

                            finish();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                });

            }else{
                invalidEmailPasword();
            }

        }





        else{
            SharedPreferences gameSettings = getSharedPreferences("my_preferences", MODE_PRIVATE);
            SharedPreferences.Editor prefEditor = gameSettings.edit();
            prefEditor.putBoolean("is_first", false);
            prefEditor.commit();
            startActivity(new Intent(Login.this,MainActivity.class));


        }
    }


    public void signUp(View v){
        Intent intent = new Intent(Login.this,Signup.class);
        startActivity(intent);


    }
    public void invalidEmailPasword(){

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Invalid Email or Password!")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }




}
