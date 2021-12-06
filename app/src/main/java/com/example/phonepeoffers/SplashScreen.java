package com.example.phonepeoffers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.phonepeoffers.Account_new.Account_new;

public class SplashScreen extends AppCompatActivity {

    ImageView go_to_account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        go_to_account=findViewById(R.id.go_to_account);

        go_to_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(getApplicationContext(), Account_new.class);
                startActivity(i);
            }
        });

    }
}