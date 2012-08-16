package org.utn.proyecto.helpful.integrart.integrar_t_android.MainMenu.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.utn.proyecto.helpful.integrart.integrar_t_android.R;

import roboguice.inject.InjectResource;

public class MainMenuItem {

    public static class MenuItem{

        public String id;
        public String content;
        public String activityDescription;

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
        
    }

    public static List<MenuItem> ITEMS = new ArrayList<MenuItem>();
    public static Map<String, MenuItem> ITEM_MAP = new HashMap<String, MenuItem>();
    
    @InjectResource(R.string.mam_item_cac) String itemNameCac;
    @InjectResource(R.string.mam_item_detail_cac) String itemDetailCac;
    
    @InjectResource(R.string.mam_item_hcc) String itemNameHcc;
    @InjectResource(R.string.mam_item_detail_hcc) String itemDetailHcc;
    
    @InjectResource(R.string.mam_item_dcc) String itemNameDcc;
    @InjectResource(R.string.mam_item_detail_dcc) String itemDetailDcc;
    
    @InjectResource(R.string.mam_item_ccc) String itemNameCcc;
    @InjectResource(R.string.mam_item_detail_ccc) String itemDetailCcc;
    
    @InjectResource(R.string.mam_item_ort) String itemNameOrt;
    @InjectResource(R.string.mam_item_detail_ort) String itemDetailOrt;
    
    @InjectResource(R.string.mam_item_csh) String itemNameCsh;
    @InjectResource(R.string.mam_item_detail_csh) String itemDetailCsh;
    
    @InjectResource(R.string.mam_item_pap) String itemNamePap;
    @InjectResource(R.string.mam_item_detail_pap) String itemDetailPap;
    
    @InjectResource(R.string.mam_item_jcc) String itemNameJcc;
    @InjectResource(R.string.mam_item_detail_jcc) String itemDetailJcc;
    
    @InjectResource(R.string.mam_item_cue) String itemNameCue;
    @InjectResource(R.string.mam_item_detail_cue) String itemDetailCue;
    
    @InjectResource(R.string.mam_item_hcd) String itemNameHcd;
    @InjectResource(R.string.mam_item_detail_hcd) String itemDetailHcd;
    
    @InjectResource(R.string.mam_item_jcm) String itemNameJcm;
    @InjectResource(R.string.mam_item_detail_jcm) String itemDetailJcm;
    
    public MainMenuItem()
    {
        addItem(new MenuItem("1",  itemNameCac ,itemDetailCac));
        addItem(new MenuItem("2", itemNameHcc,itemDetailHcc));
        addItem(new MenuItem("3", itemNameDcc ,itemDetailDcc));
        addItem(new MenuItem("4", itemNameCcc,itemDetailCcc));
        addItem(new MenuItem("5", itemNameOrt,itemDetailOrt));
        addItem(new MenuItem("6", itemNameCsh,itemDetailCsh));
        addItem(new MenuItem("7", itemNamePap,itemDetailPap));
        addItem(new MenuItem("8", itemNameJcc,itemDetailJcc));
        addItem(new MenuItem("9", itemNameCue,itemDetailCue));
        addItem(new MenuItem("10", itemNameHcd,itemDetailHcd));
        addItem(new MenuItem("11", itemNameJcm,itemDetailJcm));
    }

    private static void addItem(MenuItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }
}