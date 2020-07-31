package com.strictlygains;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class WorkoutCreateActivity extends AppCompatActivity implements View.OnClickListener{
    private SearchView search;
    private ListView wList;
    private FloatingActionButton saveButton, createButton;
    private ChipGroup chipGroup;
    private Chip chip;
    private ArrayList<String> list;
    private ArrayList<Exercise> exerciseList, userList;
    private ArrayAdapter<String> adapter;
    private Workout newWorkout;
    private String newWorkoutName;
    private Exercise choice;
    private boolean exists;
    private EditText editText, saveWorkoutET, repET, rpeET, weightET;
    private TextView setNumberTV, closeTV, exerciseName;
    private Button addSetButton, removeSetButton, addExerciseButton;
    private AlertDialog dialog, dialog2, dialog3;
    private AlertDialog.Builder dialog2Builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_create);

        exists = false;
        search = findViewById(R.id.searchView);
        wList = findViewById(R.id.workoutList);
        chipGroup = findViewById(R.id.chipGroup);
        chipGroup.setOnClickListener(this);
        saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(this);
        createButton = findViewById(R.id.createButton);
        createButton.setOnClickListener(this);
        editText = new EditText(this);
        dialog = new AlertDialog.Builder(this).create();

        dialog.setTitle("Exercise Name");
        dialog.setView(editText);

        list = new ArrayList<String>();
        exerciseList = new ArrayList<Exercise>();
        userList = new ArrayList<Exercise>();
        newWorkout = new Workout();

        // using new DataHelper class to load
        exerciseList = DataHelper.loadExercises(this, "exerciseHistory.json");

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
                        choice = exerciseList.get(i);
                        addExerciseToWorkout();
                        break;
                    }
                }
            }
        });

        wList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(WorkoutCreateActivity.this)
                        .setIcon(android.R.drawable.ic_menu_delete)
                        .setTitle("Delete " + adapter.getItem(position) + "?")
                        .setMessage("Are you sure you want to delete this exercise?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                for (int i=0; i<exerciseList.size(); i++){
                                    if (adapter.getItem(position).equals(exerciseList.get(i).getName())){
                                        exerciseList.remove(i);
                                        break;
                                    }
                                    if (DataHelper.loadExercises(WorkoutCreateActivity.this, "userExercises.json") != null) {
                                        ArrayList<Exercise> e = DataHelper.loadExercises(WorkoutCreateActivity.this, "userExercises.json");
                                        if (i < e.size() && adapter.getItem(position).equals(e.get(i).getName())) {
                                            e.remove(i);
                                            DataHelper.saveExercises(WorkoutCreateActivity.this, e);
                                            break;
                                        }
                                    }
                                }
                                // update IDs
                                for (int i=0; i<exerciseList.size(); i++)
                                    exerciseList.get(i).setID(i+1);
                                DataHelper.updateExerciseHistory(WorkoutCreateActivity.this, exerciseList);
                                // Update list for deleted item
                                list.clear();
                                for(int i = 0; i < exerciseList.size(); i++)
                                    list.add( exerciseList.get(i).getName() );
                                Collections.sort(list);
                                adapter.notifyDataSetChanged();

                            }
                        })
                        .setNegativeButton("No", null)
                        .show();

                return true;
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

        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "SAVE EXERCISE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                for(int i = 0; i < exerciseList.size(); i++){
                    // Checks to see if exercise already exists
                    if(editText.getText().toString().equals(exerciseList.get(i).getName())){
                        Toast.makeText(WorkoutCreateActivity.this,"Exercise already exists", Toast.LENGTH_SHORT).show();
                        exists = true;
                    }
                }
                // if exercise doesn't already exist, add it to exercise history file
                if (!exists) {
                    // Set ID 1 larger than size
                    int id = exerciseList.size() + 1;
                    // Add exercise. Focus not currently being used so set to default.
                    exerciseList.add(new Exercise(id, 0, 100, editText.getText().toString(), "default"));
                    DataHelper.updateExerciseHistory(WorkoutCreateActivity.this, exerciseList);
                    list.add(editText.getText().toString());
                    Collections.sort(list);
                    adapter.notifyDataSetChanged();
                }
                exists = false;

            }
        });
    }

    public void addExerciseToWorkout() {
        dialog2Builder = new AlertDialog.Builder(this);
        final View addExercisePopUpView = getLayoutInflater().inflate(R.layout.addexercise_popup, null);
        // TextView
        exerciseName = addExercisePopUpView.findViewById(R.id.exerciseName);
        closeTV = addExercisePopUpView.findViewById(R.id.closeTV);
        setNumberTV = addExercisePopUpView.findViewById(R.id.setNumberTV);
        // EditText
        repET = addExercisePopUpView.findViewById(R.id.repET);
        rpeET = addExercisePopUpView.findViewById(R.id.rpeET);
        weightET = addExercisePopUpView.findViewById(R.id.weightET);
        // Button
        addSetButton = addExercisePopUpView.findViewById(R.id.addSetButton);
        removeSetButton = addExercisePopUpView.findViewById(R.id.removeSetButton);
        addExerciseButton = addExercisePopUpView.findViewById(R.id.addExerciseButton);
        // show popup
        exerciseName.setText(choice.getName());
        dialog2Builder.setView(addExercisePopUpView);
        dialog2 = dialog2Builder.create();
        dialog2.show();
        // on click listeners
        closeTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2.dismiss();
            }
        });
        addSetButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if(rpeET.getText().toString().isEmpty() || weightET.getText().toString().isEmpty() || repET.toString().isEmpty())
                    System.out.println("One field was empty");
                else
                {
                    double weight = Double.parseDouble(weightET.getText().toString());
                    int reps = Integer.parseInt(repET.getText().toString());
                    int rpe = Integer.parseInt(rpeET.getText().toString());
                    choice.addSet(new Set(weight, reps, rpe));
                    int newSetNum = choice.getSetList().size() + 1;
                    setNumberTV.setText("Set " + newSetNum);
                }
            }
        });
        removeSetButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                int listSize = choice.getSetList().size();
                if(listSize > 0) {
                    choice.getSetList().remove(listSize - 1);
                    int newSetNum = choice.getSetList().size() + 1;
                    setNumberTV.setText("Set " + newSetNum);
                }
            }
        });
        addExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(choice.getSetList().size() > 0) {
                    newWorkout.addExercise(choice);
                    chipGroup = findViewById(R.id.chipGroup);
                    chip = (Chip) getLayoutInflater().inflate(R.layout.chip_layout, chipGroup, false);
                    chip.setText(choice.getName());
                    chipGroup.addView(chip);
                    dialog2.dismiss();
                }
                else
                    System.out.println("Empty set list");
            }
        });
    }

    @Override
    public void onClick(final View v) {
        if (v.getId() == R.id.saveButton) {
            saveWorkoutET = new EditText(v.getContext());
            dialog3 = new AlertDialog.Builder(v.getContext()).create();
            dialog3.setTitle("Workout Name");
            dialog3.setView(saveWorkoutET);
            dialog3.setButton(DialogInterface.BUTTON_POSITIVE, "SAVE WORKOUT", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(saveWorkoutET.getText().toString().length() > 0) {
                        newWorkoutName = saveWorkoutET.getText().toString();
                        newWorkout.setName(newWorkoutName);
                        DataHelper.saveWorkout(v.getContext(), newWorkout, new String("userWorkout_" + newWorkout.getName() + ".json"));
                        startActivity(new Intent(v.getContext(), MainActivity.class));
                    }
                }
            });
            dialog3.show();
        } else if (v.getId() != R.id.chipGroup && v.getId() != R.id.createButton){
            Chip c = (Chip) v;
            for (int i = 0; i < userList.size(); i++) {
                if (Objects.equals(c.getText(), userList.get(i).getName())) {
                    userList.remove(i);
                    break;
                }
            }
            chipGroup.removeView(v);   // Removes chip when clicked
        } else if (v.getId() == R.id.createButton){
            dialog.show();
        }
    }

}


