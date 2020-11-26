package com.example.mariamusman.fyp;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
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

public class MemoryProgress extends AppCompatActivity {

    android.support.v7.widget.Toolbar toolbar;

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    PieChart memoryPieChart;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_progress);



        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        pb = (ProgressBar) findViewById(R.id.memoryProgressBar);
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
                myRef.child("Patients").child(id).child("MemoryScore").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        SpeedScoreInfo scoreInfo = dataSnapshot.getValue(SpeedScoreInfo.class);
                        System.out.println("ID IS "+scoreInfo.latestTimeTaken);
                        System.out.println("Score: "+scoreInfo.score);
                        System.out.println("HighScoreTime: "+scoreInfo.timeTaken);




                        float[] yData = {scoreInfo.latestTimeTaken/1000,scoreInfo.timeTaken/1000};
                        String[] xData = {"LatestTime","HighScoreTime"};

                        memoryPieChart = (PieChart) findViewById(R.id.memoryPieChart);

                        memoryPieChart.setRotationEnabled(true);
                        memoryPieChart.setHoleRadius(25f);
                        memoryPieChart.setTransparentCircleAlpha(0);
                        memoryPieChart.setCenterText("Time In Seconds");
                        memoryPieChart.setCenterTextSize(10);

                        Description description = new Description();
                        description.setText(" ");
                        memoryPieChart.setDescription(description);

                        addDataSet(yData, xData);

                        pb.setVisibility(View.GONE);
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
        Legend legend = memoryPieChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setPosition(Legend.LegendPosition.LEFT_OF_CHART);

        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        memoryPieChart.setData(pieData);
        memoryPieChart.invalidate();


    }

    public void fetchPatientSpeedProgressPatientSide(){

                myRef.child("Patients").child(mAuth.getCurrentUser().getUid()).child("MemoryScore").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        SpeedScoreInfo scoreInfo = dataSnapshot.getValue(SpeedScoreInfo.class);
                        System.out.println("ID IS "+scoreInfo.latestTimeTaken);
                        System.out.println("Score: "+scoreInfo.score);
                        System.out.println("HighScoreTime: "+scoreInfo.timeTaken);




                        float[] yData = {scoreInfo.latestTimeTaken/1000,scoreInfo.timeTaken/1000};
                        String[] xData = {"LatestTime","HighScoreTime"};

                        memoryPieChart = (PieChart) findViewById(R.id.memoryPieChart);

                        memoryPieChart.setRotationEnabled(true);
                        memoryPieChart.setHoleRadius(25f);
                        memoryPieChart.setTransparentCircleAlpha(0);
                        memoryPieChart.setCenterText("Time In Secconds");
                        memoryPieChart.setCenterTextSize(10);


                        addDataSet(yData, xData);

                        Description description = new Description();
                        description.setText(" ");
                        memoryPieChart.setDescription(description);

                        pb.setVisibility(View.GONE);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }


}
