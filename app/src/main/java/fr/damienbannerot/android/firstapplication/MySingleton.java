package fr.damienbannerot.android.firstapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;

/**
 * Created by Damien on 22/04/15.
 */
public class MySingleton {
    private static MySingleton ourInstance = new MySingleton();
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
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_logout) {
            askLogoutConfirmation(activity);
            return true;
        }

        return true;
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
