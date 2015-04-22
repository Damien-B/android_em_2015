package fr.damienbannerot.android.firstapplication;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;


public class WebViewActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
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
        MySingleton.onOptionsItemSelected(this, item);
        return super.onOptionsItemSelected(item);
    }

    public void onGoBtnClick(View view){
        EditText url = (EditText) findViewById(R.id.act_webview_url);
        String urlStr = url.getText().toString();
        if(urlStr.equals("")){
            Toast.makeText(this, R.string.act_webview_no_url_message, Toast.LENGTH_SHORT).show();
        }else{
            if(!urlStr.startsWith("http")){
                urlStr = "http://" + urlStr;
            }
            WebView webview = (WebView) findViewById(R.id.act_webview_webview);
            webview.setWebViewClient(new WebViewClient());
            webview.loadUrl(urlStr);
        }
    }
}
