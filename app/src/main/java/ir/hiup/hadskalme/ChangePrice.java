package ir.hiup.hadskalme;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import ir.hiup.hadskalme.UIinit.validation;

public class ChangePrice extends Service {

    private final static String TAG = "CheckRecentPlay";
    private static Long MILLISECS_PER_DAY = 86400000L;
    private static Long MILLISECS_PER_MIN = 60000L;

    //private static long delay = MILLISECS_PER_MIN * 1;   // 3 minutes (for testing)
    private static long delay = MILLISECS_PER_DAY * 10;   // 10 days
    private static long delayTwo = MILLISECS_PER_DAY * 20;   // 20 days

    @Override
    public void onCreate() {
        super.onCreate();

        Log.v(TAG, "Service started");
        ir.hiup.hadskalme.Shared.init(getBaseContext());

        String m2 = ir.hiup.hadskalme.Shared.read("m4", "null");

        // Are notifications enabled?
        // Is it time for a notification?
        if (Long.valueOf(m2) < System.currentTimeMillis() - delay && ir.hiup.hadskalme.Shared.read("FirstDis","0").equals("0") && !validation.BuyedUser())
        {
            sendNotification(20);
            ir.hiup.hadskalme.Shared.write("PACKAGE","PACKTWO");
            ir.hiup.hadskalme.Shared.write("FirstDis","1");
        }
        else if (Long.valueOf(m2) < System.currentTimeMillis() - delayTwo && ir.hiup.hadskalme.Shared.read("SecondDis","0").equals("0") && !validation.BuyedUser())
        {
            sendNotification(40);
            ir.hiup.hadskalme.Shared.write("PACKAGE","PACKTHREE");
            ir.hiup.hadskalme.Shared.write("SecondDis","1");
        }
        else if(!validation.BuyedUser() && (ir.hiup.hadskalme.Shared.read("SecondDis","0").equals("1") ||  ir.hiup.hadskalme.Shared.read("FirstDis","0").equals("1")))
        {
            ir.hiup.hadskalme.Shared.write("PACKAGE","FIRSTPACK");
        }


        // Set an alarm for the next time this service should run:
        if(!validation.BuyedUser())
            setAlarm();

        Log.v(TAG, "Service stopped");
        stopSelf();
    }

    public void setAlarm() {

        Intent serviceIntent = new Intent(this, ChangePrice.class);
        PendingIntent pi = PendingIntent.getService(this, 131313, serviceIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + delay, pi);
        Log.v(TAG, "Alarm set");
    }

    public void sendNotification(int dis) {

        Intent mainIntent = new Intent(this, HomeActivity.class);
        @SuppressWarnings("deprecation")
        Notification noti = new Notification.Builder(this)
                .setAutoCancel(true)
                .setContentIntent(PendingIntent.getActivity(this, 131314, mainIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT))
                .setContentTitle(String.valueOf(dis)+" درصد تخفیف ویژه ")
                .setContentText("خرید نسخه آفلاین با تخفیف ویژه برای شما")
                .setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.applogo)
                .setTicker("خرید نسخه آفلاین با تخفیف ویژه برای شما")
                .setWhen(System.currentTimeMillis())
                .getNotification();

        NotificationManager notificationManager
                = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(131315, noti);

        Log.v(TAG, "Notification sent");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}