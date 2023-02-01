package com.tiago.ordemservico.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.tiago.ordemservico.domain.Cliente;
import com.tiago.ordemservico.domain.OrdemServico;
import com.tiago.ordemservico.domain.Tecnico;
import com.tiago.ordemservico.domain.enums.Prioridade;
import com.tiago.ordemservico.domain.enums.Status;
import com.tiago.ordemservico.repository.ClienteRepository;
import com.tiago.ordemservico.repository.OrdemServicoRepository;
import com.tiago.ordemservico.repository.TecnicoRepository;

@Service
public class DBService {

	@Autowired
	private TecnicoRepository tecnicoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private OrdemServicoRepository ordemServicoRepository;

	public void instanciaDB() {

		Tecnico t1 = new Tecnico(null, "Barney Brown", "65483653050", "(51) 9986-4444");
		Cliente c1 = new Cliente(null, "Betina Campos", "78923408077", "(49) 9879-5555");
		OrdemServico os1 = new OrdemServico(null, Prioridade.BAIXA, "teste criar ordem", Status.ANDAMENTO, t1, c1);

		t1.getOrdensDeServico().add(os1);
		c1.getOrdensDeServico().add(os1);

		tecnicoRepository.saveAll(Arrays.asList(t1));
		clienteRepository.saveAll(Arrays.asList(c1));
		ordemServicoRepository.saveAll(Arrays.asList(os1));

	}

}
