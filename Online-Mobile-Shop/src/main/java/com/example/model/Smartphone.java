/**
 * 
 */
package com.example.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * @author mirajul.islam
 *
 */
@Entity
@Table(name = "smartphone")
public class Smartphone {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private  Integer  b_id;
	private  String   brand;
	private  String   d_code;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "model_id", referencedColumnName = "mod_id")
    private Model model;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "fearures_id", referencedColumnName = "fs_id")
    private  Features features;
 
	public Smartphone() {
		super();
	}

	/**
	 * @param b_id
	 * @param brand
	 * @param d_code
	 * @param model
	 * @param features
	 */
    @JsonCreator
	public Smartphone(Integer b_id, String brand, String d_code, Model model, Features features) {
		super();
		this.b_id = b_id;
		this.brand = brand;
		this.d_code = d_code;
		this.model = model;
		this.features = features;
	}

	public Integer getB_id() {
		return b_id;
	}

	public void setB_id(Integer b_id) {
		this.b_id = b_id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getD_code() {
		return d_code;
	}

	public void setD_code(String d_code) {
		this.d_code = d_code;
	}

	public Model getModel() {
		return model;
	}

	public void setModel(Model model) {
		this.model = model;
	}

	public Features getFeatures() {
		return features;
	}

	public void setFeatures(Features features) {
		this.features = features;
	}

	@Override
	public String toString() {
		return "Smartphone [b_id=" + b_id + ", brand=" + brand + ", d_code=" + d_code + "]";
	}

}
