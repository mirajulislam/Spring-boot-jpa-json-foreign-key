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
@Table(name = "features")
public class Features {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer fs_id;
	private  String fs_Code;
	private  String screen;
    private  String camera;
    private  String battary;
    private  String capacity;  
    
    @OneToOne(mappedBy = "features")
    private Smartphone smartphone;

	public Features() {
		super();
	}

	/**
	 * @param fs_id
	 * @param fs_Code
	 * @param screen
	 * @param camera
	 * @param battary
	 * @param capacity
	 * @param smartphone
	 */
    @JsonCreator
	public Features(Integer fs_id, String fs_Code, String screen, String camera, String battary, String capacity) {
		super();
		this.fs_id = fs_id;
		this.fs_Code = fs_Code;
		this.screen = screen;
		this.camera = camera;
		this.battary = battary;
		this.capacity = capacity;
	}

	public Integer getFs_id() {
		return fs_id;
	}

	public void setFs_id(Integer fs_id) {
		this.fs_id = fs_id;
	}

	public String getFs_Code() {
		return fs_Code;
	}

	public void setFs_Code(String fs_Code) {
		this.fs_Code = fs_Code;
	}

	public String getScreen() {
		return screen;
	}

	public void setScreen(String screen) {
		this.screen = screen;
	}

	public String getCamera() {
		return camera;
	}

	public void setCamera(String camera) {
		this.camera = camera;
	}

	public String getBattary() {
		return battary;
	}

	public void setBattary(String battary) {
		this.battary = battary;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public Smartphone getSmartphone() {
		return smartphone;
	}

	public void setSmartphone(Smartphone smartphone) {
		this.smartphone = smartphone;
	}

	@Override
	public String toString() {
		return "Features [fs_id=" + fs_id + ", fs_Code=" + fs_Code + ", screen=" + screen + ", camera=" + camera
				+ ", battary=" + battary + ", capacity=" + capacity + "]";
	}

}
