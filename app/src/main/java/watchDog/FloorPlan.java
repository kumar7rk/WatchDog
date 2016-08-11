package watchDog;

import info.androidhive.slidingmenu.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
//import android.opengl.Matrix;

public class FloorPlan extends Fragment {

		ImageView imageView; 
	public FloorPlan() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_floor_plan,
				container, false);
		imageView = (ImageView)rootView.findViewById(R.id.floorplan);
		return rootView;
	}
	
}
