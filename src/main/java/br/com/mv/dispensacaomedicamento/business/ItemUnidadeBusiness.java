package br.com.mv.dispensacaomedicamento.business;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.criterion.Order;
import org.hibernate.sql.JoinFragment;
import org.springframework.dao.DataIntegrityViolationException;

import br.com.mv.commons.i18n.exception.LocalizedException;
import br.com.mv.commons.web.business.GenericManagerImpl;
import br.com.mv.commons.web.exception.NotUniqueIdException;
import br.com.mv.commons.web.util.hibernate.transform.ProjectionFilter;
import br.com.mv.regulacao.dispensacaomedicamento.dao.ItemUnidadeDao;
import br.com.mv.regulacao.dispensacaomedicamento.exception.ItemUnidadeException;
import br.com.mv.regulacao.dispensacaomedicamento.i18n.DispensacaoMedicamentoRBi;
import br.com.mv.regulacao.dispensacaomedicamento.model.ItemUnidade;
import br.com.mv.regulacao.dispensacaomedicamento.model.MedicamentoItemUnidade;
import br.com.mv.regulacao.dispensacaomedicamento.model.SituacaoSolicitacaoMedicamento;
import br.com.mv.regulacao.dispensacaomedicamento.model.SolicitacaoMedicamento;
import br.com.mv.regulacao.dispensacaomedicamento.model.Unidade;
@Named("itemUnidadeManager")
public class ItemUnidadeBusiness extends GenericManagerImpl<ItemUnidade, ItemUnidadeDao> implements ItemUnidadeManager
{

    @Inject
    SolicitacaoMedicamentoManager solicitacaoMedicamentoManager;
    @Inject
    @Override
    public void setDao(ItemUnidadeDao dao)
    {
        super.setDao(dao);
        
    }
    
    @Override
    public Collection<ItemUnidade> listarItemUnidadeAtivoFiltradoPorUnidade(String descricao,Unidade unidade)
    {
        ArrayList<ProjectionFilter> arrayList = new ArrayList<ProjectionFilter>();
        arrayList.add(new ProjectionFilter("id"));
        arrayList.add(new ProjectionFilter("descricao", descricao.concat("%") ));
        arrayList.add(new ProjectionFilter("unidade.id", unidade.getId()));
        arrayList.add(new ProjectionFilter("unidade.descricao"));
        arrayList.add(new ProjectionFilter("descricaoSigla"));
        arrayList.add(new ProjectionFilter("dataCadastro"));
        arrayList.add(new ProjectionFilter("valorFator"));
        arrayList.add(new ProjectionFilter("ativo", true));
        arrayList.add(new ProjectionFilter(Order.asc("descricao")));
        
        
        ProjectionFilter [] projectionFilters = new ProjectionFilter[arrayList.size()];
        arrayList.toArray(projectionFilters);
        
        return get(0, projectionFilters);
    }

    @Override
    public ItemUnidade salvar(ItemUnidade itemUnidade) throws DataIntegrityViolationException, NotUniqueIdException, InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        return save(itemUnidade, true);
    }

    @Override
    public void atualizar(ItemUnidade itemUnidade) throws DataIntegrityViolationException, NotUniqueIdException, InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        update(itemUnidade, true);
        
    }

    @Override
    public ItemUnidade excluir(ItemUnidade itemUnidade) throws ItemUnidadeException
    {   
        delete(itemUnidade,true);
        return itemUnidade;
    }

    @Override
    public ItemUnidade getItemUnidade(ItemUnidade itemUnidade)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Long count(ItemUnidade itemUnidade, Date dataInicial, Date dataFinal)
    {
        return count(criarProjection(itemUnidade,null));
    }

    @Override
    public Collection<ItemUnidade> listPaginado(int initialPos, int maxResult, ItemUnidade itemUnidade)
    {
        return fetch(initialPos, maxResult, criarProjection(itemUnidade,null));
    }
    
   
    
    private ProjectionFilter[] criarProjection(ItemUnidade itemUnidade, Collection<ProjectionFilter> parametrosAMais)
    {
        ArrayList<ProjectionFilter> arrayList = new ArrayList<ProjectionFilter>();
        
        
        if(itemUnidade.getId() != null)
        {
            arrayList.add(new ProjectionFilter("id", itemUnidade.getId()));
        }else
        {
            arrayList.add(new ProjectionFilter("id"));
        }
        
        if(itemUnidade.getDataCadastro() != null)
        {
            arrayList.add(new ProjectionFilter("dataCadastro", itemUnidade.getDataCadastro()));
        }else
        {
            arrayList.add(new ProjectionFilter("dataCadastro"));
        }
        
        if(itemUnidade.getDescricao() != null)
        {
            arrayList.add(new ProjectionFilter("descricao", itemUnidade.getDescricao()));
        }else
        {
            arrayList.add(new ProjectionFilter("descricao"));
        }
        
        if(itemUnidade.getDescricaoSigla() != null)
        {
            arrayList.add(new ProjectionFilter("descricaoSigla", itemUnidade.getDescricaoSigla()));
        }else
        {
            arrayList.add(new ProjectionFilter("descricaoSigla"));
        }
        
        if(itemUnidade.getUnidade() != null)
        {
            arrayList.add(new ProjectionFilter("unidade.id", itemUnidade.getUnidade().getId()));
        }else
        {
            arrayList.add(new ProjectionFilter("unidade.id"));
        }
        
        arrayList.add(new ProjectionFilter("ativo", itemUnidade.isAtivo()));
        arrayList.add(new ProjectionFilter("valorFator"));
        arrayList.add(new ProjectionFilter(Order.desc("descricao")));
        
        if(parametrosAMais != null){
            for (ProjectionFilter parametro : parametrosAMais)
            {
                arrayList.add(parametro);
            }            
        }
        
        
        ProjectionFilter[] projectionFilters = new ProjectionFilter[arrayList.size()];
        return arrayList.toArray(projectionFilters);
    }

    @Override
    public Collection<ItemUnidade> listarPorMedicamentoItemUnidade(MedicamentoItemUnidade medicamentoItemUnidade)
    {
        return getDao().listarPorMedicamentoItemUnidade(medicamentoItemUnidade);
    }

}
