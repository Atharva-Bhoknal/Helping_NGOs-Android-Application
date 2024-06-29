package com.example.helpingpeople;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class fooddataform extends AppCompatActivity {
    private EditText uname,area,esqt,conatct,uemail;

    String name = "heloo", username, email, phoneNo, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fooddataform);
        uname=findViewById(R.id.e1);
        area=findViewById(R.id.e2);
        esqt=findViewById(R.id.e3);
        conatct=findViewById(R.id.e4);
        uemail=findViewById(R.id.e5);

        TestUserData testUserData = new TestUserData();

        name = testUserData.getName();
        username = testUserData.getUsername();
        email = testUserData.getEmail();
        phoneNo = testUserData.getPhoneNo();
        password = testUserData.getPassword();

        String name = getIntent().getStringExtra("name");
        String username = getIntent().getStringExtra("username");
        String email = getIntent().getStringExtra("email");
        String phoneNo = getIntent().getStringExtra("phoneNo");
        String password = getIntent().getStringExtra("password");

        uname.setText(name);
    }
}