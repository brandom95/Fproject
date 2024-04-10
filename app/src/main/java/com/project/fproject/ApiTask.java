package com.project.fproject;

import android.os.AsyncTask;
import android.util.Log;
import android.annotation.SuppressLint;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
public class ApiTask extends AsyncTask<Void, Void, String> {

    private static final String TAG = ApiTask.class.getSimpleName();
    private static final String API_KEY = "fIlQMPt0IfPRMVgZYXsMJQ==2kewCXR3wPMcBRbp"; // Private API key

    private ApiCallback callback;

    public ApiTask(ApiCallback callback) {
        this.callback = callback;
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            URL url = new URL("https://api.api-ninjas.com/v1/bucketlist"); // URL for the API call
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("accept", "application/json");
            connection.setRequestProperty("X-Api-Key", API_KEY); // Include API key in X-Api-Key header
            InputStream responseStream = connection.getInputStream();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(responseStream);
            return root.path("item").asText();
        } catch (IOException e) {
            Log.e(TAG, "Error fetching data: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (result != null) {
            callback.onSuccess(result);
        } else {
            callback.onError("Failed to fetch data");
        }
    }

    public interface ApiCallback {
        void onSuccess(String result);

        void onError(String error);
    }
}