package ir.hiup.hadskalme;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import io.appnex.android.subscribe.AppnexPushSubscribeDialog;
import io.appnex.android.subscribe.ListenersCallback;


/**
 * Created by H.VM on 13,November,2018
 */
public class AppnexActivity extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {

            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        new AppnexPushSubscribeDialog.Builder(this)
                .listeners(new ListenersCallback() {
                    @Override
                    public void LoadingFail() {

                    }

                    @Override
                    public void dialogDissmiss() {
                        finish();
                    }

                    @Override
                    public void purchaseDone() {

                    }

                    @Override
                    public void purchaseFail() {

                    }

                    @Override
                    public void networkFail() {

                    }
                })
                .show();
    }
}
