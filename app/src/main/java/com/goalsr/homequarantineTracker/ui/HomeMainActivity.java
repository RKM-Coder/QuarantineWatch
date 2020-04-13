package com.goalsr.homequarantineTracker.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.goalsr.homequarantineTracker.R;
import com.goalsr.homequarantineTracker.Utils.PreferenceStore;
import com.goalsr.homequarantineTracker.YelligoApplication;
import com.goalsr.homequarantineTracker.adapter.FamillyListAdapter;
import com.goalsr.homequarantineTracker.apiservice.NetworkService;
import com.goalsr.homequarantineTracker.base.BaseActivity;
import com.goalsr.homequarantineTracker.dialog.CustomDialogGeneric;
import com.goalsr.homequarantineTracker.resposemodel.getPatientinfo.ReqPatient;
import com.goalsr.homequarantineTracker.resposemodel.getPatientinfo.ResPatientFamilyInfo;
import com.goalsr.homequarantineTracker.resposemodel.getPatientinfo.ResPatientInfo;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeMainActivity extends BaseActivity implements FamillyListAdapter.CheckedListener {

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
    @BindView(R.id.ll_main_patient)
    LinearLayout llMainPatient;
    @BindView(R.id.img_add)
    ImageView imgAdd;
    @BindView(R.id.rv_view_famillly)
    RecyclerView rvViewFamillly;
    @BindView(R.id.txt_main_p_name)
    TextView txtMainPName;
    @BindView(R.id.txt_main_p_mobile)
    TextView txtMainPMobile;
    private NetworkService networkService;
    private FamillyListAdapter adapter;
    ResPatientInfo resPatientInfo = new ResPatientInfo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_main);
        ButterKnife.bind(this);
        initMvp();
        adapter = new FamillyListAdapter(this, new ArrayList<ResPatientFamilyInfo>());
        adapter.setListener(this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvViewFamillly.setLayoutManager(manager);
        rvViewFamillly.setAdapter(adapter);
        if (PreferenceStore.getPrefernceHelperInstace().getIntValue(YelligoApplication.getContext(),PreferenceStore.ROLL_ID)==2){
            tvLogout.setVisibility(View.GONE);
        }else {
            tvLogout.setVisibility(View.VISIBLE);
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        getPatentInfo();
    }

    private void getPatentInfo() {
        int ciD=PreferenceStore.getPrefernceHelperInstace().getIntValue(YelligoApplication.getContext(),PreferenceStore.CITIZEN_ID);
        resPatientInfo = getPatientinfoRepository().getPatientInfo(ciD);
        setinfo();
    }

    private void setinfo() {
        if (resPatientInfo.getMobile() != null) {
            txtMainPMobile.setText("" + resPatientInfo.getMobile());
        } else {
            txtMainPMobile.setText("");
        }

        if (resPatientInfo.getName() != null) {
            txtMainPName.setText("" + resPatientInfo.getName());
        } else {
            txtMainPName.setText("");
        }

        if (getCommonApi().isInternetAvailable(HomeMainActivity.this)){
            getPatientFamillyInfo();
        }else {
            Toast.makeText(YelligoApplication.getContext(),"Please enable internet connection",Toast.LENGTH_LONG).show();
        }

        updatView();

    }

    private void getPatientFamillyInfo() {
        showProgressDialogStatic();
        ReqPatient reqPatient = new ReqPatient();
        reqPatient.setCitizenId(PreferenceStore.getPrefernceHelperInstace().getIntValue(YelligoApplication.getContext(),PreferenceStore.CITIZEN_ID));
        reqPatient.setLevel(2);
        reqPatient.setpSecurity(getCommonApi().getSecurityObject());
        networkService.getPatientFamilyInfo(reqPatient, new NetworkService.NetworkServiceListener() {
            @Override
            public void onFailure(Object response) {
                hideProgressDialogStatic();
                if (response instanceof String) {
                    if (!((String) response).equalsIgnoreCase("")) {
                        Toast.makeText(YelligoApplication.getContext(), "" + response, Toast.LENGTH_LONG).show();
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
                if (response instanceof ResPatientFamilyInfo) {
                    updatView();


                    //updateUI();

                }
            }
        });

    }

    private void updatView() {
        //ArrayList<ResPatientFamilyInfo> lisofpatient = new ArrayList<>();
        getPatienFamillytViewmodel().getLivedatPAtient().observe(this, new Observer<List<ResPatientFamilyInfo>>() {
            @Override
            public void onChanged(List<ResPatientFamilyInfo> resPatientFamilyInfos) {
                adapter.setValue((ArrayList<ResPatientFamilyInfo>) resPatientFamilyInfos);
            }
        });
        //lisofpatient.addAll(getPatientFamillyinfoRepository().getPatientFamilyInfo());

    }

    private void initMvp() {

        networkService = new NetworkService();
        networkService.inject(YelligoApplication.getContext());
    }

    @Override
    public void onInternetConnectivityChanged(boolean isConnected) {

    }

    private void submitLogout() {
        PreferenceStore.getPrefernceHelperInstace().setFlag(YelligoApplication.getContext(), PreferenceStore.LOGIN, false);
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finishAffinity();
    }

    @OnClick({R.id.tv_logout, R.id.ll_main_patient, R.id.img_add,R.id.ll_call,R.id.ll_lelp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_logout:
                showDialogWarnLogout("Do you want to logout?");
                break;
            case R.id.ll_main_patient:
                Bundle bundle=new Bundle();
                bundle.putString("key","self");
                bundle.putInt("v_id",0);
                getCommonApi().openNewScreen(PatientSymtomUpdateActivity.class,bundle);
                break;
            case R.id.img_add:
                addfamilly();
                break;

            case R.id.ll_call:
                shodilogforcall();
                break;
            case R.id.ll_lelp:
                helpmethod();
                break;
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
        if (ContextCompat.checkSelfPermission(HomeMainActivity.this,
                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            number = getResources().getStringArray(R.array.covidnumber);
            AlertDialog.Builder builder = new AlertDialog.Builder(HomeMainActivity.this);
            builder.setTitle(R.string.pick_number)
                    .setItems(R.array.covidnumber, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // The 'which' argument contains the index position
                            dialog.dismiss();
                            if (ContextCompat.checkSelfPermission(HomeMainActivity.this,
                                    Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                                String numbercall = number[which];
                                Intent intent = new Intent(Intent.ACTION_CALL);
                                intent.setData(Uri.parse("tel:" + numbercall));
                                startActivity(intent);
                            } else {
                                ActivityCompat.requestPermissions(HomeMainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 5101);

                            }
                            // of the selected item
                        }
                    });
            Dialog d = builder.create();
            d.show();
        } else {
            ActivityCompat.requestPermissions(HomeMainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 5101);
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeMainActivity.this);
                    builder.setTitle(R.string.pick_number)
                            .setItems(R.array.covidnumber, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // The 'which' argument contains the index position
                                    if (ContextCompat.checkSelfPermission(HomeMainActivity.this,
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
                                    ActivityCompat.requestPermissions(HomeMainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 5101);
                                }
                            })
                            .show();
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }

        }

    }

    private void showDialogWarnLogout(String message) {
        if (!isFinishing()) {
            CustomDialogGeneric dialog = new CustomDialogGeneric(HomeMainActivity.this, "",
                    new CustomDialogGeneric.OnButtonClickListener() {
                        @Override
                        public void onLeftButtonClick(CustomDialogGeneric dialog) {
                            dialog.dismiss();
                            // Toast.makeText(getActivity(),"Customer created successfully",Toast.LENGTH_SHORT).show();
                            submitLogout();
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

    private void addfamilly() {
        getCommonApi().openNewScreen(PatientFamillyActivity.class);
    }

    @Override
    public void onItemCheckedFamilly(int position, ResPatientFamilyInfo item) {
        Bundle bundle=new Bundle();
        bundle.putString("key","family");
        bundle.putInt("v_id",item.getCitizenFamilyPersonId());
        getCommonApi().openNewScreen(PatientSymtomUpdateActivity.class,bundle);

    }

    @Override
    public void onBackPressed() {


        if (PreferenceStore.getPrefernceHelperInstace().getIntValue(YelligoApplication.getContext(),PreferenceStore.ROLL_ID)!=2) {

            new AlertDialog.Builder(this)
                    .setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            HomeMainActivity.super.onBackPressed();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }else {
            super.onBackPressed();
        }

    }
}
