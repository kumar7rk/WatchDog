package watchDog.fragments;

import android.app.ListFragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import info.androidhive.slidingmenu.R;
import watchDog.activities.PatientInfo;
import watchDog.adapter.GetAllPatientAdapter;
import watchDog.connector.ApiConnector;

public class PatientFragment extends ListFragment {

	private ListView getAllPatientListView;
	private JSONArray jsonArray;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//
		View v = inflater.inflate(R.layout.patient_list_item, container, false);

		getAllPatientListView = (ListView) v.findViewById(android.R.id.list);

		new GetAllPatientTask().execute(new ApiConnector());
		return v;

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public void onPrepareOptionsMenu(Menu menu) {
		super.onPrepareOptionsMenu(menu);
		menu.findItem(R.id.refresh).setVisible(false);

	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getAllPatientListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						try {
							// Get the Patient which was clicked
							JSONObject PatientClicked = jsonArray
									.getJSONObject(position);
							String contact = PatientClicked
									.getString("contactNum");
							String limit = PatientClicked
									.getString("forbiddenArea");
							String name = ((TextView) view
									.findViewById(R.id.empty)).getText()
									.toString();
							Toast.makeText(getActivity(), name,
									Toast.LENGTH_LONG).show();

							// Send Patient ID
							Intent showDetails = new Intent(getActivity()
									.getApplicationContext(), PatientInfo.class);
							showDetails.putExtra("limit", limit);
							showDetails.putExtra("contactNum", contact);
							showDetails.putExtra("name", name);

							startActivity(showDetails);

						} catch (Exception e) {
							e.printStackTrace();

						}
					}

				});

	}

	public void setListAdapter(JSONArray jsonArray) {
		this.jsonArray = jsonArray;
		getAllPatientListView.setAdapter(new GetAllPatientAdapter(
				jsonArray, getActivity()));
	}

	private class GetAllPatientTask extends
			AsyncTask<ApiConnector, Long, JSONArray> {
		@Override
		protected JSONArray doInBackground(ApiConnector... params) {

			// it is executed on Background thread
			return params[0].GetAllPatient();
		}

		@Override
		protected void onPostExecute(JSONArray jsonArray) {

			setListAdapter(jsonArray);
		}
	}

}