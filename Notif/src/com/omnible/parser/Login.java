package com.omnible.parser;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import com.omnible.SettingsProvider;

public class Login {
	String username;
	String password;
	Context cxt;
	
	public Login(Context cxt){
		SettingsProvider sp = SettingsProvider.newInstance(cxt);
		this.cxt = cxt;
		this.username = String.valueOf(sp.getUsername());
		this.password = sp.getPassword();
		
	}

	public Map<String, String> getCookies() throws InterruptedException, ExecutionException{

		return new LoginTask().execute().get();
	}
	

	class LoginTask extends AsyncTask<Void, Void, Map<String, String>>{
	    @Override   
	    protected Map<String, String> doInBackground(Void... params) {
			//L = ANG is English
			//C = MPO is Marianopolis College
	    	

	    	Response loginPage = null;
			Response response = null;
			try {
				Log.d("OMNIBLE", username);
				loginPage = Jsoup.connect("https://marianopolis.omnivox.ca/Intr/Module/Identification/Login/Mobile/LoginMobile.aspx")
					    .userAgent("Mozilla/5.0 (iPhone; CPU iPhone OS 5_0 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9A334 Safari/7534.48.3")
                        .referrer("https://marianopolis.omnivox.ca/")
                        .execute();
				
				//For some reason you can't login without this k value
				Element kInput = loginPage.parse().select("input[name=k]").first();
				String k_value = kInput.attr("value");
				
				response = Jsoup
						.connect("https://marianopolis.omnivox.ca//Intr/Module/Identification/Login/Login.aspx?Mobile=True") //&C=MPO&E=P&L=ANG
					    .data("LoginMobile", "1", 
					    	"Mobile", "true", 
					    	"StatsEnvUsersNbCouleurs","",
					    	"StatsEnvUsersResolution", "",
					    	"TypeIdentification", "Etudiant",
					    	"LabelSelectionIdentification", "{INTRAFLEX=INTR_AUTH_0300}",
					    	"Device", "",
					    	"k",k_value,
					    	"NoDA", username,
					    	"PasswordEtu", password)

					    .userAgent("Mozilla/5.0 (iPhone; CPU iPhone OS 5_0 like Mac OS X) AppleWebKit/534.46 (KHTML, like Gecko) Version/5.1 Mobile/9A334 Safari/7534.48.3")
                        .referrer("https://marianopolis.omnivox.ca/")
                        .timeout(30000)
                        .cookies(loginPage.cookies())
					    .method(Method.POST).execute();
				Log.d("OMNIBLE", response.url().toString());
				Log.d("OMNIBLE", response.headers().toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}         
			return response.cookies();
	    }
	}
}
