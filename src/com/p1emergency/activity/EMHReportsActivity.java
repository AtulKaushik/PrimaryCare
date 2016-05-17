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

public class EMHReportsActivity extends SideMenuBaseActivity {
	private Animation cameraAnimation;
	private Animation textAnimation;
	private View animateRealtiveLayout;
	//private TextView animateTextView;
	private View mainLayout;
	private View progressImageView;

	public EMHReportsActivity() {
		super(R.string.action_bar_title_patient_reports);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reports);
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
		//animateTextView.setAnimation(textAnimation);
		animateRealtiveLayout.setAnimation(cameraAnimation);
		buildProgressAnimation(progressImageView).start();
	}

	private void initUI() {
		mainLayout = findViewById(R.id.LinearLayout1);
		progressImageView = findViewById(R.id.progressLogo);
		animateRealtiveLayout = findViewById(R.id.relativeLayout);
/*		animateTextView = (TextView) findViewById(R.id.privacy);*/
	}

	private void setupViews() {

		// Creating our separated list adapter
		SeparatedListAdapter sepListAdapter = new SeparatedListAdapter(this,
				R.layout.sectioned_list_header, 12);

		EMHAdapter cnsListAdapter = new EMHAdapter(this,
				getRadiologyList());
		sepListAdapter.addSection("Radiology Report", cnsListAdapter);

		EMHAdapter codListAdapter = new EMHAdapter(this,
				getPathologyList());
		sepListAdapter.addSection("Pathology Report", codListAdapter);

		EMHAdapter locListAdapter = new EMHAdapter(this,
				getLaboratoryList());
		sepListAdapter.addSection("Laboratory Report", locListAdapter);
		

		EMHAdapter mnsListAdapter = new EMHAdapter(this,
				getMiscellaneousList());
		sepListAdapter.addSection("Miscellaneous Reports", mnsListAdapter);

		// Setting the sectioned list adapter to a custom list view
		SectionedListView list = (SectionedListView) findViewById(R.id.customlist);
		list.setAdapter(sepListAdapter);

		list.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//if (position == 6) {
					Toast.makeText(EMHReportsActivity.this, "Go to Report Details Activity", Toast.LENGTH_LONG).show();

				//}

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

	private List<EMH> getPathologyList() {
		List<EMH> l = new ArrayList<EMH>();
		l.add(new EMH("Serum Protein Electrophoresis", "07/07/2014", "The University of Texas", null, null, null));
		//l.add(new EMH("Cytopathology", "14/02/2013", "The Methodist Hospital", null, null, null));
		//l.add(new EMH("Dermatopathology", "30/08/2012", "Texas A&M Health Science Center", null, null, null));
		return l;
	}

	private List<EMH> getRadiologyList() {
		List<EMH> l = new ArrayList<EMH>();
		l.add(new EMH("CT Angiography", "11/11/2014", "National Cancer Institute, TX", null, null, null));
		l.add(new EMH("CT Scanning - Chest", "27/08/2014", "National Cancer Institute, TX", null, null, null));
		l.add(new EMH("CT Scanning - Abdomen", "08/07/2014", "Department of Pathology Ð UT Southwestern, Dallas, TX", null, null, null));
		l.add(new EMH("MRI - Brain", "13/02/2013", "Department of Pathology, MD Anderson Cancer Center", null, null, null));
		return l;
	}

	private List<EMH> getLaboratoryList() {
		List<EMH> l = new ArrayList<EMH>();
		//l.add(new EMH("Immunology/Molecular Diagnostics", "07/07/2014", "UTMB School of Medicine", null, null, null));
		l.add(new EMH("Complete Blood Count (CBC)", "14/02/2013", "University of Texas Health Science Center ", null, null, null));
		l.add(new EMH("prothrombin time (PT)", "30/08/2012", "Texas A&M Health Science Center", null, null, null));
		return l;
	}
	
	private List<EMH> getMiscellaneousList() {
		List<EMH> l = new ArrayList<EMH>();
		l.add(new EMH("ECG", "16/11/2014", "UTMB School of Medicine", null, null, null));
		l.add(new EMH("TMT", "16/11/2014", "UTMB School of Medicine", null, null, null));
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

	public void startCamera(View view) {
		Toast.makeText(this, "Starting Camera..", Toast.LENGTH_LONG).show();
		Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
		 startActivityForResult(intent, 0);
	}

}
