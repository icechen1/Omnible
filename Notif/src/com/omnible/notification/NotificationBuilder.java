package com.omnible.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.omnible.parser.*;

/**
 * Helper class to build and display notifications to the system bar.
 * Create a new instance using NotificationBuilder.newInstance() first
 * @author Icechen1
 *
 */
public class NotificationBuilder {
	Context cxt;

	
	NotificationBuilder(Context cxt){
		this.cxt = cxt;
	}
	
	
	/**
	 * Creates a new instance of NotificationBuilder
	 * @param cxt Context
	 * @return a NotificationBuilder object
	 */
	public static NotificationBuilder newInstance(Context cxt){
		return new NotificationBuilder(cxt);
	}
	
	/**
	 * Builds a notification and shows it
	 * @param type (NEW_MIO, NEW_GRADE, NEW_ASSIGN, NEW_MESSAGE, NEW_LEA)
	 * @param number number of notifications of the type
	 * @return true if successful
	 */
	public boolean buildNotif(NotificationType type, int number){
		//Set title TODO Add french versions using strings and fix plurial grammar
		String title = "New elements";
    	if (type == NotificationType.NEW_MIO){
    		title = number + " new Mio";
    	}
    	
    	if (type == NotificationType.NEW_GRADE){
    		title = number + " new grade";
    	}
    	
    	if (type == NotificationType.NEW_ASSIGN){
    		title = number + " new assignment Information";
    	}
    	
    	if (type == NotificationType.NEW_MESSAGE){
    		title = number + " new message";
    	}
    	
    	if (type == NotificationType.NEW_LEA){
    		title = number + " new Lea Message";
    	}
    	
    	String url = "http://www.example.com"; //TODO Redirect to the omnivox site
    	Intent i = new Intent(Intent.ACTION_VIEW);
    	i.setData(Uri.parse(url));
    //	Intent intent = new Intent(this, MainActivity.class);
    	PendingIntent pIntent = PendingIntent.getActivity(cxt, 0, i, 0);
    	
		int dot = 300;
		int short_gap = 100;    // Length of Gap Between dots/dashes
		long[] pattern = {
		    0,  // Start immediately
		    dot, short_gap, dot
		};
    	
    	// Build notification
    	Notification noti = new NotificationCompat.Builder(cxt)
    	        .setContentTitle(title)
    	        .setContentText("You have " + number + " messages.")
    	        .setSmallIcon(com.omnible.R.drawable.ic_launcher)
    	        .setContentIntent(pIntent)
    	  //      .addAction(R.drawable.ic_menu_edit, "Edit", pIntent)
    	  //      .addAction(R.drawable.ic_menu_mark, "Done", pIntent)
    	  //      .setStyle(new NotificationCompat.BigTextStyle().bigText(longText)) 
    	        .setTicker(title)
    	        .setVibrate(pattern)
    	        .setContentInfo(String.valueOf(number))
    	       // .setSound
    	        // setUsesChronometer option which subindication to display
    	        // setWhen 
    	        // setOngoing
    	        // setNumber
    	        .setLights(0xFFFF0000, 500, 500)
    	        // setLargeIcon 
    	        // setDeleteIntent USE THIS
    	        .build();
    	
    	  
    	NotificationManager notificationManager = 
    	  (NotificationManager) cxt.getSystemService(Context.NOTIFICATION_SERVICE);

    	// Hide the notification after it's selected
    	noti.flags |= Notification.FLAG_AUTO_CANCEL;
    	//noti.flags |= Notification.FLAG_NO_CLEAR;
    	try{
    	if (type == NotificationType.NEW_MIO){
    	notificationManager.notify(0, noti); 
    	}
    	
    	if (type == NotificationType.NEW_GRADE){
    	notificationManager.notify(1, noti); 
    	}
    	
    	if (type == NotificationType.NEW_ASSIGN){
    	notificationManager.notify(2, noti); 
    	}
    	
    	if (type == NotificationType.NEW_MESSAGE){
    	notificationManager.notify(3, noti); 
    	}
    	
    	if (type == NotificationType.NEW_LEA){
    	notificationManager.notify(4, noti); 
    	}
		return true;
    	}catch(Exception e){
    		return false;  		
    	}

		
	}
}
