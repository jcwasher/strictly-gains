package com.strictlygains;

import android.content.Intent;
import android.os.Bundle;

import com.strictlygains.ui.login.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;

import com.strictlygains.ui.main.SectionsPagerAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
 //       FloatingActionButton fab = findViewById(R.id.fab);

        // copy the defaultExercises to local files if it does not yet exist
        if(!new File(getFilesDir(), "defaultExercises").exists())
            DataHelper.saveDefaultExercises(this, Objects.requireNonNull(DataHelper.loadExercises(this)));

        // Initialize exerciseHistory file with default workouts
        if (DataHelper.loadExercises(this, "exerciseHistory.json") == null) {
            ArrayList<Exercise> exerciseList = DataHelper.loadExercises(this);
            assert exerciseList != null;
            DataHelper.updateExerciseHistory(this, exerciseList);
        }




    /*   fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));      // TEMPORARY: used to access login screen.


            }

        }); */
    }



}