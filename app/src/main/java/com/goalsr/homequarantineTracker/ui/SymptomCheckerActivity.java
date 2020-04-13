package com.goalsr.homequarantineTracker.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.goalsr.homequarantineTracker.R;
import com.goalsr.homequarantineTracker.Utils.AppConstants;
import com.goalsr.homequarantineTracker.Utils.FileUploader;
import com.goalsr.homequarantineTracker.Utils.PreferenceStore;
import com.goalsr.homequarantineTracker.YelligoApplication;
import com.goalsr.homequarantineTracker.apiservice.NetworkService;
import com.goalsr.homequarantineTracker.base.BaseActivity;
import com.goalsr.homequarantineTracker.db.model.QHTracker;
import com.goalsr.homequarantineTracker.dialog.CustomDialogSuccessdialog;
import com.goalsr.homequarantineTracker.gpsenable.GpsUtils;
import com.goalsr.homequarantineTracker.imagecompress.ImageCompressionAsyncTask;
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

import static com.goalsr.homequarantineTracker.Utils.AppConstants.myPermissions;

public class SymptomCheckerActivity extends BaseActivity implements ListAdapter.CheckedListener {

    private static final String TAG = "symtom";
    TextView tvView,takesefi,takesefifilename;
    EditText etView;
    RecyclerView rvView;
    Button submitBtn;

    ListAdapter adapter;
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    public static final String IMAGE_PATH = "IMAGE_PATH";
    public static final String RETURN_DATA = "return-data";
    private Uri mCapturedImageURI = null;
    public static final int REQUEST_CAMERA_PERMISSION = 102;
    public static final int REQUEST_CAMERA_IMAGE_SELFIE = 192;
    public static final int REQUEST_READ_EXTERNAL_STORAGE_PERMISSION = 101;
    public static final int REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION = 110;
    String et;

    ArrayList<String> selectedString;
    private String userID="12222";

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
    private String filename,filepath;
    private NetworkService networkService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symtom);

        tvView = findViewById(R.id.txt_view);
        takesefifilename = findViewById(R.id.takesefifilename);
        takesefi = findViewById(R.id.takesefi);
        etView = findViewById(R.id.et_text);
        rvView = findViewById(R.id.rv_view);
        submitBtn = findViewById(R.id.submit_btn);
        initMvp();
        //userID= PreferenceStore.getPrefernceHelperInstace().getString(YelligoApplication.getContext(),PreferenceStore.CITIZEN_ID);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if (validate()){*/
                    gotoNext();
