package br.com.mv.dispensacaomedicamento.repository;

import java.util.Collection;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.mv.dispensacaomedicamento.model.ItemSolicitacaoMedicamentoApac;
import br.com.mv.dispensacaomedicamento.model.ProcedimentoCidDTO;
import br.com.mv.dispensacaomedicamento.model.ProcedimentoVigente;
import br.com.mv.dispensacaomedicamento.model.SolicitacaoMedicamentoApac;
import br.com.mv.dispensacaomedicamento.model.SolicitacaoMedicamentoApacDTO;


@Repository
public interface SolicitacaoMedicamentoApacRepository extends PagingAndSortingRepository<SolicitacaoMedicamentoApac, Long>
{
    
    public Collection<ProcedimentoCidDTO> listarProcedimentoCidDTOPorDescricao(String descricao, ProcedimentoVigente procedimentoVigente, ProcedimentoCidDTO procedimentoCidDTO, boolean isCidPadronizado);
    public boolean existeSolicitacaoMedicamentAPACPacienteParaMesmoCidPrincipal(SolicitacaoMedicamentoApac solicitacaoMedicamentoApac);
    public boolean existeSolicitacaoMedicamentAPACPacienteParaMesmoMedicamento(SolicitacaoMedicamentoApac solicitacaoMedicamentoApac, ItemSolicitacaoMedicamentoApac itemSolicitacaoMedicamentoApac);
    public SolicitacaoMedicamentoApac buscarSolicitacaoMedicamentoApacPorId(SolicitacaoMedicamentoApac solicitacaoMedicamentoApac);
    public Collection<SolicitacaoMedicamentoApacDTO> buscarAvaliacaoApac(SolicitacaoMedicamentoApac solicitacaoMedicamentoApac);
    public Collection<SolicitacaoMedicamentoApacDTO> buscarAvaliacaoApac(int initialPos, int maxResults, SolicitacaoMedicamentoApac solicitacaoMedicamentoApac);
    public Long countAvaliacaoApac(SolicitacaoMedicamentoApac solicitacaoMedicamentoApac);
}