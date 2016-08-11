package watchDog;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

public class ApiConnectorTaskManager {
	String url = "http://10.201.43.38:8080/WatchDog_Server/getAllTasks.action";
	String url1 = "http://10.201.43.38:8080/WatchDog_Server/getTaskById.action";
	
	public JSONArray GetAllTask() {

		HttpEntity httpEntity = null;

		try {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);

			HttpResponse httpResponse = httpClient.execute(httpGet);

			httpEntity = httpResponse.getEntity();

		} catch (ClientProtocolException e) {

			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		JSONArray jsonArray = null;

		if (httpEntity != null) {
			try {
				String entityResponse = EntityUtils.toString(httpEntity);

				Log.e("Entity Response : ", entityResponse);
				jsonArray = new JSONArray(entityResponse);

			} catch (JSONException e) {
				e.printStackTrace(); 
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return jsonArray;
	}

	@SuppressWarnings("unused")
	public JSONArray GetTaskDetails(int TaskID) {

		HttpEntity httpEntity = null;

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url1);

		try {
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
					10);
			String taskId = Integer.toString(TaskID);
			nameValuePairs.add(new BasicNameValuePair("taskId", taskId));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			HttpResponse response = httpclient.execute(httppost);

		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
		} catch (IOException e) {
			// TODO Auto-generated catch block
		}

		JSONArray jsonArray = null;

		if (httpEntity != null) {
			try {
				String entityResponse = EntityUtils.toString(httpEntity);

				Log.e("Entity Response : ", entityResponse);
				jsonArray = new JSONArray(entityResponse);

			} catch (JSONException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return jsonArray;

	}

}