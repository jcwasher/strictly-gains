package com.strictlygains;

import android.content.Context;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

class DataHelper {

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

    static void saveExercises(Context context, ArrayList<Exercise> list) {
        try {
            File cacheFile = File.createTempFile("userexercises.json", null, context.getCacheDir());
            FileWriter writer = new FileWriter(cacheFile);
            JSONArray jsonArray = new JSONArray(list);
            writer.write(jsonArray.toString());
            writer.flush();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
