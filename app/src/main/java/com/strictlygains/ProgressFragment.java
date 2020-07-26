package com.strictlygains;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class ProgressFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.progress_layout, container, false);
        LineChart lineChart = view.findViewById(R.id.lineChart);

        // load the default exercises
        ArrayList<Exercise> defaultExercises = DataHelper.loadExercises(Objects.requireNonNull(getContext()));
        // idk
        ArrayList<ILineDataSet> lineSets = new ArrayList<>();
        // this will hold all of the exerciseEntries
        ArrayList<LineDataSet> dataSets = new ArrayList<>();
        // this will store corresponding entries for each exercise
        ArrayList<ArrayList<Entry>> exerciseEntries = new ArrayList<>();

        // get directory where completed workouts are stored and list the files
        File dir = new File(Objects.requireNonNull(getContext()).getFilesDir().toString());
        File[] fList = dir.listFiles();

        assert defaultExercises != null;
        // each exercise will have its own entries
        for(int i = 0; i < defaultExercises.size(); i++) {
            exerciseEntries.add(new ArrayList<Entry>());
            dataSets.add(null);
        }

        float x = 0;
        if(fList != null) {
            Arrays.sort(fList);
            System.out.println(Arrays.toString(fList));
            for (File f : fList) {
                System.out.println(Arrays.toString(fList));
                // ignore the workout template
                if(!f.getName().equals("userexercises.json") && !f.getName().equals("exerciseHistory.json")) {
                    // get the exercise list associated with workout tied to File f
                    ArrayList<Exercise> eList = DataHelper.loadWorkoutExercises(getContext(), f.getName());
                    // parse through each exercise
                    if (eList != null) {
                        for(Exercise e : eList) {
                            float localMax = 0; // base case
                            // parse through each set and update localMax as needed
                            for(Set s : e.getSetList()) {
                                if(s.getWeight() > localMax)
                                    localMax = (float)s.getWeight();
                            }
                            // access the corresponding entry list and add the new max
                            exerciseEntries.get(e.getID()-1).add(new Entry(x, localMax));
                        }
                    }
                    x++;
                }
            }
        }
        for(Exercise e : defaultExercises) {
            if(exerciseEntries.get(e.getID()-1).size() > 0) {
                dataSets.set(e.getID()-1, new LineDataSet(exerciseEntries.get(e.getID()-1), e.getName()));
                lineSets.add(dataSets.get(e.getID()-1));
            }
        }

        LineData data = new LineData(lineSets);
        lineChart.setData(data);
        lineChart.invalidate();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.login_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}