package com.p1emergency.activity;

import com.p1emergency.model.TextHolder;

import com.p1emergency.root.R;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TextView.BufferType;

public class EMHLockScreenActivity extends Activity implements OnClickListener,
		OnTouchListener {

	private View firstLayout;
	private View secondLayout;
	private View thirdLayout;
	private View textLayout;
	private View editTextLayout;

	private ObjectAnimator firstLayoutTranslate;
	private ObjectAnimator secondLayoutRotate;
	private ObjectAnimator secondLayoutTranslate;
	private ObjectAnimator secondLayoutScale;
	private ObjectAnimator thirdLayoutScale;
	private ObjectAnimator thirdLayoutRotate;
	private ObjectAnimator thirdLayoutTranslate;
	private ObjectAnimator textLayoutTranslate;
	private ObjectAnimator textLayoutAlpha;

	private TextHolder mTextHolder;

	private ImageButton mBackButton;
	private Button mRestCodeButton;
	private Button mButton[] = new Button[10];

	private final int TIME = 500;
	private boolean reverse;
	private boolean keyboardHidden = true;
	private Context context;

	private int index = 0;
	private int checkSum[] = { 0, 0, 0, 0 };
	private int checkPassCode = 4;
	private String passCode = "1234";
	private String enteredPassCode = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.lockscreen);
		this.context = this;
		initUI();
		setEditTextTag();
		setObjectAnimator();
		setListener();
		setCursorOnFirstEditText();
	}

	private void initUI() {
		mTextHolder = new TextHolder();
		mTextHolder.editText[0] = (EditText) findViewById(R.id.editText1);
		mTextHolder.editText[1] = (EditText) findViewById(R.id.editText2);
		mTextHolder.editText[2] = (EditText) findViewById(R.id.editText3);
		mTextHolder.editText[3] = (EditText) findViewById(R.id.editText4);
		firstLayout = findViewById(R.id.firstLayout);
		secondLayout = findViewById(R.id.secondLayout);
		thirdLayout = findViewById(R.id.thirdLayout);
		textLayout = findViewById(R.id.photoinfo);
		editTextLayout = findViewById(R.id.editTextLayout);
		SpannableString text = styleText();
		((TextView) findViewById(R.id.phototitle)).setText(text,
				BufferType.SPANNABLE);
		initKeys();
	}

	private void initKeys() {
		mButton[0] = (Button) findViewById(R.id.zero);
		mButton[1] = (Button) findViewById(R.id.one);
		mButton[2] = (Button) findViewById(R.id.two);
		mButton[3] = (Button) findViewById(R.id.three);
		mButton[4] = (Button) findViewById(R.id.four);
		mButton[5] = (Button) findViewById(R.id.five);
		mButton[6] = (Button) findViewById(R.id.six);
		mButton[7] = (Button) findViewById(R.id.seven);
		mButton[8] = (Button) findViewById(R.id.eight);
		mButton[9] = (Button) findViewById(R.id.nine);

		mBackButton = (ImageButton) findViewById(R.id.back);
		mRestCodeButton = (Button) findViewById(R.id.resetcode);

	}

	private void setEditTextTag() {
		mTextHolder.editText[0].setTag(0);
		mTextHolder.editText[1].setTag(1);
		mTextHolder.editText[2].setTag(2);
		mTextHolder.editText[3].setTag(3);
	}

	private void setCursorOnFirstEditText() {
		mTextHolder.editText[0].requestFocus();
	}

	private void setObjectAnimator() {
		firstLayoutTranslate = ObjectAnimator.ofFloat(firstLayout,
				"translationY", 0, -512).setDuration(TIME);
		secondLayoutRotate = ObjectAnimator.ofFloat(secondLayout, "rotationX",
				270, 360).setDuration(TIME);
		secondLayoutTranslate = ObjectAnimator.ofFloat(secondLayout,
				"translationY", 100, -256).setDuration(TIME);
		secondLayoutScale = ObjectAnimator.ofFloat(secondLayout, "scaleX",
				.77f, 1).setDuration(TIME);
		thirdLayoutScale = ObjectAnimator.ofFloat(thirdLayout, "scaleX", .77f,
				1).setDuration(TIME);
		thirdLayoutRotate = ObjectAnimator.ofFloat(thirdLayout, "rotationX",
				-270, -360).setDuration(TIME);
		thirdLayoutTranslate = ObjectAnimator.ofFloat(thirdLayout,
				"translationY", 100, 0).setDuration(TIME);
		textLayoutTranslate = ObjectAnimator.ofFloat(textLayout,
				"translationY", 0, -520).setDuration(TIME);
		textLayoutAlpha = ObjectAnimator.ofFloat(textLayout, "alpha", 1.0f,
				0.0f).setDuration(TIME);
	}

	private void setListener() {
		mTextHolder.editText[0].setOnTouchListener((OnTouchListener) context);
		mTextHolder.editText[1].setOnTouchListener((OnTouchListener) context);
		mTextHolder.editText[2].setOnTouchListener((OnTouchListener) context);
		mTextHolder.editText[3].setOnTouchListener((OnTouchListener) context);
		mBackButton.setOnClickListener((OnClickListener) context);
		mRestCodeButton.setOnClickListener((OnClickListener) context);

		for (int i = 0; i < mButton.length; i++)
			mButton[i].setOnClickListener(this);

	}

	private SpannableString styleText() {
		SpannableString spannableString = new SpannableString("\'We treat, he cures!\'");
		spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#003366")), 0, 21, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		spannableString.setSpan(new StyleSpan(android.graphics.Typeface.ITALIC), 0, 21, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
		return spannableString;
	}

	@Override
	public void onClick(View view) {

		if (view != mBackButton && view != mRestCodeButton) {
			addText(view);
			index = nextCursor();
			if (index == checkPassCode) {
				index -= 1;
				validatePasscode();
			}
			mTextHolder.editText[index].requestFocus();
		} else if (view == mBackButton) {

			if (shouldHideSoftKeyBoard()) {
				hideKeyboardAnimation();
			} else {
				index = removeText();
				mTextHolder.editText[index].setText("");
				mTextHolder.editText[index].requestFocus();
				checkSum[index] = 0;
			}
		} else if (view == mRestCodeButton) {
			resetLock();

		}

	}

	private void resetLock() {
		mTextHolder.editText[0].setText("");
		mTextHolder.editText[1].setText("");
		mTextHolder.editText[2].setText("");
		mTextHolder.editText[3].setText("");
		mTextHolder.editText[0].requestFocus();
		index = 0;
		for (int i = 0; i < checkSum.length; i++)
			checkSum[i] = 0;
	}

	private void validatePasscode() {
		enteredPassCode = "";
		for (int i = 0; i < checkSum.length; i++) {
			enteredPassCode = enteredPassCode
					+ (mTextHolder.editText[i].getText().toString());
		}

		if (passCode.equals(enteredPassCode)) {
			startMyAccountsActivity();
		} else {
			Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
			editTextLayout.startAnimation(shake);
			resetLock();
			showIncorrectPasswordToast();
		}
	}

	private void showIncorrectPasswordToast() {
		/*Toast toast = Toast.makeText(context, "Incorrect code. Retry!",
				Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL,
				0, -157);
		toast.show();*/

	}

	private boolean shouldHideSoftKeyBoard() {
		int i;
		for (i = 0; i < checkSum.length; i++) {
			if (checkSum[i] == 1) {
				return false;
			}
		}
		return true;
	}

	private int nextCursor() {
		int i;
		for (i = 0; i < checkSum.length; i++) {
			if (checkSum[i] == 0) {
				return i;
			}
		}
		return checkPassCode;
	}

	private int removeText() {
		if (mTextHolder.editText[index].length() == 0) {
			if (index - 1 >= 0) {
				return index - 1;
			}
		}
		return index;
	}

	private void addText(View view) {
		String data = "";
		data = (String) view.getTag();
		if (data != null) {
			mTextHolder.editText[index].append(data);
			checkSum[index] = 1;
		}
	}

	@Override
	public boolean onTouch(View view, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_UP) {
			index = (Integer) view.getTag();
			view.requestFocus();
			hideDefaultKeyboard();
			enableKeyboard();
		}
		return true;
	}

	private void hideDefaultKeyboard() {
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

	}

	private void enableKeyboard() {
		secondLayout.setVisibility(View.VISIBLE);
		thirdLayout.setVisibility(View.VISIBLE);
		if (!reverse) {
			showKeyboardAnimation();
		}
	}

	private void hideKeyboardAnimation() {
		firstLayoutTranslate.reverse();
		secondLayoutRotate.reverse();
		secondLayoutTranslate.reverse();
		thirdLayoutRotate.reverse();
		secondLayoutScale.reverse();
		thirdLayoutScale.reverse();
		thirdLayoutTranslate.reverse();
		textLayoutTranslate.reverse();
		textLayoutAlpha.reverse();
		reverse = false;
		keyboardHidden = true;
	}

	private void showKeyboardAnimation() {
		AnimatorSet set = new AnimatorSet();
		set.playTogether(textLayoutAlpha, textLayoutTranslate,
				firstLayoutTranslate, secondLayoutRotate,
				secondLayoutTranslate, secondLayoutScale, thirdLayoutScale,
				thirdLayoutRotate, thirdLayoutTranslate);
		set.start();
		reverse = true;
		keyboardHidden = false;
	}

	private void startMyAccountsActivity() {
		//finish();
		Intent intent = new Intent(context, POEMenuActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.linear_enter_from_right, R.anim.linear_exit_to_left);
	}

	@Override
	public void onBackPressed() {
		if (!keyboardHidden) {
			hideKeyboardAnimation();
			keyboardHidden = true;
		} else {
			super.onBackPressed();
		}
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
}
