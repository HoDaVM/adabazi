package ir.hiup.hadskalme.UIinit.Sounds;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;

import ir.hiup.hadskalme.PlayAudio;
import ir.hiup.hadskalme.R;
import ir.hiup.hadskalme.Shared;

/**
 * Created by Mahdi Asiyabi on 9/27/2017.
 */

public class Sounds {

    public static void CountdownSound(Context c)
    {
        Shared.init(c);
        if(Shared.read("sfx","NULL").equals("okay"))
        {
            MediaPlayer mp = MediaPlayer.create(c, R.raw.countdown);
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.start();
        }
    }
    public static void FaultSound(Context c)
    {
        Shared.init(c);
        if(Shared.read("sfx","NULL").equals("okay"))
        {
            MediaPlayer mp = MediaPlayer.create(c, R.raw.fault);
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.start();
        }
    }
    public static void ChangeWordSound(Context c)
    {
        Shared.init(c);
        if(Shared.read("sfx","NULL").equals("okay"))
        {
            MediaPlayer mp = MediaPlayer.create(c, R.raw.changeword);
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.start();
        }
    }
    public static void simplebuttons(Context c)
    {
        Shared.init(c);
        if(Shared.read("sfx","NULL").equals("okay"))
        {
            MediaPlayer mp = MediaPlayer.create(c, R.raw.btnclicks);
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.start();
        }
    }
    public static void playbuttons(Context c)
    {
        if(Shared.read("sfx","NULL").equals("okay")) {
            MediaPlayer mp = MediaPlayer.create(c, R.raw.playbutton);
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.start();
        }
    }
    public static void playsplash(Context c)
    {
        if(Shared.read("sfx","NULL").equals("okay")) {
            MediaPlayer mp = MediaPlayer.create(c, R.raw.splashsound);
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.start();
        }
    }
    public static void playAudio(Activity act) {
        Intent objIntent = new Intent(act.getBaseContext(), PlayAudio.class);
        act.startService(objIntent);
    }

    public static void stopAudio(Activity act) {
        Intent objIntent = new Intent(act.getBaseContext(), PlayAudio.class);
        act.stopService(objIntent);
    }
}
