package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.handplay;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class LevelOneHandManager implements HandManager {
	private static final Random rnd = new Random();
	
	private final HandPlayActivity activity;
	private final Hand hand;
	private Finger pulgar;
	private FingerPoint point;
	private ImageView[] views;
	private boolean ready;
	public LevelOneHandManager(FingerPoint[] fingerPoints, HandPlayActivity activity){
		this.activity = activity;
		this.hand = new Hand(fingerPoints);
		pulgar = this.hand.getFinger(FingerNames.PULGAR);
		point = getRandomPoint();
		activity.clean();
		views = showMark(new FingerPoint[]{pulgar.getPoints(), point});
		ready = true;
	}
	
	private ImageView[] showMark(FingerPoint[] points){
		return activity.putMarks(points);
	}

	private FingerPoint getRandomPoint() {
		float[] area = hand.getArea();
		float x = randomBetween(area[0], area[2]);
		float y = randomBetween(area[1], area[3]);
		return new FingerPoint(x, y);
	}

	private float randomBetween(float a, float b) {
		return rnd.nextFloat()*(b-a) + a; 
	}

	@Override
	public boolean onTouch(View view, MotionEvent event) {
		if(!ready) return false;
		if(event.getAction() != MotionEvent.ACTION_DOWN && event.getAction() != MotionEvent.ACTION_POINTER_2_DOWN) return false;
		int currentFingers = event.getPointerCount();
		int fingerOks=0;
		activity.clean();
		Log.d("LevelOneHandPlayManager", "OnTouch " + currentFingers + " Action: " + event.getAction());
		for(int i=0;i<currentFingers;i++){
			if(findViewByPoint((int)event.getX(i), (int)event.getY(i))!=null){
				fingerOks++;
				activity.putFingers(new FingerPoint[]{new FingerPoint(event.getX(i), event.getY(i))});
			}
		}
		activity.putMarks(new FingerPoint[]{pulgar.getPoints(), point});
		if(fingerOks==2){
			Log.d("LevelOneHandPlayManager", "Success!!!");
			activity.success();
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				
				@Override
				public void run() {
					activity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							point = getRandomPoint();
							activity.clean();
							views = showMark(new FingerPoint[]{pulgar.getPoints(), point});
							ready = true;
						}
					});
				}
			}, 4500);
			ready = false;
		}
		return true;
	}

	@Override
	public HandManager getManager() {
		return this;
	}
	
	private View findViewByPoint(int x, int y){
	for(View view : views){
		int locations[] = new int[2];
		view.getLocationOnScreen(locations);
		int vx= locations[0];
		int vy= locations[1];
		int vx2 = vx + view.getWidth();
		int vy2 = vy + view.getHeight();
		
		if(x >= vx && x <= vx2
				&& y >= vy && y <= vy2)
			return view;
	}
	return null;
}

}
