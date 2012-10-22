package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.conociendoacali;

import org.utn.proyecto.helpful.integrart.integrar_t_android.events.LaunchActivityEvent;

import android.content.Context;

public class LaunchConociendoACaliEvent extends LaunchActivityEvent {
    public LaunchConociendoACaliEvent(Context context)
    {
        super(context , ConociendoACaliActivity.class);
    }

}
