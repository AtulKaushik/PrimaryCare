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

public class EMHHistoryActivity extends SideMenuBaseActivity {
	private Animation cameraAnimation;
	private Animation textAnimation;
	private View animateRealtiveLayout;
	private TextView animateTextView;
	private View mainLayout;
	private View progressImageView;

	public EMHHistoryActivity() {
		super(R.string.action_bar_title_patient_history);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_history);
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
		animateTextView.setAnimation(textAnimation);
		animateRealtiveLayout.setAnimation(cameraAnimation);
		buildProgressAnimation(progressImageView).start();
	}

	private void initUI() {
		mainLayout = findViewById(R.id.LinearLayout1);
		progressImageView = findViewById(R.id.progressLogo);
		animateRealtiveLayout = findViewById(R.id.relativeLayout);
		animateTextView = (TextView) findViewById(R.id.privacy);
	}

	private void setupViews() {

		// Creating our separated list adapter
		SeparatedListAdapter sepListAdapter = new SeparatedListAdapter(this,
				R.layout.sectioned_list_header, 6);

		EMHAdapter cnsListAdapter = new EMHAdapter(this,
				getCnsList());
		sepListAdapter.addSection("17/11/2014 - Consultation", cnsListAdapter);

		EMHAdapter codListAdapter = new EMHAdapter(this,
				getCodList());
		sepListAdapter.addSection("10/10/2014 - Consultation", codListAdapter);

		EMHAdapter locListAdapter = new EMHAdapter(this,
				getLocList());
		sepListAdapter.addSection("23/04/2014 - Consultation", locListAdapter);
		

		// Setting the sectioned list adapter to a custom list view
		SectionedListView list = (SectionedListView) findViewById(R.id.customlist);
		list.setAdapter(sepListAdapter);

		list.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				/*if (position == 6) {
					Intent intent = new Intent(EMHHistoryActivity.this,
							EmergencyMedicareActivity.class);
					startActivity(intent);

				}*/
				Toast.makeText(EMHHistoryActivity.this, "Consultation Details to be implemented", Toast.LENGTH_LONG).show();

			}
		});

		progressImageView.setVisibility(View.GONE);
		mainLayout.setVisibility(View.VISIBLE);
	}

	private void startDialog() {
		CountDownTimer timer = new CountDownTimer(500, 1000) {
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

	private List<EMH> getCodList() {
		List<EMH> l = new ArrayList<EMH>();
		l.add(new EMH("Tests", null, null, null, null, null));
		l.add(new EMH("Reports", null, null, null, null, null));
		l.add(new EMH("Prescription", null, null, null, null, null));
		l.add(new EMH("Doctor Details", null, null, null, null, null));
		return l;
	}

	private List<EMH> getCnsList() {
		List<EMH> l = new ArrayList<EMH>();
		l.add(new EMH("Tests", null, null, null, null, null));
		l.add(new EMH("Reports", null, null, null, null, null));
		l.add(new EMH("Prescription", null, null, null, null, null));
		l.add(new EMH("Doctor Details", null, null, null, null, null));
		return l;
	}

	private List<EMH> getLocList() {
		List<EMH> l = new ArrayList<EMH>();
		l.add(new EMH("Tests", null, null, null, null, null));
		l.add(new EMH("Reports", null, null, null, null, null));
		l.add(new EMH("Prescription", null, null, null, null, null));
		l.add(new EMH("Doctor Details", null, null, null, null, null));
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

}
