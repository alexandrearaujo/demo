package br.com.mv.dispensacaomedicamento.business;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.dao.DataIntegrityViolationException;

import br.com.mv.commons.web.business.GenericManagerImpl;
import br.com.mv.commons.web.exception.NotUniqueIdException;
import br.com.mv.commons.web.util.hibernate.transform.ProjectionFilter;
import br.com.mv.regulacao.dispensacaomedicamento.dao.HistoricoSolicitacaoMedicamentoDao;
import br.com.mv.regulacao.dispensacaomedicamento.model.HistoricoSolicitacaoMedicamento;
import br.com.mv.regulacao.dispensacaomedicamento.model.SolicitacaoMedicamento;

@Named("historicoSolicitacaoMedicamentoManager")
public class HistoricoSolicitacaoMedicamentoBusiness extends GenericManagerImpl<HistoricoSolicitacaoMedicamento, HistoricoSolicitacaoMedicamentoDao> implements HistoricoSolicitacaoMedicamentoManager
{
    
    @Inject
    @Override
    public void setDao(HistoricoSolicitacaoMedicamentoDao dao)
    {
        super.setDao(dao);
    }

    @Override
    public HistoricoSolicitacaoMedicamento salvarHistoricoSolicitacaoMedicamento(SolicitacaoMedicamento solicitacaoMedicamento) throws DataIntegrityViolationException, NotUniqueIdException,
            InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        HistoricoSolicitacaoMedicamento historicoSolicitacaoMedicamento = new HistoricoSolicitacaoMedicamento();
        
        historicoSolicitacaoMedicamento.setSolicitacaoMedicamento(solicitacaoMedicamento);
        historicoSolicitacaoMedicamento.setSituacaoSolicitacaoMedicamento(solicitacaoMedicamento.getSituacaoSolicitacaoMedicamento());
        historicoSolicitacaoMedicamento.setUsuario(solicitacaoMedicamento.getUsuario());
        historicoSolicitacaoMedicamento.setDataCadastro(new Date());
        
        return salvar(historicoSolicitacaoMedicamento);
    }

    @Override
    public HistoricoSolicitacaoMedicamento salvar(HistoricoSolicitacaoMedicamento historicoSolicitacaoMedicamento) throws DataIntegrityViolationException, NotUniqueIdException,
            InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        return save(historicoSolicitacaoMedicamento, true);
    }

    @Override
    public void atualizar() throws DataIntegrityViolationException, NotUniqueIdException, InstantiationException,
            IllegalAccessException, ClassNotFoundException
    {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void excluir(HistoricoSolicitacaoMedicamento historicoSolicitacaoMedicamento) throws DataIntegrityViolationException, NotUniqueIdException, InstantiationException, IllegalAccessException,
            ClassNotFoundException
    {
        //TODO removendo o objeto transiente. Não era para precisar removar, verificar com a arquitetura o por que do erro.
        historicoSolicitacaoMedicamento.setSolicitacaoMedicamento(null);
        delete(historicoSolicitacaoMedicamento, true);
    }

    @Override
    public Collection<HistoricoSolicitacaoMedicamento> buscarHistorioSolicitacaoMedicamentoPorSolicitacaoMedicamento(SolicitacaoMedicamento solicitacaoMedicamento) 
    {
        ArrayList<ProjectionFilter> listaProjection = new ArrayList<ProjectionFilter>();
        
        listaProjection.add(new ProjectionFilter("id"));
        listaProjection.add(new ProjectionFilter("situacaoSolicitacaoMedicamento.id"));
        listaProjection.add(new ProjectionFilter("solicitacaoMedicamento.id", solicitacaoMedicamento.getId()));
        listaProjection.add(new ProjectionFilter("usuario.id"));
        listaProjection.add(new ProjectionFilter("dataCadastro"));
        
        ProjectionFilter[] projectionFilters = new ProjectionFilter[listaProjection.size()];

        listaProjection.toArray(projectionFilters);
        
        
        return get(0, projectionFilters);
    }
    
    
    
}
