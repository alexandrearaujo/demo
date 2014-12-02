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
import br.com.mv.regulacao.dispensacaomedicamento.dao.ItemSolicitacaoMedicamentoDao;
import br.com.mv.regulacao.dispensacaomedicamento.model.ItemSolicitacaoMedicamento;
@Named("itemSolicitacaoMedicamentoManager")
public class ItemSolicitacaoMedicamentoBusiness extends GenericManagerImpl<ItemSolicitacaoMedicamento, ItemSolicitacaoMedicamentoRepository> implements ItemSolicitacaoMedicamentoManager
{

    
    @Inject
    @Override
    public void setDao(ItemSolicitacaoMedicamentoRepository dao)
    {
        super.setDao(dao);
    }
    
    @Override
    public ItemSolicitacaoMedicamento salvar(ItemSolicitacaoMedicamento itemSolicitacaoMedicamento) throws DataIntegrityViolationException, NotUniqueIdException, InstantiationException,
            IllegalAccessException, ClassNotFoundException
    {
        
        return save(itemSolicitacaoMedicamento,true);
    }

    @Override
    public void atualizar(ItemSolicitacaoMedicamento itemSolicitacaoMedicamento) throws DataIntegrityViolationException, NotUniqueIdException, InstantiationException, IllegalAccessException,
            ClassNotFoundException
    {
        update(itemSolicitacaoMedicamento,true);
        
    }

    @Override
    public void excluir(ItemSolicitacaoMedicamento itemSolicitacaoMedicamento) throws NotUniqueIdException, InstantiationException, IllegalAccessException,
            ClassNotFoundException
    {
        delete(itemSolicitacaoMedicamento,true); 
    }

    @Override
    public ItemSolicitacaoMedicamento getItemSolicitacaoMedicamento(ItemSolicitacaoMedicamento itemSolicitacaoMedicamento)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long count(ItemSolicitacaoMedicamento itemSolicitacaoMedicamento, Date dataInicial, Date dataFinal)
    {
        return count(criarProjection(itemSolicitacaoMedicamento,null));
    }

    @Override
    public Collection<ItemSolicitacaoMedicamento> listPaginado(int initialPos, int maxResult, ItemSolicitacaoMedicamento itemSolicitacaoMedicamento)
    {
        return fetch(initialPos, maxResult, criarProjection(itemSolicitacaoMedicamento,null));
    }
    
    private ProjectionFilter[] criarProjection(ItemSolicitacaoMedicamento itemSolicitacaoMedicamento, Collection<ProjectionFilter> parametrosAMais)
    {
        ArrayList<ProjectionFilter> arrayList = new ArrayList<ProjectionFilter>();
        
        if(itemSolicitacaoMedicamento.getId() != null)
        {
            arrayList.add(new ProjectionFilter("id", itemSolicitacaoMedicamento.getId()));
        }else
        {
            arrayList.add(new ProjectionFilter("id"));
        }
        if(itemSolicitacaoMedicamento.getDataCadastro() != null)
        {
            arrayList.add(new ProjectionFilter("solicitacaoMedicamento.id", itemSolicitacaoMedicamento.getSolicitacaoMedicamento()));
        }else
        {
            arrayList.add(new ProjectionFilter("solicitacaoMedicamento.id"));
        }
        
        arrayList.add(new ProjectionFilter("apac", itemSolicitacaoMedicamento.isApac()));
        
        if(itemSolicitacaoMedicamento.getDataCadastro() != null)
        {
            arrayList.add(new ProjectionFilter("dataCadastro", itemSolicitacaoMedicamento.getDataCadastro()));
        }else
        {
            arrayList.add(new ProjectionFilter("dataCadastro"));
        }
        if(itemSolicitacaoMedicamento.getQuantidadeSolicitadaTotal() != null)
        {
            arrayList.add(new ProjectionFilter("quantidadeSolicitadaTotal", itemSolicitacaoMedicamento.getQuantidadeSolicitadaTotal()));
        }else
        {
            arrayList.add(new ProjectionFilter("quantidadeSolicitadaTotal"));
        }
        if(itemSolicitacaoMedicamento.getQuantidadeSolicitadaTotal() != null)
        {
            arrayList.add(new ProjectionFilter("usuario.id", itemSolicitacaoMedicamento.getUsuario()));
        }else
        {
            arrayList.add(new ProjectionFilter("usuario.id"));
        }
        if(itemSolicitacaoMedicamento.getMedicamentoItemUnidade() != null)
        {
            arrayList.add(new ProjectionFilter("medicamentoItemUnidade.id", itemSolicitacaoMedicamento.getMedicamentoItemUnidade()));
        }else
        {
            arrayList.add(new ProjectionFilter("medicamentoItemUnidade.id"));
        }
        if(itemSolicitacaoMedicamento.getTipoFrequencia() != null)
        {
            arrayList.add(new ProjectionFilter("tipoFrequencia.id", itemSolicitacaoMedicamento.getTipoFrequencia()));
        }else
        {
            arrayList.add(new ProjectionFilter("tipoFrequencia.id"));
        }
       
        arrayList.add(new ProjectionFilter("tratamentoContinuo", itemSolicitacaoMedicamento.isTratamentoContinuo()));
       
        if(itemSolicitacaoMedicamento.getQuantidadePeriodoTratamento() != null)
        {
            arrayList.add(new ProjectionFilter("quantidadePeriodoTratamento", itemSolicitacaoMedicamento.getQuantidadePeriodoTratamento()));
        }else
        {
            arrayList.add(new ProjectionFilter("quantidadePeriodoTratamento"));
        }
        if(itemSolicitacaoMedicamento.getTipoPeriodoTratamento() != null)
        {
            arrayList.add(new ProjectionFilter("tipoPeriodoTratamento", itemSolicitacaoMedicamento.getTipoPeriodoTratamento()));
        }else
        {
            arrayList.add(new ProjectionFilter("tipoPeriodoTratamento"));
        }
        if(itemSolicitacaoMedicamento.getViaAdministracaoMedicamento() != null)
        {
            arrayList.add(new ProjectionFilter("viaAdministracaoMedicamento.id", itemSolicitacaoMedicamento.getViaAdministracaoMedicamento()));
        }else
        {
            arrayList.add(new ProjectionFilter("viaAdministracaoMedicamento.id"));
        }
        
         arrayList.add(new ProjectionFilter("cidNaoPadronizado", itemSolicitacaoMedicamento.isCidNaoPadronizado()));
        
        if(itemSolicitacaoMedicamento.getOrientacao() != null)
        {
            arrayList.add(new ProjectionFilter("orientacao", itemSolicitacaoMedicamento.getOrientacao()));
        }else
        {
            arrayList.add(new ProjectionFilter("orientacao"));
        }
        
        for (ProjectionFilter parametro : parametrosAMais)
        {
            arrayList.add(parametro);
        }
        
        ProjectionFilter[] projectionFilters = new ProjectionFilter[arrayList.size()];
        return arrayList.toArray(projectionFilters);
    }

  

}
