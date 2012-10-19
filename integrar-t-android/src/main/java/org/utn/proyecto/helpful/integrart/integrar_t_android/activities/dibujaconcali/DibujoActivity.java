package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.dibujaconcali;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
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
	
	@InjectView(R.id.frame)
	private FrameLayout frame;

	@InjectView(R.id.miniature)
	private ImageView miniature;
	private int dibujo;
	private static Map<Integer, Point[]> map = new HashMap<Integer, Point[]>();
	{
		map.put(R.drawable.corazon, new Point[]{
				new Point(640,110),new Point (420,20),new Point(200, 200), new Point(460,490), new Point(640,630), new Point(820,490),new Point(1080,200), new Point(860,20) 
				
		});
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		final Context context = this;
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
		fade.addListener(new Animator.AnimatorListener() {
			
			@Override
			public void onAnimationStart(Animator animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animator animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animator animation) {
			Point[] points=map.get(dibujo);
			
			for (Point point:points){
				ImageView image = new ImageView(context);
				image.setLayoutParams(new ViewGroup.LayoutParams(20, 20));
				image.setBackgroundResource(R.drawable.circle_grey);
				image.setX((float)point.x);
				image.setY((float)point.y);
				frame.addView(image);
			}
			
			
			}
			
			@Override
			public void onAnimationCancel(Animator animation) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		
		
		
	}

}
