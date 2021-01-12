/**
 * 
 */
package com.example.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.Model;
import com.example.repository.ModelRepo;

/**
 * @author mirajul.islam
 *
 */
@Service
public class ModelServicesImpl implements ModelService{
	private static Logger log = LoggerFactory.getLogger(ModelServicesImpl.class);	
	
	@Autowired
	private ModelRepo modelRepo;

	/* (non-Javadoc)
	 * @see com.example.services.ModelService#saveModel(com.example.model.Model)
	 */
	@Override
	public void saveModel(Model model) {
		modelRepo.save(model);
		log.info("Phone Model save successfully[{}]",model.toString());
	}
}
