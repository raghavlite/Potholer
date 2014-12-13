package com.example.potholer;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.location.LocationClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class Accelero extends Service implements SensorEventListener{

	
	

	private SensorManager senSensorManager;
	private Sensor senAccelerometer;
	
	private long lastUpdate;
	private long curTime ;
	private float SHAKE_THRESHOLD=40;
	
	float last_x = 0;
	float last_y = 0;
	float last_z = 0;
	
	ArrayList<String> RLatLongs;
	
	private String BASE_URL = "http://192.168.1.139:7010/sendRoadQualityInfo";
	
	ConnectionDetector cd;
	
	
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return super.onStartCommand(intent, flags, startId);
	}
	
	
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		
		senSensorManager = (SensorManager) getSystemService(getApplicationContext().SENSOR_SERVICE);
	    senAccelerometer = senSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

	    senSensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);
		
	    RLatLongs=new ArrayList<String>();
	    
	    cd = new ConnectionDetector(getApplicationContext());
	}
	
	
	
	
	
	@Override
	@Deprecated
	public void onStart(Intent intent, int startId) {
		// TODO Auto-generated method stub
		super.onStart(intent, startId);
		
		
		Toast.makeText(getApplicationContext(), "Service", Toast.LENGTH_SHORT).show();
		
		
		Log.d("Service Tag", "On Started");
		
		
	}
	
	
	
	

	
	
	
	
	
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		 senSensorManager.unregisterListener(this);
		
		
		
	}





	@Override
	public void onSensorChanged(SensorEvent sensorEvent) {
		// TODO Auto-generated method stub
		
		Sensor mySensor = sensorEvent.sensor;
		 
	    if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
	 
	    	
	    	float x = sensorEvent.values[0];
	        float y = sensorEvent.values[1];
	        float z = sensorEvent.values[2];
	        long diffTime = 0;
	    	
	        curTime = System.currentTimeMillis();
	       
	        if ((curTime - lastUpdate) > 1000) {
	        	
	        	
	             diffTime = (curTime - lastUpdate);
	            lastUpdate = curTime;
	          
	            
	            
				
				float speed = Math.abs(x + y + z - last_x - last_y - last_z)/ (diffTime)*10000;
				
				
				Log.v("SPEED", ""+speed);
				
		        
	            if (speed > SHAKE_THRESHOLD)
	            {
	            
	            	
	            	RLatLongs.add(""+MainActivity.LC.latitude + ";"+MainActivity.LC.longitude);
	            	
	            	
	            	
	            	if(RLatLongs.size()>10)
	            	{
	            	
	            		Log.i("LATLONGS", ""+RLatLongs);
	            		
	            		
	            		if (!cd.isConnectingToInternet()) {
	            			// Internet Connection is not present
//	            			Toast.makeText(getApplicationContext(), "Not connected to internet", Toast.LENGTH_SHORT).show();
	            			// stop executing code by return
	            			
	            		}
	            		
	            		else
	            		{
	            			
	            			Log.d("REPORTING", ""+RLatLongs);
	            			new PotReporter().execute(""+RLatLongs);
	            			RLatLongs.clear();
	            			
	            		}
	            		
	            	
	            	
	            	}
	            	
	            	
	            	
	            	
	            	
	            	
	            }
	 
	            last_x = x;
	            last_y = y;
	            last_z = z;
	            
	            
	            
	        }
	        
	        
	       
        }
	        
	        
	        
	        
	    }
		
		
		
	





	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}	
	
	
	
	
	
	
	
	
	
	
	
	private class PotReporter extends AsyncTask<String, Void, String> {

		
		
		
//		Activity mActivity;
//		    public AsyncTaskActivity2(Activity activity)
//		    {
//		         super();
//		         this.mActivity=activity;
//		    }
//		    
		    
		    
		    
				


				@Override
			protected String doInBackground(String... params) {
				
				String response = null;
				
				
				HttpEntity httpEntity = null;
				HttpResponse httpResponse = null;
				
				 HttpClient httpclient = new DefaultHttpClient();
				    HttpPost httppost = new HttpPost(BASE_URL);
	
				    try {
				        // Add your data
				    	
				    	
				        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
				        nameValuePairs.add(new BasicNameValuePair("pots", params[0]));
				        
				      
				        
				        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	
				        // Execute HTTP Post Request
				        httpResponse = httpclient.execute(httppost);
				        
				        httpEntity = httpResponse.getEntity();
						response = EntityUtils.toString(httpEntity);
						
						Log.d("response", response);
						
				        
				    } catch (ClientProtocolException e) {
				        // TODO Auto-generated catch block
				    	e.printStackTrace();
				    } catch (IOException e) {
				        // TODO Auto-generated catch block
				    	e.printStackTrace();
				    }
				
				
				
			
				
				return response;
	
			}
		    
		    
				@Override
				protected void onPostExecute(String jsonStr) {
					
					
				Log.d("raghav", "done Reporting");
					
				
				
				}
		    
		    
		    
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
