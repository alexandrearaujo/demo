package br.com.mv.dispensacaomedicamento.business;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.dao.DataIntegrityViolationException;

import br.com.mv.commons.i18n.exception.LocalizedException;
import br.com.mv.commons.util.SpringBeans;
import br.com.mv.commons.web.business.GenericManagerImpl;
import br.com.mv.commons.web.exception.NotUniqueIdException;
import br.com.mv.commons.web.util.hibernate.transform.ProjectionFilter;
import br.com.mv.commons.web.util.hibernate.transform.ProjectionFilter.Condicao;
import br.com.mv.flex.util.SessionUtil;
import br.com.mv.regulacao.dispensacaomedicamento.dao.MedicamentoDao;
import br.com.mv.regulacao.dispensacaomedicamento.dao.MotivoDevolucaoMedicamentoDao;
import br.com.mv.regulacao.dispensacaomedicamento.i18n.DispensacaoMedicamentoRBi;
import br.com.mv.regulacao.dispensacaomedicamento.model.MotivoDevolucaoMedicamento;
import br.com.mv.sigas.model.geral.User;

@Named("motivoDevolucaoMedicamentoManager")
public class MotivoDevolucaoMedicamentoBusiness extends GenericManagerImpl<MotivoDevolucaoMedicamento, MotivoDevolucaoMedicamentoDao> implements MotivoDevolucaoMedicamentoManager
{

    
    
    @Inject
    @Override
    public void setDao(MotivoDevolucaoMedicamentoDao dao)
    {
        super.setDao(dao);
    }
    
    @Override
    public MotivoDevolucaoMedicamento salvar(MotivoDevolucaoMedicamento motivoDevolucaoMedicamento) throws DataIntegrityViolationException, NotUniqueIdException, InstantiationException,
            IllegalAccessException, ClassNotFoundException
    {
        ArrayList<ProjectionFilter> arrayList = new ArrayList<ProjectionFilter>();
        arrayList.add(new ProjectionFilter("descricao", motivoDevolucaoMedicamento.getDescricao(),Condicao.IGUAL));
        
        ProjectionFilter[] projectionFilters = new ProjectionFilter[arrayList.size()];

        if(count(arrayList.toArray(projectionFilters)) == 0){
            prepararParaCRUD(motivoDevolucaoMedicamento);
            return save(motivoDevolucaoMedicamento, true);
        }else{
            throw new LocalizedException(DispensacaoMedicamentoRBi.BASENAME, DispensacaoMedicamentoRBi.MOTIVO_DEVOLUCAO_MEDICAMENTO_EXISTENTE);
        }
        
    }

    @Override
    public void atualizar(MotivoDevolucaoMedicamento motivoDevolucaoMedicamento) throws DataIntegrityViolationException, InstantiationException, IllegalAccessException, ClassNotFoundException,
            NotUniqueIdException
    {
        prepararParaCRUD(motivoDevolucaoMedicamento);
        update(motivoDevolucaoMedicamento, true);
    }
    
    private void prepararParaCRUD(MotivoDevolucaoMedicamento motivoDevolucaoMedicamento){
        if(motivoDevolucaoMedicamento.getDataCadastro() == null){
            motivoDevolucaoMedicamento.setDataCadastro(new Date());
        }
        if(motivoDevolucaoMedicamento.getUsuario() == null || motivoDevolucaoMedicamento.getUsuario().getId() == null){
            SessionUtil sessionUtil = SpringBeans.getManager(SessionUtil.class);
            motivoDevolucaoMedicamento.setUsuario(sessionUtil.getUser());
        }
        User usuario = new User();
        usuario.setId(motivoDevolucaoMedicamento.getUsuario().getId());
        motivoDevolucaoMedicamento.setUsuario(usuario);
    }

    @Override
    public void excluir(MotivoDevolucaoMedicamento motivoDevolucaoMedicamento) throws NotUniqueIdException, InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        prepararParaCRUD(motivoDevolucaoMedicamento);
        delete(motivoDevolucaoMedicamento, true);
    }

    @Override
    public Collection<MotivoDevolucaoMedicamento> list(MotivoDevolucaoMedicamento motivoDevolucaoMedicamento)
    {
        return get(0,criarProjection(motivoDevolucaoMedicamento, null));
    }
    
    @Override
    public Collection<MotivoDevolucaoMedicamento> list(int initialPos, int maxResult, MotivoDevolucaoMedicamento motivoDevolucaoMedicamento)
    {
        return fetch(initialPos, maxResult, criarProjection(motivoDevolucaoMedicamento,null));
    }

    @Override
    public Long count(MotivoDevolucaoMedicamento motivoDevolucaoMedicamento)
    {
        return count(criarProjection(motivoDevolucaoMedicamento,null));
    }

    @Override
    public MotivoDevolucaoMedicamento get(MotivoDevolucaoMedicamento motivoDevolucaoMedicamento)
    {
        return getUnique(criarProjection(motivoDevolucaoMedicamento, null));
    }
    
    private ProjectionFilter[] criarProjection(MotivoDevolucaoMedicamento motivoDevolucaoMedicamento, Collection<ProjectionFilter> parametros)
    {
        ArrayList<ProjectionFilter> arrayList = new ArrayList<ProjectionFilter>();
        
        if(motivoDevolucaoMedicamento.getId() != null)
        {
            arrayList.add(new ProjectionFilter("id", motivoDevolucaoMedicamento.getId()));
        }else
        {
            arrayList.add(new ProjectionFilter("id"));
        }
        
        if(motivoDevolucaoMedicamento.getDescricao() != null && motivoDevolucaoMedicamento.getDescricao() != "")
        {
            arrayList.add(new ProjectionFilter("descricao", motivoDevolucaoMedicamento.getDescricao()));
        }else
        {
            arrayList.add(new ProjectionFilter("descricao"));
        }
        
        if(motivoDevolucaoMedicamento.getDataCadastro() != null)
        {
            arrayList.add(new ProjectionFilter("dataCadastro", motivoDevolucaoMedicamento.getDataCadastro()));
        }else
        {
            arrayList.add(new ProjectionFilter("dataCadastro"));
        }
        
        if(motivoDevolucaoMedicamento.getUsuario() != null && motivoDevolucaoMedicamento.getUsuario().getId() != null )
        {
            arrayList.add(new ProjectionFilter("usuario.id", motivoDevolucaoMedicamento.getUsuario().getId()));
        }else
        {
            arrayList.add(new ProjectionFilter("usuario.id"));
        }
        
        
        if(parametros != null){
            for (ProjectionFilter projectionFilter : parametros)
            {
                arrayList.add(projectionFilter);
            }
        }
        
        ProjectionFilter[] projectionFilters = new ProjectionFilter[arrayList.size()];
        return arrayList.toArray(projectionFilters);
    }
}
