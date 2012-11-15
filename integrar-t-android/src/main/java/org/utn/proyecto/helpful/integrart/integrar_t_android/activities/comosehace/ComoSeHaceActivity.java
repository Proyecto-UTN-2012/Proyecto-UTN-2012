package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.comosehace;

import org.utn.proyecto.helpful.integrart.integrar_t_android.FileExplorer;
import org.utn.proyecto.helpful.integrart.integrar_t_android.R;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.Event;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.EventListener;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;

@ContentView(R.layout.csh_activity_main)
public class ComoSeHaceActivity extends RoboActivity implements EventListener<Void>, OnItemClickListener {

    @InjectView(R.id.btn_csh_application)
    private Button applicationButton; 
    
    static public String VIDEO = "video";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub        
        super.onCreate(savedInstanceState);

        applicationButton.setOnClickListener(new ComoSeHaceEvents(this));
        
    }
    
    private void executeCSHPreferenceActivity() {
        // TODO Auto-generated method stub
        Intent intent =  new Intent(this,ComoSeHaceActivityPreference.class);
        this.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // TODO Auto-generated method stub
        
        getMenuInflater().inflate(R.menu.csh_activity_main_menu, menu);
        //SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        
        switch (item.getItemId()) {
        case R.id.csh_menuItem_settings:
            executeCSHPreferenceActivity();
            return true;
        case R.id.csh_menuItem_video:
            executeVideoMenu();
            return true;
        case R.id.csh_menuItem_exit:
            this.finish();
            return  true;
        }
        return super.onMenuItemSelected(featureId, item);
    }
    
    public void executeVideoMenu(){
        Intent intent = new Intent(this,SelectVideoFileActivity.class);
        this.startActivity(intent);
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
