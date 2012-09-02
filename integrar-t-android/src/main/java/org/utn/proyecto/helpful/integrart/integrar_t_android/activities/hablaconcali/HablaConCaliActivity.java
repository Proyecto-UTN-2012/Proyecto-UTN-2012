package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.hablaconcali;

import java.io.IOException;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;

import android.R.integer;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.hcc_main)
public class HablaConCaliActivity extends RoboActivity implements
		OnCompletionListener {

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub

		// MediaPlayer frase = null;
		//
		// frase = MediaPlayer.create(getApplicationContext(), R.raw.chau);
		// frase.setOnCompletionListener(this);
		// frase.start();
		listening = false;
		if (mRecorder != null) {
			mRecorder.stop();
			mRecorder.release();
			mRecorder = null;
		}
		listening = false;
		super.onDestroy();
		// // frase.reset();
		// frase = null;

	}

	@Override
	protected void onStop() {

		listening = false;

		if (mRecorder != null) {
			mRecorder.stop();
			mRecorder.release();
			mRecorder = null;
		}
		nrofrase = 20;
		super.onStop();
	}

	private MediaRecorder mRecorder = null;

	// @InjectView(R.id.button1)
	// private Button button1;
	public boolean listening = true;
	public boolean grito = false;
	public boolean contesto = false;
	public Double amplitude1;
	public int nrofrase = 0;
	public int frases[] = { R.raw.como_te_llamas, R.raw.queres_jugar_conmigo,
			R.raw.cuantos_anios_tenes, R.raw.vos_sos_dios, R.raw.chau };;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		// final MediaPlayer frase = new MediaPlayer();
		Log.d("Habla con aida", "ahora si estoy logiando el onCreate");

		final MediaPlayer frase = MediaPlayer.create(getApplicationContext(),
				R.raw.hola);

		frase.start();
		frase.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {

				if (mp != null) {
					mp.stop();
					mp.release();
					mp = null;
				}

				new Ear().execute();
				// mp.release();
			}
		});
		// frase.start();
		// button1.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		//
		// frase.reset();
		// }
		// });

		// contesto = false;

		// new Ear().execute();

	} // cierra el oncreate

	// Metodo que inicializa la escucha
	public void start() {
		if (mRecorder == null) {

			// Inicializamos los parametros del grabador
			mRecorder = new MediaRecorder();
			mRecorder
					.setAudioSource(MediaRecorder.AudioSource.VOICE_RECOGNITION);
			mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
			mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

			// No indicamos ningun archivo ya que solo queremos escuchar
			mRecorder.setOutputFile("/dev/null");

			try {
				mRecorder.prepare();
			} catch (IllegalStateException e) {
				Log.e("error", "IllegalStateException");
			} catch (IOException e) {
				Log.e("error", "IOException");
			}
			mRecorder.start();
		}

	}

	// Para la escucha
	public void stop() {

		if (mRecorder != null) {
			mRecorder.stop();
			mRecorder.release();
			mRecorder = null;

		}

		if (grito == false) {

			if ((amplitude1 > 0.0) && (nrofrase < 3)) {
				listening = true;
				contesto = false;
				sigfrase();
			} else if ((amplitude1 > 0.0) && (nrofrase == 3)
					&& (grito == false)) {
				listening = true;
				contesto = false;
				nrofrase = 4;
				sigfrase();

			}

		}

		
	}

	public void sigfrase() {
		// MediaPlayer frase = new MediaPlayer();
		// frase = MediaPlayer.create(getApplicationContext(),
		// R.raw.como_te_llamas);
		// frase.setOnCompletionListener(this);
		// frase.start();

		final MediaPlayer frase = MediaPlayer.create(getApplicationContext(),
				frases[nrofrase]);
		nrofrase = nrofrase + 1;
		frase.start();
		frase.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {

				if (mp != null) {
					mp.stop();
					mp.release();
					mp = null;
				}

				new Ear().execute();
			}

		});

	}

	// Devuelve la mayor amplitud del sonido captado desde la ultima vez que se
	// llamo al metodo

	public double getAmplitude() {
		if ((mRecorder != null) && (listening == true)) {
			Log.d("en getamplitud", "estoy tomando la amplitud");
			return (double) (mRecorder.getMaxAmplitude());
		}
		Log.d("en getamplitude ", "el recorder es null");
		return 0d;
	}

	public class Ear extends AsyncTask<Void, Double, Void> {
		protected void onPreExecute() {
			start();
			// double amplitudeini = 20 * Math.log10(getAmplitude() / 32768.0);
			// if (amplitudeini < -80) {
			// amplitudeini = new Double(-80);
			// }
		}

		@Override
		protected Void doInBackground(Void... arg0) {
			amplitude1 = (double) 0;

			double amplitude = (double) 0;

			while (listening) {

				Log.d("en background ", "listening es true");
				if (amplitude1 == 0.0) {
					SystemClock.sleep(50);
					amplitude = getAmplitude();

					SystemClock.sleep(600);
					// amplitude = 20 * Math.log10(getAmplitude() / 32768.0);
					amplitude = getAmplitude();
					amplitude1 = amplitude;
					Log.d("HablaConCali", "Amplitud1: " + amplitude1);
				}

				SystemClock.sleep(400);
				amplitude = getAmplitude();

				// double amplitude = getAmplitude();
				// Double amplitude = 0d;
				// if (mRecorder != null) {
				// amplitude = (double) mRecorder.getMaxAmplitude();
				Log.d("HablaConCali", "Amplitud: " + amplitude);
				// }

				if (amplitude > amplitude1 + 2000) {
					publishProgress(amplitude);
					contesto = true;
				}

				if ((amplitude < amplitude1 + 2000) && (contesto == true)) {
					listening = false;
					Log.d(amplitude1 + "habla con cali",
							"salió con esta amplitud " + amplitude);
				}

				// if (amplitude < amplitudeini){
				// contesto = true;
				// }
				// if ((amplitude < amplitudeini+1) && (contesto == true)){

				// listening = false;

				// }
			}
			return null;

		}

		@Override
		protected void onProgressUpdate(Double... values) {
			Double value = values[0];
			if (value > 19000) {
				grito = true;
				stop();

			}
		}

		@Override
		protected void onPostExecute(Void result) {
			stop();

		}

	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		mp.release();
	}

} // cierra la clase