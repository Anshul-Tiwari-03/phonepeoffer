package com.example.phonepeoffers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.phonepeoffers.my_account.my_profile;
import com.example.phonepeoffers.offers_list.offers_all;
import com.example.phonepeoffers.offers_new.offer_new;
import com.example.phonepeoffers.orders_history.offers_history;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNav = findViewById(R.id.bottomnav);


        FragmentManager fm = getSupportFragmentManager();
        final FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragmentview, new offers_all());
        ft.addToBackStack(null);
        ft.commit();


        bottomNav.setSelectedItemId(R.id.offers);
        bottomNav.getMenu().findItem(R.id.offers).setChecked(true);


        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {


            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                FragmentManager fm = getSupportFragmentManager();
                final FragmentTransaction ft = fm.beginTransaction();
                switch (item.getItemId())
                {
                    case R.id.offers:
                        ft.replace(R.id.fragmentview, new offers_all());
                        ft.addToBackStack(null);
                        ft.commit();
                        break;

                    case R.id.account:

                        ft.replace(R.id.fragmentview, new my_profile());
                        ft.addToBackStack(null);
                        ft.commit();
                        break;
                    case R.id.history:
                        ft.replace(R.id.fragmentview, new offers_history());
                        ft.addToBackStack(null);
                        ft.commit();
                        break;


                    case R.id.add_new:
                        Intent i = new Intent(getApplicationContext(), offer_new.class);
                        startActivity(i);
                        break;


                }


                return false;
            }
        });


    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}