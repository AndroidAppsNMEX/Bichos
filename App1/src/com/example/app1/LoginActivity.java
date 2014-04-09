package com.example.app1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LoginActivity extends Activity {

	private Button bLogin;
	private Button bNewUser;
	
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		bNewUser = (Button) this.findViewById(R.id.loginAddUser);
		
		bNewUser.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onClickNewUser();
			}
		});
	}
	
	private void onClickNewUser(){
		Intent i = new Intent(this, NewUserActivity.class);
		startActivity(i);
	}
}
