package com.strictlygains;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public class ProgressFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    ArrayAdapter<String> adapter;
    LineChart lineChart;
    ArrayList<Exercise> defaultExercises;
    ArrayList<Entry> exerciseEntries;
    Spinner spinner;
    ArrayList<String> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.progress_layout, container, false);
        lineChart = view.findViewById(R.id.lineChart);

        lineChart.getXAxis().setEnabled(false); // disabling x axis labels
        lineChart.getDescription().setEnabled(false); // disabling description

        // load the default exercises
        defaultExercises = DataHelper.loadExercises(Objects.requireNonNull(getContext()), "exerciseHistory.json");

        // this will store corresponding entries for the exercise
        exerciseEntries = new ArrayList();


        spinner = view.findViewById(R.id.spinner);
        list = new ArrayList<String>();
        if(defaultExercises != null) {
            // will add all COMPLETED exercises to drop-down list
            for (int i=0; i<defaultExercises.size(); i++) {
                if (defaultExercises.get(i).getMax() > 0)
                    list.add(defaultExercises.get(i).getName());
            }
        }
        Collections.sort(list);
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        return view;
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
        // get directory where completed workouts are stored and list the files
        File dir = new File(Objects.requireNonNull(getContext()).getFilesDir().toString());
        File[] fList = dir.listFiles();
        // clear exerciseEntries
        if (exerciseEntries.size()>0)
            exerciseEntries.clear();

        float x = 0;
        boolean match = false;
        if(fList != null) {
            Arrays.sort(fList);
            System.out.println(Arrays.toString(fList));
            for (File f : fList) {
                System.out.println(Arrays.toString(fList));
                // ignore the workout template
                if(!f.getName().equals("userExercises.json")
                        && !f.getName().equals("exerciseHistory.json")
                        && !f.getName().contains("userWorkout_")
                        && !f.getName().equals("currentWorkout.json")) {
                    // get the exercise list associated with workout tied to File f
                    ArrayList<Exercise> eList = DataHelper.loadWorkoutExercises(getContext(), f.getName());
                    // parse through each exercise
                    if (eList != null) {
                        for(Exercise e : eList) {
                            float localMax = 0; // base case

                            // if exercise in selected workout is equal to exercise selected in spinner
                            if (Objects.equals(e.getName(), adapter.getItem(position))){
                                match = true;
                            }
                            if (!match)
                                continue;
                            match = false;

                            for(Set s : e.getSetList()) {
                                if(s.getWeight() > localMax)
                                    localMax = (float)s.getWeight();
                            }
                            // access the corresponding entry list and add the new max
                            exerciseEntries.add(new Entry(x, localMax));

                        }
                    }
                    x++;
                }
            }
        }
        ArrayList<ILineDataSet> lineSets = new ArrayList<>();
        LineDataSet dataSet = new LineDataSet(exerciseEntries, adapter.getItem(position));
        dataSet.setColor(Color.rgb(59, 254, 187));
        lineSets.add(dataSet);
        LineData data = new LineData(lineSets);
        lineChart.setData(data);
        lineChart.invalidate();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}