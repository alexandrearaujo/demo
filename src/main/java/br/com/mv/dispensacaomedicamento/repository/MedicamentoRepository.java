package br.com.mv.dispensacaomedicamento.repository;

import java.math.BigDecimal;
import java.util.Collection;

import org.hibernate.SQLQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.mv.dispensacaomedicamento.model.Medicamento;

@Repository
public interface MedicamentoRepository extends CrudRepository<Medicamento, Long>{

	@Query("select m from Medicamento m")
	public Collection<Medicamento> listarMedicamento();
	
    public Boolean isMedicamentoVinculadoAItemSolicitacaoPorSituacao(Medicamento medicamento, SituacaoSolicitacaoMedicamento situacao, EnumCondicaoSql condicao)
    {

        StringBuilder sql = getSqlVinculadoItemSolicitacaoPorSituacao(medicamento, situacao,condicao);

        SQLQuery query = getCurrentSession().createSQLQuery(sql.toString());

        setParametros(query, medicamento, situacao);
        
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
    
    private StringBuilder getSqlVinculadoItemSolicitacaoPorSituacao(Medicamento medicamento, SituacaoSolicitacaoMedicamento situacao, EnumCondicaoSql condicao)
    {
        StringBuilder sql = new StringBuilder();
        boolean isTemParametroSituacao = (situacao != null) ? true : false;
        sql.append("    select count(*) from solicitacao_medicamento sme  ");
        sql.append("    inner join item_solicitacao_medicamento ism on sme.cd_solicitacao_medicamento = ism.cd_solicitacao_medicamento   ");
        sql.append("    inner join medicamento_item_unidade miu on miu.cd_medicamento_item_unidade = ism.cd_medicamento_item_unidade   ");

        if (isTemParametroSituacao)
        {
            sql.append("    inner join dbamvfor.solicitacao_medicamento sm on sm.cd_solicitacao_medicamento = ism.cd_solicitacao_medicamento   ");
        }
        
        if(condicao != EnumCondicaoSql.IGUAL)
            sql.append("  where  miu.cd_medicamento "+ EnumCondicaoSql.IGUAL.getDescricao() +" :idMedicamento  AND");
        else{
            sql.append("  where  miu.cd_medicamento "+ condicao.getDescricao() +" :idMedicamento  AND");
        }
        
        if (isTemParametroSituacao)
        {
            sql.append("   sme.cd_situacao_solicitacao_mdcmt "+ condicao.getDescricao() +" :situacaoSolicitacao AND");                
        }
        return sql.delete(sql.length() - 3, sql.length());

    }
    
    private void setParametros(SQLQuery query, Medicamento Medicamento, SituacaoSolicitacaoMedicamento situacao)
    {
        query.setLong("idMedicamento", Medicamento.getId());
        if (situacao != null)
        {
            query.setLong("situacaoSolicitacao", situacao.getId());
        }

    }	
	
}
