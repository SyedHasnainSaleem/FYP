package com.example.fyp.memory;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.Collections;

public class PlayActivity extends AppCompatActivity {

    TextView tv_score;
    ImageView iv_11, iv_12, iv_13, iv_14, iv_21, iv_22, iv_23, iv_24, iv_31, iv_32, iv_33, iv_34;

    Integer[] cardArray = {101,102,103,104,105,106,201,202,203,204,205,206};

    int img101, img102, img103, img104, img105, img106, img201, img202, img203, img204, img205, img206;

    int firstCard, secondCard;
    int clickFirst, clickSecond;
    int cardNumber = 1;

    int playerScore = 0;
    private Chronometer chronometer;
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private long pauseOffset;
    private boolean running;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);


        tv_score = (TextView) findViewById(R.id.tv_score);
        iv_11 = (ImageView) findViewById(R.id.iv_11);
        iv_12 = (ImageView) findViewById(R.id.iv_12);
        iv_13 = (ImageView) findViewById(R.id.iv_13);
        iv_14 = (ImageView) findViewById(R.id.iv_14);
        iv_21 = (ImageView) findViewById(R.id.iv_21);
        iv_22 = (ImageView) findViewById(R.id.iv_22);
        iv_23 = (ImageView) findViewById(R.id.iv_23);
        iv_24 = (ImageView) findViewById(R.id.iv_24);
        iv_31 = (ImageView) findViewById(R.id.iv_31);
        iv_32 = (ImageView) findViewById(R.id.iv_32);
        iv_33 = (ImageView) findViewById(R.id.iv_33);
        iv_34 = (ImageView) findViewById(R.id.iv_34);

        iv_11.setTag("0");
        iv_12.setTag("1");
        iv_13.setTag("2");
        iv_14.setTag("3");
        iv_21.setTag("4");
        iv_22.setTag("5");
        iv_23.setTag("6");
        iv_24.setTag("7");
        iv_31.setTag("8");
        iv_32.setTag("9");
        iv_33.setTag("10");
        iv_34.setTag("11");

        frontOfCardsResources();

        Collections.shuffle(Arrays.asList(cardArray));

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        mAuth = FirebaseAuth.getInstance();

        chronometer = findViewById(R.id.chronometer);
        chronometer.setFormat("Time: %s");
        chronometer.setBase(SystemClock.elapsedRealtime());


        startChronometer();


        iv_11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_11,theCard);

            }
        });

        iv_12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_12,theCard);

            }
        });

        iv_13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_13,theCard);

            }
        });

        iv_14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_14,theCard);

            }
        });

        iv_21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_21,theCard);

            }
        });

        iv_22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_22,theCard);

            }
        });

        iv_23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_23,theCard);

            }
        });

        iv_24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_24,theCard);

            }
        });

        iv_31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_31,theCard);


            }
        });

        iv_32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_32,theCard);

            }
        });

        iv_33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_33,theCard);

            }
        });

        iv_34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_34,theCard);

            }
        });


