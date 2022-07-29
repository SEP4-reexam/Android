package com.and.sauna;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.text.SimpleDateFormat;

import Data.DatabaseHelper;
import Fragments.TrackCO2Data;

public class TrackSaunaDataActivity extends AppCompatActivity {

   // FloatingActionButton button;



    TrackCO2Data trackCO2Data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_sauna_data);

        //saveToDatabase();
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager2 viewPager2 = findViewById(R.id.viewpager);

//        button = findViewById(R.id.btnRefresh);
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                saveToDatabase();
//            }
//        });

        ViewPagerAdapter adapter = new ViewPagerAdapter(this);

        viewPager2.setAdapter(adapter);


        new TabLayoutMediator(tabLayout, viewPager2,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        if (position == 0)
                        {
                            tab.setText("CO2 ");
                        }

                        else if (position == 1)
                        {
                            tab.setText("Humidity ");
                        }
                        else if (position == 2)
                        {
                            tab.setText("Temperature ");
                        }
                    }
                }).attach();
  }





    }