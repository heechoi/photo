package com.dgit.domain;

import java.util.Date;

public class PhotoVO {
	private int pno;
	private String pid;
	private String photoName;
	private Date uploaddate;
	
	
	public PhotoVO() {}


	public int getPno() {
		return pno;
	}


	public void setPno(int pno) {
		this.pno = pno;
	}


	public String getPid() {
		return pid;
	}


	public void setPid(String pid) {
		this.pid = pid;
	}


	public String getPhotoName() {
		return photoName;
	}


	public void setPhotoName(String photoName) {
		this.photoName = photoName;
	}


	public Date getUploaddate() {
		return uploaddate;
	}


	public void setUploaddate(Date uploaddate) {
		this.uploaddate = uploaddate;
	}


	@Override
	public String toString() {
		return String.format("PhotoVO [pno=%s, pid=%s, photoName=%s, uploaddate=%s]", pno, pid, photoName, uploaddate);
	}
	
	
}
