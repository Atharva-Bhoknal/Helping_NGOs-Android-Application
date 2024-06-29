package com.example.helpingpeople;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminDisha extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    dishaAdapter dishaAdapter;
    ArrayList<dishaUser> list;
    FirebaseDatabase firebaseDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_disha);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getSupportActionBar().setTitle("Admin Colsole");


        recyclerView=findViewById(R.id.rcviewd);
        databaseReference= firebaseDatabase.getInstance().getReference().child("admin").child("disha2018").child("Ngo Request");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list=new ArrayList<>();
        dishaAdapter=new dishaAdapter(this,list);
        recyclerView.setAdapter(dishaAdapter);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snapshot1 : snapshot.getChildren()){

                    dishaUser dishaUser= snapshot1.getValue(dishaUser.class);
                    list.add(dishaUser);
                }

                dishaAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.adminupdate,menu);
//        return true;
//    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        //adminupdate intent
//        switch (item.getItemId()) {
//            case R.id.uadmin:
//                Intent intent = new Intent(AdminDisha.this, AdminUdpadte.class);
//                startActivity(intent);
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
