package br.com.mv.dispensacaomedicamento.business;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.mv.dispensacaomedicamento.exception.DispensacaoMedicamentoException;
import br.com.mv.dispensacaomedicamento.model.Estabelecimento;
import br.com.mv.dispensacaomedicamento.model.HistoricoSolicitacaoMedicamento;
import br.com.mv.dispensacaomedicamento.model.SituacaoSolicitacaoMedicamento;
import br.com.mv.dispensacaomedicamento.model.SolicitacaoMedicamento;
import br.com.mv.dispensacaomedicamento.model.SolicitacaoMedicamentoDTO;
import br.com.mv.dispensacaomedicamento.model.VinculoProfissionalDTO;
import br.com.mv.dispensacaomedicamento.repository.SolicitacaoMedicamentoRepository;

@Service
@Transactional
public class SolicitacaoMedicamentoBusiness {
    

    @Autowired
    private HistoricoSolicitacaoMedicamentoBussines historicoSolicitacaoMedicamentoBusiness;
    
    @Inject
    @Override
    public void setDao(SolicitacaoMedicamentoRepository dao)
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
