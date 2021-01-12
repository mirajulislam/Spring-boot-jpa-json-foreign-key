/**
 * 
 */
package com.example.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Features;
import com.example.repository.FeaturesRepo;

/**
 * @author mirajul.islam
 *
 */
@Service
public class FeaturesServicesImpl implements FeaturesServices{

	@Autowired
	private FeaturesRepo featuresRepo;
	private static Logger log = LoggerFactory.getLogger(FeaturesServicesImpl.class);

	/* (non-Javadoc)
	 * @see com.example.services.FeaturesServices#saveFeatures(com.example.model.Features)
	 */
	@Override
	public void saveFeatures(Features features) {
		featuresRepo.save(features);	
		log.info("Phone Features save successfully[{}]",features.toString());
	}
}
