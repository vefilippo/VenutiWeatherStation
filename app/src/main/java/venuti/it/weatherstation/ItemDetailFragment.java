package venuti.it.weatherstation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.zip.Inflater;

import venuti.it.weatherstation.graph.GraphData;
import venuti.weather.it.R;

import MeteoData.MeteoData;
import MeteoData.MeteoDataRetrieve;
import MeteoData.StaticParams;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.example.venutiweatherstation.dummy.DummyContent;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;

/**
 * A fragment representing a single Item detail screen. This fragment is either
 * contained in a {@link venuti.it.weatherstation.ItemListActivity} in two-pane mode (on tablets) or a
 * {@link venuti.it.weatherstation.ItemDetailActivity} on .
 */
@SuppressLint("ValidFragment")
public class ItemDetailFragment extends Fragment {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";
	private ViewGroup container;
	private Menu menu;
	private int graph_id = 1;

	/**
	 * The dummy content this fragment is presenting.
	 */
	private DummyContent.DummyItem mItem;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public ItemDetailFragment(Menu menu) {
		this.menu = menu;
	}
	public ItemDetailFragment(Menu menu, int graph_id) {
		this.menu = menu;
		this.graph_id = graph_id;
	}
	public ItemDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_ITEM_ID)) {
			// Load the dummy content specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
			mItem = DummyContent.ITEM_MAP.get(getArguments().getString(
					ARG_ITEM_ID));
		}
		if (menu != null){
			MenuItem mi = menu.findItem(R.id.refresh_button);
			mi.setVisible(true);
			mi = menu.findItem(R.id.more_graphs);
			mi.setVisible(false);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//getActivity().
		this.container = container;
		/*View rootView = getActivity().getLayoutInflater().inflate(R.layout.loading, container, false);
		getActivity().setContentView(rootView);*/
		View rootView = null;

		if (mItem == null) {
			/* Something went wrong, go back to main screen */
			Intent detailIntent = new Intent(getActivity()
					.getApplicationContext(), ItemListActivity.class);
			startActivity(detailIntent);
		} else {
			/* Depending on the selected entry we need to have different layouts */

			if (mItem.id.equals("1")) {
				/* We are going to Generate the Current Screen! */
				getActivity().getActionBar().setTitle(
						R.string.Current_Condition);
				rootView = createCurrentView(inflater, container);
			}
			if (mItem.id.equals("2")) {
				/* We are going to Generate the Current Screen! */
				getActivity().getActionBar().setTitle(R.string.Under_Dev);
				rootView = inflater.inflate(R.layout.on_work, container, false);
			}
			if (mItem.id.equals("3")) {
				/* We are going to Generate the Graph Screen! */
				getActivity().getActionBar().setTitle(R.string.Graphs);
				rootView = inflater.inflate(R.layout.simple_graph, container, false);
				rootView = createGraphView(inflater, container);
			}
		}
		return rootView;
	}

	/* This function creates the GraphView object in either landscape or portrait */
	private View createGraphView(LayoutInflater inflater, ViewGroup container) {
		View rootView = null;

		if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			/* Use the portrait layout */
			rootView = inflater
					.inflate(R.layout.simple_graph, container, false);
			rootView = graphRefresh(rootView);
		} else {
			/* Use the landscape layout */
			rootView = inflater
					.inflate(R.layout.simple_graph, container, false);
			rootView = graphRefresh(rootView);

		}
		return rootView;
	}

	/* This procedure refreshed the graph upon screen orientation change */
	private View graphRefresh(View rootView) {

		ConnectivityManager c_manager = (ConnectivityManager) getActivity()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo n_i = c_manager.getActiveNetworkInfo();

		if ((n_i != null) && (n_i.isConnectedOrConnecting())) {

			new GraphData(getActivity().getApplicationContext(), getActivity(), this, container, menu, 30).
			execute();

			rootView = getActivity().getLayoutInflater().inflate(R.layout.loading, container, false);

		} else {
			Toast toast = Toast.makeText(getActivity(),
					"Check your internet connectivity", Toast.LENGTH_LONG);
			toast.show();
		}

		return rootView;
	}

	private View createCurrentView(LayoutInflater inflater, ViewGroup container) {
		/* Thie method is used to generate the Current Condition Layout */
		View rootView = null;
		if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			/* Use the portrait layout */
			rootView = inflater
					.inflate(R.layout.current_port, container, false);
			rootView = currRefresh(rootView);
		} else {
			/* Use the landscape layout */
			rootView = inflater
					.inflate(R.layout.current_land, container, false);
			rootView = currRefresh(rootView);

		}
		return rootView;

	}

	private View UpdateGraphView() {
		/* This method is used to generate the Current Condition Layout */
		View rootView = null;
		if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			/* Use the portrait layout */
			rootView = getActivity().getLayoutInflater().inflate(
					R.layout.simple_graph, container, false);
			rootView = graphRefresh(rootView);
		} else {
			/* Use the landscape layout */
			rootView = getActivity().getLayoutInflater().inflate(
					R.layout.simple_graph, container, false);
			rootView = graphRefresh(rootView);
		}
		//mDialog.dismiss();
		return rootView;

	}


	private View UpdateCurrentView() {
		/* This method is used to generate the Current Condition Layout */
		View rootView = null;
		if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
			/* Use the portrait layout */
			rootView = getActivity().getLayoutInflater().inflate(
					R.layout.current_port, container, false);
			rootView = currRefresh(rootView);
		} else {
			/* Use the landscape layout */
			rootView = getActivity().getLayoutInflater().inflate(
					R.layout.current_land, container, false);
			rootView = currRefresh(rootView);
		}
		return rootView;

	}

	public View currRefresh(View rootView) {

		ConnectivityManager c_manager = (ConnectivityManager) getActivity()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo n_i = c_manager.getActiveNetworkInfo();

		if ((n_i != null) && (n_i.isConnectedOrConnecting())) {

			TextView tv;
			AsyncTask<String, Integer, String> res = new MeteoDataRetrieve(
					getActivity()).execute(StaticParams.CurrentCondition);
			try {
				MeteoData md = new MeteoData(res.get());

				/*
				 * TODO: Retrieving additional information such as: # Rain Rate
				 * # Wind Chill
				 */
				/*
				 * res = new
				 * MeteoDataRetrieve().execute(StaticParams.CurrentConditionIntegral
				 * );
				 */
				md.includeDaily(new MeteoDataRetrieve(getActivity()).execute(
						StaticParams.DailyStats).get());
				// res = new
				// MeteoDataRetrieve().execute(StaticParams.DailyStats);
				// System.out.println("Daily:\n" + res.get());

				// Setting Last Update
				tv = (TextView) rootView.findViewById(R.id.Cur_Last_Upd);
				tv.setText(getString(R.string.Update_Day) + " "
						+ md.getUpdateDay() + " "
						+ getString(R.string.Update_Time) + " "
						+ md.getUpdateTime());

				// Current Temperature
				tv = (TextView) rootView.findViewById(R.id.Current_Temp);
				tv.setText(Float.toString(md.get_cTemp()) + " °C");

				// Max Temperature
				tv = (TextView) rootView.findViewById(R.id.Cur_Max_Temp);
				tv.setText(md.get_dTemp().getMaxVal() + " °C");
				// Min Temperature
				tv = (TextView) rootView.findViewById(R.id.Cur_Min_Temp);
				tv.setText(md.get_dTemp().getMinVal() + " °C");

				// Current Humidity
				tv = (TextView) rootView.findViewById(R.id.Cur_Hum);
				tv.setText(Float.toString(md.get_cHum()) + " %");

				// Current Wind Direction & Speed
				tv = (TextView) rootView.findViewById(R.id.Cur_WSpeed);
				tv.setText(Float.toString(md.get_cWindSpeed()) + " Km/hr ");
				tv = (TextView) rootView.findViewById(R.id.Cur_WDir);
				tv.setText(md.get_cWindDirection());

				// Atmospheric Pressure
				tv = (TextView) rootView.findViewById(R.id.Cur_Bar);
				tv.setText(Float.toString(md.get_cBar()) + " hPa");

				// Current Rain Rate
				tv = (TextView) rootView.findViewById(R.id.Rain_Rate);
				if (md.get_cRainRate() == 0){
					tv.setText("---"
							+ " mm/hr");
				}else{
					tv.setText(Float.toString(md.get_cRainRate())
							+ " mm/hr");
				}

				// Current DewPoint
				/*
				 * tv = (TextView) findViewById(R.id.dew);
				 * tv.setText(Float.toString(md.get_cDewPoint()) + " �C");
				 */

				// Current Wind Chill
				/*
				 * tv = (TextView) findViewById(R.id.wind_chill);
				 * tv.setText(Float.toString(md.get_cWindChill()) + " �C");
				 */

				// Current Heat Index
				/*
				 * tv = (TextView) findViewById(R.id.heat_index);
				 * tv.setText(Float.toString(md.get_cHeatIndex()) + " �C");
				 */

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else {
			Toast toast = Toast.makeText(getActivity(),
					"Check your internet connectivity", Toast.LENGTH_LONG);
			toast.show();
		}

		return rootView;

	}

	public void onConfigurationChanged(Configuration newConfig) {
		/* Executed in case of orientation change */
		super.onConfigurationChanged(newConfig);

        Intent detailIntent = new Intent(getActivity()
                .getApplicationContext(), ItemListActivity.class);
        startActivity(detailIntent);

		View rootView = null;

		// Checks the orientation of the screen to update visualization
		if (mItem.id.equals("1")) {
			/* We are going to Re-Generate the Current Screen! */
			rootView = UpdateCurrentView();
		}
		if (mItem.id.equals("2")) {
			/* We are going to Re-Generate the Current Screen! */
		}
		if (mItem.id.equals("3")) {
			/* We are going to Re-Generate the Current Screen! */
			rootView = UpdateGraphView();
		}
		getActivity().setContentView(rootView);

	}
}
