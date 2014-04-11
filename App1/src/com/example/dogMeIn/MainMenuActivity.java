package com.example.dogMeIn;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.dogMeIn.R;

public class MainMenuActivity extends Activity {

	private Integer ownerID;
	private Button bNewPet;
	private Button bMap;
	private Button bMyProfile;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);

		Bundle bundle = getIntent().getExtras();

		int ownerId = bundle.getInt("ownerID");
		ownerID = ownerId;

		bNewPet = (Button) findViewById(R.id.bNewPet);
		bMap = (Button) findViewById(R.id.bMap);
		bMyProfile = (Button) findViewById(R.id.bmyProfile);

		bNewPet.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onClickNewPet();

			}
		});

		bMap.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onClickMap();

			}
		});

		bMyProfile.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				onClickMyProfile();

			}
		});
	}

	private void onClickNewPet() {
		Intent i = new Intent(this, NewPetActivity.class);
		i.putExtra("ownerID", ownerID);
		startActivity(i);
	}

	private void onClickMyProfile() {
		Intent i = new Intent();
		i.putExtra("ownerID", ownerID);

	}

	private void onClickMap() {
		Intent i = new Intent();
		i.putExtra("ownerID", ownerID);
	}
}
