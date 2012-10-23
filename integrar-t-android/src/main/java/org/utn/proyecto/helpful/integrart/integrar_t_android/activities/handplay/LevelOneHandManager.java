package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.handplay;


public class LevelOneHandManager extends AbstractLevelHandManager {
	private HandManager next;
	public LevelOneHandManager(FingerPoint[] fingerPoints, HandPlayActivity activity){
		super(fingerPoints, activity, 1);
	}

	private FingerPoint getRandomPoint() {
		float[] area = hand.getArea();
		float x = randomBetween(area[0], area[2]);
		float y = randomBetween(area[1], area[3]);
		return new FingerPoint(x, y);
	}

	@Override
	public HandManager getManager() {
		if(next!=null) return next;
		return this;
	}

	@Override
	protected FingerPoint[] getRandomPoints() {
		return new FingerPoint[]{getRandomPoint()};
	}

	@Override
	protected int getFingerCount() {
		return 2;
	}

	@Override
	public void setLevel(int level) {
		if(level!=1) next = new LevelXHandManager(origin, activity, level);
		activity.setManager(next);
	}
}
