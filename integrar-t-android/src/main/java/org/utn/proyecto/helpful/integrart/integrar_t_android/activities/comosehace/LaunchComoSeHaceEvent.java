package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.comosehace;

import org.utn.proyecto.helpful.integrart.integrar_t_android.events.LaunchActivityEvent;

import android.content.Context;

public class LaunchComoSeHaceEvent extends LaunchActivityEvent {
    public LaunchComoSeHaceEvent(Context context){
        super(context, ComoSeHaceActivity.class);
    }
} 
