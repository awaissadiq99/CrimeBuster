package com.crimebusters.crimebuster;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by awais on 6/17/2017.
 */

public class CheckInternetBroadcast extends BroadcastReceiver {

    String FinalUrl = "signup.php";

    @Override
    public void onReceive(Context context, Intent intent) {
        context.sendBroadcast(new Intent("INTERNET_LOST"));

        if(isNetworkAvailble(context)){
            Toast.makeText(context, "Network Is Avbl", Toast.LENGTH_SHORT).show();

            Log.i("Network","Network Avbl :");
            UserDb userDb = new UserDb(context);

            List<UserBean> userBeanList = userDb.getAllUserDetails(0);
            if(!userBeanList.isEmpty()){
                Gson gson = new Gson();
                String jsonUsers = gson.toJson(userBeanList);
                RequestParams params = new RequestParams();
                params.put("registerUsers",jsonUsers);

                AssynHttpClient.post(FinalUrl,params,responeHandler);
                userDb.UpdateStatus();
                Log.i("userBeanList","userBeanList"+userBeanList);
            }
            else{
                Log.i("Data","There is No New Registration Data");
            }
        }
        else {
            Toast.makeText(context, "Network Is Gone", Toast.LENGTH_SHORT).show();
            Log.i("Network","Network not Avbl : ");


        }
    }

    //Check internet Connection

    public boolean isNetworkAvailble(Context context){

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) { // connected to the internet
            if (activeNetwork.isConnected()) {
                // connected to wifi
                return true;
            }

        }
        return false;
    }

    AsyncHttpResponseHandler responeHandler = new AsyncHttpResponseHandler(){
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            Log.i("Response","Response Code : "+statusCode);
            //Log.i("Response","Succesfully Send Data ");
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Log.i("Response","Failure Error : "+error);
            Log.i("Response","Failure Error : "+statusCode);

        }
    };

}
