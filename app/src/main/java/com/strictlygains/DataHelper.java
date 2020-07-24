package com.strictlygains;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

class DataHelper {

    // loads default exercises
    static ArrayList<Exercise> loadExercises(Context context) {
        ArrayList<Exercise> exercises = new ArrayList<>();
        String json = "";

        try {
            InputStream is = context.getAssets().open("exercises.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        try {
            JSONObject obj = new JSONObject(json);
            JSONArray jsonArray = obj.getJSONArray("exercises");

            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject j = jsonArray.getJSONObject(i);
                exercises.add( new Exercise( j.getInt("id"), j.getInt("max"), j.getInt("goal"), j.getString("name"), j.getString("focus") ) );
            }
        } catch(JSONException e) {
            e.printStackTrace();
            return null;
        }

        return exercises;
    }

    // loads user specified exercises
    static ArrayList<Exercise> loadExercises(Context context, String filename) {
        ArrayList<Exercise> exercises = new ArrayList<>();
        String json = "";

        try {
            InputStream is = new FileInputStream( new File(context.getFilesDir(), filename));
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        try {
            JSONObject obj = new JSONObject(json);
            JSONArray jsonArray = obj.getJSONArray("userexercises");

            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject j = jsonArray.getJSONObject(i);
                exercises.add( new Exercise( j.getInt("id"), j.getInt("max"), j.getInt("goal"), j.getString("name"), j.getString("focus") ) );
            }
        } catch(JSONException e) {
            e.printStackTrace();
            return null;
        }

        return exercises;
    }

    static void saveExercises(Context context, ArrayList<Exercise> list) {
        try {
            File file = new File(context.getFilesDir(), "userexercises.json");
            FileWriter writer = new FileWriter(file);
            JSONObject finalOutput = new JSONObject();
            JSONArray jsonArray = new JSONArray();

            for(int i = 0; i < list.size(); i++) {
                try {
                    JSONObject j = new JSONObject();
                    j.put("id", list.get(i).getID());
                    j.put("max", list.get(i).getMax());
                    j.put("goal", list.get(i).getGoal());
                    j.put("name", list.get(i).getName());
                    j.put("focus", list.get(i).getFocus());
                    j.put("setList", new JSONArray() );
                    jsonArray.put(j);
                } catch (JSONException e) {
                    e.printStackTrace();
                    break;
                }
            }

            try {
                finalOutput.put("userexercises", jsonArray);
                writer.write(finalOutput.toString(4));
                writer.flush();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    static void saveWorkout(Context context, Workout w, String filename) {
        try {
            File file = new File(context.getFilesDir(), filename);
            FileWriter writer = new FileWriter(file);
            JSONObject finalOutput = new JSONObject();
            JSONArray exerciseArray = new JSONArray();

            for(int i = 0; i < w.getExerciseList().size(); i++) {
                try {
                    JSONObject exercise = new JSONObject();
                    JSONArray setArray = new JSONArray();
                    exercise.put("id", w.getExercise(i).getID());
                    exercise.put("max", w.getExercise(i).getMax());
                    exercise.put("goal", w.getExercise(i).getGoal());
                    exercise.put("name", w.getExercise(i).getName());
                    exercise.put("focus", w.getExercise(i).getFocus());

                    for(int j = 0; j < w.getExercise(i).getSetList().size(); j++) {
                        JSONObject set = new JSONObject();
                        set.put("weight", w.getExercise(i).getSet(j).getWeight());
                        set.put("reps", w.getExercise(i).getSet(j).getReps());
                        set.put("success", w.getExercise(i).getSet(j).isSuccess());
                        setArray.put(set);
                    }

                    exercise.put("setList", setArray);
                    exerciseArray.put(exercise);
                } catch (JSONException e) {
                    e.printStackTrace();
                    break;
                }
            }

            try {
                finalOutput.put(filename, exerciseArray);
                writer.write(finalOutput.toString(4));
                writer.flush();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    static ArrayList<Exercise> loadWorkoutExercises(Context context, String filename) {
        ArrayList<Exercise> exercises = new ArrayList<>();

        String json = "";

        try {
            InputStream is = new FileInputStream( new File(context.getFilesDir(), filename));
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        try {
            JSONObject obj = new JSONObject(json);
            JSONArray jsonArray = obj.getJSONArray(filename);

            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject j = jsonArray.getJSONObject(i);
                exercises.add( new Exercise( j.getInt("id"), j.getInt("max"), j.getInt("goal"), j.getString("name"), j.getString("focus") ) );

                JSONArray jsonSetList = j.getJSONArray("setList");
                for(int k = 0; k < jsonSetList.length(); k++) {
                    JSONObject l = jsonSetList.getJSONObject(k);
                    exercises.get(i).addSet(new Set( l.getDouble("weight"), l.getInt("reps"), l.getBoolean("success")));
                }
            }
        } catch(JSONException e) {
            e.printStackTrace();
            return null;
        }

        return exercises;
    }



    // Used to store max and goals
    static void updateExerciseHistory(Context context, ArrayList<Exercise> list) {
        try {
            File file = new File(context.getFilesDir(), "exerciseHistory.json");
            FileWriter writer = new FileWriter(file);
            JSONObject finalOutput = new JSONObject();
            JSONArray jsonArray = new JSONArray();

            for(int i = 0; i < list.size(); i++) {
                try {
                    JSONObject j = new JSONObject();
                    j.put("id", list.get(i).getID());
                    j.put("max", list.get(i).getMax());
                    j.put("goal", list.get(i).getGoal());
                    j.put("name", list.get(i).getName());
                    j.put("focus", list.get(i).getFocus());
                    j.put("setList", new JSONArray() );
                    jsonArray.put(j);
                } catch (JSONException e) {
                    e.printStackTrace();
                    break;
                }
            }

            try {
                finalOutput.put("userexercises", jsonArray);
                writer.write(finalOutput.toString(4));
                writer.flush();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
