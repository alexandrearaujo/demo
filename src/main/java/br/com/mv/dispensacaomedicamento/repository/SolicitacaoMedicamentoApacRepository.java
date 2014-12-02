package br.com.mv.dispensacaomedicamento.repository;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collection;

import org.hibernate.SQLQuery;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.dao.DataIntegrityViolationException;

import br.com.mv.commons.web.dao.GenericDao;
import br.com.mv.commons.web.exception.NotUniqueIdException;
import br.com.mv.regulacao.dispensacaomedicamento.exception.DispensacaoMedicamentoException;
import br.com.mv.regulacao.dispensacaomedicamento.model.ItemSolicitacaoMedicamentoApac;
import br.com.mv.regulacao.dispensacaomedicamento.model.ProcedimentoCidDTO;
import br.com.mv.regulacao.dispensacaomedicamento.model.SolicitacaoMedicamentoApac;
import br.com.mv.regulacao.dispensacaomedicamento.model.SolicitacaoMedicamentoApacDTO;
import br.com.mv.regulacao.model.geral.ProcedimentoVigente;

/**
 * 
 * @author carlos.souza
 *
 */
public interface SolicitacaoMedicamentoApacRepository extends GenericDao<SolicitacaoMedicamentoApac>
{
    
    @Override
    @SuppressWarnings("unchecked")
    public Collection<ProcedimentoCidDTO> listarProcedimentoCidDTOPorDescricao(String descricao, ProcedimentoVigente procedimentoVigente, ProcedimentoCidDTO procedimentoCidDTO, boolean isCidPadronizado)
    {
        StringBuilder sql = new StringBuilder();
        
        criaConsultaSQL(sql, procedimentoCidDTO, isCidPadronizado);
        
        SQLQuery query = getSession().createSQLQuery(sql.toString());
        query.setResultTransformer(new AliasToBeanResultTransformer(ProcedimentoCidDTO.class));
        setParametrosConsultaProcedimentoCidDTO(query, descricao, procedimentoVigente, procedimentoCidDTO, isCidPadronizado);
        
        return query.list();
    }

    private void setParametrosConsultaProcedimentoCidDTO(SQLQuery query, String descricao, ProcedimentoVigente procedimentoVigente, ProcedimentoCidDTO procedimentoCidDTO, boolean isCidPadronizado)
    {
        if(procedimentoVigente != null && procedimentoVigente.getCodigoProcedimento() != null)
            query.setParameter("idProcedimentoVigente", procedimentoVigente.getId());
        
        if(procedimentoCidDTO != null && procedimentoCidDTO.getCodigoCid() != null)
            query.setParameter("cidPrincipal", procedimentoCidDTO.getCodigoCid());
        
        if(descricao != null && !descricao.isEmpty())
        {
            query.setParameter("cdCid", "%"+descricao.concat("%"));
            query.setParameter("descricaoCid", "%"+descricao.concat("%"));
        }
    }

    private void criaConsultaSQL(StringBuilder sql, ProcedimentoCidDTO procedimentoCidDTO,boolean isCidPadronizado)
    {
        if((procedimentoCidDTO != null && (procedimentoCidDTO.getCid() != null || procedimentoCidDTO.getCodigoCid() != null)) || isCidPadronizado)
            consultarProcedimentoCidDTOPadronizado(sql, procedimentoCidDTO);
        else
            consultarProcedimentoCidDTONaoPadronizado(sql);
    }

    private void consultarProcedimentoCidDTOPadronizado(StringBuilder sql, ProcedimentoCidDTO procedimentoCidDTO )
    {
        sql.append("SELECT ");
        sql.append("PCID.CD_PROCEDIMENTO_CID   AS \"codigoProcedimentoCid\", ");
        sql.append("CID.ID_CID                 AS \"codigoCid\", ");
        sql.append("CID.CID                    AS \"cid\", ");
        sql.append("CID.DESCR_CID              AS \"descricaoCid\" ");
        sql.append("FROM DBAMVFOR.CID CID ");
        sql.append("JOIN DBAMVFOR.PROCEDIMENTO_CID PCID ON CID.ID_CID = PCID.CD_CID ");
        sql.append("JOIN DBAMVFOR.PROCEDIMENTO PRO ON PCID.CD_PROCEDIMENTO = PRO.CD_PROCEDIMENTO ");
        sql.append("JOIN DBAMVFOR.PROCEDIMENTO_VIGENTE PVIG ON PRO.CD_PROCEDIMENTO_VIGENTE = PVIG.CD_PROCEDIMENTO_VIGENTE ");
        sql.append("WHERE PVIG.CD_PROCEDIMENTO_VIGENTE =  :idProcedimentoVigente ");
        sql.append("AND PRO.DT_COMPETENCIA = (select max(dt_competencia) from dbamvfor.procedimento_cid) ");
        if(procedimentoCidDTO != null && procedimentoCidDTO.getCodigoCid() != null)
        {
            sql.append("aND PCID.SN_PRINCIPAL = 1 ");
            sql.append("AND CID.ID_CID NOT IN (:cidPrincipal) ");
        }
        else
            sql.append("aND PCID.SN_PRINCIPAL = 1 ");
        
        sql.append("AND PCID.SN_HABILITADO = 1 ");
        sql.append("AND (CID.CID LIKE :cdCid or ");
        sql.append("CID.DESCR_CID LIKE :descricaoCid) ");
        
    }

