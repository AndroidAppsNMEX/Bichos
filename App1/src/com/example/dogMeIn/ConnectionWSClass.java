package com.example.dogMeIn;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import com.example.dogMeIn.models.Owner;

public class ConnectionWSClass implements ConnectionWS {

	private Owner parseOwner(SoapObject requestSOAP) {
		SoapObject root = (SoapObject) requestSOAP.getProperty("return");
		Owner owner = new Owner();

		String userName = "";
		Integer userId = -3;
		String password = "";
		for (int i = 0; i < root.getPropertyCount(); i++) {
			Object property = root.getProperty(i);

			if (i == 1) {
				userName = property.toString();
			} else if (i == 0) {
				userId = Integer.parseInt(property.toString());
			} else {
				password = property.toString();
			}

		}
		owner.setOwnerID(userId);
		owner.setOwnerName(userName);
		owner.setOwnerPassword(password);
		return owner;
	}
	
	public int insertUserWS(String user, String pass) {
		int resultado = 0;
		try {
			SoapObject request = new SoapObject(ConnectionWS.NAMESPACE, ConnectionWS.METHOD_NAME_NUSER);

			request.addProperty("userName", user);
			request.addProperty("password", pass);

			SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			sobre.dotNet = false;
			sobre.setOutputSoapObject(request);

			HttpTransportSE transporte = new HttpTransportSE(ConnectionWS.URL);
			// Llamada
			transporte.call(ConnectionWS.SOAP_ACTION + ConnectionWS.METHOD_NAME_NUSER, sobre);

			// Resultado
			resultado = Integer.parseInt(sobre.getResponse().toString());
		} catch (HttpResponseException e) {
			resultado = -1;
		} catch (IOException e) {
			resultado = -1;
		} catch (XmlPullParserException e) {
			resultado = -1;
		} catch (ClassCastException e) {
			resultado = -1;
		}
		return resultado;
	}
	
	public Owner recoverOwner(String user) {
		Owner owner = new Owner();
		try {
			SoapObject request = new SoapObject(ConnectionWS.NAMESPACE, ConnectionWS.METHOD_NAME_USER);

			request.addProperty("nickName", user);
			SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			sobre.dotNet = false;
			sobre.setOutputSoapObject(request);

			HttpTransportSE transporte = new HttpTransportSE(ConnectionWS.URL);
			// Llamada
			transporte.call(ConnectionWS.SOAP_ACTION + ConnectionWS.METHOD_NAME_USER, sobre);

			// Resultado
			SoapObject resultRequestSOAP = (SoapObject) sobre.bodyIn;
			// Parse del XML
			owner = parseOwner(resultRequestSOAP);
		} catch (HttpResponseException e) {
			owner.setOwnerID(-1);
		} catch (IOException e) {
			owner.setOwnerID(-1);
		} catch (XmlPullParserException e) {
			owner.setOwnerID(-1);
		} catch (ClassCastException e) {
			owner.setOwnerID(-1);
		}
		return owner;
	}
	
	public Owner login(String userName, String password) {
		Owner owner = new Owner();
		try {
			SoapObject request = new SoapObject(ConnectionWS.NAMESPACE, ConnectionWS.METHOD_NAME_LOGIN);

			request.addProperty("userName", userName);
			request.addProperty("password", password);
			
			SoapSerializationEnvelope sobre = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			sobre.dotNet = false;
			sobre.setOutputSoapObject(request);

			HttpTransportSE transporte = new HttpTransportSE(ConnectionWS.URL);
			// Llamada
			transporte.call(ConnectionWS.SOAP_ACTION + ConnectionWS.METHOD_NAME_LOGIN, sobre);

			// Resultado
			SoapObject resultRequestSOAP = (SoapObject) sobre.bodyIn;
			// Parse del XML
			owner = parseOwner(resultRequestSOAP);
		} catch (HttpResponseException e) {
			owner.setOwnerID(-1);
		} catch (IOException e) {
			owner.setOwnerID(-1);
		} catch (XmlPullParserException e) {
			owner.setOwnerID(-1);
		} catch (ClassCastException e) {
			owner.setOwnerID(-1);
		}
		return owner;
	}

}
