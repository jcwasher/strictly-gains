package com.example.strictlygains;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.strictlygains.data.Exercise;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class WorkoutCreateActivity extends AppCompatActivity {
    SearchView search;
    ListView wList;
    ArrayList<String> list;
    ArrayList<Exercise> exerciseList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_create);

        search = findViewById(R.id.searchView);
        wList = findViewById(R.id.workoutList);
        list = new ArrayList<String>();
        exerciseList = new ArrayList<Exercise>();

        try{
            Scanner read = new Scanner( getAssets().open("exercises.txt") );
            read.useDelimiter(",|\\n");

            while( read.hasNext() )
                exerciseList.add(new Exercise( read.nextInt(), read.next(), read.next() ));

            for(int i = 0; i < exerciseList.size(); i++)
                list.add( exerciseList.get(i).getName() );

            read.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

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
