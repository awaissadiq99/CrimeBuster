package com.crimebusters.crimebuster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        boolean isFirstTime = MyLoginPrefences.isFirst(FirstActivity.this);

        if(isFirstTime){
            startActivity(new Intent(FirstActivity.this,Login.class));
            finish();
        }
        else{
            startActivity(new Intent(FirstActivity.this,MainActivity.class));
            finish();

        }


    }
}
