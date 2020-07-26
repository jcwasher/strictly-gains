package com.strictlygains;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

public class GoalFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    private PieChart pieChart;
    private ArrayList<Exercise> eHistoryList;
    private TextView maxTextView, goalTextView;
    private Spinner spinner;
    private ArrayList<String> list;
    private ArrayAdapter<String> adapter;
    private double goalDouble, maxDouble, progressVal, remainingVal;
    private String maxString = "0";
    private String goalString = "0";
    private String name = "";
    private ArrayList<PieEntry> values;
    private PieData data;
    private PieDataSet dataSet;
    private FloatingActionButton editGoalButton;
    private AlertDialog dialog;
    private EditText editText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.goal_layout, container, false);

        pieChart = (PieChart) view.findViewById(R.id.pieChart);
        eHistoryList = new ArrayList<>();
        eHistoryList = DataHelper.loadExercises(Objects.requireNonNull(getContext()), "exerciseHistory.json");
        maxTextView = view.findViewById(R.id.maxTextView);
        goalTextView = view.findViewById(R.id.goalTextView);
        spinner = view.findViewById(R.id.spinner);
        editGoalButton = view.findViewById(R.id.editGoalButton);
        dialog = new AlertDialog.Builder(getContext()).create();
        editText = new EditText(getContext());

        dialog.setTitle(" Edit Goal ");
        dialog.setView(editText);

        list = new ArrayList<String>();

        if(eHistoryList != null) {
            for (int i=0; i<eHistoryList.size(); i++)
                list.add(eHistoryList.get(i).getName());
        }

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);

        values = new ArrayList<>();
        values.add(new PieEntry(0f, "Progress"));
        values.add(new PieEntry(0f, "Remaining"));
        dataSet = new PieDataSet(values, name);
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(Color.rgb(59, 254, 187), Color.argb(50,211, 211, 211));
        data = new PieData(dataSet);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.GRAY);

        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "SAVE TEXT", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                goalTextView.setText(editText.getText());
                for(int i = 0; i < eHistoryList.size(); i++){
                    if(Objects.equals(adapter.getItem(position), eHistoryList.get(i).getName())){
                        eHistoryList.get(i).setGoal(Double.parseDouble(goalTextView.getText().toString()));
                        DataHelper.updateExerciseHistory(getContext(), eHistoryList);
                        eHistoryList = DataHelper.loadExercises(getContext(), "exerciseHistory.json");
                        updateAdapterItem(position);
                    }
                }

            }
        });
        editGoalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText(goalTextView.getText());
                dialog.show();
            }
        });

        updateAdapterItem(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void updateAdapterItem(int p) {
        for (int i = 0; i < eHistoryList.size(); i++) {
            if (Objects.equals(adapter.getItem(p), eHistoryList.get(i).getName())) {
                maxString = "Max: " + String.valueOf(eHistoryList.get(i).getMax());
                maxTextView.setText(maxString);
                goalString = String.valueOf(eHistoryList.get(i).getGoal());
                goalTextView.setText(goalString);
                maxDouble = eHistoryList.get(i).getMax();
                goalDouble = eHistoryList.get(i).getGoal();
                if (maxDouble >= goalDouble){
                    progressVal = 100;
                    remainingVal = 0;
                }
                else {
                    progressVal = 100 / (goalDouble / maxDouble);
                    remainingVal = 100 - progressVal;
                }
                values.set(0, new PieEntry((float) progressVal, "Progress"));
                values.set(1, new PieEntry((float) remainingVal, "Remaining"));
                pieChart.setData(data);
                // refreshes chart
                pieChart.invalidate();
                break;
            }
        }
    }

}

