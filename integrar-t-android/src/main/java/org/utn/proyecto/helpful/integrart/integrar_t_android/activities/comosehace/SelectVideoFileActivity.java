package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.comosehace;

import org.utn.proyecto.helpful.integrart.integrar_t_android.FileExplorer;
import org.utn.proyecto.helpful.integrart.integrar_t_android.R;
import org.utn.proyecto.helpful.integrart.integrar_t_android.domain.User;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.Event;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.EventBus;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.EventListener;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.FileExplorerEvent;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.DataStorageService;

import com.google.inject.Inject;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

public class SelectVideoFileActivity extends RoboActivity implements EventListener<Void>{

    @InjectView(R.id.csh_file_asociated)
    TextView textViewFileAssociated;
    
    @Inject
    private User user;
    
    @Inject
    private DataStorageService db;
    
    @Override
    public void onEvent(Event<Void> event) {
        textViewFileAssociated.setText(db.get(user.getUserName() + "."+getResources().getString(R.string.csh_sharepreference_value),String.class));
    }

    @Inject
    private EventBus bus;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        setContentView(R.layout.csh_menu_sel_archivos);
        bus.addEventListener(FileExplorerEvent.class, this );
        
        textViewFileAssociated.setText(db.get(user.getUserName() + "."+getResources().getString(R.string.csh_sharepreference_value),String.class));
    }
    
    
    
    public void createFileExplorer(View v){
       Intent intent = new Intent(this, FileExplorer.class);
        intent.putExtra(getResources().getString(R.string.csh_key),getResources().getString(R.string.csh_sharepreference_value));
        this.startActivity(intent); 
    }
    
}
