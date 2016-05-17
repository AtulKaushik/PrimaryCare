package com.p1emergency.activity;

import com.p1emergency.root.R;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.p1emergency.adapter.SwipingTabsPagerAdapter;
import com.p1emergency.fragment.EmergencyCareTabFragment;
import com.p1emergency.fragment.RegularCareTabFragment;
import com.p1emergency.fragmentsupport.AbstractBaseFragmentActivity;
import com.p1emergency.fragmentsupport.FragmentNavigationManager;


public class EmergencyMedicareActivity extends AbstractBaseFragmentActivity {

	private static FragmentNavigationManager mNavManager;

	private ViewPager mViewPager;
	private SwipingTabsPagerAdapter mTabsAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Animate!
		overridePendingTransition(R.anim.linear_enter_from_right,
				R.anim.linear_exit_to_left);

		setContentView(R.layout.example_swiping_tabs);
		setupViews();
		setupActionBar();
		setActionBarTitle(R.string.actionbar_title_loc);

	}

	private void setupViews() {

		mViewPager = (ViewPager) findViewById(R.id.swiping_tab_viewpager);
	}

	private void setupActionBar() {

		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setDisplayUseLogoEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);

		Bundle b;
		mTabsAdapter = new SwipingTabsPagerAdapter(this, mViewPager);

		b = new Bundle();
		b.putString(SwipingTabsPagerAdapter.TAB_ACTIONBAR_TITLE, getResources()
				.getString(R.string.actionbar_title_loc));

		mTabsAdapter.addTab(
				actionBar.newTab().setText(R.string.swiping_tab_summary_title),
				RegularCareTabFragment.class, b);

		b = new Bundle();
		b.putString(SwipingTabsPagerAdapter.TAB_ACTIONBAR_TITLE, getResources()
				.getString(R.string.actionbar_title_loc));
		mTabsAdapter
				.addTab(actionBar.newTab().setText(
						R.string.swiping_tab_activity_title),
						EmergencyCareTabFragment.class, b);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	public void navigateToFragment(Fragment fragment) {
		mNavManager.navigateToFragment(fragment, R.id.fragmentMainBody);
	}

	private void setupNavManager() {
		if (mNavManager != null)
			return;
		FragmentNavigationManager fragmentNavigationManager = new FragmentNavigationManager();
		fragmentNavigationManager.fragmentManager = getSupportFragmentManager();
		mNavManager = fragmentNavigationManager;
	}

	@Override
	protected void onStop() {
		Log.i(EmergencyMedicareActivity.class.getSimpleName(),
				String.format("%s onStop()  ", this.getClass().getName()));
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		Log.i(EmergencyMedicareActivity.class.getSimpleName(),
				String.format("%s onDestroy()  ", this.getClass().getName()));
		super.onDestroy();
		mNavManager = null;
	}

	@Override
	protected void onRestart() {
		Log.i(EmergencyMedicareActivity.class.getSimpleName(),
				String.format("%s onRestart()  ", this.getClass().getName()));
		setupNavManager();
		super.onRestart();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
	}

	public FragmentNavigationManager getNavigationManager() {
		return mNavManager;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.actionbar_menu, menu);
		return true;
	}

	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(R.anim.linear_enter_from_left,
				R.anim.linear_exit_to_right);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.action_call){
			Toast.makeText(EmergencyMedicareActivity.this, "Calling Hospital", Toast.LENGTH_LONG).show();
		}else if (item.getItemId() == R.id.specialty_action){
			Toast.makeText(EmergencyMedicareActivity.this, "Getting Specialty", Toast.LENGTH_LONG).show();
		} else if (item.getItemId() == R.id.action_location){
			Toast.makeText(EmergencyMedicareActivity.this, "Getting Location", Toast.LENGTH_LONG).show();
		}else if (item.getItemId() == R.id.insurer_action){
			Toast.makeText(EmergencyMedicareActivity.this, "Getting Insurers", Toast.LENGTH_LONG).show();
		}else if (item.getItemId() == R.id.action_review){
			Toast.makeText(EmergencyMedicareActivity.this, "Write Review", Toast.LENGTH_LONG).show();
		}else if (item.getItemId() == android.R.id.home)
			finish();

		return super.onOptionsItemSelected(item);
	}
	
	public void callEmergency(View view) {
		//Toast.makeText(this, "Calling Emergency..", Toast.LENGTH_LONG).show();
		String number = "tel:" + "8971811779";
        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number)); 
        startActivity(callIntent);
	}
	
	public void call911(View view) {
		//Toast.makeText(this, "Calling 911..", Toast.LENGTH_LONG).show();
		String number = "tel:" + "911";
        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(number)); 
        startActivity(callIntent);
	}
	
	public void locateHospital(View view) {
		Toast.makeText(this, "Locating hospital..", Toast.LENGTH_LONG).show();
		startActivity(new Intent(this, EMHHospitalLocatorActivity.class));
	}
	
}
