package com.strictlygains;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class WorkoutCreateActivity extends AppCompatActivity implements View.OnClickListener{
    Dialog myDialog;
    SearchView search;
    ListView eList;
    FloatingActionButton saveButton;
    ChipGroup chipGroup;
    Chip chip;
    ArrayList<String> list;
    ArrayList<Exercise> exerciseList, userList;
    ArrayAdapter<String> adapter;
    boolean match = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_create);

        myDialog = new Dialog(this);
        search = findViewById(R.id.searchView);
        eList = findViewById(R.id.exerciseList);
        chipGroup = findViewById(R.id.chipGroup);
        chipGroup.setOnClickListener(this);
        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(this);

        list = new ArrayList<String>();
        exerciseList = new ArrayList<Exercise>();
        Workout newWorkout = new Workout();

        // using new DataHelper class to load
        exerciseList = DataHelper.loadExercises(this);

        assert exerciseList != null;
        for(int i = 0; i < exerciseList.size(); i++)
            list.add( exerciseList.get(i).getName() );

        // sorting alphabetically
        Collections.sort(list);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        eList.setAdapter(adapter);
        eList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(WorkoutCreateActivity.this, adapter.getItem(position) + " Added", Toast.LENGTH_SHORT).show();
                for(int i = 0; i < exerciseList.size(); i++) {
                    if(Objects.equals(adapter.getItem(position), exerciseList.get(i).getName())) {
                        Exercise chosen = exerciseList.get(i);
                        showPopUp(view);



                        /*
                        userList.add(exerciseList.get(i));
                        chipGroup = findViewById(R.id.chipGroup);
                        chip = (Chip) getLayoutInflater().inflate(R.layout.chip_layout, chipGroup, false);
                        chip.setText(adapter.getItem(position));
                        chipGroup.addView(chip);
                        break;
                        */
                    }
                }
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.saveButton) {
            // Save currently selected workout plan in file
            DataHelper.saveExercises(this, userList);;
            startActivity(new Intent(this, MainActivity.class));
        } else if (v.getId() != R.id.chipGroup){
            Chip c = (Chip) v;
            for (int i = 0; i < userList.size(); i++) {
                if (Objects.equals(c.getText(), userList.get(i).getName())) {
                    userList.remove(i);
                    break;
                }
            }
            chipGroup.removeView(v);   // Removes chip when clicked
            //Toast.makeText(WorkoutCreateActivity.this,  c.getText()+ " Removed", Toast.LENGTH_SHORT).show();
        }
    }

    public void showPopUp(View v) {
        myDialog.setContentView(R.layout.exercise_popup);
        myDialog.show();
    }
}


/*
// May be needed to add custom exercises to history file
// add missing exercises to a file to keep track of max and goals
            if (eHistoryList != null) {
                for (int i = 0; i < userList.size(); i++) {
                    for (int j = 0; j < eHistoryList.size(); j++) {
                        if (userList.get(i).getName().equals(eHistoryList.get(j).getName())) {
                            match = true;
                            break;
                        }
                    }
                    if (!match) {
                        eHistoryList.add(userList.get(i));
                    }
                    match = false;
                }
            }
 */