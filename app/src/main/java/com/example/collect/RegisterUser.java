package com.example.collect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

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

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(full_name, email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterUser.this, "Successfully Registered!", Toast.LENGTH_LONG).show();
                                    }else {
                                        Toast.makeText(RegisterUser.this, "Registration Failed", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }else {
                            Toast.makeText(RegisterUser.this, "Failed to Register!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}