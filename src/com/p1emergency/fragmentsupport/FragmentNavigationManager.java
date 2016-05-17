package com.p1emergency.fragmentsupport;

import com.p1emergency.fragmentsupport.AbstractBaseFragmentActivity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;

public class FragmentNavigationManager {
  
  public NavUtils navUtils; 

  public FragmentManager fragmentManager;  
  public static final int DEFAULT_MAIN_FRAGMENT_ID = -1;
  
  /*
   * Navigate to the specified Fragment.
   * 
   * @param fragment to navigate to
   * @param addToBackStack if true adds the fragment to back stack   
   */  
  public void navigateToFragment(
      Fragment fragment, 
      int containerViewId,
      boolean addToBackStack) {
    
    if (fragment != null && fragmentManager != null) {
      
      FragmentTransaction ft = fragmentManager.beginTransaction();     
      ft.replace(containerViewId, fragment);
      if (addToBackStack) ft.addToBackStack(null); 
      ft.commit();   
    }    
  }

  /*
   * Navigate to the specified Fragment and add the fragment to back stack by default. 
   * 
   * @param fragment to navigate to
   * @param container view id
   */
  public void navigateToFragment(
      Fragment fragment, 
      int containerViewId) {    
    
    navigateToFragment(fragment, containerViewId, true);    
  }  
  
  
  /*
   * Navigate to the specified Fragment.  
   * 
   * @param base activity
   * @param fragment to navigate to
   * @param container view id
   * @param addToBackStack if true adds the fragment to back stack   
   */
  public static void navigateToFragment(
      AbstractBaseFragmentActivity activity, 
      Fragment fragment, 
      int containerViewId,
      boolean addToBackStack) {   
    
    if (fragment != null && activity != null) {      
      FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
      ft.replace(containerViewId, fragment);
      if (addToBackStack) ft.addToBackStack(null); 
      ft.commit();   
    } 
  }  
  
  
  /*
   * Navigate to the specified Fragment and add the fragment to back stack by default. 
   * 
   * @param base activity
   * @param fragment to navigate to
   * @param container view id    
   */
  public static void navigateToFragment(
      AbstractBaseFragmentActivity activity, 
      Fragment fragment,
      int containerViewId) {   
    
    navigateToFragment(activity, fragment, containerViewId, true);
  } 
  
  
  /*
   * Navigate to the specified Fragment.
   * 
   * @param base activity
   * @param fragment class to navigate to
   * @param container view id
   * @param addToBackStack if true adds the fragment to back stack     
   */  
  public static <T> void navigateToFragment(
      AbstractBaseFragmentActivity activity, 
      final Class<T> fragmentClass, 
      int containerViewId,
      boolean addToBackStack) {   
    
      Fragment fragment = Fragment.instantiate(activity, fragmentClass.getName());    
      FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
      ft.replace(containerViewId, fragment);
      if (addToBackStack) ft.addToBackStack(null); 
      ft.commit();   
    
  }   
  
  
  /*
   * Navigate to the specified Fragment and add the fragment to back stack by default. 
   * 
   * @param base activity
   * @param fragment class to navigate to   
   * @param container view id    
   */  
  public static <T> void navigateToFragment(
      AbstractBaseFragmentActivity activity, 
      final Class<T> fragmentClass,
      int containerViewId) {   
    
     navigateToFragment(activity, fragmentClass, containerViewId, true);      
  }  
  
  /**
   * Wrapper for NavUtils.navigateUpTo (sourceActivity, upIntent);
   * Navigate from sourceActivity to the activity specified by upIntent, finishing sourceActivity in the process. 
   * upIntent will have the flag FLAG_ACTIVITY_CLEAR_TOP set by this method, along with any others required for 
   * proper up navigation as outlined in the Android Design Guide. This method should be used when performing up 
   * navigation from within the same task as the destination. If up navigation should cross tasks in some cases, 
   * see shouldUpRecreateTask(Activity, Intent).  
   * 
   * @param sourceActivity The current activity from which the user is attempting to navigate up
   * @param upIntent An intent representing the target destination for up navigation
   */
  
  public static void navigateUpTo(
      AbstractBaseFragmentActivity sourceActivity, 
      Intent upIntent){
    
    NavUtils.navigateUpTo (sourceActivity, upIntent);
  }
  
  /**
   * Wrapper for NavUtils.shouldUpRecreateTask(sourceActivity, targetIntent);  
   * Returns true if sourceActivity should recreate the task when navigating 'up' by using targetIntent.
   * If this method returns false the app can trivially call navigateUpTo(Activity, Intent) using the same 
   * parameters to correctly perform up navigation. If this method returns false, the app should synthesize 
   * a new task stack by using TaskStackBuilder or another similar mechanism to perform up navigation.   
   * 
   * @param sourceActivity The current activity from which the user is attempting to navigate up
   * @param targetIntent An intent representing the target destination for up navigation
   * @return true if navigating up should recreate a new task stack, false if the same task should be used 
   * for the destination
   */
  public static boolean shouldUpRecreateTask (
      AbstractBaseFragmentActivity sourceActivity, 
      Intent targetIntent){
    
    return NavUtils.shouldUpRecreateTask(sourceActivity, targetIntent);    
  }
  
}
