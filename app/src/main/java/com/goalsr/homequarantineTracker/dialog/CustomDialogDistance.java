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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.goalsr.homequarantineTracker.R;


/**
 * Created
 */

public class CustomDialogDistance extends Dialog {

    private String key = "";
    private Activity activity;
    private Button btLeft, btRight;
    private TextView tvDescription;
    private ImageView imAlertImage;
    private EditText etNote;

    public static int TYPE_PROGRESS = 0, TYPE_ALERT = 1;

    private OnButtonClickListener onButtonClickListener;
    private String desc, leftButtonName, rightButtonName;
    private int leftButtonVisibility, rightButtonVisibility, alertImageID, dialogType;

    public CustomDialogDistance(Activity activity, String key, OnButtonClickListener onButtonClickListener) {
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
            //setContentView(R.layout.custom_progress_dialog);
            //View ln_animate = this.findViewById(R.id.ln_animate);

            /*RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotate.setDuration(5000);
            rotate.setInterpolator(new LinearInterpolator());
            ln_animate.startAnimation(rotate);*/


            RotateAnimation an = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            an.setInterpolator(new LinearInterpolator());
            an.setDuration(2250);
            an.setRepeatCount(-1);                // -1 = infinite repeated
            an.setRepeatMode(Animation.INFINITE); // reverses each repeat
//            an.setFillAfter(true);               // keep rotation after animation
            //ln_animate.startAnimation(an);
            return;
        } else setContentView(R.layout.custom_alert_dialog_distance_message);

        imAlertImage = findViewById(R.id.im_alertImage);
        tvDescription = findViewById(R.id.tv_description);
        etNote = findViewById(R.id.et_add_note);
        btLeft = findViewById(R.id.bt_left);
        btRight = findViewById(R.id.bt_right);
        setupView();
    }

    private void setupView() {
        setupButtons();
        if (!TextUtils.isEmpty(desc)) {
            tvDescription.setText(desc);
        }
       // imAlertImage.setImageResource(alertImageID);
    }

    private void setupButtons() {

        btLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onButtonClickListener == null)
                    CustomDialogDistance.this.dismiss();
                else onButtonClickListener.onLeftButtonClick(CustomDialogDistance.this);
            }
        });
        btRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onButtonClickListener == null)
                    CustomDialogDistance.this.dismiss();
                else onButtonClickListener.onRightButtonClick(CustomDialogDistance.this, etNote.getText().toString().trim());
            }
        });

        if (rightButtonName != null)
            btRight.setText(rightButtonName);
        if (leftButtonName != null)
            btLeft.setText(leftButtonName);


            btLeft.setVisibility(leftButtonVisibility);
        btRight.setVisibility(rightButtonVisibility);
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
        void onLeftButtonClick(CustomDialogDistance dialog);

        void onRightButtonClick(CustomDialogDistance dialog, String notes);
    }
}
