package com.goalsr.homequarantineTracker.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import com.amitshekhar.DebugDB;
import com.goalsr.homequarantineTracker.R;
import com.goalsr.homequarantineTracker.Utils.PreferenceStore;
import com.goalsr.homequarantineTracker.YelligoApplication;
import com.goalsr.homequarantineTracker.adapter.DistrictListAdapter;
import com.goalsr.homequarantineTracker.adapter.FamillyListAdapter;
import com.goalsr.homequarantineTracker.apiservice.NetworkService;
import com.goalsr.homequarantineTracker.base.BaseActivity;
import com.goalsr.homequarantineTracker.resposemodel.DistrictModel;
import com.goalsr.homequarantineTracker.resposemodel.ReqPAtientInfoByAdmin;
import com.goalsr.homequarantineTracker.resposemodel.getPatientinfo.ResPatientFamilyInfo;
import com.goalsr.homequarantineTracker.resposemodel.getPatientinfo.ResPatientInfo;
import com.goalsr.homequarantineTracker.resposemodel.getPatientinfo.ResPatientInfoByAdmin;
import com.goalsr.homequarantineTracker.view.edittext.CustomEditText;
import com.goalsr.homequarantineTracker.view.edittext.DrawableClickListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DistrictListActivity extends BaseActivity implements DistrictListAdapter.OnClickMainView {

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
    @BindView(R.id.et_searchview)
    CustomEditText etSearchview;
    @BindView(R.id.img_calender)
    ImageView imgCalender;
    @BindView(R.id.search_layout)
    LinearLayout searchLayout;
    @BindView(R.id.rv_view_famillly)
    RecyclerView rvViewFamillly;
    private ArrayList<DistrictModel> listOFdistrict;
    private DistrictListAdapter adapter;
    private NetworkService networkService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district_list);
        ButterKnife.bind(this);
        initMvp();
        initrecyclerView();
        getDistrict();
       // Log.e("DBaddress", DebugDB.getAddressLog());
        etSearchview.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

        etSearchview.setDrawableClickListener(new DrawableClickListener() {
            @Override
            public void onClick(DrawablePosition target) {
                switch (target) {
                    case RIGHT:
                        etSearchview.setText("");
                        break;

                    default:
                        break;
                }
            }
        });
    }

    private void initrecyclerView() {
        adapter = new DistrictListAdapter(this, new ArrayList<DistrictModel>());
        adapter.setOnclickListener(this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvViewFamillly.setLayoutManager(manager);
        rvViewFamillly.setAdapter(adapter);
    }


    private void getDistrict() {
        String jsonFileString = getCommonApi().getJsonFromAssets(getApplicationContext(), "District.json");
        Log.i("data", jsonFileString);

        Gson gson = new Gson();
        Type listUserType = new TypeToken<List<DistrictModel>>() {
        }.getType();
        listOFdistrict = new ArrayList<>();
        listOFdistrict = gson.fromJson(jsonFileString, listUserType);
        /*DistrictModel item = new DistrictModel();
        item.setDISTRICT_NAME(" Select District");
        item.setDistrict_code(-1);
        listOFdistrict.add(0, item);*/
        Collections.sort(listOFdistrict, new Comparator<DistrictModel>() {
            @Override
            public int compare(DistrictModel s1, DistrictModel s2) {
                return s1.getDISTRICT_NAME().compareTo(s2.getDISTRICT_NAME());
            }
        });
        adapter.addall(listOFdistrict);
        //makeSpinnerDistrictMethod((ArrayList<DistrictModel>) listOFdistrict);


    }

    private void initMvp() {
        networkService = new NetworkService();
        networkService.inject(DistrictListActivity.this);
    }

    private void getPatientInfo() {
        showProgressDialogStatic();
        ReqPAtientInfoByAdmin reqPatient = new ReqPAtientInfoByAdmin();
      /*  int cId = PreferenceStore.getPrefernceHelperInstace().getIntValue(YelligoApplication.getContext(), PreferenceStore.CITIZEN_ID);
        reqPatient.setCitizenId(cId);
        reqPatient.setLevel(2);*/
        Log.e("PatientList",PreferenceStore.getPrefernceHelperInstace().getIntValue(YelligoApplication.getContext(),PreferenceStore.DISTRICT_ID)+"");
        reqPatient.setDistCode(PreferenceStore.getPrefernceHelperInstace().getIntValue(YelligoApplication.getContext(),PreferenceStore.DISTRICT_ID));
        reqPatient.setpSecurity(getCommonApi().getSecurityObject());
        networkService.getPatientInfoListByAdmin(reqPatient, new NetworkService.NetworkServiceListener() {
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
                //requestPermissions();
                if (response instanceof ResPatientInfoByAdmin) {

                    // getPatientinfoRepository().insert((ResPatientInfo) response);
                    Intent intent = new Intent(getApplicationContext(), AdminPatientLsitActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finishAffinity();
                }
            }
        });

    }

    @Override
    public void onInternetConnectivityChanged(boolean isConnected) {

    }

    @Override
    public void onClickMain(int position, DistrictModel item) {
        getPatientinfoRepository().clear();

        PreferenceStore.getPrefernceHelperInstace().setIntValue(YelligoApplication.getContext(),PreferenceStore.DISTRICT_ID,item.getDistrict_code());
        PreferenceStore.getPrefernceHelperInstace().setString(YelligoApplication.getContext(),PreferenceStore.DISTRICT_NAME,item.getDISTRICT_NAME());
        if (getCommonApi().isInternetAvailable(DistrictListActivity.this)){
            getPatientInfo();
        }else {
            Toast.makeText(YelligoApplication.getContext(),"Please enable internet connection",Toast.LENGTH_LONG).show();
        }


    }
}
