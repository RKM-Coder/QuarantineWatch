package com.goalsr.homequarantineTracker.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
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
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.goalsr.homequarantineTracker.GlideApp;
import com.goalsr.homequarantineTracker.R;
import com.goalsr.homequarantineTracker.Utils.AppConstants;
import com.goalsr.homequarantineTracker.Utils.PreferenceStore;
import com.goalsr.homequarantineTracker.YelligoApplication;
import com.goalsr.homequarantineTracker.apiservice.NetworkService;
import com.goalsr.homequarantineTracker.base.BaseActivity;
import com.goalsr.homequarantineTracker.dialog.CustomDialogGeneric;
import com.goalsr.homequarantineTracker.gpsenable.GpsUtils;
import com.goalsr.homequarantineTracker.imagecompress.ImageCompressionToBitMapAsyncTask;
import com.goalsr.homequarantineTracker.resposemodel.ReqGvtPatientFamillySymptom;
import com.goalsr.homequarantineTracker.resposemodel.ReqGvtPatientSymptom;
import com.goalsr.homequarantineTracker.resposemodel.ReqImageChunk;
import com.goalsr.homequarantineTracker.resposemodel.ResSymtomChecker;
import com.goalsr.homequarantineTracker.resposemodel.getPatientinfo.ResPatientFamilyInfo;
import com.goalsr.homequarantineTracker.resposemodel.getPatientinfo.ResPatientInfo;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.goalsr.homequarantineTracker.Utils.AppConstants.myPermissions;

public class PatientSymtomUpdateActivity extends BaseActivity {

