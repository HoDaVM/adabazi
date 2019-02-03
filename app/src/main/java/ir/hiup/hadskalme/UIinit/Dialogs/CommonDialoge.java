package ir.hiup.hadskalme.UIinit.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import ir.hiup.hadskalme.R;
import ir.hiup.hadskalme.SplashActivity;
import ir.hiup.hadskalme.UIinit.Animations.Animations;
import ir.hiup.hadskalme.UIinit.GlobalVariables;
import ir.hiup.hadskalme.UIinit.Sounds.Sounds;
import ir.hiup.hadskalme.UIinit.UiInit;


/**
 * Created by Mahdi Asiyabi on 9/23/2017.
 */

public class CommonDialoge extends Dialog implements View.OnClickListener {
        private Activity c;
        public Dialog d;
        private String text,subject;

    public CommonDialoge(Activity a, String text,String Subject) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
            getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            this.text=text;
            this.subject=Subject;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setCancelable(true);
            setContentView(R.layout.youareofflinedialoge);
            ImageView sure = (ImageView) findViewById(R.id.sure);
            TextView texttozih = (TextView) findViewById(R.id.texttozih);
            texttozih.setText(text);
            TextView  Onvan = (TextView) findViewById(R.id.textView12);
            Onvan.setText(subject);
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
                    dismiss();
                case R.id.khoroj:
                    dismiss();
                    break;
                default:
                    break;
            }
        }
}
