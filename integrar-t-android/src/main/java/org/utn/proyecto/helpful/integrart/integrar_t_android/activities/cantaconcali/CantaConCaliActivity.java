package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.cantaconcali;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.conociendoacali.ConociendoACaliActivity;
import org.utn.proyecto.helpful.integrart.integrar_t_android.domain.User;
import org.utn.proyecto.helpful.integrart.integrar_t_android.interfaces.VerifyCharacter;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.DataStorageService;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.inject.Inject;
@ContentView(R.layout.ccc_main)
public class CantaConCaliActivity extends RoboActivity implements
OnCompletionListener, VerifyCharacter  {
    
    @Inject
    private DataStorageService db;
    
    @Inject
    private User user;
    
	@InjectView(R.id.cali)
	private ImageView cali;
	@InjectView(R.id.no)
	private Button no;
	@InjectView(R.id.si)
	private Button si;
	private AnimationDrawable caliAnimation;
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
	if (!isCaliSelected())
	{
	    executeConociendoCali();
	    return;
	}
		
	MediaPlayer sonidoCali = MediaPlayer.create(this, R.raw.hola_canta_con_cali);
	sonidoCali.start();
	caliAnimation = (AnimationDrawable) cali.getBackground();
	no.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			sacarBoton();
			
			CantaConCaliActivity context= (CantaConCaliActivity) v.getContext();
			MediaPlayer sonidoCali = MediaPlayer.create(context, R.raw.despedida_canta_con_cali);
			sonidoCali.start();
			sonidoCali.setOnCompletionListener(new OnCompletionListener() {

				@Override
				public void onCompletion(MediaPlayer mp) {

					if (mp != null) {
						mp.stop();
						mp.release();
						mp = null;

					}

					finish();	
					
				}
			});

			
			
			// TODO Auto-generated method stub		
		}
	});
	si.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
		sacarBoton();
		
		}
	});
		
	
	} //termina oncreate
	
	public void sacarBoton (){
		no.setVisibility(View.INVISIBLE);
		si.setVisibility(View.INVISIBLE);
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {

		super.onWindowFocusChanged(hasFocus);

		if (hasFocus)
			caliAnimation.start();
	}
	@Override
	public void onCompletion(MediaPlayer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
    public boolean isCaliSelected() {
           return db.contain(user.getUserName()+".cac_personaje_seleccionado");
    }
    
    private void executeConociendoCali()
    {
        Intent intent =  new Intent(this,ConociendoACaliActivity.class);
        this.startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        
        if (!isCaliSelected())
        {
            finish();
            return;
        }
    }

	
	
}
