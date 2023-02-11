package com.tiago.ordemservico.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiago.ordemservico.domain.Pessoa;
import com.tiago.ordemservico.domain.Tecnico;
import com.tiago.ordemservico.dtos.TecnicoDTO;
import com.tiago.ordemservico.repository.PessoaRepository;
import com.tiago.ordemservico.repository.TecnicoRepository;
import com.tiago.ordemservico.services.exceptions.DataIntegratyViolationException;
import com.tiago.ordemservico.services.exceptions.ObjectNotFoundException;

@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository tecnicoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;

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
		if(findByCPF(objDTO) != null) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		Tecnico newObj = new Tecnico(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone());
		return tecnicoRepository.save(newObj);
	}
	
	private Pessoa findByCPF(TecnicoDTO objDTO) {
		Pessoa obj = pessoaRepository.findByCPF(objDTO.getCpf());
		if(obj != null) {
			return obj;
		}
		return null;
	}

	public Tecnico update(Integer id, @Valid TecnicoDTO objDto) {
		Tecnico oldObj = findById(id);
		
		if(findByCPF(objDto) != null && findByCPF(objDto).getId() != id) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		
		oldObj.setCpf(objDto.getCpf());
		oldObj.setNome(objDto.getNome());
		oldObj.setTelefone(objDto.getTelefone());
		
		return tecnicoRepository.save(oldObj);
	}

	public void deleteById(Integer id) {
		Tecnico obj = findById(id);
		if(obj.getOrdensDeServico().size() > 0) {
			throw new DataIntegratyViolationException("Técnico possui ordens de serviço, não pode ser deletado!");
		}
		tecnicoRepository.deleteById(id);
	}

}
