package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

@SuppressLint({ "ViewConstructor", "ViewConstructor" })
public class EmptyMinuteView extends View implements View.OnClickListener, View.OnTouchListener{
	private int hour;
	private int minute;
	
	private OnSelectMinuteListener listener;
	private OnClickListener onClickListener;
	
	public EmptyMinuteView(Context context, int hour, int minute){
		super(context);
		this.hour = hour;
		this.minute = minute;
		super.setOnClickListener(this);
		this.setOnTouchListener(this);
	}
	
	public void setOnSelectMinuteListener(OnSelectMinuteListener listener){
		this.listener = listener;
	}

	public int getHour() {
		return hour;
	}

	public int getMinute() {
		return minute;
	}
	
	@Override
	public void setOnClickListener(OnClickListener listener){
		this.onClickListener = listener;
	}
	
	
	public interface OnSelectMinuteListener{
		public void onSelectMinute(int hour, int minute);
	}


	@Override
	public void onClick(View v) {
		if(onClickListener!=null){
			onClickListener.onClick(v);
		}
		if(listener!=null){
			listener.onSelectMinute(hour, minute);
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if(event.getAction()==MotionEvent.ACTION_HOVER_ENTER){
			Log.i("EmptyMinuteView", "ENTER ON: " + hour + "h " + minute + "m");
		}
		return false;
	}
}
