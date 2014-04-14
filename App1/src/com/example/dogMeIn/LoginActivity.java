package com.example.dogMeIn;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
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

public class LoginActivity extends Activity {

	private Button bLogin;
	private Button bNewUser;
	private EditText textUserName;
	private EditText textPassword;
	private Owner owner;
	private String userName;
	private String password;
	private ConnectionWS connectionWS = new ConnectionWSClass();
	private ConnectionSQLite connectionSQLite = new ConnectionSQLiteClass();
	private Thread webThread = new Thread(new Runnable() {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			int result = 0;
			connectionWS.login(userName, password);
			if (owner.getOwnerID() > 0) {
				result = connectionSQLite.insertUserSQLite(mContext, owner);
				hLogin.sendEmptyMessage(result);
			} else {
				hLogin.sendEmptyMessage(owner.getOwnerID());
			}
		}

	});
	private Context mContext;
	@SuppressLint("HandlerLeak")
	private Handler hLogin = new Handler() {
		public void handleMessage(Message msg) {
			prLogin.dismiss();
			if (msg.what == 0) {
				// Show the main menu's Activity.
				// Login ok;
				Intent i = new Intent(mContext, MainMenuActivity.class);
				i.putExtra("ownerID", owner.getOwnerID());
				startActivity(i);
			} else {
				// Error al insertar.

			}
		}
	};
	private ProgressDialog prLogin;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		textUserName = (EditText) findViewById(R.id.loginUserName);
		textPassword = (EditText) findViewById(R.id.loginPassword);
		bNewUser = (Button) this.findViewById(R.id.loginAddUser);
		bLogin = (Button) this.findViewById(R.id.login);

		mContext = LoginActivity.this;

		bNewUser.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onClickNewUser();
			}
		});

		bLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onClickLogin();
			}
		});

	}

	private void onClickNewUser() {
		Intent i = new Intent(this, NewUserActivity.class);
		startActivity(i);
	}

	private void onClickLogin() {
		userName = textUserName.getText().toString();
		password = textPassword.getText().toString();	
		prLogin = ProgressDialog.show(mContext, "Login...", "Login...");
		webThread.start();
	}
}
