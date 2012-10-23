package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.handplay;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public abstract class AbstractLevelHandManager implements HandManager {
	protected static final Random rnd = new Random();
	protected final HandPlayActivity activity;
	protected final Hand hand;
	protected Finger pulgar;
	private FingerPoint[] points;
	private ImageView[] views;
	private boolean ready;
	protected final FingerPoint[] origin;
	protected int level;
	
	public AbstractLevelHandManager(FingerPoint[] fingerPoints, HandPlayActivity activity, int level){
		this.level = level;
		this.origin = fingerPoints;
		this.activity = activity;
		this.hand = new Hand(fingerPoints);
		pulgar = this.hand.getFinger(FingerNames.PULGAR);
		points = getRandomPoints();
		activity.clean();
		views = showMark(pulgar.getPoints(), points);
		ready = true;
	}
	
	protected ImageView[] showMark(FingerPoint pulgar, FingerPoint[] points){
		FingerPoint[] buff = new FingerPoint[points.length+1];
		buff[0] = pulgar;
		for(int i=0;i<points.length;i++){
			buff[i+1] = points[i];
		}
		return activity.putMarks(buff);
	}
	
	abstract protected FingerPoint[] getRandomPoints();
	abstract protected int getFingerCount();
	
	private boolean detectPress(MotionEvent event){
		return event.getAction() == MotionEvent.ACTION_DOWN || 
			event.getAction() == MotionEvent.ACTION_POINTER_2_DOWN ||
			event.getAction() == MotionEvent.ACTION_POINTER_3_DOWN ||
			event.getAction() == 773 ||
			event.getAction() == 1029;
	}
	
	@Override
	public boolean onTouch(View view, MotionEvent event) {
		if(!ready) return false;
		if(!detectPress(event)) return false;
		int currentFingers = event.getPointerCount();
		int fingerOks=0;
		activity.clean();
		Log.d("AbstractLevelHandManager", "OnTouch " + currentFingers + " Action: " + event.getAction());
		for(int i=0;i<currentFingers;i++){
			activity.putFingers(new FingerPoint[]{new FingerPoint(event.getX(i), event.getY(i))});
			if(findViewByPoint((int)event.getX(i), (int)event.getY(i))!=null){
				fingerOks++;
			}
		}
		showMark(pulgar.getPoints(), points);
		if(fingerOks==getFingerCount()){
			Log.d("AbstractLevelHandManager", "Success!!!");
			activity.success();
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				
				@Override
				public void run() {
					activity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							points = getRandomPoints();
							activity.clean();
							views = showMark(pulgar.getPoints(), points);
							ready = true;
						}
					});
				}
			}, 4500);
			ready = false;
		}
		return true;
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
	
	protected float randomBetween(float a, float b) {
		return rnd.nextFloat()*(b-a) + a; 
	}
}
