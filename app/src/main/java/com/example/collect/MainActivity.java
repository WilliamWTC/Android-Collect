package com.example.collect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView register, loginButton;
    private FirebaseAuth mAuth;
    private EditText textEmail, textPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        register = findViewById(R.id.register);
        loginButton = findViewById(R.id.login);
        textEmail = findViewById(R.id.email);
        textPassword = findViewById(R.id.password);

        loginButton.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register:
                startActivity(new Intent(this, RegisterUser.class));
                break;
            case R.id.login:
                userLogin();
                break;
        }
    }

    private void userLogin() {
        String email = textEmail.getText().toString().trim();
        String password = textPassword.getText().toString().trim();

        if(email.isEmpty()){
            textEmail.setError("Eamil is required");
            textEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            textEmail.setError("Please enter a valid email!");
            textEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            textPassword.setError("Password is required!");
            textPassword.requestFocus();
            return;
        }

        if(password.length() < 7){
            textPassword.setError("Password must contain atleast 7 characters!");
            textPassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(MainActivity.this, HomePage.class));
                }else{
                    Toast.makeText(MainActivity.this, "Login Failed! Check your credentials", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}