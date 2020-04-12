package com.goalsr.homequarantineTracker.Utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.provider.Settings;
import android.view.Window;

import androidx.appcompat.app.AlertDialog;

import com.goalsr.homequarantineTracker.R;


/**
 * This class is used to get the common type of Dialogs used through out the app.
 *
 * @author Akhil Aravind
 */

public class DialogManager
{

    /**
     * Method used to display the progress dialog.
     * @param context context of the class.
     * @return Dialog with progress bar init.
     */
    public static Dialog getProgressDialog(Context context) {
        ///======================================= progress dialog init ========================

        Dialog dialog_progress = new Dialog(context);
        dialog_progress.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog_progress.setContentView(R.layout.layout_dialog_progress);
        dialog_progress.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog_progress.setCancelable(false);
        //activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return dialog_progress;
    }

    /**
     * Method used to show internet dialog.
     * @param  context context of the class.
     * @return Dialog with progress bar init.
     */
    public static void showInternetDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Please connect to internet")
                .setCancelable(true)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        context.startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                });
//                .setNegativeButton("Quit", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.dismiss();
//                    }
//                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
