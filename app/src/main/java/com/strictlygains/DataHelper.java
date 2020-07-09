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
                exercises.add( new Exercise( j.getInt("id"), j.getString("name"), j.getString("focus") ) );
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
                exercises.add( new Exercise( j.getInt("id"), j.getString("name"), j.getString("focus") ) );
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