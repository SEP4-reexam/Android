package Fragments;

import android.annotation.SuppressLint;
import android.database.sqlite.SQLiteDatabase;
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
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import Data.DatabaseHelper;


public class TrackCO2Data extends Fragment{

    LineChart lineChart;
    //  FloatingActionButton button;

    DatabaseHelper db;
    SQLiteDatabase sqLiteDatabase;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_track_humidity_data, container, false);

        lineChart = view.findViewById(R.id.line);
        addDataToGraph();
        lineChart.invalidate();

        db = new DatabaseHelper(getActivity());
        sqLiteDatabase = db.getWritableDatabase();
        saveToDatabase();

        return view;
    }
    public void addDataToGraph()
    {
        db = new DatabaseHelper(getActivity());


//        final ArrayList<Entry> yVals = new ArrayList<Entry>();
        //   final ArrayList<String> yData = db.queryYData();

        final ArrayList<String> xVals = new ArrayList<String>();
        final ArrayList<String> xData = new ArrayList<String>();

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                //add entries
//                for (int i = 0; i < db.queryYData().size(); i++) {
//                    {
//                        requireActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//
//                                yDataValues();
//                                System.out.println(yDataValues());
//                            }
//                        });
//                    }
//                    //pause between intervals
//                    {
//                        try {
//                            Thread.sleep(2000);
//                        } catch (NegativeArraySizeException | InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            }
//        }).start();

//        for (int i = 0; i < db.queryYData().size(); i++)
//        {
//            //Entry newEntry= new Entry(i, Float.parseFloat(db.queryYData().get(i)));
//            int max = 30;
//            int min = 5;
//            Random rand = new Random();
//            Entry newEntry = new Entry(i, rand.nextInt((max - min) + 1) + min);
//            yVals.add(newEntry);
//            System.out.println(yVals);
//        }

//
//        for (int i = 0; i < db.queryXData().size(); i++)
//        {
//            long offset = Timestamp.valueOf("2012-01-01 00:00:00").getTime();
//            long end = Timestamp.valueOf("2013-01-01 00:00:00").getTime();
//            long diff = end - offset + 1;
//            Timestamp rand = new Timestamp(offset + (long)(Math.random() * diff));
//            xVals.add(rand.toString());
//            System.out.println(xVals);
//        }

//        for (int i = 0; i < db.queryXData().size(); i++)
//        {
//            xVals.add(xData.get(i));
//        }

        LineDataSet lineDataSet = new LineDataSet(CurrentDataValues(), "Current CO2");

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);

        lineChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xVals));


        //LineDataSet dataSet = new LineDataSet(dataValues(), "Current CO2");
        LineDataSet dataSet2 = new LineDataSet(dataValuesRec(), "Recommended CO2");
//        dataSets.add(lineDataSet);
        dataSets.add(dataSet2);

        //Setting the color of the lines and the width of the highlighted values
        lineDataSet.setColors(Color.rgb(241, 196, 15));
        dataSet2.setColors(ColorTemplate.MATERIAL_COLORS);
        lineDataSet.setValueTextColor(Color.BLUE);
        dataSet2.setValueTextColor(Color.BLUE);
        lineDataSet.setValueTextSize(14f);
        dataSet2.setValueTextSize(14f);

        //Setting line width and other custom settings
        lineDataSet.setLineWidth(4);
        dataSet2.setLineWidth(4);
        lineDataSet.setDrawCircles(true);
        dataSet2.setDrawCircles(true);

        LineData data = new LineData(dataSets);
        lineChart.setData(data);

        //Animating line chart
        lineChart.animateX(2000, Easing.EaseInCubic);

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

    }

    private ArrayList<Entry> CurrentDataValues()
    {
        ArrayList<Entry> yVals = new ArrayList<Entry>();

        for (int i = 0; i < db.queryYData().size(); i++)
        {
            Timestamp timestamp = new Timestamp(new Date().getTime());

            // create  instance object
            Instant instant
                    = Instant.parse("2018-11-20T16:55:30.00Z");
            // print Instant Value
            System.out.println("Instant: " + instant);
            // get epochValue using getEpochSecond
            long epochValue = instant.getEpochSecond();
            // print results
            System.out.println("Java epoch Value: "
                    + epochValue);
            // create  instance object
            Instant instant1
                    = Instant.parse("2018-12-20T16:55:30.00Z");
            // print Instant Value
            System.out.println("Instant: " + instant1);

            // get epochValue using getEpochSecond
            long epochValue1 = instant1.getEpochSecond();

            // print results
            System.out.println("Java epoch Value: "
                    + epochValue1);

            int max = 70;
            int min = 55;
            Random rand = new Random();
            yVals.add(new Entry(0, rand.nextInt((max - min) + 1) + min));
            yVals.add(new Entry(epochValue1, rand.nextInt((max - min) + 1) + min));
        }
        return yVals;
    }

    private ArrayList<Entry> dataValuesRec()
    {
        Instant instant
                = Instant.parse("2030-11-20T16:55:30.00Z");

        // print Instant Value
        System.out.println("Instant: " + instant);

        // get epochValue using getEpochSecond
        long epochValue = instant.getEpochSecond();

        // print results
        System.out.println("Java epoch Value: "
                + epochValue);

        ArrayList<Entry> dataVals = new ArrayList<Entry>();
        dataVals.add(new Entry(0, 60));
        dataVals.add(new Entry(epochValue, 60));


        return dataVals;
    }
    public void saveToDatabase()
    {
        db = new DatabaseHelper(getActivity());
        String yValue = toString();

        db.saveData(yValue);

        addDataToGraph();

        db.close();
    }

}