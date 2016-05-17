package com.p1emergency.fragment;

import com.p1emergency.activity.EMHLockScreenActivity;
import com.p1emergency.activity.SplashActivity;

import com.p1emergency.root.R;


import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/**
 * @author Atul Kaushik (kaushik.atul@gmail.com)
 *
 */
public class AccountManagerDialog extends DialogFragment{
    
    private Account mAccount;
    private AccountManager mAccountManager;
    private String TAG = AccountManagerDialog.class.getSimpleName();
    
    private TextView mUser;
    private EditText mUsername;
    private EditText mPassword;
    private CheckBox mCheckBox;
    private Button mLoginBtn;
    
    private String usernameString;
    private String passwordString;
    
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        mAccount = getExistingUser();
       

        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_account_setting, null);
        initUI(view);
        
       // mUser.setText("Hello "+mAccount.name.split("@")[0]);
       // mUsername.setText(mAccount.name);
       // mUser.setText("Hello kaushik.atul");
        
        mLoginBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(validateCredentials()){
	                   setCredentials();
	                   SplashActivity mActivity = (SplashActivity) getActivity(); 
	                   Intent intent = new Intent(mActivity, EMHLockScreenActivity.class);
	                   startActivity(intent);
	                   mActivity.finish();   
	               }else{
	            	   SplashActivity mActivity = (SplashActivity) getActivity(); 
	            	   Animation shake = AnimationUtils.loadAnimation(mActivity, R.anim.shake);
	       			view.startAnimation(shake);
	                   //new AccountManagerDialog().show(getFragmentManager(), TAG);  
	               }
				
			}
		});
        builder.setView(view);    
    
        return builder.create();
       
    }
    
    private void initUI(View view) {
        mUser = ((TextView) view.findViewById(R.id.user));
        mUsername = ((EditText) view.findViewById(R.id.username));
        mPassword = ((EditText) view.findViewById(R.id.password));
        mCheckBox = (CheckBox) view.findViewById(R.id.acc_mgr_pref);
        mLoginBtn = (Button) view.findViewById(R.id.login_btn);
    }
    
    @Override
    public void dismissAllowingStateLoss() {
        // TODO Auto-generated method stub
       // super.dismissAllowingStateLoss();
    }

    private Account getExistingUser() {
        mAccountManager = AccountManager.get(getActivity()); 
        Account[] accounts = mAccountManager.getAccountsByType("com.google");
        if(accounts.length>0){
            
            return accounts[0];    
        }    
            return null;
    }
    
    /**
     * writes configuration
     */
    private void setCredentials() {
        SharedPreferences.Editor editor;
        try {
            editor = PreferenceManager.getDefaultSharedPreferences(getActivity()).edit();
            editor.putString("googleAddress", usernameString);
            editor.putString("googlePassword", passwordString);
            editor.putBoolean("remember", mCheckBox.isChecked());
            editor.putBoolean("rememberSession", !mCheckBox.isChecked());
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
     
    private boolean validateCredentials() {

         usernameString = mUsername.getText().toString();
         passwordString = mPassword.getText().toString();
        boolean flag = false;
        if (usernameString.length() > 5 && usernameString.contains("@") && usernameString.contains(".")) {
            if (passwordString.length() > 3) {
                flag = true;
            }
        }
        return flag;
    }
}
