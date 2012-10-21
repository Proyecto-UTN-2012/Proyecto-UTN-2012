package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.cantaconcali;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
@ContentView(R.layout.ccc_main)
public class CantaConCaliActivity extends RoboActivity {
	@InjectView(R.id.cali)
	private ImageView cali;
	@InjectView(R.id.no)
	private Button no;
	private AnimationDrawable caliAnimation;
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
	MediaPlayer sonidoCali = MediaPlayer.create(this, R.raw.canta_con_cali_1);
	sonidoCali.start();
	caliAnimation = (AnimationDrawable) cali.getBackground();
	no.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			CantaConCaliActivity context= (CantaConCaliActivity) v.getContext();
			MediaPlayer sonidoCali = MediaPlayer.create(context, R.raw.chau);
			sonidoCali.start();
			context.finish();
			
			// TODO Auto-generated method stub
			
		}
	});
		
	}
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {

		super.onWindowFocusChanged(hasFocus);

		if (hasFocus)
			caliAnimation.start();
	}

	
	
}
