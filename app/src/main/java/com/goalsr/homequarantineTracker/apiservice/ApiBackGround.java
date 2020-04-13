package com.goalsr.homequarantineTracker.apiservice;

import android.content.Context;

import com.goalsr.homequarantineTracker.Utils.FileUploader;
import com.goalsr.homequarantineTracker.Utils.PreferenceStore;
import com.goalsr.homequarantineTracker.YelligoApplication;
import com.goalsr.homequarantineTracker.db.model.QHTracker;
import com.goalsr.homequarantineTracker.db.repository.TravelTrackingRepository;
import com.goalsr.homequarantineTracker.resposemodel.ReqHeader;
import com.goalsr.homequarantineTracker.resposemodel.ReqTrailer;
import com.goalsr.homequarantineTracker.resposemodel.emergency.ReqBodyEmergency;
import com.goalsr.homequarantineTracker.resposemodel.emergency.ReqEmegency;
import com.goalsr.homequarantineTracker.resposemodel.poststatus.ReqStatus;
import com.goalsr.homequarantineTracker.resposemodel.poststatus.ReqSymtomBody;
import com.goalsr.homequarantineTracker.resposemodel.poststatus.UserTracker;

import java.util.ArrayList;
import java.util.List;

public class ApiBackGround {

    private Context mContext;
    TravelTrackingRepository travelTrackingRepository;
    private NetworkService networkService;

    public ApiBackGround(Context mContext) {
        this.mContext = mContext;
        travelTrackingRepository=new TravelTrackingRepository(mContext);
        initMvp();
    }

    public void uploadsync(boolean b){

        ArrayList<QHTracker> list=new ArrayList<>();
        ArrayList<ReqSymtomBody> reqSymtomBodylist=new ArrayList<>();
        if (travelTrackingRepository.getTravelListNonSync().size()>0) {
            list.addAll(travelTrackingRepository.getTravelListNonSync());
            reqSymtomBodylist.addAll(getreqListObject(list));
        }

        ReqStatus synstatus=new ReqStatus();
        UserTracker userTracker=new UserTracker();
        userTracker.setUserTrackData(reqSymtomBodylist);
        synstatus.setBody(userTracker);
        synstatus.setHeader(new ReqHeader());
        synstatus.setTrailer(new ReqTrailer());

        //networkService.makestatusupdate(synstatus,b,null);
       // uploadtoServer();


    }
    private void uploadtoServer() {
        ArrayList<String> ll=new ArrayList<String>();
        ll.addAll(travelTrackingRepository.getTravelListNonSyncImage());
        if (ll.size()>0) {
            new FileUploader().uploadFiles(YelligoApplication.getContext(), ll, "", new FileUploader.FileUploaderCallback() {
                @Override
                public void onError() {
                    //hideProgressDialog();
                    // Toast.makeText(getActivity(),ConstantMessage.tryagain,Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onFinish(String[] responses) {


                /*if (responses != null && responses.length > 0) {
                    for (int i = 0; i < responses.length; i++) {
                        TaskAttachment attachment = filesattachment.get(i);
                        attachment.setAttachment(responses[i]);
                        attachment.setSyncstatus(true);
                        filesattachment.add(attachment);
                    }
                    taskAttachmentViewModel.UpdateItem(filesattachment);
                }*/

                }

                @Override
                public void onProgressUpdate(int currentpercent, int totalpercent, int filenumber) {

                }
            });
        }
    }

    public void makeEmaergencyReqOut() {

        ReqEmegency reqEmegency=new ReqEmegency();
        ReqBodyEmergency reqBodyEmergency=new ReqBodyEmergency();
        reqBodyEmergency.setLatitude(PreferenceStore.getPrefernceHelperInstace().getString(YelligoApplication.getContext(),PreferenceStore.USER_LATITUDE));
        reqBodyEmergency.setLongitude(PreferenceStore.getPrefernceHelperInstace().getString(YelligoApplication.getContext(),PreferenceStore.USER_LONGITUDE));
       // reqBodyEmergency.setUserId(PreferenceStore.getPrefernceHelperInstace().getString(YelligoApplication.getContext(),PreferenceStore.CITIZEN_ID));
        reqBodyEmergency.setQuarantineStatus("2");
        reqEmegency.setBody(reqBodyEmergency);
        reqEmegency.setHeader(new ReqHeader());
        reqEmegency.setTrailer(new ReqTrailer());

        networkService.makeemergency(reqEmegency, null);


    }
    private void initMvp() {

        networkService = new NetworkService();
        networkService.inject(mContext);



    }

    private ArrayList<ReqSymtomBody> getreqListObject(ArrayList<QHTracker> list) {
        ArrayList<ReqSymtomBody> reqSymtomBodylist=new ArrayList<>();
        for (QHTracker tracker:list){
            ReqSymtomBody body=new ReqSymtomBody();
           // body.setUserId(PreferenceStore.getPrefernceHelperInstace().getString(YelligoApplication.getContext(),PreferenceStore.CITIZEN_ID));
            body.setLocalId(tracker.getPrimary_id());
            body.setLatitude(tracker.getLocation_lat());
            body.setLongitude(tracker.getLocation_lng());
            body.setDateTime(tracker.getMcurrentdatetimejulian());
            body.setTypeOfTracker(tracker.getTypeofdatatracker());
            //if (tracker.isSyncstutas())
            body.setSyncStatus("1");
            body.setSyncStatusImage("1");
            body.setSelfie(tracker.getSelfifilepathserver());//TODO
            body.setSelfiePathLocal(tracker.getSelfifilepathlocal());//TODO
            body.setSymptoms(tracker.getInfodata());
            reqSymtomBodylist.add(body);
        }

        return reqSymtomBodylist;
    }

    public void makereturn() {
        boolean b=PreferenceStore.getPrefernceHelperInstace().getFlag(YelligoApplication.getContext(),PreferenceStore.LAST_OUTOFRADIOUS);
        if (b) {
            PreferenceStore.getPrefernceHelperInstace().setFlag(YelligoApplication.getContext(), PreferenceStore.LAST_OUTOFRADIOUS, true);
            ReqEmegency reqEmegency=new ReqEmegency();
            ReqBodyEmergency reqBodyEmergency=new ReqBodyEmergency();
            reqBodyEmergency.setLatitude(PreferenceStore.getPrefernceHelperInstace().getString(YelligoApplication.getContext(),PreferenceStore.USER_LATITUDE));
            reqBodyEmergency.setLongitude(PreferenceStore.getPrefernceHelperInstace().getString(YelligoApplication.getContext(),PreferenceStore.USER_LONGITUDE));
            //reqBodyEmergency.setUserId(PreferenceStore.getPrefernceHelperInstace().getString(YelligoApplication.getContext(),PreferenceStore.CITIZEN_ID));
            reqEmegency.setBody(reqBodyEmergency);
            reqEmegency.setHeader(new ReqHeader());
            reqEmegency.setTrailer(new ReqTrailer());

            networkService.makeemergencyinside(reqEmegency, null);

        }

    }
}
