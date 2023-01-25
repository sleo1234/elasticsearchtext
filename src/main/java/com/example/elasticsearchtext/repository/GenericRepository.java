package com.example.elasticsearchtext.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch._types.query_dsl.RegexpQuery;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;

@Repository
public class GenericRepository {

	@Autowired
	private ElasticsearchClient elasticsearchClient;
	
	public <T> List<T> getByFieldName(String fieldName, String indexName, String searchText) throws ElasticsearchException, IOException{
		List<T> items = new ArrayList<>();
		
		
		SearchResponse<Object> searchResponse = elasticsearchClient.search(
				s -> s.index(indexName)
				       .query(q -> 
				        q.match(t -> 
				        t.field(fieldName)
				    		   .query(searchText)
				    		   )
				        ), Object.class);
		
		
		if (searchResponse.hits().hits().size() > 0) {
			List<Hit<Object>> hitsResponse = searchResponse.hits().hits();
			items = (List<T>) convertTo(hitsResponse);
		}
		
		
		
		return items;
		
		
	}
	
	
	public <T> List<T> regexQuery(String fieldName, String indexName, String regex) throws ElasticsearchException, IOException{
		List<T> items = new ArrayList<>();
		System.out.println("======================"+regex);
		Query queries =QueryBuilders
				.regexp()
				.field(fieldName)
				.value(regex)
				.flags("ALL")
				.maxDeterminizedStates(10000)
				.caseInsensitive(true)
				.build()._toQuery();
		SearchResponse<Object> searchResponse = elasticsearchClient.search(
				s -> s.index(indexName)
				.query(queries),Object.class);
		
		
		
		
		System.out.println("--------------------" +searchResponse.hits().hits().size() );
		if (searchResponse.hits().hits().size() > 0) {
			List<Hit<Object>> hitsResponse = searchResponse.hits().hits();
			items = (List<T>) convertTo(hitsResponse);
		}
		return items;
	}
	
	
	
	public <T> List<T> convertTo (List<Hit<T>> hitsList){
		
		
		 List<T> returnedList = new ArrayList<>();
		for (Hit<T> hit : hitsList) {
			returnedList.add(hit.source());
		}
		return returnedList;
	}
}