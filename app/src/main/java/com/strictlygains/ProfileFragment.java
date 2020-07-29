package com.strictlygains;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {

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
        TextInputEditText gender = view.findViewById(R.id.profile_gender);

        user = FirebaseAuth.getInstance().getCurrentUser();
        profileName.setText(user.getDisplayName());



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




        return view;
    }



}