package venuti.it.weatherstation;

import venuti.weather.it.R;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * An activity representing a single Item detail screen. This activity is only
 * used on handset devices. On tablet-size devices, item details are presented
 * side-by-side with a list of items in a {@link venuti.it.weatherstation.ItemListActivity}.
 * <p>
 * This activity is mostly just a 'shell' activity containing nothing more than
 * a {@link venuti.it.weatherstation.ItemDetailFragment}.
 */
public class ItemDetailActivity extends FragmentActivity {

	private Menu menu;
	private int click_val;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_item_detail);

		// Show the Up button in the action bar.
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setDisplayShowTitleEnabled(true);
		//getActionBar().setTitle(R.string.title_item_detail);

		// savedInstanceState is non-null when there is fragment state
		// saved from previous configurations of this activity
		// (e.g. when rotating the screen from portrait to landscape).
		// In this case, the fragment will automatically be re-added
		// to its container so we don't need to manually add it.
		// For more information, see the Fragments API guide at:
		//
		// http://developer.android.com/guide/components/fragments.html
		//
		this.click_val = Integer.parseInt(getIntent()
					.getStringExtra(ItemDetailFragment.ARG_ITEM_ID));
		if (savedInstanceState == null) {
			// Create the detail fragment and add it to the activity
			// using a fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putString(ItemDetailFragment.ARG_ITEM_ID, getIntent()
					.getStringExtra(ItemDetailFragment.ARG_ITEM_ID));
			ItemDetailFragment fragment = new ItemDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.item_detail_container, fragment).commit();
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpTo(this,
					new Intent(this, ItemListActivity.class));
			return true;
		/*case R.id.Temp_Hum_graph:
	    	// Change graph to Temp_Hum graph!
			Bundle arguments = new Bundle();
			arguments.putString(ItemDetailFragment.ARG_ITEM_ID, getIntent()
					.getStringExtra(ItemDetailFragment.ARG_ITEM_ID));
			ItemDetailFragment fragment = new ItemDetailFragment(menu,1);
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.item_detail_container, fragment).commit();
	        return true;
	    case R.id.Bar_Wspeed_graph:
	    	// Change to Bar_Wspeed graph!
			arguments = new Bundle();
			arguments.putString(ItemDetailFragment.ARG_ITEM_ID, getIntent()
					.getStringExtra(ItemDetailFragment.ARG_ITEM_ID));
			fragment = new ItemDetailFragment(menu,2);
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.item_detail_container, fragment).commit();
	        return true;*/
        case R.id.refresh_button:
            /* Refresh */
            Bundle arguments = new Bundle();
            arguments.putString(ItemDetailFragment.ARG_ITEM_ID, getIntent()
                    .getStringExtra(ItemDetailFragment.ARG_ITEM_ID));
            ItemDetailFragment fragment = new ItemDetailFragment(menu);
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction().replace(R.id.item_detail_container, fragment).commit();
            return true;
        /*case R.id.about_button:
            // More info about the application!
            AboutFragment abt_fragment = new AboutFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.item_detail_container, abt_fragment).commit();
            return true;*/
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.layout.options_menu, menu);
        // Always show the refresh button
		MenuItem mi = menu.findItem(R.id.refresh_button);
		mi.setVisible(true);
        //Do not show ever the more graphs option
        mi = menu.findItem(R.id.more_graphs);
        mi.setVisible(false);

		if (click_val == 2){
			mi = menu.findItem(R.id.more_graphs);
			mi.setVisible(false);
            mi = menu.findItem(R.id.refresh_button);
            mi.setVisible(false);
		}
		return true;
	}
	
}
