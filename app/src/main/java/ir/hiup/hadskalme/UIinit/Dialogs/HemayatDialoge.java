package ir.hiup.hadskalme.UIinit.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;

import ir.hiup.hadskalme.BuildConfig;
import ir.hiup.hadskalme.R;
import ir.hiup.hadskalme.UIinit.Animations.Animations;
import ir.hiup.hadskalme.UIinit.GlobalVariables;
import ir.hiup.hadskalme.UIinit.Sounds.Sounds;

/**
 * Created by Hadis on 20/02/2018.
 */

public class HemayatDialoge extends Dialog implements View.OnClickListener {

    private Activity c;
    public Dialog d;

    public HemayatDialoge(Activity a) {
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
        setContentView(R.layout.hemayatdialoge);
        ImageView sure = (ImageView) findViewById(R.id.sure);
        ImageView notnow = (ImageView) findViewById(R.id.notnow);
        ImageView eixt = (ImageView) findViewById(R.id.khoroj);

        sure.setOnClickListener(this);
        notnow.setOnClickListener(this);
        eixt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Sounds.simplebuttons(c.getBaseContext());
        Animations.clickeffect(v);
        GlobalVariables.flaginapp=true;
        switch (v.getId()) {
            case R.id.sure:
                    ir.hiup.hadskalme.Shared.write("Hemayat", "1");
                    GlobalVariables.flaginapp=true;
                    Intent myintent = new Intent(Intent.ACTION_EDIT);
                    myintent.setData(Uri.parse("bazaar://details?id=" + BuildConfig.APPLICATION_ID));
                    myintent.setPackage("com.farsitel.bazaar");
                    c.startActivity(myintent);
                dismiss();
                break;
            case R.id.khoroj:
            case R.id.notnow:
                dismiss();
                break;
            default:
                break;
        }
    }
}
