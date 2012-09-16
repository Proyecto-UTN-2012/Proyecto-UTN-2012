package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar;

import java.util.Calendar;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;
import org.utn.proyecto.helpful.integrart.integrar_t_android.domain.User;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.DataStorageService;

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
	private ZoomControls zoom;
	private ZoomHelper zoomHelper;
	private final DataStorageService db;
	private final User user;
	
	public CalendarDetailFragment(User user, DataStorageService db){
		this.db = db;
		this.user = user;
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		final ViewGroup view = (ViewGroup) inflater.inflate(R.layout.calendar_detail_fragment, container, false);
		zoom = (ZoomControls)view.findViewById(R.id.zoom);
		boolean showZoom = db.get(user.getUserName() + CalendarActivity.SHOW_ZOOM_KEY, Boolean.class);
		final ViewGroup grid = (ViewGroup) view.findViewById(R.id.grid);
		final ListView list = (ListView) view.findViewById(R.id.list);
		zoomHelper = new ZoomHelper(grid, zoom);
		ListAdapter adapter = new HoursAdapter(getActivity());
		list.setAdapter(adapter);
		setZoomVisible(showZoom);
		return view;
	}
	
	public void setZoomVisible(boolean visible){
		zoomHelper.showControls(visible);
		zoom.setVisibility(visible? View.VISIBLE : View.GONE);
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
		private int controlHeight;
		private float zoomLevel = 1f;
		private boolean visible = false;
		private int toAddHeight = 0;
		
		public ZoomHelper(View theView, final ZoomControls controls){
			this.view = theView;
			controlHeight = controls.getHeight();
			controls.setOnZoomInClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(initialViewWidth==0){
						controlHeight = controls.getHeight();
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
		
		public void showControls(boolean show){
			if(controlHeight==0) return;
			visible = show;
			toAddHeight = visible ? 0 : controlHeight;
			view.setLayoutParams(new LinearLayout.LayoutParams((int)(initialViewWidth/zoomLevel), (int)((initialViewHeight + toAddHeight)/zoomLevel)));
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
