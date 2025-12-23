package com.document.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "document")
public class Document {

    @Id
    @NotBlank(message = "Document id can't be empty")
    private String docId;
    
    @NotBlank(message = "doc Title can't be empty")
    @Size(min = 3,max = 50,message = "docTitle size must be in between 3 and 50 character")
    private String docTitle;
    
    @NotBlank(message = "please insert file name")
    private String fileName;
    
    @NotBlank(message = "path is required")
    private String path;
    
    @NotBlank(message = "version can't empty")
    private String version;
    
    @NotBlank(message = "docClass name is required")
    private String docClassName;
    
    public Document() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Document [docId=" + docId + ", docTitle=" + docTitle + ", fileName=" + fileName + ", path=" + path
				+ ", version=" + version + ", docClassName=" + docClassName + "]";
	}

	public Document(String docId, String docTitle, String fileName, String path, String version, String docClassName) {
		super();
		this.docId = docId;
		this.docTitle = docTitle;
		this.fileName = fileName;
		this.path = path;
		this.version = version;
		this.docClassName = docClassName;
	}

	public String getDocId() {
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
