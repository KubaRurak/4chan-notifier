package com.Godel.channotifier.entity;

import java.util.Objects;

public class Post {
    
	private String boardCode;
	private Integer threadNumber;
	private Integer postNumber;
    private String comment;
    private String postUrl;

	public Post(String boardCode, Integer threadNumber, Integer postNumber, String comment) {
		this.boardCode = boardCode;
		this.threadNumber = threadNumber;
		this.postNumber = postNumber;
		this.comment = comment;
		this.postUrl = "https://boards.4channel.org/"+boardCode+"/thread/"+threadNumber+"#p"+postNumber;
	}
	
	public Post(Integer postNumber, String comment) {
		this.postNumber = postNumber;
		this.comment = comment;
	}
	
	public void setPostUrl() {
		this.postUrl = "https://boards.4channel.org/"+this.boardCode+"/thread/"+this.threadNumber+"#p"+this.postNumber;
	}

	public String getPostUrl() {
		return postUrl;
	}

	public String getBoardCode() {
		return boardCode;
	}
	public void setBoardCode(String boardCode) {
		this.boardCode = boardCode;
	}
	public Integer getThreadNumber() {
		return threadNumber;
	}
	public void setThreadNumber(Integer threadNumber) {
		this.threadNumber = threadNumber;
	}
	public Integer getPostNumber() {
		return postNumber;
	}
	public void setPostNumber(Integer postNumber) {
		this.postNumber = postNumber;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (obj == null) {
	        return false;
	    }
	    if (getClass() != obj.getClass()) {
	        return false;
	    }
	    final Post other = (Post) obj;
	    if (!Objects.equals(this.postNumber, other.postNumber)) {
	        return false;
	    }
	    if (!Objects.equals(this.comment, other.comment)) {
	        return false;
	    }
	    return true;
	}


}