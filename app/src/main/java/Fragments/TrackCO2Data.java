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

public class TrackCO2Data extends Fragment {

    BarChart barChart;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_track_c_o2_data, container, false);

        barChart = view.findViewById(R.id.bar);

        List<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0f, 7f,"Recommended"));
        entries.add(new BarEntry(1f, 10f,"Current"));
        entries.add(new BarEntry(2f, 14f,"Highest"));


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

        Description description = new Description();
        description.setText("All values in percentages");
        description.setTextSize(15);
        barChart.setDescription(description);

        //Setting up the borders
        barChart.setDrawGridBackground(true);
        barChart.setBorderWidth(1);
        barChart.setDrawBorders(true);

        barChart.setData(data);
        barChart.setFitBars(true); // make the x-axis fit exactly all bars
        barChart.invalidate(); // refresh
       // barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(barFactors));

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
    }

}