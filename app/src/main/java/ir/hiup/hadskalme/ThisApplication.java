package ir.hiup.hadskalme;

import android.app.Application;

import com.backtory.java.internal.BacktoryClient;
import com.backtory.java.internal.Config;
import com.flurry.android.FlurryAgent;
//import com.tapligh.sdk.ADView.Tapligh;

import io.appnex.android.notification.Appnex;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class ThisApplication extends Application {

    private final static String FLURRY_KEY = "R4KBMGCMVGSHZ9T3CX2V";


    @Override
    public void onCreate() {
        super.onCreate();
        Appnex.init(this);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/IRANSANS.TTF")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );


        FlurryAgent.init(this, FLURRY_KEY);

        // Initializing backtory
        BacktoryClient.Android.init(Config.newBuilder().
                // Enabling User Services
                        initAuth("59db835ee4b029cbe395df3b",
                        "59db835ee4b0416729e79305").
                        initObjectStorage("59db835ee4b029cbe395df3c").
                        initGame("59db835ee4b02f84c2ae06f9").
                        initConnectivity("59e09752e4b0a1f1572508b1").
                        build(), this);


    }


}
