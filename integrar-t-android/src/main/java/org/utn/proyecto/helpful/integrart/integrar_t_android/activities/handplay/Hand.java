package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.handplay;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Hand {
	public final static int RIGHT_HAND = 0;
	public final static int LEFT_HAND = 1;
	public final static int TOP_BOTTOM_POSITION = 0;
	public final static int BOTTOM_TOP_POSITION = 1;
	public final static int LEFT_RIGHT_POSITION = 2;
	public final static int RIGHT_LEFT_POSITION = 3;
	
	private final static Map<FingerNames,Map<FingerNames, float[]>> ranges= new HashMap<FingerNames,Map<FingerNames, float[]>>();
	{
		Map<FingerNames, float[]> pulgarRanges = new HashMap<FingerNames, float[]>();
		pulgarRanges.put(FingerNames.INDICE, new float[]{-.3f, .26f, -1.33f, -.07f});
		pulgarRanges.put(FingerNames.MAYOR, new float[]{-.18f, .33f, -1.37f, 0f});
		pulgarRanges.put(FingerNames.ANULAR, new float[]{-.11f, .67f, -1.3f, .11f});
		pulgarRanges.put(FingerNames.MENIQUE, new float[]{.3f, 1f, .96f, -.22f});

		Map<FingerNames, float[]> indiceRanges = new HashMap<FingerNames, float[]>();
		indiceRanges.put(FingerNames.MAYOR, new float[]{.03f, .44f, -.48f, .48f});
		indiceRanges.put(FingerNames.ANULAR, new float[]{.06f, .66f, -.48f, .37f});
		indiceRanges.put(FingerNames.MENIQUE, new float[]{.22f, 78f, -.33f, .33f});

		Map<FingerNames, float[]> mayorRanges = new HashMap<FingerNames, float[]>();
		mayorRanges.put(FingerNames.ANULAR, new float[]{.03f, .26f, -.26f, .63f});
		mayorRanges.put(FingerNames.MENIQUE, new float[]{.22f, .6f, -.11f, .63f});

		Map<FingerNames, float[]> anularRanges = new HashMap<FingerNames, float[]>();
		anularRanges.put(FingerNames.MENIQUE, new float[]{.07f, .44f, -.15f, .48f});
		
		
		ranges.put(FingerNames.PULGAR, pulgarRanges);
		ranges.put(FingerNames.INDICE, indiceRanges);
		ranges.put(FingerNames.MAYOR, mayorRanges);
		ranges.put(FingerNames.ANULAR, anularRanges);
	}

	private final Map<FingerNames, Finger> fingers;
	private final int hand;
	private final int position;
	private float minX;
	private float minY;
	private float maxX;
	private float maxY;
	
	public Hand(FingerPoint[] fingerPoints){
		this.fingers = buildFingers(fingerPoints);
		this.position = calculatePosition();
		this.hand = calculateHand();
		calculateArea(fingerPoints);
	}
	
	private int calculatePosition() {
		Finger pulgar = fingers.get(FingerNames.PULGAR);
		Finger indice = fingers.get(FingerNames.INDICE);
		if(Math.abs(pulgar.getX() - indice.getX()) < Math.abs(pulgar.getY() - indice.getY())){
			return (pulgar.getY() > indice.getY())
				?BOTTOM_TOP_POSITION
				:TOP_BOTTOM_POSITION;
		}
			return (pulgar.getX() > indice.getX())
				?RIGHT_LEFT_POSITION
				:LEFT_RIGHT_POSITION;
			
	}

	private int calculateHand() {
		Finger pulgar = fingers.get(FingerNames.PULGAR);
		Finger menique = fingers.get(FingerNames.MENIQUE);
		switch(position){
		case BOTTOM_TOP_POSITION:
			return pulgar.getX() < menique.getX() ? RIGHT_HAND : LEFT_HAND;
		case TOP_BOTTOM_POSITION:
			return pulgar.getX() < menique.getX() ? LEFT_HAND : RIGHT_HAND;
		case LEFT_RIGHT_POSITION:
			return pulgar.getY() < menique.getY() ? RIGHT_HAND : LEFT_HAND;
		case RIGHT_LEFT_POSITION:
			return pulgar.getY() < menique.getY() ? LEFT_HAND : RIGHT_HAND;
		}
		return 0;
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
	
	public int getHand(){
		return hand;
	}
	
	public int getPosition(){
		return position;
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
	
	public float[] getArea(Finger finger, Finger by){
		float[] localRanges = ranges.get(by.getName()).get(finger.getName());
		if(position == BOTTOM_TOP_POSITION && hand == RIGHT_HAND)
			return getNormalArea(localRanges, by.getPoints());
		if(position == BOTTOM_TOP_POSITION && hand == LEFT_HAND)
			return getInverseXArea(localRanges, by.getPoints());
		if(position == TOP_BOTTOM_POSITION && hand == RIGHT_HAND)
			return getInverseArea(localRanges, by.getPoints());
		if(position == TOP_BOTTOM_POSITION && hand == LEFT_HAND)
			return getInverseYArea(localRanges, by.getPoints());
		if(position == LEFT_RIGHT_POSITION && hand == RIGHT_HAND)
				return getTranspuestaArea(localRanges, by.getPoints());
		if(position == LEFT_RIGHT_POSITION && hand == LEFT_HAND)
			return getTranspuestaXArea(localRanges, by.getPoints());
		if(position == RIGHT_LEFT_POSITION && hand == RIGHT_HAND)
			return getTranspuestaYArea(localRanges, by.getPoints());
		if(position == RIGHT_LEFT_POSITION && hand == LEFT_HAND)
				return getAllChangeArea(localRanges, by.getPoints());
		return null;
	}
	
	private float[] getNormalArea(float[] ranges, FingerPoint point, float minX, float minY, float maxX, float maxY){
		float[] res = new float[4];
		res[0] = Math.max(point.getX() + (maxX - minX)*ranges[0], minX);
		res[1] = Math.max(point.getY() + (maxY - minY)*ranges[2], minY);
		res[2] = Math.min(point.getX() + (maxX - minX)*ranges[1], maxX);
		res[3] = Math.min(point.getY() + (maxY - minY)*ranges[3], maxY);
//		res[0] = point.getX() + (maxX - minX)*ranges[0];
//		res[1] = point.getY() + (maxY - minY)*ranges[2];
//		res[2] = point.getX() + (maxX - minX)*ranges[1];
//		res[3] = point.getY() + (maxY - minY)*ranges[3];
		return res;
	}
	
	private float[] getNormalArea(float[] ranges, FingerPoint point){
		return getNormalArea(ranges, point, minX, minY, maxX, maxY);
	}
	
	private float[] getInverseYArea(float[] ranges, FingerPoint point){
		float[] buff = new float[]{ranges[0],ranges[1],ranges[2]*-1,ranges[3]*-1};
		return getNormalArea(buff, point, minX, maxY, maxX, minY);
	}

	private float[] getInverseXArea(float[] ranges, FingerPoint point){
		float[] buff = new float[]{ranges[0]*-1,ranges[1]*-1,ranges[2],ranges[3]};
		return getNormalArea(buff, point, maxX, minY, minX, maxY);
	}

	private float[] getInverseArea(float[] ranges, FingerPoint point){
		float[] buff = new float[]{ranges[0]*-1,ranges[1]*-1,ranges[2]*-1,ranges[3]*-1};
		return getNormalArea(buff, point, maxX, maxY, minX, minY);
	}
	
	private float[] getTranspuestaArea(float[] ranges, FingerPoint point){
		float[] buff = new float[]{ranges[2],ranges[3],ranges[0],ranges[1]};
		return getNormalArea(buff, point, minY, minX, maxY, maxX);
	}

	private float[] getTranspuestaXArea(float[] ranges, FingerPoint point){
		float[] buff = new float[]{ranges[2],ranges[3],ranges[0],ranges[1]};
		return getNormalArea(buff, point, maxY, minX, minY, maxX);
	}

	private float[] getTranspuestaYArea(float[] ranges, FingerPoint point){
		float[] buff = new float[]{ranges[2],ranges[3],ranges[0],ranges[1]};
		return getNormalArea(buff, point, maxY, maxX, minY, minX);
	}

	private float[] getAllChangeArea(float[] ranges, FingerPoint point){
		float[] buff = new float[]{ranges[2]*-1,ranges[3]*-1,ranges[0]*-1,ranges[1]*-1};
		return getNormalArea(buff, point, maxY, maxX, minY, minX);
	}
	
}
