package com.example.collect;

import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class AddItem extends AppCompatActivity {
    FirebaseFirestore fstore;
    EditText objectTitle, objectContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fstore = FirebaseFirestore.getInstance();
        objectContent = findViewById(R.id.addObjectContent);
        objectTitle = findViewById(R.id.addItemTitle);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oTitle = objectTitle.getText().toString();
                String oContent = objectContent.getText().toString();

                if(oTitle.isEmpty() || oContent.isEmpty()){
                    Toast.makeText(AddItem.this, "Your Category title/Item field can't be Empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                DocumentReference docref = fstore.collection("items").document();
                Map<String,Object> item = new HashMap<>();
                item.put("title",oTitle);
                item.put("content",oContent);
                docref.set(item).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(AddItem.this, "Items saved", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AddItem.this, "An Error occurred whilst saving the items: Try again", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}