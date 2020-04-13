package com.goalsr.homequarantineTracker.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.goalsr.homequarantineTracker.db.model.QHTracker;
import com.goalsr.homequarantineTracker.resposemodel.getPatientinfo.ResPatientInfo;

import java.util.List;

@Dao
public interface PatientInfoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(List<ResPatientInfo> ndhColorList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItem(ResPatientInfo item);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateItem(ResPatientInfo item);

    @Query("SELECT * from patient_info where CitizenID =:cid")
    ResPatientInfo getListAllItem(int cid);


    @Query("SELECT * from patient_info  where name not null order by Name ASC")
    List<ResPatientInfo> getListAllItemByAdmin();

    @Query("SELECT * from patient_info where name not null order by Name ASC")
    LiveData<List<ResPatientInfo>> getListAllItemByAdminLivedata();


    /*@Query("SELECT * from qh_travel_tracking where syncstutas= :status")
    List<QHTracker> getListAllItemNonSync(boolean status);

    @Query("SELECT selfifilepathlocal from qh_travel_tracking where syncstatusimage= :status and selfifilepathlocal not NULL")
    List<String> getListAllItemNonSyncImage(boolean status);

    @Query("SELECT * from qh_travel_tracking")
    List<QHTracker> getListAll();

    @Query("SELECT * FROM qh_travel_tracking where primary_id = :id")
    public QHTracker getItemById(String id);

    @Query("UPDATE qh_travel_tracking SET syncstutas = :synstatus  WHERE primary_id =:id ")
    void update(boolean synstatus, String id);

    @Query("UPDATE qh_travel_tracking SET syncstatusimage = :synstatus  WHERE selfifilepathlocal =:filename ")
    void updateimgestatus(boolean synstatus, String filename);*/

    //Clear DB DATA
    @Query("DELETE FROM patient_info")
    public void clearTable();
    //Clear perticular raw
    @Query("DELETE FROM patient_info where CitizenID = :id")
    public void clearTableByid(String id);
}
