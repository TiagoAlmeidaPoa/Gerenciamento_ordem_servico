package com.tiago.ordemservico.resource;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.tiago.ordemservico.domain.Cliente;
import com.tiago.ordemservico.dtos.ClienteDTO;
import com.tiago.ordemservico.services.ClienteService;

@RestController
@RequestMapping(value = "clientes")
public class ClienteResource {

	@Autowired
	private ClienteService clienteService;

	@GetMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> findById(@PathVariable Integer id) {
		ClienteDTO objDTO = new ClienteDTO(clienteService.findById(id));
		return ResponseEntity.ok(objDTO);
	}

	@GetMapping
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<ClienteDTO> listaDTO = clienteService.findAll().stream().map(obj -> new ClienteDTO(obj))
				.collect(Collectors.toList());

		return ResponseEntity.ok().body(listaDTO);
	}

	@PostMapping
	public ResponseEntity<ClienteDTO> create(@Valid @RequestBody ClienteDTO objDTO) {
		Cliente newObj = clienteService.create(objDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<ClienteDTO> update(@PathVariable Integer id, @Valid @RequestBody ClienteDTO objDTO) {
		ClienteDTO newDTO = new ClienteDTO(clienteService.update(id, objDTO));
		return ResponseEntity.ok().body(newDTO);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
		clienteService.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
