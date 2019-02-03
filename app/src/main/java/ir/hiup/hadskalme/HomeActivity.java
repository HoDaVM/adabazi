package ir.hiup.hadskalme;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.backtory.java.internal.BacktoryCallBack;
import com.backtory.java.internal.BacktoryObject;
import com.backtory.java.internal.BacktoryQuery;
import com.backtory.java.internal.BacktoryResponse;
import com.backtory.java.internal.BacktoryUser;

import org.json.JSONObject;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import ir.hiup.hadskalme.Shared;
import ir.hiup.hadskalme.UIinit.Animations.Animations;
import ir.hiup.hadskalme.UIinit.Auth;
import ir.hiup.hadskalme.UIinit.Dialogs.CommonDialoge;
import ir.hiup.hadskalme.UIinit.Dialogs.InternetDialoge;
import ir.hiup.hadskalme.UIinit.Dialogs.InviteCodeDialoge;
import ir.hiup.hadskalme.UIinit.Dialogs.OfflineDialoge;
import ir.hiup.hadskalme.UIinit.Dialogs.SendWordDialoge;
import ir.hiup.hadskalme.UIinit.Dialogs.SupportDialoge;
import ir.hiup.hadskalme.UIinit.Dialogs.UpdateDialoge;
import ir.hiup.hadskalme.UIinit.GlobalVariables;
import ir.hiup.hadskalme.UIinit.Sounds.Sounds;
import ir.hiup.hadskalme.UIinit.UiInit;
import ir.hiup.hadskalme.UIinit.validation;
import ir.hiup.hadskalme.util.IabHelper;
import ir.hiup.hadskalme.util.IabResult;
import ir.hiup.hadskalme.util.Inventory;
import ir.hiup.hadskalme.util.Purchase;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class HomeActivity extends AppCompatActivity {


    static final String SKU_PREMIUM = ir.hiup.hadskalme.Shared.read("PACKAGE","FIRSTPACK");
    static final int RC_REQUEST = 1372;
    private static final String TAG = "FORPs";
    // The helper object
    IabHelper mHelper;
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener;
    IabHelper.QueryInventoryFinishedListener mGotInventoryListener;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new SweetAlertDialog(HomeActivity.this, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("میخواین از بازی خارج بشین ؟")
                    .setConfirmText("بله")
                    .setCancelText("نه ")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            GlobalVariables.flaginapp=false;
                            startActivity(intent);
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
        setContentView(R.layout.activity_home);
        UiInit.Home(HomeActivity.this);
        Auth.update(HomeActivity.this);
        Animations.playbutton(findViewById(R.id.startgame));

        if(Shared.read("InsertDavat","null").equals("1"))
        {
            InviteCodeDialoge inviteCodeDialoge = new InviteCodeDialoge(HomeActivity.this);
            inviteCodeDialoge.show();
            Auth.CopyDatabase(HomeActivity.this);
            Shared.write("InsertDavat","2");

        }
        else if(ir.hiup.hadskalme.Shared.read("FirstAds","0").equals("0"))
        {
            if(validation.isOnline(HomeActivity.this) && !Shared.read("OfflineMode","null").equals("1"))
                GetUserWaitingInvites(BacktoryUser.getCurrentUser().getUsername());
            Auth.ourAdsinit(HomeActivity.this,1);
            ir.hiup.hadskalme.Shared.write("FirstAds","1");
        }

        if(Shared.read("backgroundmusic","null").equals("okay"))
        {
            ImageView userpic = (ImageView) findViewById(R.id.music);
            userpic.setImageResource(R.drawable.music);
        }
        else
        {
            ImageView userpic = (ImageView) findViewById(R.id.music);
            userpic.setImageResource(R.drawable.activemusic);
        }


        Auth.CheckForUpdateGift(HomeActivity.this);
        String base64EncodedPublicKey = "MIHNMA0GCSqGSIb3DQEBAQUAA4G7ADCBtwKBrwDevul45fN6JxESB4P74/BGLDpvWmmcGSnAufrC8bbuI1tL3pCe2jW4+1xoEsNvGqF/rHrBghVRZDEZaI+XFBU6POsUTGKJP+V5P5zD5ncLMSwxMTGodEBvp7ZRBBAGRt7ILLo6gh4bojNYjiLinTHu2ea59qPzETRDPj36P6CZ2Poqu6F0EA1zm6WEIsNv+HXW3QbGqsHPvuMWZFsO0DtWHxxJtll+vXu2hNDSUjMCAwEAAQ==";

        mHelper = new IabHelper(this, base64EncodedPublicKey);


        mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
            public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
                Log.d(TAG, "Query inventory finished.");
                if (result.isFailure()) {
                    Log.d(TAG, "Failed to query inventory: " + result);
                    return;
                } else {
                    Log.d(TAG, "Query inventory was successful.");
                    // does the user have the premium upgrade?

                    boolean PACK1 = false;
                    boolean PACK2 = false;
                    boolean PACK3 = false;
                    boolean PACK4 = false;
                    PACK1 = inventory.hasPurchase("SPECIALPACKAGE");
                    PACK2 = inventory.hasPurchase("PACKTWO");
                    PACK3 = inventory.hasPurchase("PACKTHREE");
                    PACK4 = inventory.hasPurchase("FIRSTPACK");
                    if (PACK1 || PACK2 || PACK3 || PACK4) {
                        Auth.MasrafTicket(HomeActivity.this);
                        UiInit.gotopage(HomeActivity.this, HomeActivity.class);
                    }
                }
            }
        };


        mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
            public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
                if (result.isFailure()) {
                    Log.d(TAG, "Error purchasing: " + result);
                    return;
                }  else if (purchase.getSku().equals(SKU_PREMIUM)) {
                    Auth.MasrafTicket(HomeActivity.this);
                }
            }
        };


        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            @Override
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    Log.d(TAG, "Problem setting up In-app Billing: " + result);
                } else {
                    if(!validation.BuyedUser())
                        mHelper.queryInventoryAsync(mGotInventoryListener);
                    findViewById(R.id.offlinemode).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Animations.clickeffect(view);
                            GlobalVariables.flaginapp = true;
                            Sounds.simplebuttons(getBaseContext());
                            OfflineDialoge offlineDialoge = new OfflineDialoge(HomeActivity.this,mHelper,mPurchaseFinishedListener);
                            offlineDialoge.show();
                        }
                    });

                }
            }
        });

        findViewById(R.id.music).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Animations.clickeffect(findViewById(R.id.music));
                if(Shared.read("backgroundmusic","null").equals("okay"))
                {
                    ImageView userpic = (ImageView) findViewById(R.id.music);
                    userpic.setImageResource(R.drawable.activemusic);
                    Shared.write("backgroundmusic","null");
                    Shared.write("sfx","null");
                    GlobalVariables.audioplay = false;
                    Sounds.stopAudio(HomeActivity.this);
                    Sounds.simplebuttons(getBaseContext());
                }
                else
                {
                    ImageView userpic = (ImageView) findViewById(R.id.music);
                    userpic.setImageResource(R.drawable.music);
                    Shared.write("backgroundmusic","okay");
                    Shared.write("sfx","okay");
                    GlobalVariables.audioplay = true;
                    Sounds.playAudio(HomeActivity.this);
                    Sounds.simplebuttons(getBaseContext());
                }
            }
        });



        findViewById(R.id.support).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animations.clickeffect(view);
                GlobalVariables.flaginapp = true;
                Sounds.simplebuttons(getBaseContext());
                SupportDialoge supportDialoge = new SupportDialoge(HomeActivity.this);
                supportDialoge.show();
            }
        });

        findViewById(R.id.sendword).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animations.clickeffect(view);
                GlobalVariables.flaginapp = true;
                Sounds.simplebuttons(getBaseContext());
                if(validation.isOnline(HomeActivity.this))
                {
                    SendWordDialoge sendWordDialoge = new SendWordDialoge(HomeActivity.this);
                    sendWordDialoge.show();
                }
                else
                {
                    CommonDialoge commonDialoge = new CommonDialoge(HomeActivity.this,"برای استفاده از این امکان باید آنلاین باشید","اینترنت");
                    commonDialoge.show();
                }

            }
        });
        findViewById(R.id.startgame).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalVariables.flaginapp = true;
                Sounds.playbuttons(getBaseContext());
                if(validation.isOnline(HomeActivity.this) || validation.userCanOffline())
                    UiInit.gotopage(HomeActivity.this,GameSetting.class);
                else
                {
                    InternetDialoge internetDialoge = new InternetDialoge(HomeActivity.this);
                    internetDialoge.show();
                }

            }
        });
        findViewById(R.id.goftogo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Animations.clickeffect(view);
                GlobalVariables.flaginapp = true;
                Sounds.simplebuttons(getBaseContext());
                startActivity(intent);
                finish();
            }
        });

        UiInit.clickfunc(HomeActivity.this,VideosActivity.class,R.id.myvideos);

    }
    public void GetUserWaitingInvites(String username) {

        BacktoryQuery getCoinsAndDouble = new BacktoryQuery("players");
        getCoinsAndDouble.whereEqualTo("username", username.trim());
        getCoinsAndDouble.findInBackground(new BacktoryCallBack<List<BacktoryObject>>() {
            @Override
            public void onResponse(BacktoryResponse<List<BacktoryObject>> backtoryResponse) {
                if (backtoryResponse.isSuccessful()) {
                    List<BacktoryObject> objectThings = backtoryResponse.body();
                    for (BacktoryObject things : objectThings) {
                        int number=0;
                        try
                        {
                            number = things.getInt("invitesWaiting");
                        }
                        catch (Exception exp)
                        {
                            number=0;
                        }
                        if(number>0)
                        {
                            Auth.usercanofflineTime(3,number,HomeActivity.this,1);
                            Auth.SetnewInvites(BacktoryUser.getCurrentUser().getUsername(),0);
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);

        // Pass on the activity result to the helper for handling
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        } else {
            Log.d(TAG, "onActivityResult handled by IABUtil.");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (!GlobalVariables.flaginapp) {
            Sounds.stopAudio(HomeActivity.this);
            GlobalVariables.audioplay = false;
        } else {
            GlobalVariables.audioplay = true;
            GlobalVariables.flaginapp = false;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!GlobalVariables.flaginapp) {
            Sounds.stopAudio(HomeActivity.this);
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
            Sounds.playAudio(HomeActivity.this);
        }
    }


}
