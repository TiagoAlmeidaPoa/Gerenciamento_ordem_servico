package com.tiago.ordemservico.resource;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.tiago.ordemservico.domain.Tecnico;
import com.tiago.ordemservico.dtos.TecnicoDTO;
import com.tiago.ordemservico.services.TecnicoService;

@CrossOrigin("*")
@RestController
@RequestMapping(value = "tecnicos")
public class TecnicoResource {

	@Autowired
	private TecnicoService tecnicoService;

	@GetMapping(value = "/{id}")
	public ResponseEntity<TecnicoDTO> findById(@PathVariable Integer id) {
		TecnicoDTO objDTO = new TecnicoDTO(tecnicoService.findById(id));
		return ResponseEntity.ok(objDTO);
	}

	@GetMapping
	public ResponseEntity<List<TecnicoDTO>> findAll() {
		List<TecnicoDTO> listaDTO = tecnicoService.findAll().stream().map(obj -> new TecnicoDTO(obj))
				.collect(Collectors.toList());

		return ResponseEntity.ok().body(listaDTO);
	}

	@PostMapping
	public ResponseEntity<TecnicoDTO> create(@Valid @RequestBody TecnicoDTO objDTO) {
		Tecnico newObj = tecnicoService.create(objDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<TecnicoDTO> update(@PathVariable Integer id, @Valid @RequestBody TecnicoDTO objDTO){
		TecnicoDTO newDTO = new TecnicoDTO(tecnicoService.update(id, objDTO));
		return ResponseEntity.ok().body(newDTO);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
		tecnicoService.deleteById(id);
		return ResponseEntity.noContent().build(); 
	}

}
