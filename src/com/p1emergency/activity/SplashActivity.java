package com.p1emergency.activity;

import com.p1emergency.root.R;

import com.actionbarsherlock.app.SherlockActivity;
import com.p1emergency.fragment.AccountManagerDialog;
import com.p1emergency.utility.Constants;
import com.p1emergency.utility.SpanUtility;
import com.p1emergency.utility.NetworkUtility.CONNECTION;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


/**
 * @author Atul Kaushik (atul.kaushik@gmail.com)
 *
 */
public class SplashActivity extends SherlockActivity {

    private CountDownTimer mSpanTimer;
    private CountDownTimer mAppQuitTimer;
    private CountDownTimer mAppWaitTimer;
    private String TAG = SplashActivity.class.getSimpleName();
    
    private String mUserName;
    private String mPassword;
    private boolean mAccRemember;
    private SharedPreferences mPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.span);

        getSpanTimer(3500, 1000);
        mSpanTimer.start();
    }

    private void getSpanTimer(long millisInFuture, long countDownInterval) {
        mSpanTimer = new CountDownTimer(millisInFuture, countDownInterval) {
            @Override
            public void onFinish() {
                getApplicationState();
            }

            private void getApplicationState() {
                new SpanUtility(SplashActivity.this, appStateHandler).execute();
            }
            
            @Override
            public void onTick(long millisUntilFinished) {
            }
        };
    }
    
    private void navigateToNextScreen() {
        if(getCredentials()){
            if(mAccRemember){
                Intent intent = new Intent(SplashActivity.this, EMHLockScreenActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.linear_enter_from_right, R.anim.linear_exit_to_left);
                finish();
            }else{
                new AccountManagerDialog().show(getFragmentManager(), TAG);        
            }
        }else{
            new AccountManagerDialog().show(getFragmentManager(), TAG);        
        }   
    }

    private Handler appStateHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            super.handleMessage(msg);
            processResult((CONNECTION) msg.obj);
        }
    };

private void getAppQuitTimer(long millisInFuture, long countDownInterval) {
    mAppQuitTimer = new CountDownTimer(millisInFuture, countDownInterval) {

        @Override
        public void onFinish() {

            finish();
        }

        @Override
        public void onTick(long millisUntilFinished) {

        }
    };
    
}

private void getAppWaitTimer(long millisInFuture, long countDownInterval) {
    mAppWaitTimer = new CountDownTimer(millisInFuture, countDownInterval) {

        @Override
        public void onFinish() {

            navigateToNextScreen();
        }

        @Override
        public void onTick(long millisUntilFinished) {

        }
    };
    
}
    private void processResult(CONNECTION connection) {

        switch (connection) {
        case QUIT:
            Toast.makeText(getApplicationContext(), "INTERNET CONNECTION NOT AVAILABLE.  QUITTING.", Toast.LENGTH_LONG)
                    .show();
            getAppQuitTimer(5000, 1000);
            mAppQuitTimer.start();
            Constants.INTERNET_CONNECTION = CONNECTION.QUIT;
            
            break;
        case PROCEED:
            navigateToNextScreen();
            Constants.INTERNET_CONNECTION = CONNECTION.PROCEED;
            
            break;
        case PROCEED_WITHOUT_INTERNET:
            
            Toast.makeText(getApplicationContext(), "INTERNET CONNECTION NOT AVAILABLE.  PROCEEDING WITHOUT INTERNET.", Toast.LENGTH_SHORT)
            .show();
            getAppWaitTimer(3000, 1000);
            mAppWaitTimer.start();
            Constants.INTERNET_CONNECTION = CONNECTION.PROCEED_WITHOUT_INTERNET;
            
            break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK){
            Log.i(TAG, "onKeyDown");
            mSpanTimer.cancel();
            Intent intent = new Intent(SplashActivity.this, EMHLockScreenActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
    
    /**
     * reads configuration
     */
    private boolean getCredentials() {
        mPreferences = PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);
        mUserName = mPreferences.getString("googleAddress", "");
        mPassword = mPreferences.getString("googlePassword", "");
        mAccRemember = mPreferences.getBoolean("remember", false);
        
        return validateCredentials();
    }
    
    private boolean validateCredentials() {
        boolean flag = false;
        if (mUserName.length() > 5 && mUserName.contains("@") && mUserName.contains(".")) {
            if (mPassword.length() > 3) {
                flag = true;
            }
        }
        return flag;
    }
}
