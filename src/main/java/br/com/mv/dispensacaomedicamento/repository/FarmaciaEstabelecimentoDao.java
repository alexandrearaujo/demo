package br.com.mv.dispensacaomedicamento.repository;

import java.util.Collection;

import org.hibernate.SQLQuery;

import br.com.mv.commons.web.dao.GenericDao;
import br.com.mv.regulacao.dispensacaomedicamento.model.FarmaciaEstabelecimento;
import br.com.mv.regulacao.dispensacaomedicamento.model.FarmaciaEstabelecimentoDTO;
import br.com.mv.regulacao.model.geral.Estabelecimento;

public interface FarmaciaEstabelecimentoDao extends GenericDao<FarmaciaEstabelecimento>
{
    @SuppressWarnings("unchecked")
    @Override
    public Collection<FarmaciaEstabelecimentoDTO> listarFarmaciaEstabelecimento(String descricao, Estabelecimento estabelecimento)
    {
        StringBuffer sql = new StringBuffer();
        
        sql.append("select ");
        sql.append("estabelecimentoSolicit.cd_estabelecimento  AS \"idEstabelecimento\" , ");
        sql.append("estabelecimentoSolicit.nm_fantasia AS \"nomeFantasiaEstabelecimento\" ");
        sql.append("from dbamvfor.farmacia_estabelecimento fes ");
        sql.append("inner join dbamvfor.farmacia far on far.cd_farmacia = fes.cd_farmacia ");
        sql.append("inner join dbamvfor.tb_estabelecimento estabelecimentoSolicit on estabelecimentoSolicit.cd_estabelecimento = fes.cd_estabelecimento ");
        sql.append("inner join dbamvfor.estabelecimento_set_estblm ese on ese.cd_estabelecimento_set_estblm = far.cd_estabelecimento_set_estblm ");
        sql.append("inner join dbamvfor.tb_estabelecimento estabelecimentoLogado on estabelecimentoLogado.cd_estabelecimento = ese.cd_estabelecimento ");
        sql.append("where estabelecimentosolicit.nm_fantasia like :descricao ");
        sql.append("and estabelecimentoLogado.cd_estabelecimento = :idEstabelecimento ");
        sql.append("group by estabelecimentoSolicit.cd_estabelecimento, estabelecimentoSolicit.nm_fantasia ");
        
        SQLQuery query = getSession().createSQLQuery(sql.toString());
        query.setResultTransformer(new AliasToBeanResultTransformer(FarmaciaEstabelecimentoDTO.class));
        
        query.setParameter("descricao", descricao.concat("%"));
        query.setParameter("idEstabelecimento", estabelecimento.getId());
        
        Collection<FarmaciaEstabelecimentoDTO> listaFarmaciaEstabelecimento = query.list();

        return listaFarmaciaEstabelecimento;
    }  
    
    @SuppressWarnings("unchecked")
    @Override
    public Collection<FarmaciaEstabelecimentoDTO> list( FarmaciaEstabelecimento farmaciaEstabelecimento)
    {
        StringBuffer sql = new StringBuffer();
        
        
        sql.append("select ");
        sql.append("fm.cd_farmacia_estabelecimento  AS \"id\", ");
        sql.append("f.nr_crf  AS \"crf\", ");
        sql.append("f.cd_farmacia AS \"idFarmacia\", ");
        sql.append("f.ds_farmacia AS \"farmacia\", ");
        sql.append("e.nm_fantasia AS \"nomeFantasiaEstabelecimento\", ");
        sql.append("se.ds_setor_estabelecimento AS \"setor\" ");
        sql.append("from dbamvfor.farmacia f ");
        sql.append("inner join DBAMVFOR.farmacia_estabelecimento fm on fm.cd_farmacia = f.cd_farmacia ");
        sql.append("inner join DBAMVFOR.tb_estabelecimento e on e.cd_estabelecimento = fm.cd_estabelecimento ");
        sql.append("left join dbamvfor.estabelecimento_set_estblm ese on ese.cd_estabelecimento_set_estblm = f.cd_estabelecimento_set_estblm ");
        sql.append("left join DBAMVFOR.setor_estabelecimento se on se.cd_setor_estabelecimento = ese.cd_setor_estabelecimento ");
        sql.append("where 1=1 ");
        
        if(farmaciaEstabelecimento.getFarmacia().getNumeroCRF() != null && farmaciaEstabelecimento.getFarmacia().getNumeroCRF() != 0) 
            sql.append("and f.nr_crf = :crf ");
        if(farmaciaEstabelecimento.getFarmacia().getDescricaoFarmacia() != null)
            sql.append("and f.ds_farmacia like :descricao ");
        if(farmaciaEstabelecimento.getEstabelecimento().getNomeFantasia() != null)
            sql.append("and se.ds_setor_estabelecimento like :setor ");
        if(farmaciaEstabelecimento.getFarmacia().getEstabelecimentoSetorEstabelecimento() != null)
            sql.append("and e.nm_fantasia like :nomeFantasiaEstabelecimento ");
        
        
        SQLQuery query = getSession().createSQLQuery(sql.toString());
        query.setResultTransformer(new AliasToBeanResultTransformer(FarmaciaEstabelecimentoDTO.class));
        
        if(farmaciaEstabelecimento.getFarmacia().getDescricaoFarmacia() != null)
            query.setParameter("descricao", farmaciaEstabelecimento.getFarmacia().getDescricaoFarmacia());
        if(farmaciaEstabelecimento.getFarmacia().getNumeroCRF() != null && farmaciaEstabelecimento.getFarmacia().getNumeroCRF() != 0 ) 
            query.setParameter("crf", farmaciaEstabelecimento.getFarmacia().getNumeroCRF());
        if(farmaciaEstabelecimento.getEstabelecimento().getNomeFantasia() != null)
            query.setParameter("setor", farmaciaEstabelecimento.getEstabelecimento().getNomeFantasia());
        if(farmaciaEstabelecimento.getFarmacia().getEstabelecimentoSetorEstabelecimento() != null)
            query.setParameter("nomeFantasiaEstabelecimento", farmaciaEstabelecimento.getFarmacia().getEstabelecimentoSetorEstabelecimento());
        
              
        Collection<FarmaciaEstabelecimentoDTO> lista = query.list();

        return lista;
    }



}
