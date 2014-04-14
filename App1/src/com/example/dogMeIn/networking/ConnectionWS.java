package com.example.dogMeIn.networking;

import java.util.List;

import com.example.dogMeIn.models.Breed;
import com.example.dogMeIn.models.Owner;
import com.example.dogMeIn.models.Pet;

public interface ConnectionWS {

	static final String SOAP_ACTION = "http://services.web.org/";
	static final String METHOD_NAME_LOGIN = "login";
	static final String METHOD_NAME_NUSER = "newUser";
	static final String METHOD_NAME_USER = "findOwnerByNick";
	static final String NAMESPACE = "http://services.web.org/";
	static final String URL = "http://192.168.43.241:8080/DogMeIn/DogMeInWeb?wsdl";
	static final String METHOD_NAME_SUBMIT = "newPet";
	static final String METHOD_NAME_BREED = "findAllBreeds";
	
	int insertUserWS(String user, String pass);
	
	Owner recoverOwner(String user);
	
	Owner login(String userName, String password);
	
	int submit(Pet pet);
	
	int recoverBreed();
	
	List<Breed> getBreedList();
}
