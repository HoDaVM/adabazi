package ir.hiup.hadskalme.UIinit.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import ir.hiup.hadskalme.R;
import ir.hiup.hadskalme.SubjectActivity;
import ir.hiup.hadskalme.TeamsActivity;
import ir.hiup.hadskalme.UIinit.Animations.Animations;
import ir.hiup.hadskalme.UIinit.GlobalVariables;
import ir.hiup.hadskalme.UIinit.Sounds.Sounds;
import ir.hiup.hadskalme.UIinit.UiInit;


/**
 * Created by Mahdi Asiyabi on 9/23/2017.
 */

public class VideoRecorderDialoge extends Dialog implements View.OnClickListener {
        private Activity c;
        private static final int MULTIPLE_PERMISSION_REQUEST = 43;

    public VideoRecorderDialoge(Activity a) {
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
            setContentView(R.layout.videorecorder);
            ImageView sure = (ImageView) findViewById(R.id.sure);
            ImageView notnow = (ImageView) findViewById(R.id.notnow);
            sure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GlobalVariables.videoRecord=1;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        c.requestPermissions(new String[]{
                                        android.Manifest.permission.CAMERA,
                                        android.Manifest.permission.RECORD_AUDIO,
                                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                MULTIPLE_PERMISSION_REQUEST);
                    }
                    dismiss();
                }
            });
            notnow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    GlobalVariables.videoRecord=2;
                    dismiss();
                }
            });

        }

        @Override
        public void onClick(View v) {
            Sounds.simplebuttons(c.getBaseContext());
            Animations.clickeffect(v);
            GlobalVariables.flaginapp=true;
            switch (v.getId()) {
                default:
                    break;
            }
        }
}
