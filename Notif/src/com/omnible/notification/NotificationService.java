package com.omnible.notification;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;

import com.omnible.*;
import com.omnible.parser.*;

public class NotificationService extends Service{
    private final IBinder mBinder = new LocalBinder();
    
    public class LocalBinder extends Binder {
    	NotificationService getService() {
            return NotificationService.this;
        }
    }
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId){
        //PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        
        //m.setRepeating(AlarmManager.RTC, TIME.getTime().getTime(), interval, pendingIntent);
        
        //Don't shut me off until we get all the info online
        return START_STICKY;
	}
	
    @Override
    public void onCreate() {
    	//Fire up the asynctask to get the info we need
    	new GetInfo().execute(this);
    }
    
    public void onResultGet(){
    	setUpNextRun();
    	finish();
    }
    
    private void setUpNextRun() {
    	//Run service every hour or so
        Intent intent = new Intent(this, NotificationService.class);
        PendingIntent pendingIntent =
            PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        
        //Create an offset from the current time in which the alarm will go off.
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.HOUR, 1);
        
        AlarmManager am = (AlarmManager)getSystemService(Context.ALARM_SERVICE);   
        am.set(AlarmManager.RTC_WAKEUP,   
        		cal.getTimeInMillis(), pendingIntent);   //TODO make this user configurable; right now it's 1 hour
		
	} 

	private void finish() {
		stopSelf();
	}

	/**
     * A little class to fetch all the info we need in an async thread 
     * as to not hold up the main thread.
     * @author Icechen1
     *
     */
    private class GetInfo extends AsyncTask<Context, Void, ArrayList<String>> {
    	NotifParser parser;
    	Context cxt;

        @Override
        protected ArrayList<String> doInBackground(Context...values) {
        	cxt = values[0];
        	//Fetches the settings
        	SettingsProvider prov = SettingsProvider.newInstance(values[0]);

        	//Load data from the Internet
        	parser = new NotifParser(String.valueOf(prov.getUsername()),prov.getPassword());

            return parser.getNotifs();
        }      

        @Override
        protected void onPostExecute(ArrayList<String> result) {         
        	NotificationBuilder notif = NotificationBuilder.newInstance(cxt);
        	
        	notif.buildNotif(NotificationType.NEW_MIO, 42);
        	
        	onResultGet();
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
        }
  }
}
