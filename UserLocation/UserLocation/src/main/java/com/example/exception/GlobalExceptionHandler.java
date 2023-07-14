package com.example.exception;

import java.time.LocalDateTime;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<MyErrorDetails> myErrorNotFound(NotFoundException er, WebRequest req){
		MyErrorDetails err = new MyErrorDetails(er.getMessage(), req.getDescription(false), LocalDateTime.now());
		return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<MyErrorDetails> myErrorEmailInvalid(ResourceNotFoundException er, WebRequest req){
		MyErrorDetails err = new MyErrorDetails(er.getMessage(), req.getDescription(false), LocalDateTime.now());
		return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
	}
}
