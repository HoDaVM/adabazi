package ir.hiup.hadskalme;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import ir.hiup.hadskalme.UIinit.Auth;
import ir.hiup.hadskalme.UIinit.GlobalVariables;
import ir.hiup.hadskalme.UIinit.Sounds.Sounds;
import ir.hiup.hadskalme.UIinit.UiInit;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class VideosActivity extends AppCompatActivity {

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            GlobalVariables.flaginapp = true;
            UiInit.gotopage(VideosActivity.this,HomeActivity.class);
        }
        return false;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);
        UiInit.VideosActivity(VideosActivity.this);

        if(ir.hiup.hadskalme.Shared.read("SecondAds","0").equals("0"))
        {
            Auth.ourAdsinit(VideosActivity.this,2);
            ir.hiup.hadskalme.Shared.write("SecondAds","1");
        }

        //Back Button
        UiInit.clickfunc(VideosActivity.this, HomeActivity.class, R.id.back);
    }

    @Override
    public void onPause() {
        super.onPause();

        if (!GlobalVariables.flaginapp) {
            Sounds.stopAudio(VideosActivity.this);
            GlobalVariables.audioplay = false;
        } else {
            GlobalVariables.audioplay = true;
            GlobalVariables.flaginapp = false;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ir.hiup.hadskalme.Shared.init(getBaseContext());
        String background = ir.hiup.hadskalme.Shared.read("backgroundmusic", null);
        if (background.equals("okay") && !GlobalVariables.audioplay) {
            GlobalVariables.audioplay = true;
            Sounds.playAudio(VideosActivity.this);
        }
    }
}
