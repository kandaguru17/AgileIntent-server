package io.app.agileintent.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
public interface ErrorMapService {

	public ResponseEntity<Map<String, String>> mapErrors(BindingResult result);
		
	
}
