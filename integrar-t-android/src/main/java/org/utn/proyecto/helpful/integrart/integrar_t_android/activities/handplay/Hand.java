package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.handplay;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hand {
	private final Map<FingerNames, Finger> fingers;
	private float minX;
	private float minY;
	private float maxX;
	private float maxY;
	public Hand(FingerPoint[] fingerPoints){
		this.fingers = buildFingers(fingerPoints);
		calculateArea(fingerPoints);
	}
	
	private void calculateArea(FingerPoint[] fingerPoints) {
		minX = Float.MAX_VALUE;
		maxX = 0f;
		minY = Float.MAX_VALUE;
		maxY = 0f;
		for(FingerPoint point : fingerPoints){
			if(minX > point.getX()) minX = point.getX();
			if(minY > point.getY()) minY = point.getY();
			if(maxX < point.getX()) maxX = point.getX();
			if(maxY < point.getY()) maxY = point.getY();
		}
	}

	private Map<FingerNames, Finger> buildFingers(FingerPoint[] fingerPoints) {
		FingerPoint average = getAverage(fingerPoints);
		int maxXIndex = getMaxXIndex(fingerPoints, average);
		int maxYIndex = getMaxYIndex(fingerPoints, average);
		FingerPoint rangeX = getRange(fingerPoints[maxXIndex], maxXIndex, maxYIndex, fingerPoints);
		FingerPoint rangeY = getRange(fingerPoints[maxYIndex], maxXIndex, maxYIndex, fingerPoints);
		int maxIndex = getMaxIndex(maxXIndex, maxYIndex, rangeX, rangeY);
		return createFingers(maxIndex, fingerPoints);
	}
	
	private Map<FingerNames, Finger> createFingers(final int maxIndex,
			final FingerPoint[] fingerPoints) {
		List<FingerPoint> points = Arrays.asList(fingerPoints);
		Collections.sort(points, new Comparator<FingerPoint>() {
			@Override
			public int compare(FingerPoint object1, FingerPoint object2) {
				float d1 = fingerPoints[maxIndex].range(object1);
				float d2 = fingerPoints[maxIndex].range(object2);
				return (int)(d1 - d2);
			}
		});
		Map<FingerNames, Finger> map = new HashMap<FingerNames, Finger>();
		map.put(FingerNames.PULGAR, new Finger(FingerNames.PULGAR, points.get(0)));
		map.put(FingerNames.INDICE, new Finger(FingerNames.INDICE, points.get(1)));
		map.put(FingerNames.MAYOR, new Finger(FingerNames.MAYOR, points.get(2)));
		map.put(FingerNames.ANULAR, new Finger(FingerNames.ANULAR, points.get(3)));
		map.put(FingerNames.MENIQUE, new Finger(FingerNames.MENIQUE, points.get(4)));
		return map;
	}

	private int getMaxIndex(int maxXIndex, int maxYIndex, FingerPoint rangeX,
			FingerPoint rangeY) {
		return (rangeX.getX() > rangeY.getY())? maxXIndex : maxYIndex; 
	}

	private FingerPoint getRange(FingerPoint fingerPoint, int maxXIndex,
			int maxYIndex, FingerPoint[] fingerPoints) {
		float x = 0;
		float y = 0;
		int count = 0;
		for(int i=0;i<fingerPoints.length;i++){
			if(i!= maxXIndex && i!= maxYIndex){
				count++;
				x+= Math.abs(fingerPoint.getX() - fingerPoints[i].getX());
				y+= Math.abs(fingerPoint.getY() - fingerPoints[i].getY());
			}
		}
		x/=count;
		y/=count;
		return new FingerPoint(x, y);
	}

	private int getMaxYIndex(FingerPoint[] fingerPoints, FingerPoint average) {
		float max = 0f;
		int maxIndex = 0;
		for(int i=0;i<fingerPoints.length;i++){
			float distance = Math.abs(fingerPoints[i].getY()-average.getY());
			if(distance > max){
				max = distance;
				maxIndex = i;
			}
		}
		return maxIndex;
	}

	private int getMaxXIndex(FingerPoint[] fingerPoints, FingerPoint average) {
		float max = 0f;
		int maxIndex = 0;
		for(int i=0;i<fingerPoints.length;i++){
			float distance = Math.abs(fingerPoints[i].getX()-average.getX());
			if(distance > max){
				max = distance;
				maxIndex = i;
			}
		}
		return maxIndex;
	}

	private FingerPoint getAverage(FingerPoint[] fingerPoints) {
		float x = 0;
		float y = 0;
		for(FingerPoint point : fingerPoints){
			x+= point.getX();
			y+= point.getY();
		}
		x/=5;
		y/=5;
		return new FingerPoint(x, y);
	}
	
	public Finger getFinger(FingerNames name){
		return fingers.get(name);
	}
	
	/**
	 * 
	 * @return [left, top, rigth, bottom]
	 */
	public float[] getArea(){
		return new float[]{minX, minY, maxX, maxY};
	}
}
