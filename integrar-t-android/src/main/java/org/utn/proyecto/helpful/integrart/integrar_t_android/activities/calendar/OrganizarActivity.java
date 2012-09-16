package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar;

import java.util.Calendar;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;
import org.utn.proyecto.helpful.integrart.integrar_t_android.domain.User;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.DataStorageService;

import android.os.Bundle;
import android.text.format.DateFormat;

import com.google.inject.Inject;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;

@ContentView(R.layout.organizar_t)
public class OrganizarActivity extends RoboActivity {
	private final static String ORGANIZAR_T_PACKAGE_KEY = ".calendar.tasks.";
	
	private Calendar date = Calendar.getInstance();
	
	@Inject
	private User user;
	
	@Inject
	private DataStorageService db;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String dateKey = DateFormat.format("yyyy.MMMM.dd", date).toString();
		if(!db.contain(user.getUserName() + ORGANIZAR_T_PACKAGE_KEY + dateKey)){
			showEmptyPanel();
		}
	}
	
	private void showEmptyPanel(){
		
	}
}
