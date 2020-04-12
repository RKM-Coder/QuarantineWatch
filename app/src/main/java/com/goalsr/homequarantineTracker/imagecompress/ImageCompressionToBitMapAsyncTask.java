package com.goalsr.homequarantineTracker.imagecompress;

import android.graphics.Bitmap;
import android.os.AsyncTask;

public abstract class ImageCompressionToBitMapAsyncTask extends AsyncTask<String, Void, Bitmap> {

    String filename;

    public ImageCompressionToBitMapAsyncTask(String filename) {
        this.filename = filename;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        if(strings.length == 0 || strings[0] == null)
            return null;
        return ImageUtils.getCompressedBitmap(strings[0]);
    }

    protected abstract void onPostExecute(Bitmap imageBytes) ;
}