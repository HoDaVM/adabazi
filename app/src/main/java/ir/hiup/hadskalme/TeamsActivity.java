package ir.hiup.hadskalme;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import javax.xml.validation.Validator;

import cn.pedant.SweetAlert.SweetAlertDialog;
import info.hoang8f.android.segmented.SegmentedGroup;
import ir.hiup.hadskalme.UIinit.Animations.Animations;
import ir.hiup.hadskalme.UIinit.GlobalVariables;
import ir.hiup.hadskalme.UIinit.Sounds.Sounds;
import ir.hiup.hadskalme.UIinit.UiInit;
import ir.hiup.hadskalme.UIinit.validation;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class TeamsActivity extends AppCompatActivity {


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new SweetAlertDialog(TeamsActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("میخواین بازی را لغو کنین ؟")
                    .setConfirmText("بله")
                    .setCancelText("نه ، ادامه بدیم")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            GlobalVariables.flaginapp = true;
                            UiInit.gotopage(TeamsActivity.this,HomeActivity.class);
                            GlobalVariables.fullreset();
                            finish();
                        }
                    })
                    .show();
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
        setContentView(R.layout.activity_teams);
        UiInit.TeamsActivity(TeamsActivity.this);

        final EditText teamone = (EditText) findViewById(R.id.textfirst);
        final EditText teamtwo = (EditText) findViewById(R.id.textsecond);
        final EditText teamthree = (EditText) findViewById(R.id.textthird);
        final EditText teamfour = (EditText) findViewById(R.id.textfour);

        SegmentedGroup segmented2 = (SegmentedGroup) findViewById(R.id.teamsnumber);
        segmented2.setTintColor(Color.parseColor("#1db375"), Color.parseColor("#ffffff"));

        final SegmentedGroup segmented3 = (SegmentedGroup) findViewById(R.id.usernumber);
        segmented3.setTintColor(Color.parseColor("#1db375"), Color.parseColor("#ffffff"));

        segmented3.setVisibility(View.GONE);
        teamone.setVisibility(View.GONE);
        teamtwo.setVisibility(View.GONE);
        teamthree.setVisibility(View.GONE);
        teamfour.setVisibility(View.GONE);
        findViewById(R.id.everyteam).setVisibility(View.GONE);
        findViewById(R.id.letsgo).setVisibility(View.GONE);
        findViewById(R.id.firstteamtext).setVisibility(View.GONE);
        findViewById(R.id.secondteamtext).setVisibility(View.GONE);
        findViewById(R.id.thirdteamtext).setVisibility(View.GONE);
        findViewById(R.id.fourteamtext).setVisibility(View.GONE);

        findViewById(R.id.two).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animations.clickeffect(view);
                GlobalVariables.flaginapp = true;
                Sounds.simplebuttons(getBaseContext());
                findViewById(R.id.everyteam).setVisibility(View.VISIBLE);
                segmented3.setVisibility(View.VISIBLE);
                GlobalVariables.numberofTeams=2;
                teamone.setVisibility(View.VISIBLE);
                teamtwo.setVisibility(View.VISIBLE);
                teamthree.setVisibility(View.GONE);
                teamfour.setVisibility(View.GONE);
                findViewById(R.id.letsgo).setVisibility(View.VISIBLE);
                findViewById(R.id.firstteamtext).setVisibility(View.VISIBLE);
                findViewById(R.id.secondteamtext).setVisibility(View.VISIBLE);
                findViewById(R.id.thirdteamtext).setVisibility(View.GONE);
                findViewById(R.id.fourteamtext).setVisibility(View.GONE);
            }
        });
        findViewById(R.id.three).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animations.clickeffect(view);
                GlobalVariables.flaginapp = true;
                Sounds.simplebuttons(getBaseContext());
                findViewById(R.id.everyteam).setVisibility(View.VISIBLE);
                segmented3.setVisibility(View.VISIBLE);
                GlobalVariables.numberofTeams=3;
                teamone.setVisibility(View.VISIBLE);
                teamtwo.setVisibility(View.VISIBLE);
                teamthree.setVisibility(View.VISIBLE);
                teamfour.setVisibility(View.GONE);
                findViewById(R.id.letsgo).setVisibility(View.VISIBLE);
                findViewById(R.id.firstteamtext).setVisibility(View.VISIBLE);
                findViewById(R.id.secondteamtext).setVisibility(View.VISIBLE);
                findViewById(R.id.thirdteamtext).setVisibility(View.VISIBLE);
                findViewById(R.id.fourteamtext).setVisibility(View.GONE);
            }
        });
        findViewById(R.id.four).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animations.clickeffect(view);
                GlobalVariables.flaginapp = true;
                Sounds.simplebuttons(getBaseContext());
                findViewById(R.id.everyteam).setVisibility(View.VISIBLE);
                segmented3.setVisibility(View.VISIBLE);
                GlobalVariables.numberofTeams=4;
                teamone.setVisibility(View.VISIBLE);
                teamtwo.setVisibility(View.VISIBLE);
                teamthree.setVisibility(View.VISIBLE);
                teamfour.setVisibility(View.VISIBLE);
                findViewById(R.id.letsgo).setVisibility(View.VISIBLE);
                findViewById(R.id.firstteamtext).setVisibility(View.VISIBLE);
                findViewById(R.id.secondteamtext).setVisibility(View.VISIBLE);
                findViewById(R.id.thirdteamtext).setVisibility(View.VISIBLE);
                findViewById(R.id.fourteamtext).setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.oneperson).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animations.clickeffect(view);
                GlobalVariables.flaginapp = true;
                Sounds.simplebuttons(getBaseContext());
                GlobalVariables.numberofusers=1;
            }
        });

        findViewById(R.id.twoperson).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animations.clickeffect(view);
                GlobalVariables.flaginapp = true;
                Sounds.simplebuttons(getBaseContext());
                GlobalVariables.numberofusers=2;
            }
        });

        findViewById(R.id.threeperson).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animations.clickeffect(view);
                GlobalVariables.flaginapp = true;
                Sounds.simplebuttons(getBaseContext());
                GlobalVariables.numberofusers=3;
            }
        });

        findViewById(R.id.fourperson).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animations.clickeffect(view);
                GlobalVariables.flaginapp = true;
                Sounds.simplebuttons(getBaseContext());
                GlobalVariables.numberofusers=4;
            }
        });

        findViewById(R.id.fiveperson).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animations.clickeffect(view);
                GlobalVariables.flaginapp = true;
                Sounds.simplebuttons(getBaseContext());
                GlobalVariables.numberofusers=5;
            }
        });

        findViewById(R.id.sixperson).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animations.clickeffect(view);
                GlobalVariables.flaginapp = true;
                Sounds.simplebuttons(getBaseContext());
                GlobalVariables.numberofusers=6;
            }
        });

        findViewById(R.id.letsgo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animations.clickeffect(view);
                GlobalVariables.flaginapp = true;
                Sounds.simplebuttons(getBaseContext());
                if(GlobalVariables.numberofTeams==0)
                    Toast.makeText(TeamsActivity.this,"لطفا تعداد تیم ها را انتخاب کنید",Toast.LENGTH_LONG).show();
                else if(GlobalVariables.numberofusers==0)
                    Toast.makeText(TeamsActivity.this,"لطفا تعداد نفرات هر تیم را انتخاب کنید",Toast.LENGTH_LONG).show();
                else
                    validation.validateinputteam(GlobalVariables.numberofTeams,TeamsActivity.this);

            }
        });

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animations.clickeffect(view);
                GlobalVariables.flaginapp = true;
                Sounds.simplebuttons(getBaseContext());
                new SweetAlertDialog(TeamsActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("میخواین بازی را لغو کنین ؟")
                        .setConfirmText("بله")
                        .setCancelText("نه ، ادامه بدیم")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                GlobalVariables.flaginapp = true;
                                UiInit.gotopage(TeamsActivity.this,HomeActivity.class);
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
            Sounds.stopAudio(TeamsActivity.this);
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
            Sounds.playAudio(TeamsActivity.this);
        }
    }
}
