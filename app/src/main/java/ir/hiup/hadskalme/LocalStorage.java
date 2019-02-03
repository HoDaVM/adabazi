package ir.hiup.hadskalme;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by arashjahani on 12/23/2017 AD.
 */

public class LocalStorage {

    public static SharedPreferences sharedPreferences;

    public static SharedPreferences get(Context context) {
        if (sharedPreferences == null)
            sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);

        return sharedPreferences;
    }

    public static void save(Context context, Object... items) {

        try {
            if (sharedPreferences == null)
                sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            for (int i = 0; i < items.length; i += 2) {

                if (items[i + 1] == null) {
                    continue;
                }
                String key = items[i].toString();
                Object value = items[i + 1];

                if (value instanceof Boolean) {
                    editor.putBoolean(key, Boolean.valueOf(value.toString()));
                } else if (value instanceof Integer) {
                    editor.putInt(key, ((Integer) value).intValue());

                } else {
                    editor.putString(key, value.toString());
                }


            }
            editor.commit();

        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }

    public static void remove(Context context, Object... items) {

        try {
            if (sharedPreferences == null)
                sharedPreferences = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);

            SharedPreferences.Editor editor = sharedPreferences.edit();

            for (int i = 0; i < items.length; i++) {

                editor.remove(items[i].toString());

            }
            editor.apply();

        } catch (Exception ex) {
            ex.printStackTrace();

        }

    }


}
