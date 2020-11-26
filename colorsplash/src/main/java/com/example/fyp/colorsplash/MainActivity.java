package com.example.fyp.colorsplash;

import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView img_question, img_answer;
    Button btn_answer, btn_refresh;
    int score;

    private Chronometer chronometer;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private long pauseOffset;
    private boolean running;

    int array_images[] = new int[]{
            R.drawable.a,
            R.drawable.b,
            R.drawable.c,
            R.drawable.de,
            R.drawable.e,
            R.drawable.f,
            R.drawable.a,
            R.drawable.b,
            R.drawable.c
    };
    int correct_answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_splash);

//        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
//        toolbar.setTitle("YOUR MEMORY");
//        setSupportActionBar(toolbar);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();

        chronometer = findViewById(R.id.chronometer);
        chronometer.setFormat("Time: %s");
        chronometer.setBase(SystemClock.elapsedRealtime());


        startChronometer();

        score =0;
        img_question = (ImageView)findViewById(R.id.img_question);
        btn_answer = (Button)findViewById(R.id.btn_answer);
        btn_answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, ChooseAnswer.class);
                startActivityForResult(intent,1999);
            }
        });
        randomImage();

        btn_refresh = (Button) findViewById(R.id.refreshBtn);
        btn_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                randomImage();
                img_answer = (ImageView)findViewById(R.id.img_answer);
                img_answer.setImageResource(R.drawable.imageuser);
            }
        });


    }
    @Override
    protected void onActivityResult(int requestcode, int resultcode, Intent data){
        super.onActivityResult(requestcode, resultcode, data);
        if(requestcode==1999) {
            if (data != null) {
                int answer_resource_id = data.getIntExtra("Answer", 0);
                img_answer = (ImageView) findViewById(R.id.img_answer);
                img_answer.setImageResource(answer_resource_id);


                if (answer_resource_id == correct_answer) {
                    score++;
                    Toast.makeText(this, "Good! You have good memory ", Toast.LENGTH_SHORT).show();
                    if (score == 5) {
                        String role = "";
                        Bundle extras = getIntent().getExtras();
                        if (extras != null) {
                            role = extras.getString("role");
                        }


                        if (!role.isEmpty() || !role.equals(" ")) {
                            if (role.equals("patient")) {
                                fetchPreviousScores(new FirebaseCallback() {
                                    @Override
                                    public void onCallBack(long highScore, long highScoreTimeTaken) {
                                        pauseChronometer();
                                        if ((highScoreTimeTaken > (SystemClock.elapsedRealtime() - chronometer.getBase())) || highScoreTimeTaken == -1) {
                                            myRef.child("Patients").child(mAuth.getCurrentUser().getUid()).child("ColorScore").child("timeTaken").setValue(SystemClock.elapsedRealtime() - chronometer.getBase());
                                            myRef.child("Patients").child(mAuth.getCurrentUser().getUid()).child("ColorScore").child("latestTimeTaken").setValue(SystemClock.elapsedRealtime() - chronometer.getBase());
                                        } else {
                                            myRef.child("Patients").child(mAuth.getCurrentUser().getUid()).child("ColorScore").child("latestTimeTaken").setValue(SystemClock.elapsedRealtime() - chronometer.getBase());
                                        }
                                        myRef.child("Patients").child(mAuth.getCurrentUser().getUid()).child("ColorScore").child("score").setValue(score);

                                        resetChronometer();
                                    }
                                });
                            } else {
                                System.out.println("INSIDE ELSE CAREGIVER");
                                pauseChronometer();
                                resetChronometer();
                            }
                        }


                        Intent intent = new Intent(MainActivity.this, FinishedActivity.class);
                        finish();
                        startActivity(intent);
                    }
                } else {
                    if (score > 0) {
                        score--;
                    } else {
                        score = 0;
                    }

                    Toast.makeText(this, "Oopps! You Forgot", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu){
//        getMenuInflater().inflate(R.menu.menu_main,menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item){
//
//        int id = item.getItemId();
////        if(id == R.id.menu_re_new ){
////            randomImage();
////            img_answer = (ImageView)findViewById(R.id.img_answer);
////            img_answer.setImageResource(R.drawable.imageuser);
////        }
//
//        return true;
//    }
    private void randomImage(){
        int img_resource =array_images[new Random().nextInt(9)];
        img_question.setImageResource(img_resource);
        correct_answer = img_resource;
    }


    public void startChronometer() {
        if (!running) {
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
        }
    }

    public void pauseChronometer() {
        if (running) {
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
        }
    }

    public void resetChronometer() {
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
    }


    public void fetchPreviousScores(final FirebaseCallback firebaseCallback){

        myRef.child("Patients").child(mAuth.getCurrentUser().getUid()).child("ColorScore").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long score = 0;
                long time = -1;
                if(dataSnapshot.child("score").getValue(Long.class)!=null){
                    score =  dataSnapshot.child("score").getValue(Long.class);
                }
                if(dataSnapshot.child("timeTaken").getValue(Long.class)!=null){
                    time = dataSnapshot.child("timeTaken").getValue(Long.class);
                }


                firebaseCallback.onCallBack(score,time);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private interface FirebaseCallback{
        void onCallBack(long highScore, long highScoreTimeTaken);
    }
}

