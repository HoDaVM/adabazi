package ir.hiup.hadskalme.UIinit.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;

import ir.hiup.hadskalme.R;
import ir.hiup.hadskalme.UIinit.Animations.Animations;
import ir.hiup.hadskalme.UIinit.GlobalVariables;
import ir.hiup.hadskalme.UIinit.Sounds.Sounds;


/**
 * Created by Mahdi Asiyabi on 9/23/2017.
 */

public class ResultHelper extends Dialog implements View.OnClickListener {
        private Activity c;
        public Dialog d;

    public ResultHelper(Activity a) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
            getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setCancelable(true);
            setContentView(R.layout.resulthelper);
            ImageView sure = (ImageView) findViewById(R.id.sure);
            ImageView khoroj = (ImageView) findViewById(R.id.khoroj);
            sure.setOnClickListener(this);
            khoroj.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Sounds.simplebuttons(c.getBaseContext());
            Animations.clickeffect(v);
            switch (v.getId()) {
                case R.id.sure:
                case R.id.khoroj:
                    dismiss();
                    break;
                default:
                    break;
            }
        }
}
