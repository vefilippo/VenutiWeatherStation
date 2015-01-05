package MeteoData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;

public class MeteoData {

	private Date last_update;	
	private float c_temp, c_hum, c_bar, c_dewp, c_ws, c_rr, c_wchill, c_hi, d_r;

	private String c_wd, ts;

	private DailyObject d_temp, d_hum, d_bar, d_ws, d_rr;
	private LinkedList<DetailMeteoObject> detail;

	public MeteoData(){
		this.detail = new LinkedList<DetailMeteoObject>();
	}

	public void addMeteoDataDetail(String res){
		/* Implement the logic to extract DailyMeteoObject from the output String 
		 * 
		 * Data is presented as a string as:
		 * 
		 * 	datetime::temp::rain:rr::hum::bar::wspeed::wdir
		 * 
		 * and repeats
		 * 
		 * */

		String split[] = res.split("::");
		int elements = 8;
		//System.out.println( split.length / elements);
		for (int i = 0; i < split.length / elements; i++){
			/* One measurement */
			DetailMeteoObject mdo = new DetailMeteoObject();
			String measure = "";
			for (int j = 0; j < elements; j++){
				switch (j){
				case 0: try {
					mdo.setMeas_date(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(split[elements*i+j]));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
				case 1: mdo.setTemp(Float.parseFloat(split[elements*i+j]));
				break;
				case 2: mdo.setRain(Float.parseFloat(split[elements*i+j]));
				break;
				case 3: mdo.setRr(Float.parseFloat(split[elements*i+j]));
				break;
				case 4: mdo.setHum(Float.parseFloat(split[elements*i+j]));
				break;
				case 5: mdo.setBar(Float.parseFloat(split[elements*i+j]));
				break;
				case 6: mdo.setW_speed(Float.parseFloat(split[elements*i+j]));
				break;
				case 7: mdo.setW_dir(split[elements*i+j]);
				break;
				}
				//System.out.println(mdo.toString());
				detail.add(mdo);
			}
			//System.out.println(measure);
		}

	}

	/* This constructor is used for the new update logic */
	public MeteoData(String cCond){
		String split[] = cCond.split("::");
		c_temp = Float.parseFloat(split[0]);
		c_hum = Float.parseFloat(split[1]);
		c_ws = Float.parseFloat(split[2]);
		c_wd = split[3];
		c_bar = Float.parseFloat(split[4]);
		c_rr = Float.parseFloat(split[5]);
		try {
			last_update = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(split[6]);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/* daily extract format: $min_bar_time[] = $row["MIN_BAR_TIME"];
			$min_bar[] = $row["MIN_BAR"];
			$max_bar_time[] = $row["MAX_BAR_TIME"];
			$max_bar[] = $row["MAX_BAR"];
			$max_hum_time[] = $row["MAX_HUM_TIME"];
			$max_hum[] = $row["MAX_HUM"];
			$min_hum_time[] = $row["MIN_HUM_TIME"];
			$min_hum[] = $row["MIN_HUM"];
			$max_wind_time[] = $row["WIND_MAX_TIME"];
			$max_wind_dir[] = $row["WIND_MAX_DIR"];
			$max_wind_speed[] = $row["WIND_MAX_SPEED"];
			$max_temp_time[] = $row["MAX_TEMP_TIME"];
			$max_temp[] = $row["MAX_TEMP"];
			$min_temp_time[] = $row["MIN_TEMP_TIME"];
			$min_temp[] = $row["MIN_TEMP"]; */
	public void includeDaily(String daily){
		String split[] = daily.split("::");
		d_bar = new DailyObject(split[3],split[2], split[1],split[0]);
		d_hum = new DailyObject(split[5], split[4],split[7], split[6]);
		d_ws = new DailyObject(split[10], split[8], split[9]);
		d_temp = new DailyObject(split[12], split[11],split[14].trim(), split[13]);
		/* TODO: daily rain rate */
	}

	public Date getDate(){
		return this.last_update;
	}


	public MeteoData(Date date){
		last_update = date;
	}

	public void set_cTemp(float value){
		c_temp = value;
	}

	public void set_cHum(float value){
		c_hum = value;
	}

	public void set_cBar(float value){
		c_bar = value;
	}

	public void set_cDewP(float value){
		c_dewp = value;
	}

	public void set_cWindSpeed(float value){
		c_ws = value;
	}

	public void set_cWinDir(String value){
		c_wd = value;
	}

	public void set_cRainRate(float value){
		c_rr = value;
	}

	public void set_cWindChill(float value){
		c_wchill = value;
	}

	public void set_cHeatIndex(float value){
		c_hi = value;
	}

	public void set_dRain(float value){
		d_r = value;
	}

	public void set_dTemp(DailyObject d){
		d_temp = d;
	}

	public void set_dHum(DailyObject d){
		d_hum = d;
	}

	public void set_dBar(DailyObject d){
		d_bar = d;
	}

	public void set_dWindSpeed(DailyObject d){
		d_ws = d;
	}

	public void set_dRainRate(DailyObject d){
		d_rr = d;
	}

	public float get_cTemp(){
		return c_temp;
	}

	public float get_cHum(){
		return c_hum;
	}

	public float get_cBar(){
		return c_bar;
	}

	public float get_cDewPoint(){
		return c_dewp;
	}

	public float get_cWindSpeed(){
		return c_ws;
	}

	public String get_cWindDirection(){
		return c_wd;
	}

	public float get_cRainRate(){
		return c_rr;
	}

	public float get_cWindChill(){
		return c_wchill;
	}

	public float get_cHeatIndex(){
		return c_hi;
	}

	public float get_dRain(){
		return d_r;
	}

	public DailyObject get_dTemp(){
		return d_temp;
	}

	public DailyObject get_dHum(){
		return d_hum;
	}

	public DailyObject get_dBar(){
		return d_bar;
	}

	public DailyObject get_dWindSpeed(){
		return d_ws;
	}

	public DailyObject get_dRainRate(){
		return d_rr;
	}

	public String getUpdate(){
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat hour_format = new SimpleDateFormat("HH:mm");

		return format.format(this.last_update) + " at " + hour_format.format(this.last_update);
	}

	public String getUpdateDay(){
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

		return format.format(this.last_update);
	}

	public String getUpdateTime(){
		SimpleDateFormat hour_format = new SimpleDateFormat("HH:mm");

		return hour_format.format(this.last_update);
	}

	public String get_testString(){
		return ts;
	}
	public void set_testString(String s){
		ts = s;
	}

	public LinkedList<DetailMeteoObject> getDetail() {
		return detail;
	}

    public LinkedList<DetailMeteoObject> getAvgDetail(int avg_input) {
        // Same as getDetail method with the difference that it will return not all the raw data but will return an average
        // value between avg_input measurements
        LinkedList<DetailMeteoObject> dmo_list = new LinkedList<DetailMeteoObject>();
        int data_size = detail.size();
        int k = 0;
        float avg_bar = 0;
        float avg_temp = 0;
        float avg_hum = 0;
        float avg_wspeed = 0;
        String wdir = "";
        for (int i = 0; i < data_size; i++) {
            avg_bar += detail.get(i).getBar();
            avg_temp += detail.get(i).getTemp();
            avg_hum += detail.get(i).getHum();
            avg_wspeed += detail.get(i).getW_speed();
            if (avg_input == 1){
                wdir = "";
            };
            if (k == avg_input - 1){
                // We need to calculate the average and store it in a single meteo object detail
                avg_bar = avg_bar / avg_input;
                avg_temp = avg_temp / avg_input;
                avg_hum = avg_hum / avg_input;
                avg_wspeed = avg_wspeed / avg_input;
                DetailMeteoObject dmo = new DetailMeteoObject();
                dmo.setBar(avg_bar);
                dmo.setTemp(avg_temp);
                dmo.setHum(avg_hum);
                dmo.setW_speed(avg_wspeed);
                dmo.setMeas_date(detail.get(i).getMeas_date());
                if (avg_input == 1){
                    dmo.setW_dir(detail.get(i).getW_dir());
                }
                dmo_list.add(dmo);

                // reset averages & Counter
                avg_bar = 0;
                avg_temp = 0;
                avg_hum = 0;
                avg_wspeed = 0;
                k=0;
            }else{
                k++;
            }

        }
        return dmo_list;
    }

	public void setDetail(LinkedList<DetailMeteoObject> detail) {
		this.detail = detail;
	}

}
