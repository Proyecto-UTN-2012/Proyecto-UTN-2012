package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar;

import java.util.Calendar;

import roboguice.activity.RoboFragmentActivity;
import android.os.Bundle;

public class CalendarDetailActivity extends RoboFragmentActivity {
		public static final String DATE = "date";
		@Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	
	        getActionBar().setDisplayHomeAsUpEnabled(true);
	
	        if (savedInstanceState == null) {
	            Bundle arguments = getIntent().getExtras();
	            CalendarDetailFragment fragment = new CalendarDetailFragment();
	            if(arguments.containsKey(CalendarDetailActivity.DATE))
	            	fragment.setDate((Calendar)arguments.get(DATE));
	            //fragment.setArguments(arguments);
	            getSupportFragmentManager().beginTransaction()
	                    .add(android.R.id.content, fragment)
	                    .commit();
	          
	        }
		}
}
