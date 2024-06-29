package com.example.helpingpeople;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Cloth_Section extends AppCompatActivity {

    Button ngoBackBtn, ngo1Btn, ngo2Btn, ngo3Btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloth_section);

        ngoBackBtn=findViewById(R.id.ngoBackBtn);
        ngo1Btn=findViewById(R.id.ngo1Btn);
        ngo2Btn=findViewById(R.id.ngo2Btn);
        ngo3Btn=findViewById(R.id.ngo3Btn);

        ngo1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Cloth_Section.this,ngo1map.class));
            }
        });

        ngo2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Cloth_Section.this,ngo2map.class));
            }
        });

        ngo3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Cloth_Section.this,ngo3map.class));
            }
        });

    }
}