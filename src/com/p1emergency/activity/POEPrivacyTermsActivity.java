package com.p1emergency.activity;

import com.p1emergency.root.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Toast;

/*
 * Displays information about the application
 */
public class POEPrivacyTermsActivity extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setTitle(R.string.privacy_terms_title);
		setContentView(R.layout.info_activity);
		
		/*WebView aboutView = (WebView)findViewById(R.id.about_content);
		aboutView.loadUrl("file:///android_asset/info.html");*/
		
		/*TextView tv = (TextView)findViewById(R.id.about_version_tv);
		tv.setText(GooglePlayServicesUtil.getOpenSourceSoftwareLicenseInfo(this));	*/	
	}
	
	public void done(View v) {
		finish();
	}
	
	private void startDialog() {
		Toast.makeText(POEPrivacyTermsActivity.this, "Please accept Privacy & Terms to proceed.", Toast.LENGTH_LONG).show();
		
		CountDownTimer timer = new CountDownTimer(3000, 3000) {
			@Override
			public void onFinish() {
				finish();
				//slide from right to left
				   overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
			}

			@Override
			public void onTick(long millisUntilFinished) {
			}
		}.start();
	}
	
	public void declinePolicy(View view) {
		startDialog();
	}
	
	public void acceptPolicy(View view) {
		startActivity(new Intent(POEPrivacyTermsActivity.this, EMHLockScreenActivity.class));
	}
}
