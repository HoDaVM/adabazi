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
import ir.hiup.hadskalme.Shared;
import ir.hiup.hadskalme.UIinit.Animations.Animations;
import ir.hiup.hadskalme.UIinit.GlobalVariables;
import ir.hiup.hadskalme.UIinit.Sounds.Sounds;

/**
 * Created by Hadis on 20/02/2018.
 */

public class UpdateDialoge extends Dialog implements View.OnClickListener {

    private Activity c;
    public Dialog d;

    public UpdateDialoge(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.c = a;
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.updatedialoge);
        ImageView sure = (ImageView) findViewById(R.id.sure);
        ImageView notnow = (ImageView) findViewById(R.id.notnow);
        ImageView eixt = (ImageView) findViewById(R.id.khoroj);

        sure.setOnClickListener(this);
        notnow.setOnClickListener(this);
        eixt.setOnClickListener(this);

            if(Shared.read("force","null").equals("1"))
            {
                setCancelable(false);
                notnow.setVisibility(View.GONE);
                eixt.setVisibility(View.GONE);
            }
            else
            {
                notnow.setVisibility(View.VISIBLE);
                eixt.setVisibility(View.VISIBLE);
                setCancelable(true);
            }
    }

    @Override
    public void onClick(View v) {
        Sounds.simplebuttons(c.getBaseContext());
        Animations.clickeffect(v);
        switch (v.getId()) {
            case R.id.sure:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("bazaar://details?id=" + BuildConfig.APPLICATION_ID));
                intent.setPackage("com.farsitel.bazaar");
                c.startActivity(intent);
                break;
            case R.id.notnow:
            case R.id.khoroj:
                dismiss();
                break;
            default:
                break;
        }
    }
}
