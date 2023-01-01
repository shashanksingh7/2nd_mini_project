package com.ashokitproject.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ashokitproject.binding.CitizenPlan;
import com.ashokitproject.binding.SearchRequest;
import com.ashokitproject.service.ReportService;

@RestController
public class ReportRestController {

	@Autowired
	private ReportService reportService;

	@GetMapping("/all-plan-name")
	public ResponseEntity<List<String>> getPlanName() {
		List<String> planName = reportService.getPlanName();
		return new ResponseEntity<>(planName, HttpStatus.OK);

	}

	@GetMapping("/all-plan-status")
	public ResponseEntity<List<String>> getPlanStatus() {
		List<String> planStatus = reportService.getPlanStatus();
		return new ResponseEntity<>(planStatus, HttpStatus.OK);

	}

	@PostMapping("/search")
	public ResponseEntity<List<CitizenPlan>> getCitizenPlans(@RequestBody SearchRequest request) {
		List<CitizenPlan> citizenPlan = reportService.getCitizenPlans(request);
		return new ResponseEntity<>(citizenPlan, HttpStatus.OK);

	}

	@GetMapping("/excel")
	public void exportExcel(HttpServletResponse response) throws Exception {
		response.setContentType("application/octet-stream");
		String key = "Content-Disposition";
		String value = "attachement;file=citizens.xls";
		response.setHeader(key, value);
		reportService.exportExcel(response);
		response.flushBuffer();

	}
	@GetMapping("/pdf")
	public void exportPdf(HttpServletResponse response) throws RuntimeException, Exception{
		
		response.setContentType("application/pdf");
		String key="Content-Disposition";
		String value="attachement;file=plans.pdf";
		response.setHeader(key, value);
		reportService.exportPdf(response);
		response.flushBuffer();
	}

}
