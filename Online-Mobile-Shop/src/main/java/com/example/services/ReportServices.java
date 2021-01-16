package com.example.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.Session;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.example.controller.PhoneController;
import com.example.model.GridData;
import com.example.model.Smartphone;
import com.example.repository.SmartphoneRepo;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@PropertySource("classpath:app.properties")

@Service
public class ReportServices {
	
	@Value("${phone.report}")
	String reportColumn;
	//private static final DateFormat df = new SimpleDateFormat("yyMM");
	private final static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	private static Logger log = LoggerFactory.getLogger(ReportServices.class);
	
	@Autowired
	private SmartphoneRepo smartphoneRepo;
	
	public File reportDownloand(HttpServletRequest request) throws Exception {
		// Create a Workbook
//		        String brand = request.getParameter("brand");
//		        String d_code = request.getParameter("d_code");
		        
		        String b_id=request.getParameter("b_id");  
		        
		    	if (b_id == null || b_id.length() == 0) {
					throw new Exception("Empty Request.");
				}
				String[] b_ids = b_id.split(",");
				Smartphone smartphone=new Smartphone();
				List<GridData> dataList = new ArrayList<GridData>();
					         
				for (int i = 0; i < b_ids.length; i++) {
					smartphone.setB_id(Integer.parseInt(b_ids[i]));
					List<GridData>data = smartphoneRepo.getSearchGridData(smartphone);	
					dataList.addAll(data);
				}
				
				Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

				// CreationHelper helps us create instances of various things like DataFormat,
				// Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way
				CreationHelper createHelper = workbook.getCreationHelper();

				// Create a Sheet
				Sheet sheet = workbook.createSheet("PhoneDocuments");

				// Create a Font for styling header cells
				Font headerFont = workbook.createFont();
				headerFont.setBold(true);
				headerFont.setFontHeightInPoints((short) 14);
				headerFont.setColor(IndexedColors.BLUE.getIndex());
				
				// Create a CellStyle with the font
				CellStyle headerCellStyle = workbook.createCellStyle();
				headerCellStyle.setFont(headerFont);

				// Create a Row
				Row headerRow = sheet.createRow(0);

				String[] phnReportColumn = reportColumn.split(",");
				
				for (int i = 0; i < phnReportColumn.length; i++) {
					Cell cell = headerRow.createCell(i);
					cell.setCellValue(phnReportColumn[i]);
					cell.setCellStyle(headerCellStyle);
				}
				
				Map<String, Object[]> data = new HashMap<String, Object[]>();
				for (int count = 0; count < dataList.size(); count++) {
					GridData gridData = dataList.get(count);
					data.put(String.valueOf(count),
					        new Object[] { (count + 1), gridData.getb_id(),gridData.getbrand(),gridData.getd_code(),gridData.getname()
					        		, gridData.getmod_code(),gridData.getversion(),gridData.getcamera(),gridData.getbattary()
					        		, gridData.getcapacity(),gridData.getscreen()
							});
				}
				// Create Cell Style for formatting Date
				CellStyle dateCellStyle = workbook.createCellStyle();
				dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
				// Create Other rows and cells with employees data

				int rownum = 1;
				for (int key = 0; key < dataList.size(); key++) {

					headerRow = sheet.createRow(rownum++);

					Object[] objArr = data.get(String.valueOf(key));
					int cellnum = 0;
					for (Object obj : objArr) {
						Cell cell = headerRow.createCell(cellnum++);
						if (obj instanceof Date) {
							cell.setCellValue(sdf.format((Date) obj));
							cell.setCellStyle(dateCellStyle);
						}
						else if (obj instanceof Boolean)
							cell.setCellValue((Boolean) obj);
						else if (obj instanceof String)
							cell.setCellValue((String) obj);
						else if (obj instanceof Double)
							cell.setCellValue((Double) obj);
						else if (obj instanceof Number)
							cell.setCellValue(((Number) obj).doubleValue());
						else if (obj == null)
							cell.setCellValue("");
						else if (obj instanceof Integer) cell.setCellValue((Integer) obj);

					}
				}
				
				// Resize all columns to fit the content size
				for (int j = 0; j < phnReportColumn.length; j++) {
					sheet.autoSizeColumn(j);
				}

				File tempFile = File.createTempFile("PHONE-REPORT-EXCEL", ".xlsx");
				FileOutputStream fileOut = new FileOutputStream(tempFile);
				workbook.write(fileOut);
				fileOut.close();

				workbook.close();
				return tempFile;				
	}
	
	public JasperPrint getPhonePdfReportGenerate(HttpServletRequest request) throws Exception {
		
		String brand = request.getParameter("brand");
		//Integer b_id = Integer.parseInt(request.getParameter("b_id"));
		//log.debug("Generating Report ", b_id);
		
		Date reportGenDate = new Date();
		DateFormat dateFormatFull = new SimpleDateFormat("dd MMM, yyyy HH:mm:ss a");
		JasperDesign jasperDesign;
		JasperPrint jasperPrint = null;
		JasperReport jasperReport;
		InputStream template = null;
		
		Map<String, Object> spArgsMap = new LinkedHashMap<String, Object>();
		Smartphone smartphone=new Smartphone();
		
		try {
			//smartphone.setB_id(b_id);
			smartphone.setBrand(brand);
			
			List<GridData>data=smartphoneRepo.getSearchGridData(smartphone);
			log.info("Received GUI request for Phone report"+data.toString());
			//spArgsMap.put("memofullDate", dateFormatFull.format(reportGenDate).toString());
			//spArgsMap.put("ds_loan_view_memo",  new JRResultSetDataSource((ResultSet) data));
			template = getClass().getResourceAsStream("/report/phone_report.jrxml");
			jasperDesign = JRXmlLoader.load(template);
			jasperReport = JasperCompileManager.compileReport(jasperDesign);
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);
			jasperPrint = JasperFillManager.fillReport(jasperReport, spArgsMap, dataSource);		
			
		} catch (Exception ex) {
			log.error("Memo Bulk Report report error : {}", ex.getLocalizedMessage());

			throw ex;
		}

		return jasperPrint;
		
	}
}
