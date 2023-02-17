package com.tiago.ordemservico.resource;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.tiago.ordemservico.dtos.OrdemServicoDTO;
import com.tiago.ordemservico.services.OrdemServicoService;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "/ordemservicos")
public class OrdemServicoResource {

	@Autowired
	private OrdemServicoService service;

	@GetMapping(value = "/{id}")
	public ResponseEntity<OrdemServicoDTO> findById(@PathVariable Integer id) {
		OrdemServicoDTO objDTO = new OrdemServicoDTO(service.findById(id));
		return ResponseEntity.ok().body(objDTO);
	}

	@GetMapping
	public ResponseEntity<List<OrdemServicoDTO>> findAll() {
		List<OrdemServicoDTO> listaDTO = service.findAll().stream().map(obj -> new OrdemServicoDTO(obj))
				.collect(Collectors.toList());

		return ResponseEntity.ok().body(listaDTO);
	}

	@PostMapping
	public ResponseEntity<OrdemServicoDTO> create(@Valid @RequestBody OrdemServicoDTO obj) {
		obj = new OrdemServicoDTO(service.created(obj));
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping
	public ResponseEntity<OrdemServicoDTO> update(@Valid @RequestBody OrdemServicoDTO obj){
		obj = new OrdemServicoDTO(service.update(obj));
		return ResponseEntity.ok().body(obj);
	}

}
