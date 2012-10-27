package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.cantaconcali;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.cuentos.CurrentCuentoActivity;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.hablaconcali.HablaConCaliActivity.Ear;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
@ContentView(R.layout.ccc_main)
public class CantaConCaliActivity extends RoboActivity implements
OnCompletionListener  {
	@InjectView(R.id.cali)
	private ImageView cali;
	@InjectView(R.id.no)
	private Button no;
	@InjectView(R.id.si)
	private Button si;
	private AnimationDrawable caliAnimation;
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
	MediaPlayer sonidoCali = MediaPlayer.create(this, R.raw.hola_canta_con_cali);
	sonidoCali.start();
	caliAnimation = (AnimationDrawable) cali.getBackground();
	no.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			sacarBoton();
			
			CantaConCaliActivity context= (CantaConCaliActivity) v.getContext();
			MediaPlayer sonidoCali = MediaPlayer.create(context, R.raw.despedida_canta_con_cali);
			sonidoCali.start();
			sonidoCali.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer mp) {

					if (mp != null) {
						mp.stop();
						mp.release();
						mp = null;

					}

					finish();	
					
				}
			});

			
			
			// TODO Auto-generated method stub		
		}
	});
	si.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
		sacarBoton();
		
		}
	});
		
	
	} //termina oncreate
	
	public void sacarBoton (){
		no.setVisibility(View.INVISIBLE);
		si.setVisibility(View.INVISIBLE);
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {

		super.onWindowFocusChanged(hasFocus);

		if (hasFocus)
			caliAnimation.start();
	}
	@Override
	public void onCompletion(MediaPlayer arg0) {
		// TODO Auto-generated method stub
		
	}

	
	
}
