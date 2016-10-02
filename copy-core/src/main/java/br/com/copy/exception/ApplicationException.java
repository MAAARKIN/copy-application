package br.com.copy.exception;

import br.com.copy.helper.ResponseInfo;
import br.com.copy.helper.ResponseStatus;
import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Marcos Filho
 *
 */
public class ApplicationException extends Exception {
	private static final long serialVersionUID = 97834081784802611L;
	
	@Getter @Setter
	private int code;
	@Getter @Setter
	private String message;
	private ResponseStatus status;

	public ApplicationException(ResponseStatus response) {
		this.code = response.getCode();
		this.message = response.getMessage();
		this.status = response;
	}
	
	public ResponseInfo getResponseInfo() {
		return new ResponseInfo(this.status.getCode(), this.status.getMessage());
	}
}
