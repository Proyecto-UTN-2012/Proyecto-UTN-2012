package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.cantaconcali;

import java.util.List;

import org.utn.proyecto.helpful.integrart.integrar_t_android.utils.SixItemPaginatorAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class CancionPageAdapter extends SixItemPaginatorAdapter<Cancion> {
		public CancionPageAdapter(FragmentManager fm, List<Cancion> canciones) {
		super(fm , canciones);
	}
	

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}


}
