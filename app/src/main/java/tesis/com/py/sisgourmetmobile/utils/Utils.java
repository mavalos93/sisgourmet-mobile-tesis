package tesis.com.py.sisgourmetmobile.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.Handler;
import android.provider.SyncStateContract;
import android.support.design.widget.Snackbar;
import android.support.v4.content.IntentCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import tesis.com.py.sisgourmetmobile.R;

/**
 * Created by diego on 25/11/15.
 */
public class Utils {

    public static boolean checkNetworkConnection(Context context) {
        boolean isConnected = false;
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                isConnected = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isConnected;
    }

    public static boolean checkIfGpsIsEnabled(Context context) {
        LocationManager mLocationManager;
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        boolean isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isGPSEnabled;
    }

    public static String getPhoneIMEI(Context mContext) {

        TelephonyManager tm = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);

        String id = tm.getDeviceId();
        String imei = "";

        if (id == null) {
            id = "not available";
        }

        int phoneType = tm.getPhoneType();
        switch (phoneType) {
            case TelephonyManager.PHONE_TYPE_NONE:
                imei = "NONE: " + id;

            case TelephonyManager.PHONE_TYPE_GSM:
                imei = "GSM: IMEI=" + id;

            case TelephonyManager.PHONE_TYPE_CDMA:
                imei = "CDMA: MEID/ESN=" + id;

            case TelephonyManager.PHONE_TYPE_SIP:
                imei = "SIP: " + id;
            /*
             * for API Level 11 or above case TelephonyManager.PHONE_TYPE_SIP:
			 * return "SIP";
			 */
            default:
                imei = "UNKNOWN: ID=" + id;
                //id = "3589730612560421"; //PRODUCCION
                //id = "8611378019202470-";
        }
        return id;
    }

    public static ProgressDialog getProgressDialog(Context mContext, String message, String title) {
        final ProgressDialog mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setTitle((title == null) ? mContext.getString(R.string.dialog_progress_title) : title);
        mProgressDialog.setMessage((message == null) ? mContext.getString(R.string.dialog_progess_message) : message);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setInverseBackgroundForced(true);
        return mProgressDialog;
    }

    public static Date getToday() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        Date today = c.getTime();
        return today;
    }

    public static Date getYesterday() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        Date yesterday = c.getTime();
        return yesterday;
    }

    public static void builToast(Context mContext, String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }


    public static int hoursDifference(Date date1, Date date2) {

        long fecha_1 = date1.getTime();
        long fecha_2 = date2.getTime();
        Log.d("FECHA_1", "FECHA_1: " + fecha_1);
        Log.d("FECHA_2", "FECHA_2: " + fecha_2);

        final int MILLI_TO_HOUR = 1000 * 60 * 60;
        long test = fecha_1 - fecha_2;
        Log.d("FECHA", "test: " + test);
        double diff = Math.floor(test / MILLI_TO_HOUR);
        Log.d("FECHA", "calculo_fecha: " + diff);
        return (int) diff;
    }


    public static String getDateFromLong(long dateLong, String format) {
        String dateString = "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date;
        try {
            date = new Date(dateLong);
            dateString = sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateString;
    }

    public static boolean validNumber(int start, int end, int value) {
        if ((value >= start) && (value <= end)) return true;
        return false;
    }

    public static boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        } else {
            return false;
        }
    }


    public static Bitmap getBitmapFromPath(String path) {
        BitmapFactory.Options options;
        options = new BitmapFactory.Options();
        options.inPreferQualityOverSpeed = false;
        options.inPurgeable = true;
        options.inSampleSize = 2;
        //options.inJustDecodeBounds = false;
        //options.inPreferredConfig = Bitmap.Config.RGB_565;
        return BitmapFactory.decodeFile(path, options);
    }

    public static String dateFormatter(Date mDate, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String formantedDate = null;
        formantedDate = sdf.format(mDate);
        return formantedDate;
    }

    public static void clearForm(ViewGroup group) {
        for (int i = 0, count = group.getChildCount(); i < count; ++i) {
            View view = group.getChildAt(i);
            if (view instanceof EditText) {
                ((EditText) view).setText("");
            }

            if (view instanceof ViewGroup && (((ViewGroup) view).getChildCount() > 0))
                clearForm((ViewGroup) view);
        }
    }


    public static String getDayOfWeek(Date date, String format){

        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        result = sdf.format(date);

        String[] weekday = new String[7];
        String returnDate;
        weekday[0] =  "Domingo";
        weekday[1] = "Lunes";
        weekday[2] = "Martes";
        weekday[3] = "Miercoles";
        weekday[4] = "Jueves";
        weekday[5] = "Viernes";
        weekday[6] = "Sábado";

        returnDate = weekday[date.getDay()];
        return returnDate + " " +result;
    }

}
