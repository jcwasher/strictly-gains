package com.strictlygains;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Objects;

public class GoalFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    private PieChart pieChart;
    private ArrayList<Exercise> userList;
    private TextView maxTextView;
    private Spinner spinner;
    private ArrayList<String> list;
    private ArrayAdapter<String> adapter;
    private double goal, maxDouble, progressVal, remainingVal;
    private String max = "0";
    private String name = "";
    private ArrayList<PieEntry> values;
    private PieData data;
    private PieDataSet dataSet;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.goal_layout, container, false);

        pieChart = (PieChart) view.findViewById(R.id.pieChart);
        userList = new ArrayList<Exercise>();
        userList = DataHelper.loadExercises(Objects.requireNonNull(getContext()), "userexercises.json");
        maxTextView = view.findViewById(R.id.maxTextView);
        spinner = view.findViewById(R.id.spinner);
        list = new ArrayList<String>();

       for (int i=0; i<userList.size(); i++)
            list.add(userList.get(i).getName());

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);

        values = new ArrayList<>();
        values.add(new PieEntry(100, "Progress"));
        values.add(new PieEntry(0, "Remaining"));
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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        for(int i = 0; i < userList.size(); i++){
            if(Objects.equals(adapter.getItem(position), userList.get(i).getName())){
                max = String.valueOf(userList.get(i).getMax());
                maxTextView.setText(max);
                double maxDouble = userList.get(i).getMax();
                goal = maxDouble * 1.5;
                progressVal = 100 / (goal / maxDouble);
                remainingVal = 100 - progressVal;
                values.set(0, new PieEntry((float) progressVal, "Progress"));
                values.set(1, new PieEntry((float) remainingVal, "Remaining"));
                pieChart.setData(data);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        // Used for edit button to change Goal
    }
}
