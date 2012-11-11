package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.cantaconcali;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;

import android.media.MediaPlayer;

public class FirstStateManager implements StateManager {
	private final CantaConCaliActivity activity;
	private final MediaPlayer hello;
	
	
	public FirstStateManager(final CantaConCaliActivity activity){
		this.activity = activity;
		this.hello = MediaPlayer.create(activity, R.raw.hola_canta_con_cali);
		hello.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){

			@Override
			public void onCompletion(MediaPlayer mp) {
				activity.startVoiceRecognitionActivity();
				mp.stop();
				mp.release();
			}
			
		});
		hello.start();
	}
	
	@Override
	public void processNo() {
		activity.end();
	}

	@Override
	public void processYes() {
		activity.selectSong();
		activity.setManager(new SelectSongStateManager(activity));
	}

}