    private void consultarProcedimentoCidDTONaoPadronizado(StringBuilder sql)
    {
        sql.append("SELECT ");
        sql.append("CI.ID_CID                 AS \"codigoCid\", ");
        sql.append("CI.CID                    AS \"cid\", ");
        sql.append("CI.DESCR_CID              AS \"descricaoCid\" ");
        sql.append("FROM DBAMVFOR.CID CI ");
        sql.append("WHERE NOT EXISTS ( ");
        sql.append("SELECT CID.ID_CID ");
        sql.append("FROM DBAMVFOR.CID CID ");
        sql.append("JOIN DBAMVFOR.PROCEDIMENTO_CID PCID ON CID.ID_CID = PCID.CD_CID ");
        sql.append("JOIN DBAMVFOR.PROCEDIMENTO PRO ON PCID.CD_PROCEDIMENTO = PRO.CD_PROCEDIMENTO ");
        sql.append("JOIN DBAMVFOR.PROCEDIMENTO_VIGENTE PVIG ON PRO.CD_PROCEDIMENTO_VIGENTE = PVIG.CD_PROCEDIMENTO_VIGENTE ");
        sql.append("WHERE PVIG.CD_PROCEDIMENTO_VIGENTE = :idProcedimentoVigente ");
        sql.append("AND PRO.DT_COMPETENCIA = (select max(dt_competencia) from dbamvfor.procedimento_cid) ");
        sql.append("AND PCID.SN_PRINCIPAL = 1 ");
        sql.append("AND PCID.SN_HABILITADO = 1 ");
        sql.append("AND (CID.CID LIKE :cdCid OR ");
        sql.append("CID.DESCR_CID LIKE :descricaoCid) ");
        sql.append("AND CID.ID_CID = CI.ID_CID ) ");
        sql.append("AND (CI.CID LIKE :cdCid or ");
        sql.append("CI.DESCR_CID LIKE :descricaoCid) ");
        
    }

    /*
     * (non-Javadoc)
     * @see br.com.mv.regulacao.dispensacaomedicamento.dao.SolicitacaoMedicamentoApacDao#existeSolicitacaoMedicamentAPACPacienteParaMesmoCidPrincipal(br.com.mv.regulacao.dispensacaomedicamento.model.SolicitacaoMedicamentoApac)
     */
    @Override
    public boolean existeSolicitacaoMedicamentAPACPacienteParaMesmoCidPrincipal(SolicitacaoMedicamentoApac solicitacaoMedicamentoApac)
    {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        StringBuilder sql = new StringBuilder();
        
        sql.append("select ");
        sql.append("count(sm.cd_solicitacao_medicamento) ");
        sql.append("from solicitacao_medicamento sm ");
        sql.append("inner join dbamvfor.tb_cidadao tcd on sm.cd_cidadao_paciente = tcd.cd_cidadao ");
        sql.append("inner join dbamvfor.solicitacao_medcmto_apac sma on sm.cd_solicitacao_medicamento = sma.cd_solicitacao_mdcmt_apac ");
        sql.append("inner join dbamvfor.procedimento_cid pc on sma.cd_procedimento_cid_principal = pc.cd_procedimento_cid  ");
        sql.append("inner join dbamvfor.cid cid on pc.cd_cid = cid.id_cid  ");
        sql.append("inner join dbamvfor.situacao_solicitacao_medcmnto ssm on sm.cd_solicitacao_medicamento = ssm.cd_situacao_solicitacao_mdcmt ");
        sql.append("where sm.cd_cidadao_paciente = :idCidadaoPaciente ");
        sql.append("and cid.cid = :codigoCid  ");
        sql.append("AND ((to_date(:dataInicial, 'dd/mm/yyyy') BETWEEN sma.dt_inicio AND sma.dt_fim) ");
        sql.append("OR (to_date(:dataFinal, 'dd/mm/yyyy' ) BETWEEN sma.dt_inicio AND sma.dt_fim)  ");
        sql.append("OR (sma.dt_inicio  > to_date(:dataInicial, 'dd/mm/yyyy')  ");
        sql.append("AND sma.dt_fim  < to_date(:dataFinal, 'dd/mm/yyyy')) )");
        sql.append("AND ssm.cd_situacao_solicitacao_mdcmt <> 4 ");
        
        SQLQuery query = getSession().createSQLQuery(sql.toString());
        query.setParameter("idCidadaoPaciente", solicitacaoMedicamentoApac.getCidadaoPaciente().getId());
        query.setParameter("codigoCid", solicitacaoMedicamentoApac.getProcedimentoCidPrincipal().getCid().getCodigoCid());
        query.setParameter("dataInicial", df.format(solicitacaoMedicamentoApac.getDataInicio()));
        query.setParameter("dataFinal", df.format(solicitacaoMedicamentoApac.getDataFim()));
        
        BigDecimal count = (BigDecimal) query.uniqueResult();
        
        if(count.intValue() == 0 )
            return false;
        else
            return true;
    }

