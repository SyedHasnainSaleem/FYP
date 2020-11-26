package com.example.mariamusman.fyp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class Register extends Activity {
    Button SignCare,SignPat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);


        SignCare = (Button) findViewById(R.id.SignCare);
        SignPat = (Button) findViewById(R.id.SignPat);

        SignCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this,CaregiverForm.class);
                startActivity(intent);
            }
        });
        SignPat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(Register.this,PatientForm.class);
                startActivity(in);
            }
        });


    }
}
