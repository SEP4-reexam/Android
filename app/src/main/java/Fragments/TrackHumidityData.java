package Fragments;


import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.and.sauna.R;
import com.github.mikephil.charting.animation.Easing;
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
import java.util.Random;


public class TrackHumidityData extends Fragment {

    LineChart lineChart;
    ArrayList<Entry> dataVals = new ArrayList<Entry>();
    int max = 100;
    int min = 0;
    Random rand = new Random();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_track_humidity_data, container, false);

        lineChart = view.findViewById(R.id.line);

        LineDataSet dataSet = new LineDataSet(dataValues(), "Current Humidity");
        //LineDataSet dataSet2 = new LineDataSet(dataValuesRec(), "Recommended Humidity");
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataSet);
        //dataSets.add(dataSet2);

        dataVals.add(new Entry(0, rand.nextInt((max - min) + 1) + min));
        //Setting the color of the lines and the width of the highlighted values
        dataSet.setColors(Color.rgb(241, 196, 15));
        //dataSet2.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextColor(Color.BLUE);
        //dataSet2.setValueTextColor(Color.BLUE);
        dataSet.setValueTextSize(14f);
        //dataSet2.setValueTextSize(14f);

        //Setting line width and other custom settings
        dataSet.setLineWidth(4);
        //dataSet2.setLineWidth(4);
        dataSet.setDrawCircles(true);
        //dataSet2.setDrawCircles(true);

        LineData data = new LineData(dataSets);
        lineChart.setData(data);

        lineChart.animateX(3000, Easing.EaseInCubic);

        data.notifyDataChanged();
        lineChart.notifyDataSetChanged();
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
        NewEntry();

        return view;
    }

    public void NewEntry()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //add entries
                for (int i = 0; i < 25; i++) {
                    {
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                dataValues();
                            }
                        });
                    }
                    //pause between intervals
                    {
                        try {
                            Thread.sleep(600);
                        } catch (NegativeArraySizeException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }


    int t = 0;
    private ArrayList<Entry> dataValues()
    {
        t+=5;
        int random = rand.nextInt((max - min) + 1) + min;
        dataVals.add(new Entry(t, random));
       //}
//        dataVals.add(new Entry(1, 24));
//        dataVals.add(new Entry(2, 2));
//        dataVals.add(new Entry(3, 18));
//        dataVals.add(new Entry(4, 20));
//        dataVals.add(new Entry(5, 24));
//        dataVals.add(new Entry(6, 2));
//        dataVals.add(new Entry(7, 18));
//        dataVals.add(new Entry(8, 24));
//        dataVals.add(new Entry(9, 2));
//        dataVals.add(new Entry(10, 18));
//        dataVals.add(new Entry(11, 24));
//        dataVals.add(new Entry(12, 2));
//        dataVals.add(new Entry(13, 18));
//        dataVals.add(new Entry(14, 20));
//        dataVals.add(new Entry(15, 20));
        System.out.println("DATA VALUES: " + dataVals);

        return dataVals;
    }

//    private ArrayList<Entry> dataValuesRec()
//    {
//        ArrayList<Entry> dataVals = new ArrayList<Entry>();
//        dataVals.add(new Entry(0, 5));
//        dataVals.add(new Entry(15, 5));
//
//        return dataVals;
//    }

}