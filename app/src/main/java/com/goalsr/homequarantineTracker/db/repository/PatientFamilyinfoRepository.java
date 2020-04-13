package com.goalsr.homequarantineTracker.db.repository;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.goalsr.homequarantineTracker.db.YellligoRoomDatabase;
import com.goalsr.homequarantineTracker.db.dao.PatientFamilyInfoDao;
import com.goalsr.homequarantineTracker.db.model.QHTracker;
import com.goalsr.homequarantineTracker.resposemodel.getPatientinfo.ResPatientFamilyInfo;

import java.util.List;

public class PatientFamilyinfoRepository {

    private PatientFamilyInfoDao mDao;
    private LiveData<List<QHTracker>> mListLiveData;
    private List<QHTracker> mList;

    public PatientFamilyinfoRepository(Application application) {
        YellligoRoomDatabase db = YellligoRoomDatabase.getDataBase(application);
        this.mDao = db.patientFamilyInfoDao();
        //mListLiveData = mDao.getListAllItem();
    }
    public PatientFamilyinfoRepository(Context application) {
        YellligoRoomDatabase db = YellligoRoomDatabase.getDataBase(application);
        this.mDao = db.patientFamilyInfoDao();
        //mListLiveData = mDao.getListAllItem();
    }

    public void insert(List<ResPatientFamilyInfo> value) {

       new InsertAsynctaskList(mDao, value).execute();
        // mDao.insertItem(value);
    }

    public void insert(ResPatientFamilyInfo value) {

        new InsertAsynctask(mDao, value).execute();
        // mDao.insertItem(value);
    }

    public List<ResPatientFamilyInfo> getPatientFamilyInfo(){
        return mDao.getListAllItem();
    }public ResPatientFamilyInfo getPatientFamilyInfo(int id){
        return mDao.getListAllItem(id);
    }

   /* public List<String> getTravelListNonSyncImage(){
        return mDao.getListAllItemNonSyncImage(false);
    }*/

    public void update(ResPatientFamilyInfo value) {

        new UpdateAsynctask(mDao, value).execute();
        // mDao.insertItem(value);
    }

    public void updatesyncdata(boolean status, String id) {

        new UpdateAsynctask2(mDao, status, id).execute();
        // mDao.insertItem(value);
    }

    public void updatesyncdataimagestatus(boolean status, String filename) {

        new UpdateAsynctaskimgestatus(mDao, status, filename).execute();
        // mDao.insertItem(value);
    }

   /* public ResPatientFamilyInfo getItem(String checkinid) {

        return mDao.getItemById(checkinid);
    }*/

    public void clear() {
        mDao.clearTable();
    }

    public void clearbyID(String id) {
        mDao.clearTable();
    }

    public ResPatientFamilyInfo checkIsExist(String mob) {

        return mDao.getItemMobileNoExist(mob);
    }



   /* public List<LocationTrackingModel> getAllItem(){

        return mDao.getListAll();
    }
    public List<LocationTrackingModel> getListAllTaskComplete(){

        return mDao.getListAllTaskComplete("3");
    }*/

    public LiveData<List<ResPatientFamilyInfo>> getListAllItemLivedata() {

        return mDao.getListAllItemLivedata();
    }

    private class InsertAsynctask extends AsyncTask<Void, Void, Void> {
        ResPatientFamilyInfo value;
        PatientFamilyInfoDao mDao;

        public InsertAsynctask(PatientFamilyInfoDao mDao, ResPatientFamilyInfo value) {
            this.value = value;
            this.mDao = mDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mDao.insertItem(value);
            return null;
        }
    }

    private class InsertAsynctaskList extends AsyncTask<Void, Void, Void> {
        List<ResPatientFamilyInfo> value;
        PatientFamilyInfoDao mDao;

        public InsertAsynctaskList(PatientFamilyInfoDao mDao, List<ResPatientFamilyInfo> value) {
            this.value = value;
            this.mDao = mDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (ResPatientFamilyInfo item : value) {
                mDao.insertItem(item);
            }

            return null;
        }
    }

    private class UpdateAsynctask extends AsyncTask<Void, Void, Void> {
        ResPatientFamilyInfo value;
        PatientFamilyInfoDao mDao;

        public UpdateAsynctask(PatientFamilyInfoDao mDao, ResPatientFamilyInfo value) {
            this.value = value;
            this.mDao = mDao;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            mDao.updateItem(value);
            return null;
        }
    }

    private class UpdateAsynctask2 extends AsyncTask<Void, Void, Void> {

        PatientFamilyInfoDao mDao;
        boolean status;
        String id;
        String today;

        public UpdateAsynctask2(PatientFamilyInfoDao mDao, boolean status, String id) {
            this.mDao = mDao;
            this.status = status;
            this.id = id;
            this.today = today;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            //mDao.update(status,id);
            return null;
        }
    }

    private class UpdateAsynctaskimgestatus extends AsyncTask<Void, Void, Void> {

        PatientFamilyInfoDao mDao;
        boolean status;
        String filename;
        String today;

        public UpdateAsynctaskimgestatus(PatientFamilyInfoDao mDao, boolean status, String filename) {
            this.mDao = mDao;
            this.status = status;
            this.filename = filename;
            this.today = today;
        }


        @Override
        protected Void doInBackground(Void... voids) {
           // mDao.updateimgestatus(status,filename);
            return null;
        }
    }
}
