package com.document.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
//add anotation
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "document")
	
public class Document {
	@Id

	public String docId;
	public String docTitle;
	public String fileName;
	public String path;
	public String version;
	public String docClassName;

	public String getDocId() {
		System.out.println("check jenkin is connected or not")
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getDocTitle() {
		return docTitle;
	}

	public void setDocTitle(String docTitle) {
		this.docTitle = docTitle;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDocClassName() {
		return docClassName;
	}

	public void setDocClassName(String docClassName) {
		this.docClassName = docClassName;
	}

}
