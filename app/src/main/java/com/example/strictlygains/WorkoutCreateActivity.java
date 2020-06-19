package com.example.strictlygains;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class WorkoutCreateActivity extends AppCompatActivity {
    SearchView search;
    ListView wList;
    ArrayList<String> list;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_create);

        search = findViewById(R.id.searchView);
        wList = findViewById(R.id.workoutList);
        list = new ArrayList<String>();

        list.add("Dead Lift");
        list.add("Squat");
        list.add("Bench Press");
        list.add("Pull-up");
        list.add("Overhead Press");
        list.add("Push-up");
        list.add("Leg Press");
        list.add("Lunges");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        wList.setAdapter(adapter);
        wList.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(WorkoutCreateActivity.this, adapter.getItem(position), Toast.LENGTH_SHORT).show();
            }
        });

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit (String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false;
            }
        });



    }
}
