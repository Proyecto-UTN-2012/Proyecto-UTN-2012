package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.dibujaconcali;

import java.util.Random;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;
import org.utn.proyecto.helpful.integrart.integrar_t_android.R.string;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.comosehace.ComoSeHaceAndarActivity;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.conociendoacali.ConociendoACaliActivity;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.cuentos.CurrentCuentoActivity;
import org.utn.proyecto.helpful.integrart.integrar_t_android.domain.User;
import org.utn.proyecto.helpful.integrart.integrar_t_android.interfaces.VerifyCharacter;
import org.utn.proyecto.helpful.integrart.integrar_t_android.metrics.ActivityMetric;
import org.utn.proyecto.helpful.integrart.integrar_t_android.metrics.Metric;
import org.utn.proyecto.helpful.integrart.integrar_t_android.metrics.MetricsService;
import org.utn.proyecto.helpful.integrart.integrar_t_android.services.DataStorageService;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
public class DibujaConCaliActivity extends RoboActivity implements VerifyCharacter {
    
    private static final Random random = new Random();

    @InjectView(R.id.auto)
    ImageView auto;

    @InjectView(R.id.perro)
    ImageView perro;

    @InjectView(R.id.avion)
    ImageView avion;

    @InjectView(R.id.corazon)
    ImageView corazon;

    @InjectView(R.id.azar)
    ImageView azar;
    
    @Inject
    private DataStorageService db;
    
    @Inject
    private User user;
    
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

        if (corazon==null){
            showUnsupportMessage();
            return;
            
        }
        perro.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Metric metrica = new Metric(user, ActivityMetric.DIBUJA_CON_CALI, getResources().getString(R.string.metric_categoria_dibujaconcali),"perro");
                metricsService.sendMetric(metrica);
                startDibujo(R.drawable.perro);
            }
        });

        auto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Metric metrica = new Metric(user, ActivityMetric.DIBUJA_CON_CALI, getResources().getString(R.string.metric_categoria_dibujaconcali),"auto");
                metricsService.sendMetric(metrica);
                startDibujo(R.drawable.auto);
            }
        });

        avion.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Metric metrica = new Metric(user, ActivityMetric.DIBUJA_CON_CALI, getResources().getString(R.string.metric_categoria_dibujaconcali),"avion");
                metricsService.sendMetric(metrica);
                startDibujo(R.drawable.avion);
            }
        });

        corazon.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Metric metrica = new Metric(user, ActivityMetric.DIBUJA_CON_CALI, getResources().getString(R.string.metric_categoria_dibujaconcali),"corazon");
                metricsService.sendMetric(metrica);
                startDibujo(R.drawable.corazon);
            }
        });

        azar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int[] dibujos = new int[] { R.drawable.perro, R.drawable.auto,
                        R.drawable.corazon, R.drawable.avion };
                Metric metrica = new Metric(user, ActivityMetric.DIBUJA_CON_CALI, getResources().getString(R.string.metric_categoria_dibujaconcali),"azar");
                metricsService.sendMetric(metrica);
                startDibujo(dibujos[random.nextInt(4)]);
            }
        });

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


    private void displayCaliMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.dcc_no_esta_cali_titulo);
        builder.setMessage(R.string.dcc_no_esta_cali_mensaje);
        builder.setPositiveButton(R.string.dcc_no_esta_cali_si, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                executeConociendoCali();
            }
        });
        builder.setNegativeButton(R.string.dcc_no_esta_cali_no, new DialogInterface.OnClickListener() {
            
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        Dialog dialog = builder.create();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener(){
            @Override
            public void onDismiss(DialogInterface dialog) {
                finish();           
            }
            
        });
        dialog.show();
    }
    
    private void executeConociendoCali()
    {
        Intent intent =  new Intent(this,ConociendoACaliActivity.class);
        this.startActivity(intent);
    }

    public boolean isCaliSelected() {
       return db.contain(user.getUserName()+".cac_personaje_seleccionado");
    }

    private void showUnsupportMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.handPlayUnsupportTitle);
        builder.setMessage(R.string.handPlayUnsupportMessage);
        builder.setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
            
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        Dialog dialog = builder.create();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener(){
            @Override
            public void onDismiss(DialogInterface dialog) {
                finish();           
            }
            
        });
        dialog.show();
    }

    
    private void startDibujo(int dibujo) {
        Intent intent = new Intent(this, DibujoActivity.class);

        intent.putExtra("dibujo", dibujo);

        this.startActivity(intent);

    }

}
