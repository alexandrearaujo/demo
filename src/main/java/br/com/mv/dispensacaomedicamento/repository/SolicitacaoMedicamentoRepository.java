package br.com.mv.dispensacaomedicamento.repository;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collection;

import org.hibernate.SQLQuery;

import br.com.mv.commons.web.dao.GenericDao;
import br.com.mv.geral.model.VinculoProfissionalDTO;
import br.com.mv.regulacao.dispensacaomedicamento.exception.DispensacaoMedicamentoException;
import br.com.mv.regulacao.dispensacaomedicamento.model.SolicitacaoMedicamento;
import br.com.mv.regulacao.dispensacaomedicamento.model.SolicitacaoMedicamentoDTO;
import br.com.mv.regulacao.model.geral.Estabelecimento;

/**
 * 
 * @author Carlos Roberto
 *
 */
public interface SolicitacaoMedicamentoRepository extends GenericDao<SolicitacaoMedicamento>
{

    @Override
    public Long count(SolicitacaoMedicamentoDTO solicitacaoMedicamentoDTO)
    {
        StringBuilder sql = new StringBuilder();
        
        sql.append("select ");
        sql.append(" count (solicitacao.cd_solicitacao_medicamento) ");
        
        sql.append("from dbamvfor.solicitacao_medicamento solicitacao ");
        sql.append("inner join dbamvfor.tb_cidadao cidadao on solicitacao.cd_cidadao_paciente = cidadao.cd_cidadao ");
        sql.append("inner join dbamvfor.tb_vinculo_profissional vinculoprofissional on solicitacao.cd_vinculo_profissional_prsctr = vinculoprofissional.cd_vinculo_profissional ");
        sql.append("inner join dbamvfor.tb_profissional profissional on vinculoprofissional.cd_profissional = profissional.cd_profissional ");
        sql.append("inner join dbamvfor.tb_estabelecimento estabelecimento on vinculoProfissional.cd_estabelecimento = estabelecimento.cd_estabelecimento ");
        sql.append("inner join dbamvfor.farmacia farmacia on solicitacao.cd_farmacia = farmacia.cd_farmacia ");
        sql.append("left join dbamvfor.estabelecimento_set_estblm estabelecimentoSetEstblm on farmacia.cd_estabelecimento_set_estblm = estabelecimentoSetEstblm.cd_estabelecimento_set_estblm ");
        sql.append("left join dbamvfor.setor_estabelecimento setorEstabelecimento ON estabelecimentosetestblm.cd_setor_estabelecimento = setorEstabelecimento.cd_setor_estabelecimento ");
        sql.append("left join dbamvfor.tb_estabelecimento estabelecimento2 on estabelecimentosetestblm.cd_estabelecimento = estabelecimento.cd_estabelecimento ");
        sql.append("where 1=1 ");
       
        setConsultaSolicitacaoMedicamentoDTO(solicitacaoMedicamentoDTO, sql);
        
        sql.append("order by cidadao.nm_cidadao ");
        
        SQLQuery query = getSession().createSQLQuery(sql.toString());
        setParametrosSolicitacaoMedicamentoDTO(solicitacaoMedicamentoDTO, query);
        BigDecimal count = (BigDecimal) query.uniqueResult();
        return count.longValue();
        
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<SolicitacaoMedicamentoDTO> list(int initialPos, int maxResults, SolicitacaoMedicamentoDTO solicitacaoMedicamentoDTO)
    {
        StringBuilder sql = new StringBuilder();
        sql.append("select ");
        sql.append("solicitacao.cd_solicitacao_medicamento                               AS \"id\", ");
        sql.append("cidadao.nm_cidadao                                                   AS \"nomePaciente\", ");
        sql.append("solicitacao.ds_codigo_receita                                        AS \"codigoReceita\", ");
        sql.append("estabelecimento.cd_estabelecimento                                   AS \"idEstabelecimentoSolicitante\", ");
        sql.append("estabelecimento.nm_fantasia                                          AS \"estabelecimentoSolicitante\", ");
        sql.append("setorEstabelecimento.setorEstabelecimento.cd_setor_estabelecimento   AS \"idSetorEstabSolicitante\", ");
        sql.append("setorEstabelecimento.ds_setor_estabelecimento                        AS \"setorEstabSolicitante\", ");
        sql.append("farmacia.ds_farmacia                                                 AS \"farmaciaAtendimento\", ");
        sql.append("to_char(solicitacao.dh_cadastro, 'DD/MM/YYYY')                       AS \"strDataSolicitacao\", ");
        sql.append("to_char(solicitacao.dt_proxima_consulta, 'DD/MM/YYYY')               AS \"strDataConsulta\", ");
        sql.append("solicitacao.dh_cadastro                                              AS \"dataSolicitacao\", ");
        sql.append("solicitacao.dt_proxima_consulta                                      AS \"dataConsulta\" ");
        
        sql.append("from dbamvfor.solicitacao_medicamento solicitacao ");
        sql.append("inner join dbamvfor.tb_cidadao cidadao on solicitacao.cd_cidadao_paciente = cidadao.cd_cidadao ");
        sql.append("inner join dbamvfor.tb_vinculo_profissional vinculoprofissional on solicitacao.cd_vinculo_profissional_prsctr = vinculoprofissional.cd_vinculo_profissional ");
        sql.append("inner join dbamvfor.tb_profissional profissional on vinculoprofissional.cd_profissional = profissional.cd_profissional ");
        sql.append("inner join dbamvfor.tb_estabelecimento estabelecimento on vinculoProfissional.cd_estabelecimento = estabelecimento.cd_estabelecimento ");
        sql.append("left join dbamvfor.farmacia farmacia on solicitacao.cd_farmacia = farmacia.cd_farmacia ");
        
        sql.append("left join dbamvfor.estabelecimento_set_estblm estabelecimentoSetEstblm on farmacia.cd_estabelecimento_set_estblm = estabelecimentoSetEstblm.cd_estabelecimento_set_estblm ");
        sql.append("left join dbamvfor.setor_estabelecimento setorEstabelecimento ON estabelecimentosetestblm.cd_setor_estabelecimento = setorEstabelecimento.cd_setor_estabelecimento ");
        sql.append("left join dbamvfor.tb_estabelecimento estabelecimento2 on estabelecimentosetestblm.cd_estabelecimento = estabelecimento.cd_estabelecimento ");
        sql.append("where 1=1 ");
        
        setConsultaSolicitacaoMedicamentoDTO(solicitacaoMedicamentoDTO, sql);
            
        sql.append("order by cidadao.nm_cidadao ");
        
        SQLQuery query = getSession().createSQLQuery(sql.toString());
        query.setFirstResult(initialPos);
        query.setMaxResults(maxResults);
        query.setResultTransformer(new AliasToBeanResultTransformer(SolicitacaoMedicamentoDTO.class));
        setParametrosSolicitacaoMedicamentoDTO(solicitacaoMedicamentoDTO, query);
        Collection<SolicitacaoMedicamentoDTO> lista = query.list(); 
        return lista;
    }

    private void setConsultaSolicitacaoMedicamentoDTO(SolicitacaoMedicamentoDTO solicitacaoMedicamentoDTO, StringBuilder sql)
    {
        if(solicitacaoMedicamentoDTO.getNomePaciente() != null && !solicitacaoMedicamentoDTO.getNomePaciente().trim().isEmpty())
            sql.append("and cidadao.nm_cidadao like :nomePaciente ");
        if(solicitacaoMedicamentoDTO.getDataInicioSolicitacao() != null && solicitacaoMedicamentoDTO.getDatafimSolicitacao() != null)
            sql.append("and trunc(solicitacao.dh_cadastro) between to_date(:dataInicioSolicitacao,'dd-mm-yyyy') and to_date(:datafimSolicitacao,'dd-mm-yyyy')  ");
        if(solicitacaoMedicamentoDTO.getDataInicioConsulta() != null && solicitacaoMedicamentoDTO.getDatafimConsulta() != null)
            sql.append("and trunc(solicitacao.dt_proxima_consulta) between to_date(:dataInicioConsulta,'dd-mm-yyyy') and to_date(:datafimConsulta,'dd-mm-yyyy') ");
        if(solicitacaoMedicamentoDTO.getEstabelecimentoSolicitante() != null && !solicitacaoMedicamentoDTO.getEstabelecimentoSolicitante().trim().isEmpty())
            sql.append("and estabelecimento.nm_fantasia like :nomeFantasia ");
        if(solicitacaoMedicamentoDTO.getSetorEstabSolicitante() != null && !solicitacaoMedicamentoDTO.getSetorEstabSolicitante().trim().isEmpty())
            sql.append("and setorEstabelecimento.ds_setor_estabelecimento like :dsSetorEstabelecimento ");
        if(solicitacaoMedicamentoDTO.getFarmaciaAtendimento() != null && !solicitacaoMedicamentoDTO.getFarmaciaAtendimento().trim().isEmpty())
            sql.append("and farmacia.ds_farmacia like :farmaciaAtendimento ");
        if(solicitacaoMedicamentoDTO.getCodigoReceita() != null && !solicitacaoMedicamentoDTO.getCodigoReceita().isEmpty())
            sql.append("and solicitacao.ds_codigo_receita = :codigoReceita ");
        if(solicitacaoMedicamentoDTO.getIdSituacaoSolicitacao() != null && solicitacaoMedicamentoDTO.getIdSituacaoSolicitacao() != -1)
            sql.append("and solicitacao.cd_situacao_solicitacao_mdcmt = :idSituacaoSolicitacao ");
    }

    private void setParametrosSolicitacaoMedicamentoDTO(SolicitacaoMedicamentoDTO solicitacaoMedicamentoDTO, SQLQuery query)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        
        if(solicitacaoMedicamentoDTO.getNomePaciente() != null && !solicitacaoMedicamentoDTO.getNomePaciente().trim().isEmpty())
            query.setParameter("nomePaciente", solicitacaoMedicamentoDTO.getNomePaciente().concat("%"));

        if(solicitacaoMedicamentoDTO.getDataInicioSolicitacao() != null && solicitacaoMedicamentoDTO.getDatafimSolicitacao() != null)
        {
            query.setParameter("dataInicioSolicitacao", dateFormat.format(solicitacaoMedicamentoDTO.getDataInicioSolicitacao()));
            query.setParameter("datafimSolicitacao", dateFormat.format(solicitacaoMedicamentoDTO.getDatafimSolicitacao()));
        }
        if(solicitacaoMedicamentoDTO.getDataInicioConsulta() != null && solicitacaoMedicamentoDTO.getDatafimConsulta() != null)
        {
            query.setParameter("dataInicioConsulta", dateFormat.format(solicitacaoMedicamentoDTO.getDataInicioConsulta()));
            query.setParameter("datafimConsulta", dateFormat.format(solicitacaoMedicamentoDTO.getDatafimConsulta()));
        }
        
        if(solicitacaoMedicamentoDTO.getEstabelecimentoSolicitante() != null && !solicitacaoMedicamentoDTO.getEstabelecimentoSolicitante().trim().isEmpty())
            query.setParameter("nomeFantasia", solicitacaoMedicamentoDTO.getEstabelecimentoSolicitante().concat("%"));
        if(solicitacaoMedicamentoDTO.getSetorEstabSolicitante() != null && !solicitacaoMedicamentoDTO.getSetorEstabSolicitante().trim().isEmpty())
            query.setParameter("dsSetorEstabelecimento", solicitacaoMedicamentoDTO.getSetorEstabSolicitante().concat("%"));
        if(solicitacaoMedicamentoDTO.getFarmaciaAtendimento() != null && !solicitacaoMedicamentoDTO.getFarmaciaAtendimento().trim().isEmpty())
            query.setParameter("farmaciaAtendimento", solicitacaoMedicamentoDTO.getFarmaciaAtendimento().concat("%"));
        if(solicitacaoMedicamentoDTO.getCodigoReceita() != null && !solicitacaoMedicamentoDTO.getCodigoReceita().isEmpty())
            query.setParameter("codigoReceita", solicitacaoMedicamentoDTO.getCodigoReceita());
        if(solicitacaoMedicamentoDTO.getIdSituacaoSolicitacao() != null && solicitacaoMedicamentoDTO.getIdSituacaoSolicitacao() != -1)
            query.setParameter("idSituacaoSolicitacao", solicitacaoMedicamentoDTO.getIdSituacaoSolicitacao());
    }

