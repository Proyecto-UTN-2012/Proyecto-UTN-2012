package org.utn.proyecto.helpful.integrart.integrar_t_android.menu.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.calendar.LaunchOrganizarEvent;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.cantaconcali.LaunchCantaConCaliEvent;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.comosehace.LaunchComoSeHaceEvent;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.conociendoacali.LaunchConociendoACaliEvent;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.cuentos.LaunchCuentosEvent;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.dibujaconcali.LaunchDibujaConCaliEvent;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.hablaconcali.LaunchHablaConCaliEvent;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.handplay.LaunchHandPlayEvent;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.pictogramas.LaunchPictogramEvent;
import org.utn.proyecto.helpful.integrart.integrar_t_android.activities.testactivity.LaunchTestActivityEvent;
import org.utn.proyecto.helpful.integrart.integrar_t_android.events.Event;

import android.app.Activity;
import android.graphics.drawable.Drawable;

public class MainMenuItem {

    public static class MenuItem{

        public String id;
        public String content;
        public String activityDescription;
        private Activity act;
        public Event<?> event;
        public Drawable bckground;
        
        public Activity getActivity() {
            return act;
        }

        public void setActivity(Activity act) {
            this.act = act;
        }

        public String getActivityDescription() {
            return activityDescription;
        }

        public void setActivityDescription(String activityDescription) {
            this.activityDescription = activityDescription;
        }

        public MenuItem(String id, String content) {
            this.id = id;
            this.content = content;
        }
        
        
        public MenuItem(String id, String content, String description, Event<?> event, Drawable bckground) {
            this.id = id;
            this.content = content;
            this.activityDescription = description;
            this.event = event;
            this.bckground =bckground;

        }
        
        @Override
        public String toString() {
            return content;
        }
        
        public MenuItem(Activity activity){
            act = activity;
        }
        
    }

    public static List<MenuItem> ITEMS = new ArrayList<MenuItem>();
    public static Map<String, MenuItem> ITEM_MAP = new HashMap<String, MenuItem>();
    public static Activity ACTIVITY_REFERENCE;
    
    public static void InitializeMenu(){
        
        if (ITEM_MAP.size() == 0){
        addItem(new MenuItem("1", ACTIVITY_REFERENCE.getResources().getString(R.string.mam_item_cac),ACTIVITY_REFERENCE.getResources().getString(R.string.mam_item_detail_cac), new LaunchConociendoACaliEvent(ACTIVITY_REFERENCE) ,ACTIVITY_REFERENCE.getResources().getDrawable(R.drawable.menu_cac)  ));
        addItem(new MenuItem("2", ACTIVITY_REFERENCE.getResources().getString(R.string.mam_item_hcc),ACTIVITY_REFERENCE.getResources().getString(R.string.mam_item_detail_hcc), new LaunchHablaConCaliEvent(ACTIVITY_REFERENCE)    ,ACTIVITY_REFERENCE.getResources().getDrawable(R.drawable.menu_hcc)  ));
        addItem(new MenuItem("3", ACTIVITY_REFERENCE.getResources().getString(R.string.mam_item_dcc),ACTIVITY_REFERENCE.getResources().getString(R.string.mam_item_detail_dcc), new LaunchDibujaConCaliEvent(ACTIVITY_REFERENCE)   ,ACTIVITY_REFERENCE.getResources().getDrawable(R.drawable.menu_dcc) ));
        addItem(new MenuItem("4", ACTIVITY_REFERENCE.getResources().getString(R.string.mam_item_ccc),ACTIVITY_REFERENCE.getResources().getString(R.string.mam_item_detail_ccc), new LaunchCantaConCaliEvent(ACTIVITY_REFERENCE), ACTIVITY_REFERENCE.getResources().getDrawable(R.drawable.menu_ccc) ));
        addItem(new MenuItem("5", ACTIVITY_REFERENCE.getResources().getString(R.string.mam_item_ort),ACTIVITY_REFERENCE.getResources().getString(R.string.mam_item_detail_ort), new LaunchOrganizarEvent(ACTIVITY_REFERENCE),  ACTIVITY_REFERENCE.getResources().getDrawable(R.drawable.menu_ort) ));
        addItem(new MenuItem("6", ACTIVITY_REFERENCE.getResources().getString(R.string.mam_item_csh),ACTIVITY_REFERENCE.getResources().getString(R.string.mam_item_detail_csh), new LaunchComoSeHaceEvent(ACTIVITY_REFERENCE), ACTIVITY_REFERENCE.getResources().getDrawable(R.drawable.menu_clu) ));
        addItem(new MenuItem("7", ACTIVITY_REFERENCE.getResources().getString(R.string.mam_item_cue),ACTIVITY_REFERENCE.getResources().getString(R.string.mam_item_detail_cue), new LaunchCuentosEvent(ACTIVITY_REFERENCE), ACTIVITY_REFERENCE.getResources().getDrawable(R.drawable.menu_cuen) ));
        addItem(new MenuItem("8", ACTIVITY_REFERENCE.getResources().getString(R.string.mam_item_hcd),ACTIVITY_REFERENCE.getResources().getString(R.string.mam_item_hcd), new LaunchPictogramEvent(ACTIVITY_REFERENCE), ACTIVITY_REFERENCE.getResources().getDrawable(R.drawable.menu_picto)));
        addItem(new MenuItem("9", ACTIVITY_REFERENCE.getResources().getString(R.string.mam_item_jcm),ACTIVITY_REFERENCE.getResources().getString(R.string.mam_item_detail_jcm), new LaunchHandPlayEvent(ACTIVITY_REFERENCE), ACTIVITY_REFERENCE.getResources().getDrawable(R.drawable.menu_jcm) ));
        
        //addItem(new MenuItem("10", ACTIVITY_REFERENCE.getResources().getString(R.string.mam_item_test),ACTIVITY_REFERENCE.getResources().getString(R.string.mam_item_detail_test), new LaunchTestActivityEvent(ACTIVITY_REFERENCE) ));
        //addItem(new MenuItem("11", ACTIVITY_REFERENCE.getResources().getString(R.string.mam_item_pap),ACTIVITY_REFERENCE.getResources().getString(R.string.mam_item_detail_pap), new LaunchPictogramEvent(ACTIVITY_REFERENCE) ));
        //addItem(new MenuItem("12", ACTIVITY_REFERENCE.getResources().getString(R.string.mam_item_jcc),ACTIVITY_REFERENCE.getResources().getString(R.string.mam_item_detail_jcc), new LaunchPictogramEvent(ACTIVITY_REFERENCE) ));
        }
    }

    private static void addItem(MenuItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }
}
