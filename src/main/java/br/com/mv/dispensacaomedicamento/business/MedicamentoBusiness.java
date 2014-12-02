package br.com.mv.dispensacaomedicamento.business;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.hibernate.criterion.Order;
import org.hibernate.sql.JoinFragment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.mv.dispensacaomedicamento.model.Medicamento;
import br.com.mv.dispensacaomedicamento.repository.MedicamentoRepository;

@Service
@Transactional
public class MedicamentoBusiness {


    @Autowired
    private MedicamentoRepository medicamentoDao;
 
    public Collection<Medicamento> listarMedicamento() {
        return extracted();
    }

	private Collection<Medicamento> extracted() {
		return medicamentoDao.listarMedicamento();
	}
	
    @Inject
    private MedicamentoItemUnidadeManager medicamentoItemUnidadeManager;
    @Inject
    private SituacaoSolicitacaoMedicamentoManager situacaoSolicitacaoMedicamentoManager;
    
    @Inject
    @Override
    public void setDao(MedicamentoRepository dao)
    {
        super.setDao(dao);
    }
    
    
    @Override
    public Collection<Medicamento> listarMedicamentosAtivoPorDescricao(String descricao)
    {
        ArrayList<ProjectionFilter> arrayList = new ArrayList<ProjectionFilter>();
        arrayList.add(new ProjectionFilter("id"));
        arrayList.add(new ProjectionFilter("descricao", descricao.concat("%") ));
        arrayList.add(new ProjectionFilter("numeroDoseDiariaDefinida"));
        arrayList.add(new ProjectionFilter("tipoPadronizado"));
        arrayList.add(new ProjectionFilter("procedimentoVigente.id"));
        arrayList.add(new ProjectionFilter("procedimentoVigente.codigoProcedimento"));
        arrayList.add(new ProjectionFilter("procedimentoVigente.nomeProcedimento"));
        arrayList.add(new ProjectionFilter("ativo", true));
        arrayList.add(new ProjectionFilter(Order.asc(".descricao")));
        
        ProjectionFilter [] projectionFilters = new ProjectionFilter[arrayList.size()];
        arrayList.toArray(projectionFilters);
        
        return get(0, projectionFilters);
    }

