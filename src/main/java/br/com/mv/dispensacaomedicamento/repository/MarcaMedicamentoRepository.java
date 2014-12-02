package br.com.mv.dispensacaomedicamento.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.mv.dispensacaomedicamento.model.MarcaMedicamento;


@Repository
public interface MarcaMedicamentoRepository extends CrudRepository<MarcaMedicamento, Long>
{

	@Query("select count(mm.cd_marca_medicamento) from marca_medicamento mm where mm.ds_marca_medicamento = :descricao ")
	public Long countMarcaMedicamentoPorDescricao(@Param("descricao") String descricao);
	
	default public boolean existeMarcaMedicamentoCadastrada(String descricao)
    {
		Long quantidade = countMarcaMedicamentoPorDescricao(descricao);

        if(quantidade.intValue() == 0 )
            return false;
        else
            return true;
    }

}
