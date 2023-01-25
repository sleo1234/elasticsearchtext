package com.example.elasticsearchtext;

public class DocumentField {
	
	String fieldName;
	
	String textValue;

	
	public DocumentField() {
		
	}
	
	public DocumentField(String fieldName, String textValue) {
		super();
		this.fieldName = fieldName;
		this.textValue = textValue;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getTextValue() {
		return textValue;
	}

	public void setTextValue(String textValue) {
		this.textValue = textValue;
	}
	
	

}
