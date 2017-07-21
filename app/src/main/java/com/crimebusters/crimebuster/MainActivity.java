package com.crimebusters.crimebuster;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;
import java.util.concurrent.RecursiveAction;

public class MainActivity extends AppCompatActivity {
    int PERMISSION_ALL = 1;
    LocationManager mlocManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mlocManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);


    }

    @Override
    protected void onResume() {
        super.onResume();
        if(checkPermissions()){

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
            case R.id.action_logout:

                SharedPreferences gameSettings = getSharedPreferences("my_preferences", MODE_PRIVATE);
                SharedPreferences.Editor prefEditor = gameSettings.edit();
                prefEditor.putBoolean("is_first", true);
                prefEditor.commit();
                startActivity(new Intent(MainActivity.this,Login.class));
                return true;
            case R.id.action_refresh:
            UserDb userDb = new UserDb(MainActivity.this);
             //   userDb.UpdateStatus();

                List<UserBean> userBeanList = userDb.getAllUserDetails(0);

                Gson gson = new Gson();
                String jsonUsers = gson.toJson(userBeanList);
                Log.i("jsonUsers","Users : "+jsonUsers);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }//End Switch
    }//end OnOptionItemSelected




    public static boolean hasPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }//end hasPermissions

    public boolean  checkPermissions(){


        String[] PERMISSIONS = {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

        if(!hasPermissions(this, PERMISSIONS)){

            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
            return false;

        }
        return true;
    }// end checkPermissions

    private void showDialogGPS() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle("Enable GPS");
        builder.setMessage("Please enable GPS");
        builder.setInverseBackgroundForced(true);
        builder.setPositiveButton("Enable", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                startActivity(
                        new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });
        builder.setNegativeButton("Ignore", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }//end show DialogGps

    public void recordCrime(View v){
        if(checkPermissions()) {
            boolean enabled = mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (!enabled) {
                showDialogGPS();

            }else {
                startActivity(new Intent(MainActivity.this, RecordCrimeActivity.class));

            }

        }
    }// end Record Crime
}
