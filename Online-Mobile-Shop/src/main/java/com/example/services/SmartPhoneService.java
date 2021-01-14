/**
 * 
 */
package com.example.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.model.Smartphone;

/**
 * @author mirajul.islam
 */

public interface SmartPhoneService {

	public void saveSmartPhone(Smartphone smartphone, MultipartFile[] files);

	//public void saveListSmartPhone(List<Smartphone> smartphone);
	public List<Smartphone> allData();

	void saveListSmartPhone(List<Smartphone> smartphoneList, MultipartFile[] files);

}
