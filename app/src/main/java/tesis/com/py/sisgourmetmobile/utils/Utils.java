package tesis.com.py.sisgourmetmobile.utils;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.Handler;
import android.provider.SyncStateContract;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.IntentCompat;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import tesis.com.py.sisgourmetmobile.R;
import tesis.com.py.sisgourmetmobile.activities.LoginActivity;

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

    public static void getSnackBar(CoordinatorLayout coordinatorLayout, String message) {
        final Snackbar snackbar = Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG);
        View snackbarView = snackbar.getView();
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setMaxLines(5);
        final View.OnClickListener clickListener = new View.OnClickListener() {
            public void onClick(View v) {
                snackbar.dismiss();
            }
        };
        snackbar.setAction(R.string.label_close, clickListener);
        snackbar.show();
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


    public static String getDayOfWeek(Date date, String format) {

        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        result = sdf.format(date);

        String[] weekday = new String[7];
        String returnDate;
        weekday[0] = "Domingo";
        weekday[1] = "Lunes";
        weekday[2] = "Martes";
        weekday[3] = "Miercoles";
        weekday[4] = "Jueves";
        weekday[5] = "Viernes";
        weekday[6] = "Sábado";

        returnDate = weekday[date.getDay()];
        return returnDate + " " + result;
    }


    public static String formatDate(Date date, String format) {
        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        result = sdf.format(date);
        return result;
    }

    public static AlertDialog.Builder createSimpleDialog(Context mContext, String title, String message, int resourceId, boolean isCancelable) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setIcon(resourceId);
        builder.setCancelable(isCancelable);

        return builder;

    }

    public static void setupAnimationProgressBar(ProgressBar mProgressBar, boolean indeterminate, int mInitValue, int mEndValue, int maxValue) {
        mProgressBar.setMax(maxValue);
        mProgressBar.setIndeterminate(indeterminate);
        ObjectAnimator animation = ObjectAnimator.ofInt(mProgressBar, "progress", mInitValue, mEndValue);
        animation.setDuration(1000);
        animation.setInterpolator(new AccelerateInterpolator());
        animation.start();

        Log.d("TAG", "CALL_ANIMATOR_PROGRESSBAR");
    }


    public static class ProgressBarAnimation extends Animation {
        private ProgressBar progressBar;
        private double from;
        private double to;

        public ProgressBarAnimation(ProgressBar progressBar, double from, double to) {
            super();
            this.progressBar = progressBar;
            this.from = from;
            this.to = to;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            double value = from + (to - from) * interpolatedTime;
            progressBar.setProgress((int) value);
        }

    }

    public static String volleyErrorHandler(Object err, Context context) {
        VolleyError error = (VolleyError) err;
        NetworkResponse response = error.networkResponse;
        String message = "No message";
        if (response == null) {
            Log.d("TAG", "ERROR: " + error.getMessage());
            if (error instanceof NetworkError) {
                //message = (error.getMessage() == null) ? context.getResources().getString(R.string.volley_network_error) : error.getMessage();
                message = context.getResources().getString(R.string.volley_network_error);
            } else if (error instanceof ServerError) {
                //message = (error.getMessage() == null) ? context.getResources().getString(R.string.volley_server_error) : error.getMessage();
                message = context.getResources().getString(R.string.volley_server_error);
            } else if (error instanceof AuthFailureError) {
                expireSession(context);
                //message = (error.getMessage() == null) ? context.getResources().getString(R.string.volley_auth_error) : error.getMessage();
                message = context.getResources().getString(R.string.volley_auth_error);
            } else if (error instanceof ParseError) {
                //message = (error.getMessage() == null) ? context.getResources().getString(R.string.volley_parse_error) : error.getMessage();
                message = context.getResources().getString(R.string.volley_parse_error);
            } else if (error instanceof NoConnectionError) {
                //message = (error.getMessage() == null) ? context.getResources().getString(R.string.volley_no_connection_error) : error.getMessage();
                message = context.getResources().getString(R.string.volley_no_connection_error);
            } else if (error instanceof TimeoutError) {
                //message = (error.getMessage() == null) ? context.getResources().getString(R.string.volley_time_out_error) : error.getMessage();
                message = context.getResources().getString(R.string.volley_time_out_error);
            }
        } else {
            try {
                message = new String(response.data);
                Log.d("TAG", "RESPOSNSE_MESSAGE: " + message);
                JSONObject jsonObject = new JSONObject(new String(response.data));
                if (response.statusCode == Constants.AUTH_ERROR_CODE) {
                    message = context.getString(R.string.error_invalid_credentials);
                } else if (response.statusCode == Constants.TOKEN_EXPIRED_CODE) {
                    expireSession(context);
                    message = context.getString(R.string.error_expired_session);
                }
                if (jsonObject.has("message")) {
                    message = jsonObject.getString("message");
                }
            } catch (Exception e) {
                e.printStackTrace();
                message = context.getString(R.string.dialog_error_unexpected) + e.getMessage();
            }
        }
        return message;
    }

    private static Drawable resizeImage(Context context, int resId, int w, int h) {
        // load the origial Bitmap
        Bitmap BitmapOrg = BitmapFactory.decodeResource(context.getResources(), resId);
        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth = w;
        int newHeight = h;
        // calculate the scale
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width, height, matrix, true);
        return new BitmapDrawable(resizedBitmap);
    }


    public static void expireSession(Context context) {
        // Clear login data
        AppPreferences.getAppPreferences(context).edit().clear().apply();
        Toast.makeText(context, context.getString(R.string.error_expired_session), Toast.LENGTH_LONG).show();
        context.startActivity(new Intent(context, LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    public static DefaultRetryPolicy getRetryPolicy() {
        int TIME_OUT_MS = 15000;


        return new DefaultRetryPolicy(
                TIME_OUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, // 0 Max retries
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    }

    public static String formatNumber(String number, String appendCurrency) {
        double data = 0;

        try {
            data = Double.parseDouble(number);
        } catch (Exception e) {
            e.printStackTrace();
        }
        NumberFormat nf = NumberFormat.getNumberInstance();
        DecimalFormat df = (DecimalFormat) nf;
        df.applyPattern("###,###.###");
        String output = df.format(data).replaceAll(",", ".");
        return output + appendCurrency;
    }

    public static byte[] bitmapToByteArray(Bitmap imageBitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return baos.toByteArray();
    }

    public static String getDayOfDate(String dateString) {
        String formatStr = dateString.substring(0, 10);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = null;
        try {
            date = dateFormat.parse(formatStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date date1 = (date);
        return sayDayName(date1);
    }

    public static float setupRatingValue(String mRatingValue) {
        return Float.parseFloat(mRatingValue);
    }

    private static String sayDayName(Date d) {
        DateFormat f = new SimpleDateFormat("EEEE");
        try {
            return f.format(d);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static int getMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.MONTH);
    }

    public static int getYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }

    public static String mapMonthName(Date date) {
        String mMonthDate = "";
        int month = getMonth(date);
        switch (month) {
            case 0:
                mMonthDate = "Enero";
                break;
            case 1:
                mMonthDate = "Febrero";
                break;
            case 2:
                mMonthDate = "Marzo";
                break;
            case 3:
                mMonthDate = "Abril";
                break;
            case 4:
                mMonthDate = "Mayo";
                break;
            case 5:
                mMonthDate = "Junio";
                break;
            case 6:
                mMonthDate = "Julio";
                break;
            case 7:
                mMonthDate = "Agosto";
                break;
            case 8:
                mMonthDate = "Septiembre";
                break;
            case 9:
                mMonthDate = "Octubre";
                break;
            case 10:
                mMonthDate = "Noviembre";
                break;
            case 11:
                mMonthDate = "Diciembre";
                break;

        }

        return mMonthDate;
    }
}
