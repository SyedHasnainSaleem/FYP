package com.example.mariamusman.fyp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class CaregiverForm extends AppCompatActivity implements View.OnClickListener{

    EditText efname, elname, ephone, eemail, epassword;
    ProgressBar progressBar;
    Spinner patientNameSpinner;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caregiver_form);

        boolean mSpinnerInitialized;

        efname = findViewById(R.id.efname);
        elname = findViewById(R.id.elname);
        ephone = findViewById(R.id.ephone);
        eemail= findViewById(R.id.eemail);
        epassword = findViewById(R.id.epassword);
        progressBar = (ProgressBar) findViewById(R.id.progressBarOnCForm);

        mSpinnerInitialized = false;
        progressBar.setVisibility(View.GONE);

        patientNameSpinner = (Spinner) findViewById(R.id.patientNameSpinner);


        FirebaseDatabase.getInstance().getReference().child("Patients").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final List<String> firstNameList = new ArrayList<>();

                for(DataSnapshot firstNameSnapshot : dataSnapshot.getChildren()){
                    if(!firstNameSnapshot.child("caregiverId").exists()) {
                        String fName = firstNameSnapshot.child("firstname").getValue(String.class);
                        firstNameList.add(fName);
                    }
                }

                ArrayAdapter<String> firstNameAdapter = new ArrayAdapter<String>(CaregiverForm.this,android.R.layout.simple_list_item_1,firstNameList);
                firstNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                patientNameSpinner.setAdapter(firstNameAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        findViewById(R.id.submit).setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();
        //databaseReference = FirebaseDatabase.getInstance().getReference("Caregiver").push().child("caregiver");

    }
    @Override
    protected void onStart() {
        super.onStart();

        if (mAuth.getCurrentUser() != null) {
            //handle the already login user
            progressBar.setVisibility(View.GONE);
            Intent intent = new Intent(getApplicationContext(), CaregiverHome.class);
            startActivity(intent);
            finish();
        }
    }

    private void registerUser() {
        final String firstname = efname.getText().toString().trim();
        final String lastname = elname.getText().toString().trim();
        final String phone = ephone.getText().toString().trim();
        final String email = eemail.getText().toString().trim();
        String password = epassword.getText().toString().trim();

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




        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {


                            readData(new FirebaseCallback() {
                                @Override
                                public void onCallBack(String key) {
                                    UserC user = new UserC(
                                            firstname,
                                            lastname,
                                            phone,
                                            email,
                                            key
                                            );
                                    FirebaseDatabase.getInstance().getReference("Caregiver")
                                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()) {
                                                progressBar.setVisibility(View.GONE);
                                                Toast.makeText(CaregiverForm.this, getString(R.string.registration_success), Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(getApplicationContext(), Login.class);
                                                startActivity(intent);
                                                finish();

                                            } else {

                                            }
                                        }
                                    });

                                    FirebaseDatabase.getInstance().getReference("Patients").child(key).child("caregiverId").setValue(mAuth.getCurrentUser().getUid()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(CaregiverForm.this, "Care giver added in patient", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                                }


                            });



                        } else {
                            Toast.makeText(CaregiverForm.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
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

    private void readData(final FirebaseCallback firebaseCallback){

        final String selectedPatientName = patientNameSpinner.getSelectedItem().toString();
        Query query = FirebaseDatabase.getInstance().getReference().child("Patients").orderByChild("firstname").equalTo(selectedPatientName);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            String key = "";
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot){
                for (DataSnapshot childSnapshot: dataSnapshot.getChildren()) {
                    key=childSnapshot.getKey();
                }
                firebaseCallback.onCallBack(key);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


    }

    private interface FirebaseCallback{
        void onCallBack(String key);
    }
}

