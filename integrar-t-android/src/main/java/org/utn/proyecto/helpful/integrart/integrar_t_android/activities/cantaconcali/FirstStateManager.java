package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.cantaconcali;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;
import org.utn.proyecto.helpful.integrart.integrar_t_android.domain.User;
import org.utn.proyecto.helpful.integrart.integrar_t_android.metrics.ActivityMetric;
import org.utn.proyecto.helpful.integrart.integrar_t_android.metrics.Metric;
import org.utn.proyecto.helpful.integrart.integrar_t_android.metrics.MetricsService;

import android.media.MediaPlayer;

public class FirstStateManager implements StateManager {
	private final CantaConCaliActivity activity;
	private final MetricsService metrics;
	private final MediaPlayer hello;
	private final User user;
	
	
	public FirstStateManager(final CantaConCaliActivity activity, MetricsService metrics, User user){
		this.activity = activity;
		this.metrics = metrics;
		this.user = user;
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
        Metric metrica = new Metric(user, ActivityMetric.CANTA_CON_CALI, 
        		activity.getString(R.string.metric_categoria_cantaconcali),
        		activity.getString(R.string.metric_categoria_cantaconcali_no));
        metrics.sendMetric(metrica);
		activity.end();
	}

	@Override
	public void processYes() {
            Metric metrica = new Metric(user, ActivityMetric.CANTA_CON_CALI, 
            		activity.getString(R.string.metric_categoria_cantaconcali),
            		activity.getString(R.string.metric_categoria_cantaconcali_si));
            metrics.sendMetric(metrica);
		activity.selectSong();
		activity.setManager(new SelectSongStateManager(activity));
	}

}
