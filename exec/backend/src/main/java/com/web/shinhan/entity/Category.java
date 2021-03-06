package com.web.shinhan.entity;

import javax.persistence.*;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity(name = "category")
public class Category {
	@Id
	private String categoryCode;

	private String categoryClassification;
	private String categoryName;

//	@OneToOne(mappedBy = "category")
//	private Store store;

	@Builder
	public Category(String categoryCode, String categoryClassification, String categoryName) {
		this.categoryCode = categoryCode;
		this.categoryClassification = categoryClassification;
		this.categoryName = categoryName;
	}

}