package venuti.it.weatherstation.graph;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Legend;
import com.github.mikephil.charting.utils.XLabels;
import com.github.mikephil.charting.utils.YLabels;

import android.graphics.Color;

import venuti.it.weatherstation.ItemDetailFragment;
import venuti.weather.it.R;
import MeteoData.DetailMeteoObject;
import MeteoData.MeteoData;
import MeteoData.MeteoDataRetrieve;
import MeteoData.StaticParams;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class GraphData extends AsyncTask<Void, Integer, Void> {

	/* 
     * This AsyncTask retrieves the data of the last 24 hours and generates the appropriate graphs.
	 * 
	 * */

    MeteoData md;
    Context context;
    Activity activity;
    ItemDetailFragment view;
    ViewGroup container;
    int avg_meas;
    Menu menu;
    LinkedList<LineData> ld_list;
    LinkedList<PieData> pd_list;

    public GraphData(Context context, Activity act, ItemDetailFragment itemDetailFragment, ViewGroup c, Menu menu, int avg_meas) {
        this.context = context;
        this.activity = act;
        this.view = itemDetailFragment;
        this.container = c;
        this.menu = menu;
        this.avg_meas = avg_meas;
    }

    @Override
    protected void onPostExecute(Void res) {
		/* 
		 * Once data has been retrieved, we need to display the graphs
		 *  
		 *  */
        activity.findViewById(R.id.progressBar2).setVisibility(View.INVISIBLE);
        activity.findViewById(R.id.graph_container).setVisibility(View.VISIBLE);

        // Temperature Graph
        LineChart layout = (LineChart) activity.findViewById(R.id.graph_1);
        TextView tv = (TextView) activity.findViewById(R.id.graph_1_title);
        tv.setText(R.string.Temp_graph);
        LineData ld = ld_list.get(0);
        SetLineLayout(layout, ld);

        // Humidity Graph
        layout = (LineChart) activity.findViewById(R.id.graph_2);
        tv = (TextView) activity.findViewById(R.id.graph_2_title);
        tv.setText(R.string.Hum_graph);
        ld = ld_list.get(1);
        SetLineLayout(layout, ld);

        // Pressure Graph
        layout = (LineChart) activity.findViewById(R.id.graph_3);
        tv = (TextView) activity.findViewById(R.id.graph_3_title);
        tv.setText(R.string.Bar_graph);
        ld = ld_list.get(2);
        SetLineLayout(layout, ld);

        // Wind Speed Graph
        layout = (LineChart) activity.findViewById(R.id.graph_4);
        tv = (TextView) activity.findViewById(R.id.graph_4_title);
        tv.setText(R.string.Wspeed_graph);
        ld = ld_list.get(3);
        SetLineLayout(layout, ld);

        // Wind Direction Pie Chart
        PieChart pie_layout = (PieChart) activity.findViewById(R.id.graph_5);
        tv = (TextView) activity.findViewById(R.id.graph_5_title);
        tv.setText(R.string.WDir_graph);
        PieData pd = pd_list.get(0);
        SetPieLayout(pie_layout, pd);

    }

    @Override
    protected Void doInBackground(Void... params) {
		/* Retrieve the data and create the needed objects */
        ConnectivityManager c_manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo n_i = c_manager.getActiveNetworkInfo();

        if ((n_i != null) && (n_i.isConnectedOrConnecting())) {

            MeteoDataRetrieve mdr = new MeteoDataRetrieve(StaticParams.FullDailyData);
            this.md = new MeteoData();
            this.md.addMeteoDataDetail(mdr.result);
            this.ld_list = getChartData();
            this.pd_list = getPieChartData();

        } else {
            Toast toast = Toast.makeText(context,
                    "Check your internet connectivity", Toast.LENGTH_LONG);
            toast.show();
        }
        return null;
    }

    private void SetPieLayout(PieChart pc, PieData pd){
        // Method that defines the common Layout of a PieChart

        pc.setDrawYValues(true);
        pc.setDrawCenterText(false);
        pc.setDrawHoleEnabled(false);
        pc.setRotationAngle(0);
        // draws the corresponding description value into the slice
        pc.setDrawXValues(true);
        // enable rotation of the chart by touch
        pc.setRotationEnabled(true);
        // display percentage values
        pc.setUsePercentValues(true);
        pc.animateXY(1500, 1500);
        pc.highlightValues(null);
        pc.setDescription("");
        pc.setValueTextSize(15f);

        pc.setData(pd);

        // Set the Legend
        Legend l = pc.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART_CENTER);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(5f);
        l.setTextSize(13f);
        l.setTextColor(Color.WHITE);
    }

    private void SetLineLayout(LineChart lc, LineData ld){
        /*  Method that defines the common layout of a LineChart */

        lc.setDescription("");
        lc.setData(ld);
        lc.setDrawXLabels(true);
        lc.setDrawYLabels(true);
        lc.setDrawLegend(false);
        lc.setYRange(ld.getYMin() - 1, ld.getYMax() + 1, true);
        lc.setDrawYValues(false);
        lc.setDrawGridBackground(false);
        XLabels xlabels = lc.getXLabels();
        xlabels.setTextColor(Color.WHITE);
        YLabels ylabels = lc.getYLabels();
        ylabels.setTextColor(Color.WHITE);
        lc.animateX(3000);

        MyMarkerView mv = new MyMarkerView(context, R.layout.marker_view);
        mv.setOffsets(-mv.getMeasuredWidth() / 2, -mv.getMeasuredHeight());
        // set the marker to the chart
        lc.setMarkerView(mv);
    }

    public LinkedList<DetailMeteoObject> getGraphData(int avg_meas) {

        LinkedList<DetailMeteoObject> dmo = md.getAvgDetail(avg_meas);
        return dmo;

    }

    private class WindDir{
        String w_dir;
        int measurements;
        double pct;

        public WindDir(String w_dir, int measurements){
            this.w_dir = w_dir;
            this.measurements = measurements;
        }

        @Override
        public boolean equals(Object other){
            if (getClass() != other.getClass()) return false;
            WindDir wdir_obj = (WindDir) other;
            return (this.w_dir == wdir_obj.w_dir)? true : false;
        }

        public void addMeas(){
            this.measurements++;
        }

        public void UpdatePct(int all_meas){
            this.pct = ((double)this.measurements / all_meas)*100;
        }
    }

    private LinkedList<PieData> getPieChartData(){
        LinkedList<PieData> pd_list = new LinkedList<PieData>();
        ArrayList<Entry> vals = new ArrayList<Entry>();
        ArrayList<WindDir> wdir_list = new ArrayList<WindDir>();
        ArrayList<String> xVals = new ArrayList<String>(); // X axis data
        LinkedList<DetailMeteoObject> dmo = this.getGraphData(1); // We want to have all the data for the pie chart not averages
        int measurements = 0;
        for (int j = 0; j< dmo.size(); j++){
            // We need to get the list of possible directions from the dmo object
            WindDir wd = new WindDir(dmo.get(j).getW_dir(),1);
            // Let's exclude all measurements with direction ---
            if (!wd.w_dir.equals("---")) {
                boolean found = false;
                int i = 0;
                // first element, should always be added with no checks
                if (wdir_list.size() == 0) {
                    wdir_list.add(wd);
                }
                //compare all entries with the wdir_list object and if a matching element is found, increase the measurement value
                while (!found && i < wdir_list.size()){
                    if (wd.w_dir.equals(wdir_list.get(i).w_dir)) {
                        wdir_list.get(i).addMeas();
                        found = true;
                        measurements++;
                    }
                    i++;
                }
                // if not matching is found, add the new entry
                if (!found){
                    wdir_list.add(wd);
                    measurements++;
                }
            }

        }

        // now we have all the raw data. We need to polish the data to include the pct and provide as output only top 5 pcts
        // Create the "bulk other"
        WindDir wd = new WindDir("Other",0);
        Iterator<WindDir> it = wdir_list.iterator();
        while(it.hasNext()){
            WindDir wd_el = it.next();
            wd_el.UpdatePct(measurements);
            if (wd_el.pct < 6){
                //Let's add this to the "Other" bulk element and remove it from the wdir_list
                wd.measurements = wd.measurements + wd_el.measurements;
                it.remove();
            }
        }

        // Other element has been created, add it if has measurements > 2
        if (wd.measurements > 1 ) {
            wdir_list.add(wd);
        }

        for (int j = 0; j < wdir_list.size(); j++){
            Entry e = new Entry(Float.parseFloat(Integer.toString(wdir_list.get(j).measurements)), j);
            vals.add(e);
            xVals.add(wdir_list.get(j).w_dir);
        }
        PieDataSet pds = new PieDataSet(vals, ""); // Wind Direction Pie Data Set

        // Set colors for slicies of pie
        pds.setColor(ColorTemplate.getHoloBlue());

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        pds.setColors(colors);

        PieData pd = new PieData(xVals, pds);
        pd_list.add(pd);

        return pd_list;

    }

    private LinkedList<LineData> getChartData() {
        /* Creates the needed data structures for the graphs creation */
        LinkedList<LineData> ld_list = new LinkedList<LineData>();
        ArrayList<Entry> vals = new ArrayList<Entry>(); // temperature graph
        ArrayList<Entry> vals_hum = new ArrayList<Entry>(); // humidty graph
        ArrayList<Entry> vals_hpa = new ArrayList<Entry>(); // atmospheric pressure graph
        ArrayList<Entry> vals_wspeed = new ArrayList<Entry>(); // wind speed graph
        ArrayList<String> xVals = new ArrayList<String>(); // X axis data
        LinkedList<DetailMeteoObject> dmo = this.getGraphData(this.avg_meas);
        for (int j = 0; j < dmo.size(); j++) {
            // for every measurement in the dailymeteoobject we have to populate and Entry object representing the data
            Entry e = new Entry(Float.parseFloat(Double.toString(dmo.get(j).getTemp())), j);
            Entry e_hum = new Entry(Float.parseFloat(Double.toString(dmo.get(j).getHum())), j);
            Entry e_hpa = new Entry(Float.parseFloat(Double.toString(dmo.get(j).getBar())), j);
            Entry e_wspeed = new Entry(Float.parseFloat(Double.toString(dmo.get(j).getW_speed())), j);
            vals.add(e);
            vals_hum.add(e_hum);
            vals_hpa.add(e_hpa);
            vals_wspeed.add(e_wspeed);
            xVals.add(new SimpleDateFormat("HH:mm").format(dmo.get(j).getMeas_date())); // X axis elements are timestamps reformatted to HH:MM format
        }
        LineDataSet lds = new LineDataSet(vals, "Temperature");
        LineDataSet lds_hum = new LineDataSet(vals_hum, "Humidity");
        LineDataSet lds_hpa = new LineDataSet(vals_hpa, "Atmospheric Pressure");
        LineDataSet lds_wspeed = new LineDataSet(vals_wspeed, "Wind Speed");

        ld_list.add(getLineDataSet(lds, xVals));
        ld_list.add(getLineDataSet(lds_hum, xVals));
        ld_list.add(getLineDataSet(lds_hpa, xVals));
        ld_list.add(getLineDataSet(lds_wspeed, xVals));

        return ld_list;

    }

    private LineData getLineDataSet(LineDataSet lds, ArrayList<String> xVals){

        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        lds.setDrawCircles(false);
        lds.setColor(Color.BLUE);
        lds.setLineWidth(1.5f);
        lds.setHighLightColor(Color.CYAN);
        dataSets.add(lds);

        LineData ld = new LineData(xVals, dataSets);
        dataSets.add(lds);

        return ld;

    }


}
