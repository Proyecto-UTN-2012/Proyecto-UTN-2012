package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.dibujaconcali;

import java.util.Timer;
import java.util.TimerTask;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.dcc_dibujo)
public class DibujoActivity extends RoboActivity {
	@InjectView(R.id.view)
	private FrameLayout view;

	@InjectView(R.id.miniature)
	private ImageView miniature;
	private int dibujo;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		Bundle arguments = getIntent().getExtras();
		dibujo = arguments.getInt("dibujo");
		miniature.setImageResource(dibujo);
		miniature.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
		view.setAlpha(1f);		
			}
		});
		
		view.setBackgroundResource(dibujo);
		ObjectAnimator fade = ObjectAnimator.ofFloat(null, "alpha", 1.0f, 0.1f);
		fade.setDuration(2000);
		fade.setStartDelay(2000);
		fade.setInterpolator(new LinearInterpolator());
		fade.setTarget(view);
		fade.start();
		
		
		
		
		
		
	}

}
