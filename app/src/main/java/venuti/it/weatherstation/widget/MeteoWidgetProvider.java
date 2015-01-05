package venuti.it.weatherstation.widget;

import java.util.Random;

import venuti.weather.it.R;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class MeteoWidgetProvider extends AppWidgetProvider {

	private static final String ACTION_CLICK = "ACTION_CLICK";

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {

		// Get all ids
		ComponentName thisWidget = new ComponentName(context, MeteoWidgetProvider.class);
		int[] allWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget);
		// Build the intent to call the service
		Intent intent = new Intent(context.getApplicationContext(),
				MeteoWidgetUpdateService.class);
		intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, allWidgetIds);

		// Update the widgets via the service
		context.startService(intent);
	}
}
