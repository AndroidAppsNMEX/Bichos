package com.example.app1.DAO;

import java.sql.Connection;
import java.util.List;

import com.example.app1.models.Breed;

public interface BreedDAO {

	Breed findBreedById(Integer breedID);

	List<Breed> findAllBreeds();
	
	void setConnection(Connection connection);
}
