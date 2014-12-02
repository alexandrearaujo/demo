package br.com.mv.dispensacaomedicamento.repository;

import java.util.Collection;

import org.hibernate.SQLQuery;

import br.com.mv.commons.web.dao.GenericDao;
import br.com.mv.regulacao.dispensacaomedicamento.model.ItemUnidade;
import br.com.mv.regulacao.dispensacaomedicamento.model.MedicamentoItemUnidade;
import br.com.mv.regulacao.dispensacaomedicamento.model.SolicitacaoMedicamento;
import br.com.mv.regulacao.dispensacaomedicamento.model.Unidade;

public interface ItemUnidadeDao extends GenericDao<ItemUnidade>
{
    @Override
    public Collection<ItemUnidade> listarPorMedicamentoItemUnidade(MedicamentoItemUnidade medicamentoItemUnidade)
    {
        StringBuilder sql = new StringBuilder();

        sql.append("    select iu.cd_item_unidade  AS \"idItemUnidade\", iu.ds_sigla_item_unidade  AS \"descricaoSigla\", iu.ds_item_unidade AS \"descricao\",iu.vl_fator  AS \"valorFator\", sn_ativo AS \"ativo\", iu.cd_unidade AS \"unidade\", u.ds_unidade AS \"unidade.descricao\"    ");
        sql.append("    from dbamvfor.item_unidade iu   ");
        sql.append("    left join dbamvfor.unidade u on u.cd_unidade = iu.cd_unidade   ");
        sql.append("    left join dbamvfor.medicamento_item_unidade miu on miu.cd_item_unidade = iu.cd_item_unidade   ");
        sql.append("    where iu.cd_item_unidade = :medicamentoItemUnidade ");

        SQLQuery query = getCurrentSession().createSQLQuery(sql.toString());

        query.setParameter("medicamentoItemUnidade", medicamentoItemUnidade.getId());
        query.setResultTransformer(new AliasToBeanResultTransformer(ItemUnidade.class));

        return query.list();
    }
}
