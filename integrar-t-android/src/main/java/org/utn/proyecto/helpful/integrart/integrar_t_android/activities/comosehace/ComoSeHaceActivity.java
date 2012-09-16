package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.comosehace;

import java.util.logging.Logger;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.Event;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.EventListener;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

@ContentView(R.layout.csh_activity_main)
public class ComoSeHaceActivity extends RoboActivity implements EventListener<Void>, OnItemClickListener {

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenuInfo menuInfo) {
        // TODO Auto-generated method stub
        super.onCreateContextMenu(menu, v, menuInfo);
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
        // TODO Auto-generated method stub
        
        /*Context context = this.getContext();
        Intent intent = new Intent(context, event.getData());
        context.startActivity(intent);
        */
        /*Intent intent =  new Intent(this,ComoSeHaceAndarActivity.class);
        this.startActivity(intent);
        return true; */
        executeCustomActivity();
        return super.onMenuItemSelected(featureId, item);
    }
    
    private void executeCustomActivity()
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
