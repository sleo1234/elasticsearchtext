package com.example.elasticsearchtext.api;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.elasticsearchtext.DocumentField;
import com.example.elasticsearchtext.Product;
import com.example.elasticsearchtext.repository.ProductRepository;

import co.elastic.clients.elasticsearch._types.ElasticsearchException;

@RestController
public class ProductController {

	@Autowired ProductRepository repo;
	
	@PostMapping("/createOrUpdateDocument")
	 
	 public ResponseEntity<Object> createDocument(@RequestBody Product product) throws IOException{
		
		 String response = repo.createOrUpdateDocument(product);
		 
		 return new ResponseEntity<>(response,HttpStatus.OK);
	 }
	@GetMapping("/fuzzy")
	public ResponseEntity<Object> getFuzzySearch(@RequestParam("fieldName") String fieldName, @RequestParam("textValue") String textValue) throws ElasticsearchException, IOException{
		
		return new ResponseEntity<>(repo.searchFor(fieldName, textValue), HttpStatus.FOUND);
	}
	
	@PostMapping("/regex")
	public ResponseEntity<Object> regexSearch(@RequestBody DocumentField document) throws ElasticsearchException, IOException{
		DocumentField fields = new DocumentField(document.getFieldName(), document.getTextValue());
		fields.setFieldName(document.getFieldName());
		fields.setTextValue(document.getTextValue());
		return new ResponseEntity<>(repo.getProds( fields.getFieldName(),fields.getTextValue()), HttpStatus.FOUND);
	}
	
}
