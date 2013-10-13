package com.example.app1.models;

public class Breed {

	private Integer breedID;
	private String breedDes;

	public Breed() {
		setBreedID(0);
		setBreedDes("");
	}

	private Integer getBreedID() {
		return breedID;
	}

	private void setBreedID(Integer breedID) {
		this.breedID = breedID;
	}

	private String getBreedDes() {
		return breedDes;
	}

	private void setBreedDes(String breedDes) {
		this.breedDes = breedDes;
	}

	public String toString() {
		return getBreedID() + ":[" + getBreedDes() + "]";
	}

	public boolean equals(Object o) {
		boolean equals = false;

		if (o instanceof User && ((Breed) o).getBreedID() == getBreedID()) {
			equals = true;
		}
		return equals;
	}
}
