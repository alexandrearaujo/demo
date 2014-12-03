package br.com.mv.dispensacaomedicamento.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.mv.dispensacaomedicamento.model.HistoricoSolicitacaoMedicamento;
import br.com.mv.dispensacaomedicamento.model.SolicitacaoMedicamento;

public interface HistoricoSolicitacaoMedicamentoRepository extends CrudRepository<HistoricoSolicitacaoMedicamento, Long>
{
	@Query("select "+ 
			"h.id, h.situacaoSolicitacaoMedicamento.id, "+
		   "usuario.id, "+
		   "dataCadastro "+
		   "from HistoricoSolicitacaoMedicamento h" + 
		   "where " +
		   "h.solicitacaoMedicamento.id  = :solicitacaoMedicamento.id")
    public Collection<HistoricoSolicitacaoMedicamento> buscarHistorioSolicitacaoMedicamentoPorSolicitacaoMedicamento(@Param("solicitacaoMedicamento") SolicitacaoMedicamento solicitacaoMedicamento);
    

	
}
