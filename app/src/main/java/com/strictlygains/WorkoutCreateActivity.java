package com.strictlygains;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Scanner;
import java.util.zip.Inflater;

public class WorkoutCreateActivity extends AppCompatActivity implements View.OnClickListener{
    SearchView search;
    ListView wList;
    ArrayList<String> list;
    ArrayList<Exercise> exerciseList, userList;
    ArrayAdapter<String> adapter;
    ChipGroup chipGroup;
    Chip chip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_create);

        search = findViewById(R.id.searchView);
        wList = findViewById(R.id.workoutList);
        list = new ArrayList<String>();
        exerciseList = new ArrayList<Exercise>();
        userList = new ArrayList<Exercise>();

        try{
            Scanner read = new Scanner( getAssets().open("exercises.txt") );
            read.useDelimiter(",|\\n");

            while( read.hasNext() )
                exerciseList.add(new Exercise( read.nextInt(), read.next(), read.next() ));

            read.close();

            for(int i = 0; i < exerciseList.size(); i++)
                list.add( exerciseList.get(i).getName() );

            Collections.sort(list);

        } catch (IOException e) {
            e.printStackTrace();
        }

        // TODO: create a cache file and write the list of exercises to it to display

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        wList.setAdapter(adapter);
        wList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for(int i = 0; i < exerciseList.size(); i++) {
                    if(Objects.equals(adapter.getItem(position), exerciseList.get(i).getName())) {
                        userList.add(exerciseList.get(i));
                        Toast.makeText(WorkoutCreateActivity.this, adapter.getItem(position) + " Added", Toast.LENGTH_SHORT).show();
                        chipGroup = findViewById(R.id.chipGroup);
                        chip = (Chip) getLayoutInflater().inflate(R.layout.chip_layout, chipGroup, false);
                        chip.setText(adapter.getItem(position));
                        chipGroup.addView(chip);
                    }
                }
            }
            // TODO: wrap userList up in a bundle and try to access in WorkoutFragment
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

    @Override
    public void onClick(View v) {
        Chip c = (Chip) v;
        chipGroup.removeView(v);
        Toast.makeText(WorkoutCreateActivity.this,  c.getText()+ " Removed", Toast.LENGTH_SHORT).show();

    }
}
