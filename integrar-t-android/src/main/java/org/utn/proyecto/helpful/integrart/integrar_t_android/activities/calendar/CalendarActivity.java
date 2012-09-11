package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar;

import java.util.Calendar;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;
import org.utn.proyecto.helpful.integrart.integrar_t_android.utils.CalendarView.OnSelectDateListener;

import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.ContentView;
import android.content.Intent;
import android.os.Bundle;

@ContentView(R.layout.calendar_activity)
public class CalendarActivity extends RoboFragmentActivity implements OnSelectDateListener{
	
    private boolean mTwoPane;
    private CalendarFragment calendarFragment;
    private CalendarDetailFragment detailFragment;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		calendarFragment = ((CalendarFragment) getSupportFragmentManager()
				.findFragmentById(R.id.calendar));
		calendarFragment.setOnSelectDateListener(this);

		if (findViewById(R.id.calendar_detail_container) != null) {
            mTwoPane = true;
            //Bundle arguments = new Bundle();
            //arguments.putString(ItemDetailFragment.ARG_ITEM_ID, id);
            detailFragment = new CalendarDetailFragment();
            //fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.calendar_detail_container, detailFragment)
                    .commit();
        }
	}

	@Override
	public void onSelectDate(Calendar date) {
		if(mTwoPane){
			detailFragment.setDate(date);
		}
		else{
			Intent detailIntent = new Intent(this, CalendarDetailActivity.class);
            
			detailIntent.putExtra(CalendarDetailActivity.DATE, date);
            startActivity(detailIntent);
		}
		
	}
}
