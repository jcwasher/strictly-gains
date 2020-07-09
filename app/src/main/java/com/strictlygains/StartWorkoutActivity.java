package com.strictlygains;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;



import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


public class StartWorkoutActivity extends AppCompatActivity implements View.OnClickListener {
    int index = 0;
    TextView exerciseName;
    ArrayList<Exercise> userList;
    Button nextExercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_start);
        exerciseName = findViewById(R.id.exerciseName);
        userList = DataHelper.loadExercises(this, "userexercises.json");
        nextExercise = findViewById(R.id.nextExercise);
        nextExercise.setOnClickListener(this);
        exerciseName.setText(userList.get(index).getName());
    }

    @Override
    public void onClick(View v) {
        if(index < userList.size() - 1) {
            index++;
            exerciseName.setText(userList.get(index).getName());
        }
        else {
            index = 0;
            nextExercise.setClickable(false);
        }
    }
}
