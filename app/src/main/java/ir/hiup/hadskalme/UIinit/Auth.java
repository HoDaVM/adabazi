package ir.hiup.hadskalme.UIinit;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.backtory.java.HttpStatusCode;
import com.backtory.java.internal.BacktoryCallBack;
import com.backtory.java.internal.BacktoryObject;
import com.backtory.java.internal.BacktoryQuery;
import com.backtory.java.internal.BacktoryResponse;
import com.backtory.java.internal.BacktoryUser;
import com.backtory.java.internal.LoginResponse;
//import com.squareup.picasso.Picasso;
//import com.tapligh.sdk.ADView.ADUtils.ADResultListener;
//import com.tapligh.sdk.ADView.ADUtils.AdLoadListener;
//import com.tapligh.sdk.ADView.Tapligh;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import cn.pedant.SweetAlert.SweetAlertDialog;
import ir.hiup.hadskalme.HomeActivity;
import ir.hiup.hadskalme.R;
import ir.hiup.hadskalme.Shared;
import ir.hiup.hadskalme.SplashActivity;
import ir.hiup.hadskalme.UIinit.Dialogs.BannerAdsDialoge;
import ir.hiup.hadskalme.UIinit.Dialogs.FreeSubscription;
import ir.hiup.hadskalme.UIinit.Dialogs.InviteCodeDialoge;
import ir.hiup.hadskalme.UIinit.Dialogs.LoginDialog;
import ir.hiup.hadskalme.UIinit.Dialogs.OfflineDialoge;
import ir.hiup.hadskalme.UIinit.Dialogs.RegisterDialog;
import ir.hiup.hadskalme.UIinit.Dialogs.SendWordDialoge;
import ir.hiup.hadskalme.UIinit.Dialogs.TextAdsDialoge;
import ir.hiup.hadskalme.UIinit.Dialogs.UpdateDialoge;
import ir.hiup.hadskalme.UserCanOfflineCheker;
import ir.hiup.hadskalme.connection.API;
import ir.hiup.hadskalme.connection.RestAdapter;
import ir.hiup.hadskalme.connection.callbacks.CallBackAppVersion;
import ir.hiup.hadskalme.connection.callbacks.CallBackVideoAds;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Mahdi Asiyabi on 10/12/2017.
 */

public class Auth {

    private static final String ALLOWED_CHARACTERS ="0123456789qwertyuiopasdfghjklzxcvbnm";

