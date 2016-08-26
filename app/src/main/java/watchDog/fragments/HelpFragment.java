package watchDog.fragments;

import info.androidhive.slidingmenu.R;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HelpFragment extends Fragment {
	//AlertDialogManager alertDialogManager = new AlertDialogManager();

	AlertDialog alertDialog;

	public HelpFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_help, container,
				false);
		String home_message = "1.Home Screen gives you the access to the functionality of the application\n\n";
		String patientInformation_message = "2. Patient information tab lets you access the data of all the patient including information like their name forbidden areas etc\n\n";
		String floorPlan_message = "3.Through this you could see the floor plan and therefore familarize yourself with the locations of the reader\n\n";
		String Elopments_message = "4.This tab gives you the main functionality of the application whenever a patient moves out of their room you'll be notified\n\n";
		alertDialog = new AlertDialog.Builder(getActivity()).create();
		alertDialog.setTitle("Help");
		alertDialog.setIcon(R.drawable.success);
		alertDialog.setMessage(home_message + "" + patientInformation_message
				+ "" + home_message + "" + patientInformation_message + ""
				+ floorPlan_message + "" + Elopments_message);
		
		alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int which) {

					}
				});
		alertDialog
				.setOnDismissListener(new DialogInterface.OnDismissListener() {
					@Override
					public void onDismiss(DialogInterface dialog) {


						Fragment newFragment = new HomeFragment();
						FragmentTransaction transaction = getFragmentManager()
								.beginTransaction();

						transaction.replace(R.id.frame_container, newFragment);
						transaction.addToBackStack(null);

						transaction.commit();

					}
				});
		alertDialog.show();
	


//		alertDialogManager.showAlertDialog(getActivity(), "Help", home_message
//				+ "" + patientInformation_message + "" + home_message + ""
//				+ patientInformation_message + "" + floorPlan_message + ""
//				+ Elopments_message, true);
		return rootView;
	}
}
