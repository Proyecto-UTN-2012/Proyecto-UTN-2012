package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.comosehace;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import edu.dhbw.andar.ARToolkit;
import edu.dhbw.andar.AndARActivity;
import edu.dhbw.andar.exceptions.AndARException;


public class ComoSeHaceAndarActivity extends AndARActivity {

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
	    getMenuInflater().inflate(R.menu.csh_activity_main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    ComoSeHaceCustomAndarObject someObject;
	ARToolkit artoolkit;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		ComoSeHaceAndarRenderer renderer = new ComoSeHaceAndarRenderer();//optional, may be set to null
		super.setNonARRenderer(renderer);//or might be omited
		try {
			//register a object for each marker type
			artoolkit = super.getArtoolkit();
			someObject = new ComoSeHaceCustomAndarObject
				("test", "patt.hiro", 80.0, new double[]{0,0});
			artoolkit.registerARObject(someObject);
			someObject = new ComoSeHaceCustomAndarObject
			("test", "android.patt", 80.0, new double[]{0,0});
			artoolkit.registerARObject(someObject);
			someObject = new ComoSeHaceCustomAndarObject
			("test", "barcode.patt", 80.0, new double[]{0,0});
			artoolkit.registerARObject(someObject);
		} catch (AndARException ex){
			//handle the exception, that means: show the user what happened
			System.out.println("");
		}		
		startPreview();
	}

	/**
	 * Inform the user about exceptions that occurred in background threads.
	 * This exception is rather severe and can not be recovered from.
	 * TODO Inform the user and shut down the application.
	 */
	public void uncaughtException(Thread thread, Throwable ex) {
		Log.e("AndAR EXCEPTION", ex.getMessage());
		finish();
	}
	
	
	
}
