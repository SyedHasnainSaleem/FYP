package com.example.mariamusman.fyp;

import android.app.Activity;
import android.bluetooth.BluetoothAssignedNumbers;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {
    Button btnGames, btnProgress, btnTools, btnWB, btnNz, btnLocat;
    android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnGames = (Button) findViewById(R.id.btnGames);
        btnGames.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Home.this,Games.class);
                startActivity(in);
            }
        });

        btnProgress = (Button) findViewById(R.id.btnProgress);
        btnProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Home.this,Progress.class);
                startActivity(in);
            }
        });
        btnTools = (Button) findViewById(R.id.btnTools);
        btnTools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Home.this,Tools.class);
                startActivity(in);
            }
        });
        btnNz = (Button) findViewById(R.id.btnNz);
        btnNz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Home.this,com.example.fyp.namaz.MainActivity.class);
                startActivity(in);
            }
        });

        btnWB = (Button) findViewById(R.id.btnWB);
        btnWB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Home.this,WellBeing.class);
                startActivity(in);
            }
        });

        btnLocat = (Button) findViewById(R.id.btnLocat);
        btnLocat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Home.this,Location.class);
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
