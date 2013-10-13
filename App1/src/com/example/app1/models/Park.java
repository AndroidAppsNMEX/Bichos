package com.example.app1.models;

public class Park {

	private Integer parkID;
	private String parkName;
	private Double parkLat;
	private Double parkLong;
	private String parkAddress;
	private String parkInfo;

	public Park() {
		
	}

	private Integer getparkID() {
		return parkID;
	}

	private void setparkID(Integer parkID) {
		this.parkID = parkID;
	}

	private String getparkName() {
		return parkName;
	}

	private void setparkName(String parkName) {
		this.parkName = parkName;
	}

	private Double getparkLat() {
		return parkLat;
	}

	private void setparkLat(Double parkLat) {
		this.parkLat = parkLat;
	}

	private Double getparkLong() {
		return parkLong;
	}

	private void setparkLong(Double parkLong) {
		this.parkLong = parkLong;
	}

	public String getparkAddress() {
		return parkAddress;
	}

	public void setparkAddress(String parkAddress) {
		this.parkAddress = parkAddress;
	}

	public String getparkInfo() {
		return parkInfo;
	}

	public void setparkInfo(String parkInfo) {
		this.parkInfo = parkInfo;
	}

	@Override
	public String toString() {
		return "park [getparkID()=" + getparkID() + ", getparkName()="
				+ getparkName() + ", getparkLat()=" + getparkLat()
				+ ", getparkLong()=" + getparkLong() + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Park))
			return false;
		Park other = (Park) obj;
		if (parkID == null) {
			if (other.parkID != null)
				return false;
		} else if (!parkID.equals(other.parkID))
			return false;
		if (parkLat == null) {
			if (other.parkLat != null)
				return false;
		} else if (!parkLat.equals(other.parkLat))
			return false;
		if (parkLong == null) {
			if (other.parkLong != null)
				return false;
		} else if (!parkLong.equals(other.parkLong))
			return false;
		return true;
	}

}
