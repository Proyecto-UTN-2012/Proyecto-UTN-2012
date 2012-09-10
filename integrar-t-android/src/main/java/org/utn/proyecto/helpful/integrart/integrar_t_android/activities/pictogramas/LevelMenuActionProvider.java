package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.pictogramas;

import java.util.Map;

import org.utn.proyecto.helpful.integrart.integrar_t_android.MenuActionProvider;
import org.utn.proyecto.helpful.integrart.integrar_t_android.R;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.EventBus;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class LevelMenuActionProvider extends MenuActionProvider{
	private final int currentLevel;

	public LevelMenuActionProvider(Context context, EventBus bus, Map<String, Object> params) {
		super(context, bus, params);
		this.currentLevel = (Integer) params.get("currentLevel");
	}

	@Override
	public boolean execute() {
		final CharSequence[] items = {"1", "2", "3"};
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		//builder.setIcon(R.drawable.icon_trans);
		builder.setTitle(R.string.selectLevel);
		builder.setSingleChoiceItems(items, currentLevel - 1, new DialogInterface.OnClickListener() {
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
