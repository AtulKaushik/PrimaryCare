package com.p1emergency.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.p1emergency.fragmentsupport.AbstractBaseFragment;

import com.p1emergency.root.R;

public class Map extends AbstractBaseFragment{
public GoogleMap googleMap = null;

@Override
public void onCreate(Bundle savedInstanceState) 
{
        super.onCreate(savedInstanceState);
        
}

@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_map, null);
    
    return view;
}

@Override
	public void onResume() {
		super.onResume();
		setUpMapIfNeeded();
	}

private void setUpMapIfNeeded()
{
    if(googleMap == null)
    {
        googleMap = ((MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map_locator)).getMap();
        if(googleMap != null)
        {
            GoogleMapOptions options = new GoogleMapOptions();
            options.mapType(GoogleMap.MAP_TYPE_NORMAL)
                .camera(new CameraPosition(new LatLng(25f, 47f), 13f, 0f, 0f));
        }
        LatLng hospital_location = new LatLng(-33.867, 151.206);

        googleMap.setMyLocationEnabled(true);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hospital_location, 13));

        googleMap.addMarker(new MarkerOptions()
                .title("Baylor University Medical Center")
                .snippet("3500 Gaston Avenue\nDallas, Texas 75246")
                .position(hospital_location));
    }
}

@Override
public void setActionBarTitle() {
	setActionBarTitle(R.string.action_bar_title_appointments);
	
}
}
