package com.goalsr.homequarantineTracker.db;

import android.content.Context;


import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.goalsr.homequarantineTracker.db.dao.PatientFamilyInfoDao;
import com.goalsr.homequarantineTracker.db.dao.PatientInfoDao;
import com.goalsr.homequarantineTracker.db.dao.TravelTrackingDao;
import com.goalsr.homequarantineTracker.db.model.QHTracker;
import com.goalsr.homequarantineTracker.resposemodel.getPatientinfo.ResPatientFamilyInfo;
import com.goalsr.homequarantineTracker.resposemodel.getPatientinfo.ResPatientInfo;


/**
 * Created by ramkrishna on 11/7/18.
 */
@Database(entities = { QHTracker.class, ResPatientInfo.class, ResPatientFamilyInfo.class}, version = 2,exportSchema = false)
public abstract class YellligoRoomDatabase extends RoomDatabase {

    private static YellligoRoomDatabase INSTANCE;
    private static String DB_NAME="aragya_master";


    public abstract TravelTrackingDao travelTrackingDao();
    public abstract PatientInfoDao patientInfoDao();
    public abstract PatientFamilyInfoDao patientFamilyInfoDao();


    public static YellligoRoomDatabase getDataBase(final Context context){

        if (INSTANCE==null){
            synchronized (YellligoRoomDatabase.class){

                INSTANCE= Room.databaseBuilder(context.getApplicationContext(),YellligoRoomDatabase.class,DB_NAME)
                        .allowMainThreadQueries()
                        .build();

            }
        }

        return INSTANCE;
    }

    /*static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE `other_expense_mode` (`_id` TEXT NOT NULL, "
                    + "`name` TEXT,`companyId` TEXT, `isActive` TEXT, PRIMARY KEY(`_id`))");

            database.execSQL("ALTER TABLE add_hoc_visit_list_table "
                    + "ADD COLUMN createdBy TEXT");

            database.execSQL("ALTER TABLE add_hoc_visit_list_table "
                    + "ADD COLUMN distance TEXT");

            database.execSQL("ALTER TABLE add_hoc_visit_list_table "
                    + "ADD COLUMN distanceTravelled TEXT");

            database.execSQL("ALTER TABLE visit_list_table "
                    + "ADD COLUMN createdBy TEXT");

            database.execSQL("ALTER TABLE visit_list_table "
                    + "ADD COLUMN distance TEXT");

            database.execSQL("ALTER TABLE visit_list_table "
                    + "ADD COLUMN distanceTravelled TEXT");

            database.execSQL("ALTER TABLE daycheck_in_out_table "
                    + "ADD COLUMN distanceTravelled TEXT");



            database.execSQL("ALTER TABLE location_tracking "
                    + "ADD COLUMN visit_id TEXT");

            database.execSQL("ALTER TABLE location_tracking "
                    + "ADD COLUMN cus_id TEXT");

            database.execSQL("ALTER TABLE location_tracking "
                    + "ADD COLUMN checkintime TEXT");

            database.execSQL("ALTER TABLE location_tracking "
                    + "ADD COLUMN cust_name TEXT");

            database.execSQL("ALTER TABLE location_tracking "
                    + "ADD COLUMN cust_address TEXT");

            database.execSQL("ALTER TABLE location_tracking "
                    + "ADD COLUMN checkstatus TEXT");

            database.execSQL("ALTER TABLE location_tracking "
                    + "ADD COLUMN cus_type TEXT");

            database.execSQL("ALTER TABLE location_tracking "
                    + "ADD COLUMN loc_desc TEXT");

            database.execSQL("ALTER TABLE visit_list_table "
                    + "ADD COLUMN assignedTo TEXT");

            database.execSQL("ALTER TABLE add_hoc_visit_list_table "
                    + "ADD COLUMN assignedTo TEXT");

        }
    };*/
    /*static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {





        }
    };*/

}
