package com.example.app1.DAO.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.example.app1.DAO.BreedDAO;
import com.example.app1.exceptions.BreedException;
import com.example.app1.models.Breed;

public class BreedDAOSql implements BreedDAO {

	private Connection connection;

	@Override
	public Breed findBreedById(Integer breedID) throws BreedException {

		Breed breed = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		if (connection == null) {
			throw new BreedException("Doesn't exist BBDD connection");
		}

		try {
			st = connection
					.prepareStatement("SELECT BREED_DESC FROM DOGPARK.BREED WHERE ID_BREED = ?");
			st.setInt(1, breedID);
			rs = st.executeQuery();
			while (rs.next()) {
				breed = new Breed();
				breed.setBreedDes(rs.getString("BREED_DESC"));
			}

		} catch (SQLException e) {
			throw new BreedException(e.getMessage());
		} finally {
			try {
				if (st != null && rs != null) {
					rs.close();
					st.close();
				}
			} catch (SQLException e) {
				throw new BreedException("Error to close the BBDD connection");
			}
		}

		return breed;
	}

	@Override
	public List<Breed> findAllBreeds() {
		List<Breed> breeds = null;
		PreparedStatement st = null;
		ResultSet rs = null;

		if (connection == null) {
			throw new BreedException("Doesn't exist BBDD connection");
		}

		try {
			st = connection
					.prepareStatement("SELECT BREED_DESC FROM DOGPARK.BREED");
			rs = st.executeQuery();
			breeds = new ArrayList<Breed>();
			while (rs.next()) {
				Breed breed = new Breed();
				Integer id = rs.getInt("ID_BREED");
				String desc = rs.getString("BREED_DESC");
				breed.setBreedID(id);
				breed.setBreedDes(desc);
				breeds.add(breed);
			}
			return breeds;

		} catch (SQLException e) {
			throw new BreedException(e.getMessage());
		} finally {
			try {
				if (st != null && rs != null) {
					rs.close();
					st.close();
				}
			} catch (SQLException e) {
				throw new BreedException("Error to close the BBDD connection");
			}
		}
	}

	@Override
	public void setConnection(Connection connection) {
		// TODO Auto-generated method stub
		this.connection = connection;
	}

}
