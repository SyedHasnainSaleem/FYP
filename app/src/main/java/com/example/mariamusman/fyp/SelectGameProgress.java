package com.example.mariamusman.fyp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class SelectGameProgress extends AppCompatActivity {

    Button attenionProgress, memoryProgress, speedProgress, problemProgress;
    android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_game_progress);


        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        problemProgress = (Button) findViewById(R.id.progressBtnProb);
        speedProgress = (Button) findViewById(R.id.progressBtnSpeed);
        memoryProgress = (Button) findViewById(R.id.progressBtnMemory);
        attenionProgress = (Button) findViewById(R.id.progressBtnAttention);


        problemProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectGameProgress.this, ProblemProgress.class);
                startActivity(intent);
            }
        });

        speedProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (SelectGameProgress.this, SpeedProgress.class);
                startActivity(intent);
            }
        });

        memoryProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectGameProgress.this, MemoryProgress.class);
                startActivity(intent);
            }
        });

        attenionProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SelectGameProgress.this, AttentionProgress.class);
                startActivity(intent);
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
}
