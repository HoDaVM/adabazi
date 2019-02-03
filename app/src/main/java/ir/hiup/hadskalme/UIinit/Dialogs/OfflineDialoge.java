package ir.hiup.hadskalme.UIinit.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.backtory.java.internal.BacktoryUser;
import com.squareup.picasso.Picasso;

import ir.hiup.hadskalme.HomeActivity;
import ir.hiup.hadskalme.R;
import ir.hiup.hadskalme.Shared;
import ir.hiup.hadskalme.UIinit.Animations.Animations;
import ir.hiup.hadskalme.UIinit.Auth;
import ir.hiup.hadskalme.UIinit.GlobalVariables;
import ir.hiup.hadskalme.UIinit.Sounds.Sounds;
import ir.hiup.hadskalme.connection.API;
import ir.hiup.hadskalme.connection.RestAdapter;
import ir.hiup.hadskalme.connection.callbacks.CallBackVideoAds;
import ir.hiup.hadskalme.util.IabHelper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Mahdi Asiyabi on 9/23/2017.
 */

public class OfflineDialoge extends Dialog implements View.OnClickListener {
        private Activity c;
        public Dialog d;
        static final String SKU_PREMIUM = ir.hiup.hadskalme.Shared.read("PACKAGE","FIRSTPACK");
        static final int RC_REQUEST = 1372;
//        private IabHelper iabHelper;
//        private IabHelper.OnIabPurchaseFinishedListener onIabPurchaseFinishedListener;

    public OfflineDialoge(Activity a, IabHelper iabHelper/*, IabHelper.OnIabPurchaseFinishedListener onIabPurchaseFinishedListener*/) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
//            this.iabHelper=iabHelper;
//            this.onIabPurchaseFinishedListener = onIabPurchaseFinishedListener;
            getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setCancelable(true);
            setContentView(R.layout.offlinemodedialoge);
            final ImageView gamepic = (ImageView) findViewById(R.id.gamepic);
            final TextView gamename = (TextView) findViewById(R.id.gamename);
            final ImageView downloadgame = (ImageView) findViewById(R.id.downloadgame);

            ColorMatrix matrix = new ColorMatrix();
            matrix.setSaturation(0);
            final ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);

            gamename.setVisibility(View.GONE);
            gamepic.setVisibility(View.GONE);
            downloadgame.setVisibility(View.GONE);

            API api = RestAdapter.createAPI();
            Call<CallBackVideoAds> callbackCall = api.getFullscreenADS(5);
            callbackCall.enqueue(new Callback<CallBackVideoAds>() {
                @Override
                public void onResponse(Call<CallBackVideoAds> call, Response<CallBackVideoAds> response) {

                    final CallBackVideoAds resp = response.body();
                    if (resp.status.equals("1"))
                    {
                        gamename.setText(resp.titletext);
                        Picasso.with(c).load(resp.img).into(gamepic);
                        if(resp.confirmbutton.equals(Shared.read("game","null")))
                        {
                            downloadgame.setColorFilter(filter);
                        }
                        else
                        {
                            Shared.write("game",resp.confirmbutton);
                            downloadgame.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    String url = resp.link;
                                    Intent i = new Intent(Intent.ACTION_VIEW);
                                    i.setData(Uri.parse(url));
                                    c.startActivity(i);
                                    dismiss();
                                    Auth.usercanofflineTime(7,1,c,4);
                                    Sounds.simplebuttons(c.getBaseContext());
                                    Animations.clickeffect(view);
                                    GlobalVariables.flaginapp=false;
                                }
                            });
                        }
                        gamename.setVisibility(View.VISIBLE);
                        gamepic.setVisibility(View.VISIBLE);
                        downloadgame.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<CallBackVideoAds> call, Throwable t) {
                    //Nothing TODO
                }
            });
            ImageView buybutton = (ImageView) findViewById(R.id.buybutton);
            ImageView ourtelegram = (ImageView) findViewById(R.id.ourtelegram);
            ImageView ourinstagram = (ImageView) findViewById(R.id.ourinstagram);
            ImageView seeads = (ImageView) findViewById(R.id.seeads);
            TextView adstext = (TextView) findViewById(R.id.adstext);
            ImageView khoroj = (ImageView) findViewById(R.id.khoroj);


            if(Shared.read("PACKAGE","FIRSTPACK").equals("PACKTWO"))
                buybutton.setBackgroundResource(R.drawable.kamtakhfif);
            else if(Shared.read("PACKAGE","FIRSTPACK").equals("PACKTHREE"))
                buybutton.setBackgroundResource(R.drawable.moretakhfif);
            else
                buybutton.setBackgroundResource(R.drawable.buyoffline);

            final EditText invitetextt = (EditText) findViewById(R.id.invitetextt);

            adstext.setText(String.valueOf(Shared.read("CompletedAds",0)+ "/10"));

            invitetextt.setText(BacktoryUser.getCurrentUser().getUsername());

            invitetextt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ClipboardManager cm = (ClipboardManager)c.getSystemService(c.CLIPBOARD_SERVICE);
                    cm.setText(invitetextt.getText());
                    Toast.makeText(c, "کد دعوت کپی شد", Toast.LENGTH_SHORT).show();
                }
            });

            khoroj.setOnClickListener(this);

            seeads.setOnClickListener(this);

            if(!Shared.read("telegramPassed","null").equals("1"))
                ourtelegram.setOnClickListener(this);
            else
                ourtelegram.setColorFilter(filter);

            if(!Shared.read("instagramPassed","null").equals("1"))
                ourinstagram.setOnClickListener(this);
            else
                ourinstagram.setColorFilter(filter);

            if(!Shared.read("OfflineMode","null").equals("1"))
                buybutton.setOnClickListener(this);
            else
                buybutton.setColorFilter(filter);

        }

        @Override
        public void onClick(View v) {
            Sounds.simplebuttons(c.getBaseContext());
            Animations.clickeffect(v);
            switch (v.getId()) {
                case R.id.buybutton:
//                    if (iabHelper != null) iabHelper.flagEndAsync();
//                    if (iabHelper != null) {
//                        iabHelper.launchPurchaseFlow(c, SKU_PREMIUM, RC_REQUEST,
//                                onIabPurchaseFinishedListener);
//                    }
                    break;
                case R.id.ourtelegram:
                    Animations.clickeffect(findViewById(R.id.buybutton));
                    Intent telegram = new Intent(Intent.ACTION_VIEW , Uri.parse("https://telegram.me/hiupco"));
                    c.startActivity(telegram);
                    Shared.write("telegramPassed","1");
                    dismiss();
                    Auth.usercanofflineTime(7,1,c,2);
                    GlobalVariables.flaginapp=false;
                    break;

                case R.id.ourinstagram:
                    Uri uri = Uri.parse("http://instagram.com/_u/hiupco");
                    Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
                    likeIng.setPackage("com.instagram.android");
                    try {
                        c.startActivity(likeIng);
                    } catch (ActivityNotFoundException e) {
                        c.startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse("http://instagram.com/hiupco")));
                    }
                    Shared.write("instagramPassed","1");
                    dismiss();
                    Auth.usercanofflineTime(7,1,c,3);
                    GlobalVariables.flaginapp=false;
                    break;
                case R.id.seeads:
                    GlobalVariables.flaginapp=false;
                    Auth.loadAd("3ED0570C66EBFE1DEF1A2C05E316FE",c);
                    break;
                case R.id.khoroj:
                    dismiss();
                    break;
                default:
                    break;
            }
        }
}
