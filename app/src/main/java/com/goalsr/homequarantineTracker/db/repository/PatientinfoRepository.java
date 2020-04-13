package com.goalsr.homequarantineTracker.db.repository;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.goalsr.homequarantineTracker.db.YellligoRoomDatabase;
import com.goalsr.homequarantineTracker.db.dao.PatientInfoDao;
import com.goalsr.homequarantineTracker.db.dao.PatientInfoDao;
import com.goalsr.homequarantineTracker.db.model.QHTracker;
import com.goalsr.homequarantineTracker.resposemodel.getPatientinfo.ResPatientInfo;

import java.util.List;

public class PatientinfoRepository {

    private PatientInfoDao mDao;
    private LiveData<List<QHTracker>> mListLiveData;
    private List<QHTracker> mList;

    public PatientinfoRepository(Application application) {
        YellligoRoomDatabase db = YellligoRoomDatabase.getDataBase(application);
        this.mDao = db.patientInfoDao();
        //mListLiveData = mDao.getListAllItem();
    }
    public PatientinfoRepository(Context application) {
        YellligoRoomDatabase db = YellligoRoomDatabase.getDataBase(application);
        this.mDao = db.patientInfoDao();
        //mListLiveData = mDao.getListAllItem();
    }

    public void insert(List<ResPatientInfo> value) {

       new InsertAsynctaskList(mDao, value).execute();
        // mDao.insertItem(value);
    }

    public void insert(ResPatientInfo value) {

        new InsertAsynctask(mDao, value).execute();
        // mDao.insertItem(value);
    }

    public ResPatientInfo getPatientInfo(int cid){
        return mDao.getListAllItem(cid);
    }

    public List<ResPatientInfo> getListAllItemByAdmin(){
        return mDao.getListAllItemByAdmin();
    }

    public LiveData<List<ResPatientInfo>> getListAllItemByAdminLivedata(){
        return mDao.getListAllItemByAdminLivedata();
    }

   /* public List<String> getTravelListNonSyncImage(){
        return mDao.getListAllItemNonSyncImage(false);
    }*/

    public void update(ResPatientInfo value) {

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

   /* public ResPatientInfo getItem(String checkinid) {

        return mDao.getItemById(checkinid);
    }*/

    public void clear() {
        mDao.clearTable();
    }

    public void clearbyID(String id) {
        mDao.clearTable();
    }



   /* public List<LocationTrackingModel> getAllItem(){

        return mDao.getListAll();
    }
    public List<LocationTrackingModel> getListAllTaskComplete(){

        return mDao.getListAllTaskComplete("3");
    }*/

    /*public LiveData<List<QHTracker>> getAllItemLivedata() {

        return mDao.getListAllItem();
    }*/

    private class InsertAsynctask extends AsyncTask<Void, Void, Void> {
        ResPatientInfo value;
        PatientInfoDao mDao;

        public InsertAsynctask(PatientInfoDao mDao, ResPatientInfo value) {
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
        List<ResPatientInfo> value;
        PatientInfoDao mDao;

        public InsertAsynctaskList(PatientInfoDao mDao, List<ResPatientInfo> value) {
            this.value = value;
            this.mDao = mDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (ResPatientInfo item : value) {
                mDao.insertItem(item);
            }

            return null;
        }
    }

    private class UpdateAsynctask extends AsyncTask<Void, Void, Void> {
        ResPatientInfo value;
        PatientInfoDao mDao;

        public UpdateAsynctask(PatientInfoDao mDao, ResPatientInfo value) {
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

        PatientInfoDao mDao;
        boolean status;
        String id;
        String today;

        public UpdateAsynctask2(PatientInfoDao mDao, boolean status, String id) {
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

        PatientInfoDao mDao;
        boolean status;
        String filename;
        String today;

        public UpdateAsynctaskimgestatus(PatientInfoDao mDao, boolean status, String filename) {
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
