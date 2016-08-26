package watchDog.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import info.androidhive.slidingmenu.R;

public class ImageAdapter extends BaseAdapter {
	private Context mContext;

	// Keep all Images in array
	public Integer[] mThumbIds = { R.drawable.ic_pages, R.drawable.ic_people,
			R.drawable.ic_photos, R.drawable.ic_whats_hot };
	final public String[] mainMenuArray = { "Create a new class",
			"Take Attendance", "Check Up Status", "Update/Modify Students",
			"Delete/Update class", "Add students"

	};

	// Constructor
	public ImageAdapter(Context c) {
		mContext = c;
	}

	@Override
	public int getCount() {
		return mThumbIds.length;
	}

	@Override
	public Object getItem(int position) {
		return mThumbIds[position];
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		//View grid;
		//LayoutInflater inflater = (LayoutInflater) mContext
		//		.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		ImageView imageView = new ImageView(mContext);
		imageView.setImageResource(mThumbIds[position]);
		imageView.setScaleType(ImageView.ScaleType.FIT_START);
		imageView.setLayoutParams(new GridView.LayoutParams(500, 500));
		TextView textView = new TextView(mContext);
		textView.setText(mainMenuArray[position]);

		return imageView;
	}

}
