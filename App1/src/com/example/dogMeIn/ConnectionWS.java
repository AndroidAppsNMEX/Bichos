package com.example.dogMeIn;

import com.example.dogMeIn.models.Owner;

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
}
