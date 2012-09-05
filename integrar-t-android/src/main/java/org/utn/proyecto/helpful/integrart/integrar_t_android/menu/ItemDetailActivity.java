package org.utn.proyecto.helpful.integrart.integrar_t_android.menu;

import org.utn.proyecto.helpful.integrart.integrar_t_android.events.EventBus;

import roboguice.activity.RoboFragmentActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import com.google.inject.Inject;

public class ItemDetailActivity extends RoboFragmentActivity {

	@Inject
	private EventBus bus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.mam_fragment_item_detail);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putString(ItemDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(ItemDetailFragment.ARG_ITEM_ID));
            ItemDetailFragment fragment = new ItemDetailFragment();
            fragment.setArguments(arguments);
            fragment.setBus(bus);
            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, ItemListActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
