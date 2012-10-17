package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.handplay;

import android.view.MotionEvent;
import android.view.View;

public class LevelOneHandManager implements HandManager {
	private final Hand hand;
	public LevelOneHandManager(FingerPoint[] fingerPoints){
		this.hand = new Hand(fingerPoints);
	}

	@Override
	public boolean onTouch(View view, MotionEvent event) {
		return false;
	}

	@Override
	public HandManager getManager() {
		return this;
	}

}
