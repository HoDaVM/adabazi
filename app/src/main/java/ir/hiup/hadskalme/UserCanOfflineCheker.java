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

public class UserCanOfflineCheker extends Service {

    private final static String TAG = "Offline";
    private static Long MILLISECS_PER_DAY = 86400000L;
    private static Long MILLISECS_PER_MIN = 60000L;

    //private static long delay = MILLISECS_PER_MIN * 1;   // 3 minutes (for testing)
    public static long delay ;  // 3 days


    @Override
    public void onCreate() {
        super.onCreate();

        Log.v(TAG, "Service started");
        ir.hiup.hadskalme.Shared.init(getBaseContext());

        String m2 = ir.hiup.hadskalme.Shared.read("m3", "null");

        // Are notifications enabled?
        // Is it time for a notification?
        if (Long.valueOf(m2) < System.currentTimeMillis() - 86400000L)
            changeStatus();

        if(ir.hiup.hadskalme.Shared.read("time","0").equals("1"))
        {
            setAlarm();
            ir.hiup.hadskalme.Shared.write("time","0");
        }

        Log.v(TAG, "Service stopped");
        stopSelf();
    }

    public void setAlarm() {

        Long delayTime = Long.valueOf(ir.hiup.hadskalme.Shared.read("delay","0"));
        Log.v(TAG, String.valueOf(delayTime));
        if(delayTime>=86400000L)
        {
            ir.hiup.hadskalme.Shared.write("usercanofflinebytime","1");
            Intent serviceIntent = new Intent(this, UserCanOfflineCheker.class);
            PendingIntent pi = PendingIntent.getService(this, 131313, serviceIntent,
                    PendingIntent.FLAG_CANCEL_CURRENT);

            AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 86400000L, pi);
            Log.v(TAG, "Alarm set");
        }
    }

    public void changeStatus() {
        ir.hiup.hadskalme.Shared.write("usercanofflinebytime","0");
        Log.v(TAG, String.valueOf("status Changed"));
        Long delayTime = Long.valueOf(ir.hiup.hadskalme.Shared.read("delay","0"));
        if(delayTime>0)
            delayTime = delayTime -  86400000L;
        ir.hiup.hadskalme.Shared.write("delay",String.valueOf(delayTime));
        setAlarm();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}