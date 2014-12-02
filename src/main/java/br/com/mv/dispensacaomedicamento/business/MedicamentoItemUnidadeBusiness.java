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
import br.com.mv.regulacao.dispensacaomedicamento.dao.MedicamentoItemUnidadeDao;
import br.com.mv.regulacao.dispensacaomedicamento.i18n.DispensacaoMedicamentoRBi;
import br.com.mv.regulacao.dispensacaomedicamento.model.Medicamento;
import br.com.mv.regulacao.dispensacaomedicamento.model.MedicamentoItemUnidade;
import br.com.mv.regulacao.dispensacaomedicamento.model.SituacaoSolicitacaoMedicamento;
import br.com.mv.regulacao.dispensacaomedicamento.model.dicionario.EnumCondicaoSql;

@Named("medicamentoItemUnidadeManager")
public class MedicamentoItemUnidadeBusiness extends GenericManagerImpl<MedicamentoItemUnidade, MedicamentoItemUnidadeDao> implements MedicamentoItemUnidadeManager
{
    @Inject
    SituacaoSolicitacaoMedicamentoManager situacaoSolicitacao;
    @Inject
    @Override
    public void setDao(MedicamentoItemUnidadeDao dao)
    {
        super.setDao(dao);
    }
    
    @Override
    public Collection<MedicamentoItemUnidade> buscarMedicamentosPorDescricao(String descricao)
    {
        ArrayList<ProjectionFilter> arrayList = new ArrayList<ProjectionFilter>();
        arrayList.add(new ProjectionFilter("id"));
        arrayList.add(new ProjectionFilter("descricaoSiglaItem"));
        arrayList.add(new ProjectionFilter("medicamento.id"));
        arrayList.add(new ProjectionFilter("medicamento.descricao", descricao.concat("%") ));
        arrayList.add(new ProjectionFilter("medicamento.numeroDoseDiariaDefinida"));
        arrayList.add(new ProjectionFilter("medicamento.tipoPadronizado"));
        arrayList.add(new ProjectionFilter("medicamento.procedimentoVigente.id", JoinFragment.LEFT_OUTER_JOIN));
        arrayList.add(new ProjectionFilter("medicamento.procedimentoVigente.codigoProcedimento"));
        arrayList.add(new ProjectionFilter("medicamento.procedimentoVigente.nomeProcedimento"));
        arrayList.add(new ProjectionFilter("ativo", true));
        arrayList.add(new ProjectionFilter(Order.asc("medicamento.descricao")));
        
        ProjectionFilter [] projectionFilters = new ProjectionFilter[arrayList.size()];
        arrayList.toArray(projectionFilters);
        
        return get(0, projectionFilters);
    }

    @Override
    public MedicamentoItemUnidade salvar(MedicamentoItemUnidade medicamentoItemUnidade) throws DataIntegrityViolationException, NotUniqueIdException, InstantiationException, IllegalAccessException,
            ClassNotFoundException
    {
        medicamentoItemUnidade.setDataCadastro(new Date());
        return save(medicamentoItemUnidade, true);
    }

    @Override
    public void atualizar(MedicamentoItemUnidade medicamentoItemUnidade) throws DataIntegrityViolationException, NotUniqueIdException, InstantiationException, IllegalAccessException,
            ClassNotFoundException
    {
        update(medicamentoItemUnidade, true);
    }

    @Override
    public MedicamentoItemUnidade excluir(MedicamentoItemUnidade medicamentoItemUnidade) throws NotUniqueIdException, InstantiationException, IllegalAccessException, ClassNotFoundException, LocalizedException
    {
        Boolean isDeletar = !getDao().isItemUnidadeVinculadoAItemSolicitacaoPorSituacao(medicamentoItemUnidade, null, EnumCondicaoSql.IGUAL); 
        if(isDeletar){
            delete(medicamentoItemUnidade,true);
            return medicamentoItemUnidade;
        }else{
            throw new LocalizedException(DispensacaoMedicamentoRBi.BASENAME, DispensacaoMedicamentoRBi.MEDICAMENTO_ITEM_UNIDADE_UTILIZADO);
        }
        
    }
    
    @Override
    public MedicamentoItemUnidade inativar(MedicamentoItemUnidade medicamentoItemUnidade) throws NotUniqueIdException, InstantiationException, IllegalAccessException, ClassNotFoundException, LocalizedException
    {
        SituacaoSolicitacaoMedicamento situacaoSolicitacaoMedicamento = situacaoSolicitacao.getPorDescricao("FINALIZADO");
        
        Boolean isInativar = getDao().isItemUnidadeVinculadoAItemSolicitacaoPorSituacao(medicamentoItemUnidade, situacaoSolicitacaoMedicamento, EnumCondicaoSql.DIFERENTE);
        if(isInativar){
            return medicamentoItemUnidade;
        }else{
            throw new LocalizedException(DispensacaoMedicamentoRBi.BASENAME, DispensacaoMedicamentoRBi.MEDICAMENTO_ITEM_UNIDADE_NAO_PODE_INATIVAR);
        }
    }
    
    

