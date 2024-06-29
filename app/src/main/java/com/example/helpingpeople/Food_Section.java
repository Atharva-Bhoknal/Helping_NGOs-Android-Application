package com.example.helpingpeople;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Food_Section extends AppCompatActivity {

    Button ngoBackBtn, ngo1Btn, ngo2Btn, ngo3Btn;

    String name, username, email, phoneNo, password,link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_section);

       // ngoBackBtn=findViewById(R.id.ngoBackBtn);
        ngo1Btn=findViewById(R.id.ngo1Btn);
        ngo2Btn=findViewById(R.id.ngo2Btn);
        ngo3Btn=findViewById(R.id.ngo3Btn);


        ngo1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Food_Section.this,ngo1map.class);


                intent.putExtra("name",name);
                intent.putExtra("username",username);
                intent.putExtra("email",email);
                intent.putExtra("phoneNo",phoneNo);
                intent.putExtra("password",password);

                startActivity(intent);
            }
        });

        ngo2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Food_Section.this,ngo2map.class));
            }
        });

        ngo3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Food_Section.this,ngo3map.class));
            }
        });
    }
}