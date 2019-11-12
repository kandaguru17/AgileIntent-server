package io.app.agileintent.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@Service
public interface ErrorMapService {

	public ResponseEntity<Map<String, String>> mapErrors(BindingResult result);
		
	
}