    /*
     * (non-Javadoc)
     * @see br.com.mv.regulacao.dispensacaomedicamento.dao.SolicitacaoMedicamentoApacDao#existeSolicitacaoMedicamentAPACPacienteParaMesmoMedicamento(br.com.mv.regulacao.dispensacaomedicamento.model.SolicitacaoMedicamentoApac, br.com.mv.regulacao.dispensacaomedicamento.model.ItemSolicitacaoMedicamentoApac)
     */
    @Override
    public boolean existeSolicitacaoMedicamentAPACPacienteParaMesmoMedicamento(SolicitacaoMedicamentoApac solicitacaoMedicamentoApac, ItemSolicitacaoMedicamentoApac itemSolicitacaoMedicamentoApac)
    {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        StringBuilder sql = new StringBuilder();
        
        sql.append("select ");
        sql.append("COUNT(DISTINCT sm.cd_solicitacao_medicamento) ");
        sql.append("FROM dbamvfor.solicitacao_medicamento sm ");
        sql.append("INNER JOIN dbamvfor.tb_cidadao tcd ON sm.cd_cidadao_paciente = tcd.cd_cidadao ");
        sql.append("INNER JOIN dbamvfor.solicitacao_medcmto_apac sma ON sm.cd_solicitacao_medicamento = sma.cd_solicitacao_mdcmt_apac ");
        sql.append("INNER JOIN dbamvfor.item_solicitacao_medicamento ism ON sm.cd_solicitacao_medicamento = ism.cd_solicitacao_medicamento ");
        sql.append("INNER JOIN dbamvfor.medicamento_item_unidade miu ON ism.cd_medicamento_item_unidade = miu.cd_medicamento_item_unidade ");
        sql.append("INNER JOIN dbamvfor.medicamento m ON miu.cd_medicamento = m.cd_medicamento ");
        sql.append("INNER JOIN dbamvfor.procedimento_vigente pv ON m.cd_procedimento_vigente = pv.cd_procedimento_vigente ");
        sql.append("INNER JOIN dbamvfor.situacao_solicitacao_medcmnto ssm ON sm.cd_solicitacao_medicamento = ssm.cd_situacao_solicitacao_mdcmt ");
        sql.append("WHERE sm.cd_cidadao_paciente     = :idCidadaoPaciente ");
        sql.append("AND pv.nr_codigo_procedimento    = :codigoProcedimento ");
        sql.append("AND ((to_date(:dataInicial, 'dd/mm/yyyy') BETWEEN sma.dt_inicio AND sma.dt_fim) ");
        sql.append("OR (to_date(:dataFinal, 'dd/mm/yyyy' ) BETWEEN sma.dt_inicio AND sma.dt_fim)  ");
        sql.append("OR (sma.dt_inicio  > to_date(:dataInicial, 'dd/mm/yyyy')  ");
        sql.append("AND sma.dt_fim  < to_date(:dataFinal, 'dd/mm/yyyy')) )");
        sql.append("AND ssm.cd_situacao_solicitacao_mdcmt <> 4 ");
        
        
        SQLQuery query = getSession().createSQLQuery(sql.toString());
        query.setParameter("idCidadaoPaciente", solicitacaoMedicamentoApac.getCidadaoPaciente().getId());
        query.setParameter("codigoProcedimento", itemSolicitacaoMedicamentoApac.getMedicamentoItemUnidade().getMedicamento().getProcedimentoVigente().getCodigoProcedimento());
        query.setParameter("dataInicial", df.format(solicitacaoMedicamentoApac.getDataInicio()));
        query.setParameter("dataFinal", df.format(solicitacaoMedicamentoApac.getDataFim()));
        
        BigDecimal count = (BigDecimal) query.uniqueResult();
        
        if(count.intValue() == 0 )
            return false;
        else
            return true;
    }

