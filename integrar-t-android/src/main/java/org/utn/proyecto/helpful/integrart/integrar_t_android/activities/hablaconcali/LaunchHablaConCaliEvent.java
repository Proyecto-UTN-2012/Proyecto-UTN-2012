package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.hablaconcali;

import org.utn.proyecto.helpful.integrart.integrar_t_android.events.LaunchActivityEvent;

import android.content.Context;

public class LaunchHablaConCaliEvent extends LaunchActivityEvent {
    public LaunchHablaConCaliEvent(Context context){
        super(context, HablaConCaliActivity.class);
    }
}
