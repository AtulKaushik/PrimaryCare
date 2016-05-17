package com.p1emergency.activity;

import com.p1emergency.fragment.Map;
import com.p1emergency.fragmentsupport.AbstractBaseFragmentActivity;

import com.p1emergency.root.R;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class EMHHospitalLocatorActivity extends AbstractBaseFragmentActivity{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_locator);

        FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		
		Map fragment = new Map();
		fragmentTransaction.replace(R.id.fragment_container, fragment, "map_fragment");
		fragmentTransaction.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
