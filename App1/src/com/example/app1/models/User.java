package com.example.app1.models;

public class User {

	private Integer userID;
	private String userName;
	private String userSurName;
	private String userMail;
	private String userPassword;

	public User() {
		setUserID(Integer.parseInt(""));
		setUserName("");
		setUserSurName("");
		setUserMail("");
		setUserPassword("");

	}

	private Integer getUserID() {
		return userID;
	}

	private void setUserID(Integer userID) {
		this.userID = userID;
	}

	private String getUserName() {
		return userName;
	}

	private void setUserName(String userName) {
		this.userName = userName;
	}

	private String getUserSurName() {
		return userSurName;
	}

	private void setUserSurName(String userSurName) {
		this.userSurName = userSurName;
	}

	private String getUserMail() {
		return userMail;
	}

	private void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	private String getUserPassword() {
		return userPassword;
	}

	private void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String toString() {
		return getUserID() + ":[" + getUserName() + "," + getUserSurName()
				+ "," + getUserMail() + "]";
	}

	public boolean equals(Object o) {
		boolean equals = false;

		if (o instanceof User && ((User) o).getUserID() == getUserID()) {
			equals = true;
		} 
		return equals;
	}
}
