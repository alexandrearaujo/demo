package br.com.mv.dispensacaomedicamento.business;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.criterion.Order;

import br.com.mv.commons.web.business.GenericManagerImpl;
import br.com.mv.commons.web.exception.NotUniqueIdException;
import br.com.mv.commons.web.util.hibernate.transform.ProjectionFilter;
import br.com.mv.regulacao.dispensacaomedicamento.dao.TipoReceituarioDao;
import br.com.mv.regulacao.dispensacaomedicamento.model.TipoReceituario;

@Named("tipoReceituarioManager")
public class TipoReceituarioBusiness extends GenericManagerImpl<TipoReceituario, TipoReceituarioDao> implements TipoReceituarioManager
{

    @Inject
    @Override
    public void setDao(TipoReceituarioDao dao)
    {
        super.setDao(dao);
    }

    @Override
    public Long count(TipoReceituario tipoReceituario)
    {
        return count(criarProjection(tipoReceituario));
    }
    
    @Override
    public Collection<TipoReceituario> listarPaginado(int initialPos, int maxResults, TipoReceituario tipoReceituario)
    {
        return fetch(initialPos, maxResults, criarProjection(tipoReceituario));
    }
    
    /*Metodo Responsável por criar os parametros de filtros para projeção
     * 
     * Ryan Fernandes
     */

    private ProjectionFilter[] criarProjection(TipoReceituario tipoReceituario)
    {

        ArrayList<ProjectionFilter> listaProjection = new ArrayList<ProjectionFilter>();
        
        if(tipoReceituario.getId() != null)
            listaProjection.add(new ProjectionFilter("id", tipoReceituario.getId()));
        
        listaProjection.add(new ProjectionFilter("id"));
        if(tipoReceituario.getDescricao() != null && !tipoReceituario.getDescricao().isEmpty())
            listaProjection.add(new ProjectionFilter("descricao", tipoReceituario.getDescricao()));
        else
            listaProjection.add(new ProjectionFilter("descricao"));
        
        if(tipoReceituario.getNumeroDiasValidadeReceita() != null && tipoReceituario.getNumeroDiasValidadeReceita() != 0L)
            listaProjection.add(new ProjectionFilter("numeroDiasValidadeReceita", tipoReceituario.getNumeroDiasValidadeReceita()));
        else
            listaProjection.add(new ProjectionFilter("numeroDiasValidadeReceita"));
        
        if(tipoReceituario.getQuantidadeViaReceita() != null)
            listaProjection.add(new ProjectionFilter("quantidadeViaReceita", tipoReceituario.getQuantidadeViaReceita()));
        else
            listaProjection.add(new ProjectionFilter("quantidadeViaReceita"));

        if(tipoReceituario.getCor() != null && tipoReceituario.getCor() != -1)
            listaProjection.add(new ProjectionFilter("cor", tipoReceituario.getCor()));
        else
            listaProjection.add(new ProjectionFilter("cor"));
        
        listaProjection.add(new ProjectionFilter(Order.asc("descricao")));
        
        ProjectionFilter[] projectionFilters = new ProjectionFilter[listaProjection.size()];

        listaProjection.toArray(projectionFilters);
        
        return projectionFilters;
    }


    @Override
    public TipoReceituario salvar(TipoReceituario tipoReceituario) throws NotUniqueIdException, InstantiationException, IllegalAccessException,
            ClassNotFoundException
    {
        return save(tipoReceituario, true);
    }

    @Override
    public void atualizar(TipoReceituario tipoReceituario) throws NotUniqueIdException, InstantiationException, IllegalAccessException,
            ClassNotFoundException
    {
        update(tipoReceituario, true);
    }

    @Override
    public void excluir(TipoReceituario tipoReceituario) throws NotUniqueIdException, InstantiationException, IllegalAccessException,
            ClassNotFoundException
    {
        delete(tipoReceituario, true);
    }

    @Override
    public Collection<TipoReceituario> list(TipoReceituario tipoReceituario)
    {
        if(tipoReceituario == null){
            tipoReceituario = new TipoReceituario();
        }
        return get(0, criarProjection(tipoReceituario));
    }
    
    @Override
    public TipoReceituario getTipoReceituario(TipoReceituario tipoReceituario)
    {
        return getUnique(criarProjection(tipoReceituario));
    }

    
}
