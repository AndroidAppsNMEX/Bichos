package com.example.dogMeIn;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.dogMeIn.db.ConnectionSQLite;
import com.example.dogMeIn.db.ConnectionSQLiteClass;
import com.example.dogMeIn.models.Owner;
import com.example.dogMeIn.networking.ConnectionWS;
import com.example.dogMeIn.networking.ConnectionWSClass;

@SuppressLint("HandlerLeak")
public class NewUserActivity extends Activity {

	private EditText textUser;
	private EditText textPass;
	private EditText textRPass;
	private Button bSubmit;
	private static final int DIALOG_ERROR_INSERT = 0;
	private static final int DIALOG_OK_INSERT = 1;
	private Thread webThread;
	private Handler h;
	private ProgressDialog prDialog;
	private Context mContext;
	private Owner owner;
	private ConnectionWS connectionWS = new ConnectionWSClass();
	private ConnectionSQLite connectionSQLite = new ConnectionSQLiteClass();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_user);

		textUser = (EditText) this.findViewById(R.id.newUser);
		textPass = (EditText) this.findViewById(R.id.newUserPassword);
		textRPass = (EditText) this.findViewById(R.id.newUserRPassword);
		bSubmit = (Button) this.findViewById(R.id.addUser);
		mContext = NewUserActivity.this;

		bSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onClickAddUser();

			}
		});

		h = new Handler() {
			public void handleMessage(Message msg) {
				prDialog.dismiss();
				if (msg.what == 0) {
					// Show the main menu's Activity.
					// executeMainMenuActivity();
					onCreateDialog(DIALOG_OK_INSERT).show();
				} else {
					// Error al insertar.
					onCreateDialog(DIALOG_ERROR_INSERT).show();
				}
			}
		};

	}

	private void onClickAddUser() {
		final String user = textUser.getText().toString();
		final String pass = textPass.getText().toString();
		String rpass = textRPass.getText().toString();

		if (!pass.equals(rpass)) {
			// Mostrar Error.
		} else if (pass.isEmpty() || user.isEmpty() || rpass.isEmpty()) {
			// Mostrar Error2.
		} else {
			webThread = new Thread(new Runnable() {
				public void run() {
					int result = connectionWS.insertUserWS(user, pass);
					if (result == 0) {
						owner = connectionWS.recoverOwner(user);
						result = connectionSQLite.insertUserSQLite(mContext, owner);
					}
					h.sendEmptyMessage(result);
				}
			});

			prDialog = ProgressDialog.show(NewUserActivity.this,
					"Connecting...", "Adding user: " + user, true, false);
			webThread.start();
		}
	}

	protected Dialog onCreateDialog(int id) {
		AlertDialog.Builder builder;
		builder = new AlertDialog.Builder(mContext);
		switch (id) {
		case DIALOG_OK_INSERT:
			builder.setMessage("Owner created.")
					.setCancelable(false)
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									Intent i = new Intent(mContext,
											MainMenuActivity.class);
									i.putExtra("ownerID", owner.getOwnerID());
									startActivity(i);
								}
							});
			break;
		case DIALOG_ERROR_INSERT:
			builder.setMessage("Error to create the owner.")
					.setCancelable(false)
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// TODO Auto-generated method stub
									dialog.cancel();
								}
							});
			break;
		}

		return builder.create();
	}

}
