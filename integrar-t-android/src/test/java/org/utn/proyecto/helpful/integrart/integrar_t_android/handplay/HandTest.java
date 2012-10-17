package org.utn.proyecto.helpful.integrart.integrar_t_android.handplay;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.handplay.Finger;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.handplay.FingerNames;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.handplay.FingerPoint;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.handplay.Hand;

public class HandTest {
	private Hand hand;
	private Map<FingerNames, Finger> fingers;
	@Before
	public void setUp() throws Exception {
		FingerPoint[] points = new FingerPoint[]{
				new FingerPoint(1039f, 223f),
				new FingerPoint(239f, 560f),
				new FingerPoint(813f, 98f),
				new FingerPoint(436f, 138f),
				new FingerPoint(644f, 54f),
		};
		fingers = new HashMap<FingerNames, Finger>();
		fingers.put(FingerNames.MENIQUE, new Finger(FingerNames.MENIQUE, points[0]));
		fingers.put(FingerNames.PULGAR, new Finger(FingerNames.PULGAR, points[1]));
		fingers.put(FingerNames.ANULAR, new Finger(FingerNames.ANULAR, points[2]));
		fingers.put(FingerNames.INDICE, new Finger(FingerNames.INDICE, points[3]));
		fingers.put(FingerNames.MAYOR, new Finger(FingerNames.MAYOR, points[4]));
		
		hand = new Hand(points);
	}

	@Test
	public void testPulgar() {
		assertEquals(fingers.get(FingerNames.PULGAR), hand.getFinger(FingerNames.PULGAR));
	}
	@Test
	public void testIndice() {
		assertEquals(fingers.get(FingerNames.INDICE), hand.getFinger(FingerNames.INDICE));
	}
	@Test
	public void testMayor() {
		assertEquals(fingers.get(FingerNames.MAYOR), hand.getFinger(FingerNames.MAYOR));
	}
	@Test
	public void testAnular() {
		assertEquals(fingers.get(FingerNames.ANULAR), hand.getFinger(FingerNames.ANULAR));
	}
	@Test
	public void testMenique() {
		assertEquals(fingers.get(FingerNames.MENIQUE), hand.getFinger(FingerNames.MENIQUE));
	}
	
	@Test
	public void testArea() {
		float[] area = hand.getArea();
		float[] expected = new float[]{239f, 54f, 1039f, 560f};
		for(int i=0;i<4;i++){
			assertTrue(expected[i]==area[i]);			
		}
	}

}
