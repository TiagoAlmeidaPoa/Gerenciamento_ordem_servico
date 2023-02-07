package com.tiago.ordemservico.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiago.ordemservico.domain.Tecnico;
import com.tiago.ordemservico.dtos.TecnicoDTO;
import com.tiago.ordemservico.repository.TecnicoRepository;
import com.tiago.ordemservico.services.exceptions.ObjectNotFoundException;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository tecnicoRepository;

	public Tecnico findById(Integer id) {
		Optional<Tecnico> obj = tecnicoRepository.findById(id);
		return obj.orElseThrow( () -> new ObjectNotFoundException(
				"Objeto não encontrado ! Id: " + id 
				+ ", Tipo: " + Tecnico.class.getName()) );
	}

	public List<Tecnico> findAll() {
		return tecnicoRepository.findAll();		
	}
	
	public Tecnico create(TecnicoDTO objDTO) {
		Tecnico newObj = new Tecnico(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone());
		return tecnicoRepository.save(newObj);
	}

}
