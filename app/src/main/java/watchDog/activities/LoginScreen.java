package watchDog.activities;

import info.androidhive.slidingmenu.R;
import watchDog.manager.AlertDialogManager;
import watchDog.connector.ApiConnectorLogin;
import watchDog.manager.SessionManager;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class LoginScreen extends Activity {

	private EditText usernameEt, passwordEt;
	private Button loginB;
	String username = null, password = null;
	private ProgressBar dialog1;
	AlertDialogManager alert = new AlertDialogManager();
	SessionManager session;
	private boolean flag = true, internet = true;

	@SuppressLint("ShowToast")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login_screen);

		isNetworkAvailable();
		if (internet == false)
			Toast.makeText(getApplicationContext(),
					"No Internet Connectivity ", 1000).show();
		session = new SessionManager(getApplicationContext());

		usernameEt = (EditText) findViewById(R.id.username);
		passwordEt = (EditText) findViewById(R.id.password);
		loginB = (Button) findViewById(R.id.LoginB);
		dialog1 = (ProgressBar) findViewById(R.id.progressBar1);

		loginB.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				username = usernameEt.getText().toString();
				password = passwordEt.getText().toString();
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(passwordEt.getWindowToken(), 0);
				imm.hideSoftInputFromWindow(usernameEt.getWindowToken(), 0);
				isNetworkAvailable();
				if (internet == false) {
					// Toast.makeText(getApplicationContext(),
					// "No Internet Connectivity ", 1000).show();
					alert.showAlertDialog(
							LoginScreen.this,
							"No Internet Connectivity",
							"Please connect to internet to access the application",
							false);
				} else {
					if (username.trim().length() > 0
							&& password.trim().length() > 0)
						new Authentication().execute(username, password);
					else if (username.trim().length() == 0)
						alert.showAlertDialog(LoginScreen.this,
								"Login failed..", "Enter username", false);
					else if (password.trim().length() == 0)
						alert.showAlertDialog(LoginScreen.this,
								"Login failed..", "Enter password", false);
				}
			}
		});

	}

	private boolean isNetworkAvailable() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager
				.getActiveNetworkInfo();
		if (activeNetworkInfo != null && activeNetworkInfo.isConnected())
			internet = true;
		else
			internet = false;

		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	private class Authentication extends AsyncTask<String, Void, Void> {

		AlertDialog alertDialog;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			alertDialog = new AlertDialog.Builder(LoginScreen.this).create();

			dialog1.setVisibility(1);

			loginB.setClickable(false);

		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			loginB.setClickable(true);

			dialog1.setVisibility(11);

			if (flag == false) {

				passwordEt.setText("");
				alertDialog.setTitle("Login failed");
				alertDialog.setIcon(R.drawable.fail);
				alertDialog.setMessage("Please check you login details");

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
								passwordEt.requestFocus();
							}
						});
				alertDialog.show();
			}

		}

		@SuppressLint({ "NewApi", "ShowToast" })
		@Override
		protected Void doInBackground(String... params) {
			// TODO Auto-generated method stub
			ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
			postParameters.add(new BasicNameValuePair("userName", usernameEt
					.getText().toString()));
			postParameters.add(new BasicNameValuePair("password", passwordEt
					.getText().toString()));

			String response = null;
			try {
				response = ApiConnectorLogin
						.executeHttpPost(
								"http://10.201.43.38:8080/WatchDog_Server/mobileLogin.action",
								postParameters);

				String res = response.toString();

				res = res.replaceAll("\\s+", "");

				JSONObject jsonObj = new JSONObject(res);
				String results = null;
				results = jsonObj.getString("result");

				final String result = results.toString();
				if (result.equalsIgnoreCase("success")) {
					String userId = jsonObj.getString("userId");
					session.createLoginSession(username, userId);

					Intent intent = new Intent(getApplicationContext(),
							MainActivity.class);
					startActivity(intent);
					finishAffinity();

				} else if (result.equalsIgnoreCase("failure")) {
					flag = false;
					alert.showAlertDialog(getApplicationContext(),
							"Login failed..", "Username/Password is incorrect",
							false);
				}

				runOnUiThread(new Runnable() {

					public void run() {

					}
				});

			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	@SuppressLint("NewApi")
	@Override
	public void onBackPressed() {

		finishAffinity();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}
}
