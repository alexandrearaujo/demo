package br.com.mv.dispensacaomedicamento.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mv.dispensacaomedicamento.repository.PessoaRepository;

@Service
public class PessoaService {
	
	@Autowired
	PessoaRepository pessoaRepository;

	
	public PessoaRepository getPessoaRepository() {
		return pessoaRepository;
	}
}
