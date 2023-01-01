package com.ashokitproject.service.Impl;

import java.awt.Color;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;


import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.ashokitproject.binding.CitizenPlan;
import com.ashokitproject.binding.SearchRequest;
import com.ashokitproject.repository.CitizenPlanRepository;
import com.ashokitproject.service.ReportService;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private CitizenPlanRepository citizenPlanRepository;

	@Override
	public List<String> getPlanName() {

		return citizenPlanRepository.getPlanName();
	}

	@Override
	public List<String> getPlanStatus() {
		// TODO Auto-generated method stub
		return citizenPlanRepository.getPlanStatus();
	}

	@Override
	public List<CitizenPlan> getCitizenPlans(SearchRequest request) {

		CitizenPlan citizenPlan = new CitizenPlan();
		if (request.getPlanName() != null && !request.getPlanName().equals("")) {
			citizenPlan.setPlanName(request.getPlanName());
		}
		if (request.getPlanStatus() != null && !request.getPlanStatus().equals("")) {
			citizenPlan.setPlanStatus(request.getPlanStatus());
		}
		Example<CitizenPlan> plan = Example.of(citizenPlan);
		List<CitizenPlan> citList = citizenPlanRepository.findAll(plan);
		return citList;
	}

	@Override
	public void exportExcel(HttpServletResponse response) throws Exception {
		// getting all the record from db

		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Create Plan Info");
		XSSFRow headerRow = sheet.createRow(0);
		headerRow.createCell(0).setCellValue("Id");
		headerRow.createCell(1).setCellValue("Name");
		headerRow.createCell(2).setCellValue("email");
		headerRow.createCell(3).setCellValue("Gender");
		headerRow.createCell(4).setCellValue("Phone No");
		headerRow.createCell(5).setCellValue("Plan Name");
		headerRow.createCell(6).setCellValue("Plan Status");
		headerRow.createCell(7).setCellValue("ssn");

		List<CitizenPlan> records = citizenPlanRepository.findAll();
		int dataRowIndex = 1;
		for (CitizenPlan record : records) {
			XSSFRow dataRow = sheet.createRow(dataRowIndex);
			dataRow.createCell(0).setCellValue(record.getCid());
			dataRow.createCell(1).setCellValue(record.getCname());
			dataRow.createCell(2).setCellValue(record.getEmail());
			dataRow.createCell(3).setCellValue(record.getGender());
			dataRow.createCell(4).setCellValue(record.getPhoneNo());
			dataRow.createCell(5).setCellValue(record.getPlanName());
			dataRow.createCell(6).setCellValue(record.getPlanStatus());
			dataRow.createCell(7).setCellValue(record.getSsn());

			dataRowIndex++;

		}
		ServletOutputStream output = response.getOutputStream();
		workbook.write(output);
		workbook.close();
		output.close();

	}

	@Override
	public void exportPdf(HttpServletResponse response) throws RuntimeException, Exception {

		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());
	    document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        font.setSize(18);
        font.setColor(Color.BLUE);
         
        Paragraph p = new Paragraph("User List of Repost::::", font);
        p.setAlignment(Paragraph.ALIGN_CENTER);
         
        document.add(p);
        
        
        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(100f);
        table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f, 3.5f,4.5f,4.5f,2.5f});
        table.setSpacingBefore(10);
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.BLUE);
        cell.setPadding(8);
         
        Font f = FontFactory.getFont(FontFactory.HELVETICA);
        font.setColor(Color.WHITE);
         
        cell.setPhrase(new Phrase("ID", f));
         
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Name", f));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("email", f));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Gender", f));
        table.addCell(cell);
        
        cell.setPhrase(new Phrase("Phone No", f));
        table.addCell(cell);
         
        cell.setPhrase(new Phrase("Plan Name", f));
        table.addCell(cell);  
        
        cell.setPhrase(new Phrase("Plan Status", f));
        table.addCell(cell); 
        
        cell.setPhrase(new Phrase("ssn", f));
        table.addCell(cell); 
        
        List<CitizenPlan> records = citizenPlanRepository.findAll();
        
        for(CitizenPlan record:records) {
        	 table.addCell(String.valueOf(record.getCid()));
        	 table.addCell(record.getCname());
        	 table.addCell(record.getEmail());
        	 table.addCell(record.getGender());
        	 table.addCell(record.getPhoneNo());
        	 table.addCell(record.getPlanName());
        	 table.addCell(record.getPlanStatus());
        	 table.addCell(String.valueOf(record.getSsn()));
        	
        }
        
        document.add(table);
        document.close();

	}

}
