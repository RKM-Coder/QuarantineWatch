package com.goalsr.homequarantineTracker.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.goalsr.homequarantineTracker.R;
import com.goalsr.homequarantineTracker.Utils.PreferenceStore;
import com.goalsr.homequarantineTracker.YelligoApplication;
import com.goalsr.homequarantineTracker.apiservice.NetworkService;
import com.goalsr.homequarantineTracker.base.BaseActivity;
import com.goalsr.homequarantineTracker.dialog.CustomDialogGeneric;
import com.goalsr.homequarantineTracker.resposemodel.gotOtpreq.ResGvtValidOtp;
import com.goalsr.homequarantineTracker.resposemodel.otpvalidGovt.ReqOtpValidGvt;
import com.google.android.material.snackbar.Snackbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    @BindView(R.id.et_phoneNum)
    EditText etPhoneNum;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.txt_104)
    TextView txt104;
    @BindView(R.id.txt_08046848600)
    TextView txt08046848600;
    @BindView(R.id.txt_9745697456)
    TextView txt9745697456;
    @BindView(R.id.txt_08066692000)
    TextView txt08066692000;
    @BindView(R.id.helptechnical)
    CardView helptechnical;
    @BindView(R.id.txt_help_Url)
    LinearLayout txtHelpUrl;
    @BindView(R.id.helpcovid)
    CardView helpcovid;
    @BindView(R.id.ll_maincall)
    LinearLayout llMaincall;
    private NetworkService networkService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_login);
        ButterKnife.bind(this);
        initMvp();

        etPhoneNum.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() == 10) {
                    btnLogin.setBackgroundResource(R.drawable.button_after_login);
                } else {
                    btnLogin.setBackgroundResource(R.drawable.button_before_login);
                }

            }
        });
    }

    private void initMvp() {

        networkService = new NetworkService();
        networkService.inject(LoginActivity.this);


    }

    /*@OnClick(R.id.btn_login)
    public void onViewClicked() {
        //getCommonApi().openNewScreen(OtpCheckerActivity.class);




    }*/

    private boolean Validation() {
        String strPhone = etPhoneNum.getText().toString();

        if (TextUtils.isEmpty(strPhone) || ((strPhone.length() != 10))) {

            Toast.makeText(getApplicationContext(), "Please Enter Valid Mobile Number", Toast.LENGTH_LONG).show();
            etPhoneNum.setText("");
            etPhoneNum.requestFocus();
            return false;
        }
        return true;

    }

    private void reqforPI() {


            networkService.makeAppLogin(etPhoneNum.getText().toString(), new NetworkService.NetworkServiceListener() {
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
                                if (((ResGvtValidOtp) response).getRoleId()==1) {
                                    PreferenceStore.getPrefernceHelperInstace().setIntValue(YelligoApplication.getContext(), PreferenceStore.ROLL_ID, ((ResGvtValidOtp) response).getRoleId());
                                    Toast.makeText(getApplicationContext(), "The verification code has been sent to your mobile number.", Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(getApplicationContext(), OtpCheckerActivity.class);
                                    i.putExtra("MobNumber", etPhoneNum.getText().toString());
                                    i.putExtra("rollid", ((ResGvtValidOtp) response).getRoleId());
                                    startActivity(i);
                                }else{
                                    Toast.makeText(getApplicationContext(), "This App to be used only by the quarantined person. Please login to officer's App", Toast.LENGTH_LONG).show();
                                }
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

    @Override
    public void onInternetConnectivityChanged(boolean isConnected) {

    }

    @OnClick({R.id.btn_login, R.id.txt_104, R.id.txt_08046848600, R.id.txt_9745697456, R.id.txt_08066692000, R.id.helptechnical, R.id.txt_help_Url, R.id.helpcovid, R.id.ll_maincall})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                if (Validation()) {
                    if (!PreferenceStore.getPrefernceHelperInstace().getString(YelligoApplication.getContext(), PreferenceStore.USER_PHONE).equalsIgnoreCase("")) {
                        if (!PreferenceStore.getPrefernceHelperInstace().getString(YelligoApplication.getContext(), PreferenceStore.USER_PHONE).equalsIgnoreCase(etPhoneNum.getText().toString())) {
                            showDialogWarnLogout("The application is registered with " + PreferenceStore.getPrefernceHelperInstace().getString(YelligoApplication.getContext(), PreferenceStore.USER_PHONE)
                                    + ", do you want to continue?");
                        } else {

                            if (getCommonApi().isInternetAvailable(LoginActivity.this)) {
                                showProgressDialogStatic();

                                reqforPI();
                            }else {
                                Toast.makeText(YelligoApplication.getContext(),"Please enable internet connection",Toast.LENGTH_LONG).show();
                            }
                        }
                    }else {

                        PreferenceStore.getPrefernceHelperInstace().clearValue(YelligoApplication.getContext(),PreferenceStore.ISUPDATEPATENTINFO);
                        PreferenceStore.getPrefernceHelperInstace().clearValue(YelligoApplication.getContext(),PreferenceStore.USER_PHONE);
                        PreferenceStore.getPrefernceHelperInstace().clearValue(YelligoApplication.getContext(),PreferenceStore.CITIZEN_ID);
                        PreferenceStore.getPrefernceHelperInstace().clearValue(YelligoApplication.getContext(),PreferenceStore.DISTRICT_NAME);
                        PreferenceStore.getPrefernceHelperInstace().clearValue(YelligoApplication.getContext(),PreferenceStore.DISTRICT_ID);
                        getPatientinfoRepository().clear();
                        getPatientFamillyinfoRepository().clear();
                        if (getCommonApi().isInternetAvailable(LoginActivity.this)) {
                            showProgressDialogStatic();

                            reqforPI();
                        }else {
                            Toast.makeText(YelligoApplication.getContext(),"Please enable internet connection",Toast.LENGTH_LONG).show();
                        }
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
    private void showDialogWarnLogout(String message) {
        if (!isFinishing()) {
            CustomDialogGeneric dialog = new CustomDialogGeneric(LoginActivity.this, "",
                    new CustomDialogGeneric.OnButtonClickListener() {
                        @Override
                        public void onLeftButtonClick(CustomDialogGeneric dialog) {

                            // Toast.makeText(getActivity(),"Customer created successfully",Toast.LENGTH_SHORT).show();
                            PreferenceStore.getPrefernceHelperInstace().clearValue(YelligoApplication.getContext(),PreferenceStore.ISUPDATEPATENTINFO);
                            PreferenceStore.getPrefernceHelperInstace().clearValue(YelligoApplication.getContext(),PreferenceStore.USER_PHONE);
                            PreferenceStore.getPrefernceHelperInstace().clearValue(YelligoApplication.getContext(),PreferenceStore.DISTRICT_NAME);
                            PreferenceStore.getPrefernceHelperInstace().clearValue(YelligoApplication.getContext(),PreferenceStore.DISTRICT_ID);
                            getPatientinfoRepository().clear();
                            getPatientFamillyinfoRepository().clear();
                            if (getCommonApi().isInternetAvailable(LoginActivity.this)) {
                                showProgressDialogStatic();

                                reqforPI();
                            }else {
                                Toast.makeText(YelligoApplication.getContext(),"Please enable internet connection",Toast.LENGTH_LONG).show();
                            }
                            dialog.dismiss();
                            //submitLogout();
                        }

                        @Override
                        public void onRightButtonClick(CustomDialogGeneric dialog, String notes) {
                            dialog.dismiss();

                        }


                    });
            dialog.setCancelable(false);
            dialog.setRightButtonText("No");
            dialog.setRightButtonVisibility(View.VISIBLE);
            dialog.setLeftButtonVisibility(View.VISIBLE);
            dialog.setLeftButtonText("Yes");
            dialog.setDialogType(CustomDialogGeneric.TYPE_ALERT);
            dialog.setDescription("" + message);
            dialog.show();
        }
    }
    private void helpmethod() {
        String url = "https://landrecords.karnataka.gov.in/covid/";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    String[] number;

    private void shodilogforcall() {
        number = getResources().getStringArray(R.array.covidnumber);
        if (ContextCompat.checkSelfPermission(LoginActivity.this,
                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            number = getResources().getStringArray(R.array.covidnumber);
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            builder.setTitle(R.string.pick_number)
                    .setItems(R.array.covidnumber, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // The 'which' argument contains the index position
                            dialog.dismiss();
                            if (ContextCompat.checkSelfPermission(LoginActivity.this,
                                    Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                                String numbercall = number[which];
                                Intent intent = new Intent(Intent.ACTION_CALL);
                                intent.setData(Uri.parse("tel:" + numbercall));
                                startActivity(intent);
                            } else {
                                ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 5101);

                            }
                            // of the selected item
                        }
                    });
            Dialog d = builder.create();
            d.show();
        } else {
            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 5101);
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                    builder.setTitle(R.string.pick_number)
                            .setItems(R.array.covidnumber, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // The 'which' argument contains the index position
                                    if (ContextCompat.checkSelfPermission(LoginActivity.this,
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
                                    ActivityCompat.requestPermissions(LoginActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 5101);
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
