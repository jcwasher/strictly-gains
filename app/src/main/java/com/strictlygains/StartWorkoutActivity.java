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
    EditText weightValue, repValue;
    ArrayList<Exercise> userList, eHistoryList;
    Button nextExercise, setSuccess, setFailure;
    Workout currentWorkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_start);
        exerciseName = findViewById(R.id.exerciseName);
        weightValue = findViewById(R.id.weightValue);
        weightValue.setOnFocusChangeListener( new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean isFocus) {
                if(isFocus)
                    weightValue.setText("");
            }
        });

        repValue = findViewById(R.id.repValue);
        repValue.setOnFocusChangeListener(  new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean isFocus) {
                if(isFocus)
                    repValue.setText("");
            }
        });
        setTV = findViewById(R.id.setTV);
        nextExercise = findViewById(R.id.nextExercise);
        nextExercise.setOnClickListener(this);
        setSuccess = findViewById(R.id.setSuccess);
        setSuccess.setOnClickListener(this);
        setFailure = findViewById(R.id.setFailed);
        setFailure.setOnClickListener(this);

        userList = DataHelper.loadExercises(this, "userexercises.json");
        eHistoryList = DataHelper.loadExercises(this, "exerciseHistory.json" );
        currentWorkout = new Workout(1);
        currentWorkout.setExerciseList(userList);
        exerciseName.setText(currentWorkout.getExercise(exerciseIndex).getName());
        if(currentWorkout.getExerciseList().size() == 1)
            nextExercise.setText(new String("Finish Workout"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setSuccess:
                currentWorkout.getExercise(exerciseIndex).addSet( new Set(Integer.parseInt(weightValue.getText().toString()), Integer.parseInt(repValue.getText().toString())) );
                currentWorkout.getExercise(exerciseIndex).getSet(setIndex++).setSuccess(true);
                setTV.setText(new String("Set " + ++setNum));
                String weight = weightValue.getText().toString();
                // Loop through exercise history. If set weight is greater than Max, update Max
                if (eHistoryList != null)
                for (int i=0; i<eHistoryList.size(); i++){
                    if (eHistoryList.get(i).getName().equals(userList.get(exerciseIndex).getName())){
                        if (!weight.equals("0") && Double.parseDouble(weight) > eHistoryList.get(i).getMax()){
                            eHistoryList.get(i).setMax(Double.parseDouble(weight));
                            Toast.makeText(this, String.valueOf(eHistoryList.get(i).getMax()), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
               //     if (!weight.equals("0") && Integer.parseInt(weight) > userList.get(exerciseIndex).getMax()) {
               //         userList.get(exerciseIndex).setMax(Double.parseDouble(weight));

                break;
            case R.id.setFailed:
                currentWorkout.getExercise(exerciseIndex).addSet( new Set(Integer.parseInt(weightValue.getText().toString()), Integer.parseInt(repValue.getText().toString())) );
                currentWorkout.getExercise(exerciseIndex).getSet(setIndex).setSuccess(false);
                nextExercise.performClick();
                break;
            case R.id.nextExercise:
                if ( exerciseIndex < currentWorkout.getExerciseList().size() - 1 ) {
                    exerciseName.setText(currentWorkout.getExercise(++exerciseIndex).getName());
                    if( exerciseIndex == currentWorkout.getExerciseList().size() - 1)
                        nextExercise.setText(new String("Finish Workout"));
                    setIndex = 0;
                    setNum = 1;
                    setTV.setText(new String("Set " + setNum));
                } else {
                    exerciseIndex = 0;
                    setIndex = 0;
                    setNum = 1;
                    nextExercise.setClickable(false);

                    Date date = Calendar.getInstance().getTime();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HHmm", Locale.US);
                    String dateString = sdf.format(date);
                    DataHelper.saveWorkout(this, currentWorkout, new String( dateString + ".json") );
                    // Update max weight
                    DataHelper.updateExerciseHistory(this, eHistoryList);
                    // DataHelper.updateExerciseHistory(this, eHistoryList);
                   // DataHelper.saveExercises(this, userList);
                    startActivity(new Intent(this, MainActivity.class));
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }
}
