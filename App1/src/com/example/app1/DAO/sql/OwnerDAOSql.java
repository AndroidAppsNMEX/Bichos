package com.example.app1.DAO.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.app1.DAO.OwnerDAO;
import com.example.app1.exceptions.OwnerException;
import com.example.app1.models.Owner;

public class OwnerDAOSql implements OwnerDAO {

	private Connection connection;

	public void newOwner(Owner owner) throws OwnerException {

		PreparedStatement st = null;

		if (connection == null) {
			throw new OwnerException("Doesn't exist Database connection");
		}

		try {
			st = connection.prepareStatement("INSERT INTO " + TABLE_OWNER
					+ " (NICKNAME, PASS) VALUES (?,?)");
			st.setString(1, owner.getOwnerName());
			st.setString(2, owner.getOwnerPassword());

			st.execute();
		} catch (SQLException e) {
			throw new OwnerException(e.getMessage());
		} finally {
			try {
				if (st != null) {
					st.close();
				}
			} catch (SQLException e) {
				throw new OwnerException(
						"Error to close the database connection");
			}
		}
	}

	public Owner findOwnerById(Integer ownerId) {
		PreparedStatement st = null;
		ResultSet rs = null;
		Owner owner = null;

		if (connection == null) {
			throw new OwnerException("Doesn't exist Database connection");
		}

		try {
			st = connection.prepareStatement("SELECT * FROM " + TABLE_OWNER
					+ "WHERE ID_OWNER = ?");
			st.setInt(1, ownerId);

			rs = st.executeQuery();

			while (rs.next()) {
				owner = new Owner();
				owner.setOwnerID(rs.getInt("ID_OWNER"));
				owner.setOwnerName(rs.getString("NICKNAME"));
				owner.setOwnerPassword(rs.getString("PASS"));
			}
			return owner;

		} catch (SQLException e) {
			throw new OwnerException(e.getMessage());
		} finally {
			try {
				if (st != null && rs != null) {
					rs.close();
					st.close();
				}
			} catch (SQLException e) {
				throw new OwnerException(
						"Error to close the database connection");
			}
		}
	}

	public void deleteOwner(Owner owner) {
		PreparedStatement st = null;

		if (connection == null) {
			throw new OwnerException("Doesn't exist Database connection");
		}

		try {
			st = connection.prepareStatement("DELETE FROM " + TABLE_OWNER
					+ "WHERE ID_OWNER = ?");
			st.setInt(1, owner.getOwnerID());

			st.executeQuery();

		} catch (SQLException e) {
			throw new OwnerException(e.getMessage());
		} finally {
			try {
				if (st != null) {
					st.close();
				}
			} catch (SQLException e) {
				throw new OwnerException(
						"Error to close the database connection");
			}
		}
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

}