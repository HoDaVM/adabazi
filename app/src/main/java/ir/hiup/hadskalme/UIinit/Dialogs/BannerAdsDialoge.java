package ir.hiup.hadskalme.UIinit.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ir.hiup.hadskalme.R;
import ir.hiup.hadskalme.UIinit.Animations.Animations;
import ir.hiup.hadskalme.UIinit.GlobalVariables;
import ir.hiup.hadskalme.UIinit.Sounds.Sounds;


/**
 * Created by Mahdi Asiyabi on 9/23/2017.
 */

public class BannerAdsDialoge extends Dialog implements View.OnClickListener {
        private Activity c;
        public Dialog d;
        String text,subject,Oklink,NotnowLink;

    public BannerAdsDialoge(Activity a, String text, String subject, String Oklink, String NotnowLink) {
            super(a);
            // TODO Auto-generated constructor stub
            this.c = a;
            getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            this.text=text;
            this.subject=subject;
            this.Oklink=Oklink;
            this.NotnowLink=NotnowLink;
        }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setCancelable(true);
            setContentView(R.layout.bannerads);
            final ImageView img = (ImageView) findViewById(R.id.imageads);
            final ImageView err = (ImageView) findViewById(R.id.khoroj);
            final TextView txt = (TextView) findViewById(R.id.invitetextt);
            Picasso.with(c).load(NotnowLink).fit().into(img);
            txt.setText(text);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!Oklink.equals("-"))
                    {
                        String url = Oklink;
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        c.startActivity(i);
                    }
                    dismiss();
                }
            });
            txt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!Oklink.equals("-"))
                    {
                        String url = Oklink;
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        c.startActivity(i);
                    }
                    dismiss();
                }
            });
            err.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });

            ImageView khoroj = (ImageView) findViewById(R.id.khoroj);
            khoroj.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Sounds.simplebuttons(c.getBaseContext());
            Animations.clickeffect(v);
            GlobalVariables.flaginapp=true;
            switch (v.getId()) {
                case R.id.khoroj:
                    dismiss();
                    break;
                default:
                    break;
            }
        }
}
