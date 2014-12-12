
	package com.example.potholer;


import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

	public class AppLocationService extends Service implements LocationListener {

		protected LocationManager locationManager;
		Location location;
		Context cxt;
		private static final long MIN_DISTANCE_FOR_UPDATE = 1;
		private static final long MIN_TIME_FOR_UPDATE = 1000;

		public AppLocationService(Context context) {
			cxt=context;
			locationManager = (LocationManager) context
					.getSystemService(LOCATION_SERVICE);
			
			
		}

		public Location getLocation(String provider) {
			
			Log.d("APPL Service","Inside getLocation");
			
		Toast.makeText(cxt, "GetLocation", Toast.LENGTH_SHORT).show();
			if (locationManager.isProviderEnabled(provider)) {
				locationManager.requestLocationUpdates(provider,
						MIN_TIME_FOR_UPDATE, MIN_DISTANCE_FOR_UPDATE, this);
				if (locationManager != null) {
					location = locationManager.getLastKnownLocation(provider);
					return location;
				}
			}
			return null;
		}

		@Override
		public void onLocationChanged(Location location) {
			
			Toast.makeText(cxt, "Loc Changed", Toast.LENGTH_SHORT).show();
			Log.d("APPLSERVICE", "Callled On Loc hanged");
			
			
		}

		@Override
		public void onProviderDisabled(String provider) {
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

		@Override
		public IBinder onBind(Intent arg0) {
			return null;
		}
		
		
		
		
		
		
		
		
		
		
		
		private class GetContacts extends AsyncTask<Void, Void, String> {
			
			
			String url;

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				// Showing progress dialog
		
			}

			@Override
			protected String doInBackground(Void... arg0) {
				// Creating service handler class instance
				ServiceHandler sh = new ServiceHandler();

				// Making a request to url and getting response
			String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

				Log.d("Response: ", "> " + jsonStr);

//				if (jsonStr != null) {
//					try {
//						JSONObject jsonObj = new JSONObject(jsonStr);
//						
//						// Getting JSON Array node
//						contacts = jsonObj.getJSONArray(TAG_CONTACTS);
	//
//						// looping through All Contacts
//						for (int i = 0; i < contacts.length(); i++) {
//							JSONObject c = contacts.getJSONObject(i);
//							
//							String id = c.getString(TAG_ID);
//							String name = c.getString(TAG_NAME);
//							String email = c.getString(TAG_EMAIL);
//							String address = c.getString(TAG_ADDRESS);
//							String gender = c.getString(TAG_GENDER);
	//
//							// Phone node is JSON Object
//							JSONObject phone = c.getJSONObject(TAG_PHONE);
//							String mobile = phone.getString(TAG_PHONE_MOBILE);
//							String home = phone.getString(TAG_PHONE_HOME);
//							String office = phone.getString(TAG_PHONE_OFFICE);
	//
//							// tmp hashmap for single contact
//							HashMap<String, String> contact = new HashMap<String, String>();
	//
//							// adding each child node to HashMap key => value
//							contact.put(TAG_ID, id);
//							contact.put(TAG_NAME, name);
//							contact.put(TAG_EMAIL, email);
//							contact.put(TAG_PHONE_MOBILE, mobile);
	//
//							// adding contact to contact list
//							contactList.add(contact);
//						}
//					} catch (JSONException e) {
//						e.printStackTrace();
//					}
//				} else {
//					Log.e("ServiceHandler", "Couldn't get any data from the url");
//				}

				return jsonStr;
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				// Dismiss the progress dialog
			
				
				Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
				Log.d("RAGHAV", result);

				
				
			}

		}

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

	}
	
	

