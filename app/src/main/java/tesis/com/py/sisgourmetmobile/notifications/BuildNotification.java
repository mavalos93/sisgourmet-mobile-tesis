package tesis.com.py.sisgourmetmobile.notifications;

import android.app.Notification;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import tesis.com.py.sisgourmetmobile.R;

/**
 * Created by Manu0 on 9/24/2017.
 */

public class BuildNotification extends NotificationCompat {

    public static NotificationCompat.Builder BuildNotification(Context context, int idResoruce, String title, String textNotification) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(idResoruce)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), idResoruce))
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setContentTitle(title)
                .setContentText(textNotification)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(Notification.PRIORITY_HIGH);
        return builder;
    }
}
