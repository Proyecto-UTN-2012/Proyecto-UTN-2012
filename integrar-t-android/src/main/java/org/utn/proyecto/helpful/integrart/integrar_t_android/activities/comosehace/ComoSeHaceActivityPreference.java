package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.comosehace;

import org.utn.proyecto.helpful.integrart.integrar_t_android.FileExplorer;
import org.utn.proyecto.helpful.integrart.integrar_t_android.R;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.dibujaconcali.DibujoActivity;

import roboguice.inject.InjectView;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class ComoSeHaceActivityPreference extends PreferenceActivity {

   // @InjectView(R.id.csh_seleccionar_button)
   // private Button fileSelection;
    /*OnPreferenceChangeListener listener = new OnPreferenceChangeListener() {    
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            // newValue is the value you choose
            return false;
        }
    };*/

    

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.csh_preference_simple_menu);
        
        
        //listPreference.setOnPreferenceChangeListener(listener);
     /*   fileSelection.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                //createFileExplorer("test");
            }
        });*/
    }
    
    protected void createFileExplorer(String key){
        Intent intent = new Intent(this, FileExplorer.class);

        //intent.putExtra(key,);

        this.startActivity(intent);
    }
}
