package com.example.app1.DAO;

import java.sql.Connection;

import com.example.app1.models.Pet;

public interface PetDAO {

	static final String TABLE_PET = "DOGPARK.PET";

	void newPet(Pet pet);
	
	Pet findPetById(Integer petId);
	
	void deletePet(Pet pet);
	
	void setConnection(Connection connection);

}
