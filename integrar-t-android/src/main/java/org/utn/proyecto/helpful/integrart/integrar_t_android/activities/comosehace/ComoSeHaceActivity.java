package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.comosehace;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.Event;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.EventListener;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

@ContentView(R.layout.csh_activity_main)
public class ComoSeHaceActivity extends RoboActivity implements EventListener<Void>, OnItemClickListener {

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onEvent(Event<Void> event) {
        // TODO Auto-generated method stub
        
    }

}
