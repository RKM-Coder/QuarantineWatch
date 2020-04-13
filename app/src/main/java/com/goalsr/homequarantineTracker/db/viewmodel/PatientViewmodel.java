package com.goalsr.homequarantineTracker.db.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.goalsr.homequarantineTracker.db.dao.PatientInfoDao;
import com.goalsr.homequarantineTracker.db.repository.PatientinfoRepository;
import com.goalsr.homequarantineTracker.resposemodel.getPatientinfo.ResPatientInfo;

import java.util.List;

public class PatientViewmodel extends AndroidViewModel {
    private PatientinfoRepository patientinfoRepository;
    private LiveData<List<ResPatientInfo>> liveData;
    public PatientViewmodel(@NonNull Application application) {
        super(application);
        patientinfoRepository=new PatientinfoRepository(application);
        liveData=patientinfoRepository.getListAllItemByAdminLivedata();

    }

    public LiveData<List<ResPatientInfo>> getLivedatPAtient(){
        return liveData;
    }
}
