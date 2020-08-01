package com.strictlygains;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.strictlygains.ui.login.LoginActivity;

import java.util.ArrayList;
import java.util.Objects;

public class ProfileFragment extends Fragment {

    private ArrayList<Exercise> eHistoryList;
    FirebaseUser user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_layout, container, false);

        TextView profileName = view.findViewById(R.id.profile_fullname);
        final TextView profileGender = view.findViewById(R.id.profile_gender);
        final TextView profileAge = view.findViewById(R.id.profile_age);
        final TextView profileHeight = view.findViewById(R.id.profile_height);
        final TextView profileWeight = view.findViewById(R.id.profile_weight);

        TextView benchMax = view.findViewById(R.id.bench_press_max);
        TextView squatMax = view.findViewById(R.id.squat_max);
        TextView deadliftMax = view.findViewById(R.id.deadlift_max);

        Button editProfile = view.findViewById(R.id.edit_profile);

        eHistoryList = DataHelper.loadExercises(Objects.requireNonNull(getContext()), "exerciseHistory.json");



        // Plan on adding custom exercises with scorllview.
        if(eHistoryList != null) {
            benchMax.setText(String.valueOf((int)eHistoryList.get(1).getMax())); // id 1 points to bench press
            squatMax.setText(String.valueOf((int)eHistoryList.get(3).getMax())); // id 3 points to
            deadliftMax.setText(String.valueOf((int)eHistoryList.get(2).getMax())); // id 2 points to bench press
        }


        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            profileName.setText(user.getDisplayName());
        }

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
                View mView = getLayoutInflater().inflate(R.layout.dialog_spinner, null);
                mBuilder.setTitle("Edit Profile");

                final Spinner mSpinner = mView.findViewById(R.id.spin);
                final TextInputEditText mAge = mView.findViewById(R.id.age);
                final TextInputEditText mHeight = mView.findViewById(R.id.height);
                final TextInputEditText mWeight = mView.findViewById(R.id.weight);

                ArrayAdapter<String> adapter =  new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item,
                        getResources().getStringArray(R.array.genderList));
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner.setAdapter(adapter);

                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        profileGender.setText("Gender: " + mSpinner.getSelectedItem().toString());
                        profileAge.setText("Age: " + mAge.getText().toString());
                        profileHeight.setText(mHeight.getText().toString());
                        profileWeight.setText(mWeight.getText().toString());
                    }
                });

                mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                // create and show the alert dialog
                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();

            }
        });



        return view;
    }


}

