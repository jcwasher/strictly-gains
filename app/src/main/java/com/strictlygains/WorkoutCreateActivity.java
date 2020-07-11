package com.strictlygains;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.LayoutInflater;
import java.util.zip.Inflater;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class WorkoutCreateActivity extends AppCompatActivity implements View.OnClickListener{
    SearchView search;
    ListView wList;
    FloatingActionButton saveButton;
    ChipGroup chipGroup;
    Chip chip;
    ArrayList<String> list;
    ArrayList<Exercise> exerciseList, userList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_create);

        search = findViewById(R.id.searchView);
        wList = findViewById(R.id.workoutList);
        chipGroup = findViewById(R.id.chipGroup);
        chipGroup.setOnClickListener(this);
        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(this);

        list = new ArrayList<String>();
        exerciseList = new ArrayList<Exercise>();
        userList = new ArrayList<Exercise>();

        // using new DataHelper class to load
        exerciseList = DataHelper.loadExercises(this);

        assert exerciseList != null;
        for(int i = 0; i < exerciseList.size(); i++)
            list.add( exerciseList.get(i).getName() );

        // sorting alphabetically
        Collections.sort(list);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        wList.setAdapter(adapter);
        wList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(WorkoutCreateActivity.this, adapter.getItem(position) + " Added", Toast.LENGTH_SHORT).show();
                for(int i = 0; i < exerciseList.size(); i++) {
                    if(Objects.equals(adapter.getItem(position), exerciseList.get(i).getName())) {
                        userList.add(exerciseList.get(i));
                        chipGroup = findViewById(R.id.chipGroup);
                        chip = (Chip) getLayoutInflater().inflate(R.layout.chip_layout, chipGroup, false);
                        chip.setText(adapter.getItem(position));
                        chipGroup.addView(chip);
                        break;
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
            DataHelper.saveExercises(this, userList);
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
}
