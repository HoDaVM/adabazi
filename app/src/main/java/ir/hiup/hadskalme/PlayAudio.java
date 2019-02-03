package ir.hiup.hadskalme;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

import ir.hiup.hadskalme.R;

/**
 * Created by Mahdi Asiyabi on 8/28/2017.
 */

public class PlayAudio extends Service {
    private static final String LOGCAT = null;
    MediaPlayer objPlayer;

    public void onCreate() {
        super.onCreate();
        Log.d(LOGCAT, "Service Started!");
        objPlayer = MediaPlayer.create(this, R.raw.background);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        objPlayer.start();
        objPlayer.setLooping(true);
        Log.d(LOGCAT, "Media Player started!");
        if (!objPlayer.isLooping()) {
            Log.d(LOGCAT, "Problem in Playing Audio");
        }
        return flags;
    }

    public void onStop() {
        objPlayer.stop();
        objPlayer.release();
    }

    public void onPause() {
        objPlayer.stop();
        objPlayer.release();
    }

    public void onDestroy() {
        objPlayer.stop();
        objPlayer.release();
    }

    @Override
    public IBinder onBind(Intent objIndent) {
        return null;
    }
}