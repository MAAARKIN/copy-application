package br.com.copy.helper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public enum ResponseStatus {

	USER_NOT_EXIST(998, "Usuario nao existe"),
	ERRO_DE_APLICACAO(999, "Ocorreu um problema na execução da aplicação");
	
	@Getter @Setter
	private int code;
	@Getter @Setter
	private String message;
}
