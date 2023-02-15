package com.tiago.ordemservico.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tiago.ordemservico.domain.Cliente;
import com.tiago.ordemservico.domain.OrdemServico;
import com.tiago.ordemservico.domain.Tecnico;
import com.tiago.ordemservico.domain.enums.Prioridade;
import com.tiago.ordemservico.domain.enums.Status;
import com.tiago.ordemservico.dtos.OrdemServicoDTO;
import com.tiago.ordemservico.repository.OrdemServicoRepository;
import com.tiago.ordemservico.services.exceptions.ObjectNotFoundException;

@Service
public class OrdemServicoService {

	@Autowired
	private TecnicoService tecnicoService;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private OrdemServicoRepository ordemServicoRepository;

	public OrdemServico findById(Integer id) {
		Optional<OrdemServico> obj = ordemServicoRepository.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado ! id: " + id + ", Tipo: " + OrdemServico.class.getName()));
	}

	public List<OrdemServico> findAll() {
		return ordemServicoRepository.findAll();
	}

	public OrdemServico created(@Valid OrdemServicoDTO obj) {
		return fromDTO(obj);
	}

	public OrdemServico fromDTO(OrdemServicoDTO obj) {
		OrdemServico newObj = new OrdemServico();
		newObj.setId(obj.getId());
		newObj.setObservacoes(obj.getObservacoes());
		newObj.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
		newObj.setStatus(Status.toEnum(obj.getStatus()));

		Tecnico tec = tecnicoService.findById(obj.getTecnico());
		Cliente cli = clienteService.findById(obj.getCliente());

		newObj.setTecnico(tec);
		newObj.setCliente(cli);

		return ordemServicoRepository.save(newObj);
	}

}
