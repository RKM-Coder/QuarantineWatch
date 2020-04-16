package com.goalsr.homequarantineTracker.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.goalsr.homequarantineTracker.R;
import com.goalsr.homequarantineTracker.Utils.AppConstants;
import com.goalsr.homequarantineTracker.Utils.PreferenceStore;
import com.goalsr.homequarantineTracker.YelligoApplication;
import com.goalsr.homequarantineTracker.adapter.SpinAdapter;
import com.goalsr.homequarantineTracker.apiservice.NetworkService;
import com.goalsr.homequarantineTracker.base.BaseActivity;
import com.goalsr.homequarantineTracker.db.viewmodel.PatientFamilyViewmodel;
import com.goalsr.homequarantineTracker.dialog.CustomDialogGeneric;
import com.goalsr.homequarantineTracker.fragment.DatePickerFragment;
import com.goalsr.homequarantineTracker.gpsenable.GpsUtils;
import com.goalsr.homequarantineTracker.resposemodel.DistrictModel;
import com.goalsr.homequarantineTracker.resposemodel.TalukModel;
import com.goalsr.homequarantineTracker.resposemodel.getPatientinfo.ReqPatient;
import com.goalsr.homequarantineTracker.resposemodel.getPatientinfo.ReqUpdatePatentIFamilynfo;
import com.goalsr.homequarantineTracker.resposemodel.getPatientinfo.ResPatientFamilyInfo;
import com.goalsr.homequarantineTracker.resposemodel.getPatientinfo.ResPatientInfo;
import com.goalsr.homequarantineTracker.resposemodel.getPatientinfo.ResUpdateInfo;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PatientFamillyActivity extends BaseActivity implements DatePickerFragment.OnFragmentInteractionListener {

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
    @BindView(R.id.et_customer_name)
    EditText etCustomerName;
    @BindView(R.id.sp_relation)
    Spinner spRelation;
    @BindView(R.id.sp_gender)
    Spinner spGender;
    @BindView(R.id.et_customer_mobile)
    EditText etCustomerMobile;
    @BindView(R.id.et_customer_email)
    EditText etCustomerEmail;
    @BindView(R.id.txt_DOA)
    TextView txtDOA;
    @BindView(R.id.txt_qurantinedate)
    TextView txtQurantinedate;
    @BindView(R.id.et_poorigin)
    EditText etPoorigin;
    @BindView(R.id.et_poarrival)
    EditText etPoarrival;
    @BindView(R.id.et_houseno)
    EditText etHouseno;
    @BindView(R.id.et_building)
    EditText etBuilding;
    @BindView(R.id.et_street)
    EditText etStreet;
    @BindView(R.id.et_city)
    EditText etCity;
    @BindView(R.id.sp_district)
    Spinner spDistrict;
    @BindView(R.id.sp_taluk)
    Spinner spTaluk;
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
    @BindView(R.id.submit_btn)
    Button submitBtn;

    private static final String TAG = "PATIENT";
    @BindView(R.id.et_age)
    EditText etAge;
    private NetworkService networkService;
    private int selectedgender = 0;
    private int selectedrelation = 0;
    int district_code = 0, taluk_code = 0;
    List<TalukModel> listOfTaluk;
    List<DistrictModel> listOFdistrict;
    boolean isfever, iscoughandsour, isbreathing, isdiarria, isdiabaties, ishypertense, isheartdisses, ishiv;
    ResPatientFamilyInfo resPatientInfo = new ResPatientFamilyInfo();
    ResPatientInfo resPatientInfobasic = new ResPatientInfo();
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

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
    private int selctedidfamily = 0;
    private String key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_familly);
        ButterKnife.bind(this);
        initMvp();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.getString("key").equalsIgnoreCase("self")) {
                key = bundle.getString("key");

            } else if (bundle.getString("key").equalsIgnoreCase("family")) {
                key = bundle.getString("key");
                selctedidfamily = bundle.getInt("v_id");
                //getPatientFamilyByid();
            }
        }

        String genderarray[] = getResources().getStringArray(R.array.gender);
        List<String> genderlist = new ArrayList<>();
        genderlist = new ArrayList<>(Arrays.asList(genderarray));

        String relationship[] = getResources().getStringArray(R.array.relation);
        List<String> relationlist = new ArrayList<>();
        relationlist = new ArrayList<>(Arrays.asList(relationship));

       /* String genderarray[] = getResources().getStringArray(R.array.gender);
        ArrayList<String> genderlist=new ArrayList<>();
        genderlist= (ArrayList<String>) Arrays.asList(genderarray);*/
        makeSpinnerGenderMethod((ArrayList<String>) genderlist);
        makeSpinnerRElationMethod((ArrayList<String>) relationlist);

        getDistrict();
        getTaluk();
        requestPermissions();
        int ciD=PreferenceStore.getPrefernceHelperInstace().getIntValue(YelligoApplication.getContext(),PreferenceStore.CITIZEN_ID);
        resPatientInfobasic = getPatientinfoRepository().getPatientInfo(ciD);

        updateUI();
       /* etCustomerMobile.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() == 10) {
                   //checkexistosno
                }

            }
        });*/
        //getPatientInfo();
    }

    private void initMvp() {
        networkService = new NetworkService();
        networkService.inject(PatientFamillyActivity.this);
    }

    private void getPatientInfo() {
        showProgressDialogStatic();
        ReqPatient reqPatient = new ReqPatient();
        reqPatient.setCitizenId(38571);
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

                    getPatientinfoRepository().insert((ResPatientInfo) response);
                    updateUI();

                }
            }
        });

    }

    private void updateUI() {

        resPatientInfo = getPatientFamillyinfoRepository().getPatientFamilyInfo(selctedidfamily);
        if (resPatientInfo == null) {
            tvHeaderFac.setText("Add Family Member");
        } else {
            tvHeaderFac.setText("Family Member Details");
        }
        if (resPatientInfo != null) {

            if (PreferenceStore.getPrefernceHelperInstace().getIntValue(YelligoApplication.getContext(),PreferenceStore.ROLL_ID)==2){

                if (resPatientInfo.getName() != null) {
                    etCustomerName.setText("" + resPatientInfo.getName());
                    etCustomerName.setEnabled(true);
                }
                if (resPatientInfo.getMobile() != null) {
                    etCustomerMobile.setText("" + resPatientInfo.getMobile());
                    etCustomerMobile.setEnabled(true);
                }

                etAge.setText("" + resPatientInfo.getAge());


                if (resPatientInfo.getEmail() != null) {
                    etCustomerEmail.setText("" + resPatientInfo.getEmail());
                    etCustomerEmail.setEnabled(true);
                }
                if (resPatientInfo.getDOA() != null) {
                    txtDOA.setText("" + AppConstants.dateFormatChangerGVT(resPatientInfo.getDOA()));
                    txtDOA.setEnabled(true);
                }
                if (resPatientInfo.getDateQurantine() != null) {
                    txtQurantinedate.setText("" + AppConstants.dateFormatChangerGVT( resPatientInfo.getDateQurantine()));
                    txtQurantinedate.setEnabled(true);
                }
                if (resPatientInfo.getPOOrigin() != null) {
                    etPoorigin.setText("" + resPatientInfo.getPOOrigin());
                    etPoorigin.setEnabled(true);
                }
                if (resPatientInfo.getPoArrival() != null) {
                    etPoarrival.setText("" + resPatientInfo.getPoArrival());
                    etPoarrival.setEnabled(true);
                }
                if (resPatientInfo.getHNo() != null) {
                    etHouseno.setText("" + resPatientInfo.getHNo());
                    etHouseno.setEnabled(true);
                }
                if (resPatientInfo.getBuilding() != null) {
                    etBuilding.setText("" + resPatientInfo.getBuilding());
                    etBuilding.setEnabled(true);
                }
                if (resPatientInfo.getStreet() != null) {
                    etStreet.setText("" + resPatientInfo.getStreet());
                    etStreet.setEnabled(true);
                }
                if (resPatientInfo.getCity() != null) {
                    etCity.setText("" + resPatientInfo.getCity());
                    etCity.setEnabled(true);
                }

                if (resPatientInfo.getGenID() != -1) {
                    spGender.setSelection(resPatientInfo.getGenID());
                    spGender.setEnabled(true);
                }

                if (resPatientInfo.getRelationId() != -1) {
                    spRelation.setSelection(resPatientInfo.getRelationId());
                    spRelation.setEnabled(true);
                }

                etAge.setText(""+resPatientInfo.getAge());
                etAge.setEnabled(true);


                if (resPatientInfobasic != null) {
                    if (resPatientInfobasic.getHNo() != null) {
                        etHouseno.setText("" + resPatientInfobasic.getHNo());
                        etHouseno.setEnabled(false);

                    }
                    if (resPatientInfobasic.getBuilding() != null) {
                        etBuilding.setText("" + resPatientInfobasic.getBuilding());
                        etBuilding.setEnabled(false);
                    }
                    if (resPatientInfobasic.getStreet() != null) {
                        etStreet.setText("" + resPatientInfobasic.getStreet());
                        etStreet.setEnabled(false);
                    }
                    if (resPatientInfobasic.getCity() != null) {
                        etCity.setText("" + resPatientInfo.getCity());
                        etCity.setEnabled(false);
                    }
                    district_code=resPatientInfobasic.getDistrictCode();
                    taluk_code=resPatientInfobasic.getTalukCode();
                    spTaluk.setEnabled(false);
                    spDistrict.setEnabled(false);
                }

                submitBtn.setVisibility(View.VISIBLE);

            }else {
                if (resPatientInfo.getName() != null) {
                    etCustomerName.setText("" + resPatientInfo.getName());
                    etCustomerName.setEnabled(false);
                }
                if (resPatientInfo.getMobile() != null) {
                    etCustomerMobile.setText("" + resPatientInfo.getMobile());
                    etCustomerMobile.setEnabled(false);
                }

                etAge.setText("" + resPatientInfo.getAge());


                if (resPatientInfo.getEmail() != null) {
                    etCustomerEmail.setText("" + resPatientInfo.getEmail());
                    etCustomerEmail.setEnabled(false);
                }
                if (resPatientInfo.getDOA() != null) {
                    txtDOA.setText("" + AppConstants.dateFormatChangerGVT(resPatientInfo.getDOA()));
                    txtDOA.setEnabled(false);
                }
                if (resPatientInfo.getDateQurantine() != null) {
                    txtQurantinedate.setText("" + AppConstants.dateFormatChangerGVT( resPatientInfo.getDateQurantine()));
                    txtQurantinedate.setEnabled(false);
                }
                if (resPatientInfo.getPOOrigin() != null) {
                    etPoorigin.setText("" + resPatientInfo.getPOOrigin());
                    etPoorigin.setEnabled(false);
                }
                if (resPatientInfo.getPoArrival() != null) {
                    etPoarrival.setText("" + resPatientInfo.getPoArrival());
                    etPoarrival.setEnabled(false);
                }
                if (resPatientInfo.getHNo() != null) {
                    etHouseno.setText("" + resPatientInfo.getHNo());
                    etHouseno.setEnabled(false);
                }
                if (resPatientInfo.getBuilding() != null) {
                    etBuilding.setText("" + resPatientInfo.getBuilding());
                    etBuilding.setEnabled(false);
                }
                if (resPatientInfo.getStreet() != null) {
                    etStreet.setText("" + resPatientInfo.getStreet());
                    etStreet.setEnabled(false);
                }
                if (resPatientInfo.getCity() != null) {
                    etCity.setText("" + resPatientInfo.getCity());
                    etCity.setEnabled(false);
                }

                if (resPatientInfo.getGenID() != -1) {
                    spGender.setSelection(resPatientInfo.getGenID());
                    spGender.setEnabled(false);
                }

                if (resPatientInfo.getRelationId() != -1) {
                    spRelation.setSelection(resPatientInfo.getRelationId());
                    spRelation.setEnabled(false);
                }

                etAge.setText(""+resPatientInfo.getAge());
                etAge.setEnabled(false);


                if (resPatientInfobasic != null) {
                    if (resPatientInfobasic.getHNo() != null) {
                        etHouseno.setText("" + resPatientInfobasic.getHNo());
                        etHouseno.setEnabled(false);

                    }
                    if (resPatientInfobasic.getBuilding() != null) {
                        etBuilding.setText("" + resPatientInfobasic.getBuilding());
                        etBuilding.setEnabled(false);
                    }
                    if (resPatientInfobasic.getStreet() != null) {
                        etStreet.setText("" + resPatientInfobasic.getStreet());
                        etStreet.setEnabled(false);
                    }
                    if (resPatientInfobasic.getCity() != null) {
                        etCity.setText("" + resPatientInfo.getCity());
                        etCity.setEnabled(false);
                    }
                    district_code=resPatientInfobasic.getDistrictCode();
                    taluk_code=resPatientInfobasic.getTalukCode();
                    spTaluk.setEnabled(false);
                    spDistrict.setEnabled(false);
                }

                submitBtn.setVisibility(View.GONE);
            }

        } else {
            //submitBtn.setVisibility(View.VISIBLE);
            if (resPatientInfobasic != null) {
                if (resPatientInfobasic.getHNo() != null) {
                    etHouseno.setText("" + resPatientInfobasic.getHNo());
                    etHouseno.setEnabled(false);

                }
                if (resPatientInfobasic.getBuilding() != null) {
                    etBuilding.setText("" + resPatientInfobasic.getBuilding());
                    etBuilding.setEnabled(false);
                }
                if (resPatientInfobasic.getStreet() != null) {
                    etStreet.setText("" + resPatientInfobasic.getStreet());
                    etStreet.setEnabled(false);
                }
                if (resPatientInfobasic.getCity() != null) {
                    etCity.setEnabled(false);
                }
                district_code=resPatientInfobasic.getDistrictCode();
                taluk_code=resPatientInfobasic.getTalukCode();
                spTaluk.setEnabled(false);
                spDistrict.setEnabled(false);
            }
        }


        makeSpinnerDistrictMethod((ArrayList<DistrictModel>) listOFdistrict);


    }

    private void getTaluk() {
        String jsonFileString = getCommonApi().getJsonFromAssets(getApplicationContext(), "District_Taluka.json");
        Log.i("data", jsonFileString);

        Gson gson = new Gson();
        Type listUserType = new TypeToken<List<TalukModel>>() {
        }.getType();
        listOfTaluk = new ArrayList<>();
        listOfTaluk = gson.fromJson(jsonFileString, listUserType);

        defaluttaluk();
    }

    private void getDistrict() {
        String jsonFileString = getCommonApi().getJsonFromAssets(getApplicationContext(), "District.json");
        Log.i("data", jsonFileString);

        Gson gson = new Gson();
        Type listUserType = new TypeToken<List<DistrictModel>>() {
        }.getType();
        listOFdistrict = new ArrayList<>();
        listOFdistrict = gson.fromJson(jsonFileString, listUserType);
        DistrictModel item = new DistrictModel();
        item.setDISTRICT_NAME(" Select District");
        item.setDistrict_code(-1);
        listOFdistrict.add(0, item);
        makeSpinnerDistrictMethod((ArrayList<DistrictModel>) listOFdistrict);


    }

    private void defaluttaluk() {
        int dftdistrict_code = listOFdistrict.get(0).getDistrict_code();
        List<TalukModel> listOfTalukBydistrict = new ArrayList<>();
        for (TalukModel talukModel : listOfTaluk) {
            if (talukModel.getDistrict_code() == dftdistrict_code) {
                listOfTalukBydistrict.add(talukModel);
            }
        }
        makeSpinnerTalukMethod((ArrayList<TalukModel>) listOfTalukBydistrict);
    }

    private void makeSpinnerGenderMethod(final ArrayList<String> list) {
        SpinAdapter<String> adapter =
                new SpinAdapter(getApplicationContext(), 0, 0, list);
//        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spGender.setAdapter(adapter);
        spGender.setSelection(0);
        adapter.notifyDataSetChanged();
        spGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedgender = position;
                /*if (position != 0) {
                    selectedgender = list.get(position).toString();
                } else {
                    selectedgender = "";
                }*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void makeSpinnerRElationMethod(final ArrayList<String> list) {
        SpinAdapter<String> adapter =
                new SpinAdapter(getApplicationContext(), 0, 0, list);
//        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spRelation.setAdapter(adapter);
        spRelation.setSelection(0);
        adapter.notifyDataSetChanged();
        spRelation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedrelation = position;
                /*if (position != 0) {
                    selectedgender = list.get(position).toString();
                } else {
                    selectedgender = "";
                }*/
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    private void makeSpinnerDistrictMethod(final ArrayList<DistrictModel> list) {

        SpinAdapter<String> adapter =
                new SpinAdapter(getApplicationContext(), 0, 0, list);
//        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spDistrict.setAdapter(adapter);
        spDistrict.setSelection(0);
        adapter.notifyDataSetChanged();
        spDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0) {
                    district_code = list.get(position).getDistrict_code();
                    Log.e("GVT-RESULT", district_code + "");
                    filtertaluk(district_code);
//                    makeSpinnerTalukMethod();
                } else {
                    district_code = -1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (resPatientInfobasic != null) {

            if (resPatientInfobasic.getDistrictCode() != -1) {

                for (int i = 0; i < listOFdistrict.size(); i++) {
                    if (listOFdistrict.get(i).getDistrict_code() == resPatientInfobasic.getDistrictCode()) {
                        spDistrict.setSelection(i);
                        spDistrict.setEnabled(false);
                    }
                }


            }
        }

    }

    private void filtertaluk(int district_code) {
        List<TalukModel> listOfTalukBydistrict = new ArrayList<>();
        for (TalukModel talukModel : listOfTaluk) {
            if (talukModel.getDistrict_code() == district_code) {
                listOfTalukBydistrict.add(talukModel);
            }
        }
        makeSpinnerTalukMethod((ArrayList<TalukModel>) listOfTalukBydistrict);
    }

    private void makeSpinnerTalukMethod(final ArrayList<TalukModel> listofTaluk) {
        TalukModel item = new TalukModel();
        item.setTALUKA_NAME(" Select Taluk");
        item.setTALUKA_CODE(-1);
        listofTaluk.add(0, item);

        SpinAdapter<String> adapter =
                new SpinAdapter(getApplicationContext(), 0, 0, listofTaluk);
//        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spTaluk.setAdapter(adapter);
        spTaluk.setSelection(0);
        adapter.notifyDataSetChanged();
        spTaluk.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                Log.e("GVT-RESULT", taluk_code + "");
                if (position != 0) {
                    taluk_code = listofTaluk.get(position).getTALUKA_CODE();

                } else {
                    taluk_code = -1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (resPatientInfobasic != null) {
            if (resPatientInfobasic.getTalukCode() != -1) {
                for (int i = 0; i < listofTaluk.size(); i++) {
                    if (listofTaluk.get(i).getTALUKA_CODE() == resPatientInfobasic.getTalukCode()) {
                        spTaluk.setSelection(i);
                        spTaluk.setEnabled(false);
                    }
                }
            }
        }

    }

    @Override
    public void onInternetConnectivityChanged(boolean isConnected) {

    }

    String daovvalue = "";

    /*@OnClick({})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_DOA:
                DatePickerFragment newFragment = new DatePickerFragment();
                daovvalue = "dao";

                // set the targetFragment to receive the results, specifying the request code
                //  newFragment.setTargetFragment(newFragment, REQUEST_CODE);
                // show the datePicker
                newFragment.show(getSupportFragmentManager(), "datePicker");
                break;
            case R.id.txt_qurantinedate:
                daovvalue = "qao";
                DatePickerFragment newFragment2 = new DatePickerFragment();


                // set the targetFragment to receive the results, specifying the request code
                //  newFragment.setTargetFragment(newFragment, REQUEST_CODE);
                // show the datePicker
                newFragment2.show(getSupportFragmentManager(), "datePicker");
                break;
        }
    }*/

    String startdate = "", enddate = "";

    @Override
    public void onFragmentInteraction(String uri) {
        if (daovvalue.equalsIgnoreCase("dao")) {
            startdate = uri;
            txtDOA.setText("" + AppConstants.dateFormatChangerGVT( uri));

        } else if (daovvalue.equalsIgnoreCase("qao")) {
            enddate = uri;
            txtQurantinedate.setText("" +  AppConstants.dateFormatChangerGVT(uri));

        }

    }

    @OnClick({R.id.txt_DOA, R.id.tv_logout, R.id.ll_call, R.id.ll_lelp, R.id.txt_qurantinedate, R.id.chk_box1, R.id.chk_box2, R.id.chk_box3, R.id.chk_box4, R.id.chk_box5, R.id.chk_box6, R.id.chk_box7, R.id.chk_box8, R.id.submit_btn, R.id.iv_back_fac})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txt_DOA:
                DatePickerFragment newFragment = new DatePickerFragment();
                daovvalue = "dao";
                Bundle args = new Bundle();

                args.putString("datefrom", "");
                newFragment.setArguments(args);
                // set the targetFragment to receive the results, specifying the request code
                //  newFragment.setTargetFragment(newFragment, REQUEST_CODE);
                // show the datePicker
                newFragment.show(getSupportFragmentManager(), "datePicker");
                break;
            case R.id.txt_qurantinedate:
                if (startdate.equalsIgnoreCase("")) {
                    showDialog("Please add start date");
                } else {
                    daovvalue = "qao";
                    DatePickerFragment newFragment2 = new DatePickerFragment();
                    Bundle args1 = new Bundle();

                    args1.putString("datefrom", startdate);
                    newFragment2.setArguments(args1);
                    // set the targetFragment to receive the results, specifying the request code
                    //  newFragment.setTargetFragment(newFragment, REQUEST_CODE);
                    // show the datePicker
                    newFragment2.show(getSupportFragmentManager(), "datePicker");
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
                if (getCommonApi().isInternetAvailable(PatientFamillyActivity.this)){
                    submitData();
                }else {
                    Toast.makeText(YelligoApplication.getContext(),"Please enable internet connection",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.iv_back_fac:
                finish();
                break;
            case R.id.tv_logout:
                showDialogWarnLogout("Do you want to logout?");
                break;
            case R.id.ll_call:
                shodilogforcall();
                break;
            case R.id.ll_lelp:
                helpmethod();
                break;

        }
    }

    private void showDialogWarnLogout(String message) {
        if (!isFinishing()) {
            CustomDialogGeneric dialog = new CustomDialogGeneric(PatientFamillyActivity.this, "",
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

    private void submitLogout() {
        PreferenceStore.getPrefernceHelperInstace().setFlag(YelligoApplication.getContext(), PreferenceStore.LOGIN, false);
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finishAffinity();
    }


    private void helpmethod() {
        String url = "https://landrecords.karnataka.gov.in/covid/";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    String[] number;

    private void shodilogforcall() {
        if (ContextCompat.checkSelfPermission(PatientFamillyActivity.this,
                Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            number = getResources().getStringArray(R.array.covidnumber);
            AlertDialog.Builder builder = new AlertDialog.Builder(PatientFamillyActivity.this);
            builder.setTitle(R.string.pick_number)
                    .setItems(R.array.covidnumber, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // The 'which' argument contains the index position
                            dialog.dismiss();
                            if (ContextCompat.checkSelfPermission(PatientFamillyActivity.this,
                                    Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                                String numbercall = number[which];
                                Intent intent = new Intent(Intent.ACTION_CALL);
                                intent.setData(Uri.parse("tel:" + numbercall));
                                startActivity(intent);
                            } else {
                                ActivityCompat.requestPermissions(PatientFamillyActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 5101);

                            }
                            // of the selected item
                        }
                    });
            Dialog d = builder.create();
            d.show();
        } else {
            ActivityCompat.requestPermissions(PatientFamillyActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 5101);
        }
    }

    private void submitData() {
        if (validation()) {

            // if ()
            ResPatientFamilyInfo resPatientInfoupdate = new ResPatientFamilyInfo();
            showProgressDialogStatic();
            resPatientInfoupdate.setName(etCustomerName.getText().toString());
            resPatientInfoupdate.setMobile(etCustomerMobile.getText().toString());
            resPatientInfoupdate.setEmail(etCustomerEmail.getText().toString());
            if (!etAge.getText().toString().equalsIgnoreCase("")) {
                resPatientInfoupdate.setAge(Integer.parseInt(etAge.getText().toString()));
            }
            resPatientInfoupdate.setGenID(selectedgender);
            resPatientInfoupdate.setRelationId(selectedrelation);
            resPatientInfoupdate.setCitizenID(resPatientInfobasic.getCitizenID());
            resPatientInfoupdate.setURoleBy(resPatientInfobasic.getCitizenID());

                resPatientInfoupdate.setCitizenFamilyPersonId(selctedidfamily);

            resPatientInfoupdate.setDOA(startdate);
            resPatientInfoupdate.setDateQurantine(enddate);
            resPatientInfoupdate.setPOOrigin(etPoorigin.getText().toString());
            resPatientInfoupdate.setPoArrival(etPoarrival.getText().toString());
            resPatientInfoupdate.setHNo(etHouseno.getText().toString());
            resPatientInfoupdate.setBuilding(etBuilding.getText().toString());
            resPatientInfoupdate.setStreet(etStreet.getText().toString());
            resPatientInfoupdate.setCity(etCity.getText().toString());
            resPatientInfoupdate.setDistrictCode(district_code);
            resPatientInfoupdate.setTalukCode(taluk_code);
            resPatientInfoupdate.setLatitude(mLocation.getLatitude());
            resPatientInfoupdate.setLongitude(mLocation.getLongitude());


            //info symtom
            resPatientInfoupdate.setFever(isfever);
            resPatientInfoupdate.setCoughSourThroat(iscoughandsour);
            resPatientInfoupdate.setBreathingProblem(isbreathing);
            resPatientInfoupdate.setDiarrhea(isdiarria);
            resPatientInfoupdate.setDiabetes(isdiabaties);
            resPatientInfoupdate.setHypertension(ishypertense);
            resPatientInfoupdate.setHeartIssue(isheartdisses);
            resPatientInfoupdate.setHIV(ishiv);

            resPatientInfoupdate.setAdditional("android");

            ReqUpdatePatentIFamilynfo info = new ReqUpdatePatentIFamilynfo();
            info.setFamilyDetails(resPatientInfoupdate);
            info.setpSecurity(getCommonApi().getSecurityObject());

            networkService.addpatentfamillyInfo(info, new NetworkService.NetworkServiceListener() {
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
                    if (response instanceof ResUpdateInfo) {
                        //getPatientinfoRepository().update(resPatientInfo);
                        if (((ResUpdateInfo) response).getFamilypersonId() != 0) {
                            resPatientInfoupdate.setCitizenFamilyPersonId(((ResUpdateInfo) response).getFamilypersonId());
                            getPatientFamillyinfoRepository().insert(resPatientInfoupdate);
                        /*PreferenceStore.getPrefernceHelperInstace().setFlag(YelligoApplication.getContext(),PreferenceStore.ISUPDATEPATENTINFO,true);
                        Intent intent = new Intent(getApplicationContext(), HomeMainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);*/
                            finish();

                            Toast.makeText(YelligoApplication.getContext(), "Successfully Updated", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
        }
    }

    private boolean validation() {


        if (mLocation == null) {
            Toast.makeText(YelligoApplication.getContext(), "Please try again later", Toast.LENGTH_LONG).show();
            return false;
        } else {
            if (mLocation.getLongitude() == 0.0) {
                Toast.makeText(YelligoApplication.getContext(), "Please try again later", Toast.LENGTH_LONG).show();
                return false;
            }

        }
        String etn=etCustomerName.getText().toString();
        if (TextUtils.isEmpty(etCustomerName.getText().toString())) {
            Toast.makeText(YelligoApplication.getContext(), "Please enter Family member Name", Toast.LENGTH_LONG).show();
            return false;
        }
        if (selectedrelation == 0) {
            Toast.makeText(YelligoApplication.getContext(), "Please select Relation ", Toast.LENGTH_LONG).show();
            return false;
        }

        if (TextUtils.isEmpty(etAge.getText().toString())){
            Toast.makeText(YelligoApplication.getContext(), "Please enter Age", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!TextUtils.isDigitsOnly(etAge.getText().toString())) {
            Toast.makeText(YelligoApplication.getContext(), "Please enter Age", Toast.LENGTH_LONG).show();
            return false;
        }

        if (TextUtils.isEmpty(etCustomerMobile.getText().toString())) {
            Toast.makeText(YelligoApplication.getContext(), "Please enter Mobile Number", Toast.LENGTH_LONG).show();
            return false;
        }

        if (etCustomerMobile.getText().toString().length() != 10) {
            showDialog("Please enter Mobile number");
            return false;
        }
        if (selctedidfamily==0) {
            if (getPatientFamillyinfoRepository().checkIsExist(etCustomerMobile.getText().toString()) != null) {
                showDialog("This mobile number already exist");
                return false;
            }
        }


        if (!TextUtils.isDigitsOnly(etCustomerMobile.getText().toString())) {
            Toast.makeText(YelligoApplication.getContext(), "Please enter Mobile Number", Toast.LENGTH_LONG).show();
            return false;
        }


        if (etCustomerMobile.getText().toString().length() != 10) {
            Toast.makeText(YelligoApplication.getContext(), "Please enter valid Mobile Number", Toast.LENGTH_LONG).show();
            return false;
        }

       /* if (startdate.equalsIgnoreCase("")) {
            Toast.makeText(YelligoApplication.getContext(), "Please enter start date", Toast.LENGTH_LONG).show();
            return false;
        }
        if (enddate.equalsIgnoreCase("")) {
            Toast.makeText(YelligoApplication.getContext(), "Please enter end date", Toast.LENGTH_LONG).show();
            return false;
        }*/

        if (selectedgender == 0) {
            Toast.makeText(YelligoApplication.getContext(), "Please select gender", Toast.LENGTH_LONG).show();
            return false;
        }
        if (district_code == 0) {
            Toast.makeText(YelligoApplication.getContext(), "Please select District", Toast.LENGTH_LONG).show();
            return false;
        }
        if (taluk_code == 0) {
            Toast.makeText(YelligoApplication.getContext(), "Please select Taluka", Toast.LENGTH_LONG).show();
            return false;
        }

        /*if (!TextUtils.isEmpty(etPoorigin.getText().toString())){
            Toast.makeText(YelligoApplication.getContext(), "Please enter Port of Origin", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!TextUtils.isEmpty(etPoarrival.getText().toString())){
            Toast.makeText(YelligoApplication.getContext(), "Please enter Port Of Arrival", Toast.LENGTH_LONG).show();
            return false;
        }*/

       /* if (!TextUtils.isEmpty(etStreet.getText().toString())) {
            Toast.makeText(YelligoApplication.getContext(), "Please enter Street Name", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!TextUtils.isEmpty(etBuilding.getText().toString())) {
            Toast.makeText(YelligoApplication.getContext(), "Please enter Building Name", Toast.LENGTH_LONG).show();
            return false;
        }
        if (!TextUtils.isEmpty(etHouseno.getText().toString())) {
            Toast.makeText(YelligoApplication.getContext(), "Please enter House Number", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!TextUtils.isEmpty(etCity.getText().toString())) {
            Toast.makeText(YelligoApplication.getContext(), "Please enter City Name", Toast.LENGTH_LONG).show();
            return false;
        }*/
        return true;
    }

    private void showDialog(String message) {
        if (!isFinishing()) {
            CustomDialogGeneric dialog = new CustomDialogGeneric(PatientFamillyActivity.this, "",
                    new CustomDialogGeneric.OnButtonClickListener() {
                        @Override
                        public void onLeftButtonClick(CustomDialogGeneric dialog) {
                            dialog.dismiss();
                            // Toast.makeText(getActivity(),"Customer created successfully",Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
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
                    findViewById(R.id.activity_main),
                    R.string.permission_rationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            ActivityCompat.requestPermissions(PatientFamillyActivity.this,
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
            ActivityCompat.requestPermissions(PatientFamillyActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
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
                    Snackbar.make(
                            findViewById(R.id.activity_main_rl),
                            R.string.permission_rationale,
                            Snackbar.LENGTH_INDEFINITE)
                            .setAction(R.string.ok, new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    // Request permission
                                    ActivityCompat.requestPermissions(PatientFamillyActivity.this,
                                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                            REQUEST_PERMISSIONS_REQUEST_CODE);
                                }
                            })
                            .show();
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case 5101: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    number = getResources().getStringArray(R.array.covidnumber);
                    AlertDialog.Builder builder = new AlertDialog.Builder(PatientFamillyActivity.this);
                    builder.setTitle(R.string.pick_number)
                            .setItems(R.array.covidnumber, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // The 'which' argument contains the index position
                                    if (ContextCompat.checkSelfPermission(PatientFamillyActivity.this,
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
                                    ActivityCompat.requestPermissions(PatientFamillyActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 5101);
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
