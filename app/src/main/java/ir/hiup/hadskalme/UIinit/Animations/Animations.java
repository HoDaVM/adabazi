package ir.hiup.hadskalme.UIinit.Animations;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AlphaAnimation;

/**
 * Created by Mahdi Asiyabi on 9/23/2017.
 */

public class Animations {

    public static void playbutton(View v)
    {
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
                mAnimationSet.start();
            }
        });
        mAnimationSet.start();
    }
    public static void clickeffect(View v)
    {
        final AlphaAnimation effect = new AlphaAnimation(0.8F, 1F);
        effect.setDuration(500);
        effect.setFillAfter(true);
        v.startAnimation(effect);
    }
}
