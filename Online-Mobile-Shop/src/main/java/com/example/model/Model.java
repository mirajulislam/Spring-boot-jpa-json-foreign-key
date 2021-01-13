/**
 * 
 */
package com.example.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * @author mirajul.islam
 *
 */
@Entity
@Table(name = "model")
public class Model {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 private  Integer mod_id;
	 private  String  name;
     private  String  version;
     private  String  mod_code;
  
     public Model() {
		super();
	}

	@OneToOne(mappedBy = "model")
     private Smartphone smartphone;

	/**
	 * @param mod_id
	 * @param name
	 * @param version
	 * @param mod_code
	 * @param smartphone
	 */
     @JsonCreator
	public Model(Integer mod_id, String name, String version, String mod_code) {
		super();
		this.mod_id = mod_id;
		this.name = name;
		this.version = version;
		this.mod_code = mod_code;
	}

	public Integer getMod_id() {
		return mod_id;
	}

	public void setMod_id(Integer mod_id) {
		this.mod_id = mod_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getMod_code() {
		return mod_code;
	}

	public void setMod_code(String mod_code) {
		this.mod_code = mod_code;
	}

	@Override
	public String toString() {
		return "Model [mod_id=" + mod_id + ", name=" + name + ", version=" + version + ", mod_code=" + mod_code + "]";
	}

	
 }
	