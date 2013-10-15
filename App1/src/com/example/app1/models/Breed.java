package com.example.app1.models;

public class Breed {

	private Integer breedID;
	private String breedDes;

	public Breed() {
		setBreedID(0);
		setBreedDes("");
	}

	public Integer getBreedID() {
		return breedID;
	}

	public void setBreedID(Integer breedID) {
		this.breedID = breedID;
	}

	public String getBreedDes() {
		return breedDes;
	}

	public void setBreedDes(String breedDes) {
		this.breedDes = breedDes;
	}

	public String toString() {
		return getBreedID() + ":[" + getBreedDes() + "]";
	}

	public boolean equals(Object o) {
		boolean equals = false;

		if (o instanceof Owner && ((Breed) o).getBreedID() == getBreedID()) {
			equals = true;
		}
		return equals;
	}
}
