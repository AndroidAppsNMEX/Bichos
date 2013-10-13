package com.example.app1.models;

public class Pet {

	private Integer petID;
	private Integer ownerID;
	private Integer breedID;
	private String petNick;
	private String petName;
	private Gender petGender;
	private Integer petAge;
	private String petDesc;
	private String petPathPhoto;

	public Pet() {

	}

	private Integer getPetID() {
		return petID;
	}

	private void setPetID(Integer petID) {
		this.petID = petID;
	}

	private Integer getOwnerID() {
		return ownerID;
	}

	private void setOwnerID(Integer ownerID) {
		this.ownerID = ownerID;
	}

	private Integer getBreedID() {
		return breedID;
	}

	private void setBreedID(Integer breedID) {
		this.breedID = breedID;
	}

	private String getPetNick() {
		return petNick;
	}

	private void setPetNick(String petNick) {
		this.petNick = petNick;
	}

	private String getPetName() {
		return petName;
	}

	private void setPetName(String petName) {
		this.petName = petName;
	}

	private Gender getPetGender() {
		return petGender;
	}

	private void setPetGender(Gender petGender) {
		this.petGender = petGender;
	}

	private Integer getPetAge() {
		return petAge;
	}

	private void setPetAge(Integer petAge) {
		this.petAge = petAge;
	}

	private String getPetDesc() {
		return petDesc;
	}

	private void setPetDesc(String petDesc) {
		this.petDesc = petDesc;
	}

	private String getPetPathPhoto() {
		return petPathPhoto;
	}

	private void setPetPathPhoto(String petPathPhoto) {
		this.petPathPhoto = petPathPhoto;
	}

	@Override
	public String toString() {
		return "Pet [getPetID()=" + getPetID() + ", getOwnerID()="
				+ getOwnerID() + ", getBreedID()=" + getBreedID()
				+ ", getPetNick()=" + getPetNick() + ", getPetName()="
				+ getPetName() + ", getPetGender()=" + getPetGender()
				+ ", getPetAge()=" + getPetAge() + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Pet))
			return false;
		Pet other = (Pet) obj;
		if (breedID == null) {
			if (other.breedID != null)
				return false;
		} else if (!breedID.equals(other.breedID))
			return false;
		if (ownerID == null) {
			if (other.ownerID != null)
				return false;
		} else if (!ownerID.equals(other.ownerID))
			return false;
		if (petID == null) {
			if (other.petID != null)
				return false;
		} else if (!petID.equals(other.petID))
			return false;
		return true;
	}

}
