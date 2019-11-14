package com.gestao.cargas.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ErroDto {
	
	private String campo;
	private String erro;
	
	public ErroDto(String campo, String erro) {
		super();
		this.campo = campo;
		this.erro = erro;
	}

}
