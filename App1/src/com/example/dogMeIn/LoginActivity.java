package com.example.dogMeIn;

import com.example.dogMeIn.R;

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
		bLogin = (Button) this.findViewById(R.id.login);
		
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
	
	private void onClickNewUser(){
		Intent i = new Intent(this, NewUserActivity.class);
		startActivity(i);
	}
	
	private void onClickLogin(){
		Intent i = new Intent(this, NewPetActivity.class);
		i.putExtra("ownerID", 0);
		startActivity(i);
	}
}
