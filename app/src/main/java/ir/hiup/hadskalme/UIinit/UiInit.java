package ir.hiup.hadskalme.UIinit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.backtory.java.internal.BacktoryCallBack;
import com.backtory.java.internal.BacktoryLeaderBoard;
import com.backtory.java.internal.BacktoryResponse;
import com.backtory.java.internal.BacktoryUser;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;
import ir.hiup.hadskalme.HomeActivity;
import ir.hiup.hadskalme.MatchActivity;
import ir.hiup.hadskalme.R;
import ir.hiup.hadskalme.UIinit.Animations.Animations;
import ir.hiup.hadskalme.UIinit.Dialogs.SendWordDialoge;
import ir.hiup.hadskalme.UIinit.Sounds.Sounds;
import ir.hiup.hadskalme.VideosActivity;
import ir.hiup.hadskalme.connection.API;
import ir.hiup.hadskalme.connection.RestAdapter;
import ir.hiup.hadskalme.connection.callbacks.CallBackWord;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static ir.hiup.hadskalme.UIinit.validation.shareVideo;

/**
 * Created by Mahdi Asiyabi on 9/22/2017.
 */

public class UiInit {

    public static void gotopage(Activity act,Class newAct)
    {
        GlobalVariables.flaginapp=true;
        Intent i = new Intent(act.getBaseContext(), newAct);
        act.startActivity(i);
    }
    public static void clickfunc(final Activity act, final Class newAct, final int ID)
    {
        act.findViewById(ID).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animations.clickeffect(act.findViewById(ID));
                gotopage(act,newAct);
                Sounds.simplebuttons(act.getBaseContext());
                GlobalVariables.flaginapp=true;
            }
        });
    }

    @SuppressLint("CutPasteId")
    public static void Splash(Activity activity)
    {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

        activity.findViewById(R.id.splashlogo).getLayoutParams().width=width/2-20;
        activity.findViewById(R.id.splashlogo).getLayoutParams().height=height/4+20;

        activity.findViewById(R.id.gameversion).getLayoutParams().width=width/4-20;
        activity.findViewById(R.id.gameversion).getLayoutParams().height=height/10;
    }

    @SuppressLint("CutPasteId")
    public static void Register(Activity activity)
    {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

        activity.findViewById(R.id.applogo).getLayoutParams().width=width-30;
        activity.findViewById(R.id.applogo).getLayoutParams().height=height/4;

        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) activity.findViewById(R.id.applogo).getLayoutParams();
        params.setMargins(0,height/9,0,0);
        activity.findViewById(R.id.applogo).setLayoutParams(params);

        activity.findViewById(R.id.textregister).getLayoutParams().width=width-30;
        activity.findViewById(R.id.textregister).getLayoutParams().height=height/4;

        activity.findViewById(R.id.register).getLayoutParams().width=width/2;
        activity.findViewById(R.id.register).getLayoutParams().height=height/11;

    }

    @SuppressLint("CutPasteId")
    public static void Home(Activity activity)
    {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

        activity.findViewById(R.id.applogo).getLayoutParams().width=width-30;
        activity.findViewById(R.id.applogo).getLayoutParams().height=height/4;

        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) activity.findViewById(R.id.applogo).getLayoutParams();
        params.setMargins(0,height/10,0,0);
        activity.findViewById(R.id.applogo).setLayoutParams(params);

        activity.findViewById(R.id.charachter).getLayoutParams().width=width;
        activity.findViewById(R.id.charachter).getLayoutParams().height=height/2+35;

        activity.findViewById(R.id.startgame).getLayoutParams().width=width/5;
        activity.findViewById(R.id.startgame).getLayoutParams().height=width/5;

        activity.findViewById(R.id.offlinemode).getLayoutParams().width=width/3+10;
        activity.findViewById(R.id.offlinemode).getLayoutParams().height=width/9;


        ConstraintLayout.LayoutParams paramsTwo = (ConstraintLayout.LayoutParams) activity.findViewById(R.id.offlinemode).getLayoutParams();
        paramsTwo.setMargins(width/8,height/8+15,0,0);
        activity.findViewById(R.id.offlinemode).setLayoutParams(paramsTwo);

        activity.findViewById(R.id.music).getLayoutParams().width=width/3+10;
        activity.findViewById(R.id.music).getLayoutParams().height=width/9;

        activity.findViewById(R.id.sendword).getLayoutParams().width=width/3+10;
        activity.findViewById(R.id.sendword).getLayoutParams().height=width/9;

        activity.findViewById(R.id.goftogo).getLayoutParams().width=width/3+10;
        activity.findViewById(R.id.goftogo).getLayoutParams().height=width/9;

        activity.findViewById(R.id.myvideos).getLayoutParams().width=width/3+10;
        activity.findViewById(R.id.myvideos).getLayoutParams().height=width/9;

        ConstraintLayout.LayoutParams paramsThree = (ConstraintLayout.LayoutParams) activity.findViewById(R.id.myvideos).getLayoutParams();
        paramsThree.setMargins(0,height/30,width/16,0);
        activity.findViewById(R.id.myvideos).setLayoutParams(paramsThree);

    }

    @SuppressLint("CutPasteId")
    public static void GameSetting(Activity activity)
    {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

        activity.findViewById(R.id.seekBar2).getLayoutParams().width=width-30;

        activity.findViewById(R.id.back).getLayoutParams().width=width/3;
        activity.findViewById(R.id.back).getLayoutParams().height=width/5;

        ConstraintLayout.LayoutParams paramsBack = (ConstraintLayout.LayoutParams) activity.findViewById(R.id.back).getLayoutParams();
        paramsBack.setMargins(0,10,0,0);
        activity.findViewById(R.id.back).setLayoutParams(paramsBack);

        activity.findViewById(R.id.wordmode).getLayoutParams().width=width/2-30;
        activity.findViewById(R.id.imagemode).getLayoutParams().width=width/2-30;

        activity.findViewById(R.id.wearemore).getLayoutParams().width=width/2-30;
        activity.findViewById(R.id.wearetwo).getLayoutParams().width=width/2-30;

       // activity.findViewById(R.id.yes).getLayoutParams().width=width/2-30;
       // activity.findViewById(R.id.no).getLayoutParams().width=width/2-30;

        activity.findViewById(R.id.letsgo).getLayoutParams().width=width/2-30;
    }

    @SuppressLint("CutPasteId")
    public static void TeamsActivity(Activity activity)
    {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;


        activity.findViewById(R.id.back).getLayoutParams().width=width/3;
        activity.findViewById(R.id.back).getLayoutParams().height=width/5;

        ConstraintLayout.LayoutParams paramsBack = (ConstraintLayout.LayoutParams) activity.findViewById(R.id.back).getLayoutParams();
        paramsBack.setMargins(0,10,0,0);
        activity.findViewById(R.id.back).setLayoutParams(paramsBack);

        activity.findViewById(R.id.two).getLayoutParams().width=width/3-10;
        activity.findViewById(R.id.three).getLayoutParams().width=width/3-10;
        activity.findViewById(R.id.four).getLayoutParams().width=width/3-10;

        activity.findViewById(R.id.oneperson).getLayoutParams().width=width/6-5;
        activity.findViewById(R.id.twoperson).getLayoutParams().width=width/6-5;
        activity.findViewById(R.id.threeperson).getLayoutParams().width=width/6-5;
        activity.findViewById(R.id.fourperson).getLayoutParams().width=width/6-5;
        activity.findViewById(R.id.fiveperson).getLayoutParams().width=width/6-5;
        activity.findViewById(R.id.sixperson).getLayoutParams().width=width/6-5;

        activity.findViewById(R.id.textfirst).getLayoutParams().width=width/2-25;
        activity.findViewById(R.id.textsecond).getLayoutParams().width=width/2-35;
        activity.findViewById(R.id.textthird).getLayoutParams().width=width/2-25;
        activity.findViewById(R.id.textfour).getLayoutParams().width=width/2-35;

        activity.findViewById(R.id.letsgo).getLayoutParams().width=width/2-30;
    }

    @SuppressLint("CutPasteId")
    public static void Subject(final Activity activity)
    {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

        activity.findViewById(R.id.back).getLayoutParams().width=width/3;
        activity.findViewById(R.id.back).getLayoutParams().height=width/5;

        ConstraintLayout.LayoutParams paramsBack = (ConstraintLayout.LayoutParams) activity.findViewById(R.id.back).getLayoutParams();
        paramsBack.setMargins(0,10,0,0);
        activity.findViewById(R.id.back).setLayoutParams(paramsBack);

        activity.findViewById(R.id.one).getLayoutParams().width=width/3-10;
        activity.findViewById(R.id.three).getLayoutParams().width=width/3-10;
        activity.findViewById(R.id.six).getLayoutParams().width=width/3-10;
        LayoutInflater vi = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Typeface face = Typeface.createFromAsset(activity.getAssets(),
                "fonts/IRANSANS.TTF");

        activity.findViewById(R.id.lnrelanss).getLayoutParams().width=width/2;
        activity.findViewById(R.id.lnrelans).getLayoutParams().width=width/2;

        int ii = 0;
        if(GlobalVariables.typeofmatch!=1)
        {
            while (ii<15) {
                final View ve = vi.inflate(R.layout.subjecttemplate, null, false);
                // fill in any details dynamically here
                TextView txt1 = (TextView) ve.findViewById(R.id.textsubject);
                txt1.setText(String.valueOf(GlobalVariables.title[ii]));
                ve.findViewById(R.id.parentsubject).getLayoutParams().width=width/2-50;
                ve.findViewById(R.id.parentsubject).getLayoutParams().height=width/5-20;
                txt1.setTypeface(face);
                final int finalIi1;
                if(ii<8)
                    finalIi1= ii+1;
                else
                    finalIi1=ii+5;

                ve.findViewById(R.id.parentsubject).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Animations.clickeffect(ve.findViewById(R.id.parentsubject));
                        Intent intent = new Intent(activity, MatchActivity.class);
                        Bundle b = new Bundle();
                        b.putInt("ID", finalIi1);
                        intent.putExtras(b);
                        activity.startActivity(intent);
                        Sounds.simplebuttons(activity.getBaseContext());
                        GlobalVariables.flaginapp=true;
                        activity.finish();
                    }
                });

                // insert into main view
                if(ii%2==0)
                {
                    ViewGroup insertPoint = (ViewGroup) activity.findViewById(R.id.lnrelanss);
                    ve.setLayoutParams(new ViewGroup.LayoutParams(width/2, width/5));
                    insertPoint.addView(ve);
                }
                else
                {
                    ViewGroup insertPoint = (ViewGroup) activity.findViewById(R.id.lnrelans);
                    ve.setLayoutParams(new ViewGroup.LayoutParams(width/2-35, width/5));
                    insertPoint.addView(ve);
                }
                ii++;
            }
        }
        else
        {
            while (ii<6) {
                final View ve = vi.inflate(R.layout.subjecttemplate, null, false);
                // fill in any details dynamically here
                TextView txt1 = (TextView) ve.findViewById(R.id.textsubject);
                txt1.setText(String.valueOf(GlobalVariables.kids[ii]));
                ve.findViewById(R.id.parentsubject).getLayoutParams().width=width/2-50;
                ve.findViewById(R.id.parentsubject).getLayoutParams().height=width/5-20;
                txt1.setTypeface(face);
                int finalIi=1;
                switch (GlobalVariables.kids[ii])
                {
                    case "اشیاء":
                        finalIi=1;
                        break;
                    case "خوراکی ها":
                        finalIi=2;
                        break;
                    case "حیوانات":
                        finalIi=4;
                        break;
                    case "مشاغل":
                        finalIi=6;
                        break;
                    case "اسباب بازی":
                        finalIi=11;
                        break;
                    case "حروف الفبا":
                        finalIi=12;
                        break;
                }
                final int finalIi1 = finalIi;
                ve.findViewById(R.id.parentsubject).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Animations.clickeffect(ve.findViewById(R.id.parentsubject));
                        Intent intent = new Intent(activity, MatchActivity.class);
                        Bundle b = new Bundle();
                        b.putInt("ID", finalIi1);
                        intent.putExtras(b);
                        activity.startActivity(intent);
                        Sounds.simplebuttons(activity.getBaseContext());
                        GlobalVariables.flaginapp=true;
                        activity.finish();
                    }
                });
                // insert into main view
                if(ii%2==0)
                {
                    ViewGroup insertPoint = (ViewGroup) activity.findViewById(R.id.lnrelanss);
                    ve.setLayoutParams(new ViewGroup.LayoutParams(width/2, width/5));
                    insertPoint.addView(ve);
                }
                else
                {
                    ViewGroup insertPoint = (ViewGroup) activity.findViewById(R.id.lnrelans);
                    ve.setLayoutParams(new ViewGroup.LayoutParams(width/2-35, width/5));
                    insertPoint.addView(ve);
                }
                ii++;
            }
        }
    }

    @SuppressLint("CutPasteId")
    public static void Match(Activity activity) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

        activity.findViewById(R.id.okgame).getLayoutParams().width = ((width * 2) / 7) - 10;
        activity.findViewById(R.id.okgame).getLayoutParams().height = height / 6;

        activity.findViewById(R.id.cancelgame).getLayoutParams().width = ((width * 2) / 7) - 10;
        activity.findViewById(R.id.cancelgame).getLayoutParams().height = height / 6;

        activity.findViewById(R.id.changeword).getLayoutParams().width = ((width * 2) / 7) - 10;
        activity.findViewById(R.id.changeword).getLayoutParams().height = height / 6;

        activity.findViewById(R.id.fault).getLayoutParams().width = ((width * 2) / 7) - 10;
        activity.findViewById(R.id.fault).getLayoutParams().height = height / 6;

        activity.findViewById(R.id.imageView).getLayoutParams().width=width-20;
        activity.findViewById(R.id.imageView).getLayoutParams().height=height/3;

        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) activity.findViewById(R.id.imageView).getLayoutParams();
        params.setMargins(0,height/18,0,0);
        activity.findViewById(R.id.imageView).setLayoutParams(params);

        ConstraintLayout.LayoutParams paramstw = (ConstraintLayout.LayoutParams) activity.findViewById(R.id.constone).getLayoutParams();
        paramstw.setMargins(0,height/18,0,0);
        activity.findViewById(R.id.constone).setLayoutParams(paramstw);

        ConstraintLayout.LayoutParams paramsth = (ConstraintLayout.LayoutParams) activity.findViewById(R.id.sure).getLayoutParams();
        paramsth.setMargins(0,height/18,0,0);
        activity.findViewById(R.id.sure).setLayoutParams(paramsth);
    }

    @SuppressLint("CutPasteId")
    public static void VideosActivity(final Activity act)
    {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        act.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;


        act.findViewById(R.id.titlevideo).getLayoutParams().width=width/3-20;
        act.findViewById(R.id.titlevideo).getLayoutParams().height=height/7;

        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) act.findViewById(R.id.titlevideo).getLayoutParams();
        params.setMargins(0,0,30,0);
        act.findViewById(R.id.titlevideo).setLayoutParams(params);

        act.findViewById(R.id.back).getLayoutParams().width=width/3;
        act.findViewById(R.id.back).getLayoutParams().height=width/5;

        ConstraintLayout.LayoutParams paramsBack = (ConstraintLayout.LayoutParams) act.findViewById(R.id.back).getLayoutParams();
        paramsBack.setMargins(0,10,0,0);
        act.findViewById(R.id.back).setLayoutParams(paramsBack);

        SQLiteDatabase db;
        db = act.openOrCreateDatabase("HadsKalme", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS videos (word VARCHAR,path TEXT,regdate VARCHAR);");

        final Cursor c = db.rawQuery("SELECT * FROM videos ORDER BY regdate DESC", null);
        if(c.getCount() != 0) {
            int i = 0;
            LayoutInflater vi = (LayoutInflater) act.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            Typeface face = Typeface.createFromAsset(act.getAssets(),
                    "fonts/IRANSANS.TTF");
            while (c.moveToNext()) {
                View ve = vi.inflate(R.layout.videostemplate, null, false);
                // fill in any details dynamically here
                TextView txt1 = (TextView) ve.findViewById(R.id.title);
                txt1.setText(" ویدئوی " + c.getString(0));
                TextView txt2 = (TextView) ve.findViewById(R.id.date);
                txt2.setText(" در   " + c.getString(2));
                txt2.setTypeface(face);
                txt1.setTypeface(face);
                ImageView img = (ImageView) ve.findViewById(R.id.img);
                Bitmap thumbnail = ThumbnailUtils.createVideoThumbnail(c.getString(1),
                        MediaStore.Images.Thumbnails.MINI_KIND);
                img.setImageBitmap(thumbnail);
                ImageView player = (ImageView) ve.findViewById(R.id.playvideo);
                final String[] cursorUri = {c.getString(1)};
                final String[] cursorUriT = {c.getString(1)};
                ImageView deleteVideo = (ImageView) ve.findViewById(R.id.deletevideo);
                deleteVideo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Animations.clickeffect(view);
                        new SweetAlertDialog(act, SweetAlertDialog.NORMAL_TYPE)
                                .setTitleText("توجه")
                                .setContentText("آیا از حذف این ویدئو مطمئن هستین ؟")
                                .setConfirmText("آره")
                                .setCancelText("نه")
                                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                                        sweetAlertDialog.dismissWithAnimation();
                                    }
                                })
                                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {

                                        Toast.makeText(act,"ویدئو با موفقیت حذف شد",Toast.LENGTH_LONG).show();
                                        final SQLiteDatabase dbt;
                                        dbt = act.openOrCreateDatabase("HadsKalme", Context.MODE_PRIVATE, null);
                                        dbt.execSQL("DELETE FROM videos WHERE path = '" + cursorUriT[0] + "'");
                                        gotopage(act,VideosActivity.class);
                                        act.finish();
                                        sDialog.dismissWithAnimation();

                                    }
                                })
                                .show();
                        //Sounds.simplebuttons(act);
                        GlobalVariables.flaginapp=true;
                    }
                });
                cursorUri[0] = "file://" + cursorUri[0];
                player.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        try {

                            Intent mVideoWatch = new Intent(Intent.ACTION_VIEW);
                            mVideoWatch.setDataAndType(Uri.parse(cursorUri[0]), "video/mp4");
                            act.startActivity(mVideoWatch);

                        } catch (Exception e) {
                            Log.e("HadsKalme", e.getMessage());
                        }
                    }
                });

                ImageView share = (ImageView) ve.findViewById(R.id.sharevideo);
                share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        shareVideo(cursorUri[0], act);
                    }
                });
                // insert into main view
                ViewGroup insertPoint = (ViewGroup) act.findViewById(R.id.lnrelans);
                ve.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, WRAP_CONTENT));
                insertPoint.addView(ve);
                i++;
            }
            c.close();
        }
    }

    public static void Result(final Activity activity)
    {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;

        activity.findViewById(R.id.replay).getLayoutParams().width=width/2-20;
        activity.findViewById(R.id.baacktomain).getLayoutParams().width=width/2-20;

        activity.findViewById(R.id.scrollview).getLayoutParams().height=height-(height/5);

        LayoutInflater vi = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Typeface face = Typeface.createFromAsset(activity.getAssets(),
                "fonts/IRANSANS.TTF");

        int ii = 0;
        while (ii<GlobalVariables.numberofTeams) {
            final View ve = vi.inflate(R.layout.resultrow, null, false);


            // fill in any details dynamically here
            final NumberFormat nf= NumberFormat.getInstance(new Locale("fa","IR"));

            TextView NameTeam = (TextView) ve.findViewById(R.id.NameTeam);
            if(!GlobalVariables.twotype)
                NameTeam.setText(validation.getTeamname(ii+1));
            NameTeam.setTypeface(face);

            TextView editableEmtiaz = (TextView) ve.findViewById(R.id.editableEmtiaz);
            TextView editablekhata = (TextView) ve.findViewById(R.id.editablekhata);
            TextView editableytime = (TextView) ve.findViewById(R.id.editableytime);
            TextView editablepointezafi = (TextView) ve.findViewById(R.id.editablepointezafi);
            TextView editabletotalpoint = (TextView) ve.findViewById(R.id.editabletotalpoint);

            if(ii==0)
            {
                if(GlobalVariables.twotype)
                    NameTeam.setText("نفر اول");
                editableEmtiaz.setText(String.valueOf(nf.format(GlobalVariables.teamonepoints)));
                editablekhata.setText(String.valueOf(nf.format(GlobalVariables.teamonefault)));
                editableytime.setText(String.valueOf(nf.format(GlobalVariables.teamonetime))+" ثانیه ");
                editablepointezafi.setText(String.valueOf(nf.format(GlobalVariables.teamonetime/30)));
                editabletotalpoint.setText(String.valueOf(nf.format(GlobalVariables.teamonepoints-GlobalVariables.teamonefault+GlobalVariables.teamonetime/30-(GlobalVariables.teamonechangedword*3))));
            }
            else if(ii==1)
            {
                if(GlobalVariables.twotype)
                    NameTeam.setText("نفر دوم");
                editableEmtiaz.setText(String.valueOf(nf.format(GlobalVariables.teamtwopoints)));
                editablekhata.setText(String.valueOf(nf.format(GlobalVariables.teamtwofault)));
                editableytime.setText(String.valueOf(nf.format(GlobalVariables.teamtwotime))+" ثانیه ");
                editablepointezafi.setText(String.valueOf(nf.format(GlobalVariables.teamtwotime/30)));
                editabletotalpoint.setText(String.valueOf(nf.format(GlobalVariables.teamtwopoints-GlobalVariables.teamtwofault+GlobalVariables.teamtwotime/30-(GlobalVariables.teamtwochangedword*3))));
            }
            else if(ii==2)
            {
                editableEmtiaz.setText(String.valueOf(nf.format(GlobalVariables.teamthreepoints)));
                editablekhata.setText(String.valueOf(nf.format(GlobalVariables.teamthreefault)));
                editableytime.setText(String.valueOf(nf.format(GlobalVariables.teamthreetime))+" ثانیه ");
                editablepointezafi.setText(String.valueOf(nf.format(GlobalVariables.teamthreetime/30)));
                editabletotalpoint.setText(String.valueOf(nf.format(GlobalVariables.teamthreepoints-GlobalVariables.teamthreefault+GlobalVariables.teamthreetime/30-(GlobalVariables.teamthreechangedword*3))));
            }
            else if(ii==3)
            {
                editableEmtiaz.setText(String.valueOf(nf.format(GlobalVariables.teamfourpoints)));
                editablekhata.setText(String.valueOf(nf.format(GlobalVariables.teamfourfault)));
                editableytime.setText(String.valueOf(nf.format(GlobalVariables.teamfourtime))+" ثانیه ");
                editablepointezafi.setText(String.valueOf(nf.format(GlobalVariables.teamfourtime/30)));
                editabletotalpoint.setText(String.valueOf(nf.format(GlobalVariables.teamfourpoints-GlobalVariables.teamfourfault+GlobalVariables.teamfourtime/30-(GlobalVariables.teamfourchangedword*3))));
            }

            editableEmtiaz.setTypeface(face);
            editablekhata.setTypeface(face);
            editableytime.setTypeface(face);
            editablepointezafi.setTypeface(face);
            editabletotalpoint.setTypeface(face);


            ViewGroup insertPoint = (ViewGroup) activity.findViewById(R.id.lnrelans);
            ve.setLayoutParams(new ViewGroup.LayoutParams(width-10, height/3));
            insertPoint.addView(ve);

            ii++;
        }

    }

    public static void settextofprepare(final Activity act)
    {
        TextView txt = (TextView) act.findViewById(R.id.texttozih);
        String Text;
        if(GlobalVariables.twotype)
        {
            if(GlobalVariables.wichteam==1)
            {
                Text="اول";
            }
            else
            {
                Text="دوم";
            }
            txt.setText(" نفر "+Text+"  گوشی را بر روی پیشانی خود بگذارد و حدس بزند و \n نفر دیگر پانتومیم اجرا کند");
        }
        else
        {
            Text=validation.getTeamname(GlobalVariables.wichteam);
            String Period = String.valueOf(GlobalVariables.whichuser);
            txt.setText(" نفر "+Period+" از تیم « "+Text+" » "+"\n"+"گوشی را بر روی پیشانی خود بگذارد و حدس بزند و \n نفر دیگر از همان تیم پانتومیم اجرا کند");
        }

    }
    public static void disableTextOrpic(Activity act)
    {
        if(GlobalVariables.typeofmatch==1)
        {
            act.findViewById(R.id.textmode).setVisibility(View.GONE);
        }
        else
        {
            act.findViewById(R.id.kidsmode).setVisibility(View.GONE);
        }
    }

    public static void sendwordpage(SendWordDialoge dialoge,Activity act)
    {

        Spinner spinner = (Spinner)dialoge.findViewById(R.id.spinner);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(act, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        for(int i=0;i<15;i++)
        {
            spinnerAdapter.add(GlobalVariables.title[i]);
        }
        spinnerAdapter.notifyDataSetChanged();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View view,
                                       int arg2, long arg3) {

                ((TextView) arg0.getChildAt(0)).setTextColor(Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        Spinner spinner2 = (Spinner)dialoge.findViewById(R.id.spinner2);

        ArrayAdapter<String> spinnerAdapter2 = new ArrayAdapter<String>(act, android.R.layout.simple_spinner_item);
        spinnerAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(spinnerAdapter2);
        spinnerAdapter2.add(String.valueOf(1));
        spinnerAdapter2.add(String.valueOf(3));
        spinnerAdapter2.add(String.valueOf(6));

        spinnerAdapter2.notifyDataSetChanged();
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View view,
                                       int arg2, long arg3) {

                ((TextView) arg0.getChildAt(0)).setTextColor(Color.WHITE);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }



}
