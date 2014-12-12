package com.example.potholer;



import info.androidhive.jsonparsing.MainActivity;
import info.androidhive.jsonparsing.ServiceHandler;

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
				
				Toast.makeText(getApplicationContext(), "Location"+location.getLatitude(),Toast.LENGTH_SHORT).show();
				
				Log.d("Raghav","raghav");
			}
		});
		
		
	}


	










	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}







	private class GetContacts extends AsyncTask<Void, Void, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
	
		}

		@Override
		protected String doInBackground(Void... arg0) {
			// Creating service handler class instance
			ServiceHandler sh = new ServiceHandler();

			// Making a request to url and getting response
			String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

			Log.d("Response: ", "> " + jsonStr);

//			if (jsonStr != null) {
//				try {
//					JSONObject jsonObj = new JSONObject(jsonStr);
//					
//					// Getting JSON Array node
//					contacts = jsonObj.getJSONArray(TAG_CONTACTS);
//
//					// looping through All Contacts
//					for (int i = 0; i < contacts.length(); i++) {
//						JSONObject c = contacts.getJSONObject(i);
//						
//						String id = c.getString(TAG_ID);
//						String name = c.getString(TAG_NAME);
//						String email = c.getString(TAG_EMAIL);
//						String address = c.getString(TAG_ADDRESS);
//						String gender = c.getString(TAG_GENDER);
//
//						// Phone node is JSON Object
//						JSONObject phone = c.getJSONObject(TAG_PHONE);
//						String mobile = phone.getString(TAG_PHONE_MOBILE);
//						String home = phone.getString(TAG_PHONE_HOME);
//						String office = phone.getString(TAG_PHONE_OFFICE);
//
//						// tmp hashmap for single contact
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
//					}
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//			} else {
//				Log.e("ServiceHandler", "Couldn't get any data from the url");
//			}

			return jsonStr;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
	
			
			Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
			Log.d("RAGHAV", result);

		}

	}

	
	
	
	
	
	

}
