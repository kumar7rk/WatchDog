package watchDog;

import info.androidhive.slidingmenu.R;

import java.util.ArrayList;

import watchDog.adapter.NavDrawerListAdapter;
import watchDog.model.NavDrawerItem;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	AlertDialogManager alert = new AlertDialogManager();
	AlertDialog alertDialog;

	// Session Manager Class
	SessionManager session;
	// nav drawer title
	private CharSequence mDrawerTitle;

	private boolean internet = true;
	// used to store app title
	private CharSequence mTitle;
	// slide menu items
	private String[] navMenuTitles;
	private TypedArray navMenuIcons;

	private ArrayList<NavDrawerItem> navDrawerItems;
	private NavDrawerListAdapter adapter;
	private boolean doubleBackToExitPressedOnce;

	@SuppressLint({ "NewApi", "ShowToast" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		isNetworkAvailable();

		session = new SessionManager(getApplicationContext());
		session.checkLogin();
		if (internet == false)
			Toast.makeText(getApplicationContext(),
					"Not Connected to internet", 1000).show();

		mTitle = mDrawerTitle = getTitle();

		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		// adding nav drawer items to array
		// Home
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0], navMenuIcons
				.getResourceId(0, -1)));
		// Patient information
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1], navMenuIcons
				.getResourceId(1, -1)));
		// Configure reader
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2], navMenuIcons
				.getResourceId(2, -1)));
		// Priority task, Will add a counter here
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3], navMenuIcons
				.getResourceId(3, -1)/* , true, "80" */));
		// Help
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4], navMenuIcons
				.getResourceId(4, -1)));
		// About us, We will add a counter here
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5], navMenuIcons
				.getResourceId(5, -1)));

		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, // nav menu toggle icon
				R.string.app_name, // nav drawer open - description for
									// accessibility
				R.string.app_name // nav drawer close - description for
									// accessibility
		) {
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}
	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			isNetworkAvailable();
			if(internet)
			displayView(position);
			else 
				Toast.makeText(getApplicationContext(), "working internet connection needed" , Toast.LENGTH_SHORT).show(); 
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// getMenuInflater().inflate(R.menu.main, menu);

		return true;
	}

	public boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		if (activeNetworkInfo != null && activeNetworkInfo.isConnected())
			internet = true;
		else {
			internet = false;
		}
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		switch (item.getItemId()) {
		case R.id.refresh:

			Fragment newFragment = new TaskManagerFragment();
			FragmentTransaction transaction = getFragmentManager()
					.beginTransaction();

			transaction.replace(R.id.frame_container, newFragment);
			transaction.addToBackStack(null);

			transaction.commit();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onBackPressed() {

		if (doubleBackToExitPressedOnce) {
			super.onBackPressed();
			return;
		}

		// if(getFragmentManager().getBackStackEntryCount() > 0) {
		// getFragmentManager().popBackStack();
		// setTitle(mDrawerTitle);
		// }

		this.doubleBackToExitPressedOnce = true;
		Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				doubleBackToExitPressedOnce = false;
			}
		}, 2000);

	}

	/* *
	 * Called when invalidateOptionsMenu() is triggered
	 */
	// @Override
	// public boolean onPrepareOptionsMenu(Menu menu) {
	// // if nav drawer is opened, hide the action items
	// boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
	// menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
	// return super.onPrepareOptionsMenu(menu);
	// }

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new HomeFragment();
			break;
		case 1:
			fragment = new PatientFragment();
			break;
		case 2:
			fragment = new FloorPlan();
			break;
		case 3:
			fragment = new TaskManagerFragment();
			break;
		case 4:

			String home_message = "1.Home Screen gives you the access to the functionality of the application\n\n";
			String patientInformation_message = "2. Patient information tab lets you access the data of all the patient including information like their name forbidden areas etc\n\n";
			String floorPlan_message = "3.Through this you could see the floor plan and therefore familarize yourself with the locations of the reader\n\n";
			String Elopments_message = "4.This tab gives you the main functionality of the application whenever a patient moves out of their room you'll be notified\n\n";
			alertDialog = new AlertDialog.Builder(MainActivity.this).create();
			alertDialog.setTitle("Help");
			alertDialog.setIcon(R.drawable.success);
			alertDialog.setMessage(home_message + ""
					+ patientInformation_message + "" + home_message + ""
					+ patientInformation_message + "" + floorPlan_message + ""
					+ Elopments_message);

			alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {

						}
					});
			alertDialog
					.setOnDismissListener(new DialogInterface.OnDismissListener() {
						@Override
						public void onDismiss(DialogInterface dialog) {

							Fragment newFragment = new HomeFragment();
							FragmentTransaction transaction = getFragmentManager()
									.beginTransaction();

							transaction.replace(R.id.frame_container,
									newFragment);
							transaction.addToBackStack(null);

							transaction.commit();

						}
					});
			alertDialog.show();

			break;
		case 5:
			session.logoutUser();
			Toast.makeText(getApplicationContext(), "Logout successful", Toast.LENGTH_SHORT)
					.show();
			break;

		default:
			break;
		}
		if (fragment != null) {
			// if(internet == true){
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction().addToBackStack("tag")
					.replace(R.id.frame_container, fragment).commit();

			// update selected item and title, then close the drawer
			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			setTitle(navMenuTitles[position]);

			// mDrawerList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			// mDrawerList.setItemChecked(position, true); // Testing
			// mDrawerList.setSelection(1);
			mDrawerLayout.closeDrawer(mDrawerList);
			// }

			mDrawerList.setBackgroundColor(Color.BLACK);

			// else Toast.makeText(getApplicationContext(),
			// "Please Check your interent connection", 1000);
		} else {
			// error in creating fragment
			Log.e("MainActivity", "Error in creating fragment");
		}
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

}
