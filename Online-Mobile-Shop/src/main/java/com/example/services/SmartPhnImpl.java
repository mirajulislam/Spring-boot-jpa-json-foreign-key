/**
 * 
 */
package com.example.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Smartphone;
import com.example.repository.SmartphoneRepo;

/**
 * @author mirajul.islam
 *
 */
@Service
public class SmartPhnImpl implements SmartPhoneService{
	
	@Autowired
	private SmartphoneRepo smartphoneRepo;
	@Autowired
	private ModelService modelService;
	@Autowired
	private FeaturesServices featuresServices;
	private static Logger log = LoggerFactory.getLogger(SmartPhnImpl.class);

	/* (non-Javadoc)
	 * @see com.example.services.SmartPhoneService#saveSmartPhone(com.example.model.Smartphone)
	 */
	@Override
	public void saveSmartPhone(Smartphone smartphone) {
		smartphoneRepo.save(smartphone);
		if(smartphone.getModel() !=null) {
			modelService.saveModel(smartphone.getModel());
		}else if(smartphone.getFeatures() !=null) {
			featuresServices.saveFeatures(smartphone.getFeatures());
		}
		log.info("Phone description save successfully[{}]",smartphone.toString());
	}

	@Override
	public void saveListSmartPhone(List<Smartphone> smartphoneList) {
		for (Smartphone smartphone : smartphoneList) {
			smartphoneRepo.save(smartphone);
			if(smartphone.getModel() !=null) {
				modelService.saveModel(smartphone.getModel());
			}else if(smartphone.getFeatures() !=null) {
				featuresServices.saveFeatures(smartphone.getFeatures());
			}			
		}
		log.info("Phone description save successfully[{}]",smartphoneList.toString());
		
	}
}
