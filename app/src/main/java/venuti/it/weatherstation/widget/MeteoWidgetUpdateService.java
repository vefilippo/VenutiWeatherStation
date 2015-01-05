package venuti.it.weatherstation.widget;

import java.util.Random;
import java.util.concurrent.ExecutionException;

import venuti.weather.it.R;

import MeteoData.MeteoData;
import MeteoData.MeteoDataRetrieve;
import MeteoData.StaticParams;
import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

public class MeteoWidgetUpdateService extends Service {

	private static final String LOG = "venuti.it.weatherstation.widget";

	@Override
	public void onStart(Intent intent, int startId) {

		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this
				.getApplicationContext());

		int[] allWidgetIds = intent
				.getIntArrayExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);

		ComponentName thisWidget = new ComponentName(getApplicationContext(),
				MeteoWidgetProvider.class);
		int[] allWidgetIds2 = appWidgetManager.getAppWidgetIds(thisWidget);

		for (int widgetId : allWidgetIds) {
			/* Retrieve the updated meteodata object */
			ConnectivityManager c_manager = (ConnectivityManager) super.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo n_i = c_manager.getActiveNetworkInfo();

			MeteoData md = null;

			if((n_i != null) && (n_i.isConnectedOrConnecting())){
                // Toast removed, required only for debugging
				//Toast toast = Toast.makeText(super.getApplicationContext(), super.getText(R.string.wid_upd_toast), Toast.LENGTH_SHORT);
				//toast.show();
				AsyncTask<String, Integer, String> res = new MeteoDataRetrieve().execute(StaticParams.CurrentCondition);
				try {
					md = new MeteoData (res.get());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			RemoteViews remoteViews = new RemoteViews(this
					.getApplicationContext().getPackageName(),
					R.layout.widget_layout);
			// Set the widget text
			if (md != null){
				Log.w("MeteoPagnacco Widget",  md.getUpdateDay());
				remoteViews.setTextViewText(R.id.c_temp,
						Float.toString(md.get_cTemp()) + " °C");
				remoteViews.setTextViewText(R.id.c_Hum,
						Float.toString(md.get_cHum()) + " %");
				remoteViews.setTextViewText(R.id.c_WSpeed,
						Float.toString(md.get_cWindSpeed()) + " Km/hr");
				remoteViews.setTextViewText(R.id.c_WDir,md.get_cWindDirection());
			}

			// Register an onClickListener
			Intent clickIntent = new Intent(this.getApplicationContext(),
					MeteoWidgetProvider.class);

			clickIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
			clickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,
					allWidgetIds);

			PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, clickIntent,
					PendingIntent.FLAG_UPDATE_CURRENT);
			remoteViews.setOnClickPendingIntent(R.id.widget_layout, pendingIntent);
			appWidgetManager.updateAppWidget(widgetId, remoteViews);
		}
		stopSelf();

	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
