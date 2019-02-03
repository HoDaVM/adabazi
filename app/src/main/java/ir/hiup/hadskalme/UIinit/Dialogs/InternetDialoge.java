package ir.hiup.hadskalme.UIinit.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import ir.hiup.hadskalme.R;
import ir.hiup.hadskalme.SplashActivity;
import ir.hiup.hadskalme.UIinit.Animations.Animations;
import ir.hiup.hadskalme.UIinit.GlobalVariables;
import ir.hiup.hadskalme.UIinit.Sounds.Sounds;
import ir.hiup.hadskalme.UIinit.UiInit;


/**
 * Created by Mahdi Asiyabi on 9/23/2017.
 */

public class InternetDialoge extends Dialog implements View.OnClickListener {
        private Activity c;
        public Dialog d;

    public InternetDialoge(Activity a) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
            getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setCancelable(false);
            setContentView(R.layout.youareofflinedialoge);
            ImageView sure = (ImageView) findViewById(R.id.sure);
            ImageView khoroj = (ImageView) findViewById(R.id.khoroj);
            sure.setOnClickListener(this);
            khoroj.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Sounds.simplebuttons(c.getBaseContext());
            Animations.clickeffect(v);
            GlobalVariables.flaginapp=true;
            switch (v.getId()) {
                case R.id.sure:
                case R.id.khoroj:
                    UiInit.gotopage(c,SplashActivity.class);
                    c.finish();
                    dismiss();
                    break;
                default:
                    break;
            }
        }
}
