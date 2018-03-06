package com.sanus.sanus.utils.alert;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.sanus.sanus.R;

public class AlertUtils {

    private CallbackAlert callbackAlert;

    public AlertUtils(CallbackAlert callbackAlert) {
        this.callbackAlert = callbackAlert;
    }

    public void configureAlert(Context context, String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle(R.string.app_name);
        builder.setCancelable(false);
        builder.setMessage(msg);

        builder.setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                callbackAlert.cancelAlert();

            }
        });
        builder.setPositiveButton(R.string.button_accept, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                callbackAlert.acceptAlert();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void registerAlert(Context context, String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle(R.string.app_name);
        builder.setCancelable(false);
        builder.setMessage(msg);

        builder.setNegativeButton(R.string.button_exit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                callbackAlert.cancelAlert();

            }
        });
        builder.setPositiveButton(R.string.button_continue, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                callbackAlert.acceptAlert();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public static ProgressDialog getLoading(Context context) {
        return new ProgressDialog(context, R.style.AlertLoading);
    }



}
