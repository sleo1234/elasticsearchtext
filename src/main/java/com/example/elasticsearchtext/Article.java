package com.example.elasticsearchtext;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "articles")
public class Article {
	
	@Field
	private String id;
	
	@Field
	private String author;
	
	@Field
	private String content;

}
