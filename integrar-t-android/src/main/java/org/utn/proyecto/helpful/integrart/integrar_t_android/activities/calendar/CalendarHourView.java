package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

public class CalendarHourView extends FrameLayout {
	private ViewGroup title;
	
	private int hour;
	private float itemSize;
	private Drawable titleBackground;
	private int titleColor;
	private float titleSize;

	public CalendarHourView(Context context) {
		super(context);
		init();
	}
	public CalendarHourView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setAttributes(attrs);
		init();
	}
	public CalendarHourView(Context context, AttributeSet attrs, int hour) {
		super(context, attrs);
		setAttributes(attrs);
		this.hour = hour;
		init();
	}
	
	private void setAttributes(AttributeSet attrs){
		TypedArray ta = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.CalendarHourIntegrarT,0,0);
		hour = ta.getInt(R.styleable.CalendarHourIntegrarT_hour,0);
		itemSize = ta.getDimension(R.styleable.CalendarHourIntegrarT_itemSize,50);
		titleBackground = ta.getDrawable(R.styleable.CalendarHourIntegrarT_titleBackground);
		titleColor = ta.getColor(R.styleable.CalendarHourIntegrarT_titleColor, 0xff000000);
		titleSize = ta.getDimension(R.styleable.CalendarHourIntegrarT_titleSize, 14);
	}
	
	private void init(){
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.calendar_hour, null);
		title = (ViewGroup) view.findViewById(R.id.title);
		setTitle();
		this.addView(view);
	}
	
	private void setTitle(){
		title.removeAllViews();
		for(int min=0;min<60;min+=5){
			TextView minTitle = new TextView(getContext());
			String text = (hour<10)? "0" + hour : ""+hour;
			text+=":";
			text+= (min<10)? "0"+min : ""+min;
			minTitle.setText(text);
			minTitle.setLayoutParams(new LayoutParams((int)itemSize, android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
			minTitle.setBackgroundDrawable(titleBackground);
			minTitle.setTextColor(titleColor);
			minTitle.setTextSize(titleSize);
			minTitle.setGravity(Gravity.CENTER);
			title.addView(minTitle);			
		}
	}
	
	public void setHour(int hour){
		this.hour = hour;
		setTitle();
	}

}
