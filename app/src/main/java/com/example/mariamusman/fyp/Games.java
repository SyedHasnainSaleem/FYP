package com.example.mariamusman.fyp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class Games extends AppCompatActivity {
    Button btnMemory, btnProb, btnSpeed, btnAttention;
    android.support.v7.widget.Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnMemory = (Button) findViewById(R.id.btnMemory);
        btnProb = (Button) findViewById(R.id.btnProb);
        btnSpeed = (Button) findViewById(R.id.btnSpeed);
        btnAttention = (Button) findViewById(R.id.btnAttention);

        btnProb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), com.example.fyp.StartingScreenActivity.class);
                intent.putExtra("role",UserGlobal.userType);
                startActivity(intent);
            }
        });

        btnSpeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), com.example.fyp.numberdrill.MainActivity.class);
                intent.putExtra("role",UserGlobal.userType);
                startActivity(intent);
            }
        });

        btnMemory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), com.example.fyp.memory.MainActivity.class);
                intent.putExtra("role",UserGlobal.userType);
                startActivity(intent);
            }
        });

        btnAttention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), com.example.fyp.colorsplash.MainActivity.class);
                intent.putExtra("role",UserGlobal.userType);
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
