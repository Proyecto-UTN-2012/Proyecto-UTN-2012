package org.utn.proyecto.helpful.integrart.integrar_t_android.preferencias;

import org.utn.proyecto.helpful.integrart.integrar_t_android.events.LaunchActivityEvent;
import android.content.Context;

public class LaunchPreferenceEvent extends LaunchActivityEvent{
    public LaunchPreferenceEvent(Context context){
        super(context, PreferenceActivity.class);
    }
}
