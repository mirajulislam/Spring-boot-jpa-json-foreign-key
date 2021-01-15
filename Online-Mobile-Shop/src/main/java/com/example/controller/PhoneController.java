/**
 * 
 */
package com.example.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.constrants.Str;
import com.example.model.GridData;
import com.example.model.Smartphone;
import com.example.payload.response.MessageResponse;
import com.example.services.ReportServices;
import com.example.services.SmartPhnImpl;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;

/**
 * @author mirajul.islam
 */
@RestController
public class PhoneController {
	private static Logger log = LoggerFactory.getLogger(PhoneController.class);
	public List<String> reportReqTimeList = new ArrayList<String>();
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
	
	@RequestMapping(value = "/phone/excel-report", method = RequestMethod.GET)
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
	
	@RequestMapping(value = "/generateLmsReport")
	@ResponseBody
	public void generateReport(HttpServletRequest request, HttpServletResponse response) throws Exception {

		JasperPrint jasperPrint = null;

		String reportName = request.getParameter("reportName");
		String reportFormat = request.getParameter("reportformat");

		log.info("Received GUI request for Phone report");
		log.info("format: {}", reportFormat);
		String reportReqTime = request.getParameter("reportReqTime");

		if (!reportReqTimeList.contains(reportReqTime)) {

			reportReqTimeList.add(reportReqTime);
		
			if(reportName.equalsIgnoreCase(Str.phonePdfReport)&& reportFormat.equalsIgnoreCase("pdf")) {
				response.setHeader("Content-Disposition", "filename=\"" + "PhoneReport.pdf" + "\"");
				jasperPrint = reportServices.getPhonePdfReportGenerate(request);
			}
			if (reportFormat.equalsIgnoreCase(Str.pdf)) {
				response.setContentType("application/pdf");
				ServletOutputStream outputStream = response.getOutputStream();

				JRPdfExporter pdfExporter = new JRPdfExporter();

				// TODO_H: UPDATE JASPER LIBRARY
				pdfExporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, outputStream);
				pdfExporter.setParameter(JRPdfExporterParameter.JASPER_PRINT, jasperPrint);
				pdfExporter.exportReport();

				response.getOutputStream().flush();
			}
		}
		else {

			log.info("Duplicate reportReqTime: {} found. Ignoring request.", reportReqTime);
			reportReqTimeList.clear();
		}
	}

}
