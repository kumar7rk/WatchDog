package watchDog;

import info.androidhive.slidingmenu.R;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;

public class HomeDuplicateFragment extends Fragment {
//	private ArrayAdapter<String> gridAdapter;
	ImageAdapter gridAdapter1;
	ImageView imageView;

	public HomeDuplicateFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater
				.inflate(R.layout.grid_layout, container, false);
		GridView gridView = (GridView) rootView.findViewById(R.id.grid_view);
		// imageView = (ImageView) rootView.findViewById(R.id.imageView1);
		gridView.setAdapter(new ImageAdapter(getActivity()));

		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Fragment fragment = null;
				switch (position) {
				case 0:
					fragment = new TaskManagerFragment();

					break;
				case 1:
					fragment = new PatientFragment();

					break;

				default:
					break;
				}
				FragmentTransaction transaction = getFragmentManager()
						.beginTransaction();

				transaction.replace(R.id.frame_container, fragment);
				transaction.addToBackStack(null);

				transaction.commit();
			}
		});

		return rootView;
	}
}
// Intent intent = new Intent(getActivity(), AndroidGridLayoutActivity.class);
// startActivity(intent);