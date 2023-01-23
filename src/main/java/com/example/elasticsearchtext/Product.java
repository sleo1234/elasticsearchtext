package com.example.elasticsearchtext;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "products")
public class Product {

	
		@Id
		
		private String id;
	
	
	   @Field(type = FieldType.Text, name = "name")
	    private String name;

	    @Field(type = FieldType.Text, name = "description")
	    private String description;

	    @Field(type = FieldType.Double, name = "price")
	    private double price;

	    
	    public Product() {
			
		}
	    
		public Product(String id, String name, String description, double price) {
			super();
			this.id = id;
			this.name = name;
			this.description = description;
			this.price = price;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public double getPrice() {
			return price;
		}

		public void setPrice(double price) {
			this.price = price;
		}

		@Override
		public String toString() {
			return "Product [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price + "]";
		}

	
	
}