    /*
     * (non-Javadoc)
     * @see br.com.mv.regulacao.dispensacaomedicamento.dao.SolicitacaoMedicamentoDao#listarVinculoProfissionalDTO(java.lang.String, br.com.mv.regulacao.model.geral.Estabelecimento)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Collection<VinculoProfissionalDTO> listarVinculoProfissionalDTO(String descricao, Estabelecimento estabelecimento)
    {
        
        StringBuilder sql = new StringBuilder();
                
        sql.append("SELECT ");
        sql.append("vinculoprofissional.cd_vinculo_profissional   AS \"idVinculoProfissional\", ");
        sql.append("vinculoprofissional.nr_registro_conselho      AS \"registroConselho\", ");
        sql.append("profissional.cd_profissional                  AS \"idProfissional\", ");
        sql.append("uf.nm_sigla_uf                                AS \"siglaUfVinculoProfissional\", ");
        sql.append("cidadao.cd_cidadao                            AS \"idCidadao\", ");
        sql.append("cidadao.nm_cidadao                            AS \"nomeCidadao\" ");
        sql.append("FROM DBAMVFOR.TB_VINCULO_PROFISSIONAL vinculoProfissional ");
        sql.append("INNER JOIN DBAMVFOR.TB_ESTABELECIMENTO estabelecimento ON vinculoProfissional.CD_ESTABELECIMENTO=estabelecimento.CD_ESTABELECIMENTO ");
        sql.append("INNER JOIN DBAMVFOR.MV_OCUPACAO_CBO ocupacao ON vinculoProfissional.CD_OCUPACAO=ocupacao.ID_OCUPACAO ");
        sql.append("INNER JOIN DBAMVFOR.TB_PROFISSIONAL profissional ON vinculoProfissional.CD_PROFISSIONAL=profissional.CD_PROFISSIONAL ");
        sql.append("INNER JOIN DBAMVFOR.TB_CIDADAO cidadao ON profissional.CD_CIDADAO=cidadao.CD_CIDADAO ");
        sql.append("INNER JOIN DBAMVFOR.UF uf ON vinculoProfissional.CD_UF=uf.CD_UF  ");
        sql.append("WHERE cidadao.nm_cidadao LIKE :nomeProfissional ");
        sql.append("AND (ocupacao.descr like '%MEDICO%' OR ocupacao.descr like '%MÉDICO%') ");
        sql.append("AND profissional.SN_HABILITADO = 1 ");
        sql.append("AND estabelecimento.CD_ESTABELECIMENTO = :idEstabelecimento ");
        sql.append("AND vinculoProfissional.SN_HABILITADO = 1 ");
        sql.append("ORDER BY cidadao.NM_CIDADAO ASC ");
        
        SQLQuery query = getSession().createSQLQuery(sql.toString());
        query.setResultTransformer(new AliasToBeanResultTransformer(VinculoProfissionalDTO.class));
        
        query.setParameter("nomeProfissional", descricao.concat("%"));
        query.setParameter("idEstabelecimento", estabelecimento.getId());
        
        Collection<VinculoProfissionalDTO> listaVinculoProfissional = query.list();
        
        return listaVinculoProfissional;
    }

    @Override
    public Boolean existeSolicitacaoMedicamentoComMesmoReceituario(SolicitacaoMedicamento solicitacaoMedicamento) throws DispensacaoMedicamentoException
    {
        
        StringBuilder sql = new StringBuilder();
        
        sql.append("select ");
        sql.append("count(cd_solicitacao_medicamento)  ");
        sql.append("from dbamvfor.solicitacao_medicamento ");
        sql.append("where cd_cidadao_paciente = :cdCidadaoPaciente ");
        sql.append("and ds_codigo_receita = :dsCodicoReceita ");
        
        SQLQuery query = getSession().createSQLQuery(sql.toString());
        
        query.setParameter("cdCidadaoPaciente", solicitacaoMedicamento.getCidadaoPaciente().getId());
        query.setParameter("dsCodicoReceita", solicitacaoMedicamento.getCodigoReceita());
        
        BigDecimal count = (BigDecimal) query.uniqueResult();
        
        if(count.intValue() > 0)
            return true;
        else
            return false;
    }
    
}
