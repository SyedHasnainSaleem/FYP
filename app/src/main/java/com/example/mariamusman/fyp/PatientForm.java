package com.example.mariamusman.fyp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PatientForm extends AppCompatActivity implements View.OnClickListener{

    EditText efname, elname, ephone, eemail, epassword,eage;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private ProgressBar progressBar;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_form);

        efname = findViewById(R.id.efname);
        elname = findViewById(R.id.elname);
        ephone = findViewById(R.id.ephone);
        eemail= findViewById(R.id.eemail);
        epassword = findViewById(R.id.epassword);
        eage= findViewById(R.id.eage);
        progressBar = (ProgressBar) findViewById(R.id.progressBarOnPForm);
        progressBar.setVisibility(View.GONE);

        findViewById(R.id.submit).setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        //databaseReference = FirebaseDatabase.getInstance().getReference("Login").push().child("patient");

    }
    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            progressBar.setVisibility(View.GONE);
            Intent intent = new Intent(getApplicationContext(), Home.class);
            finish();
            startActivity(intent);
        }
    }
    private void registerUser() {
        final String firstname = efname.getText().toString().trim();
        final String lastname = elname.getText().toString().trim();
        final String phone = ephone.getText().toString().trim();
        final String email = eemail.getText().toString().trim();
        final String age = eage.getText().toString().trim();
        final String password = epassword.getText().toString().trim();

        if (firstname.isEmpty()) {
            efname.setError(getString(R.string.input_error_fname));
            efname.requestFocus();
            return;
        }
        if (lastname.isEmpty()) {
            elname.setError(getString(R.string.input_error_lname));
            elname.requestFocus();
            return;
        }


        if (email.isEmpty()) {
            eemail.setError(getString(R.string.input_error_email));
            eemail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            eemail.setError(getString(R.string.input_error_email_invalid));
            eemail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            epassword.setError(getString(R.string.input_error_password));
            epassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            epassword.setError(getString(R.string.input_error_password_length));
            epassword.requestFocus();
            return;
        }

        if (phone.isEmpty()) {
            ephone.setError(getString(R.string.input_error_phone));
            ephone.requestFocus();
            return;
        }

        if (ephone.length() != 10) {
            ephone.setError(getString(R.string.input_error_phone_invalid));
            ephone.requestFocus();
            return;
        }
        if (age.isEmpty()) {
            eage.setError(getString(R.string.input_error_age));
            eage.requestFocus();
            return;
        }
        if (age.length() != 3) {
            eage.setError(getString(R.string.input_error_age));
            eage.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            UserP user = new UserP(
                                    firstname,
                                    lastname,
                                    phone,
                                    email,
                                    age
                            );

                            FirebaseDatabase.getInstance().getReference("Patients")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(PatientForm.this, getString(R.string.registration_success), Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(getApplicationContext(), Login.class);
                                        startActivity(intent);
                                        finish();
                                    } else {

                                    }
                                }
                            });

                        } else {
                            Toast.makeText(PatientForm.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.submit:
                registerUser();
                break;
        }

    }
}
