package org.utn.proyecto.helpful.integrart.integrar_t_android.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CalendarView extends FrameLayout implements OnItemClickListener {
	private RelativeLayout header;
	private ImageView previousButton;
	private ImageView nextButton;
	private TextView monthText;
	private GridView grid;
	
	private Calendar month;
	
	private int beforeMonthColor;
	private int currentMonthColor;
	private int afterMonthColor;
	
	private Drawable headerBackground;
	private Drawable beforeMonthBackground;
	private Drawable currentMonthBackground;
	private Drawable afterMonthBackground;
	
	private Drawable previousImage;
	private Drawable nextImage;
	
	private int headerColor;
	private int headerSize;
	
	private int lineColor;
	
	private Drawable selectedBackground;
	private int selectedColor;
	
	private CalendarAdapter adapter;
	
	private OnSelectDateListener listener;
	
	public CalendarView(Context context) {
		super(context);
		init();
	}
	
	public CalendarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setAttributes(attrs);
		init();
	}
	
	public CalendarView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setAttributes(attrs);
		init();
	}
	
	private void setAttributes(AttributeSet attrs){
		TypedArray ta = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.CalendarViewIntegrarT,0,0);
		beforeMonthColor = ta.getColor(R.styleable.CalendarViewIntegrarT_beforeMonthColor,0xff000000);
		currentMonthColor = ta.getColor(R.styleable.CalendarViewIntegrarT_currentMonthColor,0xff000000);
		afterMonthColor = ta.getColor(R.styleable.CalendarViewIntegrarT_afterMonthColor,0xff000000);
		
		headerBackground = ta.getDrawable(R.styleable.CalendarViewIntegrarT_headerBackground);
		beforeMonthBackground = ta.getDrawable(R.styleable.CalendarViewIntegrarT_beforeMonthBackground);
		currentMonthBackground = ta.getDrawable(R.styleable.CalendarViewIntegrarT_currentMonthBackground);
		afterMonthBackground = ta.getDrawable(R.styleable.CalendarViewIntegrarT_afterMonthBackground);
		
		previousImage = ta.getDrawable(R.styleable.CalendarViewIntegrarT_previousButtonImage);
		nextImage = ta.getDrawable(R.styleable.CalendarViewIntegrarT_nextButtonImage);
		
		headerColor = ta.getColor(R.styleable.CalendarViewIntegrarT_headerColor,0xff000000);
		headerSize = ta.getDimensionPixelSize(R.styleable.CalendarViewIntegrarT_headerSize, 15);
		
		lineColor = ta.getColor(R.styleable.CalendarViewIntegrarT_lineColor, 0xff000000);
		
		selectedBackground = ta.getDrawable(R.styleable.CalendarViewIntegrarT_selectedBackground);
		selectedColor = ta.getColor(R.styleable.CalendarViewIntegrarT_selectedColor, 0xff000000);
	}
	
	private void init(){
		month = Calendar.getInstance();
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.calendar_view, null);
	
		header = (RelativeLayout) view.findViewById(R.id.header);
		header.setBackgroundDrawable(headerBackground);
		
		previousButton = (ImageView)view.findViewById(R.id.previous);
		previousButton.setImageDrawable(previousImage);
		previousButton.setOnClickListener(new OnPreviousMonthClick());
		
		nextButton = (ImageView)view.findViewById(R.id.next);
		nextButton.setImageDrawable(nextImage);
		nextButton.setOnClickListener(new OnNextMonthClick());
		
		monthText = (TextView)view.findViewById(R.id.title);
		monthText.setText(DateFormat.format("MMMM yyyy", month));
		monthText.setTextColor(headerColor);
		monthText.setTextSize(headerSize);
		
		grid = (GridView)view.findViewById(R.id.gridView);
		grid.setBackgroundColor(lineColor);
		grid.setOnItemClickListener(this);
		adapter = new CalendarAdapter(getContext(), month);
		grid.setAdapter(adapter);
		this.addView(view);
	}
	
	public void setOnSelectDateListener(CalendarView.OnSelectDateListener listener){
		this.listener = listener;
	}
	
	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
		Calendar date = (Calendar) adapter.getItemAtPosition(position);
		this.adapter.setSelectedDate(position);
		if(listener!=null)
			listener.onSelectDate(date);
	}
	
	private void onPreviousMonth(){
		month.set(Calendar.MONTH, month.get(Calendar.MONTH)-1);
		adapter = new CalendarAdapter(getContext(), month);
		grid.setAdapter(adapter);
		
		monthText.setText(DateFormat.format("MMMM yyyy", month));
	}
	
	private void onNextMonth(){
		month.set(Calendar.MONTH, month.get(Calendar.MONTH)+1);
		adapter = new CalendarAdapter(getContext(), month);
		grid.setAdapter(adapter);
		
		monthText.setText(DateFormat.format("MMMM yyyy", month));
	}
	
	private class OnPreviousMonthClick implements View.OnClickListener{
		@Override
		public void onClick(View v) {
			onPreviousMonth();
		}
	}
	
	private class OnNextMonthClick implements View.OnClickListener{
		@Override
		public void onClick(View v) {
			onNextMonth();
			
		}
		
	}
	
	private class CalendarAdapter extends BaseAdapter{
		private static final int FIRST_DAY_OF_WEEK = 0;
		
		private Context mContext;
		
		private Calendar month;
	    private List<Calendar> items;
	    private View[] views;
	    
	    public Calendar[] days;
	    
	    public CalendarAdapter(Context c, Calendar monthCalendar){
	    	this.mContext = c;
	    	month = monthCalendar;
	    	month.set(Calendar.DAY_OF_MONTH, 1);
	        this.items = new ArrayList<Calendar>();
	        refreshDays();
	        views = new View[days.length];
	    }
	    
	    public void setSelectedDate(int position){
	    	for(int i=0;i<views.length;i++){
	    		if(i==position){
	    			setAsSelectedDate(position);
	    		}
	    		else{
	    			setAsNormalDate(i);
	    		}
	    	}
	    }
	    
	    private void setAsSelectedDate(int position){
	    	views[position].setBackgroundDrawable(selectedBackground);
	    	TextView text = (TextView) views[position].findViewById(R.id.date);
	    	text.setTextColor(selectedColor);
	    }
	    
	    private void setAsNormalDate(int position){
	    	Calendar date = days[position];
	    	View view = views[position];
	    	TextView dayView = (TextView) view.findViewById(R.id.date);
	    	int dayMonth = date.get(Calendar.MONTH);
		    int currentMonth = month.get(Calendar.MONTH);
		    if(dayMonth<currentMonth || (currentMonth==Calendar.JANUARY && dayMonth==Calendar.DECEMBER)){
		    	dayView.setTextColor(beforeMonthColor);
		    	view.setBackgroundDrawable(beforeMonthBackground);
		    }
		    else if((currentMonth==Calendar.DECEMBER && dayMonth==Calendar.JANUARY) || (dayMonth>currentMonth)){
		    	dayView.setTextColor(afterMonthColor);
		    	view.setBackgroundDrawable(afterMonthBackground);
		    }
		    else{
		    	dayView.setTextColor(currentMonthColor);
		    	view.setBackgroundDrawable(currentMonthBackground);		    	
		    }
	    }
		
		@Override
		public int getCount() {
			return days.length;
		}

		@Override
		public Object getItem(int position) {
			return days[position];
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = convertView;
		    TextView dayView;
		    if(convertView == null) { // if it's not recycled, initialize some attributes
		    	LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		        view = inflater.inflate(R.layout.calendar_item, null);
		          
		    }
		    dayView = (TextView)view.findViewById(R.id.date);
		    
		    
		    views[position] = view;    
		    int day = days[position].get(Calendar.DAY_OF_MONTH);
		    setAsNormalDate(position);
		    	
		    dayView.setText(""+day);
		    return view;
		}
		
		public void refreshDays()
	    {
		    // clear items
		    items.clear();
		    Calendar nextMonth = (Calendar) month.clone();
		    int monthSize = month.getActualMaximum(Calendar.DAY_OF_MONTH);
		    int firstDay = month.get(Calendar.DAY_OF_WEEK);
		    nextMonth.set(Calendar.DAY_OF_MONTH, month.get(Calendar.DAY_OF_MONTH)+monthSize);
		   // nextMonth.set(Calendar.DAY_OF_MONTH, 1);
		    int lastDay = nextMonth.get(Calendar.DAY_OF_WEEK);    
		     // figure size of the array
		    int size = monthSize + firstDay + (7 - lastDay);
		    days = new Calendar[size];
		    
	        int j=FIRST_DAY_OF_WEEK;
	        
	        // populate empty days before first real day
	        for(int i=firstDay-1;i>0;i--){
	        	Calendar day = (Calendar) month.clone();
	        	day.set(Calendar.DAY_OF_MONTH, month.get(Calendar.DAY_OF_MONTH)-i);
	        	days[j] = day;
	        	j++;
	        }
	        	
		        
	        // populate days
	        for(int i=0;i<monthSize;i++){
	        	Calendar day = (Calendar) month.clone();
	        	day.set(Calendar.DAY_OF_MONTH, month.get(Calendar.DAY_OF_MONTH)+i);
	        	days[j] = day;
	        	j++;
	        }
	        
	        //populate after last day
	        for(int i=0;j<size;i++){
	        	Calendar day = (Calendar) nextMonth.clone();
	        	day.set(Calendar.DAY_OF_MONTH, nextMonth.get(Calendar.DAY_OF_MONTH)+i);
	        	days[j] = day;
	        	j++;
	        }
	    }

	}
	
	public interface OnSelectDateListener{
		public void onSelectDate(Calendar date);
	}

}
