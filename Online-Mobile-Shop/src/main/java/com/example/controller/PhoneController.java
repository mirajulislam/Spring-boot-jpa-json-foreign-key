/**
 * 
 */
package com.example.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.GridData;
import com.example.model.Smartphone;
import com.example.payload.response.MessageResponse;
import com.example.services.ReportServices;
import com.example.services.SmartPhnImpl;

/**
 * @author mirajul.islam
 */
@RestController
public class PhoneController {
	private static Logger log = LoggerFactory.getLogger(PhoneController.class);
	@Autowired
	private SmartPhnImpl smartPhoneService;
	
	@Autowired
	private ReportServices reportServices;

	@RequestMapping(value = "/smartphone", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public ResponseEntity<MessageResponse> addSmartphone(@RequestBody Smartphone smartphone) {
		smartPhoneService.saveSmartPhone(smartphone,null);
		return ResponseEntity.ok(new MessageResponse("Stored New Phone successfully"));
	}

	@RequestMapping(value = "/smartphoneList", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<MessageResponse> addSmartphones(@RequestBody List<Smartphone> smartphones) {
		smartPhoneService.saveListSmartPhone(smartphones, null);
		return ResponseEntity.ok(new MessageResponse("Stored New Phone successfully"));
	}

	@RequestMapping(value = "/getSmartphoneList", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	public List<Smartphone> getAllSmartphones() {
		return smartPhoneService.allData();
	}

	@RequestMapping(value = "/searchData", method = RequestMethod.GET)
	public List<GridData> getDbJoinData(@RequestBody Smartphone smartphone) {
		return smartPhoneService.getSearchData(smartphone);
	}
	
	@RequestMapping(value = "/report", method = RequestMethod.GET)
	public void getReport(HttpServletRequest request, HttpServletResponse response) throws Exception {
			File file = reportServices.reportDownloand(request);
			log.info("Received GUI request for \'Phone Grid Excel Report\'");
			response.setContentType("application/force-download");
			response.setHeader("Content-Disposition", "attachment; filename=" + "PhoneReport" + ".xlsx");
			Files.copy(file.toPath(), response.getOutputStream());
			response.getOutputStream().flush();

	}
	
	@GetMapping("/downloadFile/{id}")
    public ResponseEntity < Resource > doDownloadFile(@PathVariable Integer id, HttpServletRequest request) throws FileNotFoundException {
		return smartPhoneService.downloadFile(id, request);    
    }

}
