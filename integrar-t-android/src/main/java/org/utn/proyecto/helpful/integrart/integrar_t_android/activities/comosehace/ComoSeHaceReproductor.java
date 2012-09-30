package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.comosehace;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

@ContentView(R.layout.csh_reproductor)
public class ComoSeHaceReproductor extends RoboActivity {
    
    static public String VIDEO = "video";
    
    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        
        Intent intent =  new Intent(this,ComoSeHaceAndarActivity.class);
        this.startActivity(intent);
        
        super.onDestroy();
    }

    @InjectView(R.id.csh_reproductor_layout)
    private VideoView video;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        
        String pathToReproduce; //= "android.resource://" + getPackageName() +"/"+R.raw.assasin
        
        if (savedInstanceState==null)
        {
            Bundle extras = getIntent().getExtras();
            
            pathToReproduce = extras.getString(VIDEO);
            if (pathToReproduce!=null)
            {
                runVideo(pathToReproduce);
            }
        }
    }
    
    protected void runVideo(String path){
        video.setVideoURI(Uri.parse(path));
        MediaController media = new MediaController(this);
        video.setMediaController(media);
        video.requestFocus();
        video.start();
        
        video.setOnCompletionListener(new OnCompletionListener() {
            
            @Override
            public void onCompletion(MediaPlayer mp) {
                finish();
            }
        });
    }

    
}
