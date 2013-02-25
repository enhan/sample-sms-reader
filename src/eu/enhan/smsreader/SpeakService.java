package eu.enhan.smsreader;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: manu
 * Date: 2/24/13
 * Time: 11:23 AM
 * To change this template use File | Settings | File Templates.
 */
public class SpeakService extends IntentService implements TextToSpeech.OnInitListener{

	private TextToSpeech tts;


	public SpeakService() {
		super("SpeakService");
	}

	@Override
	public void onCreate() {
		super.onCreate();
		tts = new TextToSpeech(getApplicationContext(), this);

	}

	@Override
	protected void onHandleIntent(Intent intent) {
		Log.d("MANU", "Going to talk");
		Bundle bundle = intent.getExtras();
		SmsMessage[] msgs = null;
		String str = "";
		if (bundle != null){
			Object[] pdus = (Object[]) bundle.get("pdus");
			msgs = new SmsMessage[pdus.length];
			for(int i = 0 ; i<msgs.length ;i++){
				msgs[i]=SmsMessage.createFromPdu((byte[]) pdus[i]);
				str += "Message reçu. ";
				str += msgs[i].getDisplayMessageBody();
				str +=  "\n";
			}


		}

		tts.speak(str, TextToSpeech.QUEUE_FLUSH, null);
		long endTime = System.currentTimeMillis() + 15*1000;
		while (System.currentTimeMillis() < endTime){
			synchronized (this){
				try{
					wait(endTime - System.currentTimeMillis());
				} catch (InterruptedException e) {

				}
			}
		}
		Log.d("MANU","Done !");


	}

	@Override
	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS) {

			int result = tts.setLanguage(Locale.FRANCE);

			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
				Log.e("TTS", "This Language is not supported");
			}

		} else {
			Log.e("TTS", "Initilization Failed!");
		}
	}

	@Override
	public void onDestroy() {
		if (tts != null){
			tts.stop();
			tts.shutdown();
		}
		super.onDestroy();
	}
}
