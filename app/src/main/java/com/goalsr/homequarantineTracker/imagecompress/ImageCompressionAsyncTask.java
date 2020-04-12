package com.goalsr.homequarantineTracker.imagecompress;

import android.os.AsyncTask;

public abstract class ImageCompressionAsyncTask extends AsyncTask<String, Void, String> {

    String filename;

    public ImageCompressionAsyncTask(String filename) {
        this.filename = filename;
    }

    @Override
    protected String doInBackground(String... strings) {
        if(strings.length == 0 || strings[0] == null)
            return null;
        return ImageUtils.compressImage(strings[0],filename);
    }

    protected abstract void onPostExecute(String imageBytes) ;
}