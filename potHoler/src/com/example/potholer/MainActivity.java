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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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
	
	
	
	ConnectionDetector cd;
	String BASE_URL="http://192.168.1.139:7010/getRoadQuality";
	AppLocationService appLocationService;
	GoogleMap googleMap;
	LocationRequest mLocationRequest;
	Location nwLocation;
	LocationClient mLocationClient;
	Context cntxt;
	
	public static LatLng LC;
	
	
	Intent inSer;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
	setContentView(R.layout.activity_path_google_map);
	
	
	cd = new ConnectionDetector(getApplicationContext());
	
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
	 mLocationRequest.setInterval(10000);
	 
	 
 mLocationClient = new LocationClient(this, this, this);
	
//	mLocationClient = new LocationClient
	
//	
//	appLocationService = new AppLocationService(this);
//	
//	
//	Location ab=Find_location();
//	
	inSer=new Intent(getApplicationContext(), Accelero.class);
 
	
	
	}
	
	
	
	
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		
		mLocationClient.connect();
		
		
		
		
	}
	
	
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		
		 mLocationClient.disconnect();
		
		
		
		stopService(inSer);
		
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
		
		Toast.makeText(getApplicationContext(), "Failed to Connect to Internet", Toast.LENGTH_SHORT).show();
		
		
	}













	@Override
	public void onConnected(Bundle connectionHint) {
		// TODO Auto-generated method stub
		
		 mLocationClient.requestLocationUpdates(mLocationRequest,new com.google.android.gms.location.LocationListener() {
			
			@Override
			public void onLocationChanged(Location location) {
				// TODO Auto-generated method stub
				
				
				
				
				if (!cd.isConnectingToInternet()) {
					// Internet Connection is not present
					Toast.makeText(getApplicationContext(), "Not connected to internet", Toast.LENGTH_SHORT).show();
					// stop executing code by return
				
					
					
					
					
				}
				
				else
				{
					
					
					new AsyncTaskActivity2().execute(""+location.getLatitude(),""+location.getLongitude());
					
				}
				
				
				
				
				
				
				Log.d("Raghav","raghav");
			}
		});
		
		LC= new LatLng(mLocationClient.getLastLocation().getLatitude(), mLocationClient.getLastLocation().getLongitude());
		 
		 googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LC, 16));
	
	
		 startService(inSer);
		 
		 
	
	}


	










	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}







	private class AsyncTaskActivity2 extends AsyncTask<String, Void, String> {

		
		
		
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
				        nameValuePairs.add(new BasicNameValuePair("lat", params[0]));
				        nameValuePairs.add(new BasicNameValuePair("lng", params[1]));
				      
				        
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
					
					
				Log.d("raghav", "done posting");
					
				
				googleMap.clear();
				
				if (jsonStr != null) {
				try {
					JSONObject jsonObj = new JSONObject(jsonStr);
					
					// Getting JSON Array node
					JSONArray data = jsonObj.getJSONArray("roadQualityData");

					// looping through All Contacts
					for (int i = 0; i < data.length(); i++) {
						JSONObject c = data.getJSONObject(i);
						
						String lat1 = c.getString("lat");
						double lat = Double.parseDouble(lat1);
						String logn1 = c.getString("long");
						double logn = Double.parseDouble(logn1);
						String inten = c.getString("intensity");
						
						Log.d("extracted", ""+lat);
						Log.d("extracted", ""+logn);
						
					
						
						CircleOptions circleOptions = new CircleOptions()
					    .center(new LatLng(lat, logn))
					    .radius(2)
					    .fillColor(16711680);

					// Get back the mutable Circle
					Circle circle = googleMap.addCircle(circleOptions);
						
//						googleMap.addMarker(new MarkerOptions()
//						.position(new LatLng(lat, logn))
//						.title("Pot Hole!")
//						.icon(BitmapDescriptorFactory
//						.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
//						

						// tmp hashmap for single contact
//						HashMap<String, String> contact = new HashMap<String, String>();
//
//						// adding each child node to HashMap key => value
//						contact.put(TAG_ID, id);
//						contact.put(TAG_NAME, name);
//						contact.put(TAG_EMAIL, email);
//						contact.put(TAG_PHONE_MOBILE, mobile);
//
//						// adding contact to contact list
//						contactList.add(contact);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				Log.e("ServiceHandler", "Couldn't get any data from the url");
			}
				
				
				
				
				googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLocationClient.getLastLocation().getLatitude(), mLocationClient.getLastLocation().getLongitude()), 18));
				
				
				
				
					//shareRegidTask = null;
					//Toast.makeText(getApplicationContext(), result,
						//	Toast.LENGTH_LONG).show();
				}
		    
		    
		    
		}
	
	
	
	
	
	
	

}
