package br.com.mv.dispensacaomedicamento.business;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.criterion.Order;
import org.springframework.dao.DataIntegrityViolationException;

import br.com.mv.commons.web.business.GenericManagerImpl;
import br.com.mv.commons.web.exception.NotUniqueIdException;
import br.com.mv.commons.web.util.hibernate.transform.ProjectionFilter;
import br.com.mv.regulacao.dispensacaomedicamento.dao.UnidadeDao;
import br.com.mv.regulacao.dispensacaomedicamento.model.Unidade;
@Named("unidadeManager")
public class UnidadeBusiness extends GenericManagerImpl<Unidade, UnidadeRepository> implements UnidadeManager
{

    @Inject
    @Override
    public void setDao(UnidadeRepository dao){
        super.setDao(dao);
    }
    
    @Override
    public Collection<Unidade> listarAtivosOrdenadoPorDescricao(String descricao)
    {
         ArrayList<ProjectionFilter> arrayList = new ArrayList<ProjectionFilter>();
         arrayList.add(new ProjectionFilter("id"));
         arrayList.add(new ProjectionFilter("descricao", descricao.concat("%") ));
         arrayList.add(new ProjectionFilter("descricaoSigla"));
         arrayList.add(new ProjectionFilter("valorFator"));
         arrayList.add(new ProjectionFilter("dataCadastro"));
         arrayList.add(new ProjectionFilter("ativo", true));
         arrayList.add(new ProjectionFilter(Order.asc("descricao")));
         
         ProjectionFilter [] projectionFilters = new ProjectionFilter[arrayList.size()];
         arrayList.toArray(projectionFilters);
         
         return get(0, projectionFilters);
    }

    @Override
    public Unidade salvar(Unidade unidade) throws DataIntegrityViolationException, NotUniqueIdException, InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        return save(unidade, true);
    }

    @Override
    public void atualizar(Unidade unidade) throws DataIntegrityViolationException, NotUniqueIdException, InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        update(unidade, true);
    }

    @Override
    public Unidade excluir(Unidade unidade) throws NotUniqueIdException, InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        delete(unidade,true);
        return unidade;
    }

    @Override
    public Unidade getUnidade(Unidade unidade)
    {
        return getUnique(criarProjection(unidade));
    }

    @Override
    public Long count(Unidade unidade, Date dataInicial, Date dataFinal)
    {
        return count(criarProjection(unidade));
    }

    @Override
    public Collection<Unidade> listPaginado(int initialPos, int maxResult, Unidade unidade)
    {
        return fetch(initialPos, maxResult, criarProjection(unidade));
    }
    
    private ProjectionFilter[] criarProjection(Unidade unidade)
    {
        ArrayList<ProjectionFilter> arrayList = new ArrayList<ProjectionFilter>();
        
        if(unidade.getId() != null)
        {
            arrayList.add(new ProjectionFilter("id", unidade.getId()));
        }else
        {
            arrayList.add(new ProjectionFilter("id"));
        }
        
        if(unidade.getDataCadastro() != null)
        {
            arrayList.add(new ProjectionFilter("dataCadastro", unidade.getDataCadastro()));
        }else
        {
            arrayList.add(new ProjectionFilter("dataCadastro"));
        }
        if(unidade.getDescricaoSigla() != null)
        {
            arrayList.add(new ProjectionFilter("descricaoSigla", unidade.getDescricaoSigla()));
        }else
        {
            arrayList.add(new ProjectionFilter("descricaoSigla"));
        }
        if(unidade.getDescricao() != null)
        {
            arrayList.add(new ProjectionFilter("descricao", unidade.getDescricao()));
        }else
        {
            arrayList.add(new ProjectionFilter("descricao"));
        }
        if(unidade.getValorFator() != null)
        {
            arrayList.add(new ProjectionFilter("valorFator", unidade.getValorFator()));
        }else
        {
            arrayList.add(new ProjectionFilter("valorFator"));
        }
        arrayList.add(new ProjectionFilter("ativo", unidade.isAtivo()));
        arrayList.add(new ProjectionFilter(Order.desc("descricao")));
        
        ProjectionFilter[] projectionFilters = new ProjectionFilter[arrayList.size()];
        return arrayList.toArray(projectionFilters);
    }

}
