package ir.hiup.hadskalme;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import cn.pedant.SweetAlert.SweetAlertDialog;
import ir.hiup.hadskalme.UIinit.Animations.Animations;
import ir.hiup.hadskalme.UIinit.Auth;
import ir.hiup.hadskalme.UIinit.Dialogs.HemayatDialoge;
import ir.hiup.hadskalme.UIinit.Dialogs.ResultHelper;
import ir.hiup.hadskalme.UIinit.GlobalVariables;
import ir.hiup.hadskalme.UIinit.Sounds.Sounds;
import ir.hiup.hadskalme.UIinit.UiInit;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ResultActivity extends AppCompatActivity {
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            GlobalVariables.flaginapp = true;
            GlobalVariables.fullreset();
            finish();
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
        setContentView(R.layout.activity_result);
        UiInit.Result(ResultActivity.this);

        if(!ir.hiup.hadskalme.Shared.read("HelperResult","0").equals("1"))
        {
            ResultHelper resultHelper = new ResultHelper(ResultActivity.this);
            resultHelper.show();
            ir.hiup.hadskalme.Shared.write("HelperResult","1");
        }
        else if (!ir.hiup.hadskalme.Shared.read("Hemayat","0").equals("1"))
        {
            HemayatDialoge hemayatDialoge = new HemayatDialoge(ResultActivity.this);
            hemayatDialoge.show();
        }
        else if(ir.hiup.hadskalme.Shared.read("FourthAds","0").equals("0"))
        {
            Auth.ourAdsinit(ResultActivity.this,4);
            ir.hiup.hadskalme.Shared.write("FourthAds","1");
        }
        findViewById(R.id.replay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animations.clickeffect(view);
                GlobalVariables.flaginapp = true;
                Sounds.simplebuttons(getBaseContext());
                GlobalVariables.resetScore();
                UiInit.gotopage(ResultActivity.this,SubjectActivity.class);
                finish();
            }
        });

        findViewById(R.id.baacktomain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animations.clickeffect(view);
                GlobalVariables.flaginapp = true;
                Sounds.simplebuttons(getBaseContext());
                GlobalVariables.fullreset();
                UiInit.gotopage(ResultActivity.this,HomeActivity.class);
                finish();
            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();

        if (!GlobalVariables.flaginapp) {
            Sounds.stopAudio(ResultActivity.this);
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
            Sounds.playAudio(ResultActivity.this);
        }
    }
}
