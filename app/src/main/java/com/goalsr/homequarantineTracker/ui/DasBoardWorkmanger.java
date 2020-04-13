package com.goalsr.homequarantineTracker.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.goalsr.homequarantineTracker.R;
import com.goalsr.homequarantineTracker.Utils.AppConstants;
import com.goalsr.homequarantineTracker.Utils.FileUploader;
import com.goalsr.homequarantineTracker.Utils.PreferenceStore;
import com.goalsr.homequarantineTracker.YelligoApplication;
import com.goalsr.homequarantineTracker.apiservice.NetworkService;
import com.goalsr.homequarantineTracker.base.BaseActivity;
import com.goalsr.homequarantineTracker.dialog.CustomDialogDistance;
import com.goalsr.homequarantineTracker.dialog.CustomDialogSOSdialog;
import com.goalsr.homequarantineTracker.gpsenable.GpsUtils;
import com.goalsr.homequarantineTracker.imagecompress.ImageCompressionAsyncTask;
import com.goalsr.homequarantineTracker.resposemodel.ReqHeader;
import com.goalsr.homequarantineTracker.resposemodel.ReqTrailer;
import com.goalsr.homequarantineTracker.resposemodel.emergency.ReqBodyEmergency;
import com.goalsr.homequarantineTracker.resposemodel.emergency.ReqEmegency;
import com.goalsr.homequarantineTracker.resposemodel.emergency.ResEmergency;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.goalsr.homequarantineTracker.Utils.AppConstants.myPermissions;

public class DasBoardWorkmanger extends BaseActivity {
    // Used in checking for runtime permissions.
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private static final String TAG = "worker";
    @BindView(R.id.btn_submit)
    Button btncheck;
    @BindView(R.id.btn_emergency)
    Button btnEmergency;
    private NetworkService networkService;


    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;

    /**
     * The fastest rate for active location updates. Updates will never be more frequent
     * than this value.
     */
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    /**
     * The current location.
     */
    private Location mLocation;

    /**
     * Provides access to the Fused Location Provider API.
     */
    private FusedLocationProviderClient mFusedLocationClient;

