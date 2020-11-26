package com.example.fyp.memory;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btn_play, btn_about;
    TextView tv_title;
    String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_memory);

        btn_play = (Button) findViewById(R.id.btn_play);
        btn_about = (Button) findViewById(R.id.btn_about);
        tv_title = (TextView) findViewById(R.id.tv_title);

        Typeface myCustomFont = Typeface.createFromAsset(getAssets(),"fonts/SUPERHERO.ttf");
        tv_title.setTypeface(myCustomFont);



        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    role = extras.getString("role");
                    if(!role.isEmpty() || !role.equals(" ")){
                        intent.putExtra("role",role);
                    }
                }
                startActivity(intent);
            }
        });

        btn_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    role = extras.getString("role");
                    if(!role.isEmpty() || !role.equals(" ")){
                        intent.putExtra("role",role);
                    }
                }
                startActivity(intent);
            }
        });
    }

}
