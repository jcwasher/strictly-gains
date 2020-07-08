package com.strictlygains;

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
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.workout_layout, container, false);
        Button startButton = view.findViewById(R.id.button);
        startButton.setOnClickListener(this);
        FloatingActionButton actionButton = view.findViewById(R.id.floatingActionButton);
        actionButton.setOnClickListener(this);
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