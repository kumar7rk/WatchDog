package watchDog;

import info.androidhive.slidingmenu.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

public class TaskInfoEditYellow extends Activity {

	@SuppressWarnings("unused")
	private TextView nameEt, statusEt, locationEt, taskIdEt, timer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.yellow);
		timer = (TextView) findViewById(R.id.timer);
		Timer();
	}

	public void Timer() {
		timer = (TextView) findViewById(R.id.timer);
		// TODO Auto-generated method stub
		new CountDownTimer(600000, 1000) {// CountDownTimer(edittext1.getText()+edittext2.getText())
			// also parse it to long

			@SuppressLint("NewApi")
			public void onTick(long millisUntilFinished) {
				timer.setText(""
						+ String.format(
								"%d : %d mins",
								TimeUnit.MILLISECONDS
										.toMinutes(millisUntilFinished),
								TimeUnit.MILLISECONDS
										.toSeconds(millisUntilFinished)
										- TimeUnit.MINUTES
												.toSeconds(TimeUnit.MILLISECONDS
														.toMinutes(millisUntilFinished))));
				// here you can have your logic to set text to edittext
			}

			public void onFinish() {
				timer.setText("done!");
			}
		}.start();
	}

	@SuppressWarnings("unused")
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

			new CountDownTimer(600000, 1000) {

				public void onTick(long millisUntilFinished) {
					
					
					if(TaskInfoEditYellow.this!=null && timer==null) timer = (TextView) TaskInfoEditYellow.this.findViewById(R.id.timer);  
					timer.setText(""
							+ String.format(
									"%d : %d mins",
									TimeUnit.MILLISECONDS
											.toMinutes(millisUntilFinished),
									TimeUnit.MILLISECONDS
											.toSeconds(millisUntilFinished)
											- TimeUnit.MINUTES
													.toSeconds(TimeUnit.MILLISECONDS
															.toMinutes(millisUntilFinished))));
					// here you can have your logic to set text to edittext
				}

				public void onFinish() {
			timer.setText("done");		
				}
			}.start();
		}

		@Override
		protected Void doInBackground(String... arg) {
			// TODO Auto-generated method stub

			String url = "http://10.201.45.122:8080/WatchDog_Server/updateTaskStatus.action";
//			String url = "http://10.201.42.134:8080/WatchDog_Server/updateTaskStatus.action";

			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(url);
			String taskId = taskIdEt.getText().toString();

			String current_status = statusEt.getText().toString();

			if (current_status.equalsIgnoreCase("Yellow")) {
				current_status = "GREEN";
			}

			else if (current_status.equalsIgnoreCase("RED")) {
				current_status = "YELLOW";

			} else if (current_status.equalsIgnoreCase("green")) {
				current_status = "RED";
			}
			try {
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
						10);

				nameValuePairs.add(new BasicNameValuePair("status",
						current_status));
				nameValuePairs.add(new BasicNameValuePair("taskId", taskId));

				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				HttpResponse response = httpclient.execute(httppost);
				//
				//
				// runOnUiThread(new Runnable() {
				//
				// public void run() {
				//
				// String url =
				// "http://10.201.45.122:8080/WatchDog_Server/updateTaskStatus.action";
				//
				// HttpClient httpclient = new DefaultHttpClient();
				// HttpPost httppost = new HttpPost(url);
				// String taskId = taskIdEt.getText().toString();
				//
				//
				// HttpResponse response = null;
				// try {
				// response = httpclient.execute(httppost);
				// } catch (ClientProtocolException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// } catch (IOException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				// String res = response.toString();
				// JSONObject jsonObj = null;
				// try {
				// jsonObj = new JSONObject(res);
				// } catch (JSONException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				// String result = null;
				// try {
				// result = jsonObj.getString("updateResult");
				// } catch (JSONException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				//
				// Toast.makeText(getApplicationContext(), "response" + result,
				// 1000).show();
				// }
				// });

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
			} catch (IOException e) {
				// TODO Auto-generated catch block
			}

			return null;
		}
	}
}