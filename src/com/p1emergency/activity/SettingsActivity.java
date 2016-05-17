/**
 * preference activity
 * for providing
 * app settings
 */
package com.p1emergency.activity;

import com.p1emergency.root.R;

import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.actionbarsherlock.view.MenuItem;

import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.PreferenceManager;

/**
 * @author AtulKaushik (atul.kaushik@gmail.com)
 *
 */
public class SettingsActivity extends SherlockPreferenceActivity {

    private EditTextPreference userId;
    private EditTextPreference userPwd;
    private CheckBoxPreference remember;
    
    /**
     * creates preference dialog from XML
     * @see android.preference.PreferenceActivity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        addPreferencesFromResource(R.xml.preferences);
        initUI();
        clearPreferences();
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(SettingsActivity.this, EMHLockScreenActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * initializing UI
     * */
    private void initUI() {

        userId = (EditTextPreference) findPreference("googleAddress");
        userPwd = (EditTextPreference) findPreference("googlePassword");
        remember = (CheckBoxPreference) findPreference("remember");

    }

    /**
     * Method to clear preferences
     * */
    private void clearPreferences() {

        if (!remember.isChecked()) {
            userId.setText("");
            userPwd.setText("");
        }
    }
    
    @Override
    protected void onStart() {
        if(!PreferenceManager.getDefaultSharedPreferences(this).getBoolean("remember", false)){
        }
        super.onStart();
    }
    
    @Override
    protected void onStop() { 
        super.onStop();
    }
}