    private LocationCallback mLocationCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yelligo_arogya_mitra);
        ButterKnife.bind(this);

        checkPermissionFortarck();

        PreferenceStore.getPrefernceHelperInstace().setString(YelligoApplication.getContext(),PreferenceStore.USER_DATE,AppConstants.getCurrentDate());
        initMvp();
    }
    private void initMvp() {

        networkService = new NetworkService();
        networkService.inject(YelligoApplication.getContext());



    }
    private void checkPermissionFortarck() {
        if (!checkPermissionAvl()) {

            requestPermissions();
        } else {

            startWorkmanager();
        }
    }

    private boolean checkPermissions() {
        return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
    }

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            // Log.i(TAG, "Displaying permission rationale to provide additional context.");
            Snackbar.make(
                    findViewById(R.id.activity_main),
                    R.string.permission_rationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            ActivityCompat.requestPermissions(DasBoardWorkmanger.this,
                                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    REQUEST_PERMISSIONS_REQUEST_CODE);
                        }
                    })
                    .show();
        } else {
            // Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(DasBoardWorkmanger.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    public static final String IMAGE_PATH = "IMAGE_PATH";
    public static final String RETURN_DATA = "return-data";
    private Uri mCapturedImageURI = null;
    public static final int REQUEST_CAMERA_PERMISSION = 102;
    public static final int REQUEST_CAMERA_IMAGE_SELFIE = 192;
    public static final int REQUEST_READ_EXTERNAL_STORAGE_PERMISSION = 101;
    public static final int REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION = 110;

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void launchCameraActionselfie() {
        try {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, IMAGE_PATH);
            mCapturedImageURI = getContentResolver()
                    .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            // Log.e("launchCamera", mCapturedImageURI.toString());
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
            intent.putExtra(RETURN_DATA, true);
            startActivityForResult(intent, REQUEST_CAMERA_IMAGE_SELFIE);
        } catch (Exception e) {
            e.printStackTrace();
            if (e instanceof SecurityException) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION);
                } else if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_READ_EXTERNAL_STORAGE_PERMISSION);
                } else if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA},
                            REQUEST_CAMERA_PERMISSION);
                }
            }
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */


    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 5000: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    startWorkmanager();

                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case 199:
                int flag = 0;
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] == -1) {
                        flag = -1;
                        break;
                    }
                }
                if (flag == -1)
                    new AlertDialog.Builder(this)
                            .setTitle("Permissions needed.")
                            .setMessage("Please allow "
                                    + getResources().getString(R.string.app_name)
                                    + " to access location, storage, phone call and camera.")
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //Prompt the user once explanation has been shown
                                    checkPermissionFortarck();
                                }
                            })
                            .create()
                            .show();


        }

    }
   /* public void onRequestPermissionsRccesult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        //Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
               // Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted.
               // mService.requestLocationUpdates();

            } else {
                // Permission denied.
               // setButtonsState(false);
                Snackbar.make(
                        findViewById(R.id.activity_main),
                        R.string.permission_denied_explanation,
                        Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.settings, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        })
                        .show();
            }
        }
    }*/

    private void startWorkmanager() {
        /*PeriodicWorkRequest periodicWork = new PeriodicWorkRequest.Builder(MyWorkerLocationService.class, 15, TimeUnit.MINUTES)
                .addTag(AppConstants.TRACKTAG)
                .build();
        //WorkManager.getInstance().enqueueUniquePeriodicWork("Location", ExistingPeriodicWorkPolicy.REPLACE, periodicWork);
        WorkManager.getInstance().enqueue(periodicWork);*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        GpsEnableChecking();
        getCurrentLOc();

        if (!PreferenceStore.getPrefernceHelperInstace().getString(YelligoApplication.getContext(),PreferenceStore.USER_DATE).equalsIgnoreCase(AppConstants.getCurrentDate())){
            Intent intent = new Intent(getApplicationContext(), SplashMainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finishAffinity();
        }

       /* String lastsynctime= PreferenceStore.getPrefernceHelperInstace().getString(YelligoApplication.getContext(),PreferenceStore.LAST_USER_STATUSUPDATE_DATE);
        if (!lastsynctime.equalsIgnoreCase("")){
            long v=AppConstants.getLastStausUpdatetimdif(lastsynctime);
            if (v>15){
                Toast.makeText(YelligoApplication.getContext(),"Please Update your current health status",Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(YelligoApplication.getContext(),"Please Update your current health status",Toast.LENGTH_LONG).show();
            //PreferenceStore.getPrefernceHelperInstace().setString(YelligoApplication.getContext(),PreferenceStore.LAST_USER_STATUSUPDATE_DATE,AppConstants.getCurrentDateTime());
        }*/

    }

    public void GpsEnableChecking() {

        new GpsUtils(this).turnGPSOn(new GpsUtils.onGpsListener() {
            @Override
            public void gpsStatus(boolean isGPSEnable) {
                // turn on GPS
                AppConstants.isGPS = isGPSEnable;
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Cursor cursor;
        try {

            if (checkPermissionAvl()) {
                Toast.makeText(getApplicationContext(), "Please check Permission", Toast.LENGTH_SHORT).show();
            } else if (resultCode == Activity.RESULT_OK) {
                switch (requestCode) {
                    case 142111:

                        break;
                    case REQUEST_CAMERA_IMAGE_SELFIE:

                        //Log.e("REQUEST_CAMERA_IMAGE", "------------");
                        //Log.e("mCapturedImageURI", "------" + mCapturedImageURI + "------");
                        String[] projection1 = {MediaStore.Images.Media.DATA};
                        cursor = getContentResolver().query(mCapturedImageURI, projection1, null, null, null);
                        if (cursor != null) {
                            int column_index_data = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                            cursor.moveToFirst();
                            String path = cursor.getString(column_index_data);
                            cursor.close();

                            String IMAGE_NAME = PreferenceStore.getPrefernceHelperInstace().getString(YelligoApplication.getContext(), PreferenceStore.CITIZEN_ID) + "_" + System.currentTimeMillis() + "_" + "selfi" + ".png";
                            if (isImageSupported(path)) {
                                compressImage(path, IMAGE_NAME, getFilename(IMAGE_NAME));
                            } else {

                                Toast.makeText(getApplicationContext(), "Image File Not Supported", Toast.LENGTH_SHORT).show();


                            }


                        } else {
                            //Log.e("cursor", "------null------");
                        }
                        break;

                    case 121212:
                        //Log.e("REQUEST_FILE", "------------");


                        break;
                    case 201:

                        break;
                }

              /*  if (files.size() > 0) {
                    if (BuildConfig.DEBUG) {
                        Log.e("uploadFilesSize", files.size() + "--");
                    }

                    rvAttachments.setAdapter(adapter);
                } else {
                    //Log.e("uploadFilesSize", files.size() + "--");
                }*/

            } else if (resultCode == Activity.RESULT_CANCELED) {

            }
        } catch (Exception e) {
            // For OOM exception
        }
    }

    private void compressImage(String imagePath, String staticstring, String filepathname) {

        ImageCompressionAsyncTask imageCompression = new ImageCompressionAsyncTask(filepathname) {
            @Override
            protected void onPostExecute(String file) {
                Log.e("Filepath", file);
                if (!TextUtils.isEmpty(file)) {
                    //doSameLocalDb(file, staticstring);
                    uploadtoServer(file);
                }
                // image here is compressed & ready to be sent to the server
            }
        };
        imageCompression.execute(imagePath);

    }

    public static String getFilename(String filename) {
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/ImageCompApp/HQUAR");

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            mediaStorageDir.mkdirs();
        }

//        String mImageName = "IMG_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        String mImageName = filename;
        String uriString = (mediaStorageDir.getAbsolutePath() + "/" + mImageName);
        return uriString;
    }

    private boolean isImageSupported(String path) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(path, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

        if (actualHeight == 0 || actualWidth == 0) {

            Toast.makeText(getApplicationContext(), "We are sorry, the file format is not supported, Please select valid file", Toast.LENGTH_SHORT).show();

            return false;
        } else {
            return true;
        }
        //return false;
    }

    private void uploadtoServer(String filena) {
        List<String> ll = new ArrayList<String>();
        ll.add(filena);
        new FileUploader().uploadFiles(YelligoApplication.getContext(), ll, "", new FileUploader.FileUploaderCallback() {
            @Override
            public void onError() {
                //hideProgressDialog();
                // Toast.makeText(getActivity(),ConstantMessage.tryagain,Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFinish(String[] responses) {
                /*if (responses != null && responses.length > 0) {
                    for (int i = 0; i < responses.length; i++) {
                        TaskAttachment attachment = filesattachment.get(i);
                        attachment.setAttachment(responses[i]);
                        attachment.setSyncstatus(true);
                        filesattachment.add(attachment);
                    }
                    taskAttachmentViewModel.UpdateItem(filesattachment);
                }*/

            }

            @Override
            public void onProgressUpdate(int currentpercent, int totalpercent, int filenumber) {

            }
        });
    }

    public boolean checkPermissionAvl() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            boolean resultPermission = true;
            int myPermFlag = 0;
            for (String myPermission : myPermissions) {
                if ((ActivityCompat.checkSelfPermission(this, myPermission) != PackageManager.PERMISSION_GRANTED)) {
                    myPermFlag = 1;
                    break;
                }
            }
            switch (myPermFlag) {
                case 0:
                    //continueLoading();
                    resultPermission = true;
                    break;
                case 1:
                    resultPermission = false;

                    break;
            }
            return resultPermission;
        } else {
            return true;
        }
    }


    public void checkPermissions(String... permissions) {
        int flagPermission = 1;
        for (int i = 0; i < permissions.length; i++) {
            if ((ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED)) {
                flagPermission = 0;
            }
        }
        if (flagPermission == 0) {
            ActivityCompat.requestPermissions(this, permissions, 199);
        }
    }

    /*@OnClick(R.id.btn_submit)
    public void onViewClicked() {

    }*/

    @Override
    public void onInternetConnectivityChanged(boolean isConnected) {

    }

    public void showdialogEmergencr(String message) {
        CustomDialogDistance dialog = new CustomDialogDistance(DasBoardWorkmanger.this, "",
                new CustomDialogDistance.OnButtonClickListener() {
                    @Override
                    public void onLeftButtonClick(CustomDialogDistance dialog) {

                        //callCheckInDBAInormalvisit(distancefar);

                        makeEmaergencyReq();

                        //showsucessdilog();
                        dialog.dismiss();
                        // Toast.makeText(getActivity(),"Customer created successfully",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onRightButtonClick(CustomDialogDistance dialog, String notes) {
                        dialog.dismiss();

                    }


                });
        dialog.setCancelable(false);
        dialog.setRightButtonText("No");
        dialog.setRightButtonVisibility(View.VISIBLE);
        dialog.setLeftButtonVisibility(View.VISIBLE);
        dialog.setLeftButtonText("Yes");
        dialog.setDialogType(CustomDialogDistance.TYPE_ALERT);
        dialog.setDescription("" + message);
        dialog.show();
    }

    private void makeEmaergencyReq() {
        showProgressDialogStatic();
        ReqEmegency reqEmegency=new ReqEmegency();
        ReqBodyEmergency reqBodyEmergency=new ReqBodyEmergency();
        reqBodyEmergency.setLatitude(PreferenceStore.getPrefernceHelperInstace().getString(YelligoApplication.getContext(),PreferenceStore.USER_LONGITUDE));
        reqBodyEmergency.setLongitude(PreferenceStore.getPrefernceHelperInstace().getString(YelligoApplication.getContext(),PreferenceStore.USER_LONGITUDE));
        //reqBodyEmergency.setUserId(PreferenceStore.getPrefernceHelperInstace().getString(YelligoApplication.getContext(),PreferenceStore.CITIZEN_ID));
        reqBodyEmergency.setQuarantineStatus("3");
        reqEmegency.setBody(reqBodyEmergency);
        reqEmegency.setHeader(new ReqHeader());
        reqEmegency.setTrailer(new ReqTrailer());

        networkService.makeemergency(reqEmegency, new NetworkService.NetworkServiceListener() {
            @Override
            public void onFailure(Object response) {
                hideProgressDialogStatic();
            }

            @Override
            public void onAuthFail(Object error) {
                hideProgressDialogStatic();
            }

            @Override
            public void onSuccess(Object response, Boolean cancelFlag) {
                hideProgressDialogStatic();
                if (response instanceof ResEmergency){
                    if (!((ResEmergency) response).getData().getId().equalsIgnoreCase("")){
                        showsucessdilog();
                    }else {
                        Toast.makeText(YelligoApplication.getContext(),"Please try again.",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });


    }

    private void showsucessdilog() {
        CustomDialogSOSdialog dialog = new CustomDialogSOSdialog(DasBoardWorkmanger.this, "",
                new CustomDialogSOSdialog.OnButtonClickListener() {
                    @Override
                    public void onLeftButtonClick(CustomDialogSOSdialog dialog) {

                        //callCheckInDBAInormalvisit(distancefar);

                        showsucessdilog();
                        dialog.dismiss();
                        // Toast.makeText(getActivity(),"Customer created successfully",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onRightButtonClick(CustomDialogSOSdialog dialog, String notes) {
                        dialog.dismiss();

                    }


                });
        dialog.setCancelable(false);
       /* dialog.setRightButtonText("No");
        dialog.setRightButtonVisibility(View.VISIBLE);
        dialog.setLeftButtonVisibility(View.VISIBLE);
        dialog.setLeftButtonText("Yes");
        dialog.setDialogType(CustomDialogDistance.TYPE_ALERT);
        dialog.setDescription("" + message);*/
        dialog.show();
    }

    @OnClick({R.id.btn_emergency, R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_emergency:
                if (networkService.haveNetworkAccess()){
                    showdialogEmergencr("Do you want to contact with our emergency service?");
                }else {
                    Toast.makeText(YelligoApplication.getContext(),"Please Enable Internet",Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.btn_submit:
                getCommonApi().openNewScreen(SymptomCheckerActivity.class);
                break;
        }
    }
    private void getCurrentLOc() {

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(YelligoApplication.getContext());
        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
            }
        };

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        try {
            mFusedLocationClient
                    .getLastLocation()
                    .addOnCompleteListener(new OnCompleteListener<Location>() {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            if (task.isSuccessful() && task.getResult() != null) {
                                mLocation = task.getResult();
                                PreferenceStore.getPrefernceHelperInstace().setString(YelligoApplication.getContext(),PreferenceStore.USER_LATITUDE,mLocation.getLatitude()+"");
                                PreferenceStore.getPrefernceHelperInstace().setString(YelligoApplication.getContext(),PreferenceStore.USER_LONGITUDE,mLocation.getLongitude()+"");
                                //Log.d(TAG, "LocationWORK : " + mLocation.getLongitude()+"----"+mLocation.getLatitude()+"---"+AppConstants.getCurrentDateTime());


                                mFusedLocationClient.removeLocationUpdates(mLocationCallback);
                            } else {
                                Log.w(TAG, "Failed to get location.");
                            }
                        }
                    });
        } catch (SecurityException unlikely) {
            Log.e(TAG, "Lost location permission." + unlikely);
        }

        try {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest, null);
        } catch (SecurityException unlikely) {
            //Utils.setRequestingLocationUpdates(this, false);
            Log.e(TAG, "Lost location permission. Could not request updates. " + unlikely);
        }
			/*} else {
				Log.d(TAG, "Time up to get location. Your time is : " + DEFAULT_START_TIME + " to " + DEFAULT_END_TIME);
			}*/
    }

}
