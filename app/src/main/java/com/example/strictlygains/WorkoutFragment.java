package com.example.strictlygains;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.widget.Button;

public class WorkoutFragment extends Fragment implements View.OnClickListener{
    Button myButton;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.workout_layout, container, false);
        myButton = view.findViewById(R.id.button);
        myButton.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view)
    {
        myButton.setText("Working");
    }
}