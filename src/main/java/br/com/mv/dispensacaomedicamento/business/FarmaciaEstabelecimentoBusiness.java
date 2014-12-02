package br.com.mv.dispensacaomedicamento.business;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;

import br.com.mv.commons.web.business.GenericManagerImpl;
import br.com.mv.commons.web.exception.NotUniqueIdException;
import br.com.mv.commons.web.util.hibernate.transform.ProjectionFilter;
import br.com.mv.regulacao.dispensacaomedicamento.dao.FarmaciaEstabelecimentoDao;
import br.com.mv.regulacao.dispensacaomedicamento.exception.DispensacaoMedicamentoException;
import br.com.mv.regulacao.dispensacaomedicamento.model.Farmacia;
import br.com.mv.regulacao.dispensacaomedicamento.model.FarmaciaEstabelecimento;
import br.com.mv.regulacao.dispensacaomedicamento.model.FarmaciaEstabelecimentoDTO;
import br.com.mv.regulacao.model.geral.Estabelecimento;

@Named("FarmaciaEstabelecimentoManager")
public class FarmaciaEstabelecimentoBusiness extends GenericManagerImpl<FarmaciaEstabelecimento, FarmaciaEstabelecimentoDao> implements FarmaciaEstabelecimentoManager
{
    
    @Inject
    FarmaciaManager farmaciaManager;
  
    FarmaciaEstabelecimentoManager farmaciaEstabelecimentoManager;
    
    
    FarmaciaEstabelecimento farmaciaEstabelecimento = new FarmaciaEstabelecimento();

    @Inject
    @Override
    public void setDao(FarmaciaEstabelecimentoDao dao)
    {
        super.setDao(dao);
    }
    

    @Override
    public Collection<FarmaciaEstabelecimentoDTO> listarFarmaciaEstabelecimento(String descricao, Estabelecimento estabelecimento)
    {
        return getDao().listarFarmaciaEstabelecimento(descricao, estabelecimento);
    }

    @Override
    public Long countFarmacia(FarmaciaEstabelecimento farmaciaEstabelecimento)
    {
        return null;
    }

    @Override
    public Collection<FarmaciaEstabelecimentoDTO> list(int initialPos, int maxResults, FarmaciaEstabelecimento farmaciaEstabelecimento)
    {
        
        return getDao().list(farmaciaEstabelecimento);
    }
    
    @Override
    public FarmaciaEstabelecimento buscarFarmaciaEstabelecimento (FarmaciaEstabelecimentoDTO farmaciaEstabelecimentoDTO)
    {

        ArrayList<ProjectionFilter> arrayList = new ArrayList<ProjectionFilter>();
        
        arrayList.add(new ProjectionFilter("id"));
        arrayList.add(new ProjectionFilter("estabelecimento.id" ));
        arrayList.add(new ProjectionFilter("estabelecimento.nomeFantasia"));
        arrayList.add(new ProjectionFilter("farmacia.id",farmaciaEstabelecimentoDTO.getIdFarmacia()));
        arrayList.add(new ProjectionFilter("farmacia.descricaoFarmacia"));
        arrayList.add(new ProjectionFilter("farmacia.numeroCRF"));
        arrayList.add(new ProjectionFilter("farmacia.numeracaoReceitaAuto"));
        arrayList.add(new ProjectionFilter("farmacia.dispensacaoCidIncompativel"));
        arrayList.add(new ProjectionFilter("farmacia.alertaPacienteInternado"));
        arrayList.add(new ProjectionFilter("farmacia.faturaAPAC"));
        arrayList.add(new ProjectionFilter("farmacia.pacienteBloqueadoSES"));
        arrayList.add(new ProjectionFilter("farmacia.usuario"));
        arrayList.add(new ProjectionFilter("farmacia.dataCadastro"));
        arrayList.add(new ProjectionFilter("farmacia.estabelecimentoSetorEstabelecimento.id"));
        arrayList.add(new ProjectionFilter("farmacia.estabelecimentoSetorEstabelecimento.estabelecimento.id"));
        arrayList.add(new ProjectionFilter("farmacia.estabelecimentoSetorEstabelecimento.estabelecimento.nomeFantasia"));
        arrayList.add(new ProjectionFilter("farmacia.estabelecimentoSetorEstabelecimento.setorEstabelecimento.id"));
        arrayList.add(new ProjectionFilter("farmacia.estabelecimentoSetorEstabelecimento.setorEstabelecimento.descricaoSetor"));
        arrayList.add(new ProjectionFilter("farmacia.farmaceuticoResponsavel.id"));
        arrayList.add(new ProjectionFilter("farmacia.farmaceuticoResponsavel.profissionalSigas.cidadao.id"));
        arrayList.add(new ProjectionFilter("farmacia.farmaceuticoResponsavel.profissionalSigas.cidadao.nome"));
        
        
        ProjectionFilter [] projectionFilters = new ProjectionFilter[arrayList.size()];
        arrayList.toArray(projectionFilters);
        
        FarmaciaEstabelecimento farmaciaEstabelecimento = getUnique(projectionFilters);
        
        return farmaciaEstabelecimento;
    }
    
