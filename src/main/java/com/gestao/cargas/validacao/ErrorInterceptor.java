package com.gestao.cargas.validacao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.gestao.cargas.dto.ErroDto;

@RestControllerAdvice
public class ErrorInterceptor {
	
	@Autowired
	private MessageSource messageSource;
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(Exception.class)
	public List<ErroDto> handle(Exception exception) {
		List<ErroDto> dto = new ArrayList<>();
		ErroDto erro = new ErroDto(exception.getClass().getName(), exception.getMessage());
		dto.add(erro);
		return dto;
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public List<ErroDto> handle(MethodArgumentNotValidException exception) {
		List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
		return trataExcecao(fieldErrors);
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public List<ErroDto> handle(HttpMessageNotReadableException exception) {
		Throwable mostSpecificCause = exception.getMostSpecificCause();
		String exceptionName = mostSpecificCause.getClass().getName();
        String message = mostSpecificCause.getMessage();
        
		List<ErroDto> dto = new ArrayList<>();
		ErroDto erro = new ErroDto(exceptionName, message);
		dto.add(erro);
		return dto;
	}

	private List<ErroDto> trataExcecao(List<FieldError> fieldErrors) {
		List<ErroDto> dto = new ArrayList<>();
		fieldErrors.forEach(e -> {
			String mensagem = messageSource.getMessage(e, LocaleContextHolder.getLocale());
			ErroDto erro = new ErroDto(e.getField(), mensagem);
			dto.add(erro);
		});
		
		return dto;
	}

}
