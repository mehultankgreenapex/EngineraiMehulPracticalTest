package com.example.myapplication.utility;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.myapplication.R;
import com.google.android.material.snackbar.Snackbar;

public class Utils {


    /**
     * @param context
     * @param message
     * @return
     * @description use to check internet network connection if network
     * connection not available than alert for open network
     * settings
     */
    public static boolean isOnline(final Activity context, boolean message) {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = mConnectivityManager.getActiveNetworkInfo();
        if (netInfo != null) {
            if (netInfo.isConnectedOrConnecting()) {
                return true;
            }
        }
        if (message) {
            final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
            dialog.setTitle(context.getString(R.string.app_name));
            dialog.setCancelable(false);
            dialog.setMessage(context.getString(R.string.check_connection));
            dialog.setPositiveButton(context.getString(R.string.settings), (dialog1, id) -> {
                dialog1.dismiss();
                context.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
            });
            dialog.setNegativeButton(context.getString(android.R.string.cancel),
                    (dialog12, id) -> dialog12.dismiss());
            dialog.show();

            return false;
        }
        return false;
    }


    /**
     * @param context
     * @param detail
     * @purpose for writing the log
     */
    public static void writeLog(final Context context, String detail) {
        if (true) {
            Log.d(context.getClass().getSimpleName(), detail);
        }
    }


    public static void snackbar(final View view, final String msg, boolean isSnakbar, Context mContext) {

        try {
            if (isSnakbar) {
                Snackbar snack = Snackbar.make(view, msg, Snackbar.LENGTH_LONG);
                snack.getView().setBackgroundColor(Color.parseColor("#D81B60"));
                View viewNew = snack.getView();
                TextView tv = viewNew.findViewById(R.id.snackbar_text);
                tv.setGravity(Gravity.CENTER_HORIZONTAL);
                snack.show();
            } else {
                Toast.makeText(mContext, "" + msg, Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    // To reveal a previously invisible view using this effect:
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void show(final View view) {

        // get the center for the clipping circle
        int cx = (view.getLeft() + view.getRight()) / 2;
        int cy = (view.getTop() + view.getBottom()) / 2;

        // get the final radius for the clipping circle
        int finalRadius = Math.max(view.getWidth(), view.getHeight());

        // create the animator for this view (the start radius is zero)
        Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);
        anim.setDuration(1000);

        // make the view visible and start the animation
        view.setVisibility(View.VISIBLE);
        anim.start();


    }

}