package com.company.inventory.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.company.inventory.model.Category;
import com.company.inventory.response.CategoryResponseREST;
import com.company.inventory.services.ICategoryService;
import com.company.inventory.util.CategoryExcelExporter;

import jakarta.servlet.http.HttpServletResponse;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api/v1")
public class CategoryRestController {
	
	@Autowired
	private ICategoryService service;
	
	@GetMapping("/categories")
	public ResponseEntity<CategoryResponseREST> searchCategories() {
		
		ResponseEntity<CategoryResponseREST> response = service.search();
		return response;
	}
	
	@GetMapping("/categories/{id}")
	public ResponseEntity<CategoryResponseREST> searchCategoriesById(@PathVariable Long id) {
		
		ResponseEntity<CategoryResponseREST> response = service.searchById(id);
		return response;
	}
	
	@PostMapping("/categories")
	public ResponseEntity<CategoryResponseREST> save(@RequestBody Category category) {
		
		ResponseEntity<CategoryResponseREST> response = service.save(category);
		return response;
	}
	
	@PutMapping("/categories/{id}")
	public ResponseEntity<CategoryResponseREST> update(@RequestBody Category category, @PathVariable Long id) {
		
		ResponseEntity<CategoryResponseREST> response = service.update(category, id);
		return response;
	}
	
	@DeleteMapping("/categories/{id}")
	public ResponseEntity<CategoryResponseREST> delete(@PathVariable Long id) {
		
		ResponseEntity<CategoryResponseREST> response = service.deleteById(id);
		return response;
	}
	
	@GetMapping("/categories/export/excel")
	public void exportToExcel(HttpServletResponse response) throws IOException {
		
		response.setContentType("application/octet-stream");
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=result_category";
		response.setHeader(headerKey, headerValue);
		
		ResponseEntity<CategoryResponseREST> categoryResponse = service.search();
		
		CategoryExcelExporter excelExporter = new CategoryExcelExporter(
				categoryResponse.getBody().getCategoryResponse().getCategory());
		
		excelExporter.export(response);
	}
}
