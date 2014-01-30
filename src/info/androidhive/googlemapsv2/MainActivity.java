package info.androidhive.googlemapsv2;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import tools.googlemap.Constants;
import tools.googlemap.MapUtil;
import android.app.Activity;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MainActivity extends Activity implements LocationListener {

	// Google Map
	private GoogleMap googleMap;
	RequestQueue requestQueue;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		try {

			// Loading map
			initilizeMap();
			// Changing map type
			googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
			// googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
			// googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
			// googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
			// googleMap.setMapType(GoogleMap.MAP_TYPE_NONE);

			// Showing / hiding your current location

			// Enable / Disable zooming controls
			googleMap.getUiSettings().setZoomControlsEnabled(false);

			// Enable / Disable my location button
			googleMap.getUiSettings().setMyLocationButtonEnabled(true);

			// Enable / Disable Compass icon
			googleMap.getUiSettings().setCompassEnabled(true);

			// Enable / Disable Rotate gesture
			googleMap.getUiSettings().setRotateGesturesEnabled(true);

			// Enable / Disable zooming functionality
			googleMap.getUiSettings().setZoomGesturesEnabled(true);

			double latitude = 17.385044;
			double longitude = 78.486671;

			// lets place some 10 random markers
			for (int i = 0; i < 10; i++) {
				// random latitude and logitude
				double[] randomLocation = createRandLocation(latitude,
						longitude);

				// Adding a marker
				MarkerOptions marker = new MarkerOptions().position(
						new LatLng(randomLocation[0], randomLocation[1]))
						.title("Hello Maps " + i);

				Log.e("Random", "> " + randomLocation[0] + ", "
						+ randomLocation[1]);

				// changing marker color
				if (i == 0)
					marker.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
				if (i == 1)
					marker.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
				if (i == 2)
					marker.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_CYAN));
				if (i == 3)
					marker.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
				if (i == 4)
					marker.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
				if (i == 5)
					marker.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
				if (i == 6)
					marker.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_RED));
				if (i == 7)
					marker.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
				if (i == 8)
					marker.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET));
				if (i == 9)
					marker.icon(BitmapDescriptorFactory
							.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));

				googleMap.addMarker(marker);

				// Move the camera to last position with a zoom level
				if (i == 9) {
					CameraPosition cameraPosition = new CameraPosition.Builder()
							.target(new LatLng(randomLocation[0],
									randomLocation[1])).zoom(15).build();

					googleMap.animateCamera(CameraUpdateFactory
							.newCameraPosition(cameraPosition));
				}
			}

			// new DelayedCall().delayedCall(3000, new CallBack() {
			//
			// public void onCallBack(Object... args) {
			// // TODO Auto-generated method stub
			// Log.e("", "calling back");
			//
			// LocationManager lm = (LocationManager)
			// getSystemService(Context.LOCATION_SERVICE);
			//
			// lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
			// 0, 0, MainActivity.this);
			// googleMap.setMyLocationEnabled(true);
			// }
			// }, null);

//			new DirectionsRequest(false, "anchal", "trivandrum",
//					new ResponseListner() {
//
//						@Override
//						public void onResponse(String response,
//								Exception exception) {
//							// TODO Auto-generated method stub
//							System.out.println("Response is " + response);
//						}
//					});
			makeRequests(false, "anchal", "trivandrum");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		initilizeMap();
	}

	/**
	 * function to load map If map is not created it will create it for you
	 * */
	private void initilizeMap() {
		if (googleMap == null) {
			googleMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.map)).getMap();

			// check if map is created successfully or not
			if (googleMap == null) {
				Toast.makeText(getApplicationContext(),
						"Sorry! unable to create maps", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	/*
	 * creating random postion around a location for testing purpose only
	 */
	private double[] createRandLocation(double latitude, double longitude) {

		return new double[] { latitude + ((Math.random() - 0.5) / 500),
				longitude + ((Math.random() - 0.5) / 500),
				150 + ((Math.random() - 0.5) * 10) };
	}

	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		Log.e("", "calling back");
		googleMap.clear();

		MarkerOptions mp = new MarkerOptions();

		mp.position(new LatLng(location.getLatitude(), location.getLongitude()));

		mp.title("my position");

		googleMap.addMarker(mp);

		googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
				location.getLatitude(), location.getLongitude()), 16));
	}

	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	public void makeRequests(boolean sensor, String origin, String destination) {
		ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair(Constants.PARAMETER_ORIGIN, origin));
		params.add(new BasicNameValuePair(Constants.PARAMETER_DESTINATION,
				destination));
		params.add(new BasicNameValuePair(Constants.PARAMETER_SENSOR, String
				.valueOf(sensor)));

		requestQueue = Volley.newRequestQueue(MainActivity.this);
		String url = Constants.DIRECTIONS_API_URI;

		try {
			url = url + "?"
					+ EntityUtils.toString(new UrlEncodedFormEntity(params));
		} catch (Exception e) {
			e.printStackTrace();
		}

		Log.e("TAG","URL"+url);
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Listener<JSONObject>() {

			@Override
			public void onResponse(JSONObject response) {
				// TODO Auto-generated method stub
				try {
					
					JSONArray jsonObjectArray = response.getJSONArray("routes");
					
					
					
					
					JSONObject jsonObject = jsonObjectArray.getJSONObject(0);
					
//					System.out.println(jsonObject.getJSONObject("overview_polyline").getString("points").toString());
					String path = jsonObject.getJSONObject("overview_polyline").getString("points").toString();
					MainActivity.this.drawToMap(path);
					
				
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				
			}
		});
		requestQueue.add(jsonObjectRequest);
	}
	
	public void drawToMap(String path)
	{
		List<LatLng> directionPoint = MapUtil.getDecodedPoly(path);
        
        PolylineOptions rectLine = new PolylineOptions().width(10).color(Color.RED);
      for (int i = 0; i < directionPoint.size(); i++) {
            rectLine.add(directionPoint.get(i));
      }
      // Adding route on the map
      googleMap.addPolyline(rectLine);
      CameraPosition cameraPosition = new CameraPosition.Builder()
		.target(directionPoint.get(0)).zoom(10).build();
      googleMap.animateCamera(CameraUpdateFactory
				.newCameraPosition(cameraPosition));
//      MarkerOptions marker = new MarkerOptions();
//      marker.position(new LatLng(dest_lat, dest_long));
//      marker.draggable(true);
//      googleMap.addMarker(marker);
	}
}
