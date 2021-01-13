/**
 * 
 */
package com.example.services;
import java.util.List;

import com.example.model.Smartphone;

/**
 * @author mirajul.islam
 *
 */

public interface SmartPhoneService {
	
	public void saveSmartPhone(Smartphone smartphone);	
	public void saveListSmartPhone(List<Smartphone> smartphone);
	public List<Smartphone> allData();
}
