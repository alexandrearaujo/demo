package br.com.mv.dispensacaomedicamento.business;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.inject.Inject;
import javax.inject.Named;

import org.hibernate.sql.JoinFragment;
import org.springframework.dao.DataIntegrityViolationException;

import br.com.mv.commons.i18n.exception.LocalizedException;
import br.com.mv.commons.web.business.GenericManagerImpl;
import br.com.mv.commons.web.exception.NotUniqueIdException;
import br.com.mv.commons.web.util.hibernate.transform.ProjectionFilter;
import br.com.mv.regulacao.business.geral.ProcedimentoManager;
import br.com.mv.regulacao.dispensacaomedicamento.dao.SolicitacaoMedicamentoApacDao;
import br.com.mv.regulacao.dispensacaomedicamento.exception.DispensacaoMedicamentoException;
import br.com.mv.regulacao.dispensacaomedicamento.i18n.DispensacaoMedicamentoRBi;
import br.com.mv.regulacao.dispensacaomedicamento.model.ItemSolicitacaoMedicamento;
import br.com.mv.regulacao.dispensacaomedicamento.model.ItemSolicitacaoMedicamentoApac;
import br.com.mv.regulacao.dispensacaomedicamento.model.ProcedimentoCidDTO;
import br.com.mv.regulacao.dispensacaomedicamento.model.SituacaoSolicitacaoMedicamento;
import br.com.mv.regulacao.dispensacaomedicamento.model.SolicitacaoMedicamento;
import br.com.mv.regulacao.dispensacaomedicamento.model.SolicitacaoMedicamentoApac;
import br.com.mv.regulacao.dispensacaomedicamento.model.SolicitacaoMedicamentoApacDTO;
import br.com.mv.regulacao.dispensacaomedicamento.model.dicionario.EnumSituacaoSolicitacaoMedicamento;
import br.com.mv.regulacao.model.geral.Cid;
import br.com.mv.regulacao.model.geral.Estabelecimento;
import br.com.mv.regulacao.model.geral.Procedimento;
import br.com.mv.regulacao.model.geral.ProcedimentoCid;
import br.com.mv.regulacao.model.geral.ProcedimentoVigente;
import br.com.mv.regulacao.model.prontuario.Cidadao;
import br.com.mv.sigas.model.geral.ProfissionalSigas;
import br.com.mv.sigas.model.geral.VinculoProfissionalSigas;
import flex.messaging.io.ArrayCollection;

@Named("solicitacaoMedicamentoApacManager")
public class SolicitacaoMedicamentoApacBusiness extends GenericManagerImpl<SolicitacaoMedicamentoApac, SolicitacaoMedicamentoApacDao> implements SolicitacaoMedicamentoApacManager
{
    @Inject
    private ProcedimentoManager procedimentoManager;
    
    @Inject
    private SolicitacaoMedicamentoManager solicitacaoMedicamentoManager;
    
    @Inject
    private ItemSolicitacaoMedicamentoApacManager itemSolicitacaoMedicamentoApacManager;

    @Inject
    private ItemSolicitacaoMedicamentoManager itemSolicitacaoMedicamentoManager;
    
    @Inject
    private HistoricoSolicitacaoMedicamentoManager historicoSolicitacaoMedicamentoManager;
    
    @Inject
    @Override
    public void setDao(SolicitacaoMedicamentoApacDao dao)
    {
        super.setDao(dao);
    }
    
    
    /*
     * (non-Javadoc)
     * @see br.com.mv.regulacao.dispensacaomedicamento.manager.SolicitacaoMedicamentoApacManager#peencheDataFinal(br.com.mv.regulacao.dispensacaomedicamento.model.SolicitacaoMedicamentoApac)
     */
    @Override
    public SolicitacaoMedicamentoApac preencherDataFinal(SolicitacaoMedicamentoApac solicitacaoMedicamentoApac)
    {
        if(solicitacaoMedicamentoApac != null && solicitacaoMedicamentoApac.getDataInicio() != null 
                && validarCompetenciaDataInicial(solicitacaoMedicamentoApac))
        {
            Calendar dataFinal = new GregorianCalendar();
            dataFinal.setTime(solicitacaoMedicamentoApac.getDataInicio());
            dataFinal.add(Calendar.DAY_OF_MONTH, 90);
            solicitacaoMedicamentoApac.setDataFim(dataFinal.getTime());
            return solicitacaoMedicamentoApac;
        }
        return null;
    }

    @Override
    public boolean validarCompetenciaDataInicial(SolicitacaoMedicamentoApac solicitacaoMedicamentoApac)
    {
            Calendar dataAtual = Calendar.getInstance();
            Calendar dataInicial = new GregorianCalendar();
            dataInicial.setTime(solicitacaoMedicamentoApac.getDataInicio());
            if(dataAtual.get(Calendar.MONDAY) == dataInicial.get(Calendar.MONTH))
                return true;
            else
                throw new DispensacaoMedicamentoException(DispensacaoMedicamentoException.DATA_INICIAL_APAC_FORA_MES_COMPETENCIA);
    }

    @Override
    public boolean validarCompetenciaDataFinal(SolicitacaoMedicamentoApac solicitacaoMedicamentoApac)
    {
        Calendar dataFinalMaximaPermitida = new GregorianCalendar();
        Calendar dataFinal = new GregorianCalendar();
        dataFinalMaximaPermitida.setTime(solicitacaoMedicamentoApac.getDataInicio());
        dataFinalMaximaPermitida.add(Calendar.DAY_OF_MONTH, 90);
        dataFinal.setTime(solicitacaoMedicamentoApac.getDataFim());
        
        if(dataFinal.equals(dataFinalMaximaPermitida) || dataFinal.before(dataFinalMaximaPermitida))
            return true;
        else
           throw new DispensacaoMedicamentoException(DispensacaoMedicamentoException.DATA_FINAL_APAC_SUPERIOR_90DIAS); 
    }

    /*
     * (non-Javadoc)
     * @see br.com.mv.regulacao.dispensacaomedicamento.manager.SolicitacaoMedicamentoApacManager#listaProcedimentoCidDTOPorDescricao(java.lang.String, br.com.mv.regulacao.model.geral.ProcedimentoVigente, br.com.mv.regulacao.dispensacaomedicamento.model.ProcedimentoCidDTO, boolean)
     */
    @Override
    public Collection<ProcedimentoCidDTO> listarProcedimentoCidDTOPorDescricao(String descricao, ProcedimentoVigente procedimentoVigente, ProcedimentoCidDTO cidPrincipal, boolean isCidPadronizado)
    {
        return getDao().listarProcedimentoCidDTOPorDescricao(descricao, procedimentoVigente, cidPrincipal, isCidPadronizado);
    }

    /*
     * (non-Javadoc)
     * @see br.com.mv.regulacao.dispensacaomedicamento.manager.SolicitacaoMedicamentoApacManager#validaQuantidadeMaximaItemSolicitacaoMedicamentoApac(br.com.mv.regulacao.dispensacaomedicamento.model.ItemSolicitacaoMedicamentoApac, br.com.mv.regulacao.model.geral.ProcedimentoVigente)
     */
    @Override
    public ItemSolicitacaoMedicamentoApac validarQuantidadeMaximaItemSolicitacaoMedicamentoApac(ItemSolicitacaoMedicamentoApac itemSolicitacaoMedicamentoApac)
    {
           Procedimento procedimento = procedimentoManager.buscaProcedimentoQuantidadeMaximaExecucao(itemSolicitacaoMedicamentoApac.getMedicamentoItemUnidade().getMedicamento().getProcedimentoVigente());
           Long totalQuantidadeMes = (itemSolicitacaoMedicamentoApac.getQuantidadeSolicitadaMes1() +
                   itemSolicitacaoMedicamentoApac.getQuantidadeSolicitadaMes2() + 
                   itemSolicitacaoMedicamentoApac.getQuantidadeSolicitadaMes3());
           
           if(totalQuantidadeMes == 0)
               throw new DispensacaoMedicamentoException(DispensacaoMedicamentoException.SOMATORIO_QUANTIDA_MEDICAMENTO_IGUAL_A_ZERO);

           if(procedimento != null && procedimento.getQtMaximaExecucao() != null && (procedimento.getQtMaximaExecucao() != 9999L) && (totalQuantidadeMes > procedimento.getQtMaximaExecucao()))
                throw new DispensacaoMedicamentoException(DispensacaoMedicamentoException.QUANTIDA_MEDICAMENTO_MAXIMA_PERMITIDA);   
        
           return itemSolicitacaoMedicamentoApac;
    }
    
