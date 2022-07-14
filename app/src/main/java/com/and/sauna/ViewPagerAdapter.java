package com.and.sauna;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;


import Fragments.TrackCO2Data;
import Fragments.TrackHumidityData;
import Fragments.TrackTemperatureData;

public class ViewPagerAdapter extends FragmentStateAdapter {


    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity)
    {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 0:
                return  new TrackCO2Data();
            case 1:
                return  new TrackHumidityData();
            default:
                return  new TrackTemperatureData();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

}
