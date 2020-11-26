package com.example.mariamusman.fyp;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SpeedProgress extends AppCompatActivity {

    android.support.v7.widget.Toolbar toolbar;

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;

    BarChart speedBarChart;
    PieChart speedPieChart;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed_progress);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //speedBarChart = (BarChart) findViewById(R.id.speedBarChart);

        mAuth = FirebaseAuth.getInstance();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        pb = (ProgressBar) findViewById(R.id.speedProgressBar);
        pb.setVisibility(View.VISIBLE);

        if(UserGlobal.userType.equals("caregiver")) {
            fetchPatientSpeedProgress();
        }else{
            fetchPatientSpeedProgressPatientSide();
        }




    }


    public void fetchPatientSpeedProgress(){
        myRef.child("Caregiver").child(mAuth.getCurrentUser().getUid()).child("patientId").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String id = dataSnapshot.getValue(String.class);
                myRef.child("Patients").child(id).child("NumberDrillScore").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        SpeedScoreInfo scoreInfo = dataSnapshot.getValue(SpeedScoreInfo.class);
                        System.out.println("ID IS "+scoreInfo.latestTimeTaken);
                        System.out.println("Score: "+scoreInfo.score);
                        System.out.println("HighScoreTime: "+scoreInfo.timeTaken);


                        float[] yData = {scoreInfo.latestTimeTaken/1000,scoreInfo.timeTaken/1000};
                        String[] xData = {"LatestTime","HighScoreTime"};

                        speedPieChart = (PieChart) findViewById(R.id.speedPieChart);

                        speedPieChart.setRotationEnabled(true);
                        speedPieChart.setHoleRadius(25f);
                        speedPieChart.setTransparentCircleAlpha(0);
                        speedPieChart.setCenterText("Time In Seconds");
                        speedPieChart.setCenterTextSize(10);

                        Description description = new Description();
                        description.setText(" ");
                        speedPieChart.setDescription(description);

                        addDataSet(yData, xData);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id){
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                finish();
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                break;
        }


        return super.onOptionsItemSelected(item);
    }


    private void addDataSet(float[] yData, String[] xData) {


        ArrayList<PieEntry> yEntrys = new ArrayList<>();
        ArrayList<String> xEntrys = new ArrayList<>();

        for(int i = 0; i < yData.length; i++){
            yEntrys.add(new PieEntry(yData[i] , i));
        }

        for(int i = 1; i < xData.length; i++){
            xEntrys.add(xData[i]);
        }

        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yEntrys, "Scores");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(173,255,47));
        colors.add(Color.rgb(255,69,0));

        pieDataSet.setColors(colors);

        //add legend to chart
        Legend legend = speedPieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        speedPieChart.setData(pieData);
        speedPieChart.invalidate();
        pb.setVisibility(View.GONE);
    }

    public void fetchPatientSpeedProgressPatientSide(){

                myRef.child("Patients").child(mAuth.getCurrentUser().getUid()).child("NumberDrillScore").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        SpeedScoreInfo scoreInfo = dataSnapshot.getValue(SpeedScoreInfo.class);
                        System.out.println("ID IS "+scoreInfo.latestTimeTaken);
                        System.out.println("Score: "+scoreInfo.score);
                        System.out.println("HighScoreTime: "+scoreInfo.timeTaken);


                        float[] yData = {scoreInfo.latestTimeTaken/1000,scoreInfo.timeTaken/1000};
                        String[] xData = {"LatestTime","HighScoreTime"};

                        speedPieChart = (PieChart) findViewById(R.id.speedPieChart);

                        speedPieChart.setRotationEnabled(true);
                        speedPieChart.setHoleRadius(25f);
                        speedPieChart.setTransparentCircleAlpha(0);
                        speedPieChart.setCenterText("Time In Secconds");
                        speedPieChart.setCenterTextSize(10);

                        Description description = new Description();
                        description.setText(" ");
                        speedPieChart.setDescription(description);

                        addDataSet(yData, xData);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

}
