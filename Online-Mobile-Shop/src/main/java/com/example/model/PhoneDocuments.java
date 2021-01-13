package com.example.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;

@Entity
@Table(name = "phone_documents")
public class PhoneDocuments {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String fileName;

    private String fileType;

    @Lob
    private byte[] data;
    
    @JoinColumn(name = "phone_id")
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private  Smartphone smartphone;

    public PhoneDocuments() {
		super();
	}

	@JsonCreator
	public PhoneDocuments(String fileName, String fileType, byte[] data) {
		super();
		this.fileName = fileName;
		this.fileType = fileType;
		this.data = data;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFileName() {
        return fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public byte[] getData() {
        return data;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
    

	@Override
	public String toString() {
		return "PhoneDocuments [id=" + id + ", fileName=" + fileName + ", fileType=" + fileType + "]";
	}
    
    
}
