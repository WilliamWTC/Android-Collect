package com.example.collect;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private TextView registeruser;
    private EditText fullname, textEmail, textPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();
        registeruser = findViewById(R.id.registerUser);
        fullname = findViewById(R.id.fullname);
        textEmail = findViewById(R.id.email);
        textPassword = findViewById(R.id.password);

        registeruser.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registerUser:
                registeruser();
                break;
        }
    }

    private void registeruser() {
        String full_name = fullname.getText().toString().trim();
        String email = textEmail.getText().toString().trim();
        String password = textPassword.getText().toString().trim();

        if(full_name.isEmpty()){
            fullname.setError("Full name is required");
            fullname.requestFocus();
            return;
        }

        if(email.isEmpty()){
            textEmail.setError("Email is required");
            textEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            textEmail.setError("Please provide a valid email");
            textEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            textPassword.setError("Password is required");
            textPassword.requestFocus();
            return;
        }

        if(password.length() < 7){
            textPassword.setError("Password must contain more than 7 characters");
            textPassword.requestFocus();
            return;
        }
    }
}