package com.routy.routyback.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class IngredientDTO {
	private int ingNo;
	private String ingName;
	private String ingEnName;
	private String ingDesc;
	private int ingAllergen;
	private int ingGrpNo;
	private Date ingRegDate;
}
