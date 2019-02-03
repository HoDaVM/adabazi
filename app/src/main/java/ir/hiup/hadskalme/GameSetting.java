package ir.hiup.hadskalme;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.Locale;

import app.minimize.com.seek_bar_compat.SeekBarCompat;
import info.hoang8f.android.segmented.SegmentedGroup;
import ir.hiup.hadskalme.UIinit.Animations.Animations;
import ir.hiup.hadskalme.UIinit.Dialogs.VideoRecorderDialoge;
import ir.hiup.hadskalme.UIinit.GlobalVariables;
import ir.hiup.hadskalme.UIinit.Sounds.Sounds;
import ir.hiup.hadskalme.UIinit.UiInit;
import ir.hiup.hadskalme.UIinit.validation;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class GameSetting extends AppCompatActivity {


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            GlobalVariables.flaginapp = true;
            GlobalVariables.fullreset();
            UiInit.gotopage(GameSetting.this,HomeActivity.class);
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
        setContentView(R.layout.activity_game_setting);
        UiInit.GameSetting(GameSetting.this);
        final NumberFormat nf= NumberFormat.getInstance(new Locale("fa","IR"));

        GlobalVariables.fullreset();

        VideoRecorderDialoge videoRecorderDialoge= new VideoRecorderDialoge(GameSetting.this);
        videoRecorderDialoge.show();

        SegmentedGroup segmented2 = (SegmentedGroup) findViewById(R.id.gamemode);
        segmented2.setTintColor(Color.parseColor("#1db375"), Color.parseColor("#ffffff"));

        //SegmentedGroup segmented3 = (SegmentedGroup) findViewById(R.id.videoselect);
        //segmented3.setTintColor(Color.parseColor("#1db375"), Color.parseColor("#ffffff"));

        SegmentedGroup segmented4 = (SegmentedGroup) findViewById(R.id.countselect);
        segmented4.setTintColor(Color.parseColor("#1db375"), Color.parseColor("#ffffff"));

        SeekBarCompat seekBar = (SeekBarCompat) findViewById(R.id.seekBar2);
        seekBar.setProgress(45);
        seekBar.setMax(180);

        final TextView editabletime = (TextView) findViewById(R.id.EditableTime);
        editabletime.setText(String.valueOf(nf.format(45))+" ثانیه ");
        GlobalVariables.Time=45;
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = ((int)Math.round(progress/45 ))*45;
                if(progress==0)
                    progress =45;
                GlobalVariables.Time=progress;
                seekBar.setProgress(progress);
                editabletime.setText(String.valueOf(nf.format(progress))+" ثانیه ");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        SeekBarCompat Doore = (SeekBarCompat) findViewById(R.id.door);
        Doore.setProgress(1);
        Doore.setMax(10);

        final TextView Door = (TextView) findViewById(R.id.EditableDoor);
        Door.setText(String.valueOf(nf.format(1))+" دور ");

        Doore.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress = ((int) Math.round(progress / 1));
                if(progress==0)
                    progress =1;
                GlobalVariables.dor=progress;
                seekBar.setProgress(progress);
                Door.setText(String.valueOf(nf.format(progress))+" دور ");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        findViewById(R.id.wordmode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animations.clickeffect(view);
                GlobalVariables.flaginapp = true;
                Sounds.simplebuttons(getBaseContext());
                GlobalVariables.typeofmatch=2;
            }
        });
        findViewById(R.id.imagemode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animations.clickeffect(view);
                GlobalVariables.flaginapp = true;
                Sounds.simplebuttons(getBaseContext());
                GlobalVariables.typeofmatch=1;
            }
        });

        if(!validation.isOnline(GameSetting.this))
            findViewById(R.id.imagemode).setVisibility(View.GONE);

        findViewById(R.id.wearetwo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animations.clickeffect(view);
                GlobalVariables.flaginapp = true;
                Sounds.simplebuttons(getBaseContext());
                GlobalVariables.numberofusers=2;
            }
        });
        findViewById(R.id.wearemore).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animations.clickeffect(view);
                GlobalVariables.flaginapp = true;
                Sounds.simplebuttons(getBaseContext());
                GlobalVariables.numberofusers=3;
            }
        });
       /* findViewById(R.id.yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animations.clickeffect(view);
                GlobalVariables.flaginapp = true;
                Sounds.simplebuttons(getBaseContext());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{
                                    android.Manifest.permission.CAMERA,
                                    android.Manifest.permission.RECORD_AUDIO,
                                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            MULTIPLE_PERMISSION_REQUEST);
                }
                GlobalVariables.videoRecord=1;
            }
        });
        findViewById(R.id.no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animations.clickeffect(view);
                GlobalVariables.flaginapp = true;
                Sounds.simplebuttons(getBaseContext());
                GlobalVariables.videoRecord=2;
            }
        });
*/
        findViewById(R.id.letsgo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animations.clickeffect(view);
                GlobalVariables.flaginapp = true;
                Sounds.simplebuttons(getBaseContext());
                if(GlobalVariables.typeofmatch==0)
                    Toast.makeText(GameSetting.this,"لطفا یکی از حالت های بازی را انتخاب کنید",Toast.LENGTH_LONG).show();
                else if(GlobalVariables.numberofusers==0)
                    Toast.makeText(GameSetting.this,"لطفا تعداد نفرات را انتخاب کنین",Toast.LENGTH_LONG).show();
                else if(GlobalVariables.videoRecord==0)
                    Toast.makeText(GameSetting.this,"لطفا وضعیت ضبط ویدئو را انتخاب کنین",Toast.LENGTH_LONG).show();
                else
                {
                    if(GlobalVariables.numberofusers==2)
                    {
                        GlobalVariables.totalcounter=2*GlobalVariables.dor;
                        GlobalVariables.numberofTeams=2;
                        GlobalVariables.numberofusers=1;
                        GlobalVariables.twotype=true;
                        UiInit.gotopage(GameSetting.this,SubjectActivity.class);
                        finish();
                    }
                    else
                    {
                        UiInit.gotopage(GameSetting.this,TeamsActivity.class);
                        finish();
                    }
                }
            }
        });

       findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Animations.clickeffect(view);
               GlobalVariables.flaginapp = true;
               Sounds.simplebuttons(getBaseContext());
               GlobalVariables.fullreset();
               UiInit.gotopage(GameSetting.this,HomeActivity.class);
               finish();
           }
       });
    }
    @Override
    public void onPause() {
        super.onPause();

        if (!GlobalVariables.flaginapp) {
            Sounds.stopAudio(GameSetting.this);
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
            Sounds.playAudio(GameSetting.this);
        }
    }


}
