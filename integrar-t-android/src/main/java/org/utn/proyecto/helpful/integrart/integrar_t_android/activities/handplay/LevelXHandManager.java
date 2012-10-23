package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.handplay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LevelXHandManager extends AbstractLevelHandManager {
	public LevelXHandManager(FingerPoint[] fingerPoints,
			HandPlayActivity activity, int level) {
		super(fingerPoints, activity, level);
	}

	@Override
	public HandManager getManager() {
		return this;
	}

	@Override
	protected FingerPoint[] getRandomPoints() {
		FingerPoint[] points = new FingerPoint[getFingerCount()-1];
		
		List<FingerNames> names = new ArrayList<FingerNames>(Arrays.asList(FingerNames.INDICE, FingerNames.MAYOR, FingerNames.ANULAR, FingerNames.MENIQUE));
		List<Finger> fingers = new ArrayList<Finger>();
		Finger by = hand.getFinger(FingerNames.PULGAR);
		for(int i=1;i<getFingerCount();i++){
			FingerNames name = names.remove(rnd.nextInt(names.size()));
			fingers.add(hand.getFinger(name));
		}
		Collections.sort(fingers);
		for(int i=1;i<getFingerCount();i++){
			Finger finger = fingers.get(i-1);
			float[] area = hand.getArea(finger, by);
			float x = randomBetween(area[0], area[2]);
			float y = randomBetween(area[1], area[3]);
			by = new Finger(finger.getName(), new FingerPoint(x, y));
			points[i-1] = by.getPoints();
		}
		return points;
	}

	@Override
	protected int getFingerCount() {
		return level + 1;
	}

	@Override
	public void setLevel(int level) {
		if(level == 1) activity.setManager(new LevelOneHandManager(origin, activity));
		this.level = level;
	}

}
