package com.goalsr.homequarantineTracker.networkconnectivity;


import android.os.AsyncTask;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//this async task tries to create a socket connection with google.com. If succeeds then return true otherwise false
public class CheckInternetTask extends AsyncTask<Void, Void, Boolean> {

    private WeakReference<TaskFinished<Boolean>> mCallbackWeakReference;

    CheckInternetTask(TaskFinished<Boolean> callback) {
        mCallbackWeakReference = new WeakReference<>(callback);
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            //parse url. if url is not parsed properly then return
            URL url;
            try {
                url = new URL("https://clients3.google.com/generate_204");
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return false;
            }

            //open connection. If fails return false
            HttpURLConnection urlConnection;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }

            urlConnection.setRequestProperty("User-Agent", "Android");
            urlConnection.setRequestProperty("Connection", "close");
            urlConnection.setConnectTimeout(1500);
            urlConnection.connect();
            return urlConnection.getResponseCode() == 204 && urlConnection.getContentLength() == 0;
        } catch (IOException e) {
            return false;
        }
    }
    @Override
    protected void onPostExecute(Boolean isInternetAvailable) {
        TaskFinished<Boolean> callback = mCallbackWeakReference.get();
        if (callback != null) {
            callback.onTaskFinished(isInternetAvailable);
        }
    }
}
