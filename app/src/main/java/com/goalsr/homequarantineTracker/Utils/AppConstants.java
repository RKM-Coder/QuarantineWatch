package com.goalsr.homequarantineTracker.Utils;

import android.Manifest;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class AppConstants {
    public static final int GPS_REQUEST = 5001;
    private static String TIME_ZONE = "GMT";
    public static  boolean isGPS = false;
    public final static String myPermissions[] = {
            android.Manifest.permission.INTERNET,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,

            // android.Manifest.permission.WRITE_SETTINGS,
            //android.Manifest.permission.ACCESS_NETWORK_STATE,
            // android.Manifest.permission.LOCATION_HARDWARE,
            android.Manifest.permission.READ_PHONE_STATE,
//            android.Manifest.permission.CALL_PHONE,
//            android.Manifest.permission.GET_ACCOUNTS,
            // Manifest.permission.READ_CONTACTS,
            //Manifest.permission.RECEIVE_SMS,
            // Manifest.permission.READ_SMS,
            android.Manifest.permission.CAMERA,
    };

//    public static final String LOGIN = "login_key";
    public static final String APP_TOKEN = "app_token";
    public static final String FIREBASE_TOKEN = "firebase_token";
    public static final String APP_ROLLID = "app_rollid";
    public static final String APP_USERID = "app_userid";
    public static final String APP_EMAILID = "app_emailid";
    public static final String APP_NAME = "app_name";
    public static final String APP_MOB_NUMBER = "app_mobile_number";
    public static final String APP_PROFILE_PIC = "app_profile_pic";
    public static final String FIRST_TIME = "FirstTime";
    public static final String CART_QTY = "cart_qty";
    public static final String cartSize = "0";

    public static final String MERCHANT_ID = "5338430";
    public static final String TRACKTAG = "workertrack";

    //public static DataCart dataCart = null;

    public  static String getCurrentDate() {

        Date c = Calendar.getInstance().getTime();
        //System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(c);
        //
        return formattedDate;
    }

    public static String dateFormatChangerGVT(String date_time) {
        String inputPattern = "yyyy-MM-dd'T'HH:mm:ss";
        String outputPattern = "dd MMM yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = "";

        try {
            date = inputFormat.parse(date_time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return str;
    }


    public static String getCurrentDateTime(){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        return currentDateandTime;
    }

    public static String getCurrentDateTimeGVT(){

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        return currentDateandTime;
    }


    public static long getLastStausUpdatetimdif(String lasttimestatusupdate){
        String dateStart = getCurrentDateTime();
        String dateStop = lasttimestatusupdate;

        //HH converts hour in 24 hours format (0-23), day calculation
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date d1 = null;
        Date d2 = null;

        try {
            d1 = format.parse(dateStart);
            d2 = format.parse(dateStop);

            //in milliseconds
            long diff = d2.getTime() - d1.getTime();

            long diffSeconds = diff / 1000 % 60;
            long diffMinutes = diff / (60 * 1000) % 60;
            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);

            System.out.print(diffDays + " days, ");
            System.out.print(diffHours + " hours, ");
            System.out.print(diffMinutes + " minutes, ");
            System.out.print(diffSeconds + " seconds.");
            return diffMinutes;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static int findMaxSnap(ArrayList<Integer> listsnapcount) {


        return Collections.max(listsnapcount);
    }

    public static String getJulianConverter(String inputFormat, String inputDate) {
        String formattedDate = null;


        if (inputDate != null && inputFormat != null) {

            try {

                SimpleDateFormat sdf = new SimpleDateFormat(inputFormat, Locale.getDefault());
                sdf.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));

                Date inputData = sdf.parse(inputDate);

                Calendar cal = Calendar.getInstance();

                cal.setTimeZone(TimeZone.getTimeZone(TIME_ZONE));

                cal.setTime(inputData);

                int year = cal.get(Calendar.YEAR);
                int day = cal.get(Calendar.DAY_OF_YEAR);
                int hourOfTheDay = cal.get(Calendar.HOUR_OF_DAY);
                int minutes = cal.get(Calendar.MINUTE);
                int seconds = cal.get(Calendar.SECOND);
                int miliseconds = cal.get(Calendar.MILLISECOND);

                String yearString = String.valueOf(year);
                String dayString = String.format("%03d", day);
                String hourString = String.format("%02d", hourOfTheDay);
                String minuteString = String.format("%02d", minutes);
                String secondString = String.format("%02d", seconds);
                String millisecondString = String.format("%03d", miliseconds);


                formattedDate = yearString + dayString + hourString + minuteString + secondString + millisecondString;

                /*formattedDate = "" + year + "" +dayOfTheYear + "" + hourOfTheDay + "" + minutes + "" + seconds + "" + miliseconds;
                formattedDate = cal.get(Calendar.YEAR)

                        + String.format("%03d", cal.get(Calendar.DAY_OF_YEAR))

                        + String.format("%02d", cal.get(Calendar.HOUR_OF_DAY))

                        + String.format("%02d", cal.get(Calendar.MINUTE)) + ""

                        + String.format("%02d", cal.get(Calendar.SECOND)) + ""

                        + String.format("%03d", cal.get(Calendar.MILLISECOND));*/


            } catch (Exception e) {

                //logger.error("Error due to - " + e, e);

            }


        }
        return formattedDate;
    }

    public static String getJulianDateTimeNow() {

        String dateString = "";

        try {

            Date time = Calendar.getInstance().getTime();

            SimpleDateFormat outputFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            outputFmt.setTimeZone(TimeZone.getTimeZone("GMT"));
            dateString = outputFmt.format(time);
            String gmtDateTimeString = outputFmt.format(time);
            Date gmtDate = outputFmt.parse(gmtDateTimeString);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(gmtDate);
            calendar.setTimeZone(TimeZone.getTimeZone("GMT"));

            int year = calendar.get(Calendar.YEAR);
            int day = calendar.get(Calendar.DAY_OF_YEAR);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            int second = calendar.get(Calendar.SECOND);
            int milliSecond =  calendar.get(Calendar.MILLISECOND);

            String yearString = String.valueOf(year);
            String dayString = String.format("%03d", day);
            String hourString = String.format("%02d", hour);
            String minuteString = String.format("%02d", minute);
            String secondString = String.format("%02d", second);
            String millisecondString = String.format("%03d", milliSecond);


            dateString = yearString + dayString + hourString + minuteString + secondString + millisecondString;


        } catch (ParseException e) {

// TODO Auto-generated catch block

            e.printStackTrace();

        }

        return dateString;

    }

    public static String getCHourTimeNow() {

        String dateString = "";

        try {

            Date time = Calendar.getInstance().getTime();

            SimpleDateFormat outputFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            outputFmt.setTimeZone(TimeZone.getTimeZone("GMT"));
            dateString = outputFmt.format(time);
            String gmtDateTimeString = outputFmt.format(time);
            Date gmtDate = outputFmt.parse(gmtDateTimeString);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(gmtDate);
            calendar.setTimeZone(TimeZone.getTimeZone("GMT"));

            int year = calendar.get(Calendar.YEAR);
            int day = calendar.get(Calendar.DAY_OF_YEAR);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            int second = calendar.get(Calendar.SECOND);
            int milliSecond =  calendar.get(Calendar.MILLISECOND);

            String yearString = String.valueOf(year);
            String dayString = String.format("%03d", day);
            String hourString = String.format("%02d", hour);
            String minuteString = String.format("%02d", minute);
            String secondString = String.format("%02d", second);
            String millisecondString = String.format("%03d", milliSecond);


            dateString = hourString + minuteString ;


        } catch (ParseException e) {

// TODO Auto-generated catch block

            e.printStackTrace();

        }

        return dateString;

    }



    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lon1 - lon2);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double dist = (double) (earthRadius * c);

        return dist;

    }




}
