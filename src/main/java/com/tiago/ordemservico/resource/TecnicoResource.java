package com.tiago.ordemservico.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tiago.ordemservico.domain.Tecnico;
import com.tiago.ordemservico.services.TecnicoService;

@RestController
@RequestMapping(value = "tecnicos")
public class TecnicoResource {
	
	@Autowired
	private TecnicoService tecnicoService;

	@GetMapping(value = "/{id}")
	public ResponseEntity<Tecnico> findById(@PathVariable Integer id) {
		return ResponseEntity.ok(tecnicoService.findById(id));
	}

}
