package com.example.app1;

import java.sql.SQLException;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.app1.models.Breed;

public class MainActivity extends Activity {

	private EditText textName;
	private EditText textAge;
	private Spinner spinBreed;
	private List<Breed> breedList;
	private ArrayAdapter<Breed> breedSpAdapter;
	private final int DIALOG_BAD_DB = 1;
	private final int DIALOG_EXIT = 2;
	private final int DIALOG_CORRECT = 3;
	private Context mContext;
	private static final String SOAP_ACTION = "http://services.web.org/findAllBreeds";
	private static final String METHOD_NAME = "findAllBreeds";
	private static final String NAMESPACE = "http://services.web.org/";
	private static final String URL = "http://192.168.1.130:8080/WebServices/DogParkServices?wsdl";
	private Thread webThread;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		textName = (EditText) this.findViewById(R.id.editText1);
		textAge = (EditText) this.findViewById(R.id.editText2);
		spinBreed = (Spinner) this.findViewById(R.id.spinner1);
		mContext = this.getApplicationContext();
		webThread = new Thread(new Runnable() {
			public void run() {
				soapAction();
			}
		});
		
		webThread.start();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void insertPet(View view) throws ClassNotFoundException,
			SQLException {

		textName.setText("Connection OK");

	}

	private void soapAction() {
		try {
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

			//request.addProperty("nick", "pepe");
			//request.addProperty("pass", "Xarly");

			SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			sobre.dotNet = false;
			sobre.setOutputSoapObject(request);

			HttpTransportSE transporte = new HttpTransportSE(URL);

			// Llamada
			transporte.call(SOAP_ACTION, sobre);

			// Resultado
			SoapObject resultado = (SoapObject) sobre.getResponse();

			List<Breed> breeds = (List<Breed>) resultado;
			textName.setText(breeds.get(0).getBreedDes());
			Log.i("Resultado", resultado.toString());

		} catch (Exception e) {
			textName.setText("Error " + e.getMessage());
			// Toast.makeText(mContext, e.getMessage(),
			// Toast.LENGTH_LONG).show();
		}
	}

}
