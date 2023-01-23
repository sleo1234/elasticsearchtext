package com.example.elasticsearchtext.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.elasticsearchtext.Product;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.indices.AnalyzeRequest;
import co.elastic.clients.elasticsearch.indices.AnalyzeRequest.Builder;
import co.elastic.clients.elasticsearch.indices.AnalyzeResponse;
import co.elastic.clients.elasticsearch.indices.analyze.AnalyzeDetail;
import co.elastic.clients.elasticsearch.indices.analyze.AnalyzeToken;
import co.elastic.clients.util.ObjectBuilder;


@Repository
public class ProductRepository {

	@Autowired
	GenericRepository repo;
	@Autowired ElasticsearchClient esClient;
	
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
	     System.out.println("Output of the analyzer: =====" + 
			buildAnalyzer("The fox3 jumps bla bla").tokens().get(0).token());
    // System.out.println("------------------" + response.hits().hits().toString());
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

	
	public AnalyzeResponse buildAnalyzer(String text) throws ElasticsearchException, IOException {
		
	  
		
		Function<Builder, ObjectBuilder<AnalyzeRequest>> fn = new Function<AnalyzeRequest.Builder, ObjectBuilder<AnalyzeRequest>>() {
			
			@Override
			public ObjectBuilder<AnalyzeRequest> apply(Builder t) {
				t.analyzer("standard")
				//.field(fieldName)
				.text(text);
				return t;
			}
		};
		
		
		AnalyzeRequest request = AnalyzeRequest.of(fn);
		
		 AnalyzeResponse response = esClient.indices().analyze(request);
		 List<AnalyzeToken> tokens =response.tokens();
		 AnalyzeDetail output = response.detail();
		System.out.println("*************" + response.tokens().toString());
		return response;
	}
	
	
	
	
	
}
