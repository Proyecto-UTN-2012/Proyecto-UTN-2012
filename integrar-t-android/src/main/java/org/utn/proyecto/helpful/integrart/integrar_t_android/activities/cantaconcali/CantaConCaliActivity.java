package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.cantaconcali;

import java.util.List;
import java.util.Random;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.conociendoacali.ConociendoACaliActivity;
import org.utn.proyecto.helpful.integrart.integrar_t_android.domain.User;
import org.utn.proyecto.helpful.integrart.integrar_t_android.interfaces.VerifyCharacter;
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
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognitionService;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.inject.Inject;
@ContentView(R.layout.ccc_main)
public class CantaConCaliActivity extends RoboActivity implements
VerifyCharacter, RecognitionListener  {
    private static final String TAG = "VoiceRecognition";
    
    private static Random rnd = new Random();
    
    @Inject
    private CantaUpdateService updateService;
    
    @Inject
    private CantaLoader loader;
    
    @Inject
    private DataStorageService db;
    
    @Inject
    private CaliHelper caliHelper;
    
    @Inject
    private User user;
    
    @InjectView(R.id.container)
    private FrameLayout container;
    
    @InjectView(R.id.cali)
    private CaliView cali;
    
    private MediaPlayer understand;
    
    private SpeechRecognizer voiceService;
    
    private List<Cancion> songs;
    
    @InjectView(R.id.songView)
    private LinearLayout soundView;
    
    @InjectView(R.id.songName)
    private TextView soundName;
    
    private StateManager stateManager;
    
    private MediaPlayer questionSound;
    
    private Cancion currentSong;
    
    @Inject
    private MetricsService metricsService;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        
    if (!isCaliSelected())
    {
        executeConociendoCali();
        return;
    }
    cali.setHelper(caliHelper);
    updateService.findUpdate();
    voiceService = SpeechRecognizer.createSpeechRecognizer(this);   
    initVoiceSettings();
    understand = MediaPlayer.create(this, R.raw.understand);
    stateManager = new FirstStateManager(this, metricsService, user, cali);
    questionSound = MediaPlayer.create(this, R.raw.song);
    questionSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            mp.stop();
            cali.stop();
            mp.prepareAsync();
            startVoiceRecognitionActivity();
        }
    });
    } //termina oncreate

    @Override
    protected void onDestroy(){
        super.onDestroy();
        understand.release();
        cali.stop();
        questionSound.release();
        if(currentSong!=null){
            currentSong.getSonido().release();
        }
    }
    
    private void initVoiceSettings() {
        voiceService.setRecognitionListener(this);
    }
    
    /**
     * Fire an intent to start the speech recognition activity.
     */
    public void startVoiceRecognitionActivity() {
        Intent intent = new Intent(RecognitionService.SERVICE_INTERFACE);

        // Specify the calling package to identify your application
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, getClass().getPackage().getName());

        // Display an hint to the user about what he should say.
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech recognition demo");

        // Given an hint to the recognizer about what the user is going to say
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        // Specify how many results you want to receive. The results will be sorted
        // where the first result is the one with higher confidence.
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);

        // Specify the recognition language. This parameter has to be specified only if the
        // recognition has to be done in a specific language and not the default one (i.e., the
        // system locale). Most of the applications do not have to set this parameter.
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"es_AR");
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"es_ES");
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"es");
        Log.i(TAG, "Iniciando Spech");
        voiceService.startListening(intent);
    }
    
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        super.onWindowFocusChanged(hasFocus);

        if (hasFocus){
            animateHello();
        }
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
    public void onBeginningOfSpeech() {
        Log.i(TAG, "Begin Speech");
        
    }

    @Override
    public void onBufferReceived(byte[] arg0) {
        Log.i(TAG, "bufferReceived");
        
    }

    @Override
    public void onEndOfSpeech() {
        Log.i(TAG, "End Speech");
        startVoiceRecognitionActivity();
        
    }

    @Override
    public void onError(int error) {
        Log.i(TAG, "error speech: " + error);
    }

    @Override
    public void onEvent(int eventType, Bundle params) {
        
    }

    @Override
    public void onPartialResults(Bundle partialResults) {
        Log.i(TAG, "Partial:" + partialResults.toString());
    }

    @Override
    public void onReadyForSpeech(Bundle params) {
        Log.i(TAG, "Rady Speech");
        
    }

    @Override
    public void onResults(Bundle results) {
        List<String> list = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        Log.i(TAG, "On Result: " + list.toString());
        voiceService.stopListening();
        if(list.size()<1){
            notUnderstand();
            return;
        }
        
        String text = list.get(0);
        Log.d("Canta con Cali", text);
        if("chau".equals(text) || "chaau".equals(text) || "chao".equals(text) || "chaao".equals(text)){
            end();
            return;
        }
        
        if("no".equals(text)){
            stateManager.processNo();
        }
        else if("si".equals(text) || "sii".equals(text) || "siii".equals(text) || 
        		"ssi".equals(text) || "ssii".equals(text) || "se".equals(text) || "see".equals(text)
        		|| "seee".equals(text)){
            stateManager.processYes();
        }
        else{
            notUnderstand();
        }
    }
    
    public void setManager(StateManager manager){
        stateManager = manager;
    }
    
    @Override
    public void onRmsChanged(float rmsdB) {}
    
    public void selectSong(){
        currentSong = randomSong();
        initSongQuestion(currentSong);
    }
    
    private void initSongQuestion(final Cancion song) {
        Animation leftAnim = AnimationUtils.loadAnimation(this, R.animator.left_song_animation);
        final Animation rightAnim = AnimationUtils.loadAnimation(this, R.animator.right_song_animation);
        leftAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}
            @Override
            public void onAnimationRepeat(Animation animation) {}
            @Override
            public void onAnimationEnd(Animation animation) {
                soundName.setText(song.getNombre());
                soundView.startAnimation(rightAnim);
                soundView.setVisibility(View.VISIBLE);
                animateTalk();
                questionSound.start();
            }
        });
        if(soundView.getVisibility()==View.INVISIBLE){
            soundName.setText(song.getNombre());
            soundView.startAnimation(rightAnim);
            soundView.setVisibility(View.VISIBLE);
            animateTalk();
            questionSound.start();
        }
        else{
            soundView.startAnimation(leftAnim);
        }
    }
    
    public void playCurrentSong(){
    	final Context that = this;
        soundView.setVisibility(View.INVISIBLE);
        MediaPlayer song = currentSong.getSonido();
        try {
            song.prepare();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } 
        song.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.stop();
                cali.stop();
                mp.prepareAsync();
                user.addGifts(3);
                db.put("currentUser", user);
                Dialog dialog = new GiftPopup(that, user.getGifts(), GiftCount.THREE);
                dialog.show();
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
					@Override
					public void onDismiss(DialogInterface dialog) {
						selectSong();						
					}
				});
            }
        });
        animateDance();
        song.start();
    }

    private Cancion randomSong(){
        if(songs==null || songs.isEmpty()) songs = loader.getSongs();
        int index = rnd.nextInt(songs.size());
        Cancion song = songs.remove(index);
        return song;
    }
    
    public void end(){
    	final Context that = this;
        user.addGifts(1);
        db.put("currentUser", user);
        Dialog dialog = new GiftPopup(this, user.getGifts());
        dialog.show();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
			@Override
			public void onDismiss(DialogInterface dialog) {
				MediaPlayer sonidoCali = MediaPlayer.create(that, R.raw.despedida_canta_con_cali);
				animateHello();
				soundView.setVisibility(View.INVISIBLE);
				sonidoCali.setOnCompletionListener(new OnCompletionListener() {
					
					@Override
					public void onCompletion(MediaPlayer mp) {
						cali.stop();
						if (mp != null) {
							mp.stop();
							mp.release();
							
						}
						
						finish();   
						
					}
				});
				sonidoCali.start();
				
			}
		});
    }

    private void notUnderstand() {
        animateTalk();
        understand.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.stop();
                cali.stop();
                mp.prepareAsync();
                startVoiceRecognitionActivity();
            }
        });
        understand.start();
    }
    
    public void animateHello(){
    	cali.greet();
    }
    
    public void animateTalk(){
    	cali.talk();
    }
    
    public void animateDance(){
        cali.dance();
    }
}
