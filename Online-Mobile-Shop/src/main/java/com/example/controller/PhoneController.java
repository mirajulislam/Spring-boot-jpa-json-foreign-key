/**
 * 
 */
package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.model.Smartphone;
import com.example.payload.response.MessageResponse;
import com.example.services.SmartPhnImpl;

/**
 * @author mirajul.islam
 *
 */
@RestController
public class PhoneController {
	@Autowired
	private SmartPhnImpl smartPhoneService;
	
	@RequestMapping(value = "/smartphone", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
    public ResponseEntity<MessageResponse> addSmartphone(@RequestBody Smartphone smartphone) {
		smartPhoneService.saveSmartPhone(smartphone);
		return ResponseEntity.ok(new MessageResponse("Stored New Phone successfully"));
    }
	
	@RequestMapping(value = "/smartphoneList", method = RequestMethod.POST)
	@ResponseBody
	    public ResponseEntity<MessageResponse> addSmartphones(@RequestBody List<Smartphone> smartphones) {
		    smartPhoneService.saveListSmartPhone(smartphones,null);
	        return ResponseEntity.ok(new MessageResponse("Stored New Phone successfully"));
	    }
	
	@RequestMapping(value = "/getSmartphoneList", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	    public List<Smartphone> getAllSmartphones() {
	        return smartPhoneService.allData();
	    }

}
