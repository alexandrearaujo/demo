package br.com.mv.dispensacao.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.mv.dispensacao.model.Pessoa;

public interface PessoaRepository extends CrudRepository<Pessoa, Long> {


}
