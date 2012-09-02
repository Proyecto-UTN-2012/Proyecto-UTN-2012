package org.utn.proyecto.helpful.integrart.integrar_t_android.menu.item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;

import android.app.Activity;

public class MainMenuItem {

    public static class MenuItem{

        public String id;
        public String content;
        public String activityDescription;
        private Activity act;

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
        
        public MenuItem(String id, String content, String description) {
            this.id = id;
            this.content = content;
            this.activityDescription = description;
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
        addItem(new MenuItem("1", ACTIVITY_REFERENCE.getResources().getString(R.string.mam_item_cac),ACTIVITY_REFERENCE.getResources().getString(R.string.mam_item_detail_cac) ));
        addItem(new MenuItem("2", ACTIVITY_REFERENCE.getResources().getString(R.string.mam_item_hcc),ACTIVITY_REFERENCE.getResources().getString(R.string.mam_item_detail_hcc) ));
        addItem(new MenuItem("3", ACTIVITY_REFERENCE.getResources().getString(R.string.mam_item_dcc),ACTIVITY_REFERENCE.getResources().getString(R.string.mam_item_detail_dcc) ));
        addItem(new MenuItem("4", ACTIVITY_REFERENCE.getResources().getString(R.string.mam_item_ccc),ACTIVITY_REFERENCE.getResources().getString(R.string.mam_item_detail_ccc) ));
        addItem(new MenuItem("5", ACTIVITY_REFERENCE.getResources().getString(R.string.mam_item_ort),ACTIVITY_REFERENCE.getResources().getString(R.string.mam_item_detail_ort) ));
        addItem(new MenuItem("6", ACTIVITY_REFERENCE.getResources().getString(R.string.mam_item_csh),ACTIVITY_REFERENCE.getResources().getString(R.string.mam_item_detail_csh) ));
        addItem(new MenuItem("7", ACTIVITY_REFERENCE.getResources().getString(R.string.mam_item_pap),ACTIVITY_REFERENCE.getResources().getString(R.string.mam_item_detail_pap) ));
        addItem(new MenuItem("8", ACTIVITY_REFERENCE.getResources().getString(R.string.mam_item_jcc),ACTIVITY_REFERENCE.getResources().getString(R.string.mam_item_detail_jcc) ));
        addItem(new MenuItem("9", ACTIVITY_REFERENCE.getResources().getString(R.string.mam_item_cue),ACTIVITY_REFERENCE.getResources().getString(R.string.mam_item_detail_cue) ));
        addItem(new MenuItem("10", ACTIVITY_REFERENCE.getResources().getString(R.string.mam_item_hcd),ACTIVITY_REFERENCE.getResources().getString(R.string.mam_item_hcd) ));
        addItem(new MenuItem("11", ACTIVITY_REFERENCE.getResources().getString(R.string.mam_item_jcm),ACTIVITY_REFERENCE.getResources().getString(R.string.mam_item_detail_jcm) ));
    }

    private static void addItem(MenuItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }
}
