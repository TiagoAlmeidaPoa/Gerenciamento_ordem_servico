package com.tiago.ordemservico.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiago.ordemservico.domain.OrdemServico;
import com.tiago.ordemservico.repository.OrdemServicoRepository;
import com.tiago.ordemservico.services.exceptions.ObjectNotFoundException;

@Service
public class OrdemServicoService {
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	public OrdemServico findById(Integer id) {
		Optional<OrdemServico> obj = ordemServicoRepository.findById(id);
		return obj.orElseThrow( () -> new ObjectNotFoundException("Objeto não encontrado ! id: " + id 
				+ ", Tipo: " + OrdemServico.class.getName()) );
	}
	
	public List<OrdemServico> findAll(){
		return ordemServicoRepository.findAll();
	}

}
