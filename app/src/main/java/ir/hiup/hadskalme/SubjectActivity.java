package ir.hiup.hadskalme;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import cn.pedant.SweetAlert.SweetAlertDialog;
import info.hoang8f.android.segmented.SegmentedGroup;
import ir.hiup.hadskalme.UIinit.Animations.Animations;
import ir.hiup.hadskalme.UIinit.Dialogs.SubjectHelper;
import ir.hiup.hadskalme.UIinit.GlobalVariables;
import ir.hiup.hadskalme.UIinit.Sounds.Sounds;
import ir.hiup.hadskalme.UIinit.UiInit;
import ir.hiup.hadskalme.UIinit.validation;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SubjectActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new SweetAlertDialog(SubjectActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("میخواین بازی را لغو کنین ؟")
                    .setConfirmText("بله")
                    .setCancelText("نه ، ادامه بدیم")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            GlobalVariables.flaginapp = true;
                            UiInit.gotopage(SubjectActivity.this,HomeActivity.class);
                            GlobalVariables.fullreset();
                            finish();
                        }
                    })
                    .show();
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject);
        UiInit.Subject(SubjectActivity.this);
        SegmentedGroup segmented2 = (SegmentedGroup) findViewById(R.id.teamsnumber);
        segmented2.setTintColor(Color.parseColor("#1db375"), Color.parseColor("#ffffff"));


        String txt;
        if(GlobalVariables.twotype)
        {
            int wichuser = GlobalVariables.wichteam;
            String username;
            if(wichuser==1)
                username="اول";
            else
                username="دوم";

            txt = "نفر « "+ username + " » \n موضوع را انتخاب کند";
        }
        else
        {
            String teamname = validation.getTeamname(GlobalVariables.wichteam);
            txt = "تیم « "+ teamname + " » \n موضوع را انتخاب کند";
        }

        SubjectHelper subjectHelper = new SubjectHelper(SubjectActivity.this,txt);
        subjectHelper.show();

        if (GlobalVariables.typeofmatch==2)
        {
            GlobalVariables.POINT=1;
            findViewById(R.id.selectsubject).setVisibility(View.GONE);
            findViewById(R.id.scrollview).setVisibility(View.GONE);
        }
        else
        {
            findViewById(R.id.typetext).setVisibility(View.GONE);
            segmented2.setVisibility(View.GONE);
        }

        findViewById(R.id.one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animations.clickeffect(view);
                GlobalVariables.flaginapp = true;
                Sounds.simplebuttons(getBaseContext());
                GlobalVariables.POINT=1;
                findViewById(R.id.selectsubject).setVisibility(View.VISIBLE);
                findViewById(R.id.scrollview).setVisibility(View.VISIBLE);
            }
        });
        findViewById(R.id.three).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animations.clickeffect(view);
                GlobalVariables.flaginapp = true;
                Sounds.simplebuttons(getBaseContext());
                GlobalVariables.POINT=3;
                findViewById(R.id.selectsubject).setVisibility(View.VISIBLE);
                findViewById(R.id.scrollview).setVisibility(View.VISIBLE);
            }
        });
        findViewById(R.id.six).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animations.clickeffect(view);
                GlobalVariables.flaginapp = true;
                Sounds.simplebuttons(getBaseContext());
                GlobalVariables.POINT=6;
                findViewById(R.id.selectsubject).setVisibility(View.VISIBLE);
                findViewById(R.id.scrollview).setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animations.clickeffect(view);
                GlobalVariables.flaginapp = true;
                Sounds.simplebuttons(getBaseContext());
                new SweetAlertDialog(SubjectActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("میخواین بازی را لغو کنین ؟")
                        .setConfirmText("بله")
                        .setCancelText("نه ، ادامه بدیم")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                GlobalVariables.flaginapp = true;
                                UiInit.gotopage(SubjectActivity.this,HomeActivity.class);
                                GlobalVariables.fullreset();
                                finish();
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();

        if (!GlobalVariables.flaginapp) {
            Sounds.stopAudio(SubjectActivity.this);
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
            Sounds.playAudio(SubjectActivity.this);
        }
    }
}
