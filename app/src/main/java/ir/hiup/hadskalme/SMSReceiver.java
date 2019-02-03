package ir.hiup.hadskalme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.appnex.android.subscribe.sms.SubscribeSMSReceiverHandler;


public class SMSReceiver extends BroadcastReceiver {
    private static final String SMSNUMBER = "307566";

    public static final String APPSUBSCRIBED = "AppSubscribed";

    public interface SmsListener {
        public void messageReceived(String number, String pinCode);
    }

    private static SmsListener smsListener;
    private static String smsNumber;

    @Override
    public void onReceive(Context context, Intent intent) {


// Get Bundle object contained in the SMS intent passed in
        Bundle bundle = intent.getExtras();
        SmsMessage[] smsm = null;
        String sms_str = "";
        String number = "";
        String message = "";
        String pinCode;
        Log.v("SmsMessage", intent.toString());

        if (bundle != null) {
            // Get the SMS message
            Object[] pdus = (Object[]) bundle.get("pdus");
            smsm = new SmsMessage[pdus.length];
            for (int i = 0; i < smsm.length; i++) {
                smsm[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                number = smsm[i].getOriginatingAddress();
                message += smsm[i].getMessageBody().toString();

                Log.v("SsmMessage123", message);
            }

            message = message.replace("\n", "");

            SubscribeSMSReceiverHandler.onReceive(context, number, message);

            if (number.contains(SMSNUMBER)) {

                SharedPreferences sharedPreferencesHome = PreferenceManager.getDefaultSharedPreferences(context);
                String appSubscribe = sharedPreferencesHome.getString(APPSUBSCRIBED, "");
                if (!appSubscribe.equals("AppSubscribed")) {

                    Pattern p = Pattern.compile("-?\\d+");
                    Matcher m = p.matcher(message);
                    while (m.find()) {
                        pinCode = m.group();
                        if (pinCode.length() == 4) {

                            if (smsListener != null) {
                                smsListener.messageReceived(number, pinCode);
                            } else {


                                PackageManager pm = context.getPackageManager();
                                Intent launchIntent = pm.getLaunchIntentForPackage(context.getPackageName());
                                launchIntent.putExtra("number", number);
                                launchIntent.putExtra("code", pinCode);

                                context.startActivity(launchIntent);


                            }
                            return;

                        }
                    }
                }

            }

        }


    }

    public static void bindListener(String number, SmsListener listener) {
        smsListener = listener;
        smsNumber = number;
    }

    public static void unBindListener() {
        smsListener = null;
    }}