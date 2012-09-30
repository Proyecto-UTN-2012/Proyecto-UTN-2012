package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.comosehace;

import android.view.View;
import android.view.View.OnClickListener;

public class ComoSeHaceEvents implements OnClickListener   {
    ComoSeHaceActivity activity;

    public ComoSeHaceEvents(ComoSeHaceActivity comoSeHaceActivity) {
        // TODO Auto-generated constructor stub
        activity = comoSeHaceActivity;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        activity.executeCustomActivity();
    }

}
