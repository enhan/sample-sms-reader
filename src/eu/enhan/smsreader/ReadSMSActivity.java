package eu.enhan.smsreader;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Locale;

public class ReadSMSActivity extends Activity implements TextToSpeech.OnInitListener{

	private TextToSpeech tts;
	private Button btnSpeak;
	private EditText txtText;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

	    tts = new TextToSpeech(this, this);
	    btnSpeak = (Button) findViewById(R.id.speak);
	    txtText = (EditText) findViewById(R.id.edit_message);
	    btnSpeak.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
			    speakMessage();
		    }
	    });
    }

	public void speakMessage(){
		String text = txtText.getText().toString();
		tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
	}

	@Override
	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS) {

			int result = tts.setLanguage(Locale.FRANCE);

			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
				Log.e("TTS", "This Language is not supported");
			} else {
				btnSpeak.setEnabled(true);
				speakMessage();
			}

		} else {
			Log.e("TTS", "Initilization Failed!");
		}
	}

	@Override
	protected void onDestroy() {
		if (tts != null){
			tts.stop();
			tts.shutdown();
		}
		super.onDestroy();
	}
}
