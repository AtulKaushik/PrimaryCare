package com.p1emergency.androidshared.utils;

import android.content.Context;

/**
 * This class implements the Adapter concept so that types that need to use the
 * Android Context type can do so without having to directly depend on other
 * types in android.jar.
 * 
 */
public abstract class AbstractApplicationAdapter {
	public static Context context;
}
