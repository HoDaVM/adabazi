package ir.hiup.hadskalme.UIinit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Camera;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.widget.EditText;

import java.util.Date;
import java.util.Locale;

import ir.hiup.hadskalme.R;
import ir.hiup.hadskalme.Shared;
import ir.hiup.hadskalme.SubjectActivity;

/**
 * Created by Mahdi Asiyabi on 9/29/2017.
 */

public class validation {

    public static boolean isOnline(Activity act) {
        ConnectivityManager cm = (ConnectivityManager) act.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }
    public static boolean userCanOffline()
    {
        String premium = Shared.read("OfflineMode","0");
        String premiumTimer = Shared.read("usercanofflinebytime","0");
        if(premium.equals("1") || premiumTimer.equals("1") )
            return true;
        return false;
    }
    public static boolean BuyedUser()
    {
        String premium = Shared.read("OfflineMode","0");
        if(premium.equals("1"))
            return true;
        return false;
    }
    public static void Localize() {
        String languageToLoad = "en"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
    }
    public static boolean validationmobile(String Mobile) {
        return Mobile.matches( "^(09)((0[1-3])|(3[0-9])|(1[0-9])|(9[0])|(2[0-2]))(\\d{7})$" );
    }

    public static boolean validatePassword(String Password) {

        if (Password == null || Password.length()<2) {
            return false;
        }
        return true;
    }


    public static boolean validatTexts(String Text, int method) {
        if (method == 1) {
            return Text != null && !(Text.length() < 2 && Text.length() < 20);
        } else if (method == 2) {
            return Text != null && !(Text.length() < 4 && Text.length() < 50);
        } else {
            return Text != null && !(Text.length() < 4 && Text.length() < 20);
        }
    }
    public static void validateinputteam(int numbers, Activity act)
    {
        EditText firstTeam = (EditText)act.findViewById(R.id.textfirst);
        EditText secondTeam = (EditText)act.findViewById(R.id.textsecond);
        EditText thirdTeam = (EditText)act.findViewById(R.id.textthird);
        EditText fourthTeam = (EditText)act.findViewById(R.id.textfour);

        if(numbers==2)
        {
            if( firstTeam.getText().toString().length() == 0 )
                firstTeam.setError( "وارد نمودن نام تیم اول ضروری می باشد" );
            else if( secondTeam.getText().toString().length() == 0 )
                secondTeam.setError( "وارد نمودن نام تیم دوم ضروری می باشد" );
            else
            {
                GlobalVariables.teamone= firstTeam.getText().toString();
                GlobalVariables.teamtwo= secondTeam.getText().toString();
                GlobalVariables.twotype=true;
                GlobalVariables.totalcounter=GlobalVariables.numberofTeams*GlobalVariables.numberofusers*GlobalVariables.dor;
                UiInit.gotopage(act,SubjectActivity.class);
            }
        }
        else if(numbers==3)
        {
            if( firstTeam.getText().toString().length() == 0 )
                firstTeam.setError( "وارد نمودن نام تیم اول ضروری می باشد" );
            else if( secondTeam.getText().toString().length() == 0 )
                secondTeam.setError( "وارد نمودن نام تیم دوم ضروری می باشد" );
            else if( thirdTeam.getText().toString().length() == 0 )
                thirdTeam.setError( "وارد نمودن نام تیم سوم ضروری می باشد" );
            else
            {
                GlobalVariables.teamone= firstTeam.getText().toString();
                GlobalVariables.teamtwo= secondTeam.getText().toString();
                GlobalVariables.teamthree= thirdTeam.getText().toString();
                GlobalVariables.totalcounter=GlobalVariables.numberofTeams*GlobalVariables.numberofusers*GlobalVariables.dor;
                GlobalVariables.threetype=true;
                UiInit.gotopage(act,SubjectActivity.class);

            }
        }
        else if(numbers==4)
        {
            if( firstTeam.getText().toString().length() == 0 )
                firstTeam.setError( "وارد نمودن نام تیم اول ضروری می باشد" );
            else if( secondTeam.getText().toString().length() == 0 )
                secondTeam.setError( "وارد نمودن نام تیم دوم ضروری می باشد" );
            else if( thirdTeam.getText().toString().length() == 0 )
                thirdTeam.setError( "وارد نمودن نام تیم سوم ضروری می باشد" );
            else if( fourthTeam.getText().toString().length() == 0 )
                fourthTeam.setError( "وارد نمودن نام تیم چهارم ضروری می باشد" );
            else
            {
                GlobalVariables.teamone= firstTeam.getText().toString();
                GlobalVariables.teamtwo= secondTeam.getText().toString();
                GlobalVariables.teamthree= thirdTeam.getText().toString();
                GlobalVariables.teamfour= fourthTeam.getText().toString();
                GlobalVariables.totalcounter=GlobalVariables.numberofTeams*GlobalVariables.numberofusers*GlobalVariables.dor;
                GlobalVariables.fourtype=true;
                UiInit.gotopage(act,SubjectActivity.class);

            }
        }

    }

    public static String getTeamname(int i)
    {
        switch (i) {
            case 1:
                return GlobalVariables.teamone;
            case 2:
                return GlobalVariables.teamtwo;
            case 3:
                return GlobalVariables.teamthree;
            case 4:
                return GlobalVariables.teamfour;
            default:
                break;
        }
        return null;
    }
    public static int openFrontalCamera() {
        if(GlobalVariables.videoRecord==1)
        {
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            for (int i = 0; i < Camera.getNumberOfCameras(); i++) {
                Camera.getCameraInfo(i, cameraInfo);
                if (Camera.CameraInfo.CAMERA_FACING_FRONT == cameraInfo.facing) {
                    return i;
                }
            }
            return -2;
        }
        else
        {
            return -2;
        }
    }
    public static void saveOndb(String path, String word,Activity act) {
        SQLiteDatabase db;
        String mydate = (String) android.text.format.DateFormat.format("yyyy-MM-dd HH:mm", new Date());
        db = act.openOrCreateDatabase("HadsKalme", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS videos (word VARCHAR,path TEXT,regdate VARCHAR);");
        db.execSQL("INSERT INTO videos VALUES('" + word + "','" + path +
                "','" + mydate + "');");
    }
    public static  void shareVideo(String path,Activity act) {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        path = "file://" + path;
        Uri videoUri = Uri.parse(path);
        sharingIntent.setType("video/mp4");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, videoUri);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, "ویدوی پانتومیم من در بازی حدس کلمه \n دانلود : https://cafebazaar.ir/app/ir.hiup.hadskalme");
        sharingIntent.putExtra(Intent.EXTRA_TITLE, "ویدوی پانتومیم من در بازی حدس کلمه \n دانلود : https://cafebazaar.ir/app/ir.hiup.hadskalme");
        act.startActivity(Intent.createChooser(sharingIntent, "اشتراک گذاری ویدئو با "));
        GlobalVariables.flaginapp = false;
    }


}
