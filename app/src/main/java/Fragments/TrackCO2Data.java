package Fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.and.sauna.R;
import com.and.sauna.networking.SaunaDataAPI;
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
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import Data.MeasurementsData;
import io.realm.Realm;
import io.realm.RealmResults;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.converter.gson.GsonConverterFactory;


public class TrackCO2Data extends Fragment{

    //TODO: The value of the XAxis (of the line) MUST always start from 0 and then be incremented.
    // The value of the YAxis will be the CURRENT CO2/Temp/Humidity levels.
    // The XAxis MUST be mapped to a timestamp of the current time (local timestamp)

    Date localTime = Calendar.getInstance().getTime();
    String BASE_URL = "localhost:8090/";
    SaunaDataAPI saunaDataAPI;

    LineChart lineChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_track_humidity_data, container, false);

        lineChart = view.findViewById(R.id.line);
        addDataToGraph();
        setupApi();
        CurrentDataValues(10, 20);

        return view;
    }

    private void setupApi() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        saunaDataAPI = new retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(SaunaDataAPI.class);
    }


    public void addDataToGraph()
    {

        //Adding data to the graph
      //  LineDataSet lineDataSet = new LineDataSet(CurrentDataValues(), "Current CO2");
     //   LineDataSet dataSet2 = new LineDataSet(dataValuesRec(), "Recommended CO2");

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
      //  dataSets.add(lineDataSet);
       // dataSets.add(dataSet2);

        LineData data = new LineData(dataSets);
        lineChart.setData(data);

        //Setting the color of the lines and the width of the highlighted values
      //  lineDataSet.setColors(Color.rgb(241, 196, 15));
      //  dataSet2.setColors(ColorTemplate.MATERIAL_COLORS);
      //  lineDataSet.setValueTextColor(Color.BLACK);
      //  dataSet2.setValueTextColor(Color.BLUE);
     //   lineDataSet.setValueTextSize(14f);
      //  dataSet2.setValueTextSize(14f);

        //Setting line width and other custom settings
     //   lineDataSet.setLineWidth(4);
      //  dataSet2.setLineWidth(4);
      //  lineDataSet.setDrawCircles(true);
       // dataSet2.setDrawCircles(true);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);



        //Animating line chart
        lineChart.animateX(4000);
        lineChart.invalidate();

        lineChart.setPinchZoom(true);

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

        //Formatting XAxis on the graph
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularity(1f); // one hour
        xAxis.setValueFormatter(new ValueFormatter() {

            private final SimpleDateFormat mFormat = new SimpleDateFormat("HH:mm:ss", Locale.ENGLISH);


            @Override
            public String getFormattedValue(float value) {
                long millis = TimeUnit.HOURS.toMillis((long) value);
                return mFormat.format(new Date(millis));
            }
        });

    }

    private void CurrentDataValues(int count, float range)
    {

    long now = TimeUnit.MILLISECONDS.toHours(System.currentTimeMillis());

    float to = now + count;

//    SaunaDataAPI.getHistoricalData(
//
//    )


    ArrayList<Entry> values = new ArrayList<Entry>();
        ArrayList<Entry> dataVals = new ArrayList<Entry>();

        for (float x = now; x < to; x++) {

            float y = getRandom(range, 50);
        values.add(new Entry(x, y)); // add one entry per hour
            dataVals.add(new Entry(x, 70f));
            dataVals.add(new Entry(x, 70f));
        }

//
//        Random rand = new Random();
//        int max = 100;
//        int min = 0;
//
//        for (float x = now; x < to; x++) {
//            values.add(new Entry(x, rand.nextInt((max - min) + 1) + min));
//        }
        LineDataSet lineDataSet =  new LineDataSet(values, "Current CO2");

        LineDataSet dataSet2 = new LineDataSet(dataVals, "Recommended CO2");

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);
        dataSets.add(dataSet2);

//        LineData data = new LineData(dataSets);
//        lineChart.setData(data);

        //Setting the color of the lines and the width of the highlighted values
        dataSet2.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet2.setValueTextColor(Color.BLUE);
        dataSet2.setValueTextSize(14f);

        //Setting line width and other custom settings
        dataSet2.setLineWidth(4);
        dataSet2.setDrawCircles(true);

        LineData data = new LineData(dataSets);
        lineChart.setData(data);

        //Setting the color of the lines and the width of the highlighted values
        lineDataSet.setColors(Color.rgb(241, 196, 15));

        lineDataSet.setValueTextColor(Color.BLACK);

        lineDataSet.setValueTextSize(14f);


        //Setting line width and other custom settings
        lineDataSet.setLineWidth(4);

        lineDataSet.setDrawCircles(true);



        lineChart.moveViewTo(10f, 80f, YAxis.AxisDependency.RIGHT);


       // return values;

    }

    private void dataValuesRec()
    {

      //  return dataVals;
    }

    public void saveToDatabase()
    {


        Realm realm = Realm.getDefaultInstance();

        realm.beginTransaction();

        MeasurementsData measurementsData1 = new MeasurementsData(0f, 5f, localTime);
        realm.copyToRealm(measurementsData1);
        MeasurementsData measurementsData2 = new MeasurementsData(1f, 25f, localTime);
        realm.copyToRealm(measurementsData2);
        MeasurementsData measurementsData3 = new MeasurementsData(2f, 20f, localTime);
        realm.copyToRealm(measurementsData3);
        MeasurementsData measurementsData4 = new MeasurementsData(3f, 29f, localTime);
        realm.copyToRealm(measurementsData4);

        realm.commitTransaction();

        RealmResults<MeasurementsData> results = realm.where(MeasurementsData.class).findAll();

//         //Formatting XAxis on the graph
//        XAxis xAxis = lineChart.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.TOP_INSIDE);
//        xAxis.setCenterAxisLabels(true);
//        xAxis.setGranularity(1f); // one hour
//        xAxis.setValueFormatter(new ValueFormatter() {
//
//            private final SimpleDateFormat mFormat = new SimpleDateFormat("hh:mm:ss", Locale.ENGLISH);
//
//
//            @Override
//            public String getFormattedValue(float value) {
//                long millis = TimeUnit.HOURS.toMillis((long) value);
//                return mFormat.format(new Date(millis));
//            }
//        });



    }

    protected float getRandom(float range, float start) {
        return (float) (Math.random() * range) + start;
    }


}

















//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                //add entries
//                for (int i = 0; i < 100; i++) {
//                    {
//                        requireActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//
//                                CurrentDataValues();
//                                dataValuesRec();
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







//
//    Instant instant = Instant.now();
//    long epochValue = instant.getEpochSecond();
//    long convertedEpoch =
//            (((((epochValue/60)/60)/24)/7)/12);






////            t+=5;
////        for (int i = 0; i < 10; i++) {
//            // create  instance object
//            Instant instant = Instant.now();
//            // print Instant Value
//            System.out.println("Instant: " + instant);
//            // get epochValue using getEpochSecond
//            long epochValue = instant.getEpochSecond();
//            // print results
//            System.out.println("Java epoch Value: "
//                    + epochValue);
//            // create  instance object


// yVals.add(new Entry(2, rand.nextInt((max - min) + 1) + min));
//    lineChart.notifyDataSetChanged();

//  System.out.println("$$yVals$$: " + yVals);

//    int t = 0;