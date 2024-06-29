package com.example.helpingpeople;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminUmmaid extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    FoodAdapter foodAdapter;
    ArrayList<FoodUser> list;
    FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_ummaid);
        getSupportActionBar().setTitle("Admin Console");
        recyclerView = findViewById(R.id.rcviewu);
        databaseReference = firebaseDatabase.getInstance().getReference().child("admin").child("ummid2018").child("Ngo Request");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        foodAdapter = new FoodAdapter(this, list);
        recyclerView.setAdapter(foodAdapter);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                    FoodUser foodUser = snapshot1.getValue(FoodUser.class);
                    list.add(foodUser);
                }

                foodAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.adminupdate,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //adminupdate intent
        switch (item.getItemId()) {
            case R.id.uadmin:
                Intent intent = new Intent(AdminUmmaid.this, AdminUdpadte.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}