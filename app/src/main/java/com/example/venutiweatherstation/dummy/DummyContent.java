package com.example.venutiweatherstation.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;

import venuti.weather.it.R;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

	/**
	 * An array of sample (dummy) items.
	 */
	public static List<DummyItem> ITEMS = new ArrayList<DummyItem>();

	/**
	 * A map of sample (dummy) items, by ID.
	 */
	public static Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();
	private static Context context;
	
	public DummyContent(Context c){
		this.context = c;
		ITEMS.clear();
		ITEM_MAP.clear();
		addItem(new DummyItem("1", c.getString(R.string.Current_Condition)));//"Current Condition"));
		addItem(new DummyItem("2", c.getString(R.string.Daily_Stats)));// "Daily Statistics"));
		addItem(new DummyItem("3", c.getString(R.string.Graphs)));//"Graphs"));
	}

	public static void addItem(DummyItem item) {
		ITEMS.add(item);
		ITEM_MAP.put(item.id, item);
	}

	/**
	 * A dummy item representing a piece of content.
	 */
	public static class DummyItem {
		public String id;
		public String content;

		public DummyItem(String id, String content) {
			this.id = id;
			this.content = content;
		}

		@Override
		public String toString() {
			return content;
		}
	}
}
