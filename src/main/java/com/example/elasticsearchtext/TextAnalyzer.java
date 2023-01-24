package com.example.elasticsearchtext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.ElasticsearchException;
import co.elastic.clients.elasticsearch.indices.AnalyzeRequest;
import co.elastic.clients.elasticsearch.indices.AnalyzeRequest.Builder;
import co.elastic.clients.elasticsearch.indices.AnalyzeResponse;
import co.elastic.clients.elasticsearch.indices.analyze.AnalyzeToken;
import co.elastic.clients.util.ObjectBuilder;

@Component
public class TextAnalyzer {
	
	
	private List<String> stringTokens;
	
	@Autowired ElasticsearchClient esClient;
	
	
	public AnalyzeResponse buildAnalyzer (String text, String analyzerType) throws ElasticsearchException, IOException {
	Function<Builder, ObjectBuilder<AnalyzeRequest>> fn = 
	new Function<AnalyzeRequest.Builder, ObjectBuilder<AnalyzeRequest>>() {
		
		@Override
		public ObjectBuilder<AnalyzeRequest> apply(Builder t) {
			t.analyzer(analyzerType)
			 .text(text);
			return t;
		}
		
		
	};
	
	
	AnalyzeRequest request = AnalyzeRequest.of(fn);
	
	 AnalyzeResponse response = esClient.indices().analyze(request);
	 return response;
	
	}
	
	
	public List<String> convertToArray(AnalyzeResponse response){
		stringTokens = new ArrayList<>();
	List<AnalyzeToken> tokens = response.tokens();
		
		for (int i=0; i<tokens.size(); i++) {
			stringTokens.add(tokens.get(i).token());
		}
		
		return stringTokens;
	}
	  
		 
		
		

}
