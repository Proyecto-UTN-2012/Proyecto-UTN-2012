package org.utn.proyecto.helpful.integrart.integrar_t_android.utils;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CaliView extends ImageView {
	private CaliHelper helper;
	private boolean isStoped = true;
	private AnimationDrawable currentAnimation;
	public CaliView(Context context) {
		super(context);
	}
	
	public CaliView(Context context, AttributeSet attrs){
		super(context, attrs);
	}
	
	public CaliView(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
	}

	public CaliView(Context context, CaliHelper helper) {
		super(context);
		setHelper(helper);
	}
	
	
	public void setHelper(CaliHelper helper){
		this.helper = helper;
		this.setBackgroundResource(helper.getInitTalkingCaliResource());
	}
	
	public void talk(){
		currentAnimation = (AnimationDrawable) this.getBackground();
		currentAnimation.start();
		synchronized (this) {
			isStoped = false;			
		}
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				if(!isStoped){
					((Activity)getContext()).runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							currentAnimation.stop();
							setBackgroundResource(helper.getTalkingCaliResource());
							currentAnimation = (AnimationDrawable) getBackground();
							currentAnimation.start();							
						}
					});
				}
			}
		}, 1500);
	}
	
	public void stop(){
		synchronized (this) {
			isStoped = true;			
		}
		currentAnimation.stop();
		setBackgroundResource(helper.getInitTalkingCaliResource());
	}
	

}
