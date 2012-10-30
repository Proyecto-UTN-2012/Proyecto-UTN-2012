package org.utn.proyecto.helpful.integrart.integrar_t_android.activities.conociendoacali;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

public class ConociendoCaliMessage extends Dialog {
    ConociendoACaliActivity currentActivity;
    int position;
    private DialogInterface.OnClickListener listenerPositive;
    private DialogInterface.OnClickListener listenerNegative;
    int currentPosition;
    
    public ConociendoCaliMessage(ConociendoACaliActivity activity,int position)
    {
        super (activity);
        
        currentPosition = position;
        currentActivity = activity;
        this.setContentView(R.layout.cac_dialog_message);
        listenerPositive = new DialogInterface.OnClickListener() {
            
            @Override
            public void onClick(DialogInterface dialog, int which) {
                currentActivity.WriteOnPreferenceCharacter(currentPosition);
                
            }
        };
        
        listenerNegative = new DialogInterface.OnClickListener() {
            
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                return;
            }
        };
    }
     
    public Dialog onCreateDialog(Bundle savedInstanceStat)
    {
     // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(currentActivity);
        //this.setContentView(R.layout.cac_dialog_message);
        builder.setMessage(R.string.cac_mensaje)
               .setPositiveButton(R.string.cac_si,this.listenerPositive)
               .setNegativeButton(R.string.cac_no,this.listenerNegative);
        // Create the AlertDialog object and return it
        return builder.create();
    }
    
    
    
    
}
