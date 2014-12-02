package br.com.mv.dispensacaomedicamento.repository;

import java.math.BigDecimal;

import org.hibernate.SQLQuery;

import br.com.mv.commons.web.dao.GenericDao;
import br.com.mv.regulacao.dispensacaomedicamento.model.ItemUnidade;
import br.com.mv.regulacao.dispensacaomedicamento.model.MedicamentoItemUnidade;
import br.com.mv.regulacao.dispensacaomedicamento.model.SituacaoSolicitacaoMedicamento;
import br.com.mv.regulacao.dispensacaomedicamento.model.SolicitacaoMedicamento;
import br.com.mv.regulacao.dispensacaomedicamento.model.dicionario.EnumCondicaoSql;


public interface MedicamentoItemUnidadeRepository extends GenericDao<MedicamentoItemUnidade>
{

    @Override
    public Boolean isItemUnidadeVinculadoAItemSolicitacaoPorSituacao(MedicamentoItemUnidade medicamentoItemUnidade, SituacaoSolicitacaoMedicamento situacao, EnumCondicaoSql condicao)
    {

        StringBuilder sql = getSqlPorItemUnidadePorSolicitacao(medicamentoItemUnidade, situacao,condicao);

        SQLQuery query = getCurrentSession().createSQLQuery(sql.toString());

        setParametros(query, medicamentoItemUnidade, situacao);
        
        BigDecimal count = (BigDecimal) query.uniqueResult();

        if(condicao == EnumCondicaoSql.IGUAL){
            if (count.intValue() > 0)
                return true;
            else
                return false;            
        }else{
            if (count.intValue() == 0)
                return true;
            else
                return false;
        }
    }

    
    private StringBuilder getSqlPorItemUnidadePorSolicitacao(MedicamentoItemUnidade medicamentoItemUnidade, SituacaoSolicitacaoMedicamento situacao, EnumCondicaoSql condicao)
    {
        StringBuilder sql = new StringBuilder();
        boolean isTemParametroSituacao = (situacao != null) ? true : false;
        sql.append("    select count(miu.cd_item_unidade)  ");
        sql.append("    from dbamvfor.medicamento_item_unidade miu   ");
        sql.append("    INNER JOIN dbamvfor.item_unidade iu ON iu.cd_item_unidade = miu.cd_item_unidade   ");
        sql.append("    INNER JOIN dbamvfor.item_solicitacao_medicamento ism ON miu.cd_medicamento_item_unidade = ism.cd_medicamento_item_unidade   ");

        if (isTemParametroSituacao)
        {
            sql.append("    inner join dbamvfor.solicitacao_medicamento sm on sm.cd_solicitacao_medicamento = ism.cd_solicitacao_medicamento   ");
        }
        sql.append("    where  ");
        if (medicamentoItemUnidade.getItemUnidade() != null)
            sql.append("     miu.cd_medicamento_item_unidade = :idMedicamentoItemUnidade AND");
        if (situacao != null)
        {
            sql.append("   sm.cd_situacao_solicitacao_mdcmt "+ condicao.getDescricao() +" :situacaoSolicitacao AND");                
        }
        return sql.delete(sql.length() - 3, sql.length());

    }

    private void setParametros(SQLQuery query, MedicamentoItemUnidade medicamentoItemUnidade, SituacaoSolicitacaoMedicamento situacao)
    {
        if (medicamentoItemUnidade.getItemUnidade() != null)
        {
            query.setLong("idMedicamentoItemUnidade", medicamentoItemUnidade.getId());
        }
        if (situacao != null)
        {
            query.setLong("situacaoSolicitacao", situacao.getId());
        }

    }   
}
