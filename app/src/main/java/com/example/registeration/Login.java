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

public class Login extends AppCompatActivity {
     EditText Email,PassWord;
     Button Login;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    TextView CreateAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Email = (EditText)findViewById(R.id.EMail);
        PassWord = (EditText)findViewById(R.id.PassWord);
        Login = (Button)findViewById(R.id.BtnLogin);
        CreateAccount = (TextView)findViewById(R.id.createAccount);
        fAuth =FirebaseAuth.getInstance();
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = Email.getText().toString();
                String password = PassWord.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    Email.setError("Enter Email id");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    PassWord.setError("Enter Password");
                    return;
                }
                if (password.length() < 6){
                    PassWord.setError("Password must be Atleast 6 letters");
               }

                    progressBar.setVisibility(View.VISIBLE);

                    fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                             if(task.isSuccessful()){
                                 Toast.makeText(Login.this, "Login Succesfull", Toast.LENGTH_SHORT).show();
                                 startActivity(new Intent(getApplicationContext(),MainActivity.class));
                             }
                             else{
                                 Toast.makeText(Login.this, "Error!" + task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                                 progressBar.setVisibility(View.INVISIBLE);
                             }
                        }
                    });


            }
        });
        CreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),Registeration.class));
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }
}
