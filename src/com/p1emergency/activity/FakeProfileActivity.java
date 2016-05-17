package com.p1emergency.activity;

import com.p1emergency.root.R;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.widget.Toast;

public class FakeProfileActivity extends Activity implements OnClickListener,
		OnTouchListener {


	private Context context;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//setTitle("Settings");
		//setupActionBar();
		setContentView(R.layout.fake_profile_map);
		this.context = this;
		//initUI();
		//push from bottom to top
		   overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
		   
		}
		
		private void setupActionBar() {
			ActionBar actionBar = getActionBar();
			actionBar.setDisplayShowTitleEnabled(true);
			actionBar.setDisplayShowHomeEnabled(true);
			actionBar.setDisplayHomeAsUpEnabled(true);
			actionBar.setDisplayUseLogoEnabled(true);
		}
	
	@Override
	protected void onPause() {
		super.onPause();
		//push from bottom to top
		   overridePendingTransition(R.anim.push_up_out, R.anim.push_up_in);
	}

	private void initUI() {
	}


	@Override
	public void onClick(View view) {
		
	}

	private void startPOELockScreenActivity() {
		finish();
		Intent intent = new Intent(context, EMHLockScreenActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.linear_enter_from_right, R.anim.linear_exit_to_left);
	}
	
	public void call911(View view) {
		Toast.makeText(this, "Calling Emergency..", Toast.LENGTH_LONG).show();
		String number = "tel:" + "911";
        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number)); 
        startActivity(callIntent);
	}
	
	public void locateHospital(View view) {
		Toast.makeText(this, "Locating Nearest hospital..", Toast.LENGTH_LONG).show();
		//startActivity(new Intent(this, EMHHospitalLocatorActivity.class));
		startActivity(new Intent(this, EMHMapsActivity.class));
	}
	
	public void getInfo(View view) {
		Toast.makeText(this, "Accept Privacy Policy to proceed", Toast.LENGTH_LONG).show();
		//startActivity(new Intent(this, EMHHospitalLocatorActivity.class));
		startActivity(new Intent(this, POEPrivacyTermsActivity.class));
	}
	
	public void getSettings(View view) {
		startActivity(new Intent(this, EMHSettingsActivity.class));
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void getWaitTimes(View view) {
		startActivity(new Intent(this, POEWaitTimeActivity.class));
	}
	
	public void getInfoDetails(View view) {
		//startActivity(new Intent(this, EmergencyMedicareActivity.class));
		startActivity(new Intent(this, POEInfoCenterActivity.class));
	}
	
	public void getAppointments(View view) {
		//startActivity(new Intent(this, EmergencyMedicareActivity.class));
		startActivity(new Intent(this, EMHAppointmentsActivity.class));
	}
	
	public void getProfile(View view) {
		//startActivity(new Intent(this, EmergencyMedicareActivity.class));
		startActivity(new Intent(this, POEProfileActivity.class));
	}
}
