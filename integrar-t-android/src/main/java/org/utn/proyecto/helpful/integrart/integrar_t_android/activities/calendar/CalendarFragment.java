package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;
import org.utn.proyecto.helpful.integrart.integrar_t_android.utils.CalendarView;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CalendarFragment extends Fragment {
	private CalendarView.OnSelectDateListener listener;
	private CalendarView calendar;
	
	public void setOnSelectDateListener(CalendarView.OnSelectDateListener listener){
		this.listener = listener;
		if(calendar!=null) calendar.setOnSelectDateListener(this.listener);
	}
	
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calendar_fragment, container, false);
        calendar = (CalendarView) view.findViewById(R.id.calendar);
        calendar.setOnSelectDateListener(this.listener);
        return view;
    }
}
