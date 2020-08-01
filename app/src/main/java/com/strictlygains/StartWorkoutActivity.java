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
    private int exerciseIndex = 0;
    private int setIndex = 0;
    private int setNum = 1;
    private int totalSetNum;
    private TextView exerciseName, setTV;
    private EditText weightET, repET, rpeET;
    private ArrayList<Exercise> userList, eHistoryList;
    private Button nextExercise, setSuccess, setFailure;
    private Workout currentWorkout;
    private Exercise currentExercise;
    private Set currentSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_start);

        // define UI elements
        exerciseName = findViewById(R.id.exerciseName);
        setTV = findViewById(R.id.setNumberTV);

        weightET = findViewById(R.id.weightET);
        repET = findViewById(R.id.repET);
        rpeET = findViewById(R.id.rpeET);

        nextExercise = findViewById(R.id.nextExercise);
        nextExercise.setOnClickListener(this);
        setSuccess = findViewById(R.id.setSuccess);
        setSuccess.setOnClickListener(this);
        setFailure = findViewById(R.id.setFailed);
        setFailure.setOnClickListener(this);

        currentWorkout = new Workout();
        currentWorkout.setExerciseList(DataHelper.loadWorkoutExercises(this, "currentWorkout.json"));
        eHistoryList = DataHelper.loadExercises(this, "exerciseHistory.json" );

        // set UI elements to reflect the first exercise
        exerciseName.setText(currentWorkout.getExercise(exerciseIndex).getName());
        currentSet = currentWorkout.getExercise(exerciseIndex).getSet(setIndex);
        weightET.setText(String.valueOf(currentSet.getWeight()));
        repET.setText(String.valueOf(currentSet.getReps()));
        rpeET.setText(String.valueOf(currentSet.getRPE()));

        totalSetNum = currentWorkout.getExercise(exerciseIndex).getSetList().size();
        setTV.setText("Set 1 / " + totalSetNum);
        // if Workout has only one exercise, update the nextExercise button to reflect this
        if(currentWorkout.getExerciseList().size() == 1)
            nextExercise.setText("Finish Workout");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setSuccess:
                String weight = String.valueOf(weightET.getText());
                // Loop through exercise history. If set weight is greater than Max, update Max
                if (eHistoryList != null)
                    for (int i = 0; i < eHistoryList.size(); i++) {
                        if (eHistoryList.get(i).getName().equals(currentWorkout.getExercise(exerciseIndex).getName())) {
                            if (!weight.equals("0") && Double.parseDouble(weight) > eHistoryList.get(i).getMax()) {
                                eHistoryList.get(i).setMax(Double.parseDouble(weight));
                                // Update max weight
                                DataHelper.updateExerciseHistory(this, eHistoryList);
                            }
                        }
                    }

                if(setNum < totalSetNum) {
                    currentSet = currentWorkout.getExercise(exerciseIndex).getSet(setIndex++);
                    currentSet.setSuccess(true);
                    updateSet(); // in case the user edited any text fields
                    updateUI();
                }


                else {
                    currentSet.setSuccess(true);
                    nextExercise.performClick();
                }
                break;
            case R.id.setFailed:
                nextExercise.performClick();
                break;
            case R.id.nextExercise:
                if (++exerciseIndex < currentWorkout.getExerciseList().size()) {
                    if(exerciseIndex == currentWorkout.getExerciseList().size() - 1)
                        nextExercise.setText("Finish Workout");

                    setIndex = 0;
                    setNum = 0;
                    updateUI();
                } else {
                    nextExercise.setClickable(false);
                    Date date = Calendar.getInstance().getTime();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy_MM_dd_HHmm", Locale.US);
                    String dateString = sdf.format(date);
                    DataHelper.saveWorkout(this, currentWorkout, new String( dateString + ".json") );
                    // Update max weight
                    DataHelper.updateExerciseHistory(this, eHistoryList);
                    startActivity(new Intent(this, MainActivity.class));
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }

    private void updateSet() {
        // if the user changed any of the values during exercise
        currentSet.setWeight(Double.parseDouble(String.valueOf(weightET.getText())));
        currentSet.setReps(Integer.parseInt(String.valueOf(repET.getText())));
        currentSet.setRPE(Integer.parseInt(String.valueOf(rpeET.getText())));
    }

    private void updateUI() {
        currentExercise = currentWorkout.getExercise(exerciseIndex);
        currentSet = currentExercise.getSet(setIndex);
        exerciseName.setText(currentExercise.getName());
        weightET.setText(String.valueOf(currentSet.getWeight()));
        repET.setText(String.valueOf(currentSet.getReps()));
        rpeET.setText(String.valueOf(currentSet.getRPE()));
        setTV.setText("Set " + ++setNum + " / " + totalSetNum);
    }
}
