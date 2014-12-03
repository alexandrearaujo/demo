package br.com.mv.dispensacaomedicamento.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.mv.dispensacaomedicamento.model.Medicamento;

@Repository
public interface MedicamentoRepository extends CrudRepository<Medicamento, Long>{

	@Query("select m from Medicamento m")
	public Collection<Medicamento> listarMedicamento();
	
}
