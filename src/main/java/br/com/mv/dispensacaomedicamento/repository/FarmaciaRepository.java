/**
 * 
 */
package br.com.mv.dispensacaomedicamento.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.mv.dispensacaomedicamento.model.Estabelecimento;
import br.com.mv.dispensacaomedicamento.model.Farmacia;
import br.com.mv.dispensacaomedicamento.model.FarmaciaEstabelecimento;

/**
 * @author joao.franco
 *
 */
@Repository
public interface FarmaciaRepository extends CrudRepository<Farmacia, Long>
{

		@Query("select "+
					"fm.cd_farmacia_estabelecimento,"+
					"f.nr_crf, "+
					"f.ds_farmacia, "+
					"e.nm_fantasia, "+
					"se.ds_setor_estabelecimento "+
				"from dbamvfor.farmacia f "+
					 "inner join DBAMVFOR.farmacia_estabelecimento fm on fm.cd_farmacia = f.cd_farmacia "+
					 "inner join DBAMVFOR.tb_estabelecimento e on e.cd_estabelecimento = fm.cd_estabelecimento "+
					 "inner join dbamvfor.estabelecimento_set_estblm ese on ese.cd_estabelecimento_set_estblm = f.cd_estabelecimento_set_estblm "+
					 "inner join DBAMVFOR.setor_estabelecimento se on se.cd_setor_estabelecimento = ese.cd_setor_estabelecimento "+
				"where "+
                "	 (f.nr_crf = :farmaciaEstabelecimento.crf or :farmaciaEstabelecimento.crf is null) "+
                "and (f.ds_farmacia like :farmaciaEstabelecimento.getFarmacia().getDescricaoFarmacia()% or :farmaciaEstabelecimento.getFarmacia().getDescricaoFarmacia() is null) "+
                "and (se.ds_setor_estabelecimento like :farmaciaEstabelecimento.getEstabelecimento().getNomeFantasia()% or :farmaciaEstabelecimento.getEstabelecimento().getNomeFantasia() is null) "+
                "and (e.nm_fantasia like :farmaciaEstabelecimento.getFarmacia().getEstabelecimentoSetorEstabelecimento()% or :farmaciaEstabelecimento.getFarmacia().getEstabelecimentoSetorEstabelecimento() is null) ")
	    public Collection<Farmacia> buscarFarmacia(@Param("farmaciaEstabelecimento") FarmaciaEstabelecimento farmaciaEstabelecimento);

	    public Collection<VinculoProfissionalDTO> listarFarmaceutico(String descricao, Estabelecimento estabelecimento)
	    {
	        StringBuffer sql = new StringBuffer(+
	        
	        "select "+
	        "distinct(vp.cd_estabelecimento) AS \"idEstabelecimento\", "+
	        "p.cd_cidadao AS \"idCidadao\", "+
	        "p.cd_profissional AS \"idProfissional\", "+
	        "cid.nm_cidadao AS \"nomeCidadao\" "+
	        "from dbamvfor.tb_profissional p "+
	        "inner join dbamvfor.tb_vinculo_profissional vp on p.cd_profissional = vp.cd_profissional "+
	        "inner join dbamvfor.mv_ocupacao_cbo ocbo on ocbo.id_ocupacao = vp.cd_ocupacao "+
	        "inner join DBAMVFOR.tb_cidadao cid on cid.cd_cidadao = p.cd_cidadao "+
	        "where (ocbo.descr like '%FARMACE%' OR ocbo.descr like '%FARMACÊ%') "+
	        "and cid.nm_cidadao like :nomeCidadao "+
	        "and vp.cd_estabelecimento = :idEstabelecimento "+
	        
	        
	        SQLQuery query = getSession().createSQLQuery(sql.toString()+
	        query.setResultTransformer(new AliasToBeanResultTransformer(VinculoProfissionalDTO.class)+
	        
	        query.setParameter("nomeCidadao", descricao.concat("%")+
	        query.setParameter("idEstabelecimento", estabelecimento.getId()+
	        
	        Collection<VinculoProfissionalDTO> listarFarmaceutico = query.list(+
	        
	        return listarFarmaceutico;
	    }


}
