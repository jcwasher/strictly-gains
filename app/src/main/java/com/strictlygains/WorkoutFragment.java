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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

public class WorkoutFragment extends Fragment implements View.OnClickListener{
    private ArrayList<Exercise> userList;
    private ListView wList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.workout_layout, container, false);
        Button startButton = view.findViewById(R.id.button);
        startButton.setOnClickListener(this);
        FloatingActionButton actionButton = view.findViewById(R.id.floatingActionButton);
        actionButton.setOnClickListener(this);
        wList = view.findViewById(R.id.exerciseList);
        return view;
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId()) {
            case R.id.button:
                openStartWorkoutActivity();
                break;
            case R.id.floatingActionButton:
                openWorkoutCreateActivity();
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        userList = DataHelper.loadExercises(Objects.requireNonNull(getContext()), "userexercises.json");

        if(userList != null) {
            ArrayList<String> list = new ArrayList<>();

            for(int i = 0; i < userList.size(); i++)
                list.add(userList.get(i).getName());

            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, list);
            wList.setAdapter(adapter);
        }
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