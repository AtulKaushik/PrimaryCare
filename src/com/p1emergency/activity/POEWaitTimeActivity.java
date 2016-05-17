package com.p1emergency.activity;

import com.p1emergency.root.R;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class POEWaitTimeActivity extends SideMenuBaseActivity {
	private Animation cameraAnimation;
	private Animation textAnimation;
	private Animation contentAnimation;
	private View animateRealtiveLayout;
	private TextView animateTextView;
	private View mainLayout;
	private View progressImageView;

	public POEWaitTimeActivity() {
		super(R.string.action_bar_title_wait_time);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wait_time);
		initUI();
		//push from bottom to top
		   overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
		//slide from right to left
		  // overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
		
		setAnimation();
		setupViews();
		setupActionBar();
		setSlidingActionBarEnabled(true);
		startDialog();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		

		//push from bottom to top
		   overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
	}

	private void setAnimation() {
		cameraAnimation = AnimationUtils.loadAnimation(this,
				R.anim.slide_left_acc);

		textAnimation = AnimationUtils.loadAnimation(this,
				R.anim.slide_right_acc);
		contentAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_right_acc);
		animateTextView.setAnimation(textAnimation);
		animateRealtiveLayout.setAnimation(cameraAnimation);
		mainLayout.setAnimation(contentAnimation);
		buildProgressAnimation(progressImageView).start();
	}

	private void initUI() {
		mainLayout = findViewById(R.id.LinearLayout1);
		progressImageView = findViewById(R.id.progressLogo);
		animateRealtiveLayout = findViewById(R.id.wait_time_relativeLayout);
		animateTextView = (TextView) findViewById(R.id.privacy);
	}

	private void setupViews() {
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

	public void getLocation(View view) {
		startActivity(new Intent(this, EMHMapsActivity.class));
	}
}
