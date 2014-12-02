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
import br.com.mv.flex.util.SessionUtil;
import br.com.mv.regulacao.dispensacaomedicamento.dao.MarcaMedicamentoDao;
import br.com.mv.regulacao.dispensacaomedicamento.exception.DispensacaoMedicamentoException;
import br.com.mv.regulacao.dispensacaomedicamento.exception.MarcaMedicamentoException;
import br.com.mv.regulacao.dispensacaomedicamento.model.MarcaMedicamento;
import br.com.mv.sigas.model.geral.User;


/**
 * @author Francisco Vernek
 *
 */

@Named("marcaMedicamentoManager")
public class MarcaMedicamentoBusiness extends GenericManagerImpl<MarcaMedicamento, MarcaMedicamentoRepository> implements MarcaMedicamentoManager
{
    @Inject
    @Override
    public void setDao(MarcaMedicamentoRepository dao)
    {
        super.setDao(dao);
    }
    
    @Override
    public void salvar(MarcaMedicamento marcaMedicamento) throws DataIntegrityViolationException, NotUniqueIdException, InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        marcaMedicamento.setDataCadastro(new Date());
       
        save(marcaMedicamento);
    }

    @Override
    public Collection<MarcaMedicamento> list(int initialPos, int maxResults, MarcaMedicamento marcaMedicamento)
    {   
        ProjectionFilter [] projectionFilters =
        {
                new ProjectionFilter("id"),
                new ProjectionFilter("descricao", marcaMedicamento.getDescricao()),
                new ProjectionFilter("ativo", marcaMedicamento.getAtivo()),
                new ProjectionFilter("usuario"),
                new ProjectionFilter("dataCadastro"),
        };
        return get(projectionFilters);
    }
       
    @Override
    public Long count(MarcaMedicamento marcaMedicamento) 
    {
        return count(criarProjecao(marcaMedicamento));
    }

    @Override
    public void atualizar(MarcaMedicamento marcaMedicamento) throws DataIntegrityViolationException, NotUniqueIdException, InstantiationException, IllegalAccessException, ClassNotFoundException
    {
       update(marcaMedicamento);
    }

    @Override
    public void excluir(MarcaMedicamento marcaMedicamento) throws DataIntegrityViolationException, NotUniqueIdException, InstantiationException, IllegalAccessException, ClassNotFoundException {

        try
        {
            delete(marcaMedicamento, true);
        }
        catch (DataIntegrityViolationException e)
        {
            throw new MarcaMedicamentoException(MarcaMedicamentoException.MARCA_MEDICAMENTO_JA_CADASTRADA);
        }
    }
    
    private ProjectionFilter[] criarProjecao(MarcaMedicamento marcaMedicamento)
    {
        ArrayList<ProjectionFilter> arrayList = new ArrayList<ProjectionFilter>();
        
        arrayList.add(new ProjectionFilter("id"));        
        arrayList.add(new ProjectionFilter("descricao"));
        
        ProjectionFilter[] projectionFilters = new ProjectionFilter[arrayList.size()];        
        arrayList.toArray(projectionFilters);
        
        return projectionFilters;     
    }
    
    @Override
    public boolean existeMarcaMedicamentoCadastrada(MarcaMedicamento marcaMedicamento) throws DataIntegrityViolationException, DispensacaoMedicamentoException, InstantiationException, IllegalAccessException, ClassNotFoundException, NotUniqueIdException {
    
        return getDao().existeMarcaMedicamentoCadastrada(marcaMedicamento);
       
    }

}
