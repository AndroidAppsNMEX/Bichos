package com.example.app1;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.app1.models.Breed;
import com.example.app1.models.Pet;

public class MainActivity extends Activity {

	private EditText textName;
	private EditText textAge;
	private Spinner spinBreed;
	private List<Breed> breedList;
	private ArrayAdapter<Breed> breedSpAdapter;
	private Button bSubmit;
	private final int DIALOG_BAD_DB = 1;
	private final int DIALOG_EXIT = 2;
	private final int DIALOG_CORRECT = 3;
	private final int METHOD_OPT_SUBMIT = 0;
	private final int METHOD_OPT_BREED = 1;
	private Context mContext;
	private static final String SOAP_ACTION = "http://services.web.org/";
	private static final String METHOD_NAME_SUBMIT = "newPet";
	private static final String METHOD_NAME_BREED = "findAllBreeds";
	private static final String NAMESPACE = "http://services.web.org/";
	private static final String URL = "http://192.168.43.241:8080/DogMeIn/DogMeInWeb?wsdl";
	private Thread webThread;
	private Handler h;
	private Pet pet;
	private Integer ownerID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Bundle bundle = getIntent().getExtras();
		
		int ownerId = bundle.getInt("ownerID");
		ownerID = ownerId;
		
		textName = (EditText) this.findViewById(R.id.loginUserName);
		textAge = (EditText) this.findViewById(R.id.newUserPassword);
		spinBreed = (Spinner) this.findViewById(R.id.spinner1);
		mContext = this.getApplicationContext();
		bSubmit = (Button) this.findViewById(R.id.login);

		bSubmit.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Toast.makeText(mContext, "onCLick button.", Toast.LENGTH_LONG)
						.show();
				try{
					pet = extractPet();
					webThread = new Thread(new Runnable() {
						public void run() {
							int result = soapAction(METHOD_OPT_SUBMIT);
							if (result == 0) {
								h.sendEmptyMessage(0);
							} else {
								h.sendEmptyMessage(-1);
							}
						}
					});
					
					webThread.start();
					webThread.join();
				} catch(RuntimeException e){
					Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_LONG)
					.show();
				} catch(InterruptedException e){
					Toast.makeText(mContext, "Impossible connect to server", Toast.LENGTH_LONG)
					.show();
				}
			}
		});

		h = new Handler() {
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

		webThread = new Thread(new Runnable() {
			public void run() {
				int result = soapAction(METHOD_OPT_BREED);
				if (result == 0) {
					h.sendEmptyMessage(0);
				} else {
					h.sendEmptyMessage(-1);
				}
			}
		});

		webThread.start();
		try {
			webThread.join();
			if (breedList != null) {
				breedSpAdapter = new ArrayAdapter<Breed>(this,
						android.R.layout.simple_spinner_item, breedList);
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
			result = submit();
			break;
		case METHOD_OPT_BREED:
			result = recoverBreed();
			break;
		}

		return result;

	}

	private int recoverBreed() {

		int result = 0;
		try {
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_BREED);

			SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			sobre.dotNet = false;
			sobre.setOutputSoapObject(request);

			HttpTransportSE transporte = new HttpTransportSE(URL);
			// Llamada
			transporte.call(SOAP_ACTION + METHOD_NAME_BREED, sobre);

			sobre.toString();
			// Resultado
			Vector<Object> resultado = (Vector<Object>) sobre.getResponse();

			if (resultado == null) {
				result = -1;
			} else {
				result = crearLista(resultado);
			}

		} catch (Exception e) {
			result = -1;
		}
		return result;
	}

	private int submit() {
		int result = 0;
		try {
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_SUBMIT);

		      request.addProperty("breedID", pet.getBreedID());
		      request.addProperty("ownerID", pet.getOwnerID());
		      request.addProperty("petAge", pet.getPetAge());
		      request.addProperty("petDesc", pet.getPetDesc());
		      request.addProperty("petGender", pet.getPetGender().toString());
		      request.addProperty("petID", pet.getPetID());
		      request.addProperty("petName", pet.getPetName());
		      request.addProperty("petNick", pet.getPetNick());
		      request.addProperty("petPathPhoto", pet.getPetPathPhoto());
		      
			SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			sobre.dotNet = false;
			sobre.setOutputSoapObject(request);

			HttpTransportSE transporte = new HttpTransportSE(URL);
			// Llamada
			transporte.call(SOAP_ACTION + METHOD_NAME_SUBMIT, sobre);

			result = (Integer) sobre.getResponse();
		} catch (Exception e) {
			result = -1;
		}
		return result;
	}

	private int crearLista(Vector<Object> vector) {
		/*int result = 0;
		breedList = new ArrayList<Breed>();
		try {
			for (int i = 0; i < vector.size(); i++) {
				Log.i("Vector [" + i + "]", "V: " + vector.get(i).toString());
				String element = vector.get(i).toString();

				String preElement = element.substring(8);
				Log.i("Vector [" + i + "]", "V: " + preElement);

				String sufElement = preElement.substring(0,
						preElement.length() - 3);
				Log.i("Vector [" + i + "]", "V: " + sufElement);

				StringTokenizer tokens = new StringTokenizer(sufElement, ";");

				String id = "";
				String des = "";

				int tok = 0;
				while (tokens.hasMoreTokens()) {
					String token = tokens.nextToken().trim();
					int posEqual = token.indexOf("=");
					Log.i("Igual", "int: " + posEqual + "");
					if (tok == 0) {
						des = token.substring(posEqual + 1);
					} else {
						id = token.substring(posEqual + 1);
					}
					tok++;
				}
				breedList.add(new Breed(id, des));
			}
		} catch (Exception e) {
			result = -2;
		}
		return result;*/
		return 0;
	}

	public Pet extractPet() {
		Pet p = new Pet();
		if (textName.getText().toString() != ""
				&& textAge.getText().toString() != "") {
			p.setPetName(textName.getText().toString());
			p.setPetAge(Integer.parseInt(textAge.getText().toString()));
			p.setOwnerID(ownerID);
			p.setBreedID(((Breed) spinBreed.getSelectedItem()).getBreedID());
		} else {
			throw new RuntimeException("Missing required fields.");
		}
		return p;
	}
}
