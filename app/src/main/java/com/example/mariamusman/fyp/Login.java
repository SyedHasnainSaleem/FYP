package com.example.mariamusman.fyp;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
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

import org.w3c.dom.Text;

public class Login extends AppCompatActivity {

    Button btnLogin;
    TextView txtSignUp;
    FirebaseAuth mAuth;
    EditText emailET;
    EditText passET;
    ProgressBar loginPB;
    boolean flag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);


        mAuth = FirebaseAuth.getInstance();

        btnLogin =(Button) findViewById(R.id.btnlogin);
        txtSignUp = (TextView) findViewById(R.id.txtSignUp);
        emailET = (EditText) findViewById(R.id.txtuser);
        passET = (EditText) findViewById(R.id.txtpass);
        loginPB = (ProgressBar) findViewById(R.id.loginProgressBar);
        flag=false;

        loginPB.setVisibility(View.GONE);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    loginPB.setVisibility(View.VISIBLE);
                    userAuthentication();

            }
        });

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this,Register.class);
                startActivity(i);
            }
        });

    }

    private void userAuthentication(){

        String email = emailET.getText().toString().trim();
        String password = passET.getText().toString().trim();


        if (email.isEmpty()) {
            emailET.setError(getString(R.string.input_error_email));
            emailET.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailET.setError(getString(R.string.input_error_email_invalid));
            emailET.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            passET.setError(getString(R.string.input_error_password));
            passET.requestFocus();
            return;
        }

        if (password.length() < 6) {
            passET.setError(getString(R.string.input_error_password_length));
            passET.requestFocus();
            return;
        }






        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if(task.isSuccessful()){

                    DatabaseReference databaseReference =FirebaseDatabase.getInstance().getReference().child("Patients");
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild(mAuth.getCurrentUser().getUid())){
                                flag = true;
                            }

                            if(flag){
                                UserGlobal.userType="patient";
                                loginPB.setVisibility(View.GONE);
                                Intent intent = new Intent(getApplicationContext(), Home.class);

                                startActivity(intent);
                                finish();
                            }else{
                                UserGlobal.userType="caregiver";
                                loginPB.setVisibility(View.GONE);
                                Intent intent = new Intent(getApplicationContext(), CaregiverHome.class);

                                startActivity(intent);
                                finish();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }


                    });
                }else{
                    loginPB.setVisibility(View.GONE);

                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
