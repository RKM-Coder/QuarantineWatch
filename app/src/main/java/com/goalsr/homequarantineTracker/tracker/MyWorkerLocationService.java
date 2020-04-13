package com.goalsr.homequarantineTracker.tracker;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.goalsr.homequarantineTracker.R;
import com.goalsr.homequarantineTracker.Utils.AppConstants;
import com.goalsr.homequarantineTracker.Utils.CommonApi;
import com.goalsr.homequarantineTracker.Utils.PreferenceStore;
import com.goalsr.homequarantineTracker.YelligoApplication;
import com.goalsr.homequarantineTracker.apiservice.ApiBackGround;
import com.goalsr.homequarantineTracker.db.model.QHTracker;
import com.goalsr.homequarantineTracker.db.repository.TravelTrackingRepository;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MyWorkerLocationService extends Worker {

	private static final String DEFAULT_START_TIME = "08:00";
	private static final String DEFAULT_END_TIME = "22:00";

	private static final String TAG = "MyWorker";

	/**
	 * The desired interval for location updates. Inexact. Updates may be more or less frequent.
	 */
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

	private Context mContext;
	private String userID="12222";
	/**
	 * Callback for changes in location.
	 */
	private LocationCallback mLocationCallback;
	TravelTrackingRepository travelTrackingRepository;
	ApiBackGround apiBackGround;
	public MyWorkerLocationService(@NonNull Context context, @NonNull WorkerParameters workerParams) {
		super(context, workerParams);
		mContext = context;
		apiBackGround=new ApiBackGround(mContext);
		//userID= PreferenceStore.getPrefernceHelperInstace().getString(mContext,PreferenceStore.CITIZEN_ID);
		travelTrackingRepository =new TravelTrackingRepository(context);
	}

	@NonNull
	@Override
	public Result doWork() {
		Log.d(TAG, "doWork: Done");

		Log.d(TAG, "onStartJob: STARTING JOB..");

		DateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

		Calendar c = Calendar.getInstance();
		Date date = c.getTime();
		String formattedDate = dateFormat.format(date);

		try {
			Date currentDate = dateFormat.parse(formattedDate);
			Date startDate = dateFormat.parse(DEFAULT_START_TIME);
			Date endDate = dateFormat.parse(DEFAULT_END_TIME);

			/*if (currentDate.after(startDate) && currentDate.before(endDate)) {*/
				mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext);
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
										PreferenceStore.getPrefernceHelperInstace().setString(mContext,PreferenceStore.USER_LATITUDE,mLocation.getLatitude()+"");
										PreferenceStore.getPrefernceHelperInstace().setString(mContext,PreferenceStore.USER_LONGITUDE,mLocation.getLongitude()+"");
										Log.d(TAG, "LocationWORK : " + mLocation.getLongitude()+"----"+mLocation.getLatitude()+"---"+AppConstants.getCurrentDateTime());

										addintodb(mLocation);

										// Create the NotificationChannel, but only on API 26+ because
										// the NotificationChannel class is new and not in the support library
										if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
											CharSequence name = mContext.getString(R.string.app_name);
											String description = mContext.getString(R.string.app_name);
											int importance = NotificationManager.IMPORTANCE_HIGH;
											NotificationChannel channel = new NotificationChannel(mContext.getString(R.string.app_name), name, importance);
											channel.setDescription(description);
											// Register the channel with the system; you can't change the importance
											// or other notification behaviors after this
											NotificationManager notificationManager = mContext.getSystemService(NotificationManager.class);
											notificationManager.createNotificationChannel(channel);
										}

										NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext, mContext.getString(R.string.app_name))
												.setSmallIcon(android.R.drawable.ic_menu_mylocation)
												.setContentTitle("Arogya Mitra")
												.setContentText("Arogya Mitra Tracking "/* + getCompleteAddressString(mLocation.getLatitude(), mLocation.getLongitude())*/)
												.setPriority(NotificationCompat.PRIORITY_DEFAULT)
												.setStyle(new NotificationCompat.BigTextStyle().bigText("Arogya Mitra Tracking. "/*+ AppConstants.getCurrentDateTime()*//*+ getCompleteAddressString(mLocation.getLatitude(), mLocation.getLongitude())*/));

										NotificationManagerCompat notificationManager = NotificationManagerCompat.from(mContext);

										// notificationId is a unique int for each notification that you must define
										//notificationManager.notify(1001, builder.build());

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
		} catch (ParseException ignored) {

		}

		return Result.success();
	}

	private void addintodb(Location mLocation) {
		QHTracker travelTracking = new QHTracker();
		travelTracking.setPrimary_id(""+userID+"_"+AppConstants.getJulianDateTimeNow());
		travelTracking.setMcurrentdatetimejulian(AppConstants.getJulianDateTimeNow());
		travelTracking.setMcurrenttime(AppConstants.getCHourTimeNow());
		travelTracking.setMcurrentdatetime(AppConstants.getCurrentDateTime());
		travelTracking.setSyncstutas(false);
		travelTracking.setTypeofdatatracker("tracker");

		travelTracking.setLocation_lat("" + mLocation.getLatitude());
		travelTracking.setLocation_lng("" + mLocation.getLongitude());

		if (isvalHome(mLocation)){
			travelTracking.setIsAvlHomeChecker("1");

		}else {
			travelTracking.setIsAvlHomeChecker("0");
		}
		String user_Lat=PreferenceStore.getPrefernceHelperInstace().getString(mContext,PreferenceStore.USER_HOME_LAT);
		String user_Lng=PreferenceStore.getPrefernceHelperInstace().getString(mContext,PreferenceStore.USER_HOME_LNG);
		String  userrad=PreferenceStore.getPrefernceHelperInstace().getString(mContext,PreferenceStore.USER_RADIOUS);
		if (!user_Lat.equalsIgnoreCase("")) {
			travelTrackingRepository.insert(travelTracking);
		}
	}

	private boolean isvalHome(Location mLocation) {
		String user_Lat=PreferenceStore.getPrefernceHelperInstace().getString(mContext,PreferenceStore.USER_HOME_LAT);
		String user_Lng=PreferenceStore.getPrefernceHelperInstace().getString(mContext,PreferenceStore.USER_HOME_LNG);
        String  userrad=PreferenceStore.getPrefernceHelperInstace().getString(mContext,PreferenceStore.USER_RADIOUS);
		double diff=100.0;
        if (!userrad.equalsIgnoreCase("")){
			diff=Double.parseDouble(userrad);
		}
        if (!user_Lat.equalsIgnoreCase("")) {
			double distancefar = AppConstants.distance(Double.valueOf(user_Lat), Double.parseDouble(user_Lng),
					mLocation.getLatitude(),
					mLocation.getLongitude());


			Log.e("LOC----",user_Lat+"----"+user_Lng+"--"+mLocation.getLatitude()+"----"+mLocation.getLongitude()+"--"+distancefar +"--"+diff);
			if (distancefar > diff) {
				PreferenceStore.getPrefernceHelperInstace().setFlag(YelligoApplication.getContext(),PreferenceStore.LAST_OUTOFRADIOUS,true);
				apiBackGround.makeEmaergencyReqOut();
				return true;
			}else {
				apiBackGround.makereturn();
			}
		}

        /*float distancefar = AppConstants.distance(Double.valueOf(user_Lat), Double.parseDouble(user_Lat),
				Double.valueOf(PreferenceStore.getPrefernceHelperInstace().getString(YelligoApplication.getContext(), PreferenceStore.USER_LATITUDE)),
				Double.valueOf(PreferenceStore.getPrefernceHelperInstace().getString(YelligoApplication.getContext(), PreferenceStore.USER_LONGITUDE)));*/
		return false;
	}

	private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
		String strAdd = "";
		Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
		try {
			List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
			if (addresses != null) {
				Address returnedAddress = addresses.get(0);
				StringBuilder strReturnedAddress = new StringBuilder();

				for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
					strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
				}
				strAdd = strReturnedAddress.toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strAdd+"--"+LATITUDE+"--"+LONGITUDE;
	}
}