    @Override
    public Collection<MedicamentoItemUnidade> listarPorMedicamento(Medicamento medicamento)
    {
        ArrayList<ProjectionFilter> arrayList = new ArrayList<ProjectionFilter>();
        arrayList.add(new ProjectionFilter("id"));
        arrayList.add(new ProjectionFilter("itemUnidade.id"));
        arrayList.add(new ProjectionFilter("itemUnidade.unidade.id"));
        arrayList.add(new ProjectionFilter("itemUnidade.unidade.descricao"));
        arrayList.add(new ProjectionFilter("medicamento.id", medicamento.getId()));
        arrayList.add(new ProjectionFilter("descricaoSiglaItem"));
        arrayList.add(new ProjectionFilter("descricaoSiglaUnidade"));
        arrayList.add(new ProjectionFilter("descricaoItemUnidade"));
        arrayList.add(new ProjectionFilter("valorFator"));
        arrayList.add(new ProjectionFilter("ativo"));
        arrayList.add(new ProjectionFilter("dataCadastro"));
        
        arrayList.add(new ProjectionFilter(Order.asc("itemUnidade.unidade.descricao")));
        
        ProjectionFilter [] projectionFilters = new ProjectionFilter[arrayList.size()];
        arrayList.toArray(projectionFilters);
        
        return get(0, projectionFilters);
    }
    
    public Long count(MedicamentoItemUnidade medicamentoItemUnidade, Date dataInicial, Date dataFinal)
    {
        return count(criarProjection(medicamentoItemUnidade,null));
    }
    
    private ProjectionFilter[] criarProjection(MedicamentoItemUnidade medicamentoItemUnidade, Collection<ProjectionFilter> parametrosAMais)
    {
        ArrayList<ProjectionFilter> arrayList = new ArrayList<ProjectionFilter>();
        
        if(medicamentoItemUnidade.getId() != null)
        {
            arrayList.add(new ProjectionFilter("id", medicamentoItemUnidade.getId()));
        }else
        {
            arrayList.add(new ProjectionFilter("id"));
        }
        
        if(medicamentoItemUnidade.getDataCadastro() != null)
        {
            arrayList.add(new ProjectionFilter("dataCadastro", medicamentoItemUnidade.getDataCadastro()));
        }else
        {
            arrayList.add(new ProjectionFilter("dataCadastro"));
        }
        
        if(medicamentoItemUnidade.getDescricaoItemUnidade() != null && medicamentoItemUnidade.getDescricaoItemUnidade() != null)
        {
            arrayList.add(new ProjectionFilter("descricaoItemUnidade", medicamentoItemUnidade.getDescricaoItemUnidade()));
        }else
        {
            arrayList.add(new ProjectionFilter("descricaoItemUnidade"));
        }
        
        if(medicamentoItemUnidade.getDescricaoSiglaItem() != null && medicamentoItemUnidade.getDescricaoSiglaItem() != null)
        {
            arrayList.add(new ProjectionFilter("descricaoSiglaItemUnidade", medicamentoItemUnidade.getDescricaoSiglaItem()));
        }else
        {
            arrayList.add(new ProjectionFilter("descricaoSiglaItemUnidade"));
        }
        if(medicamentoItemUnidade.getDescricaoSiglaUnidade() != null && medicamentoItemUnidade.getDescricaoSiglaUnidade() != null)
        {
            arrayList.add(new ProjectionFilter("descricaoSiglaUnidade", medicamentoItemUnidade.getDescricaoSiglaUnidade()));
        }else
        {
            arrayList.add(new ProjectionFilter("descricaoSiglaUnidade"));
        }
        if(medicamentoItemUnidade.getItemUnidade() != null && medicamentoItemUnidade.getItemUnidade().getId() != null)
        {
            arrayList.add(new ProjectionFilter("itemUnidade.id", medicamentoItemUnidade.getItemUnidade().getId(),JoinFragment.INNER_JOIN));
        }else
        {
            arrayList.add(new ProjectionFilter("itemUnidade.id",JoinFragment.INNER_JOIN));
        }
        if(medicamentoItemUnidade.getMedicamento() != null && medicamentoItemUnidade.getMedicamento().getId() != null)
        {
            arrayList.add(new ProjectionFilter("medicamento.id", medicamentoItemUnidade.getMedicamento().getId(),JoinFragment.INNER_JOIN));
        }else
        {
            arrayList.add(new ProjectionFilter("medicamento.id",JoinFragment.INNER_JOIN));
        }
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
}
