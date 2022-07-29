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

import java.sql.Time;
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
    ArrayList<Entry> yVals = new ArrayList<Entry>();
    DatabaseHelper db;
    SQLiteDatabase sqLiteDatabase;
    Random rand = new Random();
    int max = 70;
    int min = 67;
    Instant instant = Instant.now();
    long epochValue = instant.getEpochSecond();
    long convertedEpoch =
            (((((epochValue/60)/60)/24)/7)/12);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_track_humidity_data, container, false);

        lineChart = view.findViewById(R.id.line);
        addDataToGraph();

        //db.DeleteAllData();
        db = new DatabaseHelper(getActivity());
        sqLiteDatabase = db.getWritableDatabase();
        //saveToDatabase();

        return view;
    }
    public void addDataToGraph()
    {
        db = new DatabaseHelper(getActivity());
        yVals.add(new Entry(0, rand.nextInt((max - min) + 1) + min));

        new Thread(new Runnable() {
            @Override
            public void run() {
                //add entries
                for (int i = 0; i < 50; i++) {
                    {
                        requireActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                CurrentDataValues();
                            }
                        });
                    }
                    //pause between intervals
                    {
                        try {
                            Thread.sleep(2000);
                        } catch (NegativeArraySizeException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();

        LineDataSet lineDataSet = new LineDataSet(CurrentDataValues(), "Current CO2");
        LineDataSet dataSet2 = new LineDataSet(dataValuesRec(), "Recommended CO2");

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);
        dataSets.add(dataSet2);
        //Setting the color of the lines and the width of the highlighted values
        lineDataSet.setColors(Color.rgb(241, 196, 15));
        dataSet2.setColors(ColorTemplate.MATERIAL_COLORS);
        //lineDataSet.setValueTextColor(Color.BLUE);
        dataSet2.setValueTextColor(Color.BLUE);
        //lineDataSet.setValueTextSize(14f);
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

    }
    int t = 0;
    private ArrayList<Entry> CurrentDataValues()
    {
            t+=5;
//        for (int i = 0; i < 10; i++) {
            // create  instance object
            Instant instant = Instant.now();
            // print Instant Value
            System.out.println("Instant: " + instant);
            // get epochValue using getEpochSecond
            long epochValue = instant.getEpochSecond();
            // print results
            System.out.println("Java epoch Value: "
                    + epochValue);
            // create  instance object

        System.out.println(convertedEpoch);

            yVals.add(new Entry(convertedEpoch, rand.nextInt((max - min) + 1) + min));
            lineChart.notifyDataSetChanged();
            lineChart.moveViewToX(t);
            System.out.println("$$yVals$$: " + yVals);

        //}
        return yVals;

    }


    private ArrayList<Entry> dataValuesRec()
    {

        ArrayList<Entry> dataVals = new ArrayList<Entry>();

        dataVals.add(new Entry(0, 70));
        dataVals.add(new Entry(convertedEpoch, 70));

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