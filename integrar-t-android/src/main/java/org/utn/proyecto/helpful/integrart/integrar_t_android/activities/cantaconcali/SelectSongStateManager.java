package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.cantaconcali;


public class SelectSongStateManager implements StateManager {
	private final CantaConCaliActivity activity;
	
	
	public SelectSongStateManager(CantaConCaliActivity activity){
		this.activity = activity;
	}
	
	@Override
	public void processNo() {
		activity.selectSong();
	}

	@Override
	public void processYes() {
		activity.playCurrentSong();
	}

}
