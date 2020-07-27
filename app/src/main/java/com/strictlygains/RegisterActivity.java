package com.strictlygains;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.strictlygains.ui.login.LoginActivity;

public class RegisterActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


    }

    public void redirectToLogin(View view) {
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }
}