//        myRef.child("Patients").child(mAuth.getCurrentUser().getUid()).child("MemoryScore").child("timeTaken").setValue(0);
//        myRef.child("Patients").child(mAuth.getCurrentUser().getUid()).child("MemoryScore").child("latestTimeTaken").setValue(0);
//        myRef.child("Patients").child(mAuth.getCurrentUser().getUid()).child("MemoryScore").child("score").setValue(0);

    }

    private void doStuff(ImageView iv, int card){
        if(cardArray[card] == 101){
            iv.setImageResource(img101);
        } else if(cardArray[card] == 102){
            iv.setImageResource(img102);
        } else if(cardArray[card] == 103){
            iv.setImageResource(img103);
        } else if(cardArray[card] == 104){
            iv.setImageResource(img104);
        } else if(cardArray[card] == 105){
            iv.setImageResource(img105);
        } else if(cardArray[card] == 106){
            iv.setImageResource(img106);
        } else if(cardArray[card] == 201) {
            iv.setImageResource(img201);
        } else if(cardArray[card] == 202) {
            iv.setImageResource(img202);
        } else if(cardArray[card] == 203) {
            iv.setImageResource(img203);
        } else if(cardArray[card] == 204) {
            iv.setImageResource(img204);
        } else if(cardArray[card] == 205) {
            iv.setImageResource(img205);
        } else if(cardArray[card] == 206) {
            iv.setImageResource(img206);
        }

        if(cardNumber == 1){
            firstCard = cardArray[card];
            if(firstCard > 200){
                firstCard = firstCard - 100;
            }

            cardNumber = 2;
            clickFirst = card;

            iv.setEnabled(false);
        } else if(cardNumber == 2){
            secondCard = cardArray[card];
            if(secondCard > 200){
                secondCard = secondCard - 100;
            }

            cardNumber = 1;
            clickSecond = card;

            iv_11.setEnabled(false);
            iv_12.setEnabled(false);
            iv_13.setEnabled(false);
            iv_14.setEnabled(false);
            iv_21.setEnabled(false);
            iv_22.setEnabled(false);
            iv_23.setEnabled(false);
            iv_24.setEnabled(false);
            iv_31.setEnabled(false);
            iv_32.setEnabled(false);
            iv_33.setEnabled(false);
            iv_34.setEnabled(false);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    calculate();
                }
            }, 1000);
        }

    }

    private void calculate(){
        if(firstCard == secondCard){
            if(clickFirst == 0){
                iv_11.setVisibility(View.INVISIBLE);
            } else if(clickFirst == 1){
                iv_12.setVisibility(View.INVISIBLE);
            } else if(clickFirst == 2) {
                iv_13.setVisibility(View.INVISIBLE);
            } else if(clickFirst == 3) {
                iv_14.setVisibility(View.INVISIBLE);
            } else if(clickFirst == 4) {
                iv_21.setVisibility(View.INVISIBLE);
            } else if(clickFirst == 5) {
                iv_22.setVisibility(View.INVISIBLE);
            } else if(clickFirst == 6) {
                iv_23.setVisibility(View.INVISIBLE);
            } else if(clickFirst == 7) {
                iv_24.setVisibility(View.INVISIBLE);
            } else if(clickFirst == 8) {
                iv_31.setVisibility(View.INVISIBLE);
            } else if(clickFirst == 9) {
                iv_32.setVisibility(View.INVISIBLE);
            } else if(clickFirst == 10) {
                iv_33.setVisibility(View.INVISIBLE);
            } else if(clickFirst == 11) {
                iv_34.setVisibility(View.INVISIBLE);
            }

            if(clickSecond == 0){
                iv_11.setVisibility(View.INVISIBLE);
            } else if(clickSecond == 1){
                iv_12.setVisibility(View.INVISIBLE);
            } else if(clickSecond == 2) {
                iv_13.setVisibility(View.INVISIBLE);
            } else if(clickSecond == 3) {
                iv_14.setVisibility(View.INVISIBLE);
            } else if(clickSecond == 4) {
                iv_21.setVisibility(View.INVISIBLE);
            } else if(clickSecond == 5) {
                iv_22.setVisibility(View.INVISIBLE);
            } else if(clickSecond == 6) {
                iv_23.setVisibility(View.INVISIBLE);
            } else if(clickSecond == 7) {
                iv_24.setVisibility(View.INVISIBLE);
            } else if(clickSecond == 8) {
                iv_31.setVisibility(View.INVISIBLE);
            } else if(clickSecond == 9) {
                iv_32.setVisibility(View.INVISIBLE);
            } else if(clickSecond == 10) {
                iv_33.setVisibility(View.INVISIBLE);
            } else if(clickSecond == 11) {
                iv_34.setVisibility(View.INVISIBLE);
            }

            playerScore++;
            tv_score.setText("Score: " +playerScore);


        } else {
            iv_11.setImageResource(R.drawable.img_icon);
            iv_12.setImageResource(R.drawable.img_icon);
            iv_13.setImageResource(R.drawable.img_icon);
            iv_14.setImageResource(R.drawable.img_icon);
            iv_21.setImageResource(R.drawable.img_icon);
            iv_22.setImageResource(R.drawable.img_icon);
            iv_23.setImageResource(R.drawable.img_icon);
            iv_24.setImageResource(R.drawable.img_icon);
            iv_31.setImageResource(R.drawable.img_icon);
            iv_32.setImageResource(R.drawable.img_icon);
            iv_33.setImageResource(R.drawable.img_icon);
            iv_34.setImageResource(R.drawable.img_icon);

        }

        iv_11.setEnabled(true);
        iv_12.setEnabled(true);
        iv_13.setEnabled(true);
        iv_14.setEnabled(true);
        iv_21.setEnabled(true);
        iv_22.setEnabled(true);
        iv_23.setEnabled(true);
        iv_24.setEnabled(true);
        iv_31.setEnabled(true);
        iv_32.setEnabled(true);
        iv_33.setEnabled(true);
        iv_34.setEnabled(true);

        checkEnd();

    }

    private void checkEnd(){
        if(iv_11.getVisibility() == View.INVISIBLE &&
                iv_12.getVisibility() == View.INVISIBLE &&
                iv_13.getVisibility() == View.INVISIBLE &&
                iv_14.getVisibility() == View.INVISIBLE &&
                iv_21.getVisibility() == View.INVISIBLE &&
                iv_22.getVisibility() == View.INVISIBLE &&
                iv_23.getVisibility() == View.INVISIBLE &&
                iv_24.getVisibility() == View.INVISIBLE &&
                iv_31.getVisibility() == View.INVISIBLE &&
                iv_32.getVisibility() == View.INVISIBLE &&
                iv_33.getVisibility() == View.INVISIBLE &&
                iv_34.getVisibility() == View.INVISIBLE){

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(PlayActivity.this);
            alertDialogBuilder
                    .setMessage("GAME OVER!!\nPlayer Score: "+playerScore)
                    .setCancelable(false)
                    .setPositiveButton("NEW", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(getApplicationContext(), PlayActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .setNegativeButton("EXIT", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    });


            String role = "";
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                role = extras.getString("role");
            }


            if(!role.isEmpty() || !role.equals(" ")) {
                if (role.equals("patient")) {
                    fetchPreviousScores(new FirebaseCallback() {
                        @Override
                        public void onCallBack(long highScore, long highScoreTimeTaken) {
                            pauseChronometer();

                            if ((highScoreTimeTaken > (SystemClock.elapsedRealtime() - chronometer.getBase())) || highScoreTimeTaken == -1) {
                                myRef.child("Patients").child(mAuth.getCurrentUser().getUid()).child("MemoryScore").child("timeTaken").setValue(SystemClock.elapsedRealtime() - chronometer.getBase());
                                myRef.child("Patients").child(mAuth.getCurrentUser().getUid()).child("MemoryScore").child("latestTimeTaken").setValue(SystemClock.elapsedRealtime() - chronometer.getBase());
                            } else {
                                myRef.child("Patients").child(mAuth.getCurrentUser().getUid()).child("MemoryScore").child("latestTimeTaken").setValue(SystemClock.elapsedRealtime() - chronometer.getBase());
                            }
                            myRef.child("Patients").child(mAuth.getCurrentUser().getUid()).child("MemoryScore").child("score").setValue(playerScore);

                            resetChronometer();
                        }
                    });
                }else{
                    pauseChronometer();
                    resetChronometer();
                }
            }


            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
    }



    private void frontOfCardsResources(){

        img101 = R.drawable.img_101;
        img102 = R.drawable.img_102;
        img103 = R.drawable.img_103;
        img104 = R.drawable.img_104;
        img105 = R.drawable.img_105;
        img106 = R.drawable.img_106;
        img201 = R.drawable.img_201;
        img202 = R.drawable.img_202;
        img203 = R.drawable.img_203;
        img204 = R.drawable.img_204;
        img205 = R.drawable.img_205;
        img206 = R.drawable.img_206;

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

        myRef.child("Patients").child(mAuth.getCurrentUser().getUid()).child("MemoryScore").addListenerForSingleValueEvent(new ValueEventListener() {
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
