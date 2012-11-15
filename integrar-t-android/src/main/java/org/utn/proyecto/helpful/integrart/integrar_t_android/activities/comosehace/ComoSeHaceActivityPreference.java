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
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.csh_preference_simple_menu);
        
    }
    
    public void createFileExplorer(View v){
        Intent intent = new Intent(this, FileExplorer.class);
        //intent.putExtra(key,);
        this.startActivity(intent);
    }
}
