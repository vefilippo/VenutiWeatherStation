package MeteoData;

public class StaticParams {
	
	public static String CurrentCondition = "http://188.40.99.85/meteo/current.php";
	/* this php file is going to retrieve current values for additional measurements (not included in previous php file) */
	public static String CurrentConditionIntegral = "http://188.40.99.85/meteo/file.php?type=update?last=";
	public static String DailyStats = "http://188.40.99.85/meteo/file.php?type=maxmin";
	public static String FullDailyData ="http://188.40.99.85/meteo/file.php?type=full";

}
