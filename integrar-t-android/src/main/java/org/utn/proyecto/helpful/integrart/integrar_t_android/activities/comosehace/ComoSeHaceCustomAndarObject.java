package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.comosehace;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;

import edu.dhbw.andar.ARObject;
import edu.dhbw.andar.ARToolkit;
import edu.dhbw.andar.pub.SimpleBox;
import edu.dhbw.andar.util.GraphicsUtil;

public class ComoSeHaceCustomAndarObject extends ARObject {

    private static final String VIDEO = "video";
	private ComoSeHaceAndarActivity activity;
    private final static String CSH_PREFERENCE = "integrar-t-csh";
    
	//Matrix positions which contains the X,Y,Z transformed.
	//values in matrix[X or Y or Z] are the points from the center to the marker.
	private int XCordinate = 3;
	private int YCordinate = 7;
	private int ZCordinate = 11;
    

    public ComoSeHaceCustomAndarObject(String name, String patternName,
			double markerWidth, double[] markerCenter,ComoSeHaceAndarActivity comoSeHaceAndarActivity) {
		super(name, patternName, markerWidth, markerCenter);
		float   mat_ambientf[]     = {0f, 1.0f, 0f, 1.0f};
		float   mat_flashf[]       = {0f, 1.0f, 0f, 1.0f};
		float   mat_diffusef[]       = {0f, 1.0f, 0f, 1.0f};
		float   mat_flash_shinyf[] = {50.0f};
		
		//activity is passed like parameter when it is instantiated so it can call the execute video 
		activity = comoSeHaceAndarActivity;

		mat_ambient = GraphicsUtil.makeFloatBuffer(mat_ambientf);
		mat_flash = GraphicsUtil.makeFloatBuffer(mat_flashf);
		mat_flash_shiny = GraphicsUtil.makeFloatBuffer(mat_flash_shinyf);
		mat_diffuse = GraphicsUtil.makeFloatBuffer(mat_diffusef);
		
	}
	public ComoSeHaceCustomAndarObject(String name, String patternName,
			double markerWidth, double[] markerCenter, float[] customColor, ComoSeHaceAndarActivity act) {
		super(name, patternName, markerWidth, markerCenter);
		float   mat_flash_shinyf[] = {50.0f};

		//activity is passed like parameter when it is instantiated so it can call the execute video
		activity = act;
		
		mat_ambient = GraphicsUtil.makeFloatBuffer(customColor);
		mat_flash = GraphicsUtil.makeFloatBuffer(customColor);
		mat_flash_shiny = GraphicsUtil.makeFloatBuffer(mat_flash_shinyf);
		mat_diffuse = GraphicsUtil.makeFloatBuffer(customColor);
		
	}
	
	/**
	 * Just a box, imported from the AndAR project.
	 */
	private SimpleBox box = new SimpleBox();
	private FloatBuffer mat_flash;
	private FloatBuffer mat_ambient;
	private FloatBuffer mat_flash_shiny;
	private FloatBuffer mat_diffuse;
	
	/**
	 * Everything drawn here will be drawn directly onto the marker,
	 * as the corresponding translation matrix will already be applied.
	 */
	
	@Override
	public final void draw(GL10 gl) {
	    
	    //get the matrix transformed matrix
	    double[] matrix = super.getTransMatrix();
	    
	    if (markerIsClose(matrix))
	    {
	        
    	    if (isVisible() && super.getPatternName().equals("patt.hiro")){
    	        //executeReproductor("android.resource://" + activity.getPackageName() +"/"+R.raw.assasin);
    	    }
    	    
    	    if (isVisible() && super.getPatternName().equals("android.patt")){
                //executeReproductor("android.resource://" + activity.getPackageName() +"/"+R.raw.lallama);
            }
    	    
    	    if (isVisible() && super.getPatternName().equals("barcode.patt")){
                //executeReproductor("android.resource://" + activity.getPackageName() +"/"+R.raw.llama_caroso);
            }
    	    
	    }
	}
	
	/**
	 * This function calculates if the marker is close enough to the center of the display and a distance set on preferences
	 * @param matrix is the transform matrix [3][4]  (0 = orientation X; 1 = orientation Y; 2 = Orientation Z; 3 = Transformed value in X and so on)
	 * @return  if marker should be analyzed or not.
	 */
    private boolean markerIsClose(double[] matrix) {
        
        double x;
        double y;
        double z;
        //TODO: parameters should be taken from preference  
        double minRadio = 5;
        double maxRadio = 150;
        
      //TODO: parameters should be taken from preference
        double distanceFromCenterX = 50;
        double distanceFromCenterY = 50;
        
        //distance from center to the point where is the marker.
        double distance = 0;
        
        //get preference
        SharedPreferences pref = activity.getSharedPreferences(CSH_PREFERENCE,activity.MODE_PRIVATE);
        minRadio = pref.getFloat(activity.getResources().getString(R.string.csh_distacia_cercana),5);
        maxRadio = pref.getFloat(activity.getResources().getString(R.string.csh_distacia_lejana),150);
        
        //transform CM to MM
        minRadio = minRadio * 10;
        maxRadio = maxRadio * 10;
        
        if (matrix!=null)
        {
            x = matrix[XCordinate];
            y = matrix[YCordinate];
            z = matrix[ZCordinate];
            
            //Pitagoras law
            distance = Math.sqrt(x*x+y*y+z*z);
           
            if (Math.abs(x) > distanceFromCenterX && Math.abs(y) > distanceFromCenterY)
            {
                return false;
            }
        }
        
        if (distance >= minRadio && distance <= maxRadio)
        {
            return true;
       }else{
            return false;
        }
    }
    
    /**
     * Reproduce the video from the parameter assigned.
     * @param path to the video
     */
    protected void executeReproductor(String path) {
        activity.executeReproductor(path);
    }
    
	@Override
	public void init(GL10 gl) {
		
	}
}
