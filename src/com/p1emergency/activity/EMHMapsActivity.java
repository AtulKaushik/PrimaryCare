package com.p1emergency.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.p1emergency.root.R;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class EMHMapsActivity extends Activity {
	
	private GoogleMap map;
	final LatLng CENTER = new LatLng(43.661049f, -79.400917f);
	
	class Data {
		public Data(float lng, float lat, String title, String snippet) {
			super();
			this.lat = (float)lat;
			this.lng = (float)lng;
			this.title = title;
			this.snippet = snippet;
		}
		float lat;
		float lng;
		String title;
		String snippet;
	}
	
	Data[] data = {
			new Data(-79.394524f,43.655796f, "Frisco Starwood",
					"5649 Lebanon Road Frisco\n\nTX 75034\n(866) 653-2525"),  
			new Data(-79.402206f,43.657688f, "Valley Ranch",
					"8124-8288 North MacArthur Boulevard\nIrving, TX 75063\n(866) 653-2525"),    
			new Data(-79.403732f,43.666801f, "Frisco North",
					"4950 Eldorado Parkway\nFrisco, TX 75033\n(866) 653-2525"),
	};
	/**
	 * new Data(-79.400917f,43.661049f, "Frisco Starwood, 5649 Lebanon Road Frisco,",
				"75034 (866) 653-2525\nTX 75246-2088"),
		new Data(-79.394524f,43.655796f, "UT Southwestern Medical Center",
				"5323 Harry Hines Boulevard\nDallas, TX 75390-9265"),
		new Data(-79.402206f,43.657688f, "Medical City Dallas Hospital",
				"7777 Forest Lane\nDallas, TX 75230-2598"),    
		new Data(-79.390381f,43.659878f, "Texas Health Presbyterian Hospital",
				"8200 Walnut Hill Lane\nDallas, TX 75231-4426"),
		new Data(-79.403732f,43.666801f, "Baylor Institute for Rehabilitation",
				"909 North Washington Avenue\nDallas, TX 75246-1520"),/*
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setTitle(R.string.action_bar_title_locations);
		setupActionBar();
		setContentView(R.layout.activity_maps);
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map2)).getMap();
		    
		if (map==null) {
			// XXX Warn about this to the user!

			return;
		}
		
		//for (Data d : data) {
		for (int i = 0 ;i < data.length;i++) {
			Data d = data[i];
		    LatLng location = new LatLng(d.lat, d.lng);
		    if(i != 0){
		    	map.addMarker(new MarkerOptions().position(location)
				          .title(d.title)
				          .snippet(d.snippet)
				          .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker_coming)));
		    }else{
		    	map.addMarker(new MarkerOptions().position(location)
				          .title(d.title)
				          .snippet(d.snippet)
				          .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker)));
		    }
			
		}
		
		// Let the user see indoor maps if available.
		map.setIndoorEnabled(true);
		
		// Enable my-location stuff
		map.setMyLocationEnabled(true);
		
		map.moveCamera(CameraUpdateFactory.zoomTo(14));
	    map.animateCamera(CameraUpdateFactory.newLatLng(CENTER), 1750, null);
	    
	    /*LatLng hospital_location = new LatLng(-79.400917f,43.661049f);
	    
	    map.addMarker(new MarkerOptions()
        .title("Frisco Starwood")
        .snippet("5649 Lebanon Road Frisco\n\nTX 75034\n(866) 653-2525")
        .position(hospital_location)
        .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker)));*/
	}
	
	private void setupActionBar() {
		ActionBar actionBar = getActionBar();
		//actionBar.setDisplayShowTitleEnabled(true);
		//actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		//actionBar.setDisplayUseLogoEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(android.view.MenuItem item) {
		if (item.getItemId() == android.R.id.home)
			finish();
		return super.onOptionsItemSelected(item);
	}
}
