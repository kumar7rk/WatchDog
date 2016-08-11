package watchDog;

import info.androidhive.slidingmenu.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TaskInfoEditRed extends Activity {

	private TextView nameEt, destinationEt, locationEt, caregiverEt;
	Button button;

	String url = "http://10.201.43.38:8080/WatchDog_Server/getAllTasks.action";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.red);

		startService(new Intent(this, BroadcastService.class));

		nameEt = (TextView) findViewById(R.id.nameEt);
		destinationEt = (TextView) findViewById(R.id.destinationEt);
		locationEt = (TextView) findViewById(R.id.locationEt);
		caregiverEt = (TextView) findViewById(R.id.caregiverEt);
		button = (Button) findViewById(R.id.button1);

		Intent in = getIntent();

		final String name = in.getStringExtra("name");
		final String status = in.getStringExtra("status");
		final String location = in.getStringExtra("location");
		final String destination = in.getStringExtra("destination");
		final String relatedCgId = in.getStringExtra("relatedCgId");

		nameEt.setText(name);
		destinationEt.setText(destination);
		locationEt.setText(location);
		caregiverEt.setText(relatedCgId);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new editInfo().execute(name, status, location);
				// Intent(android.content.Intent.ACTION_VIEW,
				// Uri.parse("http://maps.google.com/maps?saddr=The University of Adelaide, Adelaide SA 5005, Australia&daddr= Hungry Jack's Rundle Street 6-10 Rundle Mall, York Street, Adelaide SA 5000, Australia"));
				// startActivity(intent);
				finish();
			}
		});
	}

	private class editInfo extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
		}

		@Override
		protected void onProgressUpdate(Void... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);

		}

		@SuppressWarnings("unused")
		@Override
		protected Void doInBackground(String... arg) {
			// TODO Auto-generated method stub

			String url = "http://10.201.43.38:8080/WatchDog_Server/updateTaskStatus.action";

			Intent in = getIntent();

			String current_status = in.getStringExtra("status");
			String taskId = in.getStringExtra("taskId");

			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);

			current_status = "YELLOW";
			try {
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
						10);

				nameValuePairs.add(new BasicNameValuePair("status",
						current_status));
				nameValuePairs.add(new BasicNameValuePair("taskId", taskId));

				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				HttpResponse response = httpclient.execute(httppost);

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}

			return null;
		}
	}
}