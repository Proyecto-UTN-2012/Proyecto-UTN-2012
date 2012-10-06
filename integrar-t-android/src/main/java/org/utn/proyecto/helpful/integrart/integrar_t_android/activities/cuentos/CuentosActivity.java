package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.cuentos;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.VmPolicy.Builder;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.text.Editable;
import android.text.Spannable;
import android.text.TextPaint;
import android.text.style.CharacterStyle;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


@ContentView(R.layout.cuentos)
public class CuentosActivity extends RoboActivity {

	@InjectView(R.id.pinochoButton)
	private ImageButton pinochoBtn;
	
	@InjectView(R.id.ricitosButton)
	private ImageButton ricitosBtn;
	
	@InjectView(R.id.azarButton)
	private ImageButton azarBtn;


	private String str;
	private String word;

	private List<Pagina> paginas;

	private SimpleOnPageChangeListener listener;

	private long aida;

	private int time;
	private int cuento;
	private int index1;
	private int index2;

	private boolean show = false;

//	private CuentosAdapter cuentosAdapter;
	private Editable span;

	private MediaPlayer pageSound;

	private int i = 1;

//	private CuentoThread currentThread;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		pinochoBtn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				cuento = 1;
				startCuento();
			}
		});
	
	

	

		ricitosBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				cuento = 2;
				startCuento();
			}
		});

		azarBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// puse 2-1 porque es la cantidad de cuentos menos 1
				cuento = 1 + (new Random()).nextInt(2 - 1);
				startCuento();
			}
		});


	} // termino el oncreate

	public void startCuento() {

		   Intent intent = new Intent(this, CurrentCuentoActivity.class);
		   intent.putExtra("cuento", cuento);
	        this.startActivity(intent);	   	
		
//		paginas = new ArrayList<Pagina>();
//		Pagina pagina = new Pagina(getString(R.string.pinocho_pag1),
//				getResources().getDrawable(R.drawable.pinocho_pg1),
//				MediaPlayer.create(this, R.raw.pinocho_pag1));
//		paginas.add(pagina);
//		pagina = new Pagina(getString(R.string.pinocho_pag2), getResources()
//				.getDrawable(R.drawable.pinocho_pg2), MediaPlayer.create(this,
//				R.raw.pinocho_pag2));
//		paginas.add(pagina);
//
//		cuentosAdapter = new CuentosAdapter(paginas, this);
//		cuentosPager.setAdapter(cuentosAdapter);
//		listener = new ViewPager.SimpleOnPageChangeListener() {
//
//			@Override
//			public void onPageSelected(int position) {
//				word = null;
//				if (cuentosAdapter.getTextView(position) != null) {
//					play(paginas.get(position));
//					showText(paginas.get(position),
//							cuentosAdapter.getTextView(position));
//
//				}
//			}
//
//		};
//		cuentosPager.setOnPageChangeListener(listener);
//
//		listener.onPageSelected(0);
//
//				
//		
//	}
//
//	@Override
//	public void onWindowFocusChanged(boolean hasFocus) {
//
//		super.onWindowFocusChanged(hasFocus);
//
//		if (hasFocus) {
//			listener.onPageSelected(0);
//		}
//	}
//
//	public void play(Pagina pagina) {
//		if (pageSound != null && pageSound.isPlaying()) {
//			pageSound.stop();
//			try {
//				pageSound.prepare();
//			} catch (Exception e) {
//
//				e.printStackTrace();
//			}
//		}
//		pageSound = pagina.getSound();
//
//		pageSound.seekTo(0);
//		pageSound.start();
//	}
//
//	public void showText(Pagina pag, final TextView text) {
//		if (currentThread != null)
//			currentThread.end();
//
//		final Semaphore semaphore = new Semaphore(1);
//		str = " " + pag.getText() + "   ";
//		Log.d("length", "length " + str.length());
//		text.setText(str);
//		currentThread = new CuentoThread(pag, text, semaphore);
//		currentThread.start();
//
//
//
//	}
//
//	public class Styl extends CharacterStyle {
//
//		@Override
//		public void updateDrawState(TextPaint tp) {
//			tp.setShadowLayer(10f, 0f, 0f, 0xffffff00);
//			tp.setColor(0xffffff00);
//
//		}
//
//	}
//
//	@Override
//	protected void onDestroy() {
//		if (currentThread.isAlive())
//			currentThread.end();
//		if (pageSound != null) {
//
//			pageSound.stop();
//			pageSound.release();
//			pageSound = null;
//		}
//		super.onDestroy();
//
//	}
//
//	private class CuentosAdapter extends PagerAdapter {
//		private final List<Pagina> paginas;
//
//		private final ViewGroup[] paginasView;
//		private final Context context;
//
//		public CuentosAdapter(List<Pagina> paginas, Context context) {
//			this.context = context;
//			this.paginas = paginas;
//
//			paginasView = new ViewGroup[paginas.size()];
//
//		}
//
//		@Override
//		public int getCount() {
//
//			return paginas.size();
//		}
//
//		@Override
//		public Object instantiateItem(ViewGroup container, int position) {
//
//			if (paginasView[position] == null) {
//
//				ViewGroup view = (ViewGroup) ((LayoutInflater) context
//						.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
//						.inflate(R.layout.cuentos_page, container, false);
//
//				TextView tView = (TextView) view
//						.findViewById(R.id.cuentos_TxtV);
//				ImageView iView = (ImageView) view
//						.findViewById(R.id.cuentos_Img);
//
//				tView.setText(" " + paginas.get(position).getText() + "   ");
//				iView.setBackgroundDrawable(paginas.get(position).getImage());
//				paginasView[position] = view;
//
//				container.addView(view, position);
//
//
//			}
//
//			return paginasView[position];
//
//		}
//
//		public TextView getTextView(int position) {
//			if (paginasView[position] != null)
//				return (TextView) paginasView[position]
//						.findViewById(R.id.cuentos_TxtV);
//			return null;
//
//		}
//
//		@Override
//		public boolean isViewFromObject(View arg0, Object arg1) {
//
//			return arg0 == arg1;
//
//		}
//
//		@Override
//		public void destroyItem(ViewGroup collection, int position, Object view) {
//			collection.removeView((View) view);
//		}
//
//	}
//
//	private class CuentoThread extends Thread {
//		private final Pagina pagina;
//		private final TextView texto;
//		private final Semaphore semaphore;
//		private int index1 = 0;
//		private int index2 = 0;
//		private boolean end;
//
//		public CuentoThread(final Pagina pagina, final TextView texto,
//				final Semaphore semaphore) {
//			this.pagina = pagina;
//			this.texto = texto;
//			this.semaphore = semaphore;
//		}
//
//		public void end() {
//			end = true;
//		}
//
//		@Override
//		public void run() {
//
//			while ((!"".equals(word) && !end) /* && (that==currentThread) */) {
//				try {
//					Log.d("Entre al while no jodas", "jhgjhgjhgjhghj");
//					semaphore.acquire();
//					Log.d("Ah re loco", "No estoy bloqueado por el semaforro");
//				} catch (InterruptedException e1) {
//					throw new RuntimeException(e1);
//				}
//				index1 = index2 + 1;
//
//				Log.d("showtext", "index1: " + index1 + " index2: " + index2);
//				index2 = str.indexOf(" ", index1);
//				if (index2 == index1) {
//					word = "";
//					semaphore.release();
//					continue;
//
//				}
//
//				show = true;
//				word = str.substring(index1, index2);
//				aida = new Date().getTime();
//				Log.d("word:", word);
//				time = word.length() * 90;
//				try {
//					// currentThread.sleep(time);
//					synchronized (this) {
//						this.wait(time);
//						if (end || index1 == index2)
//							break;
//					}
//					// this.sleep(time);
//					runOnUiThread(new Runnable() {
//
//						@Override
//						public void run() {
//
//							if (show == true) {
//								show = false;
//								Log.d("Aida tiene Miedo", "Tardó: "
//										+ (new Date().getTime() - aida) + "ms");
//								texto.setText(str);
//								span = texto.getEditableText();
//								Log.d("showtextRun", "index1: " + index1
//										+ " index2: " + index2);
//
//								Log.d("showtextRun ", texto.getText()
//										.toString());
//								// span.setSpan(
//								// new TextAppearanceSpan(texto
//								// .getContext(),
//								// R.style.PaintText), index1,
//								// index2,
//								// Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
//
//								span.setSpan(new Styl(), index1, index2,
//										Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
//
//								// span.setSpan(
//								// new TextAppearanceSpan(texto
//								// .getContext(),
//								// R.style.OrigText), 0,
//								// index1,
//								// Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
//
//								// texto.setText(span);
//								semaphore.release();
//							}
//
//						}
//
//					});
//
//				} catch (InterruptedException e1) {
//					throw new RuntimeException(e1);
//				}
//
//			} // termino el while
//			Log.d("While", "aHH RE LOCO SALIO DEL WAIL");
//			word = null;
//			semaphore.release();
//			runOnUiThread(new Runnable() {
//
//				@Override
//				public void run() {
//
//					texto.setText(str);
//				}
//			});
//		}
//
	}

} // termina la clase

