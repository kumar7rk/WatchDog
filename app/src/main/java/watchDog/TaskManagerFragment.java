package watchDog;

import info.androidhive.slidingmenu.R;

import org.json.JSONArray;
import org.json.JSONObject;

import watchDog.adapter.GetAllTaskAdapter;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class TaskManagerFragment extends ListFragment {

	private ListView getAllTaskListView;
	private JSONArray jsonArray;
	private ProgressDialog dialog;
	private boolean internet = true;
	AlertDialogManager alertDialogManager = new AlertDialogManager();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		if (savedInstanceState != null) {
			getFragmentManager().beginTransaction()
					.add(android.R.id.content, new TaskManagerFragment())
					.commit();
		}
	}

	@SuppressLint("ShowToast")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_task_manager,
				container, false);
		getAllTaskListView = (ListView) rootView
				.findViewById(android.R.id.list);
		isNetworkAvailable();
		if (internet == true)
			new GetAllTask().execute(new ApiConnectorTaskManager());

		else
			Toast.makeText(getActivity(), "Connect to internet ", 1000).show();
		return rootView;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.main, menu);
		MenuItem item = menu.findItem(R.id.edit);      
	    item.setVisible(false);  
	    
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
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

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getActivity()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		if (activeNetworkInfo != null && activeNetworkInfo.isConnected())
			internet = true;
		else
			internet = false;

		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);

		getAllTaskListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						try {

							JSONObject TaskClicked = jsonArray
									.getJSONObject(position);
							String name = TaskClicked.getString("patientName");
							String location = TaskClicked.getString("location");
							String status = TaskClicked.getString("status");
							String taskId = TaskClicked.getString("taskId");
							String destination = TaskClicked.getString("destination");
							String relatedCgId = TaskClicked.getString("relatedCgId");
							

							Intent i = new Intent();
							if (status.equalsIgnoreCase("yellow")) {
								i = new Intent(getActivity()
										.getApplicationContext(),
										TaskInfoEditYellow.class);
								i.putExtra("location", location);
								i.putExtra("status", status);
								i.putExtra("name", name);
								i.putExtra("taskId", taskId);
								i.putExtra("destination", destination);
								i.putExtra("relatedCgId", relatedCgId);

								startActivity(i);

							}
							if (status.equalsIgnoreCase("red")) {
								i = new Intent(getActivity()
										.getApplicationContext(),
										TaskInfoEditRed.class);
								i.putExtra("location", location);
								i.putExtra("status", status);
								i.putExtra("name", name);
								i.putExtra("destination", destination);
								i.putExtra("relatedCgId", relatedCgId);
								i.putExtra("taskId", taskId);
								startActivity(i);
							}
							if (status.equalsIgnoreCase("green")) {
								alertDialogManager
										.showAlertDialog(
												getActivity(),
												"Rescue operation completed",
												"Patient was rescued 10 minutes ago",
												true);
							}

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
	}
	public void setListAdapter(JSONArray jsonArray) {
		this.jsonArray = jsonArray;

		// if (internet == true)
		getAllTaskListView.setAdapter(new GetAllTaskAdapter(jsonArray,
				getActivity()));

	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		dialog.dismiss();

		new GetAllTask().execute(new ApiConnectorTaskManager());

	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		dialog.dismiss();

	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	private class GetAllTask extends
			AsyncTask<ApiConnectorTaskManager, Long, JSONArray> {
		protected JSONArray doInBackground(ApiConnectorTaskManager... params) {
			return params[0].GetAllTask();
		}
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			dialog = new ProgressDialog(getActivity());
			dialog.setTitle("Loading...");
			dialog.setMessage("Fetching data");
			dialog.setCancelable(false);
			dialog.show();
		}
		@Override
		protected void onPostExecute(JSONArray jsonArray) {
			if (dialog.isShowing())
				dialog.dismiss();
			setListAdapter(jsonArray);
		}
	}
}