    /*
     * (non-Javadoc)
     * @see br.com.mv.regulacao.dispensacaomedicamento.manager.SolicitacaoMedicamentoApacManager#validaDuplicidadeSolicitacaoMedicamentoApac(br.com.mv.regulacao.dispensacaomedicamento.model.SolicitacaoMedicamentoApac)
     */
    @Override
    public boolean validarDuplicidadeSolicitacaoMedicamentoApac(SolicitacaoMedicamentoApac solicitacaoMedicamentoApac, ItemSolicitacaoMedicamentoApac itemSolicitacaoMedicamentoApac)
    {
            if(existeSolicitacaoMedicamentAPACPacienteParaMesmoCidPrincipal(solicitacaoMedicamentoApac))
                throw new DispensacaoMedicamentoException(DispensacaoMedicamentoException.PACIENTE_CID_PRINCIPAL_MESMO_PERIODO_VALIDADE);
            if(existeSolicitacaoMedicamentAPACPacienteParaMesmoMedicamento(solicitacaoMedicamentoApac, itemSolicitacaoMedicamentoApac))
                throw new DispensacaoMedicamentoException(DispensacaoMedicamentoException.PACIENTE_MEDICAMENTO_MESMO_PERIODO_VALIDADE);
        return false;
    }

    /*
     * (non-Javadoc)
     * @see br.com.mv.regulacao.dispensacaomedicamento.manager.SolicitacaoMedicamentoApacManager#existeSolicitacaoMedicamentAPACPacienteParaMesmoCidPrincipal()
     */
    @Override
    public boolean existeSolicitacaoMedicamentAPACPacienteParaMesmoCidPrincipal(SolicitacaoMedicamentoApac solicitacaoMedicamentoApac)
    {
        return getDao().existeSolicitacaoMedicamentAPACPacienteParaMesmoCidPrincipal(solicitacaoMedicamentoApac);
    }

    /*
     * (non-Javadoc)
     * @see br.com.mv.regulacao.dispensacaomedicamento.manager.SolicitacaoMedicamentoApacManager#existeSolicitacaoMedicamentAPACPacienteParaMesmoMedicamento()
     */
    @Override
    public boolean existeSolicitacaoMedicamentAPACPacienteParaMesmoMedicamento(SolicitacaoMedicamentoApac solicitacaoMedicamentoApac, ItemSolicitacaoMedicamentoApac itemSolicitacaoMedicamentoApac)
    {
        return getDao().existeSolicitacaoMedicamentAPACPacienteParaMesmoMedicamento(solicitacaoMedicamentoApac, itemSolicitacaoMedicamentoApac);
    }
    
    @Override
    public Collection<SolicitacaoMedicamentoApac> listarAvaliacaoApacOrm(SolicitacaoMedicamentoApac solicitacaoMedicamentoApac)
    {
        Collection<SolicitacaoMedicamentoApac> listOrm = new ArrayList<SolicitacaoMedicamentoApac>(); 
        Collection<SolicitacaoMedicamentoApacDTO> listDto = getDao().buscarAvaliacaoApac(solicitacaoMedicamentoApac);

        for (SolicitacaoMedicamentoApacDTO dto : listDto)
        {
            listOrm.add(getOrmSolicitacaoMedicamentoApacDto(dto));
        }

        return listOrm;
    }
    
    @Override
    public Collection<SolicitacaoMedicamentoApac> listarAvaliacaoApacOrm(int initialPos, int maxResults, SolicitacaoMedicamentoApac solicitacaoMedicamentoApac)
    {
        Collection<SolicitacaoMedicamentoApac> listOrm = new ArrayList<SolicitacaoMedicamentoApac>(); 
        Collection<SolicitacaoMedicamentoApacDTO> listDto = getDao().buscarAvaliacaoApac(initialPos, maxResults, solicitacaoMedicamentoApac);

        for (SolicitacaoMedicamentoApacDTO dto : listDto)
        {
            listOrm.add(getOrmSolicitacaoMedicamentoApacDto(dto));
        }

        return listOrm;
    }
    
    @Override
    public Collection<SolicitacaoMedicamentoApacDTO> listarAvaliacaoApacDto(SolicitacaoMedicamentoApac solicitacaoMedicamentoApac)
    {
        return getDao().buscarAvaliacaoApac(solicitacaoMedicamentoApac);
    }

    @Override
    public Collection<SolicitacaoMedicamentoApacDTO> listarAvaliacaoApacDto(int initialPos, int maxResults, SolicitacaoMedicamentoApac solicitacaoMedicamentoApac)
    {
        return getDao().buscarAvaliacaoApac(initialPos, maxResults, solicitacaoMedicamentoApac);
    }
    
    @Override
    public Long countAvaliacaoApac(SolicitacaoMedicamentoApac solicitacaoMedicamentoApac)
    {        
        return getDao().countAvaliacaoApac(solicitacaoMedicamentoApac);
    }

    @Override
    public SolicitacaoMedicamentoApac salvarSolicitacaoMedicamentoApacComHistorico(SolicitacaoMedicamentoApac solicitacaoMedicamentoApac, Collection<ItemSolicitacaoMedicamentoApac> listaItemSolicitacaoMedicamentoApac) throws DispensacaoMedicamentoException, DataIntegrityViolationException, NotUniqueIdException,
            InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        
        validarDuplicidadeSolicitacaoMedicamentoMesmoReceituarioPaciente(solicitacaoMedicamentoApac);
        adicionaSituacaoReceituarioEntregue(solicitacaoMedicamentoApac);
        if(solicitacaoMedicamentoApac.getDataCadastro() == null)
            solicitacaoMedicamentoApac.setDataCadastro(new Date());
        validaTipoHemofiliaTipoInibidor(solicitacaoMedicamentoApac);

        SolicitacaoMedicamento solicitacaoMedicamentoClone = getSolicitacaoMedicamentoClone(solicitacaoMedicamentoApac);
        
