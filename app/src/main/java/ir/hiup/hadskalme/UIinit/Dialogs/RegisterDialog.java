package ir.hiup.hadskalme.UIinit.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;

import com.backtory.java.internal.BacktoryUser;

import ir.hiup.hadskalme.R;
import ir.hiup.hadskalme.UIinit.Animations.Animations;
import ir.hiup.hadskalme.UIinit.Auth;
import ir.hiup.hadskalme.UIinit.GlobalVariables;
import ir.hiup.hadskalme.UIinit.Sounds.Sounds;


/**
 * Created by Mahdi Asiyabi on 9/23/2017.
 */

public class RegisterDialog extends Dialog implements View.OnClickListener {
        private Activity c;
        public Dialog d;

    public RegisterDialog(Activity a) {
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
            setContentView(R.layout.registerdialog);
            ImageView yes = (ImageView) findViewById(R.id.registerbutton);
            ImageView khoroj = (ImageView) findViewById(R.id.khoroj);
            yes.setOnClickListener(this);
            khoroj.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Sounds.simplebuttons(c.getBaseContext());
            Animations.clickeffect(v);
            GlobalVariables.flaginapp=true;
            switch (v.getId()) {
                case R.id.registerbutton:
                    EditText name = (EditText) findViewById(R.id.name);
                    EditText fname = (EditText) findViewById(R.id.fname);
                    EditText username = (EditText) findViewById(R.id.username);
                    EditText password = (EditText) findViewById(R.id.password);
                    EditText mobile = (EditText) findViewById(R.id.mobile);
                    Auth.register(c,this,name.getText().toString(),fname.getText().toString(),username.getText().toString().trim(),password.getText().toString().trim(),mobile.getText().toString());
                    break;
                case R.id.khoroj:
                    dismiss();
                    break;
                default:
                    break;
            }
        }
}
