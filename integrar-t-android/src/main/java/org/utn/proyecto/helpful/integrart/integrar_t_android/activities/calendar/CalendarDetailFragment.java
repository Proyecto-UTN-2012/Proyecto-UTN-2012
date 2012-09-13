package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar;

import java.util.Calendar;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ZoomControls;

public class CalendarDetailFragment extends Fragment {
	private Calendar date;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		final ViewGroup view = (ViewGroup) inflater.inflate(R.layout.calendar_detail_fragment, container, false);
		ZoomControls zoom = (ZoomControls)view.findViewById(R.id.zoom);
		final ViewGroup grid = (ViewGroup) view.findViewById(R.id.grid);
		final ListView list = (ListView) view.findViewById(R.id.list);
		new ZoomHelper(grid, zoom);
		ListAdapter adapter = new HoursAdapter(getActivity());
		list.setAdapter(adapter);
		return view;
	}
	
	public void setDate(Calendar date){
		this.date = date;
	}
	
	private class HoursAdapter extends BaseAdapter{
		private Context context;
		public HoursAdapter(Context context){
			this.context = context;
		}
		@Override
		public int getCount() {
			return 24;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {			
			if(convertView==null){
				LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = (CalendarHourView) inflater.inflate(R.layout.calendar_hour_item, null);
			}
			((CalendarHourView)convertView).setHour(position);
			return convertView;
		}
		
	}
	
	private class ZoomHelper{
		private final static float SCALE_FACTOR = .25f;
		
		private final View view;

		private int initialViewWidth;
		private int initialViewHeight;
		private float zoomLevel = 1f;
		public ZoomHelper(View theView, ZoomControls controls){
			this.view = theView;
			controls.setOnZoomInClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(initialViewWidth==0){
						initialViewWidth = view.getWidth();
						initialViewHeight = view.getHeight();
					}
					changeZoom(SCALE_FACTOR);
				}
			});
			controls.setOnZoomOutClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(initialViewWidth==0){
						initialViewWidth = view.getWidth();
						initialViewHeight = view.getHeight();
					}
					changeZoom(-SCALE_FACTOR);
				}
			});
		}
		
		private void changeZoom(float factor){
			zoomLevel+=factor;
			if(zoomLevel<=0) zoomLevel = 0.25f;
			view.setScaleX(zoomLevel);
			view.setScaleY(zoomLevel);
			view.setPivotX(0f);
			view.setPivotY(0f);
			view.setLayoutParams(new LinearLayout.LayoutParams((int)(initialViewWidth/zoomLevel), (int)(initialViewHeight/zoomLevel)));
		}
	}
}
