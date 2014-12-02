package br.com.mv.dispensacaomedicamento.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.mv.dispensacaomedicamento.model.ItemUnidade;
import br.com.mv.dispensacaomedicamento.model.MedicamentoItemUnidade;

public interface ItemUnidadeRepository extends CrudRepository<ItemUnidade,Long>
{
    @Query(value=
            "select "+
            		"iu.cd_item_unidade  AS \"idItemUnidade\", "+
            		"iu.ds_sigla_item_unidade  AS \"descricaoSigla\", "+
            		"iu.ds_item_unidade AS \"descricao\", "+
            		"iu.vl_fator  AS \"valorFator\", "+
            		"sn_ativo AS \"ativo\", "+
            		"iu.cd_unidade AS \"unidade\", "+
            		"u.ds_unidade AS \"unidade.descricao\" "+
            "from "+
            		"dbamvfor.item_unidade iu "+
            		"left join dbamvfor.unidade u on u.cd_unidade = iu.cd_unidade "+
            		"left join dbamvfor.medicamento_item_unidade miu on miu.cd_item_unidade = iu.cd_item_unidade "+
            "where "+
    		"iu.cd_item_unidade = :medicamentoItemUnidade.getId()") 
	public Collection<ItemUnidade> listarPorMedicamentoItemUnidade(@Param("medicamentoItemUnidade") MedicamentoItemUnidade medicamentoItemUnidade);
}
