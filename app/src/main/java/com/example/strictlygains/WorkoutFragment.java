package com.example.strictlygains;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.widget.Button;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class WorkoutFragment extends Fragment implements View.OnClickListener{
    Button myButton;
    FloatingActionButton actionButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.workout_layout, container, false);
        myButton = view.findViewById(R.id.button);
        myButton.setOnClickListener(this);
        actionButton = view.findViewById(R.id.floatingActionButton);
        actionButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId()) {
            case R.id.button:
                myButton.setText("Working");
                break;
            case R.id.floatingActionButton:
                openWorkoutCreateActivity();
                break;
            default:
                    break;
        }
    }

    public void openWorkoutCreateActivity()
    {
        Intent intent = new Intent(getActivity(), WorkoutCreateActivity.class);
        startActivity(intent);
    }

}