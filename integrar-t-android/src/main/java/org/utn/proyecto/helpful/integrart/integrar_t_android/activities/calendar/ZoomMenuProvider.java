package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar;

import java.util.Map;

import org.utn.proyecto.helpful.integrart.integrar_t_android.MenuActionProvider;
import org.utn.proyecto.helpful.integrart.integrar_t_android.R;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.EventBus;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class ZoomMenuProvider extends MenuActionProvider {
	private boolean zoom;
	
	protected ZoomMenuProvider(Context context, EventBus bus,
			Map<String, Object> params) {
		super(context, bus, params);
		zoom = (Boolean) params.get("zoom");
	}

	@Override
	public boolean execute() {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		String zoomText = context.getResources().getString(R.string.showZoomText);
		builder.setMultiChoiceItems(new String[]{zoomText}, new boolean[]{zoom}, new DialogInterface.OnMultiChoiceClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				bus.dispatch(new ShowZoomEvent(context, isChecked));
			}
		});
		builder.create().show();
		return true;
	}

}
