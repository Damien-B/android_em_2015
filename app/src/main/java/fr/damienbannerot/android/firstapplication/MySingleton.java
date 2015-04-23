package fr.damienbannerot.android.firstapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;

import java.util.Locale;

/**
 * Created by Damien on 22/04/15.
 */
public class MySingleton {
    private static MySingleton ourInstance = new MySingleton();
    private static Locale locale;
    private Bitmap bitmap;

    public static Bitmap getBitmap() {
        return ourInstance.bitmap;
    }
    public static void setBitmap(Bitmap bitmap) {
        ourInstance.bitmap = bitmap;
    }

    public static boolean onOptionsItemSelected(ActionBarActivity activity, MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        switch(id){
            case R.id.action_settings :
                return true;
            case R.id.action_logout :
                askLogoutConfirmation(activity);
                return true;
            case R.id.action_lang_en :
                changeLang(activity, "en");
                return true;
            case R.id.action_lang_fr :
                changeLang(activity, "fr");

                return true;
        }
        return true;
    }

    private static void changeLang(ActionBarActivity activity, String lang){
        saveLang(activity, lang);
        setLocale(activity, lang);
        activity.recreate();
    }

    private static void saveLang(ActionBarActivity activity, String lang){

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences((Activity) activity);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("lang", lang);
        editor.commit();
    }

    public static void restoreLang(ActionBarActivity activity){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences((Activity) activity);
        setLocale(activity, preferences.getString("lang", "en"));
    }

    private static void setLocale(ActionBarActivity activity, String lang){
        locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;

        activity.getResources().updateConfiguration(config, null);

    }


    private static boolean askLogoutConfirmation(final ActionBarActivity activity){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setMessage(R.string.act_menu_dialog_message)
                .setTitle(R.string.act_menu_dialog_title);

        //adding buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id) {
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(((Activity) activity).getApplicationContext());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("is_logged", false);
                editor.commit();
                startMainActivity(activity);
                activity.finish();
            }

        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d("xxxxxxx", "Cancel button");
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
        return false;

    }

    private static void startMainActivity(final ActionBarActivity activity){

        Intent intent = new Intent(activity, MainActivity.class);
        ((Activity) activity).startActivity(intent);

    }

    private MySingleton() {
    }
}
