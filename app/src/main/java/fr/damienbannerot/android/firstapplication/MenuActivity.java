package fr.damienbannerot.android.firstapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class MenuActivity extends ActionBarActivity {

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Intent intent = getIntent();
        String email = getIntent().getStringExtra("email");
        TextView emailView = (TextView) findViewById(R.id.act_menu_email);
        emailView.setText(email);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_logout) {
            askLogoutConfirmation();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void startMainActivity(){

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }

    private boolean askLogoutConfirmation(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(R.string.act_menu_dialog_message)
        .setTitle(R.string.act_menu_dialog_title);

        //adding buttons
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("is_logged", false);
                editor.commit();
                startMainActivity();
                finish();
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

    public void webViewBtnClick(View view){
        Intent intent = new Intent(this, WebViewActivity.class);
        startActivity(intent);
    }

    public void PhotoBtnClick(View view){
        Intent intent = new Intent(this, PhotoActivity.class);
        startActivity(intent);
    }
}