        if(solicitacaoMedicamentoApac.isApac())
        {
            salvar(solicitacaoMedicamentoApac);
            salvarListaItemSolicitacaoMedicamento(solicitacaoMedicamentoApac, listaItemSolicitacaoMedicamentoApac);
            solicitacaoMedicamentoManager.salvarHitoricoSolicitacaoMedicamento(solicitacaoMedicamentoApac);
            return solicitacaoMedicamentoApac;
        }
        else
        {
            solicitacaoMedicamentoManager.salvar(solicitacaoMedicamentoClone);
            salvarListaItemSolicitacaoMedicamento(solicitacaoMedicamentoApac.clone(solicitacaoMedicamentoClone), listaItemSolicitacaoMedicamentoApac);
            solicitacaoMedicamentoManager.salvarHitoricoSolicitacaoMedicamento(solicitacaoMedicamentoClone);
            return solicitacaoMedicamentoApac.clone(solicitacaoMedicamentoClone);
        }
        
    }


    @Override
    public SolicitacaoMedicamentoApac atualizarSolicitacaoMedicamentoApac(SolicitacaoMedicamentoApac solicitacaoMedicamentoApac, Collection<ItemSolicitacaoMedicamentoApac> listaItemSolicitacaoMedicamentoApac) throws DispensacaoMedicamentoException, DataIntegrityViolationException, NotUniqueIdException, InstantiationException, IllegalAccessException, ClassNotFoundException
    {
       SolicitacaoMedicamento solicitacaoMedicamentoBD = solicitacaoMedicamentoManager.getUnique(getProjecaoFilterSolicitacaoMedicamento(solicitacaoMedicamentoApac));
       SolicitacaoMedicamento solicitacaoMedicamentoClone = null;
       
       if(validarSituacaoSolicitacaoMedicamentoApac(solicitacaoMedicamentoBD))
       {
           if(solicitacaoMedicamentoBD.isApac() == true && solicitacaoMedicamentoBD.isApac() == solicitacaoMedicamentoApac.isApac())
           {
               removeObjetosTrnasientsSolicitacaoMedicamentoApac(solicitacaoMedicamentoApac);
               validaTipoHemofiliaTipoInibidor(solicitacaoMedicamentoApac);
               atualizar(solicitacaoMedicamentoApac);
               exclurListaItemSolicitacaoMedicamento(solicitacaoMedicamentoApac);
               atualizarItemSolicitacaoMedicamento(solicitacaoMedicamentoApac, listaItemSolicitacaoMedicamentoApac);
               return solicitacaoMedicamentoApac;
           }
           else if (solicitacaoMedicamentoBD.isApac() == false && solicitacaoMedicamentoBD.isApac() == solicitacaoMedicamentoApac.isApac())
           {
               solicitacaoMedicamentoClone = getSolicitacaoMedicamentoClone(solicitacaoMedicamentoApac);
               removeObjetosTrnasientsSolicitacaoMedicamento(solicitacaoMedicamentoClone);
               exclurListaItemSolicitacaoMedicamento(solicitacaoMedicamentoApac);
               solicitacaoMedicamentoManager.atualizar(solicitacaoMedicamentoClone);
               atualizarItemSolicitacaoMedicamento(solicitacaoMedicamentoApac.clone(solicitacaoMedicamentoClone), listaItemSolicitacaoMedicamentoApac);
               return solicitacaoMedicamentoApac.clone(solicitacaoMedicamentoClone);
           }
           else
           {
               excluirSolicitacaoMedicamentoComHistorico(solicitacaoMedicamentoApac);
               solicitacaoMedicamentoApac.setId(null);
               solicitacaoMedicamentoApac = salvarSolicitacaoMedicamentoApacComHistorico(solicitacaoMedicamentoApac, listaItemSolicitacaoMedicamentoApac);
           }
       }
       
       return solicitacaoMedicamentoApac;
    }

    private void removeObjetosTrnasientsItemSolicitacaoMedicamentoApac(ItemSolicitacaoMedicamentoApac itemSolicitacaoMedicamentoApac)
    {
        if(itemSolicitacaoMedicamentoApac.getProcedimentoCidPrincipal() != null && 
                itemSolicitacaoMedicamentoApac.getProcedimentoCidPrincipal().getId() != null &&
                itemSolicitacaoMedicamentoApac.getProcedimentoCidPrincipal().getId() != 0)
        {
            if(itemSolicitacaoMedicamentoApac.getProcedimentoCidPrincipal().getCid() != null && itemSolicitacaoMedicamentoApac.getProcedimentoCidPrincipal().getCid().getId() == 0)
               itemSolicitacaoMedicamentoApac.getProcedimentoCidPrincipal().setCid(null);
            if(itemSolicitacaoMedicamentoApac.getProcedimentoCidPrincipal().getProcedimento() != null && itemSolicitacaoMedicamentoApac.getProcedimentoCidPrincipal().getProcedimento().getId() == 0)
               itemSolicitacaoMedicamentoApac.getProcedimentoCidPrincipal().setProcedimento(null);
        }
        else
           itemSolicitacaoMedicamentoApac.setProcedimentoCidPrincipal(null);
        
        if(itemSolicitacaoMedicamentoApac.getProcedimentoCidSecundario() != null &&
                itemSolicitacaoMedicamentoApac.getProcedimentoCidSecundario().getId() != null &&
                itemSolicitacaoMedicamentoApac.getProcedimentoCidSecundario().getId() != 0)
        {
            if(itemSolicitacaoMedicamentoApac.getProcedimentoCidSecundario().getCid() != null && itemSolicitacaoMedicamentoApac.getProcedimentoCidSecundario().getCid().getId() == 0)
                itemSolicitacaoMedicamentoApac.getProcedimentoCidSecundario().setCid(null);
             if(itemSolicitacaoMedicamentoApac.getProcedimentoCidSecundario().getProcedimento() != null && itemSolicitacaoMedicamentoApac.getProcedimentoCidSecundario().getProcedimento().getId() == 0)
                itemSolicitacaoMedicamentoApac.getProcedimentoCidSecundario().setProcedimento(null);
        }
        else
            itemSolicitacaoMedicamentoApac.setProcedimentoCidSecundario(null);
        
        if(itemSolicitacaoMedicamentoApac.getMedicamentoItemUnidade() != null && itemSolicitacaoMedicamentoApac.getMedicamentoItemUnidade().getId() == 0)
            itemSolicitacaoMedicamentoApac.setMedicamentoItemUnidade(null);
        if(itemSolicitacaoMedicamentoApac.getSolicitacaoMedicamento() != null && itemSolicitacaoMedicamentoApac.getSolicitacaoMedicamento().getId() == 0)
            itemSolicitacaoMedicamentoApac.setSolicitacaoMedicamento(null);
        if(itemSolicitacaoMedicamentoApac.getViaAdministracaoMedicamento() != null && itemSolicitacaoMedicamentoApac.getViaAdministracaoMedicamento().getId() == 0)
            itemSolicitacaoMedicamentoApac.setViaAdministracaoMedicamento(null);
    }


    private void removeObjetosTrnasientsSolicitacaoMedicamentoApac(SolicitacaoMedicamentoApac solicitacaoMedicamentoApac)
    {
        solicitacaoMedicamentoApac.setListaItemSolicitacaoMedicamentosApac(null);
        if(solicitacaoMedicamentoApac.getFarmacia() != null && solicitacaoMedicamentoApac.getFarmacia().getId() == null)
            solicitacaoMedicamentoApac.setFarmacia(null);
        if(solicitacaoMedicamentoApac.getProcedimentoCidPrincipal() != null && solicitacaoMedicamentoApac.getProcedimentoCidPrincipal().getId() == null)
            solicitacaoMedicamentoApac.setProcedimentoCidPrincipal(null);
        if(solicitacaoMedicamentoApac.getProcedimentoCidSecundario() != null && solicitacaoMedicamentoApac.getProcedimentoCidSecundario().getId() == null)
            solicitacaoMedicamentoApac.setProcedimentoCidSecundario(null);
        if(solicitacaoMedicamentoApac.getProfissionalAutorizador() != null && solicitacaoMedicamentoApac.getProfissionalAutorizador().getId() == null)
            solicitacaoMedicamentoApac.setProfissionalAutorizador(null);
        if(solicitacaoMedicamentoApac.getProfissionalExecutante() != null && solicitacaoMedicamentoApac.getProfissionalExecutante().getId() == null)
            solicitacaoMedicamentoApac.setProfissionalExecutante(null);
        if(solicitacaoMedicamentoApac.getSituacaoSolicitacaoMedicamento() != null && solicitacaoMedicamentoApac.getSituacaoSolicitacaoMedicamento().getId() == null)
            solicitacaoMedicamentoApac.setSituacaoSolicitacaoMedicamento(null);
        if(solicitacaoMedicamentoApac.getSolicitacaoMedicamentoAnteriorApac() != null && solicitacaoMedicamentoApac.getSolicitacaoMedicamentoAnteriorApac().getId() == null)
            solicitacaoMedicamentoApac.setSolicitacaoMedicamentoAnteriorApac(null);
        if(solicitacaoMedicamentoApac.getVinculoProfissional() != null && solicitacaoMedicamentoApac.getVinculoProfissional().getId() == null)
            solicitacaoMedicamentoApac.setVinculoProfissional(null);
        
    }

    private void removeObjetosTrnasientsSolicitacaoMedicamento(SolicitacaoMedicamento solicitacaoMedicamento)
    {
        solicitacaoMedicamento.setListaItemSolicitacaoMedicamentosApac(null);
        if(solicitacaoMedicamento.getFarmacia() != null && solicitacaoMedicamento.getFarmacia().getId() == null)
            solicitacaoMedicamento.setFarmacia(null);
        if(solicitacaoMedicamento.getSituacaoSolicitacaoMedicamento() != null && solicitacaoMedicamento.getSituacaoSolicitacaoMedicamento().getId() == null)
            solicitacaoMedicamento.setSituacaoSolicitacaoMedicamento(null);
        if(solicitacaoMedicamento.getCidadaoPaciente() != null && solicitacaoMedicamento.getCidadaoPaciente().getId() == null)
            solicitacaoMedicamento.setCidadaoPaciente(null);
        if(solicitacaoMedicamento.getUsuario() != null && solicitacaoMedicamento.getUsuario().getId() == null)
            solicitacaoMedicamento.setUsuario(null);
        if(solicitacaoMedicamento.getVinculoProfissional() != null && solicitacaoMedicamento.getVinculoProfissional().getId() == null)
            solicitacaoMedicamento.setVinculoProfissional(null);
        
    }
    

    private void atualizarItemSolicitacaoMedicamento(SolicitacaoMedicamentoApac solicitacaoMedicamentoApac, Collection<ItemSolicitacaoMedicamentoApac> listaItemSolicitacaoMedicamentoApac) throws DataIntegrityViolationException, InstantiationException, IllegalAccessException, ClassNotFoundException, NotUniqueIdException
    {
        ItemSolicitacaoMedicamento itemSolicitacaoMedicamentoAux = null;
        SolicitacaoMedicamento solicitacaoMedicamentoClone = new SolicitacaoMedicamento();
        solicitacaoMedicamentoClone = solicitacaoMedicamentoClone.clone(solicitacaoMedicamentoApac);
        
        if(listaItemSolicitacaoMedicamentoApac.size() > 0)
        {
            for (ItemSolicitacaoMedicamentoApac itemSolicitacaoMedicamentoApac : listaItemSolicitacaoMedicamentoApac)
            {   
                
                if(isItemSolicitacaoMedicamentoApac(itemSolicitacaoMedicamentoApac))
                {
                    itemSolicitacaoMedicamentoApac.setSolicitacaoMedicamento(solicitacaoMedicamentoClone);
                    itemSolicitacaoMedicamentoApac.setUsuario(solicitacaoMedicamentoClone.getUsuario());
                    if(itemSolicitacaoMedicamentoApac.getDataCadastro() == null)
                        itemSolicitacaoMedicamentoApac.setDataCadastro(new Date());
                    removeObjetosTrnasientsItemSolicitacaoMedicamentoApac(itemSolicitacaoMedicamentoApac);
                    itemSolicitacaoMedicamentoApacManager.salvar(itemSolicitacaoMedicamentoApac);
                }
                else
                {
                    itemSolicitacaoMedicamentoAux = new ItemSolicitacaoMedicamento();
                    itemSolicitacaoMedicamentoAux = itemSolicitacaoMedicamentoAux.clone(itemSolicitacaoMedicamentoApac);
                    itemSolicitacaoMedicamentoAux.setSolicitacaoMedicamento(solicitacaoMedicamentoClone);
                    itemSolicitacaoMedicamentoAux.setUsuario(solicitacaoMedicamentoClone.getUsuario());
                    if(itemSolicitacaoMedicamentoAux.getDataCadastro() == null)
                        itemSolicitacaoMedicamentoAux.setDataCadastro(new Date());
                    itemSolicitacaoMedicamentoAux.setMedicamentoItemUnidade(itemSolicitacaoMedicamentoApac.getMedicamentoItemUnidade());
                    itemSolicitacaoMedicamentoAux.setViaAdministracaoMedicamento(itemSolicitacaoMedicamentoApac.getViaAdministracaoMedicamento());
                    itemSolicitacaoMedicamentoAux.setTipoFrequencia(itemSolicitacaoMedicamentoAux.getTipoFrequencia());
                    
                    itemSolicitacaoMedicamentoManager.salvar(itemSolicitacaoMedicamentoAux);    
                }
            }
        }
    }


    /**
     * @param itemSolicitacaoMedicamentoApac
     * @return
     */
    private boolean isItemSolicitacaoMedicamentoApac(ItemSolicitacaoMedicamentoApac itemSolicitacaoMedicamentoApac)
    {
        return itemSolicitacaoMedicamentoApac.getMedicamentoItemUnidade().getMedicamento().getTipoPadronizado() != null && 
                itemSolicitacaoMedicamentoApac.getMedicamentoItemUnidade().getMedicamento().getTipoPadronizado() == 0L && 
                itemSolicitacaoMedicamentoApac.isApac();
    }

    private void validarDuplicidadeSolicitacaoMedicamentoMesmoReceituarioPaciente(SolicitacaoMedicamento solicitacaoMedicamento) throws DispensacaoMedicamentoException
    {
        Boolean existeSolicitacaoMedicamentoComMesmoReceituario = false;
        
        existeSolicitacaoMedicamentoComMesmoReceituario = solicitacaoMedicamentoManager.existeSolicitacaoMedicamentoComMesmoReceituario(solicitacaoMedicamento);
        if(existeSolicitacaoMedicamentoComMesmoReceituario)
            throw new DispensacaoMedicamentoException(DispensacaoMedicamentoException.SOLICITACAO_MEDICAMENTO_MESMO_NUMERO_RECEITA_PACIENTE);
    }


    private void validaTipoHemofiliaTipoInibidor(SolicitacaoMedicamentoApac solicitacaoMedicamentoApac)
    {
        if(!(solicitacaoMedicamentoApac.getTipoHemofilia() > 0L))
            solicitacaoMedicamentoApac.setTipoHemofilia(null);
        
        if(!(solicitacaoMedicamentoApac.getTipoInibidor() > 0L))
            solicitacaoMedicamentoApac.setTipoInibidor(null);
    }

    private void adicionaSituacaoReceituarioEntregue(SolicitacaoMedicamentoApac solicitacaoMedicamentoApac)
    {
        SituacaoSolicitacaoMedicamento situacaoSolicitacaoMedicamento = new SituacaoSolicitacaoMedicamento();
        situacaoSolicitacaoMedicamento.setId(1L);
        situacaoSolicitacaoMedicamento.setAtivo(true);
        solicitacaoMedicamentoApac.setSituacaoSolicitacaoMedicamento(situacaoSolicitacaoMedicamento);
    }


    private void salvarListaItemSolicitacaoMedicamento(SolicitacaoMedicamentoApac solicitacaoMedicamentoApac, Collection<ItemSolicitacaoMedicamentoApac> listaItemSolicitacaoMedicamentoApac) throws DataIntegrityViolationException, InstantiationException, IllegalAccessException, ClassNotFoundException, NotUniqueIdException
    {
        ItemSolicitacaoMedicamento itemSolicitacaoMedicamentoAux = null;
        SolicitacaoMedicamento solicitacaoMedicamentoClone = new SolicitacaoMedicamento();
        solicitacaoMedicamentoClone = solicitacaoMedicamentoClone.clone(solicitacaoMedicamentoApac);
        
        if(listaItemSolicitacaoMedicamentoApac.size() > 0)
        {
            for (ItemSolicitacaoMedicamentoApac itemSolicitacaoMedicamentoApac : listaItemSolicitacaoMedicamentoApac)
            {   
                
                if(isItemSolicitacaoMedicamentoApac(itemSolicitacaoMedicamentoApac))
                {
                    itemSolicitacaoMedicamentoApac.setSolicitacaoMedicamento(solicitacaoMedicamentoClone);
                    itemSolicitacaoMedicamentoApac.setUsuario(solicitacaoMedicamentoClone.getUsuario());
                    if(itemSolicitacaoMedicamentoApac.getDataCadastro() == null)
                        itemSolicitacaoMedicamentoApac.setDataCadastro(new Date());
                    removeObjetosTrnasientsItemSolicitacaoMedicamentoApac(itemSolicitacaoMedicamentoApac);
                    itemSolicitacaoMedicamentoApacManager.salvar(itemSolicitacaoMedicamentoApac);
                }
                else
                {
                    itemSolicitacaoMedicamentoAux = new ItemSolicitacaoMedicamento();
                    itemSolicitacaoMedicamentoAux = itemSolicitacaoMedicamentoAux.clone(itemSolicitacaoMedicamentoApac);
                    itemSolicitacaoMedicamentoAux.setSolicitacaoMedicamento(solicitacaoMedicamentoClone);
                    itemSolicitacaoMedicamentoAux.setUsuario(solicitacaoMedicamentoClone.getUsuario());
                    itemSolicitacaoMedicamentoAux.setMedicamentoItemUnidade(itemSolicitacaoMedicamentoApac.getMedicamentoItemUnidade());
                    itemSolicitacaoMedicamentoAux.setViaAdministracaoMedicamento(itemSolicitacaoMedicamentoApac.getViaAdministracaoMedicamento());
                    if(itemSolicitacaoMedicamentoAux.getDataCadastro() == null)
                        itemSolicitacaoMedicamentoAux.setDataCadastro(new Date());
                    itemSolicitacaoMedicamentoAux.setTipoFrequencia(itemSolicitacaoMedicamentoAux.getTipoFrequencia());
                    
                    itemSolicitacaoMedicamentoManager.salvar(itemSolicitacaoMedicamentoAux);    
                }
            }
        }
    }

    private void exclurListaItemSolicitacaoMedicamento(SolicitacaoMedicamentoApac solicitacaoMedicamentoApac)
    {
        Collection<ItemSolicitacaoMedicamento> listaItemSolicitacaoMedicamentosBD =  (Collection<ItemSolicitacaoMedicamento>) itemSolicitacaoMedicamentoManager.get(getProjecaoFilterItemSolicitacaoMedicamento(solicitacaoMedicamentoApac));
        
        flush();
        
        for (ItemSolicitacaoMedicamento itemSolicitacaoMedicamento : listaItemSolicitacaoMedicamentosBD)
        {
            ItemSolicitacaoMedicamentoApac itemSolicitacaoMedicamentoApacBD;
            itemSolicitacaoMedicamentoApacBD = itemSolicitacaoMedicamentoApacManager.getUnique(getProjecaoFilterItemSolicitacaoMedicamentoApac(itemSolicitacaoMedicamento));
            if(itemSolicitacaoMedicamentoApacBD != null && itemSolicitacaoMedicamentoApacBD.getId() != null)
            {
                itemSolicitacaoMedicamentoApacBD.setSolicitacaoMedicamento(getSolicitacaoMedicamentoClone(solicitacaoMedicamentoApac));
                removeObjetosTrnasientsItemSolicitacaoMedicamentoApac(itemSolicitacaoMedicamentoApacBD);
                itemSolicitacaoMedicamentoApacManager.delete(itemSolicitacaoMedicamentoApacBD,true);
                itemSolicitacaoMedicamento.setSolicitacaoMedicamento(getSolicitacaoMedicamentoClone(solicitacaoMedicamentoApac));
                itemSolicitacaoMedicamentoManager.delete(itemSolicitacaoMedicamento, true);
            }
            else
            {
                itemSolicitacaoMedicamento.setSolicitacaoMedicamento(getSolicitacaoMedicamentoClone(solicitacaoMedicamentoApac));
                itemSolicitacaoMedicamentoManager.delete(itemSolicitacaoMedicamento, true);
            }
        }   
    }


    private SolicitacaoMedicamento getSolicitacaoMedicamentoClone(SolicitacaoMedicamentoApac solicitacaoMedicamentoApac)
    {
        SolicitacaoMedicamento solicitacaoMedicamento = new SolicitacaoMedicamento();
        solicitacaoMedicamento = solicitacaoMedicamento.clone(solicitacaoMedicamentoApac);
        return solicitacaoMedicamento;
    }
    
    @Override
    public SolicitacaoMedicamentoApac salvar(SolicitacaoMedicamentoApac solicitacaoMedicamentoApac) throws DispensacaoMedicamentoException, DataIntegrityViolationException,
            NotUniqueIdException, InstantiationException, IllegalAccessException, ClassNotFoundException
    {
        return save(solicitacaoMedicamentoApac, true);
    }

    @Override
    public void atualizar(SolicitacaoMedicamentoApac solicitacaoMedicamentoApac) throws DispensacaoMedicamentoException, DataIntegrityViolationException, NotUniqueIdException, InstantiationException,
            IllegalAccessException, ClassNotFoundException
    {
        update(solicitacaoMedicamentoApac, true);
    }

    @Override
    public void excluir(SolicitacaoMedicamentoApac solicitacaoMedicamentoApac) throws DispensacaoMedicamentoException, DataIntegrityViolationException, NotUniqueIdException, InstantiationException,
            IllegalAccessException, ClassNotFoundException
    {
        delete(solicitacaoMedicamentoApac, true);
    }

    @Override
    public SolicitacaoMedicamentoApac buscarSolicitacaoMedicamentoApacPorId(SolicitacaoMedicamentoApac solicitacaoMedicamentoApac)
    {
        SolicitacaoMedicamentoApac solicitacaoMedicamentoApacBD = getUnique(getProjecaoFilterSolicitacaoMedicamentoApac(solicitacaoMedicamentoApac));
        
        if(solicitacaoMedicamentoApacBD != null && solicitacaoMedicamentoApacBD.getId() != null)
        {
            solicitacaoMedicamentoApacBD.setListaItemSolicitacaoMedicamentosApac(retornarListaItemSolicitacaoMedicamentosApac(solicitacaoMedicamentoApac));
            return solicitacaoMedicamentoApacBD;
        }
        else
        {
            SolicitacaoMedicamento solicitacaoMedicamentoBD = solicitacaoMedicamentoManager.getUnique(getProjecaoFilterSolicitacaoMedicamento(solicitacaoMedicamentoApac));
            solicitacaoMedicamentoApacBD = new SolicitacaoMedicamentoApac();
            SolicitacaoMedicamentoApac solicitacaoMedicamentoApacAux = solicitacaoMedicamentoApac.clone(solicitacaoMedicamentoBD);
            solicitacaoMedicamentoApacAux.setListaItemSolicitacaoMedicamentosApac(retornarListaItemSolicitacaoMedicamentosApac(solicitacaoMedicamentoApac));
            return solicitacaoMedicamentoApacAux;
        }
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public Collection<ItemSolicitacaoMedicamentoApac> retornarListaItemSolicitacaoMedicamentosApac(SolicitacaoMedicamentoApac solicitacaoMedicamentoApac)
    {
        Collection<ItemSolicitacaoMedicamento> listaItemSolicitacaoMedicamentosBD =  (Collection<ItemSolicitacaoMedicamento>) itemSolicitacaoMedicamentoManager.get(getProjecaoFilterItemSolicitacaoMedicamento(solicitacaoMedicamentoApac));
        
        Collection<ItemSolicitacaoMedicamentoApac> listaItemSolicitacaoMedicamentosApac = new ArrayCollection();
        
        for (ItemSolicitacaoMedicamento itemSolicitacaoMedicamento : listaItemSolicitacaoMedicamentosBD)
        {
            ItemSolicitacaoMedicamentoApac itemSolicitacaoMedicamentoApac;
            itemSolicitacaoMedicamentoApac = itemSolicitacaoMedicamentoApacManager.getUnique(getProjecaoFilterItemSolicitacaoMedicamentoApac(itemSolicitacaoMedicamento));
            if(itemSolicitacaoMedicamentoApac != null && itemSolicitacaoMedicamentoApac.getId() != null)
                listaItemSolicitacaoMedicamentosApac.add(itemSolicitacaoMedicamentoApac);
            else
            {
                itemSolicitacaoMedicamentoApac = new ItemSolicitacaoMedicamentoApac();
                listaItemSolicitacaoMedicamentosApac.add(itemSolicitacaoMedicamentoApac.clone(itemSolicitacaoMedicamento));
            }
        }        
        
        return listaItemSolicitacaoMedicamentosApac;
    }


    private ProjectionFilter[] getProjecaoFilterItemSolicitacaoMedicamento(SolicitacaoMedicamento solicitacaoMedicamento)
    {
        ArrayList<ProjectionFilter> listaProjection = new ArrayList<ProjectionFilter>();

        listaProjection.addAll(getArrayItemSolicitacaoMedicamento(solicitacaoMedicamento));
                
        ProjectionFilter[] projectionFilters = new ProjectionFilter[listaProjection.size()];

        listaProjection.toArray(projectionFilters);
        
        return projectionFilters;
    }


    private ProjectionFilter[] getProjecaoFilterItemSolicitacaoMedicamentoApac(ItemSolicitacaoMedicamento itemSolicitacaoMedicamento)
    {
        ArrayList<ProjectionFilter> listaProjection = new ArrayList<ProjectionFilter>();
        
        listaProjection.addAll(getArrayItemSolicitacaoMedicamentoPorIdItemSolicitacaoMedicamento(itemSolicitacaoMedicamento));
        
        listaProjection.add(new ProjectionFilter("procedimentoCidPrincipal.id"));
        listaProjection.add(new ProjectionFilter("procedimentoCidPrincipal.procedimento.id", JoinFragment.LEFT_OUTER_JOIN));
        listaProjection.add(new ProjectionFilter("procedimentoCidPrincipal.procedimento.descricaoProcedimento"));
        listaProjection.add(new ProjectionFilter("procedimentoCidPrincipal.procedimento.procedimentoVigente.id", JoinFragment.LEFT_OUTER_JOIN));
        listaProjection.add(new ProjectionFilter("procedimentoCidPrincipal.procedimento.procedimentoVigente.nomeProcedimento"));
        listaProjection.add(new ProjectionFilter("procedimentoCidPrincipal.procedimento.procedimentoVigente.flagHabilitado"));
        listaProjection.add(new ProjectionFilter("procedimentoCidPrincipal.cid.id", JoinFragment.LEFT_OUTER_JOIN));
        listaProjection.add(new ProjectionFilter("procedimentoCidPrincipal.cid.codigoCid"));
        listaProjection.add(new ProjectionFilter("procedimentoCidPrincipal.cid.descricaoCid"));
        
        listaProjection.add(new ProjectionFilter("procedimentoCidSecundario.id", JoinFragment.LEFT_OUTER_JOIN));
        listaProjection.add(new ProjectionFilter("procedimentoCidSecundario.procedimento.id", JoinFragment.LEFT_OUTER_JOIN));
        listaProjection.add(new ProjectionFilter("procedimentoCidSecundario.procedimento.descricaoProcedimento"));
        listaProjection.add(new ProjectionFilter("procedimentoCidSecundario.procedimento.procedimentoVigente.id", JoinFragment.LEFT_OUTER_JOIN));
        listaProjection.add(new ProjectionFilter("procedimentoCidSecundario.procedimento.procedimentoVigente.nomeProcedimento"));
        listaProjection.add(new ProjectionFilter("procedimentoCidSecundario.procedimento.procedimentoVigente.flagHabilitado"));
        listaProjection.add(new ProjectionFilter("procedimentoCidPrincipal.cid.id", JoinFragment.LEFT_OUTER_JOIN));
        listaProjection.add(new ProjectionFilter("procedimentoCidPrincipal.cid.codigoCid"));
        listaProjection.add(new ProjectionFilter("procedimentoCidPrincipal.cid.descricaoCid"));
        
        listaProjection.add(new ProjectionFilter("quantidadeSolicitadaMes1"));
        listaProjection.add(new ProjectionFilter("quantidadeSolicitadaMes2"));
        listaProjection.add(new ProjectionFilter("quantidadeSolicitadaMes3"));
        
        ProjectionFilter[] projectionFilters = new ProjectionFilter[listaProjection.size()];

        listaProjection.toArray(projectionFilters);
        
        return projectionFilters;
    }


    private Collection<? extends ProjectionFilter> getArrayItemSolicitacaoMedicamentoPorIdItemSolicitacaoMedicamento(ItemSolicitacaoMedicamento itemSolicitacaoMedicamento)
    {
        ArrayList<ProjectionFilter> listaProjection = new ArrayList<ProjectionFilter>();
        
        listaProjection.add(new ProjectionFilter("id", itemSolicitacaoMedicamento.getId()));
        listaProjection.add(new ProjectionFilter("solicitacaoMedicamento.id"));
        listaProjection.add(new ProjectionFilter("apac"));
        listaProjection.add(new ProjectionFilter("dataCadastro"));
        listaProjection.add(new ProjectionFilter("quantidadeSolicitadaTotal"));
        listaProjection.add(new ProjectionFilter("usuario.id"));
        listaProjection.add(new ProjectionFilter("medicamentoItemUnidade.id"));
        listaProjection.add(new ProjectionFilter("medicamentoItemUnidade.descricaoSiglaItem" ));
        listaProjection.add(new ProjectionFilter("medicamentoItemUnidade.descricaoSiglaUnidade" ));
        listaProjection.add(new ProjectionFilter("medicamentoItemUnidade.descricaoItemUnidade" ));
        
        listaProjection.add(new ProjectionFilter("medicamentoItemUnidade.medicamento.id" ));
        listaProjection.add(new ProjectionFilter("medicamentoItemUnidade.medicamento.descricao" ));
        listaProjection.add(new ProjectionFilter("medicamentoItemUnidade.medicamento.numeroDoseDiariaDefinida"));
        listaProjection.add(new ProjectionFilter("medicamentoItemUnidade.medicamento.tipoPadronizado"));
        listaProjection.add(new ProjectionFilter("medicamentoItemUnidade.medicamento.procedimentoVigente.id", JoinFragment.LEFT_OUTER_JOIN));
        listaProjection.add(new ProjectionFilter("medicamentoItemUnidade.medicamento.procedimentoVigente.codigoProcedimento"));
        listaProjection.add(new ProjectionFilter("medicamentoItemUnidade.medicamento.procedimentoVigente.nomeProcedimento"));
        listaProjection.add(new ProjectionFilter("medicamentoItemUnidade.medicamento.ativo"));
        
        listaProjection.add(new ProjectionFilter("tipoFrequencia.id"));
        listaProjection.add(new ProjectionFilter("tipoFrequencia.descricaoFrequencia"));
        listaProjection.add(new ProjectionFilter("tipoFrequencia.impressaoReceita"));
        listaProjection.add(new ProjectionFilter("tipoFrequencia.periodicidade"));
        listaProjection.add(new ProjectionFilter("tipoFrequencia.horarioInicial"));

        listaProjection.add(new ProjectionFilter("tratamentoContinuo"));
        listaProjection.add(new ProjectionFilter("quantidadePeriodoTratamento"));
        listaProjection.add(new ProjectionFilter("tipoPeriodoTratamento"));
        
        listaProjection.add(new ProjectionFilter("viaAdministracaoMedicamento.id"));
        listaProjection.add(new ProjectionFilter("cidNaoPadronizado"));
        listaProjection.add(new ProjectionFilter("orientacao"));
        listaProjection.add(new ProjectionFilter("quantidadeDose"));
        
        return listaProjection;
    }


    private ArrayList<ProjectionFilter> getArrayItemSolicitacaoMedicamento(SolicitacaoMedicamento solicitacaoMedicamento)
    {
        
        ArrayList<ProjectionFilter> listaProjection = new ArrayList<ProjectionFilter>();
        
        listaProjection.add(new ProjectionFilter("id"));
        listaProjection.add(new ProjectionFilter("solicitacaoMedicamento.id", solicitacaoMedicamento.getId()));
        listaProjection.add(new ProjectionFilter("apac"));
        listaProjection.add(new ProjectionFilter("dataCadastro"));
        listaProjection.add(new ProjectionFilter("quantidadeSolicitadaTotal"));
        listaProjection.add(new ProjectionFilter("usuario.id"));
        listaProjection.add(new ProjectionFilter("medicamentoItemUnidade.id"));
        listaProjection.add(new ProjectionFilter("medicamentoItemUnidade.descricaoSiglaItem" ));
        listaProjection.add(new ProjectionFilter("medicamentoItemUnidade.descricaoSiglaUnidade" ));
        listaProjection.add(new ProjectionFilter("medicamentoItemUnidade.descricaoItemUnidade" ));
        
        listaProjection.add(new ProjectionFilter("medicamentoItemUnidade.medicamento.id" ));
        listaProjection.add(new ProjectionFilter("medicamentoItemUnidade.medicamento.descricao" ));
        listaProjection.add(new ProjectionFilter("medicamentoItemUnidade.medicamento.numeroDoseDiariaDefinida"));
        listaProjection.add(new ProjectionFilter("medicamentoItemUnidade.medicamento.tipoPadronizado"));
        listaProjection.add(new ProjectionFilter("medicamentoItemUnidade.medicamento.procedimentoVigente.id", JoinFragment.LEFT_OUTER_JOIN));
        listaProjection.add(new ProjectionFilter("medicamentoItemUnidade.medicamento.procedimentoVigente.codigoProcedimento"));
        listaProjection.add(new ProjectionFilter("medicamentoItemUnidade.medicamento.procedimentoVigente.nomeProcedimento"));
        listaProjection.add(new ProjectionFilter("medicamentoItemUnidade.medicamento.ativo"));
        
        listaProjection.add(new ProjectionFilter("tipoFrequencia.id"));
        listaProjection.add(new ProjectionFilter("tipoFrequencia.descricaoFrequencia"));
        listaProjection.add(new ProjectionFilter("tipoFrequencia.impressaoReceita"));
        listaProjection.add(new ProjectionFilter("tipoFrequencia.periodicidade"));
        listaProjection.add(new ProjectionFilter("tipoFrequencia.horarioInicial"));

        listaProjection.add(new ProjectionFilter("tratamentoContinuo"));
        listaProjection.add(new ProjectionFilter("quantidadePeriodoTratamento"));
        listaProjection.add(new ProjectionFilter("tipoPeriodoTratamento"));
        
        listaProjection.add(new ProjectionFilter("viaAdministracaoMedicamento.id"));
        listaProjection.add(new ProjectionFilter("cidNaoPadronizado"));
        listaProjection.add(new ProjectionFilter("orientacao"));
        listaProjection.add(new ProjectionFilter("quantidadeDose"));
        
        return listaProjection;
    }


    private ProjectionFilter[] getProjecaoFilterSolicitacaoMedicamento(SolicitacaoMedicamento solicitacaoMedicamento)
    {
        ArrayList<ProjectionFilter> listaProjection = new ArrayList<ProjectionFilter>();
       
        if(solicitacaoMedicamento != null && solicitacaoMedicamento.getId() != null)
            listaProjection.add(new ProjectionFilter("id", solicitacaoMedicamento.getId()));

        listaProjection.add(new ProjectionFilter("situacaoSolicitacaoMedicamento.id"));
        listaProjection.add(new ProjectionFilter("cidadaoPaciente.id"));
        listaProjection.add(new ProjectionFilter("cidadaoPaciente.nome"));
        listaProjection.add(new ProjectionFilter("cidadaoPaciente.cpf"));
        listaProjection.add(new ProjectionFilter("cidadaoPaciente.nascimento"));
        listaProjection.add(new ProjectionFilter("cidadaoPaciente.sexo"));
        listaProjection.add(new ProjectionFilter("cidadaoPaciente.nomeMae"));
        listaProjection.add(new ProjectionFilter("codigoReceita"));
        listaProjection.add(new ProjectionFilter("vinculoProfissional.id"));
        listaProjection.add(new ProjectionFilter("vinculoProfissional.registroConselho"));
        listaProjection.add(new ProjectionFilter("vinculoProfissional.estabelecimento.id"));
        listaProjection.add(new ProjectionFilter("vinculoProfissional.estabelecimento.nomeFantasia"));
        listaProjection.add(new ProjectionFilter("vinculoProfissional.uf.id"));
        listaProjection.add(new ProjectionFilter("vinculoProfissional.uf.descricaoUfSucinta"));
        listaProjection.add(new ProjectionFilter("vinculoProfissional.profissionalSigas.id"));
        listaProjection.add(new ProjectionFilter("vinculoProfissional.profissionalSigas.cidadao.id"));
        listaProjection.add(new ProjectionFilter("vinculoProfissional.profissionalSigas.cidadao.nome"));
        listaProjection.add(new ProjectionFilter("dataProximaConsulta"));
        listaProjection.add(new ProjectionFilter("farmacia.id", JoinFragment.LEFT_OUTER_JOIN));
        listaProjection.add(new ProjectionFilter("farmacia.descricaoFarmacia"));
        listaProjection.add(new ProjectionFilter("prescricaoMedica"));
        listaProjection.add(new ProjectionFilter("dataCadastro"));
        listaProjection.add(new ProjectionFilter("versao"));
        listaProjection.add(new ProjectionFilter("apac"));
        
        ProjectionFilter[] projectionFilters = new ProjectionFilter[listaProjection.size()];

        listaProjection.toArray(projectionFilters);
        
        return projectionFilters;
    }
    
    private ProjectionFilter[] getProjecaoFilterSolicitacaoMedicamentoApac(SolicitacaoMedicamentoApac solicitacaoMedicamentoApac)
    {
        ArrayList<ProjectionFilter> listaProjection = new ArrayList<ProjectionFilter>();
        
        listaProjection.addAll(getArrayProjectionFilterSolicitacaoMedicamento(solicitacaoMedicamentoApac));
        
        listaProjection.add(new ProjectionFilter("numeroApac"));
        listaProjection.add(new ProjectionFilter("dataInicio"));
        listaProjection.add(new ProjectionFilter("dataFim"));
        listaProjection.add(new ProjectionFilter("tipoHemofilia"));
        listaProjection.add(new ProjectionFilter("tipoInibidor"));
        listaProjection.add(new ProjectionFilter("profissionalAutorizador.id", JoinFragment.LEFT_OUTER_JOIN));
        listaProjection.add(new ProjectionFilter("profissionalExecutante.id", JoinFragment.LEFT_OUTER_JOIN));
        listaProjection.add(new ProjectionFilter("numeroPeso"));
        listaProjection.add(new ProjectionFilter("numeroAltura"));
        listaProjection.add(new ProjectionFilter("transplante"));
        listaProjection.add(new ProjectionFilter("quantidadeTransplante"));
        listaProjection.add(new ProjectionFilter("gestante"));
        listaProjection.add(new ProjectionFilter("procedimentoCidPrincipal.id", JoinFragment.LEFT_OUTER_JOIN));
        listaProjection.add(new ProjectionFilter("procedimentoCidPrincipal.procedimento.id", JoinFragment.LEFT_OUTER_JOIN));
        listaProjection.add(new ProjectionFilter("procedimentoCidPrincipal.procedimento.descricaoProcedimento"));
        listaProjection.add(new ProjectionFilter("procedimentoCidPrincipal.procedimento.procedimentoVigente.id", JoinFragment.LEFT_OUTER_JOIN));
        listaProjection.add(new ProjectionFilter("procedimentoCidPrincipal.procedimento.procedimentoVigente.nomeProcedimento"));
        listaProjection.add(new ProjectionFilter("procedimentoCidPrincipal.procedimento.procedimentoVigente.flagHabilitado"));
        listaProjection.add(new ProjectionFilter("procedimentoCidPrincipal.cid.id", JoinFragment.LEFT_OUTER_JOIN));
        listaProjection.add(new ProjectionFilter("procedimentoCidPrincipal.cid.codigoCid"));
        listaProjection.add(new ProjectionFilter("procedimentoCidPrincipal.cid.descricaoCid"));
        
        listaProjection.add(new ProjectionFilter("procedimentoCidSecundario.id", JoinFragment.LEFT_OUTER_JOIN));
        listaProjection.add(new ProjectionFilter("procedimentoCidSecundario.procedimento.id", JoinFragment.LEFT_OUTER_JOIN));
        listaProjection.add(new ProjectionFilter("procedimentoCidSecundario.procedimento.descricaoProcedimento"));
        listaProjection.add(new ProjectionFilter("procedimentoCidSecundario.procedimento.procedimentoVigente.id", JoinFragment.LEFT_OUTER_JOIN));
        listaProjection.add(new ProjectionFilter("procedimentoCidSecundario.procedimento.procedimentoVigente.nomeProcedimento"));
        listaProjection.add(new ProjectionFilter("procedimentoCidSecundario.procedimento.procedimentoVigente.flagHabilitado"));
        listaProjection.add(new ProjectionFilter("procedimentoCidPrincipal.cid.id", JoinFragment.LEFT_OUTER_JOIN));
        listaProjection.add(new ProjectionFilter("procedimentoCidPrincipal.cid.codigoCid"));
        listaProjection.add(new ProjectionFilter("procedimentoCidPrincipal.cid.descricaoCid"));
        
        listaProjection.add(new ProjectionFilter("observacao"));
        listaProjection.add(new ProjectionFilter("dataAutorizacao"));
        listaProjection.add(new ProjectionFilter("dataInicioTratamento"));
        listaProjection.add(new ProjectionFilter("solicitacaoMedicamentoAnteriorApac.id", JoinFragment.LEFT_OUTER_JOIN));
        listaProjection.add(new ProjectionFilter("renovada"));
        
        ProjectionFilter[] projectionFilters = new ProjectionFilter[listaProjection.size()];

        listaProjection.toArray(projectionFilters);
        
        return projectionFilters;
    }
    
    private ArrayList<ProjectionFilter> getArrayProjectionFilterSolicitacaoMedicamento(SolicitacaoMedicamento solicitacaoMedicamento)
    {
        ArrayList<ProjectionFilter> listaProjection = new ArrayList<ProjectionFilter>();
       
        listaProjection.add(new ProjectionFilter("id", solicitacaoMedicamento.getId()));

        listaProjection.add(new ProjectionFilter("situacaoSolicitacaoMedicamento.id"));
        listaProjection.add(new ProjectionFilter("cidadaoPaciente.id"));
        listaProjection.add(new ProjectionFilter("cidadaoPaciente.nome"));
        listaProjection.add(new ProjectionFilter("cidadaoPaciente.cpf"));
        listaProjection.add(new ProjectionFilter("cidadaoPaciente.nascimento"));
        listaProjection.add(new ProjectionFilter("cidadaoPaciente.sexo"));
        listaProjection.add(new ProjectionFilter("cidadaoPaciente.nomeMae"));
        listaProjection.add(new ProjectionFilter("codigoReceita"));
        listaProjection.add(new ProjectionFilter("vinculoProfissional.id"));
        listaProjection.add(new ProjectionFilter("vinculoProfissional.registroConselho"));
        listaProjection.add(new ProjectionFilter("vinculoProfissional.estabelecimento.id"));
        listaProjection.add(new ProjectionFilter("vinculoProfissional.estabelecimento.nomeFantasia"));
        listaProjection.add(new ProjectionFilter("vinculoProfissional.uf.id"));
        listaProjection.add(new ProjectionFilter("vinculoProfissional.uf.descricaoUfSucinta"));
        listaProjection.add(new ProjectionFilter("vinculoProfissional.profissionalSigas.id"));
        listaProjection.add(new ProjectionFilter("vinculoProfissional.profissionalSigas.cidadao.id"));
        listaProjection.add(new ProjectionFilter("vinculoProfissional.profissionalSigas.cidadao.nome"));
        
        listaProjection.add(new ProjectionFilter("dataProximaConsulta"));
        listaProjection.add(new ProjectionFilter("farmacia.id", JoinFragment.LEFT_OUTER_JOIN));
        listaProjection.add(new ProjectionFilter("prescricaoMedica"));
        listaProjection.add(new ProjectionFilter("dataCadastro"));
        listaProjection.add(new ProjectionFilter("versao"));
        listaProjection.add(new ProjectionFilter("apac"));
        
        return listaProjection;
    }
    
    @SuppressWarnings("serial")
    private SolicitacaoMedicamentoApac getOrmSolicitacaoMedicamentoApacDto(final SolicitacaoMedicamentoApacDTO dto)
    {
        SolicitacaoMedicamentoApac solicitacaoMedicamentoApac = new SolicitacaoMedicamentoApac();

        solicitacaoMedicamentoApac.setId(dto.getId());
        solicitacaoMedicamentoApac.setNumeroApac(dto.getNumeroApac());

        // Vínculo Profissional SIGAS
        solicitacaoMedicamentoApac.setProfissionalAutorizador(new VinculoProfissionalSigas() {{
            setId(dto.getIdVinculoProfissional());
            setProfissionalSigas(new ProfissionalSigas());
            getProfissionalSigas().setId(dto.getIdProfissional());
            setEstabelecimento(new Estabelecimento());
            getEstabelecimento().setId(dto.getIdEstabelecimento());
            getEstabelecimento().setNomeFantasia(dto.getNomeFantasiaEstab());
        }});

        // Cidadão
        solicitacaoMedicamentoApac.setCidadaoPaciente(new Cidadao() {{
            setId(dto.getIdCidadaoPaciente());
            setNome(dto.getNomeCidadao());
            setNascimento(dto.getNascimentoCidadao());
            setSexo(dto.getSexoCidadao());
        }});

        // Procedimento CID Principal
        solicitacaoMedicamentoApac.setProcedimentoCidPrincipal(new ProcedimentoCid() {{
            setId(dto.getIdProcCidPrincipal());
            setCid(new Cid());
            getCid().setId(dto.getIdCidPrincipal());
            getCid().setDescricaoCid(dto.getDescricaoCidPrin());
            getCid().setCidApac(dto.getCidApacPrincipal());
            setProcedimento(new Procedimento());
            getProcedimento().setId(dto.getIdProcePrincipal());
            getProcedimento().setNomeProcedimento(dto.getDescProcedimentoPrin());            
        }});

        // Procedimento CID Secundario
        solicitacaoMedicamentoApac.setProcedimentoCidSecundario(new ProcedimentoCid() {{
            setId(dto.getIdProcCidSecundario());
            setCid(new Cid());
            getCid().setId(dto.getIdCidSecundario());
            getCid().setDescricaoCid(dto.getDescricaoCidSecun());
            getCid().setCidApac(dto.getCidApacSecundario());
            setProcedimento(new Procedimento());
            getProcedimento().setId(dto.getIdProceSecundario());
            getProcedimento().setNomeProcedimento(dto.getDescProcedimentoSecun());
        }});

        solicitacaoMedicamentoApac.setDataInicio(dto.getDataInicio());
        solicitacaoMedicamentoApac.setDataFim(dto.getDataFim());

        return solicitacaoMedicamentoApac;
    }


    @Override
    public void excluirSolicitacaoMedicamentoComHistorico(SolicitacaoMedicamentoApac solicitacaoMedicamentoApac) throws NotUniqueIdException, InstantiationException, IllegalAccessException, ClassNotFoundException, LocalizedException
    {
        SolicitacaoMedicamento solicitacaoMedicamentoBD = solicitacaoMedicamentoManager.getUnique(getProjecaoFilterSolicitacaoMedicamento(solicitacaoMedicamentoApac));
        
        if(validarSituacaoSolicitacaoMedicamentoApac(solicitacaoMedicamentoApac))
        {
            solicitacaoMedicamentoManager.excluirHitoricoSolicitacaoMedicamento(solicitacaoMedicamentoBD);
            
            if(solicitacaoMedicamentoBD.isApac() == true)
            {
                SolicitacaoMedicamentoApac solicitacaoMedicamentoApacBD = buscarSolicitacaoMedicamentoApacPorId(solicitacaoMedicamentoApac);
                removeObjetosTrnasientsSolicitacaoMedicamentoApac(solicitacaoMedicamentoApacBD);
                exclurListaItemSolicitacaoMedicamento(solicitacaoMedicamentoApacBD);
                excluir(solicitacaoMedicamentoApacBD);
              
            }
            else 
            {
                solicitacaoMedicamentoApac = solicitacaoMedicamentoApac.clone(solicitacaoMedicamentoBD);
                exclurListaItemSolicitacaoMedicamento(solicitacaoMedicamentoApac);
                removeObjetosTrnasientsSolicitacaoMedicamento(solicitacaoMedicamentoBD);
                solicitacaoMedicamentoManager.excluir(solicitacaoMedicamentoBD);
            }
        }
        
    }


    private boolean validarSituacaoSolicitacaoMedicamentoApac(SolicitacaoMedicamento solicitacaoMedicamento)
    {
        if(solicitacaoMedicamento != null && solicitacaoMedicamento.getSituacaoSolicitacaoMedicamento() != null &&
           solicitacaoMedicamento.getSituacaoSolicitacaoMedicamento().getId() != EnumSituacaoSolicitacaoMedicamento.RECEITUARIO_ENTREGUE.getId())
        {
            throw new LocalizedException(DispensacaoMedicamentoRBi.BASENAME, DispensacaoMedicamentoRBi.EXCLUIR_SOLICITACAO_MEDICAMENTO_APAC);
        }
        return true;
    }


    @Override
    public Boolean verificarItemMedicamentoUnidadeTipoApac(ProcedimentoVigente procedimentoVigente)
    {
        return procedimentoManager.verificarItemMedicamentoUnidadeTipoApac(procedimentoVigente);
    }
}
