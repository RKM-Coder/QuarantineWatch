package com.goalsr.homequarantineTracker.db.repository;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;


import androidx.lifecycle.LiveData;

import com.goalsr.homequarantineTracker.db.YellligoRoomDatabase;
import com.goalsr.homequarantineTracker.db.dao.TravelTrackingDao;
import com.goalsr.homequarantineTracker.db.model.QHTracker;

import java.util.List;

public class TravelTrackingRepository {

    private TravelTrackingDao mDao;
    private LiveData<List<QHTracker>> mListLiveData;
    private List<QHTracker> mList;

    public TravelTrackingRepository(Application application) {
        YellligoRoomDatabase db = YellligoRoomDatabase.getDataBase(application);
        this.mDao = db.travelTrackingDao();
        //mListLiveData = mDao.getListAllItem();
    }
    public TravelTrackingRepository(Context application) {
        YellligoRoomDatabase db = YellligoRoomDatabase.getDataBase(application);
        this.mDao = db.travelTrackingDao();
        //mListLiveData = mDao.getListAllItem();
    }

    public void insert(List<QHTracker> value) {

        new InsertAsynctaskList(mDao, value).execute();
        // mDao.insertItem(value);
    }

    public void insert(QHTracker value) {

        new InsertAsynctask(mDao, value).execute();
        // mDao.insertItem(value);
    }

    public List<QHTracker> getTravelListNonSync(){
        return mDao.getListAllItemNonSync(false);
    }

    public List<String> getTravelListNonSyncImage(){
        return mDao.getListAllItemNonSyncImage(false);
    }

    public void update(QHTracker value) {

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

    public QHTracker getItem(String checkinid) {

        return mDao.getItemById(checkinid);
    }

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

    public LiveData<List<QHTracker>> getAllItemLivedata() {

        return mDao.getListAllItem();
    }

    private class InsertAsynctask extends AsyncTask<Void, Void, Void> {
        QHTracker value;
        TravelTrackingDao mDao;

        public InsertAsynctask(TravelTrackingDao mDao, QHTracker value) {
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
        List<QHTracker> value;
        TravelTrackingDao mDao;

        public InsertAsynctaskList(TravelTrackingDao mDao, List<QHTracker> value) {
            this.value = value;
            this.mDao = mDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (QHTracker item : value) {
                mDao.insertItem(item);
            }

            return null;
        }
    }

    private class UpdateAsynctask extends AsyncTask<Void, Void, Void> {
        QHTracker value;
        TravelTrackingDao mDao;

        public UpdateAsynctask(TravelTrackingDao mDao, QHTracker value) {
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

        TravelTrackingDao mDao;
        boolean status;
        String id;
        String today;

        public UpdateAsynctask2(TravelTrackingDao mDao, boolean status, String id) {
            this.mDao = mDao;
            this.status = status;
            this.id = id;
            this.today = today;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            mDao.update(status,id);
            return null;
        }
    }

    private class UpdateAsynctaskimgestatus extends AsyncTask<Void, Void, Void> {

        TravelTrackingDao mDao;
        boolean status;
        String filename;
        String today;

        public UpdateAsynctaskimgestatus(TravelTrackingDao mDao, boolean status, String filename) {
            this.mDao = mDao;
            this.status = status;
            this.filename = filename;
            this.today = today;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            mDao.updateimgestatus(status,filename);
            return null;
        }
    }
}
