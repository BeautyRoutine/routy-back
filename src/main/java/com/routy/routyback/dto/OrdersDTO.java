package com.routy.routyback.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrdersDTO {
	private int odNo;
	private int userNo;
	private String odName;
	private String odHp;
	private int odZip;
	private String odJibunAddr;
	private String odRoadAddr;
	private String odDetailAddr;
	private int odBcCode;
	private int odPrdPrice;
	private int odDelvPrice;
	private String odDelvMsg;
	private int odDelvKeyType;
	private String odDelvKey;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date odRegdate;

}
