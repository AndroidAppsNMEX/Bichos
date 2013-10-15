package com.example.app1.models;

public class Pet {

	public Integer petID;
	public Integer ownerID;
	public Integer breedID;
	public String petNick;
	public String petName;
	public Gender petGender;
	public Integer petAge;
	public String petDesc;
	public String petPathPhoto;

	public Pet() {

	}

	public Integer getPetID() {
		return petID;
	}

	public void setPetID(Integer petID) {
		this.petID = petID;
	}

	public Integer getOwnerID() {
		return ownerID;
	}

	public void setOwnerID(Integer ownerID) {
		this.ownerID = ownerID;
	}

	public Integer getBreedID() {
		return breedID;
	}

	public void setBreedID(Integer breedID) {
		this.breedID = breedID;
	}

	public String getPetNick() {
		return petNick;
	}

	public void setPetNick(String petNick) {
		this.petNick = petNick;
	}

	public String getPetName() {
		return petName;
	}

	public void setPetName(String petName) {
		this.petName = petName;
	}

	public Gender getPetGender() {
		return petGender;
	}

	public void setPetGender(Gender petGender) {
		this.petGender = petGender;
	}

	public Integer getPetAge() {
		return petAge;
	}

	public void setPetAge(Integer petAge) {
		this.petAge = petAge;
	}

	public String getPetDesc() {
		return petDesc;
	}

	public void setPetDesc(String petDesc) {
		this.petDesc = petDesc;
	}

	public String getPetPathPhoto() {
		return petPathPhoto;
	}

	public void setPetPathPhoto(String petPathPhoto) {
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