//                }
            }
        });

        takesefi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               launchCameraActionselfie();
            }
        });

        initView();



    }
    private void initMvp() {

        networkService = new NetworkService();
        networkService.inject(YelligoApplication.getContext());



    }


    private void gotoNext() {
        showProgressDialogStatic();
        for (int i=0; i<selectedString.size(); i++){
            if (selectedString.get(i).equals("Other")){
                selectedString.set(i, et);
            }
        }

        QHTracker travelTracking = new QHTracker();
        travelTracking.setPrimary_id(""+userID+"_"+AppConstants.getJulianDateTimeNow());
        travelTracking.setMcurrentdatetimejulian(AppConstants.getJulianDateTimeNow());
        travelTracking.setMcurrenttime(AppConstants.getCHourTimeNow());
        travelTracking.setMcurrentdatetime(AppConstants.getCurrentDateTime());
        travelTracking.setSyncstutas(false);
        travelTracking.setTypeofdatatracker("status");
        travelTracking.setInfodata(android.text.TextUtils.join(",", selectedString));
        travelTracking.setSelfifilepathlocal(filepath);
        travelTracking.setSelfifilepathserver(filename);

        travelTracking.setLocation_lat("" + PreferenceStore.getPrefernceHelperInstace().getString(YelligoApplication.getContext(),PreferenceStore.USER_LATITUDE));
        travelTracking.setLocation_lng("" + PreferenceStore.getPrefernceHelperInstace().getString(YelligoApplication.getContext(),PreferenceStore.USER_LONGITUDE));

        if (isvalHome()){
            travelTracking.setIsAvlHomeChecker("1");

        }else {
            travelTracking.setIsAvlHomeChecker("0");
        }
        getTravelTrackingRepository().insert(travelTracking);


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
              /*  hideProgressDialogApp();
                makesynccall();*/
              if (networkService.haveNetworkAccess()) {
                  makesynccall();
              }
                PreferenceStore.getPrefernceHelperInstace().setString(YelligoApplication.getContext(),PreferenceStore.LAST_USER_STATUSUPDATE_DATE,AppConstants.getCurrentDateTime());
                showdialog("successfully submitted");
                hideProgressDialogStatic();
            }
        }, 500);
       /* Intent i = new Intent(this, Main2Activity.class);
        i.putExtra("selectedString", selectedString.toString());
        startActivity(i);*/
    }

    private void makesynccall() {

    }

    private boolean isvalHome() {
        String user_Lat=PreferenceStore.getPrefernceHelperInstace().getString(YelligoApplication.getContext(),PreferenceStore.USER_HOME_LAT);
        String user_Lng=PreferenceStore.getPrefernceHelperInstace().getString(YelligoApplication.getContext(),PreferenceStore.USER_HOME_LNG);
        String  userrad=PreferenceStore.getPrefernceHelperInstace().getString(YelligoApplication.getContext(),PreferenceStore.USER_RADIOUS);
        double diff=100.0;
        if (!userrad.equalsIgnoreCase("")){
            diff=Double.parseDouble(userrad);
        }/* ApiBackGround apiBackGround=new ApiBackGround(YelligoApplication.getContext());
        apiBackGround.uploadsync(false);*/
        /*double distancefar = AppConstants.distance(Double.valueOf(user_Lat), Double.parseDouble(user_Lng),
                mLocation.getLatitude(),
                mLocation.getLongitude());*/



        double distancefar = AppConstants.distance(Double.valueOf(user_Lat), Double.parseDouble(user_Lng),
				Double.valueOf(PreferenceStore.getPrefernceHelperInstace().getString(YelligoApplication.getContext(), PreferenceStore.USER_LATITUDE)),
				Double.valueOf(PreferenceStore.getPrefernceHelperInstace().getString(YelligoApplication.getContext(), PreferenceStore.USER_LONGITUDE)));
        if (distancefar>diff){
           /* ApiBackGround apiBackGround=new ApiBackGround(YelligoApplication.getContext());
            apiBackGround.makeEmaergencyReqOut();*/
            return true;
        }
        return false;
    }

    private boolean validate() {
        if (selectedString == null || selectedString.size() == 0){
            Toast.makeText(this, "Please Choose any one", Toast.LENGTH_LONG).show();
            return false;
        } else if (isOtherSelected()){
            et = etView.getText().toString();
            if (et.isEmpty()){
                etView.setError("Please provide some text");
                Toast.makeText(this, "Please provide some text", Toast.LENGTH_LONG).show();
                return false;
            }
        }
        return true;
    }

    private boolean isOtherSelected() {
        for (int i=0; i<selectedString.size(); i++){
            if (selectedString.get(i).equals("Other")){
                return true;
            }
        }
        return false;
    }

    private void initView() {
        adapter = new ListAdapter(this, new ArrayList<SymtomModel>());
        adapter.setListener(this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvView.setLayoutManager(manager);
        rvView.setAdapter(adapter);
        selectedString = new ArrayList<>();
    }

    private ArrayList<SymtomModel> getList() {
      /*  ArrayList<String> listString = new ArrayList<>();
        listString.add("Fever");
        listString.add("cough");
        listString.add("Cold");
        listString.add("Tiredness");
        listString.add("Other");*/
        return getCommonApi().getListOdSymtom();
    }

    @Override
    public void onItemChecked(View v, int position, SymtomModel item, ArrayList<SymtomModel> listString, boolean isChecked) {
        if (isChecked){
            if (position == listString.size()-1 && item.getStrname().equals("Others")){
                etView.setVisibility(View.VISIBLE);
            }
            addOption(item.getStrname());
        } else {
            if (position == listString.size()-1 && item.getStrname().equals("Others")){
                etView.setText("");
                etView.setVisibility(View.GONE);
            }
            removeOption(item.getStrname());
        }
    }

    private void removeOption(String item) {
        for (int i=0; i<selectedString.size(); i++){
            if (selectedString.get(i).equals(item)){
                selectedString.remove(i);
            }
        }
    }

    private void addOption(String item) {
        selectedString.add(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkPermissionFortarck();
        GpsEnableChecking();
        etView.setText("");
        selectedString.clear();
        adapter.setValue(getList());
//        getCurrentLOc();
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

    @Override
    public void onInternetConnectivityChanged(boolean isConnected) {

    }

    private void checkPermissionFortarck() {
        if (!checkPermissionAvl()) {

            requestPermissions();
        } else {

            getCurrentLOc();
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
                            ActivityCompat.requestPermissions(SymptomCheckerActivity.this,
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
            ActivityCompat.requestPermissions(SymptomCheckerActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }



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

                    getCurrentLOc();

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

            if (!checkPermissionAvl()) {
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
                                filename=IMAGE_NAME;
                                filepath=getFilename(IMAGE_NAME);
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

               /* if (files.size() > 0) {
                    if (BuildConfig.DEBUG) {
                        Log.e("uploadFilesSize", files.size() + "--");
                    }

                    //rvAttachments.setAdapter(adapter);
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
                    takesefifilename.setVisibility(View.VISIBLE);
                    takesefifilename.setText(""+staticstring);
                    filename=staticstring;
                    filepath=filepathname;
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
        List<String> ll=new ArrayList<String>();
        ll.add(filena);
        new FileUploader().uploadFiles(YelligoApplication.getContext(),ll, "", new FileUploader.FileUploaderCallback() {
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

    public  void showdialog(String message){
        CustomDialogSuccessdialog dialog = new CustomDialogSuccessdialog(SymptomCheckerActivity.this, "",
                new CustomDialogSuccessdialog.OnButtonClickListener() {
                    @Override
                    public void onLeftButtonClick(CustomDialogSuccessdialog dialog) {
                        dialog.dismiss();
                        // Toast.makeText(getActivity(),"Customer created successfully",Toast.LENGTH_SHORT).show();

                            if (!isFinishing()) {
                               onBackPressed();

                        }

                    }

                    @Override
                    public void onRightButtonClick(CustomDialogSuccessdialog dialog, String notes) {

                        dialog.dismiss();
                        if (!isFinishing()) {
                            onBackPressed();
                        }

                    }


                });
       /* dialog.setCancelable(false);
        dialog.setRightButtonText("Cancle");
        dialog.setRightButtonVisibility(View.GONE);
        dialog.setLeftButtonVisibility(View.VISIBLE);
        dialog.setLeftButtonText("OK");
        dialog.setDialogType(CustomDialogGeneric.TYPE_ALERT);
        dialog.setDescription("" + message);*/
        dialog.show();
    }
}
