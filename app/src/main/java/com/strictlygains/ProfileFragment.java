package com.strictlygains;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.Objects;

public class ProfileFragment extends Fragment {

    private ArrayList<Exercise> eHistoryList;
    FirebaseUser user;
    private static final String[] genderOptions = new String[] {
            "Male", "Female", "Other"
    };
    private String radioButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_layout, container, false);

        TextView profileName = view.findViewById(R.id.profile_fullname);
        TextView benchMax = view.findViewById(R.id.bench_press_max);
        TextView squatMax = view.findViewById(R.id.squat_max);
        TextView deadliftMax = view.findViewById(R.id.deadlift_max);

        eHistoryList = DataHelper.loadExercises(Objects.requireNonNull(getContext()), "exerciseHistory.json");



        // Plan on adding custom exercises with scorllview.
        if(eHistoryList != null) {
            benchMax.setText(String.valueOf(eHistoryList.get(1).getMax())); // id 1 points to bench press
            squatMax.setText(String.valueOf(eHistoryList.get(3).getMax())); // id 3 points to
            deadliftMax.setText(String.valueOf(eHistoryList.get(2).getMax())); // id 2 points to bench press
        }


        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            profileName.setText(user.getDisplayName());
        }

/*
        gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioButton = genderOptions[0];
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Gender");
                builder.setSingleChoiceItems(genderOptions, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int o) {
                        radioButton = genderOptions[o];
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.setNegativeButton("CANCEL", null);
            }
        });


*/

        return view;
    }



}