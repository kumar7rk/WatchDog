package watchDog;

import info.androidhive.slidingmenu.R;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PatientInfoEdit extends Activity {

	private EditText nameEt, locationEt, contactEt;
	Button button;
//	String url = "http://10.201.45.122:8080/WatchDog_Server/getAllPatients.action";
	String url = "http://10.201.43.38:8080/WatchDog_Server/getAllPatients.action";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.patient_info_edit);

		nameEt = (EditText) findViewById(R.id.editText2);
		locationEt = (EditText) findViewById(R.id.editText5);
		contactEt = (EditText) findViewById(R.id.editText6);
		button = (Button) findViewById(R.id.button1);
		Intent in = getIntent();

		final String name = in.getStringExtra("name");
		final String location = in.getStringExtra("limit");
		final String contact = in.getStringExtra("contactNum");

		nameEt.setText(name);
		locationEt.setText(location);
		contactEt.setText(contact);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Log.e("buttonSaveclick", "buttonupdatclick");
				new editInfo().execute(name, location, contact);
				Toast.makeText(PatientInfoEdit.this, name, Toast.LENGTH_SHORT)
						.show();
			}
		});
	}

	private class editInfo extends AsyncTask<String, Void, Void> {

		@Override
		protected Void doInBackground(String... arg) {
			// TODO Auto-generated method stub
			/*
			 * String naame = arg[0]; String s5 = arg[1]; String s6 = arg[2];
			 * List<NameValuePair> params = new ArrayList<NameValuePair>();
			 * params.add(new BasicNameValuePair("s2", s2)); params.add(new
			 * BasicNameValuePair("s5", s5)); params.add(new
			 * BasicNameValuePair("s6", s6));
			 * 
			 * ServiceHandler serviceClient = new ServiceHandler();
			 * 
			 * String json = serviceClient.makeServiceCall(url,
			 * ServiceHandler.POST, params);
			 * 
			 * Log.d("Create Prediction Request: ", "> " + json);
			 * 
			 * if (json != null) { try {
			 * 
			 * JSONObject jsonObj = new JSONObject(json); Iterator<?> x =
			 * jsonObj.keys(); JSONArray jsonArray = new JSONArray();
			 * 
			 * while (x.hasNext()){ String key = (String) x.next();
			 * jsonArray.put(jsonObj.get(key)); } boolean error =
			 * jsonObj.getBoolean("error"); // checking for error node in json
			 * if (!error) { // new category created successfully
			 * Log.e(" added successfully ", "> " +
			 * jsonObj.getString("message")); } else { Log.e("  Error: ", "> " +
			 * jsonObj.getString("message")); }
			 * 
			 * } catch (JSONException e) { e.printStackTrace(); }
			 * 
			 * } else { Log.e("JSON Data", "JSON data error!"); }
			 */return null;
		}
	}
}