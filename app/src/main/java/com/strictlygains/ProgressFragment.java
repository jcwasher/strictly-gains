package com.strictlygains;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class ProgressFragment extends Fragment {
    private final int MAX_DATA_POINTS = 100000;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.progress_layout, container, false);
        GraphView graph = view.findViewById(R.id.graph);

        ArrayList<Exercise> defaultExercises = DataHelper.loadExercises(Objects.requireNonNull(getContext()));
        ArrayList<LineGraphSeries<DataPoint>> seriesList = new ArrayList<>();

        // get directory where completed workouts are stored and list the files
        File dir = new File(Objects.requireNonNull(getContext()).getFilesDir().toString());
        File[] fList = dir.listFiles();

        assert defaultExercises != null;
        for(int i = 0; i < defaultExercises.size(); i++) {
            seriesList.add(new LineGraphSeries<DataPoint>());
        }

        boolean[] flag = new boolean[seriesList.size()];

        double x = 0;
        if(fList != null) {
            for (File f : fList) {
                // ignore the workout template
                if(!f.getName().equals("userexercises.json") && !f.getName().equals("exerciseHistory.json")) {
                    // get the exercise list associated with workout tied to File f
                    ArrayList<Exercise> eList = DataHelper.loadWorkoutExercises(getContext(), f.getName());
                    x += 10; // increment x for new workout slot
                    // parse through each exercise
                    if (eList != null) {
                        for(Exercise e : eList) {
                            double localMax = 0; // base case
                            // parse through each set and update localMax as needed
                            for(Set s : e.getSetList()) {
                                if(s.getWeight() > localMax)
                                    localMax = s.getWeight();
                            }
                            seriesList.get(e.getID()-1).appendData(new DataPoint(x, localMax), true, MAX_DATA_POINTS);
                            flag[e.getID()-1] = true;
                        }
                    }
                }
            }
        }

        for(int i = 0; i < flag.length; i++) {
            if(flag[i]) {
                graph.addSeries(seriesList.get(i));
            }
        }

        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    // show normal x values
                    return super.formatLabel(value, isValueX);
                } else {
                    // show currency for y values
                    return super.formatLabel(value, isValueX) + " lbs";
                }
            }
        });

        /* series1.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(getActivity(), "Upper Body: Day/Weight: "+dataPoint, Toast.LENGTH_SHORT).show();
            }
        });
        series2.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(getActivity(), "Lower Body: Day/Weight: "+dataPoint, Toast.LENGTH_SHORT).show();
            }
        }); */

        return view;
    }
}