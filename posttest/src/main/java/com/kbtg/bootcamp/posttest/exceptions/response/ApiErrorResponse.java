package com.kbtg.bootcamp.posttest.exceptions.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ApiErrorResponse {
	private LocalDateTime timestamp;
	private int status;
	private String error;
	private String message;
	private String path;

	public ApiErrorResponse(LocalDateTime timestamp, int status, String error, String message, String path) {
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
		this.message = message;
		this.path = path;
	}


}
