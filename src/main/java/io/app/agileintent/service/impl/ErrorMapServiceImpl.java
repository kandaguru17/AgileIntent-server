package io.app.agileintent.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import io.app.agileintent.service.ErrorMapService;

@Service
public class ErrorMapServiceImpl implements ErrorMapService {


	public ResponseEntity<Map<String, String>> mapErrors(BindingResult result) {

		Map<String, String> errorMap = new HashMap<>();
		
		if (result.hasErrors()) {

			for (FieldError error : result.getFieldErrors()) {
				errorMap.put(error.getField(), error.getDefaultMessage());
			}

			return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.BAD_REQUEST);

		}
		return null;

	}
}
