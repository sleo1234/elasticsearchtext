package com.example.elasticsearchtext.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.elasticsearchtext.Product;
import com.example.elasticsearchtext.TextAnalyzer;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.indices.AnalyzeResponse;


@Repository
public class ProductRepository {

	@Autowired
	GenericRepository repo;
	@Autowired ElasticsearchClient esClient;
	
	@Autowired
	TextAnalyzer analyze ;
	
	String indexName = "products";

	public List<Product> searchFor(String fieldName, String textValue) throws ElasticsearchException, IOException {
		

		List<Product> results = new ArrayList<Product>();
	SearchResponse<Product> response = esClient.search(s -> s
			    .index(indexName) 
			    .query(q -> 
			     q.fuzzy(t -> 
			     t.field(fieldName)
			     .value(textValue).
			     fuzziness("AUTO").
			     maxExpansions(50).
			     transpositions(true)
			            )
			    ),
			    Product.class      
			);
    
   AnalyzeResponse res = analyze.buildAnalyzer("The lazy foz bal bla bla", "standard");
   System.out.println(analyze.convertToArray(res));
       results = repo.convertTo(response.hits().hits());
       return results;
	}

	
	
	
	
	public String createOrUpdateDocument(Product product) throws IOException {

		
		IndexResponse response = esClient
				.index(i -> i.index(indexName).id(product.getId()).document(product));

		if (response.result().name().equals("Created")) {
			return new StringBuilder("Document has been successfully created.").toString();
		} else if (response.result().name().equals("Updated")) {
			return new StringBuilder("Document has been successfully updated.").toString();
		}
		return new StringBuilder("Error while performing the operation.").toString();

	}

	
	
	
	public List<Product> getProds( String fieldName, String regex) throws ElasticsearchException, IOException{
		return repo.regexQuery(fieldName, indexName, regex);
	}
	
	
	
}
