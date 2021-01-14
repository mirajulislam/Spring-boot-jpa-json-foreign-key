/**
 * 
 */
package com.example.services;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.model.GridData;
import com.example.model.PhoneDocuments;
import com.example.model.Response;
import com.example.model.Smartphone;
import com.example.repository.SmartphoneRepo;

/**
 * @author mirajul.islam
 */
@Service
public class SmartPhnImpl implements SmartPhoneService {

	@Autowired
	private SmartphoneRepo smartphoneRepo;
	@Autowired
	private ModelService modelService;
	@Autowired
	private FeaturesServices featuresServices;

	@Autowired
	private PhoneDocumentsServices phoneDocumentsServices;

	private static Logger log = LoggerFactory.getLogger(SmartPhnImpl.class);

	/* (non-Javadoc)
	 * @see com.example.services.SmartPhoneService#saveSmartPhone(com.example.model.Smartphone)
	 */
	@Override
	public void saveSmartPhone(Smartphone smartphone) {
		smartphoneRepo.save(smartphone);
		if (smartphone.getModel() != null) {
			modelService.saveModel(smartphone.getModel());
		}
		else if (smartphone.getFeatures() != null) {
			featuresServices.saveFeatures(smartphone.getFeatures());
		}
		log.info("Phone description save successfully[{}]", smartphone.toString());
	}

	@Override
	public void saveListSmartPhone(List<Smartphone> smartphoneList, MultipartFile[] files) {
		for (Smartphone smartphone : smartphoneList) {
			smartphoneRepo.save(smartphone);
			if (smartphone.getModel() != null) {
				modelService.saveModel(smartphone.getModel());
			}
			else if (smartphone.getFeatures() != null) {
				featuresServices.saveFeatures(smartphone.getFeatures());
			}
			else if (smartphone.getPhoneDocuments() != null && files != null) {
				Arrays.asList(files).stream().map(file -> uploadFile(file)).collect(Collectors.toList());
			}
		}
		log.info("Phone description save successfully[{}]", smartphoneList.toString());
	}

	@Override
	public List<Smartphone> allData() {
		return smartphoneRepo.findAll();
	}

	public Response uploadFile(MultipartFile file) {
		PhoneDocuments fileName = phoneDocumentsServices.storeFile(file);

		String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/").path(fileName.getFileName())
		        .toUriString();

		return new Response(fileName.getFileName(), fileDownloadUri, file.getContentType(), file.getSize());
	}

	public List<GridData> getData(Smartphone smartphone) {
		List<GridData> joinData = smartphoneRepo.getJoinData(smartphone);
		return joinData;
	}

	/* (non-Javadoc)
	 * @see com.example.services.SmartPhoneService#getJoinData()
	 */
}
