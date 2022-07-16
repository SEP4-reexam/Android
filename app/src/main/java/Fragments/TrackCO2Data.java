package Fragments;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.and.sauna.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
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

        View view =inflater.inflate(R.layout.fragment_track_c_o2_data, container, false);

        barChart = view.findViewById(R.id.bar);

        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, 7f,"Recommended"));
        entries.add(new BarEntry(1f, 10f,"Current"));
        entries.add(new BarEntry(2f, 14f,"Highest"));


//        barEntries = new ArrayList<>();
//        barEntries.add(new BarEntry(1f, 2));
//        barEntries.add(new BarEntry(3f, 4));
//        barEntries.add(new BarEntry(5f, 6));

        BarDataSet barDataSet = new BarDataSet(entries, "CO2");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLUE);
        barDataSet.setValueTextSize(18f);

        ArrayList<String> barFactors = new ArrayList<>();
        barFactors.add("Recommended");
        barFactors.add("Current");
        barFactors.add("Highest");

        XAxis xAxis = barChart.getXAxis();
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);
        BarData data = new BarData(barDataSet);
        data.setBarWidth(0.9f); // set custom bar width
       // data.setValueTextSize(12);

        Description description = new Description();
        description.setText("All values in parts-per-million");
        barChart.setDescription(description);

        barChart.setData(data);
        barChart.setFitBars(true); // make the x-axis fit exactly all bars
        barChart.invalidate(); // refresh
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(barFactors));

        //BARCHART LEGEND
        Legend l = barChart.getLegend();
        l.setFormSize(10f); // set the size of the legend forms/shapes
        l.setForm(Legend.LegendForm.CIRCLE); // set what type of form/shape should be used
        l.setTextSize(12f);
        l.setTextColor(Color.BLACK);
        List<LegendEntry> lentries = new ArrayList<>();
        for (int i = 0; i < barFactors.size(); i++) {
            LegendEntry entry = new LegendEntry();
            entry.formColor = ColorTemplate.MATERIAL_COLORS[i];
            entry.label = barFactors.get(i);
            lentries.add(entry);
        }
        l.setXEntrySpace(5f); // set the space between the legend entries on the x-axis
        l.setYEntrySpace(5f);
        l.setCustom(lentries);



        return view;
       // return inflater.inflate(R.layout.fragment_track_c_o2_data, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

}