package dev.info.basic.viswaLab.pushNotificationServices;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import dev.info.basic.viswaLab.Activitys.HomeActivity;
import dev.info.basic.viswaLab.R;

import static android.app.Notification.DEFAULT_LIGHTS;
import static android.app.Notification.DEFAULT_SOUND;
import static android.app.Notification.DEFAULT_VIBRATE;

/**
 * Created by RSR on 10-01-2017.
 */

public class GCMPushReceiverService extends GcmListenerService {
    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    //This method will be called on every new message received
    @Override
    public void onMessageReceived(String from, Bundle data) {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
        editor.putBoolean("isMessageReceived", true);
        editor.putString("isNotificationReceived", "y");
        editor.commit();
        //Getting the message from the bundle
        String message = data.getString("message");
        Bitmap Images = null;
        if (data.getString("image") != null) {
            Log.v("IMAGES", data.getString("image"));
            Images = getBitmapFromURL(data.getString("image"));
        } else {
            Log.v("IMAGES", "NULL");
        }
        // Toast.makeText(this,message,Toast.LENGTH_LONG).show();
        //Displaying a notiffication with the message
        sendNotification(message, Images);
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    //This method is generating a notification and displaying the notification
    private void sendNotification(String message, Bitmap images) {
        if (prefs.getBoolean("isLoginSucess", false)) {
            Intent intent = new Intent(this, HomeActivity.class);
            Bundle bundle = new Bundle();
            bundle.putBoolean("notify", true);
            intent.putExtras(bundle);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            final PendingIntent resultPendingIntent =
                    PendingIntent.getActivity(
                            this,
                            0,
                            intent,
                            PendingIntent.FLAG_CANCEL_CURRENT
                    );

            Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder noBuilder = new NotificationCompat.Builder(this)
                    .setContentTitle("Priced Property")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentText(message)
                    .setStyle(new NotificationCompat.BigPictureStyle()
                            .bigPicture(images))
                    .setAutoCancel(true)
                    .setSound(sound)
                    .setDefaults(DEFAULT_SOUND | DEFAULT_VIBRATE | DEFAULT_LIGHTS)
                    .setContentIntent(resultPendingIntent);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancelAll();
            notificationManager.notify(0, noBuilder.build()); //0 = ID of notification
        }
    }
}