    @Override
    public SolicitacaoMedicamentoApac buscarSolicitacaoMedicamentoApacPorId(SolicitacaoMedicamentoApac solicitacaoMedicamentoApac)
    {
        
        
        StringBuilder sql = new StringBuilder();
        
        sql.append("select ");
        
        sql.append("solicitacaoMedicamento.cd_solicitacao_medicamento                               AS \"id\", ");
        sql.append("cidadao.nm_cidadao                                                              AS \"nomePaciente\", ");
        sql.append("solicitacaoMedicamento.ds_codigo_receita                                        AS \"codigoReceita\", ");
        sql.append("estabelecimento.cd_estabelecimento                                              AS \"idEstabelecimentoSolicitante\", ");
        sql.append("estabelecimento.nm_fantasia                                                     AS \"estabelecimentoSolicitante\", ");
        sql.append("setorEstabelecimento.setorEstabelecimento.cd_setor_estabelecimento              AS \"idSetorEstabSolicitante\", ");
        sql.append("setorEstabelecimento.ds_setor_estabelecimento                                   AS \"setorEstabSolicitante\", ");
        sql.append("farmacia.ds_farmacia                                                            AS \"farmaciaAtendimento\", ");
        sql.append("to_char(solicitacaoMedicamento.dh_cadastro, 'DD/MM/YYYY')                       AS \"strDataSolicitacao\", ");
        sql.append("to_char(solicitacaoMedicamento.dt_proxima_consulta, 'DD/MM/YYYY')               AS \"strDataConsulta\", ");
        sql.append("solicitacaoMedicamento.dh_cadastro                                              AS \"dataSolicitacao\", ");
        sql.append("solicitacaoMedicamento.dt_proxima_consulta                                      AS \"dataConsulta\" ");

        sql.append("solicitacaoMedicamentoApac.nr_apac                                              AS \"numeroApac\" ");
        sql.append("solicitacaoMedicamentoApac.dt_inicio                                            AS \"dataInicio\" ");
        sql.append("solicitacaoMedicamentoApac.dt_fim                                               AS \"dataFim\" ");
        sql.append("solicitacaoMedicamentoApac.dt_fim                                               AS \"dataFim\" ");
        sql.append("solicitacaoMedicamentoApac.tp_hemofilia                                         AS \"tipoHemofilia\" ");
        sql.append("solicitacaoMedicamentoApac.tp_inibidor                                          AS \"tipoHemofilia\" ");
        sql.append("solicitacaoMedicamentoApac.tp_inibidor                                          AS \"tipoInibidor\" ");
        sql.append("solicitacaoMedicamentoApac.nr_peso                                              AS \"numeroPeso\" ");
        sql.append("solicitacaoMedicamentoApac.nr_altura                                            AS \"numeroAltura\" ");
        sql.append("solicitacaoMedicamentoApac.sn_transplante                                       AS \"transplante\" ");
        sql.append("solicitacaoMedicamentoApac.qt_transplantes                                      AS \"quantidadeTransplante\" ");

        sql.append("procedimentoCidPrincipal.cd_procedimento_cid                                    AS \"idProcCidPrincipal\" ");
        sql.append("procedimentoPrincipal.cd_procedimento                                           AS \"idProcePrincipal\" ");
        sql.append("procedimentoPrincipal.ds_procedimento                                           AS \"descProcedimentoPrin\" ");
        sql.append("procedimentoPrincipal.dt_competencia                                            AS \"dataCompetenciaProcePrin\" ");
        
        
        sql.append("from dbamvfor.solicitacao_medicamento solicitacaoMedicamento ");
        sql.append("inner join dbamvfor.tb_cidadao cidadao on solicitacaoMedicamento.cd_cidadao_paciente = cidadao.cd_cidadao ");
        sql.append("inner join dbamvfor.tb_vinculo_profissional vinculoprofissional on solicitacao.cd_vinculo_profissional_prsctr = vinculoprofissional.cd_vinculo_profissional ");
        sql.append("inner join dbamvfor.tb_profissional profissional on vinculoprofissional.cd_profissional = profissional.cd_profissional ");
        sql.append("inner join dbamvfor.tb_estabelecimento estabelecimento on vinculoProfissional.cd_estabelecimento = estabelecimento.cd_estabelecimento ");
            
        sql.append("left join dbamvfor.farmacia farmacia on solicitacaoMedicamento.cd_farmacia = farmacia.cd_farmacia ");
        sql.append("left join dbamvfor.solicitacao_medcmto_apac solicitacaoMedicamentoApac on solicitacaoMedicamento.cd_solicitacao_medicamento = solicitacaoMedicamentoApac.cd_solicitacao_mdcmt_apac");
        sql.append("left join dbamvfor.procedimento_cid procedimentoCidPrincipal on solicitacaoMedicamentoApac.cd_procedimento_cid_principal = procedimentoCidPrincipal.cd_procedimento_cid");
        sql.append("left join dbamvfor.procedimento procedimentoPrincipal on procedimentoCidPrincipal.cd_procedimento = procedimentoPrincipal.cd_procedimento");
        
        sql.append("left join dbamvfor.procedimento_cid procedimentoCidSecundario on solicitacaoMedicamentoApac.cd_procedimento_cid_principal = procedimentoCidSecundario.cd_procedimento_cid");
        
        sql.append("left join dbamvfor.estabelecimento_set_estblm estabelecimentoSetEstblm on farmacia.cd_estabelecimento_set_estblm = estabelecimentoSetEstblm.cd_estabelecimento_set_estblm ");
        sql.append("left join dbamvfor.setor_estabelecimento setorEstabelecimento ON estabelecimentosetestblm.cd_setor_estabelecimento = setorEstabelecimento.cd_setor_estabelecimento ");
        sql.append("left join dbamvfor.tb_estabelecimento estabelecimento2 on estabelecimentosetestblm.cd_estabelecimento = estabelecimento.cd_estabelecimento ");
        sql.append("where 1=1 ");
        
        
        return null;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public Collection<SolicitacaoMedicamentoApacDTO> buscarAvaliacaoApac(SolicitacaoMedicamentoApac solicitacaoMedicamentoApac)
    {      
       StringBuilder sql = new StringBuilder();
       SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
       
       sql.append("select ");
       sql.append(" s.cd_solicitacao_mdcmt_apac                         AS \"id\", ");
       sql.append(" s.nr_apac                                           AS \"numeroApac\", ");
       sql.append(" v.cd_vinculo_profissional                           AS \"idVinculoProfissional\", ");
       sql.append(" v.cd_profissional                                   AS \"idProfissional\", ");
       sql.append(" v.cd_estabelecimento                                AS \"idEstabelecimento\", ");
       sql.append(" e.nm_fantasia                                       AS \"nomeFantasiaEstab\", ");
       sql.append(" m.cd_cidadao_paciente                               AS \"idCidadaoPaciente\", ");
       sql.append(" o.nm_cidadao                                        AS \"nomeCidadao\", ");
       sql.append(" to_char(o.dt_nascimento, 'dd/MM/yyyy HH:mm:ss')     AS \"nascimentoCidadaoStr\", ");
       sql.append(" o.tp_sexo                                           AS \"sexoCidadao\", ");
       sql.append(" p1.cd_procedimento_cid                              AS \"idProcCidPrincipal\", ");
       sql.append(" r1.cd_procedimento                                  AS \"idProcePrincipal\", ");
       sql.append(" r1.ds_procedimento                                  AS \"descProcedimentoPrin\", ");
       sql.append(" to_char(r1.dt_competencia, 'dd/MM/yyyy HH:mm:ss')   AS \"dataCompetProcePrinSrc\", ");
       sql.append(" p2.cd_procedimento_cid                              AS \"idProcCidSecundario\", ");
       sql.append(" r2.cd_procedimento                                  AS \"idProceSecundario\", ");
       sql.append(" r2.ds_procedimento                                  AS \"descProcedimentoSecun\", ");
       sql.append(" to_char(r2.dt_competencia, 'dd/MM/yyyy HH:mm:ss')   AS \"dataCompetProceSecunSrc\", ");
       sql.append(" p1.cd_cid                                           AS \"idCidPrincipal\", ");
       sql.append(" i1.descr_cid                                        AS \"descricaoCidPrin\", ");
       sql.append(" i1.cid_apac                                         AS \"cidApacPrincipal\", ");
       sql.append(" p2.cd_cid                                           AS \"idCidSecundario\", ");
       sql.append(" i2.descr_cid                                        AS \"descricaoCidSecun\", ");
       sql.append(" i2.cid_apac                                         AS \"cidApacSecundario\", ");
       sql.append(" to_char(s.dt_inicio, 'dd/MM/yyyy HH:mm:ss')         AS \"dataInicioStr\", ");
       sql.append(" to_char(s.dt_fim, 'dd/MM/yyyy HH:mm:ss')            AS \"dataFimStr\" ");
       sql.append("from DBAMVFOR.solicitacao_medcmto_apac s ");
       sql.append("left outer join DBAMVFOR.solicitacao_medicamento m on (s.cd_solicitacao_mdcmt_apac = m.cd_solicitacao_medicamento) ");
       sql.append("left outer join DBAMVFOR.tb_cidadao o on (m.cd_cidadao_paciente = o.cd_cidadao) ");
       sql.append("left outer join DBAMVFOR.tb_vinculo_profissional v on (s.cd_vinculo_profissional_autzd = v.cd_vinculo_profissional) ");
       sql.append("left outer join DBAMVFOR.tb_estabelecimento e on (e.cd_estabelecimento = v.cd_estabelecimento) ");
       sql.append("left outer join DBAMVFOR.procedimento_cid p1 on (s.cd_procedimento_cid_principal = p1.cd_procedimento_cid) ");
       sql.append("left outer join DBAMVFOR.procedimento r1 on (p1.cd_procedimento = r1.cd_procedimento) ");
       sql.append("left outer join DBAMVFOR.procedimento_cid p2 on (s.cd_procedimento_cid_secundario = p2.cd_procedimento_cid) ");
       sql.append("left outer join DBAMVFOR.procedimento r2 on (p2.cd_procedimento = r2.cd_procedimento) ");
       sql.append("left outer join DBAMVFOR.cid i1 on (p1.cd_cid = i1.id_cid) ");
       sql.append("left outer join DBAMVFOR.cid i2 on (p2.cd_cid = i2.id_cid) ");
       sql.append("where (:idEstabelecimento is null or v.cd_estabelecimento = :idEstabelecimento)");
       sql.append("and   (:idCidadaoPaciente is null or m.cd_cidadao_paciente = :idCidadaoPaciente)");
       sql.append("and   (:numeroApac is null or s.nr_apac = :numeroApac)");
       sql.append("and   (:idProcePrincipal is null or p1.cd_procedimento = :idProcePrincipal)");
       sql.append("and   (:idCidPrincipal is null or p1.cd_cid = :idCidPrincipal)");
       sql.append("and   ((:dataInicio is null or (to_date(:dataInicio, 'dd/MM/yyyy') between s.dt_inicio  and s.dt_fim))");
       sql.append("or    (:dataFim is null or (to_date(:dataFim, 'dd/MM/yyyy') between s.dt_inicio  and s.dt_fim)))");
       
       SQLQuery query = getSession().createSQLQuery(sql.toString());
       
       if (solicitacaoMedicamentoApac != null && 
               solicitacaoMedicamentoApac.getProfissionalAutorizador() != null && 
               solicitacaoMedicamentoApac.getProfissionalAutorizador().getEstabelecimento() != null &&
               solicitacaoMedicamentoApac.getProfissionalAutorizador().getEstabelecimento().getId() != null)
           query.setParameter("idEstabelecimento", solicitacaoMedicamentoApac.getProfissionalAutorizador().getEstabelecimento().getId());
       else
           query.setParameter("idEstabelecimento", null, StandardBasicTypes.LONG);
       
       if (solicitacaoMedicamentoApac != null && 
               solicitacaoMedicamentoApac.getCidadaoPaciente() != null && 
               solicitacaoMedicamentoApac.getCidadaoPaciente().getId() != null)
           query.setParameter("idCidadaoPaciente", solicitacaoMedicamentoApac.getCidadaoPaciente().getId());
       else
           query.setParameter("idCidadaoPaciente", null, StandardBasicTypes.LONG);
       
       if (solicitacaoMedicamentoApac != null && solicitacaoMedicamentoApac.getNumeroApac() != null)
           query.setParameter("numeroApac", solicitacaoMedicamentoApac.getNumeroApac());
       else
           query.setParameter("numeroApac", null, StandardBasicTypes.CHAR_ARRAY);

       if (solicitacaoMedicamentoApac != null &&
               solicitacaoMedicamentoApac.getProcedimentoCidPrincipal() != null &&
               solicitacaoMedicamentoApac.getProcedimentoCidPrincipal().getProcedimento() != null &&
               solicitacaoMedicamentoApac.getProcedimentoCidPrincipal().getProcedimento().getId() != null)
           query.setParameter("idProcePrincipal", solicitacaoMedicamentoApac.getProcedimentoCidPrincipal().getProcedimento().getId());
       else
           query.setParameter("idProcePrincipal", null, StandardBasicTypes.LONG);
       
       if (solicitacaoMedicamentoApac != null && 
               solicitacaoMedicamentoApac.getProcedimentoCidPrincipal() != null &&
               solicitacaoMedicamentoApac.getProcedimentoCidPrincipal().getCid().getId() != null)
           query.setParameter("idCidPrincipal", solicitacaoMedicamentoApac.getProcedimentoCidPrincipal().getCid().getId());
       else
           query.setParameter("idCidPrincipal", null, StandardBasicTypes.LONG);
       
       if (solicitacaoMedicamentoApac != null)
       {
           query.setParameter("dataInicio", (solicitacaoMedicamentoApac.getDataInicio() != null ? df.format(solicitacaoMedicamentoApac.getDataInicio()) : null), StandardBasicTypes.DATE);
           query.setParameter("dataFim", (solicitacaoMedicamentoApac.getDataFim() != null ? df.format(solicitacaoMedicamentoApac.getDataFim()) : null), StandardBasicTypes.DATE);
       }
       
       query.setResultTransformer(new AliasToBeanResultTransformer(SolicitacaoMedicamentoApacDTO.class));
       
       return query.list();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<SolicitacaoMedicamentoApacDTO> buscarAvaliacaoApac(int initialPos, int maxResults, SolicitacaoMedicamentoApac solicitacaoMedicamentoApac)
    {
        StringBuilder sql = new StringBuilder();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        
        sql.append("select ");
        sql.append(" s.cd_solicitacao_mdcmt_apac                     AS \"id\", ");
        sql.append(" s.nr_apac                                       AS \"numeroApac\", ");
        sql.append(" v.cd_vinculo_profissional                       AS \"idVinculoProfissional\", ");
        sql.append(" v.cd_profissional                               AS \"idProfissional\", ");
        sql.append(" v.cd_estabelecimento                            AS \"idEstabelecimento\", ");
        sql.append(" e.nm_fantasia                                   AS \"nomeFantasiaEstab\", ");
        sql.append(" m.cd_cidadao_paciente                           AS \"idCidadaoPaciente\", ");
        sql.append(" o.nm_cidadao                                    AS \"nomeCidadao\", ");
        sql.append(" to_char(o.dt_nascimento, 'dd/MM/yyyy')          AS \"nascimentoCidadaoStr\", ");
        sql.append(" o.tp_sexo                                       AS \"sexoCidadao\", ");
        sql.append(" p1.cd_procedimento_cid                          AS \"idProcCidPrincipal\", ");
        sql.append(" r1.cd_procedimento                              AS \"idProcePrincipal\", ");
        sql.append(" r1.ds_procedimento                              AS \"descProcedimentoPrin\", ");
        sql.append(" to_char(r1.dt_competencia, 'dd/MM/yyyy')        AS \"dataCompetProcePrinSrc\", ");
        sql.append(" p2.cd_procedimento_cid                          AS \"idProcCidSecundario\", ");
        sql.append(" r2.cd_procedimento                              AS \"idProceSecundario\", ");
        sql.append(" r2.ds_procedimento                              AS \"descProcedimentoSecun\", ");
        sql.append(" to_char(r2.dt_competencia, 'dd/MM/yyyy')        AS \"dataCompetProceSecunSrc\", ");
        sql.append(" p1.cd_cid                                       AS \"idCidPrincipal\", ");
        sql.append(" i1.descr_cid                                    AS \"descricaoCidPrin\", ");
        sql.append(" i1.cid_apac                                     AS \"cidApacPrincipal\", ");
        sql.append(" p2.cd_cid                                       AS \"idCidSecundario\", ");
        sql.append(" i2.descr_cid                                    AS \"descricaoCidSecun\", ");
        sql.append(" i2.cid_apac                                     AS \"cidApacSecundario\", ");
        sql.append(" to_char(s.dt_inicio, 'dd/MM/yyyy')              AS \"dataInicioStr\", ");
        sql.append(" to_char(s.dt_fim, 'dd/MM/yyyy')                 AS \"dataFimStr\" ");
        sql.append("from DBAMVFOR.solicitacao_medcmto_apac s ");
        sql.append("left outer join DBAMVFOR.solicitacao_medicamento m on (s.cd_solicitacao_mdcmt_apac = m.cd_solicitacao_medicamento) ");
        sql.append("left outer join DBAMVFOR.tb_cidadao o on (m.cd_cidadao_paciente = o.cd_cidadao) ");
        sql.append("left outer join DBAMVFOR.tb_vinculo_profissional v on (s.cd_vinculo_profissional_autzd = v.cd_vinculo_profissional) ");
        sql.append("left outer join DBAMVFOR.tb_estabelecimento e on (e.cd_estabelecimento = v.cd_estabelecimento) ");
        sql.append("left outer join DBAMVFOR.procedimento_cid p1 on (s.cd_procedimento_cid_principal = p1.cd_procedimento_cid) ");
        sql.append("left outer join DBAMVFOR.procedimento r1 on (p1.cd_procedimento = r1.cd_procedimento) ");
        sql.append("left outer join DBAMVFOR.procedimento_cid p2 on (s.cd_procedimento_cid_secundario = p2.cd_procedimento_cid) ");
        sql.append("left outer join DBAMVFOR.procedimento r2 on (p2.cd_procedimento = r2.cd_procedimento) ");
        sql.append("left outer join DBAMVFOR.cid i1 on (p1.cd_cid = i1.id_cid) ");
        sql.append("left outer join DBAMVFOR.cid i2 on (p2.cd_cid = i2.id_cid) ");
        sql.append("where (:idEstabelecimento is null or v.cd_estabelecimento = :idEstabelecimento)");
        sql.append("and   (:idCidadaoPaciente is null or m.cd_cidadao_paciente = :idCidadaoPaciente)");
        sql.append("and   (:numeroApac is null or s.nr_apac = :numeroApac)");
        sql.append("and   (:idProcePrincipal is null or p1.cd_procedimento = :idProcePrincipal)");
        sql.append("and   (:idCidPrincipal is null or p1.cd_cid = :idCidPrincipal)");
        sql.append("and   ((:dataInicio is null or (to_date(:dataInicio, 'dd/MM/yyyy') between s.dt_inicio  and s.dt_fim))");
        sql.append("or    (:dataFim is null or (to_date(:dataFim, 'dd/MM/yyyy') between s.dt_inicio  and s.dt_fim)))");
        
        SQLQuery query = getSession().createSQLQuery(sql.toString());
        query.setFirstResult(initialPos);
        query.setMaxResults(maxResults);
        
        if (solicitacaoMedicamentoApac != null && 
                solicitacaoMedicamentoApac.getProfissionalAutorizador() != null && 
                solicitacaoMedicamentoApac.getProfissionalAutorizador().getEstabelecimento() != null &&
                solicitacaoMedicamentoApac.getProfissionalAutorizador().getEstabelecimento().getId() != null)
            query.setParameter("idEstabelecimento", solicitacaoMedicamentoApac.getProfissionalAutorizador().getEstabelecimento().getId());
        else
            query.setParameter("idEstabelecimento", null, StandardBasicTypes.LONG);
        
        if (solicitacaoMedicamentoApac != null && 
                solicitacaoMedicamentoApac.getCidadaoPaciente() != null && 
                solicitacaoMedicamentoApac.getCidadaoPaciente().getId() != null)
            query.setParameter("idCidadaoPaciente", solicitacaoMedicamentoApac.getCidadaoPaciente().getId());
        else
            query.setParameter("idCidadaoPaciente", null, StandardBasicTypes.LONG);
        
        if (solicitacaoMedicamentoApac != null && solicitacaoMedicamentoApac.getNumeroApac() != null)
            query.setParameter("numeroApac", solicitacaoMedicamentoApac.getNumeroApac());
        else
            query.setParameter("numeroApac", null, StandardBasicTypes.CHAR_ARRAY);

        if (solicitacaoMedicamentoApac != null &&
                solicitacaoMedicamentoApac.getProcedimentoCidPrincipal() != null &&
                solicitacaoMedicamentoApac.getProcedimentoCidPrincipal().getProcedimento() != null &&
                solicitacaoMedicamentoApac.getProcedimentoCidPrincipal().getProcedimento().getId() != null)
            query.setParameter("idProcePrincipal", solicitacaoMedicamentoApac.getProcedimentoCidPrincipal().getProcedimento().getId());
        else
            query.setParameter("idProcePrincipal", null, StandardBasicTypes.LONG);
        
        if (solicitacaoMedicamentoApac != null && 
                solicitacaoMedicamentoApac.getProcedimentoCidPrincipal() != null &&
                solicitacaoMedicamentoApac.getProcedimentoCidPrincipal().getCid().getId() != null)
            query.setParameter("idCidPrincipal", solicitacaoMedicamentoApac.getProcedimentoCidPrincipal().getCid().getId());
        else
            query.setParameter("idCidPrincipal", null, StandardBasicTypes.LONG);
        
        if (solicitacaoMedicamentoApac != null)
        {
            query.setParameter("dataInicio", (solicitacaoMedicamentoApac.getDataInicio() != null ? df.format(solicitacaoMedicamentoApac.getDataInicio()) : null), StandardBasicTypes.DATE);
            query.setParameter("dataFim", (solicitacaoMedicamentoApac.getDataFim() != null ? df.format(solicitacaoMedicamentoApac.getDataFim()) : null), StandardBasicTypes.DATE);
        }
        
        query.setResultTransformer(new AliasToBeanResultTransformer(SolicitacaoMedicamentoApacDTO.class));
        
        return query.list();
    }
    
    @Override
    public Long countAvaliacaoApac(SolicitacaoMedicamentoApac solicitacaoMedicamentoApac)
    {
        StringBuilder sql = new StringBuilder();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        
        sql.append("select ");
        sql.append(" count(s.nr_apac)   AS  \"contNumeroApac\" ");
        sql.append("from DBAMVFOR.solicitacao_medcmto_apac s ");
        sql.append("left outer join DBAMVFOR.solicitacao_medicamento m on (s.cd_solicitacao_mdcmt_apac = m.cd_solicitacao_medicamento) ");
        sql.append("left outer join DBAMVFOR.tb_cidadao o on (m.cd_cidadao_paciente = o.cd_cidadao) ");
        sql.append("left outer join DBAMVFOR.tb_vinculo_profissional v on (s.cd_vinculo_profissional_autzd = v.cd_vinculo_profissional) ");
        sql.append("left outer join DBAMVFOR.tb_estabelecimento e on (e.cd_estabelecimento = v.cd_estabelecimento) ");
        sql.append("left outer join DBAMVFOR.procedimento_cid p1 on (s.cd_procedimento_cid_principal = p1.cd_procedimento_cid) ");
        sql.append("left outer join DBAMVFOR.procedimento r1 on (p1.cd_procedimento = r1.cd_procedimento) ");
        sql.append("left outer join DBAMVFOR.procedimento_cid p2 on (s.cd_procedimento_cid_secundario = p2.cd_procedimento_cid) ");
        sql.append("left outer join DBAMVFOR.procedimento r2 on (p2.cd_procedimento = r2.cd_procedimento) ");
        sql.append("left outer join DBAMVFOR.cid i1 on (p1.cd_cid = i1.id_cid) ");
        sql.append("left outer join DBAMVFOR.cid i2 on (p2.cd_cid = i2.id_cid) ");
        sql.append("where (:idEstabelecimento is null or v.cd_estabelecimento = :idEstabelecimento)");
        sql.append("and   (:idCidadaoPaciente is null or m.cd_cidadao_paciente = :idCidadaoPaciente)");
        sql.append("and   (:numeroApac is null or s.nr_apac = :numeroApac)");
        sql.append("and   (:idProcePrincipal is null or p1.cd_procedimento = :idProcePrincipal)");
        sql.append("and   (:idCidPrincipal is null or p1.cd_cid = :idCidPrincipal)");
        sql.append("and   ((:dataInicio is null or (to_date(:dataInicio, 'dd/MM/yyyy') between s.dt_inicio  and s.dt_fim))");
        sql.append("or    (:dataFim is null or (to_date(:dataFim, 'dd/MM/yyyy') between s.dt_inicio  and s.dt_fim)))");
        
        SQLQuery query = getSession().createSQLQuery(sql.toString());
        
        if (solicitacaoMedicamentoApac != null && 
                solicitacaoMedicamentoApac.getProfissionalAutorizador() != null && 
                solicitacaoMedicamentoApac.getProfissionalAutorizador().getEstabelecimento() != null &&
                solicitacaoMedicamentoApac.getProfissionalAutorizador().getEstabelecimento().getId() != null)
            query.setParameter("idEstabelecimento", solicitacaoMedicamentoApac.getProfissionalAutorizador().getEstabelecimento().getId());
        else
            query.setParameter("idEstabelecimento", null, StandardBasicTypes.LONG);
        
        if (solicitacaoMedicamentoApac != null && 
                solicitacaoMedicamentoApac.getCidadaoPaciente() != null && 
                solicitacaoMedicamentoApac.getCidadaoPaciente().getId() != null)
            query.setParameter("idCidadaoPaciente", solicitacaoMedicamentoApac.getCidadaoPaciente().getId());
        else
            query.setParameter("idCidadaoPaciente", null, StandardBasicTypes.LONG);
        
        if (solicitacaoMedicamentoApac != null && solicitacaoMedicamentoApac.getNumeroApac() != null)
            query.setParameter("numeroApac", solicitacaoMedicamentoApac.getNumeroApac());
        else
            query.setParameter("numeroApac", null, StandardBasicTypes.CHAR_ARRAY);

        if (solicitacaoMedicamentoApac != null &&
                solicitacaoMedicamentoApac.getProcedimentoCidPrincipal() != null &&
                solicitacaoMedicamentoApac.getProcedimentoCidPrincipal().getProcedimento() != null &&
                solicitacaoMedicamentoApac.getProcedimentoCidPrincipal().getProcedimento().getId() != null)
            query.setParameter("idProcePrincipal", solicitacaoMedicamentoApac.getProcedimentoCidPrincipal().getProcedimento().getId());
        else
            query.setParameter("idProcePrincipal", null, StandardBasicTypes.LONG);
        
        if (solicitacaoMedicamentoApac != null && 
                solicitacaoMedicamentoApac.getProcedimentoCidPrincipal() != null &&
                solicitacaoMedicamentoApac.getProcedimentoCidPrincipal().getCid().getId() != null)
            query.setParameter("idCidPrincipal", solicitacaoMedicamentoApac.getProcedimentoCidPrincipal().getCid().getId());
        else
            query.setParameter("idCidPrincipal", null, StandardBasicTypes.LONG);
        
        if (solicitacaoMedicamentoApac != null)
        {
            query.setParameter("dataInicio", (solicitacaoMedicamentoApac.getDataInicio() != null ? df.format(solicitacaoMedicamentoApac.getDataInicio()) : null), StandardBasicTypes.DATE);
            query.setParameter("dataFim", (solicitacaoMedicamentoApac.getDataFim() != null ? df.format(solicitacaoMedicamentoApac.getDataFim()) : null), StandardBasicTypes.DATE);
        }
        
        BigDecimal count = (BigDecimal) query.uniqueResult();
        return count.longValue();
    }