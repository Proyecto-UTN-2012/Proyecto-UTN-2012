package org.utn.proyecto.helpful.integrart.integrar_t_android;

import java.io.InputStream;
import java.util.List;

import org.utn.proyecto.helpful.integrart.integrar_t_android.domain.ActivityResource;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.ComunicationService;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.ComunicationService.ExternalResourceType;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.ComunicationService.OnLineMode;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.UpdateService;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.UpdateService.OnArriveNewResources;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.inject.Inject;

@ContentView(R.layout.main)
public class IntegrarTMainActivity extends RoboActivity implements OnArriveNewResources{
	@InjectView(R.id.title)			private TextView title;
	@InjectResource(R.string.hello)	private String titleText;
	@InjectView(R.id.imageView1)	private ImageView image;
	
	@Inject
	private UpdateService updateService;
	
	@Inject
	private ComunicationService comunicationService;

	private static String TAG = "integrar-t-android";

    /**
     * Called when the activity is first created.
     * @param savedInstanceState If the activity is being re-initialized after 
     * previously being shut down then this Bundle contains the data it most 
     * recently supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it is null.</b>
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
		Log.i(TAG, "onCreate");
        title.setText(titleText);
        OnLineMode mode = comunicationService.evaluateComunication();
        Toast.makeText(this, "OnLine mode: " + mode.name(), Toast.LENGTH_LONG).show();
        if(mode==OnLineMode.ON)
        	updateService.findUpdates("testActivity", this);
    }

	@Override
	public void onArriveNewResources(List<ActivityResource> resources) {
		if(resources.isEmpty()) return;
		InputStream is = comunicationService.findStream(ExternalResourceType.STATICS, resources.get(0).getPath());
		Drawable d = Drawable.createFromStream(is, "src");
		image.setImageDrawable(d);
	}
}

