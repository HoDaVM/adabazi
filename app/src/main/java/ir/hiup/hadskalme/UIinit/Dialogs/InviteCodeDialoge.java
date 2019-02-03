package ir.hiup.hadskalme.UIinit.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backtory.java.internal.BacktoryUser;

import ir.hiup.hadskalme.R;
import ir.hiup.hadskalme.UIinit.Animations.Animations;
import ir.hiup.hadskalme.UIinit.Auth;
import ir.hiup.hadskalme.UIinit.GlobalVariables;
import ir.hiup.hadskalme.UIinit.Sounds.Sounds;

/**
 * Created by Hadis on 20/02/2018.
 */

public class InviteCodeDialoge extends Dialog implements View.OnClickListener {

    private Activity c;
    public Dialog d;

    public InviteCodeDialoge(Activity a) {
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
        setContentView(R.layout.submitinvitecode);
        TextView yes = (TextView) findViewById(R.id.submit);
        TextView khoroj = (TextView) findViewById(R.id.dismiss);
        yes.setOnClickListener(this);
        khoroj.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Sounds.simplebuttons(c.getBaseContext());
        Animations.clickeffect(v);
        GlobalVariables.flaginapp=true;
        switch (v.getId()) {
            case R.id.submit:
                EditText invitecode = (EditText) findViewById(R.id.invitecode);
                Auth.CheckInviteCode(String.valueOf(invitecode.getText()),1,c,this);
                break;
            case R.id.dismiss:
                dismiss();
                break;
            default:
                break;
        }
    }
}
