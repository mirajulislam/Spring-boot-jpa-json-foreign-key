/**
 * 
 */
package com.example.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.model.ImportData;

/**
 * @author mirajul.islam
 *
 */
@Service
public class ImportDataService {
	
	private static Logger log = LogManager.getLogger(ImportDataService.class);
	
	@Value("${app.upload.file:${user.home}}")
	public String EXCEL_FILE_PATH;
	
	
	Workbook wb = null;
	
	public void getExcelDataAsList() throws Exception{
		List<ImportData> dataList = readExcelFile();		
		
		for (ImportData ob : dataList) {
			
//			Loan loan = getBaseLoanData(ob);			
//			Customer cust = getBaseCustomerDta(ob);
//			cust.setUserModKey(110302);	
//			
//			//insert customer
//			cust = customerService.insertCustomer(cust);
//			
//            loan.setCustomerIdKey(cust.getCustomerIdKey());
//			loan.setIdCustomerVer(cust.getIdCustomerVer());
//			Date createDate=new SimpleDateFormat("dd/MM/yyyy").parse(Str.INSERTDATE);  
//			// set loan tracking id at save time
//			loan.setLoanTrackingId(loanService.getLoanTrackingId());
//			loan.setUserModKey(110302);
//			loan.setCreateDate(createDate);
//			loan.setDttMod(createDate);
//			loan.setStateId(110005);
//			//create loan
//			Loan loan2 = importExecute(loan);	
//			
//			//received loan		
//			
//			Date receivedDate=new SimpleDateFormat("dd/MM/yyyy").parse(Str.RECEIVEDDATE);  
//			loan.setDttMod(receivedDate);
//			loan.setApplicationNo(ob.getFileSerial());
//			loan.setStateId(110069);
//			loan.setLoanId(loan2.getLoanId());
//			Loan loan3 = importUpdate(loan);
//			
//			//approvedloan
//			
//			Date approvedDate=new SimpleDateFormat("dd/MM/yyyy").parse(Str.APPROVEDDATE);  
//			loan.setDttMod(approvedDate);
//			loan.setStateId(110034);
//			loan.setLoanId(loan2.getLoanId());
//			Loan loan4 = importUpdate(loan);	
//			
//			//send to cad
//			
//			Date sentTocadDate=new SimpleDateFormat("dd/MM/yyyy").parse(Str.SENDTOCAD);  
//			loan.setDttMod(sentTocadDate);
//			loan.setStateId(110022);
//			loan.setLoanId(loan2.getLoanId());
//			Loan loan5 = importUpdate(loan);	
			
		}
	}
	
	
	private List<ImportData> readExcelFile(){

		List<ImportData> dataList = new ArrayList<ImportData>();
		
		try {
			wb = new XSSFWorkbook(new FileInputStream(new File(EXCEL_FILE_PATH)));
			Sheet sheet = wb.getSheetAt(0);

			boolean skipHeader = true;

			for (Row row : sheet) {
				if (skipHeader) {
					skipHeader = false;
					continue;
				}

				List<Cell> cells = new ArrayList<Cell>();

				int lastColumn = Math.max(row.getLastCellNum(), 33);
				for (int cn = 0; cn < lastColumn; cn++) {
					Cell c = row.getCell(cn, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
					cells.add(c);
				}

				ImportData info = extractInfoFromCell(cells);
				dataList.add(info);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (wb != null) {
				try {
					wb.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return dataList;
	}
	
	private static ImportData extractInfoFromCell(List<Cell> cells) {
		ImportData info = new ImportData();
		DataFormatter dataFormatter = new DataFormatter();

		Cell fileSerial = cells.get(0);
		if (fileSerial != null) {
			info.setFileSerial(fileSerial.getStringCellValue());
		}
		
		Cell bpNo = cells.get(1);
		if (bpNo != null) {
			info.setBpNo(bpNo.getStringCellValue());
		}
		
		Cell loanType = cells.get(2);
		if (loanType != null) {
			info.setLoanType(loanType.getStringCellValue());
		}
		
		Cell nameOfBorrower = cells.get(3);
		if (nameOfBorrower != null) {
			info.setNameOfBorrower(nameOfBorrower.getStringCellValue());
		}

		Cell dateOfBirth = cells.get(4);
		if (dateOfBirth != null) {
			 if (HSSFDateUtil.isCellDateFormatted(dateOfBirth)) {
			          info.setDateOfBirth(dateOfBirth.getDateCellValue());
			    }
		}
	
		Cell designation = cells.get(5);
		if (designation != null) {
			info.setDesignation(designation.getStringCellValue());
		}
		
		Cell customerType = cells.get(6);
		if (customerType != null) {
			info.setCustomerType(customerType.getStringCellValue());
		}
		
		String cif = dataFormatter.formatCellValue(cells.get(7));
		if (cif != null) {
			info.setcIf(cif);
		}

		String account = dataFormatter.formatCellValue(cells.get(8));
		if (account != null) {
			info.setAccountNo(account);
		}
		String nid = dataFormatter.formatCellValue(cells.get(9));
		if (nid != null) {
			info.setnId(nid);
		}
		
		String tin = dataFormatter.formatCellValue(cells.get(10));
		if (tin != null) {
			info.setTiN(tin);
		}
		
		Cell dateOfJoin = cells.get(11);
		if (dateOfJoin != null) {
			 if (HSSFDateUtil.isCellDateFormatted(dateOfJoin)) {
			          info.setDateOfJoin(dateOfJoin.getDateCellValue());
			    }
		}
		
		Cell crmReceived = cells.get(12);
		if (crmReceived != null) {
			 if (HSSFDateUtil.isCellDateFormatted(crmReceived)) {
			          info.setcRmReceivedDate(crmReceived.getDateCellValue());
			    }
		}
		
		Cell analysit = cells.get(13);
		if (analysit != null) {
			info.setAnalyst(analysit.getStringCellValue());
		}
		
		Cell status = cells.get(14);
		if (status != null) {
			info.setStatus(status.getStringCellValue());
		}		
		
		Cell appliedAmound = cells.get(15);
		if (appliedAmound != null) {
			if(appliedAmound.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {				
				info.setAppliedAmount(appliedAmound.getNumericCellValue());
			}
		}
		
		Cell approvedAmound = cells.get(16);
		if (approvedAmound != null) {
			if(approvedAmound.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {				
				info.setApprovedAmount(approvedAmound.getNumericCellValue());
			}
		}
		
		Cell statusDate = cells.get(17);
		if (statusDate != null) {
			 if (HSSFDateUtil.isCellDateFormatted(statusDate)) {
			          info.setStatusDate(statusDate.getDateCellValue());
			    }
		}
		
		Cell sentToCadDate = cells.get(18);
		if (sentToCadDate != null) {
			 if (HSSFDateUtil.isCellDateFormatted(sentToCadDate)) {
			          info.setSendToCadDate(sentToCadDate.getDateCellValue());
			    }
		}
		
		Cell tenor = cells.get(19);
		if (tenor != null) {
			if(tenor.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {				
				info.setTenorYear(tenor.getNumericCellValue());
			}
		}
		
		Cell rate = cells.get(20);
		if (rate != null) {
			if(rate.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {				
				info.setInterestRate(rate.getNumericCellValue());
			}
		}
		Cell district = cells.get(21);
		if (district != null) {
			info.setDistrict(district.getStringCellValue());
		}
		Cell dbr = cells.get(22);
		if (dbr != null) {
			if(dbr.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {				
				info.setProposedDBR(dbr.getNumericCellValue());
			}
		}
		Cell mobile = cells.get(23);
		if (mobile != null) {
			info.setMobile(mobile.getStringCellValue());
		}
		Cell branch = cells.get(24);
		if (branch != null) {
			info.setSourcingBrc(branch.getStringCellValue());
		}
		
		String rmrc = dataFormatter.formatCellValue(cells.get(25));
		if (rmrc != null) {
			info.setRmRcCode(rmrc);
		}
		Cell gurantorName = cells.get(26);
		if (gurantorName != null) {
			info.setNameOfGuarantor(gurantorName.getStringCellValue());
		}
		String gurantorNid = dataFormatter.formatCellValue(cells.get(27));
		if (gurantorNid != null) {
			info.setNidOfGurantor(gurantorNid);
		}
		Cell topUp = cells.get(28);
		if (topUp != null) {
			info.setOverLoan(topUp.getStringCellValue());
		}
		Cell marriedStatus = cells.get(29);
		if (marriedStatus != null) {
			info.setMaritalStatus(marriedStatus.getStringCellValue());
		}
		Cell loanAcc = cells.get(30);
		if (loanAcc != null) {
			info.setLoanAccount(loanAcc.getStringCellValue());
		}
		Cell nameOfBangla = cells.get(31);
		if (nameOfBangla != null) {
			info.setBanglaNameOfBorrower(nameOfBangla.getStringCellValue());
		}
		Cell division = cells.get(32);
		if (division != null) {
			info.setDivision(division.getStringCellValue());
		}
		return info;
	}

	
	/**
	 * @param ob
	 * @return
	 */
//	private Customer getBaseCustomerDta(ImportData ob) {
//		// TODO Auto-generated method stub
//		Customer cust = new Customer();
//			 
//			 cust.setBpNo(ob.getBpNo());
//			 cust.setCustomerName(ob.getNameOfBorrower());
//			 cust.setDesignation(ob.getDesignation());			 
//			 cust.setJoiningDate(ob.getDateOfBirth());			 
//			 cust.setNid(ob.getnId());
//			 cust.setTin(ob.getTiN());
//			 cust.setAccountNo(ob.getAccountNo());
//			 cust.setCif(ob.getcIf());
//			 cust.setMaritalStatus(ob.getMaritalStatus());
//			 cust.setMobile(ob.getMobile());
//			 cust.setBanglaNameOfBorrower(ob.getBanglaNameOfBorrower());
//			 cust.setOfficeDistrict(ob.getDistrict());
//			 cust.setOfficeDivision(ob.getDivision());				
//		return cust;
//	}
	/**
	 * @param ob
	 * @return
	 */
//	private Loan getBaseLoanData(ImportData ob) {
//	     	Loan loan = new Loan();
//			
//			loan.setAppliedLoanAmount(ob.getAppliedAmount());
//			loan.setRecommendedForApproval(ob.getApprovedAmount());
//			String overLoan=ob.getOverLoan();
//			String custtype=ob.getCustomerType();
//			String loanType=ob.getLoanType();
//			 
//			if(overLoan!=null) {
//				loan.setOverLoan(1);
//			}else {
//				loan.setOverLoan(0);
//			}		
//			 if(custtype.equals("Gold")) {
//				 loan.setIdCustomerTypeKey(101358);
//			 }else{
//				 loan.setIdCustomerTypeKey(100080);
//			 }	
//			 
////			 if(loanType.equals("PL-Bp")) {
////				 loan.setIdLoanTypeKey(65);
////			 }else if(loanType.equals("GPF")) {
////				 loan.setIdLoanTypeKey(565);
////			 }else if(loanType.equals("HBL-BP")) {
////				 loan.setIdLoanTypeKey(767);
////			 }
////			 else{
////				 loan.setIdLoanTypeKey(676);
////			 }	
//			 
//			loan.setTenorYear(ob.getTenorYear());
//			loan.setInterestRate(ob.getInterestRate());
//			loan.setProposedDBR(ob.getProposedDBR());
//			loan.setNameOfGuarantor(ob.getNameOfGuarantor());
//			loan.setGuarantorNid(ob.getNidOfGurantor());
//			loan.setSourcingBrc(ob.getSourcingBrc());
//			loan.setStaffId(ob.getRmRcCode());
//			log.debug("Read Excel file to  Loan Application detail [{}], [{}]",loan.toString());
//		
//		return loan;
//	}

	
}
