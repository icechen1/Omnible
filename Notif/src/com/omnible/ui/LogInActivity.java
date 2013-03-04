package com.omnible.ui;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.omnible.*;
import com.omnible.notification.NotificationService;
import com.omnible.parser.Login;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.app.Activity;
import android.content.Intent;

public class LogInActivity extends SherlockActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		EditText user = (EditText) findViewById(R.id.et_username);
		EditText pass = (EditText) findViewById(R.id.et_password);
		SettingsProvider settings = SettingsProvider.newInstance(this);
		user.setText(String.valueOf(settings.getUsername()));
		pass.setText(settings.getPassword());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void handleLogin(View v) throws IOException, InterruptedException, ExecutionException{
		EditText user = (EditText) findViewById(R.id.et_username);
		EditText pass = (EditText) findViewById(R.id.et_password);
		
		SettingsProvider settings = SettingsProvider.newInstance(this);
		settings.saveCegep(SettingsProvider.Cegep.MARIANO);
		settings.saveUsername(Integer.parseInt(user.getText().toString()));
		settings.savePassword(pass.getText().toString());
		
	   // Intent WebView = new Intent(this, NotificationService.class);
		Intent WebView = new Intent(this, WebViewActivity.class);
	    startActivity(WebView);

	}
}
