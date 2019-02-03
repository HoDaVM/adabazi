package ir.hiup.hadskalme;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.backtory.java.internal.BacktoryUser;
//import com.tapligh.sdk.ADView.Tapligh;

import cn.pedant.SweetAlert.SweetAlertDialog;
import ir.hiup.hadskalme.UIinit.Auth;
import ir.hiup.hadskalme.UIinit.Dialogs.InternetDialoge;
import ir.hiup.hadskalme.UIinit.Sounds.Sounds;
import ir.hiup.hadskalme.UIinit.UiInit;
import ir.hiup.hadskalme.UIinit.validation;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SplashActivity extends AppCompatActivity {

    CountDownTimer countDownTimer;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        UiInit.Splash(SplashActivity.this);
        recordRunTime();

        if (validation.isOnline(SplashActivity.this))
            Auth.getAppVersion();

//        final Tapligh tapligh = Tapligh.newInstance(SplashActivity.this);
//        tapligh.setToken("XANPN2QFAO2RHSXFMSGHKVNBHGAYOH");

        if (validation.isOnline(SplashActivity.this) || (validation.userCanOffline())) {
            ir.hiup.hadskalme.Shared.write("FirstAds", "0");
            ir.hiup.hadskalme.Shared.write("SecondAds", "0");
            ir.hiup.hadskalme.Shared.write("ThirdAds", "0");
            ir.hiup.hadskalme.Shared.write("FourthAds", "0");
            ir.hiup.hadskalme.Shared.write("FifthAds", "0");
            startService(new Intent(this, CheckRecentRun.class));
            final int[] i = {0, 0};
            countDownTimer = new CountDownTimer(3 * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                    if (i[0] == 0) {
                        setAlphaAnimation(findViewById(R.id.splashlogo));
                        i[0] = 1;
                    }
                    if (millisUntilFinished > 2000 && i[1] == 0) {
                        Sounds.playsplash(getBaseContext());
                        i[1] = 1;
                    }
                }

                @Override
                public void onFinish() {
                    final BacktoryUser currentU = BacktoryUser.getCurrentUser();
                    if (currentU == null)
                        UiInit.gotopage(SplashActivity.this, RegisterActivity.class);
                    else
                        UiInit.gotopage(SplashActivity.this, HomeActivity.class);
                    finish();
                }
            };
            countDownTimer.start();
        } else {
            InternetDialoge internetDialoge = new InternetDialoge(SplashActivity.this);
            internetDialoge.show();
        }
    }

    public static void setAlphaAnimation(View v) {
        ObjectAnimator fadeOut = ObjectAnimator.ofFloat(v, "alpha", 1f, .4f);
        fadeOut.setDuration(1500);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(v, "alpha", .4f, 1f);
        fadeIn.setDuration(1500);

        final AnimatorSet mAnimationSet = new AnimatorSet();

        mAnimationSet.play(fadeIn).after(fadeOut);

        mAnimationSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }
        });
        mAnimationSet.start();
    }

    public void recordRunTime() {
        ir.hiup.hadskalme.Shared.init(getBaseContext());
        ir.hiup.hadskalme.Shared.write("m2", String.valueOf(System.currentTimeMillis()));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
