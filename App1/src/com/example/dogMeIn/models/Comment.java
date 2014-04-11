package com.example.dogMeIn.models;

import java.sql.Date;

public class Comment {

	private Integer parkID;
	private Integer userID;
	private Date commentDate;
	private String commentText;
	
	public Comment() {
		
	}

	private Integer getParkID() {
		return parkID;
	}

	private void setParkID(Integer parkID) {
		this.parkID = parkID;
	}

	private Integer getUserID() {
		return userID;
	}

	private void setUserID(Integer userID) {
		this.userID = userID;
	}

	private Date getCommentDate() {
		return commentDate;
	}

	private void setCommentDate(Date commentDate) {
		this.commentDate = commentDate;
	}

	private String getCommentText() {
		return commentText;
	}

	private void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	@Override
	public String toString() {
		return "Comment [getParkID()=" + getParkID() + ", getUserID()="
				+ getUserID() + ", getCommentDate()=" + getCommentDate()
				+ ", getCommentText()=" + getCommentText() + "]";
	}
	
	
}
