package org.utn.proyecto.helpful.integrart.integrar_t_android.utils;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;
import org.utn.proyecto.helpful.integrart.integrar_t_android.domain.User;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.DataStorageService;

import roboguice.inject.ContextSingleton;
import android.util.SparseIntArray;

import com.google.inject.Inject;

@ContextSingleton
public class CaliHelper {
	private static final SparseIntArray initTalking = new SparseIntArray(6);
	private static final SparseIntArray talking = new SparseIntArray(6);
	private static final SparseIntArray initGreeting = new SparseIntArray(6);
	private static final SparseIntArray greeting = new SparseIntArray(6);
	private static final SparseIntArray dancing = new SparseIntArray(6);
	
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

		initGreeting.put(R.drawable.cali1, R.drawable.cali_sip);
		initGreeting.put(R.drawable.cali2, R.drawable.cali_sir);
		initGreeting.put(R.drawable.cali3, R.drawable.cali_sib);
		initGreeting.put(R.drawable.cali4, R.drawable.cali_sig);
		initGreeting.put(R.drawable.cali5, R.drawable.cali_sio);
		initGreeting.put(R.drawable.cali6, R.drawable.cali_silb);

		greeting.put(R.drawable.cali1, R.drawable.cali_sp);
		greeting.put(R.drawable.cali2, R.drawable.cali_sr);
		greeting.put(R.drawable.cali3, R.drawable.cali_sb);
		greeting.put(R.drawable.cali4, R.drawable.cali_sg);
		greeting.put(R.drawable.cali5, R.drawable.cali_so);
		greeting.put(R.drawable.cali6, R.drawable.cali_slb);

		dancing.put(R.drawable.cali1, R.drawable.cali_bp);
		dancing.put(R.drawable.cali2, R.drawable.cali_br);
		dancing.put(R.drawable.cali3, R.drawable.cali_bb);
		dancing.put(R.drawable.cali4, R.drawable.cali_bg);
		dancing.put(R.drawable.cali5, R.drawable.cali_bo);
		dancing.put(R.drawable.cali6, R.drawable.cali_blb);
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

	public int getInitGreetingCaliResource(int caliResource){ 
		return initGreeting.get(caliResource);
	}
	
	public int getInitGreetingCaliResource(){
		int caliResource = db.get(user.getUserName() + ".cac_personaje_seleccionado", Integer.class);
		return getInitGreetingCaliResource(caliResource);
	}
	
	public int getGreetingCaliResource(int caliResource){ 
		return greeting.get(caliResource);
	}
	
	public int getGreetingCaliResource(){
		int caliResource = db.get(user.getUserName() + ".cac_personaje_seleccionado", Integer.class);
		return getGreetingCaliResource(caliResource);
	}
	
	public int getDancingCaliResource(int caliResource){ 
		return dancing.get(caliResource);
	}
	
	public int getDancingCaliResource(){
		int caliResource = db.get(user.getUserName() + ".cac_personaje_seleccionado", Integer.class);
		return getDancingCaliResource(caliResource);
	}
}
