package com.routy.routyback.common.category;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Category {
	private String mainStr;
	private Map<Integer, String> sub;
	
	public Category(String mainStr, Map<Integer, String> sub) {
		this.mainStr = mainStr;
        this.sub = sub;
	}

}
