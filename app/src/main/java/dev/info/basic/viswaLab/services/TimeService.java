package dev.info.basic.viswaLab.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import dev.info.basic.viswaLab.Activitys.SplashScreen;
import dev.info.basic.viswaLab.Database.helper;
import dev.info.basic.viswaLab.R;
import dev.info.basic.viswaLab.models.ScheduleAlertModel;

/**
 * Created by Giri on 2/2/2018.
 */

public class TimeService extends Service {

    private static final String TAG = "Service";
    IBinder iBinder = new MyBinder();

    static IntentFilter s_intentFilter;

    helper dbHelper;


    static {
        s_intentFilter = new IntentFilter();
        s_intentFilter.addAction(Intent.ACTION_TIME_TICK);
        s_intentFilter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
        s_intentFilter.addAction(Intent.ACTION_DATE_CHANGED);

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //  Toast.makeText(getApplicationContext(),"TimeService",Toast.LENGTH_LONG);

        dbHelper = new helper(this);
        registerReceiver(m_timeChangedReceiver, s_intentFilter);

    }


    private final BroadcastReceiver m_timeChangedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(Intent.ACTION_TIME_CHANGED) ||
                    action.equals(Intent.ACTION_TIMEZONE_CHANGED)) {
            }
            getDate();

        }
    };


    private void addNotification(String ship_name, String equipment_name, String scheduled_date) {
        try {
            Uri notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notificationUri);
            r.play();
           /* int timer = manager.getTimer();
            String message = null;
            switch (timer) {
                case 0:
                    message = "Today is ";
                    break;
                case 1:
                    message = "Tomorrow is ";
                    break;
                case 2:
                    message = "Day after Tomorrow is  ";
                    break;
            }
*/
            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("VLIMS")
                            .setSound(notificationUri)
                            .setContentText("Lube Oil Sample Collection is Scheduled on" + "\n" + scheduled_date + "\n" + " For" + equipment_name + " in" + ship_name);

            Intent notificationIntent = new Intent(this, SplashScreen.class);
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(contentIntent);

            // Add as notification
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(0, builder.build());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class MyBinder extends Binder {
        TimeService getService() {
            return TimeService.this;
        }
    }

    private void getDate() {

        dbHelper = new helper(getApplicationContext());

        List<ScheduleAlertModel> contactsDetailses = dbHelper.getAllScheduleAlerts();
        if (contactsDetailses.size() > 0) {

            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DAY_OF_MONTH, 5);

            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            String formattedDate = df.format(calendar.getTime());

            SimpleDateFormat timef = new SimpleDateFormat("HH:mm");
            String time = timef.format(calendar.getTime());

            if (time.equals("12:00") || time.equals("10:00")) {

                for (int i = 0; i < contactsDetailses.size(); i++) {

                    String scheduled_date = "";
                    try {
                        String ship_name = contactsDetailses.get(i).getShipName();
                        String equipment_name = contactsDetailses.get(i).getEquipment();
                        scheduled_date = contactsDetailses.get(i).getSheduleDate();
                        scheduled_date = scheduled_date.replace("/Date(", "").replace(")/", "");

                        if (scheduled_date.length() > 0) {

                            Data data = convertDate(scheduled_date);

                            String birthmonth = data.getMonth();
                            String birthDate = data.getDay();
                            Data curentDate = convertDate(formattedDate);

                            String currentday = curentDate.getDay();
                            String currentzMonth = curentDate.getMonth();

                            Log.d("TAG1", currentday + ":" + birthDate);

                            if (birthDate.equals(currentday) && birthmonth.equals(currentzMonth)) {
//                                        if (manager.isNotificationOn()) {
                                addNotification(ship_name, equipment_name, scheduled_date);
                            }
                        }

                    } catch (NullPointerException e) {
                        scheduled_date = "";
                    }

                }
            } else {
                Log.d(TAG, "" + time);
            }

        } else {
        }


    }

    private Data convertDate(String birthday) {

        Data data = new Data();
        birthday = birthday.replace("/Date(", "").replace(")/", "");
        DateFormat inputDF = new SimpleDateFormat("dd/MM/yyyy");
        Date date1 = null;
        try {

            date1 = inputDF.parse(birthday);
            Calendar cal = Calendar.getInstance();
            cal.setTime(date1);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            String year = String.valueOf(cal.get(Calendar.YEAR));
            month = month + 1;
            String day1 = String.valueOf(day);

            data.setMonth(String.valueOf(month));
            data.setYear(year);
            if (day < 10) {
                day1 = "0" + day;
                data.setDay(day1);
            } else {
                data.setDay(day1);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return data;
    }

    private class Data {
        private String month, day, year;

        public String getMonth() {

            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }
    }
}
