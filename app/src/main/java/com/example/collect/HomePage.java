package com.example.collect;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collect.model.Adapter;
import com.example.collect.model.Item;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView objectList;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView nav_view;
    Adapter adapter;
    FirebaseFirestore fstore;
    FirestoreRecyclerAdapter<Item, ItemViewHolder> itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer);
        fstore = FirebaseFirestore.getInstance();
        nav_view = findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(this);
        objectList = findViewById(R.id.objectlist);

        Query query = fstore.collection("items").orderBy("title", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Item> allItems = new FirestoreRecyclerOptions.Builder<Item>()
                .setQuery(query,Item.class).build();

        itemAdapter = new FirestoreRecyclerAdapter<Item, ItemViewHolder>(allItems) {
            @Override
            protected void onBindViewHolder(@NonNull ItemViewHolder holder, int position, @NonNull Item model) {
                holder.objectTitle.setText(model.getTitle());
                holder.objectContent.setText(model.getContent());

                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(v.getContext(), ObjectDetails.class);
                        i.putExtra("title", model.getTitle());
                        i.putExtra("content", model.getContent());
                        v.getContext().startActivity(i);
                    }
                });

            }

            @NonNull
            @Override
            public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.collections,parent, false);
                return new ItemViewHolder(view);
            }
        };

        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(true);
        toggle.syncState();

        objectList.setLayoutManager(new LinearLayoutManager(this));
        objectList.setAdapter(itemAdapter);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.addObject:
                startActivity(new Intent(this, AddItem.class));
                break;
            case R.id.note_progress:
                startActivity(new Intent(this, NotesProgress.class));
                break;
            case R.id.logout:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
        return false;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{

        TextView objectTitle, objectContent;
        View view;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            objectTitle = itemView.findViewById(R.id.title);
            objectContent = itemView.findViewById(R.id.content);
            view = itemView;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        itemAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (itemAdapter != null)
            itemAdapter.stopListening();
    }
}