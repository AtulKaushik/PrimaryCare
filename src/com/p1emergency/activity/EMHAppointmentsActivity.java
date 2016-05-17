package com.p1emergency.activity;

import com.p1emergency.root.R;

import java.util.ArrayList;
import java.util.List;

import com.p1emergency.adapter.EMHAdapter;
import com.p1emergency.adapter.SeparatedListAdapter;
import com.p1emergency.model.EMH;
import com.p1emergency.view.SectionedListView;


import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class EMHAppointmentsActivity extends SideMenuBaseActivity {
	private Animation cameraAnimation;
	private Animation textAnimation;
	private View animateRealtiveLayout;
	private View mainLayout;
	private View progressImageView;

	public EMHAppointmentsActivity() {
		super(R.string.action_bar_title_appointments);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_appointments);
		initUI();
		setAnimation();
		setupViews();
		setupActionBar();
		setSlidingActionBarEnabled(true);
		startDialog();
	}

	private void setAnimation() {
		cameraAnimation = AnimationUtils.loadAnimation(this,
				R.anim.slide_left_acc);

		textAnimation = AnimationUtils.loadAnimation(this,
				R.anim.slide_right_acc);
		animateRealtiveLayout.setAnimation(cameraAnimation);
		buildProgressAnimation(progressImageView).start();
	}

	private void initUI() {
		mainLayout = findViewById(R.id.LinearLayout1);
		progressImageView = findViewById(R.id.progressLogo);
		animateRealtiveLayout = findViewById(R.id.relativeLayout);
	}

	private void setupViews() {

		// Creating our separated list adapter
		SeparatedListAdapter sepListAdapter = new SeparatedListAdapter(this,
				R.layout.sectioned_list_header, -1);

		EMHAdapter cnsListAdapter = new EMHAdapter(this,
				getCnsList());
		sepListAdapter.addSection("APPOINTMENTS", cnsListAdapter);

		EMHAdapter locListAdapter = new EMHAdapter(this,
				getLocList());
		sepListAdapter.addSection("PAST APPOINTMENTS", locListAdapter);

		// Setting the sectioned list adapter to a custom list view
		SectionedListView list = (SectionedListView) findViewById(R.id.customlist);
		list.setAdapter(sepListAdapter);

		list.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//if (position == 6) {
				Toast.makeText(EMHAppointmentsActivity.this, "Show Past Appointments\' Detail Activity", Toast.LENGTH_LONG).show();
					/*Intent intent = new Intent(EMHAppointmentsActivity.this,
							EmergencyMedicareActivity.class);
					startActivity(intent);*/

				//}

			}
		});

	}

	private void startDialog() {
		CountDownTimer timer = new CountDownTimer(1500, 1000) {
			@Override
			public void onFinish() {
				buildProgressAnimation(progressImageView).end();
				progressImageView.setVisibility(View.GONE);
				mainLayout.setVisibility(View.VISIBLE);
			}

			@Override
			public void onTick(long millisUntilFinished) {
			}
		}.start();
	}

	private List<EMH> getCnsList() {
		List<EMH> l = new ArrayList<EMH>();
		l.add(new EMH("04:30 PM, 21/11/2014", "Set Reminder", "Last Name, First Name (MD)\nFrisco Starwood", null, null, true));
		l.add(new EMH("08:30 AM, 25/11/2014", "Set Reminder", "Last Name, First Name (MD)\nValley Ranch", null, null, true));
		l.add(new EMH("07:30 AM, 11/02/2015", "Set Reminder", "Last Name, First Name\nFrisco North", null, null, true));
		return l;
	}

	private List<EMH> getLocList() {
		List<EMH> l = new ArrayList<EMH>();
		l.add(new EMH("09:00 PM, 07/02/2014", null, "Last Name, First Name (MD)\nFrisco Starwood", null, null, null));
		l.add(new EMH("08:30 AM, 13/04/2013", "Missed", "Last Name, First Name (MD)\nValley Ranch", "Reschedule", null, null));
		l.add(new EMH("02:30 PM, 27/03/2012", null, "Last Name, First Name\nFrisco North", null, null, null));
		l.add(new EMH("04:30 PM, 05/12/2011", "Missed", "Last Name, First Name (MD)\nFrisco Starwood", "Reschedule", null, null));
		return l;
	}

	private void setupActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayUseLogoEnabled(true);
	}

	public static ValueAnimator buildProgressAnimation(View progress) {
		ValueAnimator rotate = ObjectAnimator.ofFloat(progress, "rotation", 0,
				360);
		rotate.setDuration(1000);
		rotate.setInterpolator(null);
		rotate.setRepeatCount(ValueAnimator.INFINITE);
		return rotate;
	}

	public void getAppointment(View view) {
		Intent intent = new Intent(EMHAppointmentsActivity.this,
				EmergencyMedicareActivity.class);
		startActivity(intent);
	}
}
