package MeteoData;

import java.util.Date;

public class DetailMeteoObject {
	private String w_dir;
	private Float w_speed, temp, hum, rr, rain, bar;
	private Date meas_date;
	
	public DetailMeteoObject(){
		
	}

	public String getW_dir() {
		return w_dir;
	}

	public Float getW_speed() {
		return w_speed;
	}

	public Float getTemp() {
		return temp;
	}

	public Float getHum() {
		return hum;
	}

	public Float getRr() {
		return rr;
	}

	public Float getRain() {
		return rain;
	}

	public Date getMeas_date() {
		return meas_date;
	}

	public void setW_dir(String w_dir) {
		this.w_dir = w_dir;
	}

	public void setW_speed(Float w_speed) {
		this.w_speed = w_speed;
	}

	public void setTemp(Float temp) {
		this.temp = temp;
	}

	public void setHum(Float hum) {
		this.hum = hum;
	}

	public void setRr(Float rr) {
		this.rr = rr;
	}

	public void setRain(Float rain) {
		this.rain = rain;
	}

	public void setMeas_date(Date date) {
		this.meas_date = date;
	}

	public Float getBar() {
		return bar;
	}

	public void setBar(Float bar) {
		this.bar = bar;
	}
	
	public String toString(){
		String res = "";
		res += "Date: " + this.meas_date + "\n";
		res += "Temp: " + this.temp + " °C\n";
		return res;
	}

}
