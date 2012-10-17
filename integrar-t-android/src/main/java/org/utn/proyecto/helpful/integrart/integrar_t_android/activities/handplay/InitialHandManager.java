package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.handplay;

import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

public class InitialHandManager implements HandManager {
	private final HandPlayActivity activity;
	private HandManager next;
	private FingerPoint[] fingers = new FingerPoint[5];
	private int currentFingers;
	
	public InitialHandManager(HandPlayActivity activity){
		this.activity = activity;
		this.next = this;
	}
	@Override
	public boolean onTouch(View view, MotionEvent event) {
		currentFingers = event.getPointerCount();
		if(currentFingers==5){
			for(int i=0;i<5;i++){
				fingers[i] = new FingerPoint(event.getX(i), event.getY(i));
			}
		}
		if(event.getAction()==MotionEvent.ACTION_UP && fingers[4]!=null){
			ObjectAnimator fade = ObjectAnimator.ofFloat(null, "alpha", 1.0f, 0.0f);
			fade.setDuration(2000);
			fade.setStartDelay(2000);
			fade.setInterpolator(new LinearInterpolator());
			ObjectAnimator[] animators = new ObjectAnimator[5];
			animators[0] = fade;
			for(int i=1;i<5;i++){
				animators[i] = fade.clone();
			}
			Log.d("Lista de Dedos", Arrays.asList(fingers).toString());
			activity.putFingers(fingers, animators);
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				
				@Override
				public void run() {
					next = new LevelOneHandManager(fingers);
					
				}
			}, 4500);
			next = null;
		}
		return true;
	}
	@Override
	public HandManager getManager() {
		return next;
	}
}
