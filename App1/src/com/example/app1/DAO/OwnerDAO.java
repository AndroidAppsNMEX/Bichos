package com.example.app1.DAO;

import java.sql.Connection;
import com.example.app1.models.Owner;

public interface OwnerDAO {

	static final String TABLE_OWNER = "DOGPARK.OWNER";

	void newOwner(Owner owner);
	
	Owner findOwnerById(Integer ownerId);
	
	void deleteOwner(Owner owner);
	
	void setConnection(Connection connection);

}
