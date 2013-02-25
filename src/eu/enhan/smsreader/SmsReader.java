package eu.enhan.smsreader;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: manu
 * Date: 2/23/13
 * Time: 11:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class SmsReader  extends BroadcastReceiver {




	@Override
	public void onReceive(Context context, Intent intent) {
		Log.d("MANU", "Received SMS");

		Intent speakIntent = new Intent(context, SpeakService.class);
		speakIntent.putExtras(intent);
		context.startService(speakIntent);

	}


}
