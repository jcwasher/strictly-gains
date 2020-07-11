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

public class ProgressFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.progress_layout, container, false);
        GraphView graph = view.findViewById(R.id.graph);

        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    // show normal x values
                    return super.formatLabel(value, isValueX);
                } else {
                    // show currency for y values
                    return super.formatLabel(value, isValueX) + "lbs";
                }
            }
        });

        double x = 0;
        double y = 50;
        double z = 50;

        LineGraphSeries series1 = new LineGraphSeries<>();
        LineGraphSeries series2 = new LineGraphSeries<>();

        int numDataPoints = 10;
        for (int i = 0; i < numDataPoints; i++){
            x += 10;
            y += 5;
            z += 15;
            series1.appendData(new DataPoint(x,y), true, 10);
            series2.appendData(new DataPoint(x+.5,z), true, 10);
        }



        series1.setOnDataPointTapListener(new OnDataPointTapListener() {
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
        });

        series1.setColor(Color.BLUE);
        series2.setColor(Color.RED);
        graph.addSeries(series1);
        graph.addSeries(series2);

        return view;

    }



}