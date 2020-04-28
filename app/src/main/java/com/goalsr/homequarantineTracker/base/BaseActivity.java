package com.goalsr.homequarantineTracker.base;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.airbnb.lottie.LottieAnimationView;
import com.goalsr.homequarantineTracker.R;
import com.goalsr.homequarantineTracker.Utils.CommonApi;
import com.goalsr.homequarantineTracker.Utils.DeviceUtill;
import com.goalsr.homequarantineTracker.Utils.DialogManager;
import com.goalsr.homequarantineTracker.Utils.PreferenceStore;
import com.goalsr.homequarantineTracker.YelligoApplication;
import com.goalsr.homequarantineTracker.apiservice.ApiBackGround;
import com.goalsr.homequarantineTracker.db.repository.PatientFamilyinfoRepository;
import com.goalsr.homequarantineTracker.db.repository.PatientinfoRepository;
import com.goalsr.homequarantineTracker.db.repository.TravelTrackingRepository;
import com.goalsr.homequarantineTracker.db.viewmodel.PatientFamilyViewmodel;
import com.goalsr.homequarantineTracker.db.viewmodel.PatientViewmodel;
import com.goalsr.homequarantineTracker.networkconnectivity.NetworkIdentityListener;


public abstract class BaseActivity extends AppCompatActivity implements NetworkIdentityListener {

    private CommonApi commonApi;
    /*CategoryRepository categoryRepository;
    LanguageRepository languageRepository;
    ProguctTagRepository proguctTagRepository;*/
    TravelTrackingRepository travelTrackingRepository;
    PatientinfoRepository patientinfoRepository;
    PatientFamilyinfoRepository patientFamilyinfoRepository;
    Context mContext;

    private Dialog dialogProgress;
    private LottieAnimationView lottieAnimationView;

    private PatientViewmodel patientViewmodel;
    private PatientFamilyViewmodel patientfamilyViewmodel;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
        commonApi = CommonApi.getInstance(this);
      /*  categoryRepository=new CategoryRepository(mContext);
        languageRepository=new LanguageRepository(mContext);
        proguctTagRepository=new ProguctTagRepository(mContext);*/
        travelTrackingRepository=new TravelTrackingRepository(mContext);
        patientinfoRepository=new PatientinfoRepository(mContext);
        patientFamilyinfoRepository=new PatientFamilyinfoRepository(mContext);
        dialogProgress = DialogManager.getProgressDialog(mContext);
        lottieAnimationView = dialogProgress.findViewById(R.id.lottie_cdp);
        patientViewmodel=ViewModelProviders.of(this).get(PatientViewmodel.class);
        patientfamilyViewmodel=ViewModelProviders.of(this).get(PatientFamilyViewmodel.class);
    }

    public PatientViewmodel  getPatientViewmodel(){
        return patientViewmodel;
    }

    public PatientFamilyViewmodel  getPatienFamillytViewmodel(){
        return patientfamilyViewmodel;
    }

    public Dialog getDialogProgress() {
        return dialogProgress;
    }

    public LottieAnimationView getLottieAnimationView() {
        return lottieAnimationView;
    }

    public TravelTrackingRepository getTravelTrackingRepository() {
        return travelTrackingRepository;
    }

    public PatientinfoRepository getPatientinfoRepository() {
        return patientinfoRepository;
    }

    public PatientFamilyinfoRepository getPatientFamillyinfoRepository() {
        return patientFamilyinfoRepository;
    }

    private boolean isDateAndTimeAutomatic() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return Settings.Global.getInt(YelligoApplication.getContext().getContentResolver(), Settings.Global.AUTO_TIME, 0) == 1;
        } else {
            return android.provider.Settings.System.getInt(YelligoApplication.getContext().getContentResolver(), android.provider.Settings.System.AUTO_TIME, 0) == 1;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!isDateAndTimeAutomatic()) {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            alertBuilder.setCancelable(false);
            alertBuilder.setTitle("Automatic Date and Time Permission");
            alertBuilder.setMessage("Please enable automatic date and time permission");
            alertBuilder.setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    startActivityForResult(new Intent(Settings.ACTION_DATE_SETTINGS), 999);
                    finish();
                }
            });

            AlertDialog alert = alertBuilder.create();
            alert.show();
        }else if (new DeviceUtill().isDeviceRooted(YelligoApplication.getContext())){
            showAlertDialogAndExitApp("This device is rooted. You can't use this app.");
        }
       /* ApiBackGround apiBackGround=new ApiBackGround(YelligoApplication.getContext());
        apiBackGround.uploadsync(false);*/

    }

    public void showAlertDialogAndExitApp(String message) {

        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                });

        alertDialog.show();
    }


   /*public CategoryRepository getCategoryRepository() {
        return categoryRepository;
    }

    public LanguageRepository getLanguageRepository() {
        return languageRepository;
    }

    public ProguctTagRepository getProguctTagRepository() {
        return proguctTagRepository;
    }*/

    public void showProgressDialogStatic() {
        if (dialogProgress != null) {
            if (!dialogProgress.isShowing()) {
                dialogProgress.show();
                lottieAnimationView.playAnimation();
            }
        }
    }

    public void hideProgressDialogStatic() {
        if (dialogProgress != null) {
            if (dialogProgress.isShowing()) {
                lottieAnimationView.cancelAnimation();
                dialogProgress.dismiss();
            }
        }
    }

    /*@Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
    }*/

    public CommonApi getCommonApi() {
        return commonApi;
    }
}
