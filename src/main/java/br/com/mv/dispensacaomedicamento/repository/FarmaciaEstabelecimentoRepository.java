package br.com.mv.dispensacaomedicamento.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.mv.dispensacaomedicamento.model.FarmaciaEstabelecimento;

@Repository
public interface FarmaciaEstabelecimentoRepository extends CrudRepository<FarmaciaEstabelecimento, Long>
{

    @Query(
    	"select "+
    			"estabelecimentoSolicit.cd_estabelecimento , "+
    			"estabelecimentoSolicit.nm_fantasia "+
    	"from dbamvfor.farmacia_estabelecimento fes "+
    		 "inner join dbamvfor.farmacia far on far.cd_farmacia = fes.cd_farmacia "+
    		 "inner join dbamvfor.tb_estabelecimento estabelecimentoSolicit on estabelecimentoSolicit.cd_estabelecimento = fes.cd_estabelecimento "+
    		 "inner join dbamvfor.estabelecimento_set_estblm ese on ese.cd_estabelecimento_set_estblm = far.cd_estabelecimento_set_estblm "+
    		 "inner join dbamvfor.tb_estabelecimento estabelecimentoLogado on estabelecimentoLogado.cd_estabelecimento = ese.cd_estabelecimento "+
        "where estabelecimentosolicit.nm_fantasia like :descricao% "+
        	"and estabelecimentoLogado.cd_estabelecimento = :idEstabelecimento "+
    	"group by estabelecimentoSolicit.cd_estabelecimento, estabelecimentoSolicit.nm_fantasia ")
	public Collection<FarmaciaEstabelecimento> listarFarmaciaEstabelecimento(@Param("descricao") String descricao, @Param("idEstabelecimento") Long idEstabelecimento);
    
    @Query(        
    		"select "+
            "fm.cd_farmacia_estabelecimento  AS \"id\", "+
            "f.nr_crf  AS \"crf\", "+
            "f.cd_farmacia AS \"idFarmacia\", "+
            "f.ds_farmacia AS \"farmacia\", "+
            "e.nm_fantasia AS \"nomeFantasiaEstabelecimento\", "+
            "se.ds_setor_estabelecimento AS \"setor\" "+
            "from dbamvfor.farmacia f "+
            "inner join DBAMVFOR.farmacia_estabelecimento fm on fm.cd_farmacia = f.cd_farmacia "+
            "inner join DBAMVFOR.tb_estabelecimento e on e.cd_estabelecimento = fm.cd_estabelecimento "+
            "left join dbamvfor.estabelecimento_set_estblm ese on ese.cd_estabelecimento_set_estblm = f.cd_estabelecimento_set_estblm "+
            "left join DBAMVFOR.setor_estabelecimento se on se.cd_setor_estabelecimento = ese.cd_setor_estabelecimento "+
            "where  "+
                "	 (f.nr_crf = :farmaciaEstabelecimento.crf or :farmaciaEstabelecimento.crf is null) "+
                "and (f.ds_farmacia like :farmaciaEstabelecimento.getFarmacia().getDescricaoFarmacia()% or :farmaciaEstabelecimento.getFarmacia().getDescricaoFarmacia() is null) "+
                "and (se.ds_setor_estabelecimento like :farmaciaEstabelecimento.getEstabelecimento().getNomeFantasia()% or :farmaciaEstabelecimento.getEstabelecimento().getNomeFantasia() is null) "+
                "and (e.nm_fantasia like :farmaciaEstabelecimento.getFarmacia().getEstabelecimentoSetorEstabelecimento()% or :farmaciaEstabelecimento.getFarmacia().getEstabelecimentoSetorEstabelecimento() is null) ")
    public Collection<FarmaciaEstabelecimento> list(@Param("farmaciaEstabelecimento") FarmaciaEstabelecimento farmaciaEstabelecimento);



}