    public Collection<FarmaciaEstabelecimento> buscarFarmaciaEstabelecimento (FarmaciaEstabelecimento farmaciaEstabelecimento)
    {

        ArrayList<ProjectionFilter> arrayList = new ArrayList<ProjectionFilter>();
        
        arrayList.add(new ProjectionFilter("id"));
        arrayList.add(new ProjectionFilter("estabelecimento.id" ));
        arrayList.add(new ProjectionFilter("estabelecimento.nomeFantasia"));
        arrayList.add(new ProjectionFilter("farmacia.id",farmaciaEstabelecimento.getFarmacia().getId()));
        arrayList.add(new ProjectionFilter("farmacia.id"));
        arrayList.add(new ProjectionFilter("farmacia.descricaoFarmacia"));
        arrayList.add(new ProjectionFilter("farmacia.numeroCRF"));
        arrayList.add(new ProjectionFilter("farmacia.numeracaoReceitaAuto"));
        arrayList.add(new ProjectionFilter("farmacia.dispensacaoCidIncompativel"));
        arrayList.add(new ProjectionFilter("farmacia.alertaPacienteInternado"));
        arrayList.add(new ProjectionFilter("farmacia.faturaAPAC"));
        arrayList.add(new ProjectionFilter("farmacia.pacienteBloqueadoSES"));
        arrayList.add(new ProjectionFilter("farmacia.dataCadastro"));
        arrayList.add(new ProjectionFilter("farmacia.estabelecimentoSetorEstabelecimento.id"));
        arrayList.add(new ProjectionFilter("farmacia.estabelecimentoSetorEstabelecimento.estabelecimento.id"));
        arrayList.add(new ProjectionFilter("farmacia.estabelecimentoSetorEstabelecimento.estabelecimento.nomeFantasia"));
        arrayList.add(new ProjectionFilter("farmacia.estabelecimentoSetorEstabelecimento.setorEstabelecimento.id"));
        arrayList.add(new ProjectionFilter("farmacia.estabelecimentoSetorEstabelecimento.setorEstabelecimento.descricaoSetor"));
        arrayList.add(new ProjectionFilter("farmacia.farmaceuticoResponsavel.id"));
        arrayList.add(new ProjectionFilter("farmacia.farmaceuticoResponsavel.profissionalSigas.cidadao.id"));
        arrayList.add(new ProjectionFilter("farmacia.farmaceuticoResponsavel.profissionalSigas.cidadao.nome"));
        
        ProjectionFilter [] projectionFilters = new ProjectionFilter[arrayList.size()];
        arrayList.toArray(projectionFilters);
        
        return get(projectionFilters);
    }


    @Override
    public void salvar(FarmaciaEstabelecimento farmaciaEstabelecimento) throws InstantiationException, IllegalAccessException, ClassNotFoundException, NotUniqueIdException, IllegalArgumentException, InvocationTargetException, IntrospectionException
    {
        farmaciaEstabelecimento.setDataCadastro(new Date());
        limparTransiente(farmaciaEstabelecimento);
        save(farmaciaEstabelecimento,true);
        
    }
    
    private void limparTransiente(FarmaciaEstabelecimento farmaciaEstabelecimento){
        if(farmaciaEstabelecimento.getEstabelecimento() != null  && farmaciaEstabelecimento.getEstabelecimento().getId() == null)
            farmaciaEstabelecimento.setEstabelecimento(null);

        if(farmaciaEstabelecimento.getFarmacia() != null  && farmaciaEstabelecimento.getFarmacia().getId() == null)
            farmaciaEstabelecimento.setFarmacia(null);
    }
    
