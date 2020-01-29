package br.com.erudio.exceptions;

import java.io.Serializable;
import java.util.Date;

public class ExceptionResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	
	private Date timestamp;
	private Integer status;
	private String error;
	private String message;
	private String detais;
	
	
	public ExceptionResponse(Date timestamp, Integer status, String error, String message, String detais) {
		this.timestamp = timestamp;
		this.status = status;
		this.error = error;
		this.message = message;
		this.detais = detais;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public Date getTimestamp() {
		return timestamp;
	}


	public Integer getStatus() {
		return status;
	}


	public String getError() {
		return error;
	}


	public String getMessage() {
		return message;
	}


	public String getDetais() {
		return detais;
	}
	
	
	
	
}
