/**
 * 
 */
package br.com.mv.dispensacaomedicamento.business;

import java.util.ArrayList;

import javax.inject.Inject;
import javax.inject.Named;

import br.com.mv.commons.web.business.GenericManagerImpl;
import br.com.mv.commons.web.exception.NotUniqueIdException;
import br.com.mv.commons.web.util.hibernate.transform.ProjectionFilter;
import br.com.mv.regulacao.dispensacaomedicamento.dao.EnderecoEntregaPacienteDao;
import br.com.mv.regulacao.dispensacaomedicamento.model.EnderecoEntregaPaciente;
import br.com.mv.regulacao.dispensacaomedicamento.model.EnderecoEntregaPacienteDTO;
import br.com.mv.regulacao.model.prontuario.Cidadao;

/**
 * @author joao.franco
 *
 */
@Named ("enderecoEntregaManager")

public class EnderecoEntregaPacienteBusiness extends GenericManagerImpl<EnderecoEntregaPaciente, EnderecoEntregaPacienteRepository> implements EnderecoEntregaPacienteManager
{

    
    @Inject
    @Override
    public void setDao(EnderecoEntregaPacienteRepository dao)
    {
        super.setDao(dao);
    }
    
    @Override
    public Long countEndereceEntregaPaciente(EnderecoEntregaPaciente enderecoEntregaPaciente)
    {
        return count(criarProjectionEnderecoEntregaPaciente(enderecoEntregaPaciente));
    }

    private ProjectionFilter criarProjectionEnderecoEntregaPaciente(EnderecoEntregaPaciente enderecoEntregaPaciente)
    {
        ArrayList<ProjectionFilter> listaProjection = new ArrayList<ProjectionFilter>();
        return null;
    }

    @Override
    public EnderecoEntregaPacienteDTO preencherEnderecoByCidadao (Cidadao cidadao )
    {
      
           return getDao().preencherEnderecoByCidadao(cidadao);
           
    }
    
    
    @Override
    public EnderecoEntregaPacienteDTO buscarEnderecoEntrega (Cidadao cidadao)
    {
        return getDao().buscarEnderecoEntrega(cidadao);
    }
    

    @Override
    public void atualizar(EnderecoEntregaPaciente enderecoEntregaPaciente)
    {
        
    }

    @Override
    public void excluir(EnderecoEntregaPaciente enderecoEntregaPaciente)
    {
        
    }
    @Override
    public void salvar(EnderecoEntregaPaciente enderecoEntregaPaciente) throws NotUniqueIdException, InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        save(enderecoEntregaPaciente,true);
    }
    @Override
    public void salvarListaEnderecoEntregaPaciente(EnderecoEntregaPaciente enderecoEntregaPaciente) throws NotUniqueIdException, InstantiationException, IllegalAccessException, ClassNotFoundException
    {
            salvar(enderecoEntregaPaciente);
        
    }
    

}
