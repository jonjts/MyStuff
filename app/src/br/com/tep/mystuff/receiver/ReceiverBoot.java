package br.com.tep.mystuff.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import br.com.tep.mystuff.service.SyncService;

public class ReceiverBoot extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Intent i = new Intent(context,SyncService.class);
		context.startService(i);
	}

}
