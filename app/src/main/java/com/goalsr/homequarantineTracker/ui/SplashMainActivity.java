package com.goalsr.homequarantineTracker.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

//import com.amitshekhar.DebugDB;
import com.goalsr.homequarantineTracker.R;
import com.goalsr.homequarantineTracker.Utils.PreferenceStore;
import com.goalsr.homequarantineTracker.YelligoApplication;
import com.goalsr.homequarantineTracker.apiservice.NetworkService;
import com.goalsr.homequarantineTracker.base.BaseActivity;
import com.goalsr.homequarantineTracker.resposemodel.getPatientinfo.ReqPatient;
import com.goalsr.homequarantineTracker.resposemodel.getPatientinfo.ResPatientInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import butterknife.ButterKnife;

import static com.goalsr.homequarantineTracker.Utils.AppConstants.myPermissions;

public class SplashMainActivity extends BaseActivity {


    private static int splashTimeOut = 2000;
    private String token = "";
    private NetworkService networkService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity_main);
        ButterKnife.bind(this);
        initMvp();
        //imgLogo = (ImageView) findViewById(R.id.imagelogo);


        /*Animation myanim = AnimationUtils.loadAnimation(this, R.anim.splash_icon_anim);
        imagelogo.startAnimation(myanim);*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            mayRequestContacts();
            checkPermission();
        } else {
            continueLoading();
        }
//        Log.e("DBaddress", DebugDB.getAddressLog());

    }
    private void initMvp() {

        networkService = new NetworkService();
        networkService.inject(SplashMainActivity.this);


    }

    private void continueLoading() {

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w("", "getInstanceId failed", task.getException());
                            return;
                        }
                        // Get new Instance ID token

                        token = task.getResult().getToken();

                        Log.e("TOKEN", token);
                        // Log and toast
                        PreferenceStore.getPrefernceHelperInstace().setString(getApplicationContext(), PreferenceStore.FIREBASE_TOKEN, token);
                        //PreferenceStore.getPrefernceHelperInstace().setString(getApplicationContext(),PreferenceStore.FIREBASE_APPID,token);
                        // String msg = getString(R.string.msg_token_fmt, token);
                        // Log.d("Token mes", msg);
                        //Log.d("Token", token);

                        FirebaseMessaging.getInstance().setAutoInitEnabled(true);

                        /*RequestToken requestToken = new RequestToken();
                        requestToken.setDeviceToken(token);

                        showProgressDialogStatic();
                        if (networkService.haveNetworkAccess()) {

                            networkService.firebaseToken(requestToken, xuuth, LoginActivity.this);
                        }else {
                            hideProgressDialogStatic();
                            alert.show("","Please Connect Internet.");
                        }*/

                    }
                });
        /*if (PreferenceStore.getPrefernceHelperInstace().getString(YelligoApplication.getContext(), PreferenceStore.USER_DATE).equalsIgnoreCase("")){
            PreferenceStore.getPrefernceHelperInstace().setString(YelligoApplication.getContext(),PreferenceStore.USER_DATE,AppConstants.getCurrentDate());
        }else {
            if (!PreferenceStore.getPrefernceHelperInstace().getString(YelligoApplication.getContext(), PreferenceStore.USER_DATE).equalsIgnoreCase(AppConstants.getCurrentDate())) {
                PreferenceStore.getPrefernceHelperInstace().setString(YelligoApplication.getContext(),PreferenceStore.USER_DATE,AppConstants.getCurrentDate());
            }
        }*/

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                /*getCommonApi().openNewScreen(DasBoardWorkmanger.class);
                finish();*/
                /*if (PreferenceStore.getPrefernceHelperInstace().isFirstTime(SplashMainActivity.this, AppConstants.FIRST_TIME)) {
                    PreferenceStore.getPrefernceHelperInstace().setFirstTime(SplashMainActivity.this, AppConstants.FIRST_TIME, false);
                    Intent i = new Intent(SplashActivity.this, WelcomeActivity.class);
                    startActivity(i);
                    finish();

                } else*/
                if (!PreferenceStore.getPrefernceHelperInstace().getFlag(SplashMainActivity.this, PreferenceStore.LOGIN)) {
                    Intent i = new Intent(SplashMainActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                } else {
                    if (PreferenceStore.getPrefernceHelperInstace().getIntValue(YelligoApplication.getContext(),PreferenceStore.ROLL_ID)==2){
                        if (PreferenceStore.getPrefernceHelperInstace().getIntValue(SplashMainActivity.this, PreferenceStore.DISTRICT_ID)==0) {
                            Intent intent = new Intent(getApplicationContext(), DistrictListActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finishAffinity();
                        } else {

                            Intent intent = new Intent(getApplicationContext(), AdminPatientLsitActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finishAffinity();

                        }
                    }else {
                        if (PreferenceStore.getPrefernceHelperInstace().getFlag(SplashMainActivity.this, PreferenceStore.ISUPDATEPATENTINFO)) {
                            Intent i = new Intent(SplashMainActivity.this, HomeMainActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            getPatientInfo();
                        }
                    }
                }


            }
        }, splashTimeOut);
    }
    //Check M Permission in ANDROID

    private void getPatientInfo() {
        showProgressDialogStatic();
        ReqPatient reqPatient = new ReqPatient();
        int cId = PreferenceStore.getPrefernceHelperInstace().getIntValue(YelligoApplication.getContext(), PreferenceStore.CITIZEN_ID);
        reqPatient.setCitizenId(cId);
        reqPatient.setLevel(2);
        reqPatient.setpSecurity(getCommonApi().getSecurityObject());
        networkService.getPatientInfo(reqPatient, new NetworkService.NetworkServiceListener() {
            @Override
            public void onFailure(Object response) {
                hideProgressDialogStatic();
                if (response instanceof String) {
                    Toast.makeText(YelligoApplication.getContext(), "" + response, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onAuthFail(Object error) {
                hideProgressDialogStatic();
            }

            @Override
            public void onSuccess(Object response, Boolean cancelFlag) {
                hideProgressDialogStatic();

                if (response instanceof ResPatientInfo) {
                    PreferenceStore.getPrefernceHelperInstace().setString(YelligoApplication.getContext(), PreferenceStore.USER_PHONE, ((ResPatientInfo) response).getMobile());
                    getPatientinfoRepository().insert((ResPatientInfo) response);
                    // updateUI();
                    if (((ResPatientInfo) response).getURoleBy() != 0) {
                        PreferenceStore.getPrefernceHelperInstace().setFlag(YelligoApplication.getContext(), PreferenceStore.ISUPDATEPATENTINFO, true);
                        Intent intent = new Intent(getApplicationContext(), HomeMainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finishAffinity();
                    } else {
                        PreferenceStore.getPrefernceHelperInstace().setFlag(YelligoApplication.getContext(), PreferenceStore.ISUPDATEPATENTINFO, false);
                        Intent intent = new Intent(getApplicationContext(), PatientDetailsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("key", "");
                        bundle.putInt("v_id", 0);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finishAffinity();


                    }


                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && resultCode == -1) {
            checkPermission();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 199) {
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
                                checkPermission();
                            }
                        })
                        .create()
                        .show();

            if (flag == 0) {
                continueLoading();
            }
        }
    }


    public void checkPermission() {
        int myPermFlag = 0;
        for (String myPermission : myPermissions) {
            if ((ActivityCompat.checkSelfPermission(this, myPermission) != PackageManager.PERMISSION_GRANTED)) {
                myPermFlag = 1;
                break;
            }
        }
        switch (myPermFlag) {
            case 0:
                continueLoading();
                break;
            case 1:
                getCommonApi().checkPermissions(myPermissions);
                break;
        }
    }

    @Override
    public void onInternetConnectivityChanged(boolean isConnected) {

    }
}
