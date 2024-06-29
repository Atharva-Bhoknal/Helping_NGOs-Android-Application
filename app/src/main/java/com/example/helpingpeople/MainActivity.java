package com.example.helpingpeople;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

    private static int SPLASH_SCREEN = 3000;
    Animation topAnim, BottomAnim;
    ImageView image;
    TextView logo, slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        // ANIMATION

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        BottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        // HOOKS

        image = findViewById(R.id.car);
        logo = findViewById(R.id.t1);
        slogan = findViewById(R.id.t2);

        // ASSIGNING ANIMATION

        image.setAnimation(topAnim);
        logo.setAnimation(BottomAnim);
        slogan.setAnimation(BottomAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(MainActivity.this, Login.class);
//                Intent intent = new Intent(MainActivity.this, MainPage.class);
                startActivity(intent);
                finish();

            }
        }, SPLASH_SCREEN);


    }
}