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
import br.com.mv.regulacao.dispensacaomedicamento.dao.FormaFarmaceuticaDao;
import br.com.mv.regulacao.dispensacaomedicamento.model.FormaFarmaceutica;

@Named("formaFarmaceuticaManager")
public class FormaFarmaceuticaBusiness extends GenericManagerImpl<FormaFarmaceutica, FormaFarmaceuticaRepository> implements FormaFarmaceuticaManager
{

    @Inject
    @Override
    public void setDao(FormaFarmaceuticaRepository dao)
    {
        super.setDao(dao);
        
    }
    
    @Override
    public Collection<FormaFarmaceutica> listAtivas(FormaFarmaceutica formaFarmaceutica)
    {
        if(formaFarmaceutica == null){
            formaFarmaceutica = new FormaFarmaceutica();
            formaFarmaceutica.setAtivo(true);
        }
        return get(0, criarProjection(formaFarmaceutica));
    }
    

    @Override
    public FormaFarmaceutica salvar(FormaFarmaceutica formaFarmaceutica) throws DataIntegrityViolationException, NotUniqueIdException, InstantiationException, IllegalAccessException,
            ClassNotFoundException
    {
        return save(formaFarmaceutica, true);
    }

    @Override
    public void atualizar(FormaFarmaceutica formaFarmaceutica) throws DataIntegrityViolationException, NotUniqueIdException, InstantiationException, IllegalAccessException,
            ClassNotFoundException
    {
        update(formaFarmaceutica, true);
        
    }

    @Override
    public FormaFarmaceutica excluir(FormaFarmaceutica formaFarmaceutica) throws NotUniqueIdException, InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        delete(formaFarmaceutica,true);
        return formaFarmaceutica;
    }

    @Override
    public FormaFarmaceutica getFormaFarmaceutica(FormaFarmaceutica formaFarmaceutica)
    {
        return getUnique(criarProjection(formaFarmaceutica));
    }

    @Override
    public Long count(FormaFarmaceutica formaFarmaceutica, Date dataInicial, Date dataFinal)
    {
        return count(criarProjection(formaFarmaceutica));
    }

    @Override
    public Collection<FormaFarmaceutica> listPaginado(int initialPos, int maxResult, FormaFarmaceutica formaFarmaceutica)
    {
        return fetch(initialPos, maxResult, criarProjection(formaFarmaceutica));
    }
    
    private ProjectionFilter[] criarProjection(FormaFarmaceutica formaFarmaceutica)
    {
        ArrayList<ProjectionFilter> arrayList = new ArrayList<ProjectionFilter>();
        
        if(formaFarmaceutica.getId() != null)
        {
            arrayList.add(new ProjectionFilter("id", formaFarmaceutica.getId()));
        }else
        {
            arrayList.add(new ProjectionFilter("id"));
        }
        
        if(formaFarmaceutica.getDescricao() != null)
        {
            arrayList.add(new ProjectionFilter("descricao", formaFarmaceutica.getDescricao()));
        }else
        {
            arrayList.add(new ProjectionFilter("descricao"));
        }
        
        if(formaFarmaceutica.isAtivo() != null)
        {
            arrayList.add(new ProjectionFilter("ativo", formaFarmaceutica.isAtivo()));
        }else
        {
            arrayList.add(new ProjectionFilter("ativo"));
        }
        arrayList.add(new ProjectionFilter(Order.asc("descricao")));
        ProjectionFilter[] projectionFilters = new ProjectionFilter[arrayList.size()];
        return arrayList.toArray(projectionFilters);
    }
}
