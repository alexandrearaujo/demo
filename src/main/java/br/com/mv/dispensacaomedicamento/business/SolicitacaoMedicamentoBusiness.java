package br.com.mv.dispensacaomedicamento.business;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.dao.DataIntegrityViolationException;

import br.com.mv.commons.web.business.GenericManagerImpl;
import br.com.mv.commons.web.exception.NotUniqueIdException;
import br.com.mv.commons.web.util.hibernate.transform.ProjectionFilter;
import br.com.mv.geral.model.VinculoProfissionalDTO;
import br.com.mv.regulacao.dispensacaomedicamento.dao.SolicitacaoMedicamentoDao;
import br.com.mv.regulacao.dispensacaomedicamento.exception.DispensacaoMedicamentoException;
import br.com.mv.regulacao.dispensacaomedicamento.model.HistoricoSolicitacaoMedicamento;
import br.com.mv.regulacao.dispensacaomedicamento.model.SituacaoSolicitacaoMedicamento;
import br.com.mv.regulacao.dispensacaomedicamento.model.SolicitacaoMedicamento;
import br.com.mv.regulacao.dispensacaomedicamento.model.SolicitacaoMedicamentoDTO;
import br.com.mv.regulacao.model.geral.Estabelecimento;
import br.com.mv.regulador.model.BuscaVagaMunicipio;

@Named("solicitacaoMedicamentoManager")
public class SolicitacaoMedicamentoBusiness extends GenericManagerImpl<SolicitacaoMedicamento, SolicitacaoMedicamentoDao> implements SolicitacaoMedicamentoManager
{
    

    @Inject
    private HistoricoSolicitacaoMedicamentoManager historicoSolicitacaoMedicamentoManager;
    
    @Inject
    @Override
    public void setDao(SolicitacaoMedicamentoDao dao)
    {
        super.setDao(dao);
    }

    @Override
    public Long count(SolicitacaoMedicamentoDTO solicitacaoMedicamentoDTO)
    {
        return getDao().count(solicitacaoMedicamentoDTO);
    }

    @Override
    public Collection<SolicitacaoMedicamentoDTO> list(int initialPos, int maxResults, SolicitacaoMedicamentoDTO solicitacaoMedicamentoDTO)
    {
        return getDao().list(initialPos, maxResults, solicitacaoMedicamentoDTO);
    }

    @Override
    public Collection<VinculoProfissionalDTO> listarVinculoProfissionalDTO(String descricao, Estabelecimento estabelecimento)
    {
        return getDao().listarVinculoProfissionalDTO(descricao, estabelecimento);
    }
    
    @Override
    public void salvarHitoricoSolicitacaoMedicamento(SolicitacaoMedicamento solicitacaoMedicamento) throws DataIntegrityViolationException, InstantiationException, IllegalAccessException, ClassNotFoundException, NotUniqueIdException
    {
        historicoSolicitacaoMedicamentoManager.salvarHistoricoSolicitacaoMedicamento(solicitacaoMedicamento);
    }

    @Override
    public void excluirHitoricoSolicitacaoMedicamento(SolicitacaoMedicamento solicitacaoMedicamento) throws DataIntegrityViolationException, InstantiationException, IllegalAccessException, ClassNotFoundException, NotUniqueIdException
    {
       
        for (HistoricoSolicitacaoMedicamento historicoSolicitacaoMedicamentoBD: historicoSolicitacaoMedicamentoManager.buscarHistorioSolicitacaoMedicamentoPorSolicitacaoMedicamento(solicitacaoMedicamento))
        {
            historicoSolicitacaoMedicamentoManager.excluir(historicoSolicitacaoMedicamentoBD);
        }
    }
    
    @Override
    public Boolean existeSolicitacaoMedicamentoComMesmoReceituario(SolicitacaoMedicamento solicitacaoMedicamento) throws DispensacaoMedicamentoException
    {
        return getDao().existeSolicitacaoMedicamentoComMesmoReceituario(solicitacaoMedicamento);
    }

    @Override
    public SolicitacaoMedicamento salvar(SolicitacaoMedicamento solicitacaoMedicamento) throws DispensacaoMedicamentoException, DataIntegrityViolationException, NotUniqueIdException,
    InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        return save(solicitacaoMedicamento, true);
    }

    @Override
    public SolicitacaoMedicamento atualizarSolicitacaoMedicamento(SolicitacaoMedicamento solicitacaoMedicamento) throws DispensacaoMedicamentoException, DataIntegrityViolationException,
            NotUniqueIdException, InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public void atualizar(SolicitacaoMedicamento solicitacaoMedicamento) throws DispensacaoMedicamentoException, DataIntegrityViolationException, NotUniqueIdException, InstantiationException,
            IllegalAccessException, ClassNotFoundException
    {
        update(solicitacaoMedicamento, true);
        
    }

    @Override
    public void excluir(SolicitacaoMedicamento solicitacaoMedicamento) throws DispensacaoMedicamentoException, DataIntegrityViolationException, NotUniqueIdException, InstantiationException,
            IllegalAccessException, ClassNotFoundException
    {
        delete(solicitacaoMedicamento, true);
        
    }

    @Override
    public SolicitacaoMedicamento getSolicitacaoMedicamentoPorSituacao(SituacaoSolicitacaoMedicamento situacao)
    {
        ArrayList<ProjectionFilter> arrayList = new ArrayList<ProjectionFilter>();
        arrayList.add(new ProjectionFilter("id"));
        arrayList.add(new ProjectionFilter("situacaoSolicitacaoMedicamento.id" ));
        arrayList.add(new ProjectionFilter("situacaoSolicitacaoMedicamento.descricaoSituacaoMedicamento",situacao.getDescricaoSituacaoMedicamento() ));
        ProjectionFilter [] projectionFilters = new ProjectionFilter[arrayList.size()];
        arrayList.toArray(projectionFilters);
        
        return getUnique(projectionFilters);
    }
    
}
