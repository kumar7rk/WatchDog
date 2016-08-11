package watchDog;

import info.androidhive.slidingmenu.R;

import android.app.Activity;
import android.content.Intent;

import android.os.Bundle;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class PatientInfo extends Activity {

	TextView nameTv, limitTv, contactTv;
	Button updateB;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_patient_info);
//		getActionBar().setDisplayHomeAsUpEnabled(true);

		Intent in = getIntent();

		final String name = in.getStringExtra("name");
		final String limit = in.getStringExtra("limit");
		final String contact = in.getStringExtra("contactNum");

		nameTv = (TextView) findViewById(R.id.textView2);
		limitTv = (TextView) findViewById(R.id.textView5);
		contactTv = (TextView) findViewById(R.id.textView6);

		nameTv.setText(name);
		limitTv.setText(limit);
		contactTv.setText(contact);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		MenuItem item = menu.findItem(R.id.refresh);      
	    item.setVisible(false);  
	    
		return true;
	  
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		switch (item.getItemId()) {
		case R.id.action_setting:
			return true;
		case R.id.edit:
			Intent in = getIntent();

			final String name = in.getStringExtra("name");
			final String limit = in.getStringExtra("limit");
			final String contact = in.getStringExtra("contactNum");

			Intent i = new Intent(getApplicationContext(),
					PatientInfoEdit.class);
			i.putExtra("name", name);
			i.putExtra("limit", limit);
			i.putExtra("contactNum", contact);

			startActivity(i);

			return true;
		default:
			return super.onOptionsItemSelected(item);

		}
	}

}
