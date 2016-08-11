package watchDog.adapter;

import info.androidhive.slidingmenu.R;

import java.util.Calendar;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class GetAllTaskAdapter extends BaseAdapter {
	private JSONArray dataArray;
	private Activity activity;
	private ImageView imageView;
	private static LayoutInflater inflater = null;
	String datetime;
	Date date;

	public GetAllTaskAdapter(JSONArray jsonArray, Activity a) {
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
			convertView = inflater
					.inflate(R.layout.fragment_task_manager, null);
			listView = new ListView(activity);

			convertView.setTag(listView);
		}
		try {
			TextView textView = (TextView) convertView.findViewById(R.id.empty);
			TextView textView1 = (TextView) convertView
					.findViewById(R.id.empty1);
			TextView textView2 = (TextView) convertView
					.findViewById(R.id.empty2);

			JSONObject jsonObject = this.dataArray.getJSONObject(position);
			String status = jsonObject.getString("status");
			String name = jsonObject.getString("patientName");

			Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR);
			int min = c.get(Calendar.MINUTE);

			textView.setText(name);
			textView1.setText(status);
			textView2.setText(hour + ":" + min);
			textView2.setTextColor(Color.CYAN);
			imageView = (ImageView) convertView.findViewById(R.id.imageView1);
			/*
			 * Animation animation = new TranslateAnimation(0, 0, 0, 1000);
			 * animation.setDuration(1000); textView.startAnimation(animation);
			 * textView1.startAnimation(animation);
			 * textView2.startAnimation(animation);
			 * imageView.startAnimation(animation);
			 * textView.setVisibility(View.VISIBLE);
			 * textView2.setVisibility(View.VISIBLE);
			 * imageView.setVisibility(View.VISIBLE);
			 */

			if (status.equalsIgnoreCase("RED")) {
				imageView.setImageResource(R.drawable.r);

			}
			if (status.equalsIgnoreCase("YELLOW")) {
				imageView.setImageResource(R.drawable.y);

			}
			if (status.equalsIgnoreCase("GREEN")) {
				imageView.setImageResource(R.drawable.g);

			}

		} catch (JSONException je) {

			je.printStackTrace();
		}

		return convertView;
	}
}