    public static void getAppVersion()
    {
        API api = RestAdapter.createAPI();
        Call<CallBackAppVersion> callbackCall = api.GettAppVersion(1);
        callbackCall.enqueue(new Callback<CallBackAppVersion>() {
            @Override
            public void onResponse(Call<CallBackAppVersion> call, Response<CallBackAppVersion> response) {

                final CallBackAppVersion resp = response.body();
                Shared.write("versioncode",resp.appversion);
                Shared.write("force",resp.force);

            }
            @Override
            public void onFailure(Call<CallBackAppVersion> call, Throwable t) {
                //Nothing TODO
                Log.d("Problem",String.valueOf(t));

            }
        });
    }
    public static void update(Activity activity)
    {
        String versionServer = ir.hiup.hadskalme.Shared.read("versioncode","null");

        int versionName = 0;
        try {
            versionName = activity.getPackageManager()
                    .getPackageInfo(activity.getPackageName(), 0).versionCode;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        if(!versionServer.equals(String.valueOf(versionName)))
        {
            UpdateDialoge updateDialoge = new UpdateDialoge(activity);
            updateDialoge.show();
        }

    }

    public static void GetCurrentVersion(Activity activity)
    {
        int versionName = 0;
        try {
            versionName = activity.getPackageManager()
                    .getPackageInfo(activity.getPackageName(), 0).versionCode;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        ir.hiup.hadskalme.Shared.write("currentversion",String.valueOf(versionName));
    }

    public static void CheckForUpdateGift(Activity activity)
    {
        int versionName = 0;
        try {
            versionName = activity.getPackageManager()
                    .getPackageInfo(activity.getPackageName(), 0).versionCode;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if(versionName>Integer.valueOf(Shared.read("currentversion","0")) && !validation.userCanOffline())
        {
            //Toast.makeText(activity,Shared.read("currentversion","0") + " : " + String.valueOf(versionName),Toast.LENGTH_LONG).show();
            Shared.write("currentversion",String.valueOf(versionName));
            usercanofflineTime(7,1,activity,7);
        }
    }


    public static void Login(final Activity act, final LoginDialog log, final String username, String password)
    {

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(act);
        progressDoalog.setMessage("لطفا منتظر باشید...");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.setCancelable(false);
        progressDoalog.show();
        // Pass user info to login
        BacktoryUser.loginInBackground(username, password,
                new BacktoryCallBack<LoginResponse>() {

                    // Login operation done (fail or success), handling it:
                    @Override
                    public void onResponse(BacktoryResponse<LoginResponse> response) {
                        // Checking result of operation
                        if (response.isSuccessful()) {
                            // Login successfull
                            Shared.write("USERNAME",username);
                            Shared.write("GUEST","0");
                            Shared.write("InsertDavat","2");
                            log.dismiss();
                            progressDoalog.dismiss();
                            UiInit.gotopage(act,HomeActivity.class);
                            act.finish();

                        } else if (response.code() == HttpStatusCode.Unauthorized.code()) {
                            Toast.makeText(act,"نام کاربری یا رمز عبور اشتباه است",Toast.LENGTH_LONG).show();
                            progressDoalog.dismiss();
                        } else {
                            // Operation generally failed, maybe internet connection issue
                            Toast.makeText(act,"ورود با خطا مواجه شد لطفا مجدداً تلاش فرمایید",Toast.LENGTH_LONG).show();
                            progressDoalog.dismiss();
                        }
                    }

                });
    }

    public static void register(final Activity act, final RegisterDialog reg, String name, String fname, final String username, final String password, String mobile)
    {
        boolean nameV = validation.validatTexts(name, 1);
        boolean fnameV = validation.validatTexts(fname, 2);
        boolean usernameV = validation.validatTexts(username, 3);
        boolean passwordV = validation.validatePassword(password);
        boolean mobileV = validation.validationmobile(mobile);
        if (nameV && fnameV && usernameV && passwordV && mobileV) {
            final ProgressDialog progressDoalog;
            progressDoalog = new ProgressDialog(act);
            progressDoalog.setMessage("لطفا منتظر باشید...");
            progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDoalog.setCancelable(false);
            progressDoalog.show();
            // First create a user and fill his/her data
            BacktoryUser newUser = new BacktoryUser.
                    Builder().
                    setFirstName(name).
                    setLastName(fname).
                    setUsername(username.trim()).
                    setEmail(username+"@yahoo.com").
                    setPassword(password.trim()).
                    setPhoneNumber(mobile)
                    .build();

            // Registring user to backtory (in background)
            newUser.registerInBackground(new BacktoryCallBack<BacktoryUser>() {

                // Register operation done (fail or success), handling it:
                @Override
                public void onResponse(BacktoryResponse<BacktoryUser> response) {
                    // Checking result of operation
                    if (response.isSuccessful()) {
                        // Successful
                        Shared.write("USERNAME",response.body().getUsername());
                        Shared.write("GUEST","0");
                        // Pass user info to login
                        BacktoryUser.loginInBackground(username.trim(), password.trim(),
                                new BacktoryCallBack<LoginResponse>() {

                                    // Login operation done (fail or success), handling it:
                                    @Override
                                    public void onResponse(BacktoryResponse<LoginResponse> response) {
                                        // Checking result of operation
                                        if (response.isSuccessful()) {

                                            BacktoryObject inituser = new BacktoryObject("players");
                                            inituser.put("username", username.trim());
                                            inituser.put("invitesWaiting", 0);
                                            inituser.put("coins", 500);
                                            inituser.saveInBackground(new BacktoryCallBack<Void>() {
                                                @Override
                                                public void onResponse(BacktoryResponse<Void> response) {
                                                    if (response.isSuccessful()){
                                                        reg.dismiss();
                                                        progressDoalog.dismiss();
                                                        Shared.write("InsertDavat","1");
                                                        Toast.makeText(act,"ثبت نام با موفقیت انجام شد،خوش آمدید",Toast.LENGTH_LONG).show();
                                                        UiInit.gotopage(act,HomeActivity.class);
                                                        act.finish();
                                                    }
                                                }
                                            });
                                        }
                                    }

                                });
                        //
                    } else if (response.code() == HttpStatusCode.Conflict.code()) {
                        // Username is invalid
                        EditText username = (EditText) reg.findViewById(R.id.username);
                        username.setError("لطفا نام کاربری دیگری انتخاب کنید.");
                        progressDoalog.dismiss();
                    } else {
                        // General failure
                        Toast.makeText(act,"ثبت نام با خطا مواجه شد لطفا مجدداً تلاش فرمایید",Toast.LENGTH_LONG).show();
                        progressDoalog.dismiss();
                    }
                }
            });
        }
        else {
            if (!nameV) {
                EditText namer = (EditText) reg.findViewById(R.id.name);
                namer.setError("لطفا نام را به درستی وارد نمایید");
            } else if (!fnameV) {
                EditText namer = (EditText) reg.findViewById(R.id.fname);
                namer.setError("لطفا نام خانوادگی را به درستی وارد نمایید");
            } else if (!usernameV) {
                EditText namer = (EditText) reg.findViewById(R.id.username);
                namer.setError("نام کاربری باید انگلیسی و حداقل 5 کاراکتر باشد");
            } else if (!mobileV) {
                EditText namer = (EditText) reg.findViewById(R.id.mobile);
                namer.setError("موبایل وارد شده صحیح نمی باشد");
            } else if (!passwordV) {
                EditText namer = (EditText) reg.findViewById(R.id.password);
                namer.setError("رمز عبور نمی تواند خالی باشد.");
            }
        }

    }



    public static void ourAdsinit(final Activity act, int location)
    {
        API api = RestAdapter.createAPI();
        Call<CallBackVideoAds> callbackCall = api.getFullscreenADS(location);
        callbackCall.enqueue(new Callback<CallBackVideoAds>() {
            @Override
            public void onResponse(Call<CallBackVideoAds> call, Response<CallBackVideoAds> response) {

                final CallBackVideoAds resp = response.body();
                if (resp.status.equals("1"))
                {

                    if(resp.type.equals("1"))
                    {
                        TextAdsDialoge textAdsDialoge = new TextAdsDialoge(act,resp.data,resp.titletext,resp.link,"-");
                        textAdsDialoge.show();
                    }
                    else
                    {
                        BannerAdsDialoge bannerAdsDialoge= new BannerAdsDialoge(act,resp.data,resp.titletext,resp.link,resp.img);
                        bannerAdsDialoge.show();

                    }

                }
            }

            @Override
            public void onFailure(Call<CallBackVideoAds> call, Throwable t) {
                //Nothing TODO
            }
        });
    }

    public static void sendword(final Activity act, String id, Integer Point, String Word, final SendWordDialoge dialoge)
    {
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(act);
        progressDoalog.setMessage("لطفا منتظر باشید...");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.setCancelable(false);
        progressDoalog.show();
        BacktoryObject noteeee = new BacktoryObject("words");
        noteeee.put("wordid",id);
        noteeee.put("username", BacktoryUser.getCurrentUser().getUsername());
        noteeee.put("word", Word);
        noteeee.put("wordpoint",Point);

        noteeee.saveInBackground(new BacktoryCallBack<Void>() {
            @Override
            public void onResponse(BacktoryResponse<Void> response) {
                if (response.isSuccessful()){
                    // successful save. good place to show a toast
                    BacktoryQuery getCoinsAndDouble = new BacktoryQuery("players");
                    getCoinsAndDouble.whereEqualTo("username", BacktoryUser.getCurrentUser().getUsername());
                    getCoinsAndDouble.findInBackground(new BacktoryCallBack<List<BacktoryObject>>() {
                       @Override
                       public void onResponse(BacktoryResponse<List<BacktoryObject>> backtoryResponse) {
                           if (backtoryResponse.isSuccessful()) {
                               List<BacktoryObject> objectThings = backtoryResponse.body();
                               for (BacktoryObject things : objectThings) {
                                   progressDoalog.dismiss();
                                   Toast.makeText(act, "ممنون ، کلمه ارسال شد.", Toast.LENGTH_LONG).show();
                                   dialoge.dismiss();
                               }
                           }
                       }
                   });
                    progressDoalog.dismiss();
                }
                else {
                    // see response.message() to know the cause of error
                    progressDoalog.dismiss();
                    Toast.makeText(act, "خطایی رخ داده است ، لطفا مجددا تلاش کنید.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public static void usercanofflineTime(long days,long count,Activity activity,int fromwhere)
    {
            final NumberFormat nf= NumberFormat.getInstance(new Locale("fa","IR"));
            Shared.write("usercanofflinebytime","1");
            String text="";
            if(Long.valueOf(ir.hiup.hadskalme.Shared.read("delay","0"))!=0L)
            {
                Long delay = Long.valueOf(Shared.read("delay","0"));
                delay = delay + (days*count*86400000);
                Shared.write("delay",String.valueOf(delay));
                if(fromwhere==1)
                    text= String.valueOf(nf.format(days*count)) + " روز  به مجموع زمان اشتراک های رایگان شما بابت کسانی که به بازی دعوت کرده اید ، اضافه شد ";
                if(fromwhere==2)
                    text= String.valueOf(nf.format(days)) + " روز اشتراک رایگان بابت عضویت در کانال تلگرام بهتون اضافه شد. ";
                if(fromwhere==3)
                    text= String.valueOf(nf.format(days)) + " روز اشتراک رایگان بابت عضویت در صفحه اینستاگرام بهتون اضافه شد. ";
                if(fromwhere==4)
                    text= String.valueOf(nf.format(days)) + " روز اشتراک رایگان بابت دانلود اپلیکیشن بهتون اضافه شد. ";
                if(fromwhere==5)
                    text= String.valueOf(nf.format(days)) + " روز اشتراک رایگان بابت مشاهده تبلیغ بهتون اضافه شد. ";
                if(fromwhere==6)
                    text= String.valueOf(nf.format(days)) + " روز اشتراک رایگان بابت ثبت کد دعوت دریافت کردید. ";
                if(fromwhere==7)
                    text= String.valueOf(nf.format(days)) + " روز اشتراک رایگان بابت بروزرسانی بازی دریافت کردید. ";
            }
            else
            {
                recordRunTime(activity);
                Shared.write("time","1");
                Shared.write("delay",String.valueOf(days*count*86400000));
                if(fromwhere==1)
                    text= String.valueOf(nf.format(days*count)) + " روز  اشتراک رایگان  بابت کسانی که به بازی دعوت کرده اید ، دریافت کردید ";
                if(fromwhere==2)
                    text= String.valueOf(nf.format(days)) + " روز اشتراک رایگان بابت عضویت در کانال تلگرام دریافت کردید. ";
                if(fromwhere==3)
                    text= String.valueOf(nf.format(days)) + " روز اشتراک رایگان بابت عضویت در صفحه اینستاگرام دریافت کردید. ";
                if(fromwhere==4)
                    text= String.valueOf(nf.format(days)) + " روز اشتراک رایگان بابت دانلود اپلیکیشن دریافت کردید. ";
                if(fromwhere==5)
                    text= String.valueOf(nf.format(days)) + " روز اشتراک رایگان بابت مشاهده تبلیغ دریافت کردید. ";
                if(fromwhere==6)
                    text= String.valueOf(nf.format(days)) + " روز اشتراک رایگان بابت ثبت کد دعوت دریافت کردید. ";
                if(fromwhere==7)
                    text= String.valueOf(nf.format(days)) + " روز اشتراک رایگان بابت بروزرسانی بازی دریافت کردید. ";
            }
            FreeSubscription freeSubscription = new FreeSubscription(activity,text);
            freeSubscription.show();
            activity.startService(new Intent(activity, UserCanOfflineCheker.class));

    }


    static void recordRunTime(Activity activity) {
        ir.hiup.hadskalme.Shared.init(activity);
        ir.hiup.hadskalme.Shared.write("m3", String.valueOf(System.currentTimeMillis()));
    }

    public static void SetnewInvites(final String username,final int count) {
        BacktoryQuery getCoinsAndDouble = new BacktoryQuery("players");
        getCoinsAndDouble.whereEqualTo("username", username.trim());
        getCoinsAndDouble.findInBackground(new BacktoryCallBack<List<BacktoryObject>>() {
            @Override
            public void onResponse(BacktoryResponse<List<BacktoryObject>> backtoryResponse) {
                if (backtoryResponse.isSuccessful()) {
                    List<BacktoryObject> objectThings = backtoryResponse.body();
                    for (BacktoryObject things : objectThings) {
                        String dd = things.getObjectId();

                        BacktoryQuery noteQuery = new BacktoryQuery("players");
                            noteQuery.getInBackground(dd,
                                    new BacktoryCallBack<BacktoryObject>() {
                                        @Override
                                        public void onResponse(BacktoryResponse<BacktoryObject> response) {
                                            if (response.isSuccessful()) {
                                                BacktoryObject note = response.body();
                                                    note.put("invitesWaiting",count);
                                                note.saveInBackground(new BacktoryCallBack<Void>() {
                                                    @Override
                                                    public void onResponse(BacktoryResponse<Void> backtoryResponse) {

                                                    }
                                                });
                                            }
                                            else
                                            {

                                            }
                                        }
                                    });
                    }
                }
                else
                {
                    Log.v("Username","notFound");
                }
            }
        });

    }

    public static void CheckInviteCode(final String username, final int count, final Activity activity, final InviteCodeDialoge inviteCodeDialoge) {
        BacktoryQuery getCoinsAndDouble = new BacktoryQuery("players");
        getCoinsAndDouble.whereEqualTo("invitecode", username.trim());
        getCoinsAndDouble.findInBackground(new BacktoryCallBack<List<BacktoryObject>>() {
            @Override
            public void onResponse(BacktoryResponse<List<BacktoryObject>> backtoryResponse) {
                if (backtoryResponse.isSuccessful()) {
                    List<BacktoryObject> objectThings = backtoryResponse.body();
                    if(objectThings.size()>0)
                    {
                        for (BacktoryObject things : objectThings) {
                            String dd = things.getObjectId();
                            int number=0;
                            try
                            {
                                number = things.getInt("invitesWaiting");
                            }
                            catch (Exception exp)
                            {
                                number=0;
                            }
                            inviteCodeDialoge.dismiss();
                            usercanofflineTime(3,1,activity,6);
                            if((backtoryResponse.body().size() !=0))
                            {
                                BacktoryQuery noteQuery = new BacktoryQuery("players");
                                final int finalNumber = number;
                                noteQuery.getInBackground(dd,
                                        new BacktoryCallBack<BacktoryObject>() {
                                            @Override
                                            public void onResponse(BacktoryResponse<BacktoryObject> response) {
                                                if (response.isSuccessful()) {
                                                    BacktoryObject note = response.body();
                                                    note.put("invitesWaiting", finalNumber +count);
                                                    note.saveInBackground(new BacktoryCallBack<Void>() {
                                                        @Override
                                                        public void onResponse(BacktoryResponse<Void> backtoryResponse) {

                                                        }
                                                    });
                                                }

                                            }
                                        });
                            }

                        }
                    }
                    else
                    {
                        Toast.makeText(activity,"کد وارد شده صحیح نمی باشد",Toast.LENGTH_LONG).show();
                    }

                }
            }
        });

    }
    public static void MasrafTicket(final Activity act) {
        Shared.write("OfflineMode","1");
        Toast.makeText(act,"نسخه آفلاین با موفقیت فعال گردید.",Toast.LENGTH_LONG).show();
        UiInit.gotopage(act,HomeActivity.class);
    }
    public static void CopyDatabase(final Activity activity)
    {
        DataBaseHelper dbhelper = null;
        try {
            dbhelper = new DataBaseHelper(activity);
            dbhelper.opendatabase();
            dbhelper.createdatabase();
        } catch (IOException e) {
            Log.d("FILE",String.valueOf(e));
        }
    }
    public static void loadAd(final String zoneId, final Activity activity) {

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(activity);
        progressDoalog.setMessage("لطفا منتظر باشید...");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDoalog.setCancelable(false);
        progressDoalog.show();
//        final Tapligh tapligh = Tapligh.newInstance(activity);
//        tapligh.setTestEnable(false);
//        tapligh.loadAd(zoneId , new AdLoadListener() {
//            @Override
//            public void onAdReady(String unitCode, String token) {
//                showad(tapligh,"3ED0570C66EBFE1DEF1A2C05E316FE",activity);
//                progressDoalog.dismiss();
//            }
//            @Override
//            public void onLoadError(String unitCode, LoadErrorStatus
//                    loadErrorStatus) {
//                progressDoalog.dismiss();
//            }
//        });
    }



//    public static void showad(final Tapligh tapligh, final String token, final Activity activity)
//    {

//        tapligh.showAd(token , new ADResultListener () {
//            @Override
//            public void onAdResult(ADResult adResult, String token) {
//                int CompletedAds = Shared.read("CompletedAds",0);
//                Log.i("Tapligh" , "token->" + token);
//                switch (adResult){
//                    case adVideoClosedOnView:
//                        Toast.makeText(activity,"برای دریافت جایزه ویدئو را باید کامل ببینی.",Toast.LENGTH_LONG).show();
//                        Log.i("Tapligh", "adVideoClosedOnView");
//                        break;
//                    case adClicked:
//                    case adVideoClosedAfterFulView:
//                        if(CompletedAds==9)
//                        {
//                            Shared.write("CompletedAds",0);
//                            Auth.usercanofflineTime(7,0,activity,5);
//                        }
//                        else
//                        {
//                            Shared.write("CompletedAds",CompletedAds+1);
//                            Toast.makeText(activity,String.valueOf((9-CompletedAds))+ "  تبلیغ دیگه موند برای 7 روز اشتراک رایگان ",Toast.LENGTH_LONG).show();
//                        }
//                        Log.i("Tapligh", "adViewCompletely");
//                        break;
//                }
//            }
//            @Override
//            public void onRewardReady(String reward) {
//                Log.i("Tapligh" , "reward->" + reward);
//            }
//        });
//    }


    public static void GuestLogin(final Activity act) {

            final ProgressDialog progressDoalog;
            progressDoalog = new ProgressDialog(act);
            progressDoalog.setMessage("لطفا منتظر باشید...");
            progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDoalog.setCancelable(false);
            progressDoalog.show();
            final BacktoryUser currentU = BacktoryUser.getCurrentUser();
            if (currentU == null) {
                // Request a guest user from backtory
                BacktoryUser.loginAsGuestInBackground(new BacktoryCallBack<LoginResponse>() {
                    // Login done (fail or success), checking for result
                    @Override
                    public void onResponse(BacktoryResponse<LoginResponse> response) {
                        // Checking if operation was successful
                        if (response.isSuccessful()) {
                            Shared.write("InsertDavat","1");
                            // Getting new username and password from CURRENT user
                            String newUsername = BacktoryUser.getCurrentUser().getUsername();
                            String newPassword = BacktoryUser.getCurrentUser().getGuestPassword();

                            BacktoryObject inituser = new BacktoryObject("players");
                            inituser.put("username", newUsername.trim());
                            inituser.put("invitesWaiting", 0);
                            inituser.put("invitecode", newUsername.trim());
                            inituser.saveInBackground(new BacktoryCallBack<Void>() {
                                @Override
                                public void onResponse(BacktoryResponse<Void> response) {
                                    if (response.isSuccessful()) {
                                        progressDoalog.dismiss();
                                        UiInit.gotopage(act, HomeActivity.class);
                                    }
                                    else
                                        progressDoalog.dismiss();
                                }
                            });
                        }
                        else
                            progressDoalog.dismiss();
                    }
                });
            }


    }
}
