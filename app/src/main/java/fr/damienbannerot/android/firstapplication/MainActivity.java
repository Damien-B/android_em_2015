package fr.damienbannerot.android.firstapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean isLogged = preferences.getBoolean("is_logged", false);
        if(isLogged){
            //TODO start next activity
            startMenuActivity();
            finish();
        }

        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

        return super.onOptionsItemSelected(item);
    }

    public void onSubmitClick(View view){


        EditText emailView = (EditText) findViewById(R.id.act_main_email);
        String email = emailView.getText().toString();
        if(email.equals("")) {
            Toast toast = Toast.makeText(this, R.string.act_main_email_notfound, Toast.LENGTH_SHORT);
            toast.show();
        }else {

            EditText passwordView = (EditText) findViewById(R.id.act_main_password);
            String password = passwordView.getText().toString();
            if (password.equals("")) {
                Toast toast = Toast.makeText(this, R.string.act_main_password_notfound, Toast.LENGTH_SHORT);
                toast.show();
            } else {
                if(preferences.getString("user_email", "").equals(email)){
                    if(preferences.getString("user_password", "").equals(password)){
                        Toast toast = Toast.makeText(this, R.string.act_main_invalid_password, Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }else{
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("user_email", email);
                    editor.putString("user_password", password);
                    editor.putBoolean("is_logged", true);
                    editor.commit();
                    startMenuActivity();
                }

            }
        }
    }

    public void startMenuActivity(){

        Intent intent = new Intent(this, MenuActivity.class);
        intent.putExtra("email", preferences.getString("user_email", ""));
        startActivity(intent);

    }
}
