package MeteoData;

public class DailyObject {
	
	private String max_val;
	private String max_time;
	private String min_val;
	private String min_time;
	private String direction;
	
	public DailyObject (String one, String two, String three, String four){
		max_val = one;
		max_time = two;
		min_val = three;
		min_time = four;
	}
	
	public DailyObject(String one, String two){
		max_val = one;
		max_time = two;
	}

	public DailyObject(String one, String two, String three){
		max_val = one;
		max_time = two;
		setDirection(three);
	}
	
	public String getMaxTime(){
		return max_time;
	}
	
	public String getMaxVal(){
		return max_val;
	}
	
	public String getMinTime(){
		return min_time;
	}
	
	public String getMinVal(){
		return min_val;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

}
