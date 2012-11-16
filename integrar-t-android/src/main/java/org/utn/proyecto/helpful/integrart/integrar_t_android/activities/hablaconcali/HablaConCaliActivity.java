package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.hablaconcali;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.conociendoacali.ConociendoACaliActivity;
import org.utn.proyecto.helpful.integrart.integrar_t_android.domain.User;
import org.utn.proyecto.helpful.integrart.integrar_t_android.interfaces.VerifyCharacter;
import org.utn.proyecto.helpful.integrart.integrar_t_android.metrics.ActivityMetric;
import org.utn.proyecto.helpful.integrart.integrar_t_android.metrics.Metric;
import org.utn.proyecto.helpful.integrart.integrar_t_android.metrics.MetricsService;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.DataStorageService;
import org.utn.proyecto.helpful.integrart.integrar_t_android.utils.CaliHelper;
import org.utn.proyecto.helpful.integrart.integrar_t_android.utils.CaliView;
import org.utn.proyecto.helpful.integrart.integrar_t_android.utils.GiftCount;
import org.utn.proyecto.helpful.integrart.integrar_t_android.utils.GiftPopup;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import com.google.inject.Inject;

@ContentView(R.layout.hcc_main)
public class HablaConCaliActivity extends RoboActivity implements
        OnCompletionListener, VerifyCharacter {
	private final static String USER_KEY = "currentUser";

    @Inject
    private MetricsService metricsService;
    
    @InjectView(R.id.caliHcc)
    private CaliView cali;
    
    @Inject
    private DataStorageService db;
    
    @Inject
    private User user;
    
    @Inject
    private CaliHelper caliHelper;

//    private AnimationDrawable caliAnimation;

    private MediaRecorder mRecorder = null;

    private MediaPlayer frase = null;
    // public long time;

    private boolean listening = true;

    private boolean grito = false;

    private boolean contesto = false;

    private Double amplitude1;

    private int nrofrase = 0;

	private List<Integer> frases = new ArrayList<Integer>(Arrays.asList(
			R.raw.como_te_llamas, R.raw.queres_jugar_conmigo,
			R.raw.cuantos_anios_tenes, R.raw.vos_sos_dios, R.raw.chau));

    @Override
    protected void onResume() {
        super.onResume();
        
        if (!isCaliSelected())
        {
            finish();
            return;
        }
    }
    
    @Override
    protected void onStop() {

        listening = false;
        frases.clear();
        

        if (mRecorder != null) {
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        }

        if ((frase != null) && (frase.isPlaying())) {

            frase.stop();
            frase.release();
            frase = null;
        }
        //
        Log.d("onstop", "estoy en el onstop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d("ondestroy", "estoy en el ondestroy");
        listening = false;
        if (mRecorder != null) {
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        }

        cali.stop();
//        caliAnimation.stop();

        super.onDestroy();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (!isCaliSelected())
        {
            executeConociendoCali();
            return;
        }
        
        Log.d("Habla con aida", "ahora si estoy logiando el onCreate");

        cali.setHelper(caliHelper);
//        cali.setBackgroundResource(caliHelper.getInitTalkingCaliResource());
//        caliAnimation = (AnimationDrawable) cali.getBackground();

        // final MediaPlayer frase = MediaPlayer.create(getApplicationContext(),
        // R.raw.hola);

        frase = MediaPlayer.create(getApplicationContext(), R.raw.hola);

        frase.start();

        frase.setOnErrorListener(new MediaPlayer.OnErrorListener() {

            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.d("hola frase error", "hola error");
                mp.release();
                frase = null;
                return false;
            }
        });

        frase.setOnCompletionListener(new OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {

                if (mp != null) {
                    mp.stop();
                    mp.release();
                    frase = null;

                }
                
                cali.stop();

                new Ear().execute();
                // mp.release();
            }
        });

    } // cierra el oncreate

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        super.onWindowFocusChanged(hasFocus);

        if (hasFocus)
            cali.greet();
        	//cali.talk();
    }

    // Metodo que inicializa la escucha
    public void start() {
        if (mRecorder == null) {
            // initime();

            // Inicializamos los parametros del grabador
            mRecorder = new MediaRecorder();
            mRecorder
                    .setAudioSource(MediaRecorder.AudioSource.VOICE_RECOGNITION);
            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            // No indicamos ningun archivo ya que solo queremos escuchar
            mRecorder.setOutputFile("/dev/null");

            try {
                mRecorder.prepare();
            } catch (IllegalStateException e) {
                Log.e("error", "IllegalStateException");
            } catch (IOException e) {
                Log.e("error", "IOException");
            }
            mRecorder.start();
            // endtime();
        }

    }

    // private void initime() {
    // time = new Date().getTime();
    // }
    //
    // private void endtime() {
    // Log.e("start", "tiempo: " + (new Date().getTime() - time));
    // }

    // Para la escucha
    public void stop() {

        if (!grito) {

            if ((amplitude1 > 0.0) && (frases.size() > 0)) {

				listening = true;
				contesto = false;
				user.addGifts(3);
				Dialog giftDialog = new GiftPopup(this, user.getGifts(), GiftCount.THREE);
				giftDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
					
					@Override
					public void onDismiss(DialogInterface dialog) {
						sigfrase();
					}
				});
				giftDialog.show();
				db.put(USER_KEY, user);
			} else {

                try {
                    this.finish();
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }

            }

        } else {
            
            //Procedimiento por gritar
        	user.addGifts(1);
        	db.put(USER_KEY, user);
        	Dialog dialog = new GiftPopup(this, user.getGifts());
        	dialog.show();
        	dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
				@Override
				public void onDismiss(DialogInterface dialog) {
					MediaPlayer mp = MediaPlayer.create(getApplicationContext(),
							R.raw.chau);
					
					mp.start();
					cali.greet();
					
					mp.setOnErrorListener(new MediaPlayer.OnErrorListener() {
						
						@Override
						public boolean onError(MediaPlayer mp, int what, int extra) {
							Log.d("hola frase error", "hola error");
							mp.release();
							mp = null;
							return false;
						}
					});
					
					mp.setOnCompletionListener(new OnCompletionListener() {
						
						@Override
						public void onCompletion(MediaPlayer mp) {
							
							if (mp != null) {
								mp.stop();
								mp.release();
								mp = null;
								cali.stop();
							}
							finish();
						}
					});
					
				}
			});
            Log.d("si gritó", "gritó");
            
            Metric metrica = new Metric(user, ActivityMetric.HABLA_CON_CALI, getString(R.string.metric_categoria_hablaconcali));
            metricsService.sendMetric(metrica);
        }
    }

    public void sigfrase() {

        int cantfrases = frases.size() - 1;
        if (cantfrases > 1) {
            nrofrase = randomNumber(0, cantfrases - 1);
        } else {
            nrofrase = 0;
        }

        // final MediaPlayer frase = MediaPlayer.create(getApplicationContext(),
        // frases.get(nrofrase));

        frase = MediaPlayer.create(getApplicationContext(),
                frases.get(nrofrase));
        frase.start();

        cali.talk();
//        caliAnimation.stop();
//        caliAnimation.start();

        frases.remove(nrofrase);
        frase.setOnErrorListener(new MediaPlayer.OnErrorListener() {

            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.d("frase error", "error");
                mp.release();
                frase = null;
                return false;
            }
        });
        frase.setOnCompletionListener(new OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {

                if (mp != null) {
                    mp.stop();
                    mp.release();
                    cali.stop();
                    frase = null;
                }

                new Ear().execute();
            }

        });

    }

    public static int randomNumber(int min, int max) {
        return min + (new Random()).nextInt(max - min);
    }

    // Devuelve la mayor amplitud del sonido captado desde la ultima vez que se
    // llamo al metodo

    public double getAmplitude() {
        if ((mRecorder != null) && (listening == true)) {
            Log.d("en getamplitud", "estoy tomando la amplitud");
            return (double) (mRecorder.getMaxAmplitude());
        }
        Log.d("en getamplitude ", "el recorder es null");
        return 0d;
    }

    public class Ear extends AsyncTask<Void, Double, Void> {
        protected void onPreExecute() {
            start();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            amplitude1 = (double) 0;

            double amplitude = (double) 0;

            while (listening) {

                Log.d("en background ", "grito es " + grito);
                if (amplitude1 == 0.0) {
                    SystemClock.sleep(50);
                    amplitude = getAmplitude();

                    SystemClock.sleep(600);

                    amplitude = getAmplitude();
                    amplitude1 = amplitude;
                    Log.d("HablaConCali", "Amplitud1: " + amplitude1);
                }

                SystemClock.sleep(400);
                amplitude = getAmplitude();

                Log.d("HablaConCali", "Amplitud: " + amplitude);

                if (amplitude > amplitude1 + 2000 || amplitude==32767d) {
                    publishProgress(amplitude);
                    contesto = true;
                }

                if ((amplitude < amplitude1 + 2000) && (contesto == true)) {
                    listening = false;
                    Log.d(amplitude1 + "habla con cali",
                            "salió con esta amplitud " + amplitude);
                }

            }
            return null;

        }

        @Override
        protected void onProgressUpdate(Double... values) {
            Double value = values[0];
            if (value==32767d) {
                grito = true;
                listening = false;
                stop();

            }

        }

        @Override
        protected void onPostExecute(Void result) {
            stop();

        }

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        // mp.release();
    }

    @Override
    public boolean isCaliSelected() {
           return db.contain(user.getUserName()+".cac_personaje_seleccionado");
    }
    
    private void executeConociendoCali()
    {
        Intent intent =  new Intent(this,ConociendoACaliActivity.class);
        this.startActivity(intent);
    }

    // @Override
    // public boolean onCreateOptionsMenu(Menu menu)
    // {
    // //creates a menu inflater
    // MenuInflater inflater = getMenuInflater();
    // //generates a Menu from a menu resource file
    // //R.menu.main_menu represents the ID of the XML resource file
    // inflater.inflate(R.menu.hablaconcali_menu, menu);
    // return true;
    // }
    //
    // @Override
    // public boolean onOptionsItemSelected(MenuItem item)
    // {
    // // //check selected menu item
    // switch (item.getItemId()) {
    // case R.id.uno: confcantfrases = 1;
    // break;
    //
    // case R.id.dos: confcantfrases = 2;
    // break;
    //
    //
    // case R.id.tres: confcantfrases = 3;
    // break;
    //
    //
    // case R.id.cuatro: confcantfrases = 4;
    //
    // break;
    //
    // case R.id.cinco: confcantfrases = 5;
    // break;
    //
    //
    // case R.id.seis: confcantfrases = 6;
    // break;
    //
    // case R.id.siete: confcantfrases = 7;
    // break;
    //
    // case R.id.ocho: confcantfrases = 8;
    // break;
    //
    // case R.id.nueve: confcantfrases = 9;
    // break;
    //
    // case R.id.diez: confcantfrases = 10;
    //
    // break;
    //
    // }
    // return true;
    // }

}