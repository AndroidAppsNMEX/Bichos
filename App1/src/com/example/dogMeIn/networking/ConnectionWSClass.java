package com.example.dogMeIn.networking;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import com.example.dogMeIn.models.Breed;
import com.example.dogMeIn.models.Owner;
import com.example.dogMeIn.models.Pet;

public class ConnectionWSClass implements ConnectionWS {

	public List<Breed> breedList;
	
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
	
	public int submit(Pet pet) {
		int result = 0;
		try {
			SoapObject request = new SoapObject(ConnectionWS.NAMESPACE, ConnectionWS.METHOD_NAME_SUBMIT);
			request.addProperty("breedID", pet.getBreedID());
			request.addProperty("ownerID", pet.getOwnerID());
			request.addProperty("petAge", pet.getPetAge());
			request.addProperty("petDesc", pet.getPetDesc());
			request.addProperty("petGender", pet.getPetGender().toString());
			request.addProperty("petID", pet.getPetID());
			request.addProperty("petName", pet.getPetName());
			request.addProperty("petNick", pet.getPetNick());
			request.addProperty("petPathPhoto", pet.getPetPathPhoto());

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.dotNet = false;
			envelope.setOutputSoapObject(request);

			HttpTransportSE transporte = new HttpTransportSE(ConnectionWS.URL);
			// Llamada
			transporte.call(ConnectionWS.SOAP_ACTION + ConnectionWS.METHOD_NAME_SUBMIT, envelope);

			result = Integer.parseInt(envelope.getResponse().toString());
		} catch (Exception e) {
			result = -1;
			e.printStackTrace();
		}
		return result;
	}
	
	public int recoverBreed() {

		int result = 0;
		try {
			SoapObject request = new SoapObject(ConnectionWS.NAMESPACE, ConnectionWS.METHOD_NAME_BREED);

			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
					SoapEnvelope.VER11);
			envelope.dotNet = false;
			envelope.setOutputSoapObject(request);

			HttpTransportSE transporte = new HttpTransportSE(ConnectionWS.URL);
			// Llamada
			transporte.call(ConnectionWS.SOAP_ACTION + ConnectionWS.METHOD_NAME_BREED, envelope);

			// Resultado
			SoapObject requestSOAP = (SoapObject) envelope.bodyIn;

			if (requestSOAP == null) {
				result = -1;
			} else {
				result = createBreedList(requestSOAP);
			}

		} catch (Exception e) {
			e.printStackTrace();
			result = -1;
		}
		return result;
	}
	
	private int createBreedList(SoapObject requestSOAP) {
		int result = 0;
		breedList = new ArrayList<Breed>();
		try {
			for (int i = 0; i < requestSOAP.getPropertyCount(); i++) {
				SoapObject breed = (SoapObject) requestSOAP.getProperty(i);

				Breed b = new Breed();
				b.setBreedID(Integer.parseInt(breed
						.getPropertyAsString("breedID")));
				b.setBreedDes(breed.getPropertyAsString("breedDes"));

				breedList.add(b);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public List<Breed> getBreedList(){
		return breedList;
	}

}
