package com.example.elasticsearchtext.repository;

import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.elasticsearchtext.Article;

@Repository
public class ArticleRepository {

	@Autowired GenericRepository repo;
	String indexName ="articles";
	
	public void searchFor(){
	   // FuzzyQueryBuilder builder = fuzzyQuery("text", Article.class);
	    
	    
	    
	}
}
