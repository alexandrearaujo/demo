package br.com.mv.dispensacaomedicamento.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.mv.dispensacaomedicamento.model.Pessoa;

public interface PessoaRepository extends CrudRepository<Pessoa, Long> {


}