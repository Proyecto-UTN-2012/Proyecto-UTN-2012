package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.dibujaconcali;

import java.util.Random;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.cuentos.CurrentCuentoActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.inject.Inject;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.dcc_main)
public class DibujaConCaliActivity extends RoboActivity {

	private static final Random random = new Random();

	@InjectView(R.id.gato)
	ImageView gato;

	@InjectView(R.id.perro)
	ImageView perro;

	@InjectView(R.id.avion)
	ImageView avion;

	@InjectView(R.id.corazon)
	ImageView corazon;

	@InjectView(R.id.azar)
	ImageView azar;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		perro.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				startDibujo(R.drawable.perro);
			}
		});

		gato.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				startDibujo(R.drawable.gato);
			}
		});

		avion.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				startDibujo(R.drawable.avion);
			}
		});

		corazon.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				startDibujo(R.drawable.corazon);
			}
		});

		azar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				int[] dibujos = new int[] { R.drawable.perro, R.drawable.gato,
						R.drawable.corazon, R.drawable.avion };
				startDibujo(dibujos[random.nextInt(4)]);
			}
		});

	}

	private void startDibujo(int dibujo) {
		Intent intent = new Intent(this, DibujoActivity.class);

		intent.putExtra("dibujo", dibujo);

		this.startActivity(intent);

	}

}
