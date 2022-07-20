package Fragments;


import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.and.sauna.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;


public class TrackHumidityData extends Fragment {

    LineChart lineChart;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_track_humidity_data, container, false);

        lineChart = view.findViewById(R.id.line);

        LineDataSet dataSet = new LineDataSet(dataValues(), "Current Humidity");
        LineDataSet dataSet2 = new LineDataSet(dataValuesRec(), "Recommended Humidity");
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet);
        dataSets.add(dataSet2);

        //Setting the color of the lines and the width of the highlighted values
        dataSet.setColors(Color.rgb(241, 196, 15));
        dataSet2.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextColor(Color.BLUE);
        dataSet2.setValueTextColor(Color.BLUE);
        dataSet.setValueTextSize(14f);
        dataSet2.setValueTextSize(14f);

        //Setting line width and other custom settings
        dataSet.setLineWidth(4);
        dataSet2.setLineWidth(4);
        dataSet.setDrawCircles(true);
        dataSet2.setDrawCircles(true);

        LineData data = new LineData(dataSets);
        lineChart.setData(data);
        lineChart.invalidate();

        lineChart.setDrawGridBackground(true);

        //Setting border width and color
        lineChart.setBorderWidth(1);
        lineChart.setDrawBorders(true);

        //Setting the bottom right corner text
        Description description = new Description();
        description.setText("All values in percentages");
        description.setTextSize(15);
        lineChart.setDescription(description);


        ArrayList<String> lineFactors = new ArrayList<>();
        lineFactors.add("Recommended");
        lineFactors.add("Current");

        //Setting up the legend
        Legend l = lineChart.getLegend();
        l.setFormSize(10f); // set the size of the legend forms/shapes
        l.setForm(Legend.LegendForm.CIRCLE); // set what type of form/shape should be used
        l.setTextSize(12f);
        l.setTextColor(Color.BLACK);
        List<LegendEntry> lentries = new ArrayList<>();

        for (int i = 0; i < lineFactors.size(); i++) {
            LegendEntry entry = new LegendEntry();
            entry.formColor = ColorTemplate.MATERIAL_COLORS[i];
            entry.label = lineFactors.get(i);
            lentries.add(entry);
        }

        l.setXEntrySpace(5f); // set the space between the legend entries on the x-axis
        l.setYEntrySpace(5f);
        l.setCustom(lentries);

        //lineChart.notifyDataSetChanged();

        return view;
    }

    private ArrayList<Entry> dataValues()
    {
        ArrayList<Entry> dataVals = new ArrayList<Entry>();
        dataVals.add(new Entry(0, 20));
        dataVals.add(new Entry(1, 24));
        dataVals.add(new Entry(2, 2));
        dataVals.add(new Entry(3, 18));

        return dataVals;
    }

    private ArrayList<Entry> dataValuesRec()
    {
        ArrayList<Entry> dataVals = new ArrayList<Entry>();
        dataVals.add(new Entry(0, 5));
        dataVals.add(new Entry(2, 10));

        return dataVals;
    }

}