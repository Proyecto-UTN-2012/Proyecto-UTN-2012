package org.utn.proyecto.helpful.integrart.integrar_t_android.preferencias;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;

import android.os.Bundle;

public class PreferenceActivity extends android.preference.PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferencias);
    }

}
