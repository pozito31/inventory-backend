package com.company.inventory.services;

import org.springframework.http.ResponseEntity;

import com.company.inventory.response.CategoryResponseREST;

public interface ICategoryService {
	
	public ResponseEntity<CategoryResponseREST> search();
	public ResponseEntity<CategoryResponseREST> searchById(Long id);

}
