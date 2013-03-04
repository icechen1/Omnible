package com.omnible.ui;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.omnible.*;
import com.omnible.notification.NotificationService;
import com.omnible.parser.Login;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.app.Activity;
import android.content.Intent;

public class WebViewActivity extends SherlockActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web);
		
     // -- JSoup part here -- //
	    Login l = new Login(this);
	    try {
	    	onFinish(l.getCookies());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void onFinish(Map<String, String> m) {
	    final WebView wv = (WebView) findViewById(R.id.webView);
	 // -- Android Cookie part here --
	    CookieSyncManager.getInstance().sync();
	    CookieManager cm = CookieManager.getInstance();
	 //   k=QXF1bUhDVnJUSDVPLzJzYzY0czVycXg4RGFPWTVyNUFxdFdQdk4yOGZMUUZLNkpvQVVvaVQ4M0pZeDJLSks0aw__; path=/

	    Iterator<Entry<String, String>> it = m.entrySet().iterator();
	    final String url = "https://marianopolis.omnivox.ca/Mobl/";
	    while (it.hasNext()) {
	    	Map.Entry<String, String> pairs = (Map.Entry<String, String>)it.next();
	    	Log.d("OMNIBLE", pairs.getKey() + " = " + pairs.getValue());
	    	cm.setCookie(url, pairs.getKey()+"="+ pairs.getValue() +"; path=/");
	    	it.remove(); // avoids a ConcurrentModificationException
	    }

        WebSettings webSettings = wv.getSettings();
        webSettings.setSavePassword(true);
        webSettings.setSaveFormData(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setDomStorageEnabled(true);
        
        wv.setWebChromeClient(new WebChromeClient());
        wv.setWebViewClient(new WebViewClient() {  
            @Override  
            public void onPageFinished(WebView view, String url)  
            {  
        	    wv.loadUrl("javascript:(function() { " + "document.getElementById('divFooter').style.display = 'none'; " + "})()");

            }  
        });  
        
        webSettings.setUserAgentString("Mozilla/5.0 (iPhone; CPU iPhone OS 5_0 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9A334 Safari/7534.48.3");
	    wv.loadUrl(url);
    	//wv.setWebViewClient(new WebViewClient());
	}

}