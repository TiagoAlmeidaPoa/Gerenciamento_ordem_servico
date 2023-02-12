package com.tiago.ordemservico.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiago.ordemservico.domain.Cliente;
import com.tiago.ordemservico.domain.Pessoa;
import com.tiago.ordemservico.dtos.ClienteDTO;
import com.tiago.ordemservico.repository.ClienteRepository;
import com.tiago.ordemservico.repository.PessoaRepository;
import com.tiago.ordemservico.services.exceptions.DataIntegratyViolationException;
import com.tiago.ordemservico.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;

	public Cliente findById(Integer id) {
		Optional<Cliente> obj = clienteRepository.findById(id);
		return obj.orElseThrow( () -> new ObjectNotFoundException(
				"Objeto não encontrado ! Id: " + id 
				+ ", Tipo: " + Cliente.class.getName()) );
	}

	public List<Cliente> findAll() {
		return clienteRepository.findAll();		
	}
	
	public Cliente create(ClienteDTO objDTO) {
		if(findByCPF(objDTO) != null) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		Cliente newObj = new Cliente(null, objDTO.getNome(), objDTO.getCpf(), objDTO.getTelefone());
		return clienteRepository.save(newObj);
	}
	
	private Pessoa findByCPF(ClienteDTO objDTO) {
		Pessoa obj = pessoaRepository.findByCPF(objDTO.getCpf());
		if(obj != null) {
			return obj;
		}
		return null;
	}

	public Cliente update(Integer id, @Valid ClienteDTO objDto) {
		Cliente oldObj = findById(id);
		
		if(findByCPF(objDto) != null && findByCPF(objDto).getId() != id) {
			throw new DataIntegratyViolationException("CPF já cadastrado na base de dados!");
		}
		
		oldObj.setCpf(objDto.getCpf());
		oldObj.setNome(objDto.getNome());
		oldObj.setTelefone(objDto.getTelefone());
		
		return clienteRepository.save(oldObj);
	}

	public void deleteById(Integer id) {
		Cliente obj = findById(id);
		if(obj.getOrdensDeServico().size() > 0) {
			throw new DataIntegratyViolationException("Cliente possui ordens de serviço, não pode ser deletado!");
		}
		clienteRepository.deleteById(id);
	}

}
