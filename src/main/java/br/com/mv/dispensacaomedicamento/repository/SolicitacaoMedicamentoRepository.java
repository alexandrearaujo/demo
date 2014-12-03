package br.com.mv.dispensacaomedicamento.repository;

import java.util.Collection;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.mv.dispensacaomedicamento.exception.DispensacaoMedicamentoException;
import br.com.mv.dispensacaomedicamento.model.Estabelecimento;
import br.com.mv.dispensacaomedicamento.model.SolicitacaoMedicamento;
import br.com.mv.dispensacaomedicamento.model.VinculoProfissionalDTO;

public interface SolicitacaoMedicamentoRepository extends PagingAndSortingRepository<SolicitacaoMedicamento, Long>
{

    public Long count(SolicitacaoMedicamento solicitacaoMedicamento, Pageable page);
	public Collection<VinculoProfissionalDTO> listarVinculoProfissionalDTO(String descricao, Estabelecimento estabelecimento);
    public Boolean existeSolicitacaoMedicamentoComMesmoReceituario(SolicitacaoMedicamento solicitacaoMedicamento) throws DispensacaoMedicamentoException;
    
}
