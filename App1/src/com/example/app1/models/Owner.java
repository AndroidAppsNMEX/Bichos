package com.example.app1.models;

public class Owner {

	private Integer ownerID;
	private String ownerName;
	private String ownerSurName;
	private String ownerMail;
	private String ownerPassword;

	public Owner() {
		setOwnerID(Integer.parseInt(""));
		setOwnerName("");
		setOwnerSurName("");
		setOwnerMail("");
		setOwnerPassword("");

	}

	public Integer getOwnerID() {
		return ownerID;
	}

	public void setOwnerID(Integer ownerID) {
		this.ownerID = ownerID;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	private String getOwnerSurName() {
		return ownerSurName;
	}

	private void setOwnerSurName(String ownerSurName) {
		this.ownerSurName = ownerSurName;
	}

	private String getOwnerMail() {
		return ownerMail;
	}

	private void setOwnerMail(String ownerMail) {
		this.ownerMail = ownerMail;
	}

	public String getOwnerPassword() {
		return ownerPassword;
	}

	public void setOwnerPassword(String ownerPassword) {
		this.ownerPassword = ownerPassword;
	}

	public String toString() {
		return getOwnerID() + ":[" + getOwnerName() + "," + getOwnerSurName()
				+ "," + getOwnerMail() + "]";
	}

	public boolean equals(Object o) {
		boolean equals = false;

		if (o instanceof Owner && ((Owner) o).getOwnerID() == getOwnerID()) {
			equals = true;
		} 
		return equals;
	}
}
