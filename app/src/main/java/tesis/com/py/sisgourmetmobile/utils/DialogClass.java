package tesis.com.py.sisgourmetmobile.utils;

import android.app.AlertDialog;
import android.content.Context;

/**
 * Created by Manu0 on 22/1/2017.
 */

public class DialogClass {


    public static AlertDialog.Builder createSimpleDialog(Context context, String title, String message, int idResource) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setIcon(idResource);
        alertDialog.setCancelable(false);
        return alertDialog;
    }
}
