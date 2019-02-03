package ir.hiup.hadskalme.UIinit.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import ir.hiup.hadskalme.R;
import ir.hiup.hadskalme.UIinit.Animations.Animations;
import ir.hiup.hadskalme.UIinit.Auth;
import ir.hiup.hadskalme.UIinit.GlobalVariables;
import ir.hiup.hadskalme.UIinit.Sounds.Sounds;
import ir.hiup.hadskalme.UIinit.UiInit;

/**
 * Created by Hadis on 20/02/2018.
 */

public class SendWordDialoge extends Dialog implements View.OnClickListener {

    private Activity c;
    public SendWordDialoge d;

    public SendWordDialoge(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        this.d=this;
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(true);
        setContentView(R.layout.sendworddialoge);
        UiInit.sendwordpage(this,c);
        ImageView registerbutton = (ImageView) findViewById(R.id.registerbutton);
        ImageView khoroj = (ImageView) findViewById(R.id.khoroj);
        registerbutton.setOnClickListener(this);
        khoroj.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        Sounds.simplebuttons(c.getBaseContext());
        Animations.clickeffect(v);
        switch (v.getId()) {
            case R.id.registerbutton:
                Animations.clickeffect(findViewById(R.id.registerbutton));
                EditText editText = (EditText) findViewById(R.id.text);
                String ed_text = editText.getText().toString().trim();

                if(ed_text.isEmpty() || ed_text.length() == 0 || ed_text.equals("") || ed_text.length() < 3)
                {
                    editText.setError("لطفا کلمه را به درستی وارد نمایید.");
                }
                else
                {
                    Spinner mySpinner=(Spinner) findViewById(R.id.spinner2);
                    String WordPoint = mySpinner.getSelectedItem().toString();
                    Spinner mySpinner2=(Spinner) findViewById(R.id.spinner);
                    String Wordid = mySpinner2.getSelectedItem().toString();
                    Auth.sendword(c,Wordid,Integer.valueOf(WordPoint),editText.getText().toString(),d);
                }
                break;
            case R.id.khoroj:
                dismiss();
                break;
            default:
                break;
        }
    }
}
