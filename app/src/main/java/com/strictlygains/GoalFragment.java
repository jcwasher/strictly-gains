package com.strictlygains;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class GoalFragment extends Fragment {
    private PieChart pieChart;
    private ArrayList<Exercise> userList = new ArrayList<>();
    private TextView maxTextView;
    private int index = 0;
    private String max = "0";
    private String name = "";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.goal_layout, container, false);

        pieChart = (PieChart) view.findViewById(R.id.pieChart);
        userList = DataHelper.loadExercises(Objects.requireNonNull(getContext()), "userexercises.json");
        maxTextView = view.findViewById(R.id.maxTextView);
        if (userList != null) {
            max = String.valueOf(userList.get(index).getMax());
            name = userList.get(index).getName();
        }
        maxTextView.setText(max);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);

        ArrayList<PieEntry> values = new ArrayList<>();
        values.add(new PieEntry(75f, "Progress"));
        values.add(new PieEntry(25f, "Remaining"));
        PieDataSet dataSet = new PieDataSet(values, name);
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.YELLOW);

        pieChart.setData(data);
        return view;
    }
}
