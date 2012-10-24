package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.dibujaconcali;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;

@ContentView(R.layout.dcc_dibujo)
public class DibujoActivity extends RoboActivity {
	@InjectView(R.id.view)
	private FrameLayout view;

	@InjectView(R.id.frame)
	private FrameLayout frame;

	@InjectView(R.id.miniature)
	private ImageView miniature;
	private int dibujo;
	private static Map<Integer, Point[]> map = new HashMap<Integer, Point[]>();
	{
		map.put(R.drawable.corazon, new Point[] { new Point(640, 110),
				new Point(420, 20), new Point(200, 200), new Point(460, 490),
				new Point(640, 630), new Point(820, 490), new Point(1080, 200),
				new Point(860, 20)

		});

		map.put(R.drawable.avion, new Point[] { new Point(225, 150),
				new Point(152, 235), new Point(45, 278), new Point(152, 330),
				new Point(291, 313), new Point(380, 373), new Point(172, 513),
				new Point(370, 560), new Point(495, 550), new Point(635, 585),
				new Point(690, 516), new Point(765, 490), new Point(950, 505),
				new Point(1140, 455), new Point(1070, 350),
				new Point(1175, 325), new Point(1138, 290),
				new Point(1070, 185), new Point(710, 270), new Point(450, 260),
				new Point(460, 200), new Point(390, 195) });

		map.put(R.drawable.perro, new Point[] { new Point(515, 205),
				new Point(310, 200), new Point(100, 345), new Point(15, 480),
				new Point(245, 470), new Point(410, 365), new Point(460, 375),
				new Point(520, 455), new Point(470, 500), new Point(540, 505),
				new Point(525, 530), new Point(705, 535), new Point(700, 490),
				new Point(685, 440), new Point(925, 440), new Point(910, 465),
				new Point(825, 505), new Point(900, 515), new Point(900, 535),
				new Point(1075, 535), new Point(1045, 440),
				new Point(1105, 385), new Point(1180, 405),
				new Point(1105, 355), new Point(925, 295), new Point(610, 300)

		});

		map.put(R.drawable.auto, new Point[] { new Point(555, 50),
				new Point(235, 260), new Point(85, 490), new Point(85, 585),
				new Point(135, 585), new Point(193, 585), new Point(320, 660),
				new Point(475, 585), new Point(545, 585), new Point(745, 585),
				new Point(800, 585), new Point(955, 660), new Point(1095, 585),
				new Point(1150, 580), new Point(1220, 575),
				new Point(1195, 480), new Point(905, 260)

		});

	}

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		final Context context = this;
		Bundle arguments = getIntent().getExtras();
		dibujo = arguments.getInt("dibujo");
		miniature.setImageResource(dibujo);
		miniature.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				view.setAlpha(1f);
			}
		});

		view.setBackgroundResource(dibujo);
		ObjectAnimator fade = ObjectAnimator.ofFloat(null, "alpha", 1.0f, 0.1f);
		fade.setDuration(2000);
		fade.setStartDelay(2000);
		fade.setInterpolator(new LinearInterpolator());
		fade.setTarget(view);
		fade.start();
		fade.addListener(new Animator.AnimatorListener() {

			@Override
			public void onAnimationStart(Animator animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animator animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				Point[] points = map.get(dibujo);

				for (Point point : points) {
					ImageView image = new ImageView(context);
					image.setLayoutParams(new ViewGroup.LayoutParams(20, 20));
					image.setBackgroundResource(R.drawable.circle_grey);
					image.setX((float) point.x);
					image.setY((float) point.y);
					frame.addView(image);
				}

			}

			@Override
			public void onAnimationCancel(Animator animation) {
				// TODO Auto-generated method stub

			}
		});

	}

}
