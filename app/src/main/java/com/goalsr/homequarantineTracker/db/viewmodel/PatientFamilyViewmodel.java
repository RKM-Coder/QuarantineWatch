package com.goalsr.homequarantineTracker.db.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.goalsr.homequarantineTracker.db.repository.PatientFamilyinfoRepository;
import com.goalsr.homequarantineTracker.db.repository.PatientinfoRepository;
import com.goalsr.homequarantineTracker.resposemodel.getPatientinfo.ResPatientFamilyInfo;
import com.goalsr.homequarantineTracker.resposemodel.getPatientinfo.ResPatientInfo;

import java.util.List;

public class PatientFamilyViewmodel extends AndroidViewModel {
    private PatientFamilyinfoRepository patientinfoRepository;
    private LiveData<List<ResPatientFamilyInfo>> liveData;
    public PatientFamilyViewmodel(@NonNull Application application) {
        super(application);
        patientinfoRepository=new PatientFamilyinfoRepository(application);
        liveData=patientinfoRepository.getListAllItemLivedata();

    }

    public LiveData<List<ResPatientFamilyInfo>> getLivedatPAtient(){
        return liveData;
    }
}
