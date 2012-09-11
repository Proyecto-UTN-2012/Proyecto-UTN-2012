package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.pictogramas;

import java.util.Map;

import org.utn.proyecto.helpful.integrart.integrar_t_android.MenuActionProvider;
import org.utn.proyecto.helpful.integrart.integrar_t_android.R;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.ChangeColorsEvent;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.EventBus;
import org.utn.proyecto.helpful.integrart.integrar_t_android.utils.ColorPickerDialog;

import android.content.Context;

public class ColorMenuActionProvider extends MenuActionProvider {

	public ColorMenuActionProvider(Context context, EventBus bus,
			Map<String, Object> params) {
		super(context, bus, params);
	}

	@Override
	public boolean execute() {
		ColorPickerDialog dialog = new ColorPickerDialog(context, new ColorPickerDialog.OnColorChangedListener(){
			
			@Override
			public void colorChanged(int color) {
				bus.dispatch(new ChangeColorsEvent(context, new int[]{0xffd7d7d7, color}));
			}
			
		}, 0);
		dialog.setTitle(R.string.selectColor);
		dialog.show();
		return false;
	}

}
