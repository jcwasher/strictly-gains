package com.strictlygains;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class StartWorkoutActivity extends AppCompatActivity implements View.OnClickListener {
    int exerciseIndex = 0;
    int setIndex = 0;
    int setNum = 1;
    TextView exerciseName, setTV;
    EditText weightValue, repValue;
    ArrayList<Exercise> userList;
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
        currentWorkout = new Workout(1);
        currentWorkout.setExerciseList(userList);
        exerciseName.setText(currentWorkout.getExercise(exerciseIndex).getName());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setSuccess:
                currentWorkout.getExercise(exerciseIndex).addSet( new Set(Integer.parseInt(weightValue.getText().toString()), Integer.parseInt(repValue.getText().toString())) );
                currentWorkout.getExercise(exerciseIndex).getSet(setIndex++).setSuccess(true);
                setTV.setText(new String("Set " + ++setNum));
                String weight = weightValue.getText().toString();
                // if set weight is greater than Max, update Max
                if (!weight.equals("0") && Integer.parseInt(weight) > userList.get(exerciseIndex).getMax()) {
                    userList.get(exerciseIndex).setMax(Integer.parseInt(weight));
                }
                break;
            case R.id.setFailed:
                currentWorkout.getExercise(exerciseIndex).addSet( new Set(Integer.parseInt(weightValue.getText().toString()), Integer.parseInt(repValue.getText().toString())) );
                currentWorkout.getExercise(exerciseIndex).getSet(setIndex).setSuccess(false);
                nextExercise.performClick();
                break;
            case R.id.nextExercise:
                if ( exerciseIndex < currentWorkout.getExerciseList().size() - 1 ) {
                    exerciseName.setText(currentWorkout.getExercise(++exerciseIndex).getName());
                    setIndex = 0;
                    setNum = 1;
                    setTV.setText(new String("Set " + setNum));
                } else {
                    exerciseIndex = 0;
                    setIndex = 0;
                    setNum = 1;
                    nextExercise.setClickable(false);
                    DataHelper.saveWorkout(this, currentWorkout, "Workout1.json");
                    startActivity(new Intent(this, MainActivity.class));
                    // Update max weight
                    DataHelper.saveExercises(this, userList);
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }
}