    @Override
    public Medicamento salvar(Medicamento medicamento) throws DataIntegrityViolationException, NotUniqueIdException, InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        return save(medicamento, true);
    }

   
    

    @Override
    public void excluir(Medicamento medicamento) throws NotUniqueIdException, InstantiationException, IllegalAccessException, ClassNotFoundException, LocalizedException
    {
        Boolean isDeletar = !getDao().isMedicamentoVinculadoAItemSolicitacaoPorSituacao(medicamento, null, EnumCondicaoSql.IGUAL);
        if(isDeletar){
            medicamento.setMedicamentosItensUnidade(medicamentoItemUnidadeManager.listarPorMedicamento(medicamento));
            if(medicamento.getMedicamentosItensUnidade().size() > 0){
                excluirMedicamentosItensUnidade(medicamento.getMedicamentosItensUnidade());                
            }
            if(medicamento.getProcedimentoVigente().getId() == null)
                medicamento.setProcedimentoVigente(null);
            delete(medicamento, true);
        }else{
            throw new LocalizedException(DispensacaoMedicamentoRBi.BASENAME, DispensacaoMedicamentoRBi.MEDICAMENTO_NAO_PODE_EXCLUIR);
        }
    }
    
    @Override
    public Medicamento inativar(Medicamento medicamento) 
    {
        SituacaoSolicitacaoMedicamento situacaoSolicitacaoMedicamento = situacaoSolicitacaoMedicamentoManager.getPorDescricao("FINALIZADO");
        Boolean isInativar = getDao().isMedicamentoVinculadoAItemSolicitacaoPorSituacao(medicamento, situacaoSolicitacaoMedicamento, EnumCondicaoSql.DIFERENTE);
        if(isInativar){
            return medicamento;
        }else{
            throw new LocalizedException(DispensacaoMedicamentoRBi.BASENAME, DispensacaoMedicamentoRBi.MEDICAMENTO_NAO_INATIVAR);
        }
    }
    
    private void excluirMedicamentosItensUnidade(Collection<MedicamentoItemUnidade> medicamentosItensUnidade) throws InstantiationException, IllegalAccessException, ClassNotFoundException, LocalizedException, NotUniqueIdException{
        for (MedicamentoItemUnidade medicamentoItemUnidade : medicamentosItensUnidade)
        {
            medicamentoItemUnidadeManager.excluir(medicamentoItemUnidade);
        }
    }

    
    @Override
    public Long count(Medicamento medicamento)
    {
        return count(criarProjection(medicamento,null));
    }

    @Override
    public Long count(Medicamento medicamento, Date dataInicial, Date dataFinal)
    {
        return count(criarProjection(medicamento,null));
    }

    @Override
    public Collection<Medicamento> list(int initialPos, int maxResult, Medicamento medicamento)
    {
        return fetch(initialPos, maxResult, criarProjection(medicamento,relacoesMedicamento(medicamento)));
    }
    
    @Override
    public Medicamento get(Medicamento medicamento)
    {
        return getUnique(criarProjection(medicamento,relacoesMedicamento(medicamento)));
    }
    
    private ArrayList<ProjectionFilter> relacoesMedicamento(Medicamento medicamento){
        ArrayList<ProjectionFilter> arrayList = new ArrayList<ProjectionFilter>();
        if(medicamento.getTipoReceituario() != null && medicamento.getTipoReceituario().getId() != null)
        {
            arrayList.add(new ProjectionFilter("tipoReceituario.id",medicamento.getTipoReceituario().getId(),JoinFragment.LEFT_OUTER_JOIN));
        }else
        {
            arrayList.add(new ProjectionFilter("tipoReceituario.id",JoinFragment.LEFT_OUTER_JOIN));
        }
        arrayList.add(new ProjectionFilter("tipoReceituario.descricao"));
        if(medicamento.getProcedimentoVigente() != null && medicamento.getProcedimentoVigente().getId() != null)
        {
            arrayList.add(new ProjectionFilter("procedimentoVigente.id",medicamento.getProcedimentoVigente().getId(),JoinFragment.LEFT_OUTER_JOIN));
        }else
        {
            arrayList.add(new ProjectionFilter("procedimentoVigente.id",JoinFragment.LEFT_OUTER_JOIN));
        }
        arrayList.add(new ProjectionFilter("procedimentoVigente.nomeProcedimento"));
        arrayList.add(new ProjectionFilter("procedimentoVigente.codigoProcedimento"));
        arrayList.add(new ProjectionFilter("formaFarmaceutica.id",JoinFragment.LEFT_OUTER_JOIN));
        arrayList.add(new ProjectionFilter("formaFarmaceutica.descricao"));
        return arrayList;
    }

    @Override
    public Medicamento salvarMedicamento(Medicamento medicamento) throws DataIntegrityViolationException, InstantiationException, IllegalAccessException, ClassNotFoundException, NotUniqueIdException
    {
        Medicamento salvo = salvar(medicamento); 
        if(medicamento.getMedicamentosItensUnidade().size() > 0){
            for (MedicamentoItemUnidade medicamentoItemUnidade : medicamento.getMedicamentosItensUnidade())
            {
                medicamentoItemUnidade.setMedicamento(salvo);
                medicamentoItemUnidadeManager.salvar(medicamentoItemUnidade);
            }
        }
        return salvo;
    }
    
    @Override
    public void atualizar(Medicamento medicamento) throws DataIntegrityViolationException, InstantiationException, IllegalAccessException, ClassNotFoundException, NotUniqueIdException{
        for (MedicamentoItemUnidade medicamentoItemUnidade : medicamento.getMedicamentosItensUnidade())
        {
            medicamentoItemUnidade.setMedicamento(medicamento);
            if(medicamentoItemUnidade.getId() == null)
                medicamentoItemUnidade = medicamentoItemUnidadeManager.salvar(medicamentoItemUnidade);
            
            medicamentoItemUnidadeManager.atualizar(medicamentoItemUnidade);
        }
        if(medicamento.getProcedimentoVigente().getId() == null && medicamento.getProcedimentoVigente().getCodigoProcedimento() == null){
            medicamento.setProcedimentoVigente(null);
        }
        medicamento.setMedicamentosItensUnidade(null);
        update(medicamento, true);
    }
    
   
    
    private ProjectionFilter[] criarProjection(Medicamento medicamento, Collection<ProjectionFilter> parametros)
    {
        ArrayList<ProjectionFilter> arrayList = new ArrayList<ProjectionFilter>();
        
        if(medicamento.getId() != null)
        {
            arrayList.add(new ProjectionFilter("id", medicamento.getId()));
        }else
        {
            arrayList.add(new ProjectionFilter("id"));
        }
        
        if(medicamento.getDescricao() != null)
        {
            arrayList.add(new ProjectionFilter("descricao",medicamento.getDescricao()));
        }else
        {
            arrayList.add(new ProjectionFilter("descricao"));
        }
        
        if(medicamento.getTipoEspecificoSexo() != null)
        {
            arrayList.add(new ProjectionFilter("tipoEspecificoSexo",medicamento.getTipoEspecificoSexo()));
        }else
        {
            arrayList.add(new ProjectionFilter("tipoEspecificoSexo"));
        }
        
        if(medicamento.getTipoPadronizado() != null)
        {
            arrayList.add(new ProjectionFilter("tipoPadronizado",medicamento.getTipoPadronizado()));
        }else
        {
            arrayList.add(new ProjectionFilter("tipoPadronizado"));
        }
        
        if(medicamento.getNumeroDoseDiariaDefinida() != null)
        {
            arrayList.add(new ProjectionFilter("numeroDoseDiariaDefinida",medicamento.getNumeroDoseDiariaDefinida()));
        }else
        {
            arrayList.add(new ProjectionFilter("numeroDoseDiariaDefinida"));
        }
        
        if(medicamento.getTipoControle() != null)
        {
            arrayList.add(new ProjectionFilter("tipoControle",medicamento.getTipoControle()));
        }else
        {
            arrayList.add(new ProjectionFilter("tipoControle"));
        }
        
        if(medicamento.getAtivo() != null)
        {
            arrayList.add(new ProjectionFilter("ativo",medicamento.getAtivo() ));
        }else
        {
            arrayList.add(new ProjectionFilter("ativo"));
        }
        
        if(medicamento.getPlacebo() != null)
        {
            arrayList.add(new ProjectionFilter("placebo",medicamento.getPlacebo() ));
        }else
        {
            arrayList.add(new ProjectionFilter("placebo"));
        }
        
        if(medicamento.getTermolabel() != null)
        {
            arrayList.add(new ProjectionFilter("termolabel",medicamento.getTermolabel() ));
        }else
        {
            arrayList.add(new ProjectionFilter("termolabel"));
        }
        
        if(medicamento.getDispensaRetorno() != null)
        {
            arrayList.add(new ProjectionFilter("dispensaRetorno",medicamento.getDispensaRetorno() ));
        }else
        {
            arrayList.add(new ProjectionFilter("dispensaRetorno"));
        }
        
        if(medicamento.getFracionaEmbalagem() != null)
        {
            arrayList.add(new ProjectionFilter("fracionaEmbalagem",medicamento.getFracionaEmbalagem() ));
        }else
        {
            arrayList.add(new ProjectionFilter("fracionaEmbalagem"));
        }
        
        arrayList.add(new ProjectionFilter(Order.asc("descricao")));
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