    @Override
    public void excluirFarmacia(FarmaciaEstabelecimentoDTO farmaciaEstabelecimentoDTO) throws NotUniqueIdException, InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        try
        {
            Collection<FarmaciaEstabelecimento>listFarmaciaEstabelecimentos = buscarFarmacia(farmaciaEstabelecimentoDTO);
            for(FarmaciaEstabelecimento farmaciaEstabelecimento : listFarmaciaEstabelecimentos)
            {
               excluir(farmaciaEstabelecimento);
            }
        }
        catch (DataIntegrityViolationException e)
        {
            String constraint = ((ConstraintViolationException) e.getCause()).getConstraintName();
            if(constraint.equals(""))
                throw new DispensacaoMedicamentoException(DispensacaoMedicamentoException.FARMACIA_JA_CADASTRADA); 
        }
        }

    @Override
    public void excluir(FarmaciaEstabelecimento farmaciaEstabelecimento) throws NotUniqueIdException, InstantiationException, IllegalAccessException,
    ClassNotFoundException
    {
        delete(farmaciaEstabelecimento, true);
        
    }

    public Collection<FarmaciaEstabelecimento> buscarFarmacia(FarmaciaEstabelecimentoDTO farmaciaEstabelecimentoDTO)
    {
        ArrayList<ProjectionFilter> arrayList = new ArrayList<ProjectionFilter>();
       
        arrayList.add(new ProjectionFilter("id"));
        arrayList.add(new ProjectionFilter("estabelecimento.id"));
        arrayList.add(new ProjectionFilter("user.id"));
        arrayList.add(new ProjectionFilter("dataCadastro"));
           
        
        ProjectionFilter [] projectionFilters = new ProjectionFilter [arrayList.size()];
        arrayList.toArray(projectionFilters);
        
        return get(projectionFilters);
    }

    @Override
    public FarmaciaEstabelecimento atualizarFarmaciaEstabelecimento(FarmaciaEstabelecimento farmaciaEstabelecimento, Collection<Estabelecimento> listaEstabelecimento) throws InstantiationException, IllegalAccessException, ClassNotFoundException, NotUniqueIdException, IllegalArgumentException, InvocationTargetException, IntrospectionException
    {
        
        revomerEstabelecimentoFarmacia(farmaciaEstabelecimento);
        salvarEstabelecimentoFarmacia(farmaciaEstabelecimento, listaEstabelecimento);
        
        return farmaciaEstabelecimento;
    }

    private FarmaciaEstabelecimento salvarEstabelecimentoFarmacia(FarmaciaEstabelecimento farmaciaEstabelecimento, Collection<Estabelecimento> listaEstabelecimento) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException, NotUniqueIdException, IntrospectionException
    {
        farmaciaEstabelecimento.getFarmacia().setDataCadastro(new Date());
        farmaciaManager.atualizar(farmaciaEstabelecimento.getFarmacia());
       for (Estabelecimento estabelecimentoAux : listaEstabelecimento)
        {
           if(farmaciaEstabelecimento.getId()!= null)
               farmaciaEstabelecimento.setId(null);
               farmaciaEstabelecimento.setEstabelecimento(estabelecimentoAux);
           salvar(farmaciaEstabelecimento);
           
        }  
       return farmaciaEstabelecimento;
    }


    private FarmaciaEstabelecimento revomerEstabelecimentoFarmacia(FarmaciaEstabelecimento farmaciaEstabelecimento)
    {
        Collection<FarmaciaEstabelecimento> listaFarmaciaEstabelecimento =  buscarFarmaciaEstabelecimento(farmaciaEstabelecimento);
        
        for (FarmaciaEstabelecimento farmaciaEstabelecimento2 : listaFarmaciaEstabelecimento)
        {
            FarmaciaEstabelecimento farmaciaEstabelecimentoAux = new FarmaciaEstabelecimento();
            farmaciaEstabelecimentoAux.setId(farmaciaEstabelecimento2.getId());
            farmaciaEstabelecimentoAux.setFarmacia(new Farmacia());
            farmaciaEstabelecimentoAux.getFarmacia().setId(farmaciaEstabelecimento2.getId());
            farmaciaEstabelecimentoAux.setEstabelecimento(new Estabelecimento());
            farmaciaEstabelecimentoAux.getEstabelecimento().setId(farmaciaEstabelecimento2.getId());
            delete(farmaciaEstabelecimentoAux);
        }   
        return farmaciaEstabelecimento;
    }
}
