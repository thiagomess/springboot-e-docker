package br.com.erudio.exceptions;

import java.io.Serializable;
import java.util.Date;

public class ExceptionResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	
	private Date timestamp;
	private String message;
	private String detais;
	
	public ExceptionResponse(Date timestamp, String message, String detais) {
		this.timestamp = timestamp;
		this.message = message;
		this.detais = detais;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

	public String getDetais() {
		return detais;
	}
	
}
