package com.example.kschmidty.popularmovies;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainDiscoveryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_discovery);

        if(findViewById(R.id.container) != null){
            if(savedInstanceState != null){
                return;
            }

            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();

            FragmentDiscovery discoveryFragment = new FragmentDiscovery();

            ft.add(R.id.container,discoveryFragment);
            ft.commit();


        }
    }
}
