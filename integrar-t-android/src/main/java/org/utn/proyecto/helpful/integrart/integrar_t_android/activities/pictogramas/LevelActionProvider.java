package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.pictogramas;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.EventBus;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class LevelActionProvider{
	private final Context context;
	private final EventBus bus;

	public LevelActionProvider(Context context, EventBus bus) {
		this.context = context;
		this.bus = bus;
	}
	
	public boolean onPerformDefaultAction(){
		final CharSequence[] items = {"1", "2", "3"};
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		//builder.setIcon(R.drawable.icon_trans);
		builder.setTitle(R.string.selectLevel);
		builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int item) {
				bus.dispatch(new ChangeLevelEvent(context, item+1));
				dialog.dismiss();
			}
		});
		builder.create().show();		
		return true;
	}
}
