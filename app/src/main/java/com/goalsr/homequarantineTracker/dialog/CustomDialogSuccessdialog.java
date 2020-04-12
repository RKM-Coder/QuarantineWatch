package com.goalsr.homequarantineTracker.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.goalsr.homequarantineTracker.R;


/**
 * Created
 */

public class CustomDialogSuccessdialog extends Dialog {

    private String key = "";
    private Activity activity;
//    private Button btLeft, btRight;
//    private TextView tvDescription;
    private ImageView imAlertImage;
//    private EditText etNote;

    public static int TYPE_PROGRESS = 0, TYPE_ALERT = 1;

    private OnButtonClickListener onButtonClickListener;
    private String desc, leftButtonName, rightButtonName;
    private int leftButtonVisibility, rightButtonVisibility, alertImageID, dialogType;

    public CustomDialogSuccessdialog(Activity activity, String key, OnButtonClickListener onButtonClickListener) {
        super(activity);
        this.activity = activity;
        this.key = key;
        leftButtonVisibility = rightButtonVisibility = View.VISIBLE;
        this.onButtonClickListener = onButtonClickListener;
        //alertImageID = R.drawable.ic_location_on_black_24dp;
        dialogType = TYPE_ALERT;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        try {
            getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (dialogType == TYPE_PROGRESS)  {


            RotateAnimation an = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            an.setInterpolator(new LinearInterpolator());
            an.setDuration(2250);
            an.setRepeatCount(-1);                // -1 = infinite repeated
            an.setRepeatMode(Animation.INFINITE); // reverses each repeat
//            an.setFillAfter(true);               // keep rotation after animation
            //ln_animate.startAnimation(an);
            return;
        } else setContentView(R.layout.activity_yelligo_arogya_mitra_dialog_successalert);

        imAlertImage = findViewById(R.id.iv_close);
        //tvDescription = findViewById(R.id.tv_description);
        /*etNote = findViewById(R.id.et_add_note);
        btLeft = findViewById(R.id.bt_left);
        btRight = findViewById(R.id.bt_right);*/
        setupView();
    }

    private void setupView() {
        setupButtons();
        if (!TextUtils.isEmpty(desc)) {
            //tvDescription.setText(desc);
        }
       // imAlertImage.setImageResource(alertImageID);
    }

    private void setupButtons() {

        /*imAlertImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onButtonClickListener == null)
                    CustomDialogSOSdialog.this.dismiss();
                else onButtonClickListener.onLeftButtonClick(CustomDialogSOSdialog.this);
            }
        });*/
        imAlertImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onButtonClickListener == null)
                    CustomDialogSuccessdialog.this.dismiss();
                else onButtonClickListener.onRightButtonClick(CustomDialogSuccessdialog.this, "");
            }
        });

    }

    public void setDialogType(int type) {
        dialogType = type;
    }

   /* public void setAlertImage(int alertImageID) {
        if (alertImageID <= 0)
            return;
        this.alertImageID = alertImageID;
    }*/

    public void setDescription(String text) {
        if (TextUtils.isEmpty(text))
            return;
        if (text.trim().toLowerCase().contains("internal server error")) {
            desc = "Temporarily unavailable due to maintenance. We'll back shortly.";
        } else
            desc = text;
    }

    public void setLeftButtonText(String text) {
        if (TextUtils.isEmpty(text))
            return;
        leftButtonName = text;
    }

    public void setRightButtonText(String text) {
        if (TextUtils.isEmpty(text))
            return;
        rightButtonName = text;
    }

    public void setLeftButtonVisibility(int visibility) {
        leftButtonVisibility = visibility;
    }

    public void setRightButtonVisibility(int visibility) {
        rightButtonVisibility = visibility;
    }

    public interface OnButtonClickListener {
        void onLeftButtonClick(CustomDialogSuccessdialog dialog);

        void onRightButtonClick(CustomDialogSuccessdialog dialog, String notes);
    }
}
