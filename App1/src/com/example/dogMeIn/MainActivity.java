package com.example.dogMeIn;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.example.dogMeIn.db.DogMeInProvider;
import com.example.dogMeIn.models.Owner;

@SuppressLint("HandlerLeak")
public class MainActivity extends Activity {

	private ProgressDialog prALogin;
	private Handler hALogin = new Handler() {
		public void handleMessage(Message msg) {
			prALogin.dismiss();
			if (msg.what == 0) {
				// Lanzar menu
				Toast.makeText(mContext, "Login Correcto.", Toast.LENGTH_SHORT)
						.show();
				Intent i = new Intent(mContext, MainMenuActivity.class);
				i.putExtra("ownerID", owner.getOwnerID());
				startActivity(i);
			} else {
				// Lanzar ventana de login
				Dialog dialog = onCreateDialog(0);
				dialog.show();
			}
		}
	};
	private Thread webThread;
	private Context mContext;
	private Owner owner;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mContext = MainActivity.this;

		webThread = new Thread(new Runnable() {
			public void run() {
				int result = checkOwner();
				hALogin.sendEmptyMessage(result);
			}
		});

		prALogin = ProgressDialog.show(MainActivity.this, "Checking...",
				"Checking user", true, false);
		webThread.start();
	}

	private int checkOwner() {
		int result = 0;
		Cursor c = getContentResolver().query(
				DogMeInProvider.CONTENT_URI_OWNER, null, null, null, "DESC");
		if (c == null || c.getCount() == 0) {
			result = -1;
		} else {
			owner = new Owner();
			owner.setOwnerID(c.getInt(0));
			owner.setOwnerName(c.getString(1));
			owner.setOwnerPassword(c.getString(2));
		}
		return result;
	}

	protected Dialog onCreateDialog(int id) {
		AlertDialog.Builder builder;
		builder = new AlertDialog.Builder(mContext);
		builder.setMessage(
				"Doesn't exist any owner in your device. Have you created any other owner before?")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								Intent i = new Intent(mContext,
										LoginActivity.class);
								startActivity(i);
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Intent i = new Intent(mContext, NewUserActivity.class);
						startActivity(i);
					}
				});
		return builder.create();
	}
}
