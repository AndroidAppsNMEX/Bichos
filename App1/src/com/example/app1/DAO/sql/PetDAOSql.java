package com.example.app1.DAO.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.app1.DAO.PetDAO;
import com.example.app1.exceptions.PetException;
import com.example.app1.models.Gender;
import com.example.app1.models.Pet;

public class PetDAOSql implements PetDAO {

	private Connection connection;

	@Override
	public void newPet(Pet pet) {
		PreparedStatement st = null;

		if (connection == null) {
			throw new PetException("Doesn't exist Database connection");
		}

		try {
			st = connection.prepareStatement("INSERT INTO " + TABLE_PET
					+ " (ID_pet, ID_BREED, DES_IPET, DES_NAME, GENDER,"
					+ " AGE, PET_DESC, PHOTO) VALUES (?,?,?,?,?,?,?,?)");
			st.setInt(1, pet.getPetID());
			st.setInt(2, pet.getBreedID());
			st.setString(3, pet.getPetNick());
			st.setString(4, pet.getPetName());
			st.setInt(5, pet.getPetGender().ordinal());
			st.setInt(6, pet.getPetAge());
			st.setString(7, pet.getPetDesc());
			st.setString(8, pet.getPetPathPhoto());

			st.execute();
		} catch (SQLException e) {
			throw new PetException(e.getMessage());
		} finally {
			try {
				if (st != null) {
					st.close();
				}
			} catch (SQLException e) {
				throw new PetException("Error to close the database connection");
			}
		}

	}

	@Override
	public Pet findPetById(Integer petId) {
		PreparedStatement st = null;
		ResultSet rs = null;
		Pet pet = null;

		if (connection == null) {
			throw new PetException("Doesn't exist Database connection");
		}

		try {
			st = connection.prepareStatement("SELECT * FROM " + TABLE_PET
					+ "WHERE ID_PET = ?");
			st.setInt(1, petId);

			rs = st.executeQuery();

			while (rs.next()) {
				pet = new Pet();
				pet.setPetID(rs.getInt("ID_PET"));
				pet.setOwnerID(rs.getInt("ID_OWNER"));
				pet.setBreedID(rs.getInt("ID_BREED"));
				pet.setPetNick(rs.getString("DES_IPET"));
				pet.setPetName(rs.getString("DES_NAME"));
				int gender = rs.getInt("GENDER");
				pet.setPetGender(gender == 0 ? Gender.Male : Gender.Female);
				pet.setPetDesc(rs.getString("PET_DESC"));
				pet.setPetPathPhoto(rs.getString("PHOTO"));
			}
			return pet;

		} catch (SQLException e) {
			throw new PetException(e.getMessage());
		} finally {
			try {
				if (st != null && rs != null) {
					rs.close();
					st.close();
				}
			} catch (SQLException e) {
				throw new PetException("Error to close the database connection");
			}
		}
	}

	@Override
	public void deletePet(Pet pet) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setConnection(Connection connection) {
		// TODO Auto-generated method stub
		this.connection = connection;
	}

}
