package com.example.dogMeIn;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dogMeIn.models.Breed;
import com.example.dogMeIn.models.Gender;
import com.example.dogMeIn.models.Pet;
import com.example.dogMeIn.networking.ConnectionWS;
import com.example.dogMeIn.networking.ConnectionWSClass;

public class NewPetActivity extends Activity {

	private EditText textName;
	private EditText textAge;
	private EditText textNick;
	private Spinner spinBreed;
	private ArrayAdapter<Breed> breedSpAdapter;
	private Button bSubmit;
	private RadioButton rbMale;
	private RadioGroup rgGender;
	private final int METHOD_OPT_SUBMIT = 0;
	private final int METHOD_OPT_BREED = 1;
	private Context mContext;
	private Thread webThread;
	private Handler hBreedList;
	private Handler hSubmit;
	private Pet pet;
	private Integer ownerID;
	private ConnectionWS connectionWS = new ConnectionWSClass();

	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_pet);

		Bundle bundle = getIntent().getExtras();

		int ownerId = bundle.getInt("ownerID");
		ownerID = ownerId;

		textName = (EditText) this.findViewById(R.id.newPetName);
		textAge = (EditText) this.findViewById(R.id.newPetAge);
		textNick = (EditText) this.findViewById(R.id.newPetNick);
		spinBreed = (Spinner) this.findViewById(R.id.spBreed);
		mContext = this.getApplicationContext();
		bSubmit = (Button) this.findViewById(R.id.login);
		rgGender = (RadioGroup) findViewById(R.id.rgGender);
		rbMale = (RadioButton) findViewById(R.id.rbMale);
		
		rgGender.clearCheck();
		rgGender.check(rbMale.getId());

		bSubmit.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Toast.makeText(mContext, "onCLick button.", Toast.LENGTH_LONG)
						.show();
				try {
					pet = extractPet();
					webThread = new Thread(new Runnable() {
						public void run() {
							int result = soapAction(METHOD_OPT_SUBMIT);
							if (result == 0) {
								hSubmit.sendEmptyMessage(0);
							} else {
								hSubmit.sendEmptyMessage(-1);
							}
						}
					});

					webThread.start();
					webThread.join();

				} catch (RuntimeException e) {
					Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG)
							.show();
				} catch (InterruptedException e) {
					Toast.makeText(mContext, "Impossible connect to server",
							Toast.LENGTH_LONG).show();
				}
			}
		});

		hBreedList = new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what == 0) {
					Toast.makeText(mContext, "The DB is online.",
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(mContext, "The DB is offline.",
							Toast.LENGTH_LONG).show();
				}
			}
		};

		hSubmit = new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what == 0) {
					onClickSubmit();
				} else {
					Toast.makeText(mContext, "The DB is offline.",
							Toast.LENGTH_LONG).show();
				}
			}
		};

		webThread = new Thread(new Runnable() {
			public void run() {
				int result = soapAction(METHOD_OPT_BREED);
				if (result == 0) {
					hBreedList.sendEmptyMessage(0);
				} else {
					hBreedList.sendEmptyMessage(-1);
				}
			}
		});

		webThread.start();
		try {
			webThread.join();
			if (connectionWS.getBreedList() != null) {
				breedSpAdapter = new ArrayAdapter<Breed>(this,
						android.R.layout.simple_spinner_item, connectionWS.getBreedList());
				breedSpAdapter
						.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
				spinBreed.setAdapter(breedSpAdapter);
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private int soapAction(int method) {

		int result = 0;

		switch (method) {
		case METHOD_OPT_SUBMIT:
			result = connectionWS.submit(pet);
			break;
		case METHOD_OPT_BREED:
			result = connectionWS.recoverBreed();
			break;
		}

		return result;

	}

	private Pet extractPet() {
		Pet p = new Pet();
		if (textName.getText().toString() != ""
				&& textAge.getText().toString() != "") {
			p.setPetName(textName.getText().toString());
			p.setPetAge(Integer.parseInt(textAge.getText().toString()));
			p.setPetNick(textNick.getText().toString());
			p.setOwnerID(ownerID);
			p.setBreedID(((Breed) spinBreed.getSelectedItem()).getBreedID());
			if(rbMale.isChecked()){
				p.setPetGender(Gender.Male);
			} else {
				p.setPetGender(Gender.Female);
			}
		} else {
			throw new RuntimeException("Missing required fields.");
		}
		return p;
	}

	private void onClickSubmit() {
		Intent i = new Intent(this, MainMenuActivity.class);
		i.putExtra("ownerID", ownerID);
		startActivity(i);
	}
}
