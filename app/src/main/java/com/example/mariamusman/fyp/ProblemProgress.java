package com.example.mariamusman.fyp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProblemProgress extends AppCompatActivity {

    android.support.v7.widget.Toolbar toolbar;

    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;

    BarChart problemBarChart;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_problem_progress);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        problemBarChart = (BarChart) findViewById(R.id.problemBarChart);

        pb = (ProgressBar) findViewById(R.id.problemProgressBar);
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
                myRef.child("Patients").child(id).child("QuizScore").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ProblemScoreInfo problemScoreInfo = dataSnapshot.getValue(ProblemScoreInfo.class);
                        System.out.println("latestTime "+problemScoreInfo.latestTimetaken);
                        System.out.println("latestScore: "+problemScoreInfo.latestScore);
                        System.out.println("HighScore: "+problemScoreInfo.score);
                        System.out.println("HighScoreTime: "+problemScoreInfo.timetaken);

                        ArrayList<BarEntry> barEntries = new ArrayList<>();
                        barEntries.add(new BarEntry(problemScoreInfo.latestScore,problemScoreInfo.latestTimetaken/1000));
                        barEntries.add(new BarEntry(problemScoreInfo.score,problemScoreInfo.timetaken/1000));

                        BarDataSet barDataSet = new BarDataSet(barEntries, "TIME IN SECONDS");
                        barDataSet.setBarBorderWidth(0.9f);



                        ArrayList<String> xAxis = new ArrayList<>();
                        xAxis.add("Latest Score");
                        xAxis.add("High Score");


                        BarData theData = new BarData(barDataSet);


                        problemBarChart.setData(theData);
                        problemBarChart.setTouchEnabled(false);
                        problemBarChart.setDragEnabled(false);
                        problemBarChart.setScaleEnabled(true);
                        Description description = new Description();
                        description.setText("Latest and High");
                        problemBarChart.setDescription(description);
                        problemBarChart.invalidate();

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

    public void fetchPatientSpeedProgressPatientSide(){

                myRef.child("Patients").child(mAuth.getCurrentUser().getUid()).child("QuizScore").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ProblemScoreInfo problemScoreInfo = dataSnapshot.getValue(ProblemScoreInfo.class);
                        System.out.println("latestTime "+problemScoreInfo.latestTimetaken);
                        System.out.println("latestScore: "+problemScoreInfo.latestScore);
                        System.out.println("HighScore: "+problemScoreInfo.score);
                        System.out.println("HighScoreTime: "+problemScoreInfo.timetaken);

                        ArrayList<BarEntry> barEntries = new ArrayList<>();
                        barEntries.add(new BarEntry(problemScoreInfo.latestScore,problemScoreInfo.latestTimetaken/1000));
                        barEntries.add(new BarEntry(problemScoreInfo.score,problemScoreInfo.timetaken/1000));

                        BarDataSet barDataSet = new BarDataSet(barEntries, "TIME IN SECONDS");
                        barDataSet.setBarBorderWidth(0.9f);



                        ArrayList<String> xAxis = new ArrayList<>();
                        xAxis.add("Latest Score");
                        xAxis.add("High Score");

//                        barDataSet.setStackLabels(new String[] {"Latest","High"});
                        //barDataSet.setStackLabels(new String[] {"LatestScore", "HighScore"});

                        BarData theData = new BarData(barDataSet);


                        problemBarChart.setData(theData);
                        problemBarChart.setTouchEnabled(false);
                        problemBarChart.setDragEnabled(false);
                        problemBarChart.setScaleEnabled(true);
                        Description description = new Description();
                        description.setText("Latest and High");
                        problemBarChart.setDescription(description);
                        problemBarChart.invalidate();

                        pb.setVisibility(View.GONE);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

}
