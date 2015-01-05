package MeteoData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;

public class MeteoDataRetrieve extends AsyncTask<String, Integer, String> {
	
	private ProgressDialog progressDialog;
	private Activity activity;
	public String result;
	
	public MeteoDataRetrieve (Activity activity){
		this.activity = activity;	
	}
	public MeteoDataRetrieve(){
		this.activity = null;
	}
	
    @Override
    protected void onPreExecute()
    {
    	/*if (this.activity != null){
        	progressDialog= ProgressDialog.show(activity, "Downloading Data","Please Wait...", true);	
    	}*/

        //do initialization of required objects objects here                
    }  

		private static String convertStreamToString(InputStream is) {
	    /*
	     * To convert the InputStream to String we use the BufferedReader.readLine()
	     * method. We iterate until the BufferedReader return null which means
	     * there's no more data to read. Each line will appended to a StringBuilder
	     * and returned as String.
	     */
	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();

	    String line = null;
	    try {
	        while ((line = reader.readLine()) != null) {
	            sb.append(line + "\n");
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            is.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    return sb.toString();
	}

	public MeteoDataRetrieve(String url){
		
		//System.out.println(url);
		HttpClient httpclient = new DefaultHttpClient();

	    // Prepare a request object
	    HttpGet httpget = new HttpGet(url); 

	    // Execute the request
	    HttpResponse response;
	    	//System.out.println("before");
	        try {
				response = httpclient.execute(httpget);
				//System.out.println("after");
		        
		        Log.i("Venuti.net",response.getStatusLine().toString());

		        // Get hold of the response entity
		        HttpEntity entity = response.getEntity();
		        // If the response does not enclose an entity, there is no need
		        // to worry about connection release
		        
		        //System.out.println(entity);

		        if (entity != null) {

		            // A Simple JSON Response Read
		            InputStream instream = entity.getContent();
		            this.result= convertStreamToString(instream);
		            //System.out.println(result);
		            // now you have the string representation of the HTML request
		            instream.close();
		        }
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	}
	
	@Override
	protected String doInBackground(String... params) {
		HttpClient httpclient = new DefaultHttpClient();

	    // Prepare a request object
	    HttpGet httpget = new HttpGet(params[0]); 

	    // Execute the request
	    HttpResponse response;
	        try {
				response = httpclient.execute(httpget);
		        
		        Log.i("Venuti.net",response.getStatusLine().toString());

		        // Get hold of the response entity
		        HttpEntity entity = response.getEntity();
		        // If the response does not enclose an entity, there is no need
		        // to worry about connection release

		        if (entity != null) {

		            // A Simple JSON Response Read
		            InputStream instream = entity.getContent();
		            String result= convertStreamToString(instream);
		            //System.out.println(result);
		            // now you have the string representation of the HTML request
		            instream.close();
		            return result;
		        }
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
		return null;
	}
	
	protected void onPostExecute(String result)
    {
		/*if(this.activity != null){
	        progressDialog.dismiss();
		}*/
    };
	
}
