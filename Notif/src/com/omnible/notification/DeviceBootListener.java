package com.omnible.notification;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class DeviceBootListener extends BroadcastReceiver 
{
	
	private boolean service_started=false;
	private PendingIntent mAlarmSender;

@Override
public void onReceive(Context context, Intent intent) 
{
   // Intent Service = new Intent(context, NotificationService.class);
   // context.startService(Service);
    if(!service_started){

        Intent Intent = new Intent(context, NotificationService.class);
        context.startService(Intent);

        service_started=true;
    }
}
}