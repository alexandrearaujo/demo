package br.com.mv.dispensacaomedicamento.repository;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;

import br.com.mv.dispensacaomedicamento.model.TipoRelatorio;

public interface TipoRelatorioRepository extends CrudRepository<TipoRelatorio,Long>
{

//    public Collection<TipoRelatorio> listarTipoRelatorio(String nomeTipoRelatorio)
//    {
//        ArrayList<ProjectionFilter> arrayList = new ArrayList<ProjectionFilter>();
//        arrayList.add(new ProjectionFilter("id"));
//        arrayList.add(new ProjectionFilter("nomeTipoRelatorio", nomeTipoRelatorio));
//        arrayList.add(new ProjectionFilter("descricaoTipoRelatorio"));
//        arrayList.add(new ProjectionFilter(Order.asc("nomeTipoRelatorio")));
//        
//        ProjectionFilter [] projectionFilters = new ProjectionFilter[arrayList.size()];
//        return get(0, arrayList.toArray(projectionFilters));
//    }
    public Collection<TipoRelatorio> listarTipoRelatorio(String nomeTipoRelatorio);
}
