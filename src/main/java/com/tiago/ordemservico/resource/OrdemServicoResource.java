package com.tiago.ordemservico.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tiago.ordemservico.dtos.OrdemServicoDTO;
import com.tiago.ordemservico.services.OrdemServicoService;

@RestController
@RequestMapping(value = "/ordemservicos")
public class OrdemServicoResource {
	
	@Autowired
	private OrdemServicoService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<OrdemServicoDTO> findById(@PathVariable Integer id){
		OrdemServicoDTO objDTO = new OrdemServicoDTO(service.findById(id));
		return ResponseEntity.ok().body(objDTO);
	}

}
