package com.example.mariamusman.fyp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class CaregiverHome extends AppCompatActivity {
    Button btnGames, btnProgress, btnTools, btnWB, btnNz, btnLocat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caregiver_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button progressBtn = (Button) findViewById(R.id.progressBtnC);
        Button profileBtn = (Button) findViewById(R.id.profileBtnC);

        progressBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CaregiverHome.this, SelectGameProgress.class);
                startActivity(intent);
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CaregiverHome.this, PatientProfile.class);
                startActivity(intent);
            }
        });

        btnGames = (Button) findViewById(R.id.btnGames);
        btnGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(CaregiverHome.this,Games.class);
                startActivity(in);
            }
        });

        btnTools = (Button) findViewById(R.id.btnTools);
        btnTools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(CaregiverHome.this,Tools.class);
                startActivity(in);
            }
        });
        btnNz = (Button) findViewById(R.id.btnNz);
        btnNz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(CaregiverHome.this,com.example.fyp.namaz.MainActivity.class);
                startActivity(in);
            }
        });

        btnWB = (Button) findViewById(R.id.btnWB);
        btnWB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(CaregiverHome.this,WellBeing.class);
                startActivity(in);
            }
        });

        btnLocat = (Button) findViewById(R.id.btnLocat);
        btnLocat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(CaregiverHome.this,Location.class);
                startActivity(in);
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
