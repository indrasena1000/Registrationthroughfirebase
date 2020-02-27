package com.example.registeration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class    Registeration extends AppCompatActivity {
      EditText eFullName,eMail,ePassword;
      Button eLogin;
      TextView loginHere;
      FirebaseAuth fAuth;
      ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeration);

        eFullName = (EditText)findViewById(R.id.FName);
        eMail = (EditText)findViewById(R.id.EMail);
        ePassword = (EditText)findViewById(R.id.PassWord);
        eLogin =(Button)findViewById(R.id.BtnRegister);
        loginHere = (TextView)findViewById(R.id.createAccount);

        fAuth = FirebaseAuth.getInstance();
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        if(fAuth.getCurrentUser()!= null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        eLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = eMail.getText().toString();
                String password = ePassword.getText().toString();

                if(TextUtils.isEmpty(email)){
                  eMail.setError("Enter Email Id");
                  return;
                }

                if(TextUtils.isEmpty(password)){
                    ePassword.setError("Enter Password");
                    return;
                }
                if(password.length()<6){
                    ePassword.setError("password must be atleast 6 letterds");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                //register user in firebase

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            fAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(Registeration.this, "Registration Succesfull", Toast.LENGTH_SHORT).show();
                                       // startActivity(new Intent(getApplicationContext(),MainActivity.class));

                                        progressBar.setVisibility(View.INVISIBLE);
                                     // eMail.setText("thankyou for registering enjoy our services");
                                    }else{
                                        Toast.makeText(Registeration.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                }

                            });
                            //Toast.makeText(Registeration.this, "Registeration Successfull", Toast.LENGTH_SHORT).show();
                          //startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }
                        else{
                            Toast.makeText(Registeration.this, "Error!" + task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                           progressBar.setVisibility(View.INVISIBLE);
                        }
                    }
                });
            }
        });
        loginHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             startActivity(new Intent(getApplicationContext(),Login.class));
             progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

}
