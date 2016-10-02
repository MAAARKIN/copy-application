package br.com.copy.helper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class ResponseInfo {

	@Getter @Setter
	private int code;
	@Getter @Setter
	private String message;
}
