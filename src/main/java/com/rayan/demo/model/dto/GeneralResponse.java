package com.rayan.demo.model.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class GeneralResponse<T> {

	private LocalDateTime time;
	private int code;
	private String message;
	private T body;


	public GeneralResponse(int code, String message, T body) {
		this.code = code;
		this.message = message;
		this.body = body;
		this.time = LocalDateTime.now();
	}

	public GeneralResponse(int code, String message) {
		this.code = code;
		this.message = message;
		this.time = LocalDateTime.now();
	}
}
