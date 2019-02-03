package ir.hiup.hadskalme;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.os.Vibrator;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;
import ir.hiup.hadskalme.UIinit.Animations.Animations;
import ir.hiup.hadskalme.UIinit.DataBaseHelper;
import ir.hiup.hadskalme.UIinit.GlobalVariables;
import ir.hiup.hadskalme.UIinit.Sounds.Sounds;
import ir.hiup.hadskalme.UIinit.UiInit;
import ir.hiup.hadskalme.UIinit.validation;
import ir.hiup.hadskalme.connection.API;
import ir.hiup.hadskalme.connection.RestAdapter;
import ir.hiup.hadskalme.connection.callbacks.CallBackWord;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MatchActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    private static final String LOG_TAG = "Ada Bazi";
    CountDownTimer countDownTimer;
    MediaRecorder mediaRecorder;
    SurfaceHolder surfaceHolder;
    boolean recording;
    String pathOfVideo;
    private Camera mCamera;
    int cameraid=validation.openFrontalCamera();
    TextView textmodes;
    ImageView imagemode;
    String PIC;
    int ID;
    int POINT;
    long seconds;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new SweetAlertDialog(MatchActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("میخواین بازی را لغو کنین ؟")
                    .setConfirmText("بله")
                    .setCancelText("نه ، ادامه بدیم")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            if(cameraid!=-2)
                            {
                                stopvideo();
                            }
                            countDownTimer.cancel();
                            GlobalVariables.flaginapp = true;
                            UiInit.gotopage(MatchActivity.this,HomeActivity.class);
                            GlobalVariables.fullreset();
                        }
                    })
                    .show();
        }
        return false;
    }

    public static Camera openFrontalCamera() {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        for (int i = 0; i < Camera.getNumberOfCameras(); i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (Camera.CameraInfo.CAMERA_FACING_FRONT == cameraInfo.facing) {
                return Camera.open(i);
            }
        }
        return Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_match);
        UiInit.Match(MatchActivity.this);
        UiInit.settextofprepare(MatchActivity.this);
        UiInit.disableTextOrpic(MatchActivity.this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int CameraCheck = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA);
            int RecorderCheck = ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO);
            int WriteCheck = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (CameraCheck != PackageManager.PERMISSION_GRANTED && RecorderCheck != PackageManager.PERMISSION_GRANTED && WriteCheck != PackageManager.PERMISSION_GRANTED) {
                cameraid = -2;
                if (GlobalVariables.videoRecord == 1)
                    Toast.makeText(MatchActivity.this, "به دلیل عدم اجازه به دسترسی های لازم ، ضبط ویدئو انجام نمی شود.", Toast.LENGTH_LONG).show();
            }
        }
        if(GlobalVariables.typeofmatch==2)
            findViewById(R.id.changeword).setVisibility(View.GONE);

        textmodes = (TextView) findViewById(R.id.textmode);
        imagemode = (ImageView) findViewById(R.id.kidsmode);
        Intent intent = getIntent();
        if(GlobalVariables.typeofmatch==1)
            PIC = "TRUE";
        else
            PIC = "FALSE";

        ID = intent.getIntExtra("ID", 0);
        POINT = GlobalVariables.POINT;

        textmodes = (TextView) findViewById(R.id.textmode);
        imagemode = (ImageView) findViewById(R.id.kidsmode);

        if(validation.isOnline(MatchActivity.this))
        {
            final String[] result = new String[1];
            API api = RestAdapter.createAPI();
            Call<CallBackWord> callbackCall = api.GetWord(ID, PIC,POINT);
            callbackCall.enqueue(new Callback<CallBackWord>() {

                @Override
                public void onResponse(Call<CallBackWord> call, Response<CallBackWord> response) {
                    CallBackWord resp = response.body();
                    result[0] = resp.name;
                    if (GlobalVariables.typeofmatch==1) {
                        Picasso.with(getBaseContext()).load(result[0]).into(imagemode);
                    } else {
                        textmodes.setText(result[0]);
                    }
                }

                @Override
                public void onFailure(Call<CallBackWord> call, Throwable t) {
                    Toast.makeText(MatchActivity.this, "لطفا از اتصال اینترنت خود مطمئن شوید." + t, Toast.LENGTH_LONG).show();
                    UiInit.gotopage(MatchActivity.this,HomeActivity.class);
                }
            });
        }
        else if(validation.userCanOffline())
        {
            DataBaseHelper dbhelper = null;
            try {
                dbhelper = new DataBaseHelper(MatchActivity.this);
            } catch (IOException e) {
                e.printStackTrace();
            }
            textmodes.setText(dbhelper.getWord(POINT,ID));

        }

        if(GlobalVariables.typeofmatch==2)
        {
            if(validation.isOnline(MatchActivity.this))
            {
                final String[] result = new String[1];
                API api = RestAdapter.createAPI();
                Call<CallBackWord> callbackCall = api.GetWord(ID, PIC,POINT);
                callbackCall.enqueue(new Callback<CallBackWord>() {

                    @Override
                    public void onResponse(Call<CallBackWord> call, Response<CallBackWord> response) {
                        CallBackWord resp = response.body();
                        result[0] = resp.name;
                        GlobalVariables.WORD=result[0];
                    }

                    @Override
                    public void onFailure(Call<CallBackWord> call, Throwable t) {
                        Toast.makeText(MatchActivity.this, "لطفا از اتصال اینترنت خود مطمئن شوید." + t, Toast.LENGTH_LONG).show();
                        UiInit.gotopage(MatchActivity.this,HomeActivity.class);
                    }
                });
            }
            else if(validation.userCanOffline())
            {

                DataBaseHelper dbhelper = null;
                try {
                    dbhelper = new DataBaseHelper(MatchActivity.this);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                GlobalVariables.WORD=dbhelper.getWord(POINT,ID);

            }
        }

        findViewById(R.id.cancelgame).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animations.clickeffect(view);
                GlobalVariables.flaginapp = true;
                Sounds.simplebuttons(getBaseContext());
                new SweetAlertDialog(MatchActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("میخواین بازی را لغو کنین ؟")
                        .setConfirmText("بله")
                        .setCancelText("نه ، ادامه بدیم")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                if(cameraid!=-2)
                                {
                                    stopvideo();
                                }
                                countDownTimer.cancel();
                                GlobalVariables.flaginapp = true;
                                UiInit.gotopage(MatchActivity.this,HomeActivity.class);
                                GlobalVariables.fullreset();
                                finish();
                            }
                        })
                        .show();
                //Sounds.simplebuttons(getBaseContext());
                GlobalVariables.flaginapp=true;
            }
        });
        findViewById(R.id.okgame).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animations.clickeffect(view);
                GlobalVariables.flaginapp = true;
                Sounds.simplebuttons(getBaseContext());
                countDownTimer.cancel();
                CalculatePoints();
                //Sounds.simplebuttons(getBaseContext());
                GlobalVariables.flaginapp=true;
            }
        });

        SurfaceView myVideoView = (SurfaceView) findViewById(R.id.surface);

        if(cameraid!=-2)
        {
            recording = false;
            try {
                releaseCameraAndPreview();
                mCamera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
                mediaRecorder = new MediaRecorder();
                mediaRecorder.setCamera(mCamera);
                pathOfVideo = initMediaRecorder();
                String mystring;
                if(GlobalVariables.numberofusers==2)
                {
                    mystring= "نفر "+GlobalVariables.period;
                }
                else
                {
                    mystring= " نفر "+GlobalVariables.period+" از تیم "+ validation.getTeamname(GlobalVariables.counter);
                }
                validation.saveOndb(pathOfVideo,mystring,MatchActivity.this);
                surfaceHolder = myVideoView.getHolder();
                surfaceHolder.addCallback(this);
                surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

            } catch (Exception e) {
                Log.e(getString(R.string.app_name), "failed to open Camera");
                findViewById(R.id.surface).setVisibility(View.GONE);
                e.printStackTrace();
            }



        }
        else
        {
            findViewById(R.id.surface).setVisibility(View.GONE);
        }

        findViewById(R.id.sure).setOnClickListener(prepare);
        findViewById(R.id.fault).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animations.clickeffect(view);
                GlobalVariables.flaginapp = true;
                Sounds.FaultSound(getBaseContext());
                switch (GlobalVariables.wichteam) {
                    case 1:
                        GlobalVariables.teamonefault+=1;
                        break;
                    case 2:
                        GlobalVariables.teamtwofault+=1;
                        break;
                    case 3:
                        GlobalVariables.teamthreefault+=1;
                        break;
                    case 4:
                        GlobalVariables.teamfourfault+=1;
                        break;
                }
                Toast.makeText(MatchActivity.this,"خطا ثبت شد",Toast.LENGTH_LONG).show();
            }
        });

        if(GlobalVariables.typeofmatch==1)
            findViewById(R.id.changeword).setVisibility(View.GONE);
        else
            findViewById(R.id.changeword).setVisibility(View.VISIBLE);


        findViewById(R.id.changeword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animations.clickeffect(view);
                GlobalVariables.flaginapp = true;
                Sounds.ChangeWordSound(getBaseContext());
                TextView changer = (TextView) findViewById(R.id.textmode);
                findViewById(R.id.changeword).setClickable(false);
                switch (GlobalVariables.wichteam) {
                    case 1:
                        if(GlobalVariables.teamonepoints>=3)
                        {
                            GlobalVariables.teamonechangedword+=1;
                            changer.setText(String.valueOf(GlobalVariables.WORD));
                            Toast.makeText(MatchActivity.this,"کلمه با کسر 3 امتیاز عوض شد",Toast.LENGTH_LONG).show();
                        }
                        else
                            Toast.makeText(MatchActivity.this,"امتیاز شما برای تعویض کلمه کافی نمی باشد.",Toast.LENGTH_LONG).show();

                        break;
                    case 2:
                        if(GlobalVariables.teamtwopoints>=3)
                        {
                            GlobalVariables.teamtwochangedword+=1;
                            changer.setText(String.valueOf(GlobalVariables.WORD));
                            Toast.makeText(MatchActivity.this,"کلمه با کسر 3 امتیاز عوض شد",Toast.LENGTH_LONG).show();
                        }
                        else
                            Toast.makeText(MatchActivity.this,"امتیاز شما برای تعویض کلمه کافی نمی باشد.",Toast.LENGTH_LONG).show();

                        break;
                    case 3:
                        if(GlobalVariables.teamthreepoints>=3)
                        {
                            GlobalVariables.teamthreechangedword+=1;
                            changer.setText(String.valueOf(GlobalVariables.WORD));
                            Toast.makeText(MatchActivity.this,"کلمه با کسر 3 امتیاز عوض شد",Toast.LENGTH_LONG).show();
                        }
                        else
                            Toast.makeText(MatchActivity.this,"امتیاز شما برای تعویض کلمه کافی نمی باشد.",Toast.LENGTH_LONG).show();

                        break;
                    case 4:
                        if(GlobalVariables.teamfourpoints>=3)
                        {
                            GlobalVariables.teamfourchangedword+=1;
                            changer.setText(String.valueOf(GlobalVariables.WORD));
                            Toast.makeText(MatchActivity.this,"کلمه با کسر 3 امتیاز عوض شد",Toast.LENGTH_LONG).show();
                        }
                        else
                            Toast.makeText(MatchActivity.this,"امتیاز شما برای تعویض کلمه کافی نمی باشد.",Toast.LENGTH_LONG).show();

                        break;

                }

            }
        });
    }
    private void releaseCameraAndPreview() {

        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }
    View.OnClickListener prepare = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            findViewById(R.id.prepaare).setVisibility(View.GONE);
            Animations.clickeffect(view);
            GlobalVariables.flaginapp = true;
            Sounds.simplebuttons(getBaseContext());
            countDownTimer = new CountDownTimer(GlobalVariables.Time * 1000, 500) {
                // 500 means, onTick function will be called at every 500 milliseconds
                NumberFormat nf=NumberFormat.getInstance(new Locale("fa","EG"));
                TextView textTimer;
                int a=0;
                @Override
                public void onTick(long leftTimeInMilliseconds) {
                    seconds = leftTimeInMilliseconds / 1000;
                    textTimer = (TextView) findViewById(R.id.bartext);
                    textTimer.setText( nf.format(seconds) + " ثانیه " );
                    if(a==0 && seconds<5)
                    {
                        Sounds.CountdownSound(getBaseContext());
                        a=1;
                    }
                }

                @Override
                public void onFinish() {
                    CalculatePoints();
                }
            };
            if(cameraid!=-2)
            {
                startvideo();
            }
            else
            {
                countDownTimer.start();
            }
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            v.vibrate(500);

        }
    };

    public void stopvideo() {
        recording = false;
        try {

            mediaRecorder.stop();
            mediaRecorder.reset();

            finish();
        } catch (Exception e) {
            Log.e(LOG_TAG, "Failed to stop recorder", e);
        }
        mediaRecorder.release();
        mediaRecorder = null;
        if (mCamera != null) {
            mCamera.lock();
            mCamera.release();
            mCamera = null;
        }
    }

    public void startvideo() {
        mCamera.unlock();
        countDownTimer.start();
        mediaRecorder.start();
        recording = true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (mCamera != null) {
            Camera.Parameters params = mCamera.getParameters();
            mCamera.setParameters(params);
            try {
                mCamera.setPreviewDisplay(holder);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mCamera.startPreview();
        }
        prepareMediaRecorder();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {

    }

    private String initMediaRecorder() {
        mediaRecorder.setCamera(mCamera);
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
        mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
        mediaRecorder.setVideoFrameRate(15);
        CamcorderProfile camcorderProfile = CamcorderProfile.get(Camera.CameraInfo.CAMERA_FACING_FRONT, CamcorderProfile.QUALITY_HIGH);  //get your own profile
        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setPreviewSize(camcorderProfile.videoFrameWidth, camcorderProfile.videoFrameHeight);
        mCamera.setParameters(parameters);
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_MOVIES), "hadskalme" + System.currentTimeMillis() + ".mp4");
        if (file.exists()) {
            file.delete();
        }
        mediaRecorder.setOutputFile(file.getAbsolutePath());

        mediaRecorder.setMaxDuration(150); // Set max duration 90 sec.
        mediaRecorder.setMaxFileSize(15000000); // Set max file size 15M

        return String.valueOf(file.getAbsolutePath());
    }

    private void prepareMediaRecorder() {

        mediaRecorder.setPreviewDisplay(surfaceHolder.getSurface());

        try {
            mediaRecorder.prepare();
        } catch (IllegalStateException | IOException e) {
            Log.e(LOG_TAG, "  to prepare recorder", e);
        }
    }
    void CalculatePoints()
    {

        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(500);
        if(seconds!=0) {
            switch (GlobalVariables.wichteam) {
                case 1:
                    GlobalVariables.teamonepoints+=GlobalVariables.POINT;
                    GlobalVariables.teamonetime+=seconds;
                    break;
                case 2:
                    GlobalVariables.teamtwopoints+=GlobalVariables.POINT;
                    GlobalVariables.teamtwotime+=seconds;
                    break;
                case 3:
                    GlobalVariables.teamthreepoints+=GlobalVariables.POINT;
                    GlobalVariables.teamthreetime+=seconds;
                    break;
                case 4:
                    GlobalVariables.teamfourpoints+=GlobalVariables.POINT;
                    GlobalVariables.teamfourtime+=seconds;
                    break;
            }
        }


        if(GlobalVariables.totalcounter==GlobalVariables.countershomar)
        {
            UiInit.gotopage(MatchActivity.this,ResultActivity.class);
            finish();
        }
        else
        {
            if(GlobalVariables.whichuser==GlobalVariables.numberofusers && GlobalVariables.wichteam==GlobalVariables.numberofTeams)
            {
                GlobalVariables.wichteam=1;
                GlobalVariables.whichuser=1;
            }
            else if(GlobalVariables.wichteam==GlobalVariables.numberofTeams)
            {
                GlobalVariables.wichteam=1;
                GlobalVariables.whichuser+=1;
            }
            else
                GlobalVariables.wichteam+=1;

            GlobalVariables.countershomar+=1;

            UiInit.gotopage(MatchActivity.this,SubjectActivity.class);
            finish();
        }


    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mCamera != null)
            stopvideo();
    }

    @Override
    public void onPause() {
        super.onPause();

        if (!GlobalVariables.flaginapp) {
            Sounds.stopAudio(MatchActivity.this);
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
            Sounds.playAudio(MatchActivity.this);
        }
    }
}
