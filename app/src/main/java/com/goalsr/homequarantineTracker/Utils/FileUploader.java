package com.goalsr.homequarantineTracker.Utils;

import android.content.Context;
import android.util.Log;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.goalsr.homequarantineTracker.BuildConfig;
import com.goalsr.homequarantineTracker.apiservice.ApiClient;
import com.goalsr.homequarantineTracker.db.repository.TravelTrackingRepository;

import java.io.File;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

/**
 * Created by RTLPC3 on 4/9/2019.
 */

public class FileUploader {

    public FileUploaderCallback fileUploaderCallback;
    private List<String> files;
    public int uploadIndex = -1;
    private long totalFileLength = 0;
    private long totalFileUploaded = 0;

    private String[] responses;
    private Context context;
    private float progress = 0;
//    private ProgressBar progressbar;
    private String key = "";
    private CommonApi commonApi;
   // private TaskAttachmentRepositry taskAttachmentRepositry;
    private TravelTrackingRepository travelTrackingRepository;




    public interface FileUploaderCallback {
        void onError();

        void onFinish(String[] responses);

        void onProgressUpdate(int currentpercent, int totalpercent, int filenumber);
    }

    public void uploadFiles(Context context, List<String> files, String key, FileUploaderCallback fileUploaderCallback) {
        this.fileUploaderCallback = fileUploaderCallback;
        this.context = context;
        travelTrackingRepository=new TravelTrackingRepository(context);
        this.files = files;
        this.key = key;
        this.uploadIndex = -1;
        totalFileUploaded = 0;
        totalFileLength = 0;
        uploadIndex = -1;
        commonApi = new CommonApi();
        //taskAttachmentRepositry = new TaskAttachmentRepositry(context);
        responses = new String[files.size()];
//        progressbar = new ProgressBar(context);
//        progressbar.setVisibility(View.VISIBLE);
        //Log.e("INDEXRAM",""+files.get(0).getUripath());
        for (int i = 0; i < files.size(); i++) {
            totalFileLength = totalFileLength + files.get(i).length();
        }
        uploadNext();
    }

    private void uploadNext() {
        if (files.size() > 0) {
            if (uploadIndex != -1)
                totalFileUploaded = totalFileUploaded + files.get(uploadIndex).length();
            uploadIndex++;
            if (uploadIndex < files.size()) {
                uploadSingleFile(uploadIndex);
            } else {
                fileUploaderCallback.onFinish(responses);
            }
        } else {
            fileUploaderCallback.onError();
        }
    }

    private void uploadSingleFile(final int index) {

        // Log.e("onStateChanged",index+""+files.get(index).getUripath());

        try {
            CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(context,
                    "us-west-2:8cc39e87-eaef-4978-9064-ae4ccf8d8fb5", Regions.US_WEST_2);

            ClientConfiguration c1 = new ClientConfiguration();
            c1.setMaxConnections(8);
            c1.setMaxErrorRetry(3);
            c1.setSocketTimeout(300000);
            c1.setConnectionTimeout(60000);

            AmazonS3 s3 = new AmazonS3Client(credentialsProvider, c1);
            final TransferUtility transferUtility = new TransferUtility(s3, context);

            String path = files.get(index);
            String pathName = files.get(index);
           /* String fileName = path.substring(path.lastIndexOf("/")+1);
            String filePath = path.substring(0,path.lastIndexOf("/")+1)+fileName.substring(0,fileName.lastIndexOf("."));
            Log.e("uploadSingleFileName", fileName + "-");
            Log.e("uploadSingleFilePath", filePath);*/
            //Log.e("onStateChanged", "start");
            //String awsFileName = commonApi.getJulianDateTimeNow() + "_" + key;
            if (path == null) {
                uploadNext();

            } else {
                final File file = new File(path);
                final TransferObserver observer = transferUtility.upload(ApiClient.AMAZON_BUCKET, pathName,
                        file, CannedAccessControlList.PublicRead);
                observer.setTransferListener(new TransferListener() {
                    @Override
                    public void onStateChanged(int id, TransferState state) {
                        if (BuildConfig.DEBUG) {
                            Log.e("onStateChanged", id + "" + state.name());
                        }
                        if (state != null) {
                            if (BuildConfig.DEBUG) {
                                Log.e("onStateChanged", id + state.name());
                            }
                            if (state == TransferState.COMPLETED) {
                                travelTrackingRepository.updatesyncdataimagestatus(true,observer.getKey());
                                String url = "https://" + ApiClient.AMAZON_BUCKET + ".s3.amazonaws.com/" + observer.getKey();
                                if (BuildConfig.DEBUG) {
                                    Log.e("URL :,", url);
                                }
//we just need to share this File url with Api service request.
                                try {
                                    //taskAttachmentRepositry.updatebykey(observer.getKey(),url);
                                    responses[index] = url;
                                } catch (ArrayIndexOutOfBoundsException e) {
                                    e.getStackTrace();
                                }


                                uploadNext();

                            }
                        }
                    }

                    @Override
                    public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                        long _bytesCurrent = bytesCurrent;
                        long _bytesTotal = bytesTotal;
                        //Log.e("onStateChanged", id + ""+bytesCurrent);


                    /*float percentage =  ((float)_bytesCurrent /(float)_bytesTotal * 100);
                    Log.d("percentage","" +percentage);

                    progress = progress + percentage/files.size();

                    progressbar.setProgress((int) progress);*/
                    }

                    @Override
                    public void onError(int id, Exception ex) {
                        //Toast.makeText(context, "Unable to Upload", Toast.LENGTH_SHORT).show();
                        ex.printStackTrace();
                        //Log.e("onStateChanged999", id + ex.getMessage());
                        fileUploaderCallback.onError();
                    }
                });
            }
        } catch (Exception e) {

            e.printStackTrace();
            Log.e("onStateChanged--", "" + e.getMessage());
        }
    }

    private class ProgressUpdater implements Runnable {
        private long mUploaded;
        private long mTotal;

        public ProgressUpdater(long uploaded, long total) {
            mUploaded = uploaded;
            mTotal = total;
        }

        @Override
        public void run() {
            int current_percent = (int) (100 * mUploaded / mTotal);
            int total_percent = (int) (100 * (totalFileUploaded + mUploaded) / totalFileLength);
            fileUploaderCallback.onProgressUpdate(current_percent, total_percent, uploadIndex + 1);
        }
    }
}