    private static final String TAG = "symtomchecker";
    @BindView(R.id.tv_header_fac)
    TextView tvHeaderFac;
    @BindView(R.id.iv_back_fac)
    ImageButton ivBackFac;
    @BindView(R.id.tv_logout)
    TextView tvLogout;
    @BindView(R.id.toolbar_fac)
    Toolbar toolbarFac;
    @BindView(R.id.divider)
    View divider;
    @BindView(R.id.txt_main_p_name)
    TextView txtMainPName;
    @BindView(R.id.txt_main_p_mobile)
    TextView txtMainPMobile;
    @BindView(R.id.txtemail)
    TextView txtemail;
    @BindView(R.id.ll_main_patient)
    LinearLayout llMainPatient;
    @BindView(R.id.chk_box_txt1)
    TextView chkBoxTxt1;
    @BindView(R.id.chk_box1)
    CheckBox chkBox1;
    @BindView(R.id.chk_box_txt2)
    TextView chkBoxTxt2;
    @BindView(R.id.chk_box2)
    CheckBox chkBox2;
    @BindView(R.id.chk_box_txt3)
    TextView chkBoxTxt3;
    @BindView(R.id.chk_box3)
    CheckBox chkBox3;
    @BindView(R.id.chk_box_txt4)
    TextView chkBoxTxt4;
    @BindView(R.id.chk_box4)
    CheckBox chkBox4;
    @BindView(R.id.chk_box_txt5)
    TextView chkBoxTxt5;
    @BindView(R.id.chk_box5)
    CheckBox chkBox5;
    @BindView(R.id.chk_box_txt6)
    TextView chkBoxTxt6;
    @BindView(R.id.chk_box6)
    CheckBox chkBox6;
    @BindView(R.id.imageicon7)
    ImageView imageicon7;
    @BindView(R.id.chk_box_txt7)
    TextView chkBoxTxt7;
    @BindView(R.id.chk_box7)
    CheckBox chkBox7;
    @BindView(R.id.imageicon)
    ImageView imageicon;
    @BindView(R.id.chk_box_txt8)
    TextView chkBoxTxt8;
    @BindView(R.id.ll_chk)
    LinearLayout llChk;
    @BindView(R.id.chk_box8)
    CheckBox chkBox8;
    @BindView(R.id.takesefi)
    TextView takesefi;
    @BindView(R.id.img_preview)
    ImageView imgPreview;
    @BindView(R.id.submit_btn)
    Button submitBtn;
    @BindView(R.id.fmlayout)
    FrameLayout fmlayout;
    ResPatientInfo resPatientInfo;
    ResPatientFamilyInfo resPatientFamilyInfo;
    int sidfamily = 0;
    @BindView(R.id.txt_main_p_relation)
    TextView txtMainPRelation;
    private String key = "";
    boolean isfever, iscoughandsour, isbreathing, isdiarria, isdiabaties, ishypertense, isheartdisses, ishiv;


    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    public static final String IMAGE_PATH = "IMAGE_PATH";
    public static final String RETURN_DATA = "return-data";
    private Uri mCapturedImageURI = null;
    public static final int REQUEST_CAMERA_PERMISSION = 102;
    public static final int REQUEST_CAMERA_IMAGE_SELFIE = 192;
    public static final int REQUEST_READ_EXTERNAL_STORAGE_PERMISSION = 101;
    public static final int REQUEST_WRITE_EXTERNAL_STORAGE_PERMISSION = 110;

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
    private String filename, filepath;
    private NetworkService networkService;
    List<String> famillyrelation = new ArrayList<>();
    private String[] number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_symtom_update);
        ButterKnife.bind(this);
        initMvp();
        checkPermissionFortarck();
        String genderarray[] = getResources().getStringArray(R.array.relation);

        famillyrelation = new ArrayList<>(Arrays.asList(genderarray));
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getString("key").equalsIgnoreCase("self")) {
                key = bundle.getString("key");
                getPatientSelf();
            } else if (bundle.getString("key").equalsIgnoreCase("family")) {
                key = bundle.getString("key");
                sidfamily = bundle.getInt("v_id");
                getPatientFamilyByid();
            }
        }
        resPatientInfo = new ResPatientInfo();
        int ciD=PreferenceStore.getPrefernceHelperInstace().getIntValue(YelligoApplication.getContext(),PreferenceStore.CITIZEN_ID);
        resPatientInfo = getPatientinfoRepository().getPatientInfo(ciD);


    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mFusedLocationClient!=null){
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }

    private void initMvp() {
        networkService = new NetworkService();
        networkService.inject(PatientSymtomUpdateActivity.this);
    }

    private void getPatientSelf() {
        resPatientInfo = new ResPatientInfo();
        int ciD=PreferenceStore.getPrefernceHelperInstace().getIntValue(YelligoApplication.getContext(),PreferenceStore.CITIZEN_ID);
        resPatientInfo = getPatientinfoRepository().getPatientInfo(ciD);
        if (resPatientInfo != null) {
            if (resPatientInfo.getName() != null) {
                txtMainPName.setText("Name: " + resPatientInfo.getName());
            }
            txtMainPRelation.setText("Relation: Self");
            if (resPatientInfo.getMobile() != null) {
                txtMainPMobile.setText("Mobile: " + resPatientInfo.getMobile());
            }
            if (resPatientInfo.getDateQurantine() != null) {
                txtemail.setText("End Date of Quarantine: " + AppConstants.dateFormatChangerGVT(resPatientInfo.getDateQurantine()));
            }
        }
    }

    private void getPatientFamilyByid() {
        resPatientFamilyInfo = new ResPatientFamilyInfo();
        resPatientFamilyInfo = getPatientFamillyinfoRepository().getPatientFamilyInfo(sidfamily);
        if (resPatientFamilyInfo != null) {
            if (resPatientFamilyInfo.getName() != null) {
                txtMainPName.setText("Name: " + resPatientFamilyInfo.getName());
            }
            if (resPatientFamilyInfo.getRelationId()!=0) {
                if (famillyrelation.get(resPatientFamilyInfo.getRelationId()) != null) {
                    txtMainPRelation.setText("Relation: "+famillyrelation.get(resPatientFamilyInfo.getRelationId()));
                }
            }
            if (resPatientFamilyInfo.getMobile() != null) {
                txtMainPMobile.setText("Mobile: " + resPatientFamilyInfo.getMobile());
            }
            if (resPatientFamilyInfo.getDateQurantine() != null) {
                txtemail.setText("End Date of Quarantine: " + AppConstants.dateFormatChangerGVT(resPatientFamilyInfo.getDateQurantine()));
            }
        }
    }

    @OnClick({R.id.ll_call,R.id.ll_lelp,R.id.tv_logout, R.id.takesefi, R.id.ll_main_patient, R.id.chk_box1, R.id.chk_box2, R.id.chk_box3, R.id.chk_box4, R.id.chk_box5, R.id.chk_box6, R.id.chk_box7, R.id.chk_box8, R.id.submit_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_logout:
                if (validation()) {
                    if (key.equalsIgnoreCase("self")) {
                        if (getCommonApi().isInternetAvailable(PatientSymtomUpdateActivity.this)){
                            symtomUpdateSelf();
                        }else {
                            Toast.makeText(YelligoApplication.getContext(),"Please enable internet connection",Toast.LENGTH_LONG).show();
                        }

                    } else if (key.equalsIgnoreCase("family")) {
                        if (getCommonApi().isInternetAvailable(PatientSymtomUpdateActivity.this)){
                            symtomUpdateFamily();
                        }else {
                            Toast.makeText(YelligoApplication.getContext(),"Please enable internet connection",Toast.LENGTH_LONG).show();
                        }

                    }
                } /*else {
                    showDialog("Please take photo", false);
                }*/


                break;
            case R.id.ll_main_patient:
                if (key.equalsIgnoreCase("self")){
                    Bundle bundle=new Bundle();
                    bundle.putString("key","self");
                    bundle.putInt("v_id",0);
                    getCommonApi().openNewScreen(PatientDetailsActivity.class,bundle);
                }else if (key.equalsIgnoreCase("Family")){
                    Bundle bundle=new Bundle();
                    bundle.putString("key","family");
                    bundle.putInt("v_id",resPatientFamilyInfo.getCitizenFamilyPersonId());
                    getCommonApi().openNewScreen(PatientFamillyActivity.class,bundle);
                }
                break;
            case R.id.chk_box1:
                isfever = chkBox1.isChecked();
                break;
            case R.id.chk_box2:
                iscoughandsour = chkBox2.isChecked();
                break;
            case R.id.chk_box3:
                isbreathing = chkBox3.isChecked();
                break;
            case R.id.chk_box4:
                isdiarria = chkBox4.isChecked();
                break;
            case R.id.chk_box5:
                isdiabaties = chkBox5.isChecked();
                break;
            case R.id.chk_box6:
                ishypertense = chkBox6.isChecked();
                break;
            case R.id.chk_box7:
                isheartdisses = chkBox7.isChecked();
                break;
            case R.id.chk_box8:
                ishiv = chkBox8.isChecked();
                break;
            case R.id.submit_btn:
                // upload();
                break;
            case R.id.takesefi:
                captureImpl();
                break;

            case R.id.ll_call:
                shodilogforcall();
                break;
            case R.id.ll_lelp:
                helpmethod();
                break;
        }
    }

    private boolean validation() {
        if (mLocation == null) {
            showDialogLocTry("Unable to fetch location,please on off GPS and try again  ", false);
            return false;
        } else {
            if (mLocation.getLongitude() == 0.0) {
                showDialogLocTry("Unable to fetch location,please on off GPS and try again  ", false);
                return false;
            }

        }

        if (value.size()==0){
            showDialog("Please take photo", false);
            return false;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        GpsEnableChecking();
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

                            String IMAGE_NAME = PreferenceStore.getPrefernceHelperInstace().getIntValue(YelligoApplication.getContext(), PreferenceStore.CITIZEN_ID) + "_" + System.currentTimeMillis() + "_" + "selfi" + ".png";
                            if (isImageSupported(path)) {
                                filename = IMAGE_NAME;
                                filepath = getFilename(IMAGE_NAME);
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
            Log.e("eee",e.getMessage());
        }
    }

    public static String convert(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, outputStream);

        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }

    private void compressImage(String imagePath, String staticstring, String filepathname) {

        ImageCompressionToBitMapAsyncTask imageCompression = new ImageCompressionToBitMapAsyncTask("") {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                showProgressDialogStatic();
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                String base64File = convert(bitmap);

                hideProgressDialogStatic();
                value = new ArrayList<>();

                splitBase64(base64File);
                Log.e("tttttttt", value.size() + "---" + base64File.length());

                if (bitmap != null) {

                    GlideApp.with(YelligoApplication.getContext())
                            .load(bitmap)
                            .into(imgPreview);
                    fmlayout.setVisibility(View.VISIBLE);
                   /* takesefifilename.setVisibility(View.VISIBLE);
                    takesefifilename.setText(""+staticstring);
                    filename=staticstring;
                    filepath=filepathname;
                    uploadtoServer(file);*/
                }
                // image here is compressed & ready to be sent to the server
            }
        };
        imageCompression.execute(imagePath);

    }

    ArrayList<String> value = new ArrayList<>();
    /*public ArrayList<String> getSplitImageList(String str){

        Log.e("Filepath", str.length()+"");

        if (str.length()>20000){
            int indexcount=str.length()/20000;
            *//*String result=str.substring(0,20000);
            if (str.length()>0){

            }*//*
           // value= (ArrayList<String>) Arrays.asList(str.split(str,indexcount));

        }
        return value;
    }*/

    private void splitBase64(String str) {
        //Log.e("tt",str);
        if (str.length() > 20000) {

            String result = str.substring(0, 20000);
            Log.e("Length---", result.length() + "");
            value.add(result);

            splitBase64(str.substring(20000));
        } else {
            value.add(str);
        }
        //return result;
    }


    public static String getFilename(String filename) {
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/ImageCompApp/HQUAR");

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            mediaStorageDir.mkdirs();
        }

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

    private void checkPermissionFortarck() {
        if (!checkPermissionAvl()) {

           requestPermissions();
        } else {

            getCurrentLOc();
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
                                PreferenceStore.getPrefernceHelperInstace().setString(YelligoApplication.getContext(), PreferenceStore.USER_LATITUDE, mLocation.getLatitude() + "");
                                PreferenceStore.getPrefernceHelperInstace().setString(YelligoApplication.getContext(), PreferenceStore.USER_LONGITUDE, mLocation.getLongitude() + "");
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

    private void requestPermissions() {
        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_FINE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            // Log.i(TAG, "Displaying permission rationale to provide additional context.");
            Snackbar.make(
                    findViewById(R.id.mainlayout),
                    R.string.permission_rationale_enable,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            ActivityCompat.requestPermissions(PatientSymtomUpdateActivity.this,
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
            ActivityCompat.requestPermissions(PatientSymtomUpdateActivity.this,
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
    private void helpmethod() {
        String url = "https://landrecords.karnataka.gov.in/covid/";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    private void shodilogforcall() {
        if (ContextCompat.checkSelfPermission(PatientSymtomUpdateActivity.this,
                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            number = getResources().getStringArray(R.array.covidnumber);
            AlertDialog.Builder builder = new AlertDialog.Builder(PatientSymtomUpdateActivity.this);
            builder.setTitle(R.string.pick_number)
                    .setItems(R.array.covidnumber, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // The 'which' argument contains the index position
                            dialog.dismiss();
                            if (ContextCompat.checkSelfPermission(PatientSymtomUpdateActivity.this,
                                    Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                                String numbercall = number[which];
                                Intent intent = new Intent(Intent.ACTION_CALL);
                                intent.setData(Uri.parse("tel:" + numbercall));
                                startActivity(intent);
                            } else {
                                ActivityCompat.requestPermissions(PatientSymtomUpdateActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 5101);

                            }
                            // of the selected item
                        }
                    });
            Dialog d = builder.create();
            d.show();
        } else {
            ActivityCompat.requestPermissions(PatientSymtomUpdateActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 5101);
        }
    }



    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 34: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    getCurrentLOc();

                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case 5101: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    number = getResources().getStringArray(R.array.covidnumber);
                    AlertDialog.Builder builder = new AlertDialog.Builder(PatientSymtomUpdateActivity.this);
                    builder.setTitle(R.string.pick_number)
                            .setItems(R.array.covidnumber, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // The 'which' argument contains the index position
                                    if (ContextCompat.checkSelfPermission(PatientSymtomUpdateActivity.this,
                                            Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                                        String numbercall = number[which];
                                        Intent intent = new Intent(Intent.ACTION_CALL);
                                        intent.setData(Uri.parse("tel:" + numbercall));
                                        startActivity(intent);
                                    }
                                    // of the selected item
                                }
                            });
                    Dialog d = builder.create();
                    d.show();

                } else {
                    Snackbar.make(
                            findViewById(R.id.mainlayout),
                            R.string.permission_rationale,
                            Snackbar.LENGTH_INDEFINITE)
                            .setAction(R.string.ok, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    // Request permission
                                    ActivityCompat.requestPermissions(PatientSymtomUpdateActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 5101);
                                }
                            })
                            .show();
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


    ReqImageChunk reqImageChunk;

    private void upload() {

        showProgressDialogStatic();
        reqImageChunk = new ReqImageChunk();
        String uniqueId = UUID.randomUUID().toString();

        reqImageChunk.setDOC_CAPTYPE("Image");
        reqImageChunk.setDOC_TYPE("PATIENT_SELFIE_IMAGE");
        reqImageChunk.setDOC_CBY(PreferenceStore.getPrefernceHelperInstace().getIntValue(YelligoApplication.getContext(),PreferenceStore.USER_ID_login));
        reqImageChunk.setDOC_CHNK_CNT(value.size());
        reqImageChunk.setDOC_CITZ_ID(PreferenceStore.getPrefernceHelperInstace().getIntValue(YelligoApplication.getContext(),PreferenceStore.CITIZEN_ID));
        // reqImageChunk.setDOC_CNK("");
        if (key.equalsIgnoreCase("self")) {
            reqImageChunk.setDOC_FAMLY_PER_ID(0);
            reqImageChunk.setDOC_MOBILE("" + resPatientInfo.getMobile());
            reqImageChunk.setDOC_ROLE_ID(resPatientInfo.getURoleBy());
        } else {
            reqImageChunk.setDOC_FAMLY_PER_ID(resPatientFamilyInfo.getCitizenFamilyPersonId());
            reqImageChunk.setDOC_MOBILE("" + resPatientFamilyInfo.getMobile());
            reqImageChunk.setDOC_ROLE_ID(resPatientFamilyInfo.getURoleBy());
        }
        String imagename = PreferenceStore.getPrefernceHelperInstace().getIntValue(YelligoApplication.getContext(),PreferenceStore.CITIZEN_ID) + "_" + uniqueId + ".png";
        reqImageChunk.setDOC_IMAGE_NAME("" + imagename);

        reqImageChunk.setDOC_LAT(mLocation.getLatitude());
        reqImageChunk.setDOC_LONG(mLocation.getLongitude());


        reqImageChunk.setDOC_TIMESTAMP("" + AppConstants.getCurrentDateTimeGVT());
        reqImageChunk.setItemSentStatus(true);
        reqImageChunk.setMultiMediaChunk_unique_id(uniqueId);


        requestImage();


        // showProgressDialogStatic();

    }

    int count = 0;

    private void requestImage() {
        if (count < value.size()) {

            reqImageChunk.setDOC_INDEX(count);//
            reqImageChunk.setDOC_CNK("" + value.get(count));
            networkService.sendImageFile(reqImageChunk, new NetworkService.NetworkServiceListener() {
                @Override
                public void onFailure(Object response) {
                    hideProgressDialogStatic();
                    count = 0;
                    showDialog("Please try Again", false);

                }

                @Override
                public void onAuthFail(Object error) {

                }

                @Override
                public void onSuccess(Object response, Boolean cancelFlag) {
                    if (response instanceof String) {

                        count++;
                        requestImage();
                    }

                }
            });
        } else {
            showDialog("Photo & symptoms uploaded successfully", true);
            hideProgressDialogStatic();

        }
    }

    private void showDialog(String message, boolean b) {
        if (!isFinishing()) {
            CustomDialogGeneric dialog = new CustomDialogGeneric(PatientSymtomUpdateActivity.this, "",
                    new CustomDialogGeneric.OnButtonClickListener() {
                        @Override
                        public void onLeftButtonClick(CustomDialogGeneric dialog) {
                            dialog.dismiss();
                            if (b) {
                                PreferenceStore.getPrefernceHelperInstace().setFlag(YelligoApplication.getContext(), PreferenceStore.ISUPDATEPATENTINFO, true);
                                Intent intent = new Intent(getApplicationContext(), HomeMainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finishAffinity();
                            }
                            //dialog.dismiss();
                        }

                        @Override
                        public void onRightButtonClick(CustomDialogGeneric dialog, String notes) {
                            dialog.dismiss();

                        }


                    });
            dialog.setCancelable(false);
            dialog.setRightButtonText("Retry");
            dialog.setRightButtonVisibility(View.GONE);
            dialog.setLeftButtonVisibility(View.VISIBLE);
            dialog.setLeftButtonText("OK");
            dialog.setDialogType(CustomDialogGeneric.TYPE_ALERT);
            dialog.setDescription("" + message);
            dialog.show();
        }
    }

    private void showDialogLocTry(String message, boolean b) {
        if (!isFinishing()) {
            CustomDialogGeneric dialog = new CustomDialogGeneric(PatientSymtomUpdateActivity.this, "",
                    new CustomDialogGeneric.OnButtonClickListener() {
                        @Override
                        public void onLeftButtonClick(CustomDialogGeneric dialog) {
                            dialog.dismiss();

                            //dialog.dismiss();
                        }

                        @Override
                        public void onRightButtonClick(CustomDialogGeneric dialog, String notes) {
                            dialog.dismiss();
                            if (b) {
                                //TODO check Location
                                if (!checkPermissionAvl()) {
                                    showRationaleDialog();
                                }else {
                                    new GpsUtils(PatientSymtomUpdateActivity.this).turnGPSOn(new GpsUtils.onGpsListener() {
                                        @Override
                                        public void gpsStatus(boolean isGPSEnable) {
                                            // turn on GPS
                                            AppConstants.isGPS = isGPSEnable;
                                            getCurrentLOc();
                                        }
                                    });
                                }

                            }

                        }


                    });
            dialog.setCancelable(false);
            dialog.setRightButtonText("Retry");
            dialog.setRightButtonVisibility(View.VISIBLE);
            dialog.setLeftButtonVisibility(View.VISIBLE);
            dialog.setLeftButtonText("Cancel");
            dialog.setDialogType(CustomDialogGeneric.TYPE_ALERT);
            dialog.setDescription("" + message);
            dialog.show();
        }
    }

    public void showRationaleDialog() {
        Snackbar.make(findViewById(R.id.mainlayout),
                "Please Grant Permissions To Use The App",
                Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                }).show();
    }


    private void captureImpl() {

        launchCameraActionselfie();
    }

    private void symtomUpdateSelf() {

        showProgressDialogStatic();
        int ciD=PreferenceStore.getPrefernceHelperInstace().getIntValue(YelligoApplication.getContext(),PreferenceStore.CITIZEN_ID);
        ReqGvtPatientSymptom reqGvtPatientSymptom = new ReqGvtPatientSymptom();
        reqGvtPatientSymptom.setAdditionalInfo("ANDROID");
        reqGvtPatientSymptom.setCitizenID(getPatientinfoRepository().getPatientInfo(ciD).getCitizenID());
        reqGvtPatientSymptom.setBreathingProblem(isbreathing);
        reqGvtPatientSymptom.setCoughSourThroat(iscoughandsour);
        reqGvtPatientSymptom.setDiarrhea(isdiarria);
        reqGvtPatientSymptom.setFever(isfever);
        reqGvtPatientSymptom.setDiabetes(isdiabaties);
        reqGvtPatientSymptom.setHeartIssue(isheartdisses);
        reqGvtPatientSymptom.setHypertension(ishypertense);
        reqGvtPatientSymptom.setHIV(ishiv);
        reqGvtPatientSymptom.setRoleId(PreferenceStore.getPrefernceHelperInstace().getIntValue(YelligoApplication.getContext(),PreferenceStore.ROLL_ID));
        reqGvtPatientSymptom.setUBy(PreferenceStore.getPrefernceHelperInstace().getIntValue(YelligoApplication.getContext(),PreferenceStore.USER_ID_login));
        reqGvtPatientSymptom.setpSecurity(getCommonApi().getSecurityObject());

        networkService.sendpatientsymtomInfo(reqGvtPatientSymptom, new NetworkService.NetworkServiceListener() {
            @Override
            public void onFailure(Object response) {
                hideProgressDialogStatic();
                if (response instanceof String) {
                    Toast.makeText(YelligoApplication.getContext(), "" + response, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onAuthFail(Object error) {

            }

            @Override
            public void onSuccess(Object response, Boolean cancelFlag) {
                hideProgressDialogStatic();
                if (response instanceof ResSymtomChecker) {
                    upload();
                   // Toast.makeText(YelligoApplication.getContext(), "Successfully Updated", Toast.LENGTH_LONG).show();

                }
            }
        });


    }

    private void symtomUpdateFamily() {
        showProgressDialogStatic();
        int ciD=PreferenceStore.getPrefernceHelperInstace().getIntValue(YelligoApplication.getContext(),PreferenceStore.CITIZEN_ID);
        ReqGvtPatientFamillySymptom reqGvtPatientSymptom = new ReqGvtPatientFamillySymptom();
        reqGvtPatientSymptom.setAdditionalInfo("ANDROID");
        reqGvtPatientSymptom.setFamilyPersonId(resPatientFamilyInfo.getCitizenFamilyPersonId());
        reqGvtPatientSymptom.setCitizenID(getPatientinfoRepository().getPatientInfo(ciD).getCitizenID());
        reqGvtPatientSymptom.setBreathingProblem(isbreathing);
        reqGvtPatientSymptom.setCoughSourThroat(iscoughandsour);
        reqGvtPatientSymptom.setDiarrhea(isdiarria);
        reqGvtPatientSymptom.setFever(isfever);
        reqGvtPatientSymptom.setDiabetes(isdiabaties);
        reqGvtPatientSymptom.setHeartIssue(isheartdisses);
        reqGvtPatientSymptom.setHypertension(ishypertense);
        reqGvtPatientSymptom.setHIV(ishiv);
        reqGvtPatientSymptom.setRoleId(PreferenceStore.getPrefernceHelperInstace().getIntValue(YelligoApplication.getContext(),PreferenceStore.ROLL_ID));
        reqGvtPatientSymptom.setUBy(PreferenceStore.getPrefernceHelperInstace().getIntValue(YelligoApplication.getContext(),PreferenceStore.USER_ID_login));
        reqGvtPatientSymptom.setpSecurity(getCommonApi().getSecurityObject());

        networkService.sendpatientFamillysymtomInfo(reqGvtPatientSymptom, new NetworkService.NetworkServiceListener() {
            @Override
            public void onFailure(Object response) {
                hideProgressDialogStatic();
                if (response instanceof String) {
                    Toast.makeText(YelligoApplication.getContext(), "" + response, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onAuthFail(Object error) {

            }

            @Override
            public void onSuccess(Object response, Boolean cancelFlag) {
                hideProgressDialogStatic();
                if (response instanceof ResSymtomChecker) {
                    upload();
                   // Toast.makeText(YelligoApplication.getContext(), "Successfully Updated", Toast.LENGTH_LONG).show();

                }
            }
        });


    }

    @Override
    public void onInternetConnectivityChanged(boolean isConnected) {

    }
}
