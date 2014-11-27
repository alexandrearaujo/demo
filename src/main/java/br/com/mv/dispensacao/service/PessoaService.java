package br.com.mv.dispensacao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mv.dispensacao.repository.PessoaRepository;

@Service
public class PessoaService {
	
	@Autowired
	PessoaRepository pessoaRepository;

	
	public PessoaRepository getPessoaRepository() {
		return pessoaRepository;
	}
}
