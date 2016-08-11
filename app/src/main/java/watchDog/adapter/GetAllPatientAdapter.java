package watchDog.adapter;

import info.androidhive.slidingmenu.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class GetAllPatientAdapter extends BaseAdapter {
	private JSONArray dataArray;
	private Activity activity;

	private static LayoutInflater inflater = null;

	public GetAllPatientAdapter(JSONArray jsonArray, Activity a) {
		this.dataArray = jsonArray;
		this.activity = a;

		inflater = (LayoutInflater) this.activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return this.dataArray.length();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public void notifyDataSetChanged() {
		// TODO Auto-generated method stub
		super.notifyDataSetChanged();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ListView listView;

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.patient_list_item, null);
			listView = new ListView(activity);

			convertView.setTag(listView);
		}
		try {
			JSONObject jsonObject = this.dataArray.getJSONObject(position);
			String name = jsonObject.getString("patientName");
			TextView textView1 = (TextView) convertView.findViewById(R.id.empty);
			textView1.setText(name);

		} catch (JSONException je) {

			je.printStackTrace();
		}

		return convertView;
	}

}