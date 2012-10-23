package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.testactivity;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;

import roboguice.activity.RoboFragmentActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.widget.ListView;

@ContentView(R.layout.test_activity)
public class TestActivity extends RoboFragmentActivity{
		
	 @Override
	 public void onCreate(Bundle savedInstanceState) {
		 super.onCreate(savedInstanceState);
		 //list.setAdapter(new BookPagerAdapter(this));
	 }
}
