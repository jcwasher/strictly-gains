package com.strictlygains;

import androidx.appcompat.app.AppCompatActivity;

import android.app.LauncherActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 4000;

    Animation topAnim, botAnim;
    ImageView image;
    TextView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animation);

        image = findViewById(R.id.imageView);
        logo = findViewById(R.id.textView1);

        image.setAnimation(topAnim);
        logo.setAnimation(topAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent Splash = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(Splash);
                finish();
            }
        }, SPLASH_SCREEN);


    }
}