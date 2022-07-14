package Fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.and.sauna.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrackHumidityData#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrackCO2Data extends Fragment {

    BarChart barChart;
    BarData barData;
    BarDataSet barDataSet;
    List barEntries;

//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
//    public TrackCO2Data() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment TrackHumidityData.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static TrackCO2Data newInstance(String param1, String param2) {
//        TrackCO2Data fragment = new TrackCO2Data();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view =inflater.inflate(R.layout.fragment_track_c_o2_data, container, false);

        barChart = view.findViewById(R.id.bar);

        barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(1f, 2));
        barEntries.add(new BarEntry(3f, 4));
        barEntries.add(new BarEntry(5f, 6));

        barDataSet = new BarDataSet(barEntries, "Data set");

        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        barDataSet.setValueTextColor(Color.BLUE);
        barDataSet.setValueTextSize(18f);

        barData = new BarData(barDataSet);

        barChart.setData(barData);

        barChart.setFitBars(true);
        barChart.getDescription().setText("Example suffering");


        return view;
       // return inflater.inflate(R.layout.fragment_track_c_o2_data, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

}