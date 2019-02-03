package ir.hiup.hadskalme;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

import ir.hiup.hadskalme.Shared;
import ir.hiup.hadskalme.UIinit.Animations.Animations;
import ir.hiup.hadskalme.UIinit.Auth;
import ir.hiup.hadskalme.UIinit.Dialogs.LoginDialog;
import ir.hiup.hadskalme.UIinit.Dialogs.RegisterDialog;
import ir.hiup.hadskalme.UIinit.GlobalVariables;
import ir.hiup.hadskalme.UIinit.Sounds.Sounds;
import ir.hiup.hadskalme.UIinit.UiInit;
import ir.hiup.hadskalme.UIinit.validation;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RegisterActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    private static final String TAG = "RegisterActivity";
    private static final int RC_SMS_PERM = 122;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        smsPermission();
        Shared.write("usercanofflinebytime", "0");
        Shared.write("delay", String.valueOf(0));
        ir.hiup.hadskalme.Shared.write("PACKAGE", "FIRSTPACK");
        Shared.write("backgroundmusic", "okay");
        Shared.write("sfx", "okay");

        UiInit.Register(RegisterActivity.this);

        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animations.clickeffect(view);
                GlobalVariables.flaginapp = true;
                Sounds.simplebuttons(getBaseContext());
                if (!validation.userCanOffline())
                    Auth.GetCurrentVersion(RegisterActivity.this);
                Auth.GuestLogin(RegisterActivity.this);

            }
        });
        recordRunTime();
        startService(new Intent(RegisterActivity.this, ChangePrice.class));
    }

    public void recordRunTime() {
        ir.hiup.hadskalme.Shared.init(getBaseContext());
        ir.hiup.hadskalme.Shared.write("m4", String.valueOf(System.currentTimeMillis()));
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(RC_SMS_PERM)
    private void smsPermission() {
        if (EasyPermissions.hasPermissions(getApplicationContext(), Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_PHONE_STATE)) {
            // Have permission, do the thing!
        } else {
            // Request one permission
            EasyPermissions.requestPermissions(this, getString(R.string.rationale_sms),
                    RC_SMS_PERM, Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_PHONE_STATE);
        }
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Log.d(TAG, "onPermissionsGranted:" + requestCode + ":" + perms.size());
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());
    }
}
