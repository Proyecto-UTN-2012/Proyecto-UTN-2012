package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.cuentos;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.c_main)
public class CurrentCuentoActivity extends RoboActivity {
	@InjectView(R.id.cuentosViewPager)
	private ViewPager cuentosPager;


	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		Bundle arguments = getIntent().getExtras();
		int cuento = arguments.getInt("cuento");
		Toast.makeText(this, "cuento elegido "+cuento, Toast.LENGTH_LONG).show();
		
	}
}
