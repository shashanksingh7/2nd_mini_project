package com.ashokitproject.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.ashokitproject.binding.CitizenPlan;
import com.ashokitproject.binding.SearchRequest;


public interface ReportService {

	
	public List<String>getPlanName();
	
	public List<String>getPlanStatus();
	
	public List<CitizenPlan>getCitizenPlans(SearchRequest request);
	
	public void exportExcel(HttpServletResponse response) throws Exception;
	
	public void exportPdf(HttpServletResponse response) throws RuntimeException, Exception;
	
}
