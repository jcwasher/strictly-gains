package com.strictlygains;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class StartWorkoutActivity extends AppCompatActivity implements View.OnClickListener {
    int exerciseIndex = 0;
    int setIndex = 0;
    int setNum = 1;
    TextView exerciseName, setTV;
    EditText weightET, repET, rpeET;
    ArrayList<Exercise> userList, eHistoryList;
    Button nextExercise, setSuccess, setFailure;
    Workout currentWorkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_start);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setSuccess:
                /*
                // Loop through exercise history. If set weight is greater than Max, update Max
                if (eHistoryList != null)
                for (int i=0; i<eHistoryList.size(); i++){
                    if (eHistoryList.get(i).getName().equals(userList.get(exerciseIndex).getName())){
                        if (!weight.equals("0") && Double.parseDouble(weight) > eHistoryList.get(i).getMax()){
                            eHistoryList.get(i).setMax(Double.parseDouble(weight));
                        }
                    }
                } */
                break;
            case R.id.setFailed:
                nextExercise.performClick();
                break;
            case R.id.nextExercise:
                if (true) {
                    // rework
                } else {
                    nextExercise.setClickable(false);
                    /*
                    Date date = Calendar.getInstance().getTime();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HHmm", Locale.US);
                    String dateString = sdf.format(date);
                    DataHelper.saveWorkout(this, currentWorkout, new String( dateString + ".json") );
                    // Update max weight
                    DataHelper.updateExerciseHistory(this, eHistoryList);
                    startActivity(new Intent(this, MainActivity.class)); */
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }
}
