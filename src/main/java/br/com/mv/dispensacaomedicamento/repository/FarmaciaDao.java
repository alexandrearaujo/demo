/**
 * 
 */
package br.com.mv.dispensacaomedicamento.repository;

import java.util.Collection;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import br.com.mv.commons.web.dao.GenericDao;
import br.com.mv.geral.model.VinculoProfissionalDTO;
import br.com.mv.regulacao.dispensacaomedicamento.model.Farmacia;
import br.com.mv.regulacao.dispensacaomedicamento.model.FarmaciaEstabelecimento;
import br.com.mv.regulacao.model.geral.Estabelecimento;

/**
 * @author joao.franco
 *
 */
public interface FarmaciaDao extends GenericDao<Farmacia>
{
	@Repository("farmaciaDao")
	public class FarmaciaDaoHibernate extends GenericDaoHibernate<Farmacia> implements FarmaciaDao
	{

	    @Override
	    public Collection<Farmacia> buscarFarmacia(FarmaciaEstabelecimento farmaciaEstabelecimento)
	    {
	        // TODO Auto-generated method stub
	        StringBuffer sql = new StringBuffer();
	        
	        
	        sql.append("select ");
	        sql.append("fm.cd_farmacia_estabelecimento  AS \"id\",");
	        sql.append("f.nr_crf  AS \"crf\", ");
	        sql.append("f.ds_farmacia AS \"farmacia\", ");
	        sql.append("e.nm_fantasia AS \"nomeFantasiaEstabelecimento\", ");
	        sql.append("se.ds_setor_estabelecimento AS \"setor\" ");
	        sql.append("from dbamvfor.farmacia f ");
	        sql.append("inner join DBAMVFOR.farmacia_estabelecimento fm on fm.cd_farmacia = f.cd_farmacia ");
	        sql.append("inner join DBAMVFOR.tb_estabelecimento e on e.cd_estabelecimento = fm.cd_estabelecimento ");
	        sql.append("inner join dbamvfor.estabelecimento_set_estblm ese on ese.cd_estabelecimento_set_estblm = f.cd_estabelecimento_set_estblm ");
	        sql.append("inner join DBAMVFOR.setor_estabelecimento se on se.cd_setor_estabelecimento = ese.cd_setor_estabelecimento ");
	        sql.append("where 1 = 1 ");
	        if(farmaciaEstabelecimento.getFarmacia().getNumeroCRF() != null && farmaciaEstabelecimento.getFarmacia().getNumeroCRF() != 0) 
	            sql.append("and f.nr_crf = :crf ");
	        if(farmaciaEstabelecimento.getFarmacia().getDescricaoFarmacia() != null)
	            sql.append("and f.ds_farmacia like :descricao ");
	        if(farmaciaEstabelecimento.getEstabelecimento().getNomeFantasia() != null)
	            sql.append("and se.ds_setor_estabelecimento like :setor ");
	        if(farmaciaEstabelecimento.getFarmacia().getEstabelecimentoSetorEstabelecimento() != null)
	            sql.append("and e.nm_fantasia like :nomeFantasiaEstabelecimento ");
	        
	        SQLQuery query = getSession().createSQLQuery(sql.toString());
	        query.setResultTransformer(new AliasToBeanResultTransformer(Farmacia.class));
	        
	        if(farmaciaEstabelecimento.getFarmacia().getDescricaoFarmacia() != null)
	            query.setParameter("descricao", farmaciaEstabelecimento.getFarmacia().getDescricaoFarmacia());
	        if(farmaciaEstabelecimento.getFarmacia().getNumeroCRF() != null && farmaciaEstabelecimento.getFarmacia().getNumeroCRF() != 0 ) 
	            query.setParameter("crf", farmaciaEstabelecimento.getFarmacia().getNumeroCRF());
	        if(farmaciaEstabelecimento.getEstabelecimento().getNomeFantasia() != null)
	            query.setParameter("setor", farmaciaEstabelecimento.getEstabelecimento().getNomeFantasia());
	        if(farmaciaEstabelecimento.getFarmacia().getEstabelecimentoSetorEstabelecimento() != null)
	            query.setParameter("nomeFantasiaEstabelecimento", farmaciaEstabelecimento.getFarmacia().getEstabelecimentoSetorEstabelecimento());
	        
	              
	        Collection<Farmacia> list = query.list();
	        
	        return list;
	    }
	    

	    @SuppressWarnings("unchecked")
	    @Override
	    public Collection<VinculoProfissionalDTO> listarFarmaceutico(String descricao, Estabelecimento estabelecimento)
	    {
	        StringBuffer sql = new StringBuffer();
	        
	        sql.append("select ");
	        sql.append("distinct(vp.cd_estabelecimento) AS \"idEstabelecimento\", ");
	        sql.append("p.cd_cidadao AS \"idCidadao\", ");
	        sql.append("p.cd_profissional AS \"idProfissional\", ");
	        sql.append("cid.nm_cidadao AS \"nomeCidadao\" ");
	        sql.append("from dbamvfor.tb_profissional p ");
	        sql.append("inner join dbamvfor.tb_vinculo_profissional vp on p.cd_profissional = vp.cd_profissional ");
	        sql.append("inner join dbamvfor.mv_ocupacao_cbo ocbo on ocbo.id_ocupacao = vp.cd_ocupacao ");
	        sql.append("inner join DBAMVFOR.tb_cidadao cid on cid.cd_cidadao = p.cd_cidadao ");
	        sql.append("where (ocbo.descr like '%FARMACE%' OR ocbo.descr like '%FARMACÊ%') ");
	        sql.append("and cid.nm_cidadao like :nomeCidadao ");
	        sql.append("and vp.cd_estabelecimento = :idEstabelecimento ");
	        
	        
	        SQLQuery query = getSession().createSQLQuery(sql.toString());
	        query.setResultTransformer(new AliasToBeanResultTransformer(VinculoProfissionalDTO.class));
	        
	        query.setParameter("nomeCidadao", descricao.concat("%"));
	        query.setParameter("idEstabelecimento", estabelecimento.getId());
	        
	        Collection<VinculoProfissionalDTO> listarFarmaceutico = query.list();
	        
	        return listarFarmaceutico;
	    }


	    @Override
	    public Collection<Farmacia> buscarFarmacia(Farmacia farmacia)
	    {
	        // TODO Auto-generated method stub
	        return null;
	    }    


}
