package com.example.app1;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class NewUserActivity extends Activity {

	private EditText textUser;
	private EditText textPass;
	private EditText textRPass;
	private Button bSubmit;
	private static final String SOAP_ACTION = "http://services.web.org/";
	private static final String METHOD_NAME_NUSER = "newUser";
	private static final String NAMESPACE = "http://services.web.org/";
	private static final String URL = "http://192.168.43.241:8080/DogMeIn/DogMeInWeb?wsdl";
	private Thread webThread;
	private Handler h;
	private ProgressDialog prDialog;
	private Context mContext;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_user);

		textUser = (EditText) this.findViewById(R.id.newUser);
		textPass = (EditText) this.findViewById(R.id.newUserPassword);
		textRPass = (EditText) this.findViewById(R.id.newUserRPassword);
		bSubmit = (Button) this.findViewById(R.id.addUser);
		mContext = this.getApplicationContext();
		
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
					// Todo OK
				} else {
					// Error al insertar.
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
					int result = insertUser(user, pass);
					h.sendEmptyMessage(result);
				}
			});

			prDialog = ProgressDialog.show(NewUserActivity.this, "Connecting...",
					"Adding user: " + user, true, false);
			webThread.start();
		}
	}

	private int insertUser(String user, String pass) {
		int resultado = 0;
		try {
			SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_NUSER);

			request.addProperty("userName", user);
			request.addProperty("password", pass);

			SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			sobre.dotNet = false;
			sobre.setOutputSoapObject(request);

			HttpTransportSE transporte = new HttpTransportSE(URL);
			// Llamada
			transporte.call(SOAP_ACTION + METHOD_NAME_NUSER, sobre);

			// Resultado
			resultado = Integer.parseInt(sobre.getResponse().toString());
		} catch (HttpResponseException e) {
			resultado = -1;
		} catch (IOException e) {
			resultado = -1;
		} catch (XmlPullParserException e) {
			resultado = -1;
		} catch (ClassCastException e){
			resultado = -1;
		} 
		return resultado;
	}

}
