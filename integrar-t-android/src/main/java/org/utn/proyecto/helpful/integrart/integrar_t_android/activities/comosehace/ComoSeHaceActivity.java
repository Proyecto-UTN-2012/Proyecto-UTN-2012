package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.comosehace;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;
import org.utn.proyecto.helpful.integrart.integrar_t_android.R.string;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.Event;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.EventListener;

import com.google.inject.Inject;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;

@ContentView(R.layout.csh_activity_main)
public class ComoSeHaceActivity extends RoboActivity implements EventListener<Void>, OnItemClickListener {

    @InjectView(R.id.btn_csh_application)
    private Button applicationButton; 
    
    @InjectView(R.id.btn_csh_setting)
    private Button settingButton;
    
    static public String VIDEO = "video";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub        
        super.onCreate(savedInstanceState);

        applicationButton.setOnClickListener(new ComoSeHaceEvents(this));
        settingButton.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                // TODO instanciar configuracion general.
                executeReproductor("android.resource://" + getPackageName() +"/"+R.raw.assasin);
            }
       });
    }

    protected void executeReproductor(String path) {
        // TODO Auto-generated method stub
        Intent intent =  new Intent(this,ComoSeHaceReproductor.class);
        intent.putExtra(VIDEO, path);
        this.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        
        //getMenuInflater().inflate(R.menu.csh_activity_main_menu, menu);
        //SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        // TODO Auto-generated method stub
        return super.onMenuItemSelected(featureId, item);
    }
    
    public void executeCustomActivity()
    {
        Intent intent =  new Intent(this,ComoSeHaceAndarActivity.class);
        this.startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void onEvent(Event<Void> event) {
        // TODO Auto-generated method stub
        
    }
    
   

}
