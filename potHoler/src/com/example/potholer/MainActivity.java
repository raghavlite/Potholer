package com.example.potholer;




import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements   GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener {
	
	
	AppLocationService appLocationService;
	GoogleMap googleMap;
	LocationRequest mLocationRequest;
	Location nwLocation;
	LocationClient mLocationClient;
	Context cntxt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
	setContentView(R.layout.activity_path_google_map);
	
	
	
	
	SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager()
			.findFragmentById(R.id.map);
	googleMap = fm.getMap();
	
	
	if(googleMap==null)
	{
		Toast.makeText(getApplicationContext(), "Install Google Maps ", Toast.LENGTH_SHORT).show();
		
		
		
		return;
	}
	cntxt=getApplicationContext();
	
	
	googleMap.setMyLocationEnabled(true);
	googleMap.isMyLocationEnabled();
	

	// Enable / Disable zooming controls
	googleMap.getUiSettings().setZoomControlsEnabled(true);

	// Enable / Disable my location button
	googleMap.getUiSettings().setMyLocationButtonEnabled(true);

	// Enable / Disable Compass icon
	googleMap.getUiSettings().setCompassEnabled(true);

	// Enable / Disable Rotate gesture
	googleMap.getUiSettings().setRotateGesturesEnabled(true);

	// Enable / Disable zooming functionality
	googleMap.getUiSettings().setZoomGesturesEnabled(true);
	
	
	
	
	
	 mLocationRequest = LocationRequest.create();
	
	 
	 mLocationRequest.setPriority(
             LocationRequest.PRIORITY_HIGH_ACCURACY);
	 
//	 mLocationRequest.setSmallestDisplacement(10);
	 mLocationRequest.setInterval(1000);
	 
	 
 mLocationClient = new LocationClient(this, this, this);
	
//	mLocationClient = new LocationClient
	
//	
//	appLocationService = new AppLocationService(this);
//	
//	
//	Location ab=Find_location();
//	
	
	
	
	
	}
	
	
	
	
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		mLocationClient.connect();
		
	}
	
	
	
	
	
	
	
	
	

	
	public Location Find_location()
	{
		
		
		
//		 nwLocation = appLocationService
//					.getLocation(LocationManager.NETWORK_PROVIDER);
//			
			
			Location gpsLocation = appLocationService
					.getLocation(LocationManager.GPS_PROVIDER);
//
//			if (gpsLocation != null) {
//				double latitude = gpsLocation.getLatitude();
//				double longitude = gpsLocation.getLongitude();
//				Toast.makeText(
//						getApplicationContext(),
//						"Mobile Location (GPS): \nLatitude: " + latitude
//								+ "\nLongitude: " + longitude,
//						Toast.LENGTH_LONG).show();
//			} else {
//				showSettingsAlert("GPS");
//			}
			
			if (nwLocation != null) {
//				double latitude = nwLocation.getLatitude();
//				double longitude = nwLocation.getLongitude();
				///Toast.makeText(
					//	getApplicationContext(),
					//	"Mobile Location (NW): \nLatitude: " + latitude
						//		+ "\nLongitude: " + longitude,
						//Toast.LENGTH_LONG).show();
				return nwLocation;
				
				
			} else {
				showSettingsAlert("NETWORK");
			}
			return null;

		
		
		
		
		
	}
	
	
	
	
	
	
	

	
	
	
	public void showSettingsAlert(String provider) {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

		alertDialog.setTitle(provider + " SETTINGS");

		alertDialog
				.setMessage(provider + " is not enabled! Want to go to settings menu?");

		alertDialog.setPositiveButton("Settings",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						startActivity(intent);
					}
				});

		alertDialog.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				});

		alertDialog.show();
	}













	@Override
	public void onConnectionFailed(ConnectionResult result) {
		// TODO Auto-generated method stub
		
	}













	@Override
	public void onConnected(Bundle connectionHint) {
		// TODO Auto-generated method stub
		
		 mLocationClient.requestLocationUpdates(mLocationRequest,new com.google.android.gms.location.LocationListener() {
			
			@Override
			public void onLocationChanged(Location location) {
				// TODO Auto-generated method stub
				
				
				
				new AsyncTaskActivity2().execute(""+location.getLatitude(),""+location.getLongitude());
				
				
				Log.d("Raghav","raghav");
			}
		});
		
		
	}


	










	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}







	private class AsyncTaskActivity2 extends AsyncTask<String, Void, String> {

		String url="http://192.168.1.139:7010/getRoadQuality";
		
		
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
				    HttpPost httppost = new HttpPost(url);
	
				    try {
				        // Add your data
				    	String[] rv={params[0],params[1]};
				    	
				    	String str1 = Arrays.toString(rv);  
				    	
				    	
				    	
				    	
				    	
				        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
				        nameValuePairs.add(new BasicNameValuePair("coordinate", str1));
				      
				        
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
				protected void onPostExecute(String result) {
					
					
				Log.d("raghav", "done posting");
					
					//shareRegidTask = null;
					//Toast.makeText(getApplicationContext(), result,
						//	Toast.LENGTH_LONG).show();
				}
		    
		    
		    
		}
	
	
	
	
	
	
	

}
