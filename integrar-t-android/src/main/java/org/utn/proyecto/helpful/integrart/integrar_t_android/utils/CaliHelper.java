package org.utn.proyecto.helpful.integrart.integrar_t_android.utils;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;
import org.utn.proyecto.helpful.integrart.integrar_t_android.domain.User;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.DataStorageService;

import com.google.inject.Inject;

import roboguice.inject.ContextSingleton;
import android.util.SparseIntArray;

@ContextSingleton
public class CaliHelper {
	private static final SparseIntArray initTalking = new SparseIntArray(6);
	private static final SparseIntArray talking = new SparseIntArray(6);
	
	{
		initTalking.put(R.drawable.cali1, R.drawable.cali_hip);
		initTalking.put(R.drawable.cali2, R.drawable.cali_hir);
		initTalking.put(R.drawable.cali3, R.drawable.cali_hib);
		initTalking.put(R.drawable.cali4, R.drawable.cali_hig);
		initTalking.put(R.drawable.cali5, R.drawable.cali_hio);
		initTalking.put(R.drawable.cali6, R.drawable.cali_hilb);

		talking.put(R.drawable.cali1, R.drawable.cali_hp);
		talking.put(R.drawable.cali2, R.drawable.cali_hr);
		talking.put(R.drawable.cali3, R.drawable.cali_hb);
		talking.put(R.drawable.cali4, R.drawable.cali_hg);
		talking.put(R.drawable.cali5, R.drawable.cali_ho);
		talking.put(R.drawable.cali6, R.drawable.cali_hlb);
	}
	
	private final User user;
	private final DataStorageService db;
	
	@Inject
	public CaliHelper(DataStorageService db, User user){
		this.user = user;
		this.db = db;
	}
	
	public int getInitTalkingCaliResource(int caliResource){ 
		return initTalking.get(caliResource);
	}
	
	public int getInitTalkingCaliResource(){
		int caliResource = db.get(user.getUserName() + ".cac_personaje_seleccionado", Integer.class);
		return getInitTalkingCaliResource(caliResource);
	}

	public int getTalkingCaliResource(int caliResource){ 
		return talking.get(caliResource);
	}
	
	public int getTalkingCaliResource(){
		int caliResource = db.get(user.getUserName() + ".cac_personaje_seleccionado", Integer.class);
		return getTalkingCaliResource(caliResource);
	}
}
