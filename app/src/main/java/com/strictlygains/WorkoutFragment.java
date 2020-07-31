package com.strictlygains;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class WorkoutFragment extends Fragment implements View.OnClickListener{
    private ArrayList<Workout> workoutList;
    private Workout currentWorkout;
    private ListView wList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceStte) {
        View view = inflater.inflate(R.layout.workout_layout, container, false);
        Button startButton = view.findViewById(R.id.beginButton);
        startButton.setOnClickListener(this);
        FloatingActionButton actionButton = view.findViewById(R.id.createButton);
        actionButton.setOnClickListener(this);
        Button statsButton = view.findViewById(R.id.statsButton);
        FloatingActionButton editButton = view.findViewById(R.id.editButton);
        // ONLY CREATE BUTTON WORKS
        editButton.setClickable(false);
        statsButton.setClickable(false);
        startButton.setClickable(false);

        wList = view.findViewById(R.id.workoutList);
        workoutList = getUserWorkouts();

        if(workoutList.size() > 0) {
            ArrayList<String> list = new ArrayList<>();

            for(int i = 0; i < workoutList.size(); i++)
                list.add(workoutList.get(i).getName());

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, list);
            wList.setAdapter(adapter);
        }
        return view;
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId()) {
            case R.id.beginButton: {
                if (false) // currentWorkout needs to be selected
                    Toast.makeText(getActivity(), "Please select a workout.", Toast.LENGTH_SHORT ).show();
                else
                    openStartWorkoutActivity();
                break;
            }
            case R.id.createButton:
                openWorkoutCreateActivity();
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        workoutList = getUserWorkouts();

        if(workoutList.size() > 0) {
            ArrayList<String> list = new ArrayList<>();

            for(int i = 0; i < workoutList.size(); i++)
                list.add(workoutList.get(i).getName());

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, list);
            wList.setAdapter(adapter);
        }
    }

    private ArrayList<Workout> getUserWorkouts() {
        ArrayList<Workout> workoutList = new ArrayList<>();
        File dir = new File(Objects.requireNonNull(getContext()).getFilesDir().toString());
        File[] fList = dir.listFiles();

        if(fList != null) {
            for (File f : fList) {
                // we have found a user created workout
                if(f.getName().contains("userWorkout_")) {
                    Workout w = new Workout();
                    w.setExerciseList(DataHelper.loadWorkoutExercises(getContext(), f.getName()));
                    String name = f.getName();
                    name = name.substring(12, name.length()-5);
                    w.setName(name);
                    workoutList.add(w);
                }
            }
        }

        return workoutList;
    }

    private void openWorkoutCreateActivity()
    {
        Intent intent = new Intent(getActivity(), WorkoutCreateActivity.class);
        startActivity(intent);
    }

    private void openStartWorkoutActivity()
    {
        Intent intent = new Intent(getActivity(), StartWorkoutActivity.class);
        startActivity(intent);
    }

}