package com.goalsr.homequarantineTracker.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.chaos.view.PinView;
import com.goalsr.homequarantineTracker.R;
import com.goalsr.homequarantineTracker.Utils.PreferenceStore;
import com.goalsr.homequarantineTracker.YelligoApplication;
import com.goalsr.homequarantineTracker.apiservice.NetworkService;
import com.goalsr.homequarantineTracker.base.BaseActivity;
import com.goalsr.homequarantineTracker.resposemodel.getPatientinfo.ReqPatient;
import com.goalsr.homequarantineTracker.resposemodel.getPatientinfo.ResPatientInfo;
import com.goalsr.homequarantineTracker.resposemodel.gotOtpreq.ResGvtValidOtp;
import com.goalsr.homequarantineTracker.resposemodel.otpvalidGovt.ReqOtpValidGvt;
import com.goalsr.homequarantineTracker.resposemodel.otpvalidGovt.ResGvtValidOtpValid;
import com.google.android.material.snackbar.Snackbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OtpCheckerActivity extends BaseActivity {


    @BindView(R.id.pin_view)
    PinView pinView;
    @BindView(R.id.tv_resendOTP)
    TextView tvResendOTP;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.txt_info_login)
    TextView txtInfoLogin;
    @BindView(R.id.tv_resendOTPtimer)
    TextView tvResendOTPtimer;
    @BindView(R.id.txt_104)
    TextView txt104;
    @BindView(R.id.txt_08046848600)
    TextView txt08046848600;
    @BindView(R.id.txt_9745697456)
    TextView txt9745697456;
    @BindView(R.id.txt_08066692000)
    TextView txt08066692000;
    @BindView(R.id.ll_maincall)
    LinearLayout llMaincall;
    @BindView(R.id.helptechnical)
    CardView helptechnical;
    @BindView(R.id.txt_help_Url)
    LinearLayout txtHelpUrl;
    @BindView(R.id.helpcovid)
    CardView helpcovid;

    private String mobnum;
    private int rollid;
    private NetworkService networkService;

    private CountDownTimer countDownTimer;
    private String[] number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_checker);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        mobnum = bundle.getString("MobNumber", "");
        rollid = bundle.getInt("rollid", 0);
        //tvPhoneNum.setText("" + mobnum);
        initMvp();
        String txtHeading = "One Time Passcode (OTP) has been sent to your number " + mobnum + ". Please enter the otp to continue.";

        txtInfoLogin.setText("" + txtHeading);

        staerCountTimer();
    }

    public int counter = 30;

    private void staerCountTimer() {
        tvResendOTPtimer.setVisibility(View.VISIBLE);
        tvResendOTP.setVisibility(View.GONE);
        counter = 30;
        countDownTimer = new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                counter--;
                tvResendOTPtimer.setText("" + counter);
            }

            public void onFinish() {
                counter = 30;
                tvResendOTPtimer.setVisibility(View.GONE);
                tvResendOTP.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }

    private void initMvp() {

        networkService = new NetworkService();
        networkService.inject(OtpCheckerActivity.this);


    }



    private boolean Validation() {
        String otp = pinView.getText().toString();
        if (TextUtils.isEmpty(otp)) {
            Toast.makeText(OtpCheckerActivity.this, "Please Enter OTP Number", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void makeotpValid(ReqOtpValidGvt reqOtpValid) {
        showProgressDialogStatic();
        networkService.makeotpvalid(reqOtpValid, new NetworkService.NetworkServiceListener() {
            @Override
            public void onFailure(Object response) {
                hideProgressDialogStatic();
                if (response instanceof ResGvtValidOtpValid) {
                    String error = ((ResGvtValidOtpValid) response).getMessageToDisplay();
                    Toast.makeText(getApplicationContext(), "" + error, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onAuthFail(Object error) {

                hideProgressDialogStatic();
            }

            @Override
            public void onSuccess(Object response, Boolean cancelFlag) {
                hideProgressDialogStatic();
                if (response instanceof ResGvtValidOtpValid) {

                    if (((ResGvtValidOtpValid) response).getStatuscode() == 200) {

                        if (((ResGvtValidOtpValid) response).isStatus()) {
                            if(PreferenceStore.getPrefernceHelperInstace().getIntValue(YelligoApplication.getContext(),PreferenceStore.ROLL_ID)==2){

                                PreferenceStore.getPrefernceHelperInstace().setFlag(YelligoApplication.getContext(), PreferenceStore.LOGIN, true);
                                PreferenceStore.getPrefernceHelperInstace().setIntValue(YelligoApplication.getContext(), PreferenceStore.USER_ID_login, ((ResGvtValidOtpValid) response).getUserId());
                                if (PreferenceStore.getPrefernceHelperInstace().getIntValue(OtpCheckerActivity.this, PreferenceStore.DISTRICT_ID)==0) {
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
                                PreferenceStore.getPrefernceHelperInstace().setIntValue(YelligoApplication.getContext(), PreferenceStore.CITIZEN_ID, ((ResGvtValidOtpValid) response).getUserId());
                                PreferenceStore.getPrefernceHelperInstace().setIntValue(YelligoApplication.getContext(), PreferenceStore.USER_ID_login, ((ResGvtValidOtpValid) response).getUserId());
                      /*  PreferenceStore.getPrefernceHelperInstace().setString(YelligoApplication.getContext(), PreferenceStore.USER_NAME, ((ResOtpValid) response).getData().getName());
                        PreferenceStore.getPrefernceHelperInstace().setString(YelligoApplication.getContext(), PreferenceStore.USER_HOME_LAT, ((ResOtpValid) response).getData().getLatitude());
                        PreferenceStore.getPrefernceHelperInstace().setString(YelligoApplication.getContext(), PreferenceStore.USER_HOME_LNG, ((ResOtpValid) response).getData().getLongitude());
                        PreferenceStore.getPrefernceHelperInstace().setString(YelligoApplication.getContext(), PreferenceStore.USER_RADIOUS, ((ResOtpValid) response).getData().getRadius());
                        PreferenceStore.getPrefernceHelperInstace().setString(YelligoApplication.getContext(), PreferenceStore.USER_EMAIL, ((ResOtpValid) response).getData().getEmailId());
                        PreferenceStore.getPrefernceHelperInstace().setString(YelligoApplication.getContext(), PreferenceStore.USER_PHONE, ((ResOtpValid) response).getData().getPhoneNo());*/
                                PreferenceStore.getPrefernceHelperInstace().setFlag(YelligoApplication.getContext(), PreferenceStore.LOGIN, true);

                                if (PreferenceStore.getPrefernceHelperInstace().getFlag(OtpCheckerActivity.this, PreferenceStore.ISUPDATEPATENTINFO)) {
                                    Intent intent = new Intent(getApplicationContext(), HomeMainActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                    finishAffinity();
                                } else {

                                    getPatientInfo();

                                }
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "" + ((ResGvtValidOtpValid) response).getMessageToDisplay(), Toast.LENGTH_LONG).show();
                        }


                    } else {
                        Toast.makeText(getApplicationContext(), "" + ((ResGvtValidOtpValid) response).getMessageToDisplay(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

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
    public void onInternetConnectivityChanged(boolean isConnected) {

    }

    @OnClick({R.id.tv_resendOTP, R.id.btn_login, R.id.txt_104, R.id.txt_08046848600, R.id.txt_9745697456, R.id.txt_08066692000, R.id.ll_maincall, R.id.helptechnical, R.id.txt_help_Url, R.id.helpcovid})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_resendOTP:

                requestotp();
                break;
            case R.id.btn_login:
                if (Validation()) {

         /*  ReqOTpVAlidBody reqOTpVAlidBody = new ReqOTpVAlidBody();
           reqOTpVAlidBody.setDeviceId("12222300039383883");
           reqOTpVAlidBody.setPhoneNo("" + mobnum);
           reqOTpVAlidBody.setOtp("" + pinView.getText().toString());
           reqOTpVAlidBody.setPnsToken("" + PreferenceStore.getPrefernceHelperInstace().getString(YelligoApplication.getContext(), PreferenceStore.FIREBASE_TOKEN));

           ReqOtpValid reqOtpValid = new ReqOtpValid();
           reqOtpValid.setBody(reqOTpVAlidBody);
           reqOtpValid.setHeader(new ReqHeader());
           reqOtpValid.setTrailer(new ReqTrailer());*/

                    if (getCommonApi().isInternetAvailable(OtpCheckerActivity.this)){
                        ReqOtpValidGvt reqOtpValidGvt = new ReqOtpValidGvt();
                        reqOtpValidGvt.setMobileNo(mobnum);
                        reqOtpValidGvt.setOTP(pinView.getText().toString());
                        reqOtpValidGvt.setpSecurity(getCommonApi().getSecurityObject());
                        reqOtpValidGvt.setRole(rollid);

                        makeotpValid(reqOtpValidGvt);
                    }else {
                        Toast.makeText(YelligoApplication.getContext(),"Please enable internet connection",Toast.LENGTH_LONG).show();
                    }


                }
                break;
            case R.id.txt_104:

                shodilogforcall();
                break;
            case R.id.txt_08046848600:
                shodilogforcall();
                break;
            case R.id.txt_9745697456:
                shodilogforcall();
                break;
            case R.id.txt_08066692000:
                shodilogforcall();
                break;
            case R.id.helptechnical:
                break;
            case R.id.txt_help_Url:
                helpmethod();
                break;
            case R.id.helpcovid:

                break;

            case R.id.ll_maincall:
                shodilogforcall();
                break;
        }
    }

    private void requestotp() {

        showProgressDialogStatic();
            networkService.makeAppLogin(mobnum, new NetworkService.NetworkServiceListener() {
                @Override
                public void onFailure(Object response) {
                    hideProgressDialogStatic();
                    if (response instanceof ResGvtValidOtp) {
                        String error = ((ResGvtValidOtp) response).getMessageToDisplay();
                        if (error != null) {
                            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
                        }
                    }

                }

                @Override
                public void onAuthFail(Object error) {
                    hideProgressDialogStatic();
                }

                @Override
                public void onSuccess(Object response, Boolean cancelFlag) {
                    hideProgressDialogStatic();

                    if (response instanceof ResGvtValidOtp) {
                        if (((ResGvtValidOtp) response).getStatuscode() == 200) {
                            if (((ResGvtValidOtp) response).isStatus()) {
                                PreferenceStore.getPrefernceHelperInstace().setIntValue(YelligoApplication.getContext(), PreferenceStore.ROLL_ID, ((ResGvtValidOtp) response).getRoleId());

                                staerCountTimer();
                              /*  Toast.makeText(getApplicationContext(), "The verification code has been sent to your mobile number.", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(getApplicationContext(), OtpCheckerActivity.class);
                                i.putExtra("MobNumber", number);
                                i.putExtra("rollid", rollid);
                                startActivity(i);*/
                            } else {
                                Toast.makeText(getApplicationContext(), "Please Try Again", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            if (((ResGvtValidOtp) response).getMessageToDisplay() != null) {
                                Toast.makeText(getApplicationContext(), "" + ((ResGvtValidOtp) response).getMessageToDisplay(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
            });

    }

    private void helpmethod() {
        String url = "https://landrecords.karnataka.gov.in/covid/";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }



    private void shodilogforcall() {
        if (ContextCompat.checkSelfPermission(OtpCheckerActivity.this,
                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            number = getResources().getStringArray(R.array.covidnumber);
            AlertDialog.Builder builder = new AlertDialog.Builder(OtpCheckerActivity.this);
            builder.setTitle(R.string.pick_number)
                    .setItems(R.array.covidnumber, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // The 'which' argument contains the index position
                            dialog.dismiss();
                            if (ContextCompat.checkSelfPermission(OtpCheckerActivity.this,
                                    Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                                String numbercall = number[which];
                                Intent intent = new Intent(Intent.ACTION_CALL);
                                intent.setData(Uri.parse("tel:" + numbercall));
                                startActivity(intent);
                            } else {
                                ActivityCompat.requestPermissions(OtpCheckerActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 5101);

                            }
                            // of the selected item
                        }
                    });
            Dialog d = builder.create();
            d.show();
        } else {
            ActivityCompat.requestPermissions(OtpCheckerActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 5101);
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 5101: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    number = getResources().getStringArray(R.array.covidnumber);
                    AlertDialog.Builder builder = new AlertDialog.Builder(OtpCheckerActivity.this);
                    builder.setTitle(R.string.pick_number)
                            .setItems(R.array.covidnumber, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // The 'which' argument contains the index position
                                    if (ContextCompat.checkSelfPermission(OtpCheckerActivity.this,
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
                            findViewById(R.id.activity_main_rl),
                            R.string.permission_rationale,
                            Snackbar.LENGTH_INDEFINITE)
                            .setAction(R.string.ok, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    // Request permission
                                    ActivityCompat.requestPermissions(OtpCheckerActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 5101);
                                }
                            })
                            .show();
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }

        }

    }
}
